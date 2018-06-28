package com.leotech.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.leotech.dao.MeshDao;

public class MeshService {
	public static List<JSONObject> getAllMesh() {
		return MeshDao.instance().getAllMesh();
	}
	public static Boolean updateIsDirty(int uuid){
		Boolean isDirty = false;
		return MeshService.updateIsDirty(uuid, isDirty);
	}
	public static Boolean updateIsDirty(int uuid, Boolean isDirty){
		return MeshDao.instance().updateIsDirty(uuid, isDirty);
	}
	public static Boolean updateIsDirty_All(){
		Boolean isDirty = true;
		return MeshService.updateIsDirty_All(isDirty);
	}
	public static Boolean updateIsDirty_All(Boolean isDirty){
		return MeshDao.instance().updateIsDirty_All(isDirty);
	}
}
