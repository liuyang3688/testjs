package com.ieslab.base.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.ieslab.base.dao.KaiGuanDao;

public class KaiGuanService {
	
	public static List<JSONObject> getKaiGuanByStation(String czid) {
		return KaiGuanDao.instance().getKaiGuanByStation(czid);
	}

}
