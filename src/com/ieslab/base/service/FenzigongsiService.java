package com.ieslab.base.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

public class FenzigongsiService {

	/**
	 * 根据公司ID获取这个公司下分子公司的名称、所在省份和经纬度
	 * @param JiTuanID
	 * @param type 
	 * @return
	 */
	public static List<JSONObject> findAllfenCompany(){
		return BaseModelCache.getInstance().getGongsiList();
	}
}
