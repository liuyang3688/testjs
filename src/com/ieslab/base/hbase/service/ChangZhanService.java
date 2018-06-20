package com.ieslab.base.hbase.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.ieslab.base.hbase.dao.ChangZhanDao;

public class ChangZhanService {
	
	public static List<JSONObject> getChangZhanData(final String bjcsids, final String bjid, String starttime, String endtime) {
		return ChangZhanDao.instance().getChangZhanData(bjcsids, bjid, starttime, endtime);
	}	
}
