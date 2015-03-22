package com.victor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.victor.dao.PaymentDAO;

/**
 * @author Wiktor
 *
 */
@Service
public class PaymentService {

	@Autowired
	PaymentDAO paymentDAO;

	/** {@link PaymentService#paymentDAO} */
	public void setPaymentDAO(PaymentDAO paymentDAO) {
		this.paymentDAO = paymentDAO;
	}

}
