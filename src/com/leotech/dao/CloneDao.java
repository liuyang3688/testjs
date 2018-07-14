package com.leotech.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.alibaba.fastjson.JSONObject;
import com.leotech.util.SpringContextBase;

public class CloneDao {
	private static CloneDao _dao = null;
	public JdbcTemplate jdbcTemplate;
	private CloneDao()
	{
		if (null == jdbcTemplate) {
			jdbcTemplate = (JdbcTemplate) SpringContextBase.getSpringContext().getBean("jdbcTemplate");
		}
	}
	public static synchronized CloneDao instance(){
		if(null == _dao){
			_dao = new CloneDao();
		}
		return _dao;
	}
	public List<JSONObject> getAllClone()
	{
		List<JSONObject> list = new ArrayList<JSONObject>();
		try {
			//String sqlFilter = " where isDirty=1 and isShow=1";
			String sqlFilter = " where isShow=1";
			String strSql = "select *  from clone";
			strSql += sqlFilter;
			list = jdbcTemplate.query(strSql, new RowMapper<JSONObject>() {
				public JSONObject mapRow(ResultSet result, int index)
						throws SQLException {
					JSONObject clone = new JSONObject();
					clone.put("uuid", result.getInt("uuid"));
					clone.put("name", result.getString("name"));
					clone.put("isShow", result.getInt("isShow"));
					clone.put("copyfrom", result.getString("copyfrom"));
					clone.put("x", result.getDouble("pos_x"));
					clone.put("y", result.getDouble("pos_y"));
					clone.put("z", result.getDouble("pos_z"));
					clone.put("rot_x", result.getDouble("rot_x"));
					clone.put("rot_y", result.getDouble("rot_y"));
					clone.put("rot_z", result.getDouble("rot_z"));
					clone.put("scl_x", result.getDouble("scl_x"));
					clone.put("scl_y", result.getDouble("scl_y"));
					clone.put("scl_z", result.getDouble("scl_z"));
					clone.put("parent", result.getString("parent"));
					return clone;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public Boolean updateIsDirty(int uuid, Boolean isDirty) {
		int rows = this.jdbcTemplate.update("update clone set isDirty=? where uuid=?", isDirty, uuid);
		return rows>0 ? true : false;
	}
	public Boolean updateIsDirty_All(Boolean isDirty) {
		int rows = this.jdbcTemplate.update("update clone set isDirty=? where 1=1", isDirty);
		return rows>0 ? true : false;
	}
}

