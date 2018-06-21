package com.leotech.service;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.leotech.dao.FengchangDao;
public class FengChangService {

	/**
	 * 根据公司ID获取这个公司下的风场信息
	 * @param comid
	 * @return
	 */
	public static List<JSONObject> findAllCZ() {
		List<JSONObject> list = FengchangDao.instance().findAllCZ();
		
		List<JSONObject> temp = new ArrayList<JSONObject>();
		
		for(JSONObject obj: list){
			temp.add(obj);
		}
		return temp;
	}
}
