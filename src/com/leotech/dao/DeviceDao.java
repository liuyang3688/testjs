package com.leotech.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONArray;
import com.leotech.util.SpringContextBase;

public class DeviceDao {
	private static DeviceDao _dao = null;
	public JdbcTemplate jdbcTemplate;
	private DeviceDao()
	{
		if (null == jdbcTemplate) {
			jdbcTemplate = (JdbcTemplate) SpringContextBase.getSpringContext().getBean("jdbcTemplate");
		}
	}
	public static synchronized DeviceDao instance(){
		if(null == _dao){
			_dao = new DeviceDao();
		}
		return _dao;
	}
	public JSONArray getAllDevice()
	{
		final JSONArray devices = new JSONArray();
		try {
			String sqlFilter = " where device.typeId=device_type.uuid";
			String strSql = "select id,device.code,device.name,device.memo,device_type.tpl  from device, device_type";
			strSql += sqlFilter;
			jdbcTemplate.query(strSql, new RowCallbackHandler(){
				public void processRow(ResultSet result) throws SQLException {
					JSONObject device = new JSONObject();
					device.put("uuid", result.getInt("id"));
					device.put("code", result.getString("code"));
					device.put("devName", result.getString("name"));
					device.put("memo", result.getString("memo"));
					device.put("copyfrom", result.getString("tpl"));
					
					devices.add(device);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return devices;
	}

}

