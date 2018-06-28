package com.leotech.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.leotech.dao.LabelDao;

public class LabelService {
	public static List<JSONObject> getAllLabel() {
		return LabelDao.instance().getAllLabel();
	}
	public static Boolean updateIsDirty(int uuid){
		Boolean isDirty = false;
		return LabelService.updateIsDirty(uuid, isDirty);
	}
	public static Boolean updateIsDirty(int uuid, Boolean isDirty){
		return LabelDao.instance().updateIsDirty(uuid, isDirty);
	}
	public static Boolean updateIsDirty_All(){
		Boolean isDirty = true;
		return LabelService.updateIsDirty_All(isDirty);
	}
	public static Boolean updateIsDirty_All(Boolean isDirty){
		return LabelDao.instance().updateIsDirty_All(isDirty);
	}
}