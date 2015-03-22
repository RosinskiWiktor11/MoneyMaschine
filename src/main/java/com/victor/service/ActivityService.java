package com.victor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.victor.dao.ActivityDAO;

/**
 * @author Wiktor
 *
 */
@Service
public class ActivityService {

	@Autowired
	ActivityDAO activityDAO;

	/** {@link ActivityService#activityDAO} */
	public void setActivityDAO(ActivityDAO activityDAO) {
		this.activityDAO = activityDAO;
	}

}
