package com.ieslab.base.hbase.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.ieslab.base.hbase.dao.FengJiDao;

public class FengJiService {
	
	public static List<JSONObject> getFengJiData(final String bjcsids, final String bjid, String starttime, String endtime) {
	
		return FengJiDao.instance().getFengjiData(bjcsids, bjid, starttime, endtime);
	}	
}
