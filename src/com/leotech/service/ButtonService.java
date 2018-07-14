package com.leotech.service;


import com.alibaba.fastjson.JSONArray;
import com.leotech.dao.ButtonDao;

public class ButtonService {
	public static JSONArray getAllBtn() {
		return ButtonDao.instance().getAllBtn();
	}
}
