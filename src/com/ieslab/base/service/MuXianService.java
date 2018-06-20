package com.ieslab.base.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.ieslab.base.dao.MuXianDao;

public class MuXianService {
	
	public static List<JSONObject> getMuXianByStation(String czid) {
		return MuXianDao.instance().getMuXianByStation(czid);
	}

}
