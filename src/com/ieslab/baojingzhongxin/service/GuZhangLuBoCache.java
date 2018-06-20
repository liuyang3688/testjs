package com.ieslab.baojingzhongxin.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.JSONObject;

public class GuZhangLuBoCache {

	public static Map<String, JSONObject> cacheMap = new ConcurrentHashMap<String, JSONObject>();
}
