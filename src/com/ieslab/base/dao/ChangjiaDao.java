package com.ieslab.base.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.alibaba.fastjson.JSONObject;
import com.ieslab.util.SpringContextBase;

public class ChangjiaDao {
	
	private static ChangjiaDao dao = null;
	@Autowired
	public JdbcTemplate jdbcTemplate;
	public ChangjiaDao() {
		if (jdbcTemplate == null) {
			jdbcTemplate = (JdbcTemplate) SpringContextBase.getSpringContext()
					.getBean("jdbcTemplate");
		}
	}
	
	public static synchronized ChangjiaDao instance(){
		if(dao == null){
			dao = new ChangjiaDao();
		}
		return dao;
	}
	
	/**
	 * 获取全部厂家信息
	 * @return
	 */
	public List<JSONObject> findAll() {
		List<JSONObject> list = new ArrayList<JSONObject>();
		try {
			String strSql = "select * from ies_ms.changjia";
			
			list = jdbcTemplate.query(strSql, new RowMapper<JSONObject>() {
				public JSONObject mapRow(ResultSet result, int index)
						throws SQLException {
					JSONObject cj = new JSONObject();
					cj.put("id", result.getString("id"));
					cj.put("bianhao", result.getString("bianhao"));
					cj.put("mingzi", result.getString("mingzi"));
					return cj;
				}
			});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}
	
}
