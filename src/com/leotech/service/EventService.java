package com.leotech.service;


import com.alibaba.fastjson.JSONObject;
import com.leotech.dao.EventDao;

public class EventService {
	public static JSONObject getAllEvent() {
		return EventDao.instance().getAllEvent();
	}
}