package com.leotech.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.leotech.dao.CloneDao;

public class CloneService {
	public static List<JSONObject> getAllClone() {
		return CloneDao.instance().getAllClone();
	}
	public static Boolean updateIsDirty(int uuid){
		Boolean isDirty = false;
		return CloneService.updateIsDirty(uuid, isDirty);
	}
	public static Boolean updateIsDirty(int uuid, Boolean isDirty){
		return CloneDao.instance().updateIsDirty(uuid, isDirty);
	}
	public static Boolean updateIsDirty_All(){
		Boolean isDirty = true;
		return CloneService.updateIsDirty_All(isDirty);
	}
	public static Boolean updateIsDirty_All(Boolean isDirty){
		return CloneDao.instance().updateIsDirty_All(isDirty);
	}
}