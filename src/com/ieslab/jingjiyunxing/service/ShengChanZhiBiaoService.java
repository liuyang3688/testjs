package com.ieslab.jingjiyunxing.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.jdbc.core.RowMapper;

import com.alibaba.fastjson.JSONObject;
import com.ieslab.base.service.BaseModelCache;
import com.ieslab.jingjiyunxing.dao.ShengChanZhiBiaoDao;
import com.ieslab.util.DateUtil;

public class ShengChanZhiBiaoService {
	/**
	 * 获取指标数据
	 * @param table 
	 * @param ids 
	 * @param zbid 
	 * @param startTime 
	 * @param endTime 
	 * @param timeType 
	 * @param weeknum 
	 * @param timeType 
	 * @param week 
	 * @param jibie 
	 * @return
	 */
	public static List<JSONObject> findData(String table, String ids, String zbid, String startTime, String endTime, String timeType, String week, String jibie){
		List<JSONObject> list = null;
		list = ShengChanZhiBiaoDao.instance().findData(table,ids,zbid,startTime,endTime,timeType,week,jibie);
		return list;
	}
	
	/**
	 * 获取对标数据
	 * @param table 
	 * @param ids 
	 * @param zbid 
	 * @param startTime 
	 * @param endTime 
	 * @param timeType 
	 * @param weeknum 
	 * @param timeType 
	 * @param week 
	 * @param jibie 
	 * @return
	 */
	public static List<JSONObject> findDuibiaoData(String table, String ids, String zbid, 
			String startTime, String endTime, String dbStartTime, String dbEndTime, 
			String timeType, String week, String dbweek, String jibie){
		
		List<JSONObject> lst = new ArrayList<JSONObject>();
		
		if("CLFJXMCaps".equals(zbid.split("_")[0])){
			return lst;
		}
		List<JSONObject> list = ShengChanZhiBiaoDao.instance().findData(table,ids,zbid,startTime,endTime,timeType,week,jibie);
		List<JSONObject> dbList = ShengChanZhiBiaoDao.instance().findData(table,ids,zbid,dbStartTime,dbEndTime,timeType,dbweek,jibie);
		Map<String, JSONObject> dbMap = new ConcurrentHashMap<String, JSONObject>();
		for(JSONObject obj : dbList){
			dbMap.put(obj.getString("id"), obj);
		}
		for(int i = 0;i < list.size();i++){
			if(dbMap.containsKey(list.get(i).getString("id"))){
				float value = list.get(i).getFloat(zbid)-dbMap.get(list.get(i).getString("id")).getFloat(zbid);
				JSONObject o = new JSONObject();
				o.put("name", list.get(i).getString("name"));
				o.put("value", value);
				lst.add(o);
			}
		}
		return lst;
	}
	
	/**
	 * 获取历史数据
	 * @param table 
	 * @param ids 
	 * @param zbid 
	 * @param jibie 
	 * @return
	 */
	public static List<JSONObject> findHisData(String table, String ids, String zbid, String jibie){
		List<JSONObject> lst = new ArrayList<JSONObject>();
		
		if("CLFJXMCaps".equals(zbid.split("_")[0])){
			return lst;
		}
		String bjlx = "";
	    switch(jibie){
		    case "1":bjlx = "1";break;
		    case "2":bjlx = "5";break;
		    case "3":bjlx = "34";break;
		    case "4":bjlx = "31";break;
	    }
		lst = ShengChanZhiBiaoDao.instance().findHisData(table, ids, zbid, jibie, bjlx);
		
		return lst;
	}
}
