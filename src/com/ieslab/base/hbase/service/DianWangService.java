package com.ieslab.base.hbase.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.ieslab.base.hbase.dao.DianWangDao;

public class DianWangService {
	
	public static List<JSONObject> getDianWangData(final String bjcsids, final String bjid, String starttime, String endtime) {
		return DianWangDao.instance().getDianWangData(bjcsids, bjid, starttime, endtime);
	}
}
