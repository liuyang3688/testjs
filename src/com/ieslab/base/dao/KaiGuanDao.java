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
/**
 * 开关
 * @author Li Hao
 *
 */
public class KaiGuanDao {
	
	private static KaiGuanDao dao = null;
	@Autowired
	public JdbcTemplate jdbcTemplate;
	public KaiGuanDao() {
		if (jdbcTemplate == null) {
			jdbcTemplate = (JdbcTemplate) SpringContextBase.getSpringContext()
					.getBean("jdbcTemplate");
		}
	}
	
	public static synchronized KaiGuanDao instance(){
		if(dao == null){
			dao = new KaiGuanDao();
		}
		return dao;
	}
	/**
	 * 获取开关通过厂站ID
	 * @param czid
	 * @return
	 */
	public List<JSONObject> getKaiGuanByStation(String czid) {
		
		List<JSONObject> res = new ArrayList<JSONObject>();
		String strSql = " select id, mingzi, bianhao from ies_ms.KAIGUAN t ";
		if(!"0".equals(czid)){
			String sqlFilter = " where t.changzhanid="+czid;
			strSql += sqlFilter;
		}
		try {
			
			res = jdbcTemplate.query(strSql, new RowMapper<JSONObject>() {
				public JSONObject mapRow(ResultSet result, int index)
						throws SQLException {
					JSONObject obj = new JSONObject();
					
					obj.put("id", result.getInt("id") + "");
					obj.put("mingzi", result.getString("mingzi"));
					obj.put("bianhao", result.getString("bianhao"));
					
					return obj;
				}
			});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return res;
	}
	
	
	/**
	 * 根据开关id 查询开关类型
	 * @param czid
	 * @return
	 */
	public List<JSONObject> getKaiGuanType(String kgid) {
		List<JSONObject> res = new ArrayList<JSONObject>();
		String strSql = " SELECT t.id, t.subtype FROM ies_ms.KAIGUAN t where t.id in("+ kgid + ")" ;
		try {
			res = jdbcTemplate.query(strSql, new RowMapper<JSONObject>() {
				public JSONObject mapRow(ResultSet result, int index)
						throws SQLException {
					JSONObject obj = new JSONObject();
//					obj.put("id", result.getString("id"));
					obj.put("subtype", result.getString("subtype"));
					return obj;
				}
			});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return res;
	}
	
	
	//查询所有开关的名字 id
	public List<JSONObject> getAllkaiguan() {
		
		List<JSONObject> res = new ArrayList<JSONObject>();
		String strSql = " SELECT ID, mingzi, bianhao, changzhanid FROM kaiguan ";
		try {
			res = jdbcTemplate.query(strSql, new RowMapper<JSONObject>() {
				public JSONObject mapRow(ResultSet result, int index)
						throws SQLException {
					JSONObject obj = new JSONObject();
					obj.put("id", result.getInt("id") + "");
					obj.put("mingzi", result.getString("mingzi"));
					obj.put("bianhao", result.getString("bianhao"));
					obj.put("changzhanid", result.getString("changzhanid"));
					return obj;
				}
			});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return res;
	}
}
