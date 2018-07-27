package com.leotech.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONArray;
import com.leotech.util.SpringContextBase;

public class AreaDao {
	private static AreaDao _dao = null;
	public JdbcTemplate jdbcTemplate;
	private AreaDao()
	{
		if (null == jdbcTemplate) {
			jdbcTemplate = (JdbcTemplate) SpringContextBase.getSpringContext().getBean("jdbcTemplate");
		}
	}
	public static synchronized AreaDao instance(){
		if(null == _dao){
			_dao = new AreaDao();
		}
		return _dao;
	}
	public JSONArray getAllArea()
	{
		final JSONArray areas = new JSONArray();
		try {
			String sqlFilter = " where 1=1";
			String strSql = "select * from area";
			strSql += sqlFilter;
			jdbcTemplate.query(strSql, new RowCallbackHandler(){
				public void processRow(ResultSet result) throws SQLException {
					JSONObject area = new JSONObject();
					area.put("id", result.getInt("id"));
					area.put("name", result.getString("name"));
					areas.add(area);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return areas;
	}

}

