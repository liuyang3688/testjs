package com.leotech.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONArray;
import com.leotech.util.SpringContextBase;

public class PowerDao {
	private static PowerDao _dao = null;
	private static Map<String, PowerDao.PowerInfo > powerInfoMap = null;
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
	public void fetchPowerCount(){
		try {
			if (powerInfoMap == null) {
				powerInfoMap = new HashMap<String, PowerDao.PowerInfo>();
			} else {
				powerInfoMap.clear();
			}
			powerInfoMap.clear();
			// From统计
			String sqlFilter = " where power.fromDevice = device.code and device.typeid = device_type.uuid and power.isShow=1";
			String strSql = "select power.name, power.from, power.radius, device_type.ethRowCount, device_type.ethColCount from power, device, device_type";
			strSql += sqlFilter;
			jdbcTemplate.query(strSql, new RowCallbackHandler(){
				public void processRow(ResultSet result) throws SQLException {
					PowerDao.PowerInfo info = new PowerDao.PowerInfo();
					info.name = result.getString("power.name");
					info.from = result.getString("power.from");
					info.radius = result.getDouble("power.radius");
					info.fmRowCount = result.getInt("device_type.ethRowCount");
					info.fmColCount = result.getInt("device_type.ethColCount");
					powerInfoMap.put(info.name, info);
				}
			});
			//To统计
			sqlFilter = " where power.toDevice = device.code and device.typeid = device_type.uuid and power.isShow=1";
			strSql = "select power.name, power.to, device_type.ethRowCount, device_type.ethColCount from power, device, device_type";
			strSql += sqlFilter;
			jdbcTemplate.query(strSql, new RowCallbackHandler(){
				public void processRow(ResultSet result) throws SQLException {
					String name = result.getString("power.name");
					String to = result.getString("power.to");
					int toRowCount = result.getInt("device_type.ethRowCount");
					int toColCount = result.getInt("device_type.ethColCount");
					if (powerInfoMap.containsKey(name)) {
						PowerDao.PowerInfo info = powerInfoMap.get(name);
						info.to = to;
						info.toRowCount = toRowCount;
						info.toColCount = toColCount;
					}
				}
			});


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public JSONArray getAllPower()
	{
		fetchPowerCount();
		final JSONArray powers = new JSONArray();
		try {
			for (PowerDao.PowerInfo info : powerInfoMap.values()){
				JSONObject power = new JSONObject();
				power.put("name", info.name);
				power.put("from", info.from);
				power.put("to", info.to);
				power.put("radius", info.radius);
				power.put("fmRowCount", info.fmRowCount);
				power.put("fmColCount", info.fmColCount);
				power.put("toRowCount", info.toRowCount);
				power.put("toColCount", info.toColCount);
				powers.add(power);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return powers;
	}
	class PowerInfo {
		public String   name;
		public String   from;
		public String   to;
		public int      fmRowCount;
		public int      fmColCount;
		public int      toRowCount;
		public int      toColCount;
		public double   radius;
		public PowerInfo(){
			this.fmRowCount = 42;
			this.fmColCount = 20;
			this.toRowCount = 1;
			this.toColCount = 10;
			this.radius = 1.0;
		}
	}
}

