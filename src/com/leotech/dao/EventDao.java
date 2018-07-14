package com.leotech.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.ConcurrentHashMap;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
//import org.springframework.jdbc.core.RowMapper;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.leotech.util.SpringContextBase;

public class EventDao {
	private static EventDao _dao = null;
	public JdbcTemplate jdbcTemplate;
	private EventDao()
	{
		if (null == jdbcTemplate) {
			jdbcTemplate = (JdbcTemplate) SpringContextBase.getSpringContext().getBean("jdbcTemplate");
		}
	}
	public static synchronized EventDao instance(){
		if(null == _dao){
			_dao = new EventDao();
		}
		return _dao;
	}
	public JSONObject getAllEvent()
	{
		final JSONObject events = new JSONObject();
		try {
			String sqlFilter = " where isShow=1";
			String strSql = "select *  from event";
			strSql += sqlFilter;
			jdbcTemplate.query(strSql, new RowCallbackHandler(){
				public void processRow(ResultSet result) throws SQLException {
					String type = result.getString("type");
					JSONObject event = new JSONObject();
					event.put("uuid", result.getInt("uuid"));
					event.put("name", result.getString("name"));
					event.put("type", type);
					event.put("match_func", result.getString("match_func"));
					event.put("deal_func", result.getString("deal_func"));
					switch(type) {
					case "dbclick":
						if(events.containsKey("dbclick")) {
							((JSONArray)events.get("dbclick")).add(event);
						} else {
							JSONArray arr = new JSONArray();
							arr.add(event);
							events.put("dbclick", arr);
						}
						break;
					case "rclick":
						if(events.containsKey("rclick")) {
							((JSONArray)events.get("rclick")).add(event);
						} else {
							JSONArray arr = new JSONArray();
							arr.add(event);
							events.put("rclick", arr);
						}
						break;
					case "click":
						if(events.containsKey("click")) {
							((JSONArray)events.get("click")).add(event);
						} else {
							JSONArray arr = new JSONArray();
							arr.add(event);
							events.put("click", arr);
						}
						break;
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return events;
	}
}

