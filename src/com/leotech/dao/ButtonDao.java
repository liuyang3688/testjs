package com.leotech.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.leotech.util.SpringContextBase;

public class ButtonDao {
	private static ButtonDao _dao = null;
	public JdbcTemplate jdbcTemplate;
	private ButtonDao()
	{
		if (null == jdbcTemplate) {
			jdbcTemplate = (JdbcTemplate) SpringContextBase.getSpringContext().getBean("jdbcTemplate");
		}
	}
	public static synchronized ButtonDao instance(){
		if(null == _dao){
			_dao = new ButtonDao();
		}
		return _dao;
	}
	
	public JSONArray getAllBtn()
	{
		final JSONArray btns = new JSONArray();
		try {
			String sqlFilter = " where isShow=1";
			String strSql = "select *  from button";
			strSql += sqlFilter;
			jdbcTemplate.query(strSql, new RowCallbackHandler(){
				public void processRow(ResultSet result) throws SQLException {
					JSONObject btn = new JSONObject();
					btn.put("uuid", result.getInt("uuid"));
					btn.put("name", result.getString("name"));
					btn.put("title", result.getString("title"));
					btn.put("img", result.getString("img"));
					btn.put("callback", result.getString("callback"));
					
					btns.add(btn);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return btns;
	}
}
