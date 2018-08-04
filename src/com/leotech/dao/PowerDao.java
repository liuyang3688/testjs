package com.leotech.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONArray;
import com.leotech.util.SpringContextBase;

public class PowerDao {
	private static PowerDao _dao = null;
	public JdbcTemplate jdbcTemplate;
	private PowerDao()
	{
		if (null == jdbcTemplate) {
			jdbcTemplate = (JdbcTemplate) SpringContextBase.getSpringContext().getBean("jdbcTemplate");
		}
	}
	public static synchronized PowerDao instance(){
		if(null == _dao){
			_dao = new PowerDao();
		}
		return _dao;
	}
	public JSONArray getAllPower()
	{
		final JSONArray powers = new JSONArray();
		try {
			String sqlFilter = " where 1=1";
			String strSql = "select * from power";
			strSql += sqlFilter;
			jdbcTemplate.query(strSql, new RowCallbackHandler(){
				public void processRow(ResultSet result) throws SQLException {
					JSONObject power = new JSONObject();
					power.put("id", result.getInt("id"));
					power.put("name", result.getString("name"));
					power.put("from", result.getString("from"));
					power.put("to", result.getString("to"));
					powers.add(power);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return powers;
	}

}

