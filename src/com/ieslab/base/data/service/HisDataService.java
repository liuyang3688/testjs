package com.ieslab.base.data.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.ieslab.base.data.dao.HisDataDao;

public class HisDataService {
	
	public static List<JSONObject> getYucePowerByCzid(String czid, String yuceType, String starttime, String endtime){
	
		return HisDataDao.getInstance().getYucePowerByCzid(czid, yuceType, starttime, endtime);
	}	
}
