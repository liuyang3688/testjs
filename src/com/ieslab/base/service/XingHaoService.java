package com.ieslab.base.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.ieslab.base.dao.XinghaoDao;

public class XingHaoService {
	/**
	 * 获取所有的型号信息
	 * @return
	 */
	public static List<JSONObject> getAllXingHao(){
		return BaseModelCache.getInstance().getXinghaoList();
	}
	
	/**
	 * 根据场站id查询型号
	 * @return
	 */
	public static List<JSONObject> getAllXingHaoBychangzhan(){
		XinghaoDao xinghaoDao = new XinghaoDao();
		return xinghaoDao.xinghaoBychangzhan();
	}

}
