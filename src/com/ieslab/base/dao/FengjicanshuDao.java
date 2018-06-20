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

public class FengjicanshuDao {
	
	private static FengjicanshuDao dao = null;
	@Autowired
	public JdbcTemplate jdbcTemplate;
	public FengjicanshuDao() {
		if (jdbcTemplate == null) {
			jdbcTemplate = (JdbcTemplate) SpringContextBase.getSpringContext()
					.getBean("jdbcTemplate");
		}
	}
	
	public static synchronized FengjicanshuDao instance(){
		if(dao == null){
			dao = new FengjicanshuDao();
		}
		return dao;
	}
	
	public List<JSONObject> findAllbjcs() {
		List<JSONObject> list = new ArrayList<JSONObject>();
		try {
			String strSql = "SELECT b.bjlxid, b.id, b.mingzi, b.dtype, b.rdtype, b.isvalid, b.sendweb, b.saveflag FROM bujiancanshu b";
			list = jdbcTemplate.query(strSql, new RowMapper<JSONObject>() {
				public JSONObject mapRow(ResultSet result, int index)
						throws SQLException {
					JSONObject obj = new JSONObject();
					obj.put("id", result.getString("id"));
					obj.put("mingzi", result.getString("mingzi"));
					obj.put("bjlxid", result.getString("bjlxid"));
					obj.put("dtype", result.getString("dtype"));
					
					obj.put("rdtype", result.getString("rdtype"));
					obj.put("isvalid", result.getString("isvalid"));
					obj.put("sendweb", result.getString("sendweb"));
					obj.put("saveflag" , result.getString("saveflag"));
					return obj;
				}
			});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}
	
}
