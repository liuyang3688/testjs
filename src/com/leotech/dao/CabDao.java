package com.leotech.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONArray;
import com.leotech.util.SpringContextBase;

public class CabDao {
	private static CabDao _dao = null;
	public JdbcTemplate jdbcTemplate;
	private CabDao()
	{
		if (null == jdbcTemplate) {
			jdbcTemplate = (JdbcTemplate) SpringContextBase.getSpringContext().getBean("jdbcTemplate");
		}
	}
	public static synchronized CabDao instance(){
		if(null == _dao){
			_dao = new CabDao();
		}
		return _dao;
	}
	public JSONArray getAllCab()
	{
		final JSONArray cabs = new JSONArray();
		try {
			String sqlFilter = " where cabinet.areaId=area.id and cabinet.systemId=system.id";
			String strSql = "select cabinet.code,cabinet.areaId,cabinet.systemId,area.name,system.name from cabinet, area, system";
			strSql += sqlFilter;
			jdbcTemplate.query(strSql, new RowCallbackHandler(){
				public void processRow(ResultSet result) throws SQLException {
					JSONObject cab = new JSONObject();
					cab.put("code", result.getString("code"));
					cab.put("areaId", result.getInt("areaId"));
					cab.put("systemId", result.getInt("systemId"));
					cab.put("areaName", result.getString("area.name"));
					cab.put("systemName", result.getString("system.name"));
					cabs.add(cab);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cabs;
	}

}

