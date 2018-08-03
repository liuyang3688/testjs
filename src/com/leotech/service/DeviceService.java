package com.leotech.service;

import com.alibaba.fastjson.JSONObject;
import com.leotech.dao.DeviceDao;

public class DeviceService {
	public static JSONObject getDeviceInfo(String code) {
		return DeviceDao.instance().getDeviceInfo(code);
	}
}
