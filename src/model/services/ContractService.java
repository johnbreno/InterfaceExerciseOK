package model.services;

import java.util.Calendar;
import java.util.Date;

import model.entities.Contract;
import model.entities.Installment;

public class ContractService {

	private OnlinePaymentService onlinePaymentService;
	
	public ContractService(OnlinePaymentService onlinePaymentService) {
		this.onlinePaymentService = onlinePaymentService;
	}
	
	public void processContract(Contract contract, int months) {
		double basicQuota = contract.getTotalValue() / months;
		for (int i = 1; i <= months; i++) {
			Date date = addMonths(contract.getDate(), i);
			double updatedQuota = basicQuota + onlinePaymentService.interest(basicQuota, i);
			double fullQuota = updatedQuota + onlinePaymentService.paymentFee(updatedQuota);
			contract.addInstallment(new Installment(date, fullQuota));
		}
	}
	
	public Date addMonths(Date date, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, n);
		return cal.getTime();
	}
	
//	public void processContract(Contract contract, int numberOfInstallments) {
//		
//		List<Installment> installments = new ArrayList<>();
//		double installmentAmount = (double)(contract.getTotalValue() / numberOfInstallments);
//		for (int i=1; i<=numberOfInstallments; i++) {
//			Calendar cal = Calendar.getInstance();
//			cal.setTime(contract.getDate());
//			cal.add(Calendar.MONTH, i);
//			
//			Date nextDueDate = cal.getTime();
//			double QuotaAmount = paymentService.getQuota(installmentAmount, i);
//			
//			installments.add(new Installment(nextDueDate, QuotaAmount));
//		}
//		
//		contract.setInstallments(installments);
//	}
	
}
