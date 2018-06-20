package com.ieslab.base.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.JSONObject;
import com.ieslab.base.dao.BaseTableDao;

public class BaseModelCacheService{
	
	BaseTableDao dao = null;
	public BaseModelCacheService(){
		dao = new BaseTableDao();
	}
	/**
	 * 通过部件类型、部件ID获取某个部件信息
	 * @param bjtype
	 * @param bjid
	 * @return
	 */
	public JSONObject getBaseObject(String bjtype, String bjid){
		//首先判断这个bytype对应的部件类型是否已经在缓存存在
		if(BaseModelCache.getInstance().getBjTypeMap().containsKey(bjtype)){
			return BaseModelCache.getInstance().getBjTypeMap().get(bjtype).get(bjid);
		}else{//如果不存在，那么去初始化这个bjtype对应的表的信息
			//根据bjtype获取表名
			String tableName = dao.getTableNameByBjType(bjtype);
			if(tableName != null){
				List<JSONObject> list = dao.getTableInfo(tableName);
				if(list != null){
					Map<String, JSONObject> map = new ConcurrentHashMap<String, JSONObject>();
					for(JSONObject obj: list){
						map.put(obj.getString("id"), obj);
					}
					BaseModelCache.getInstance().getBjTypeMap().put(bjtype, map);
					if(map.containsKey(bjid)){
						return map.get(bjid);
					}
				}
			}
		}
		return null;
	}
}
