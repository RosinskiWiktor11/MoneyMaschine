package com.victor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.victor.dao.FundActivityDAO;

/**
 * @author Wiktor
 *
 */
@Service
public class FundActivityService {

	@Autowired
	FundActivityDAO fundActivityDAO;

	/** {@link FundActivityService#fundActivityDAO} */
	public void setFundActivityDAO(FundActivityDAO fundActivityDAO) {
		this.fundActivityDAO = fundActivityDAO;
	}

}
