package com.leotech.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONArray;
import com.leotech.util.SpringContextBase;

public class SysDao {
	private static SysDao _dao = null;
	public JdbcTemplate jdbcTemplate;
	private SysDao()
	{
		if (null == jdbcTemplate) {
			jdbcTemplate = (JdbcTemplate) SpringContextBase.getSpringContext().getBean("jdbcTemplate");
		}
	}
	public static synchronized SysDao instance(){
		if(null == _dao){
			_dao = new SysDao();
		}
		return _dao;
	}
	public JSONArray getAllSys()
	{
		final JSONArray syss = new JSONArray();
		try {
			String sqlFilter = " where 1=1";
			String strSql = "select * from system";
			strSql += sqlFilter;
			jdbcTemplate.query(strSql, new RowCallbackHandler(){
				public void processRow(ResultSet result) throws SQLException {
					JSONObject sys = new JSONObject();
					sys.put("id", result.getInt("id"));
					sys.put("name", result.getString("name"));
					sys.put("areaId", result.getInt("areaId"));
					syss.add(sys);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return syss;
	}

}

