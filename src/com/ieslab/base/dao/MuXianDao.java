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
 * 母线
 * @author Li Hao
 *
 */
public class MuXianDao {
	
	private static MuXianDao dao = null;
	@Autowired
	public JdbcTemplate jdbcTemplate;
	public MuXianDao() {
		if (jdbcTemplate == null) {
			jdbcTemplate = (JdbcTemplate) SpringContextBase.getSpringContext()
					.getBean("jdbcTemplate");
		}
	}
	
	public static synchronized MuXianDao instance(){
		if(dao == null){
			dao = new MuXianDao();
		}
		return dao;
	}
	/**
	 * 获取母线通过厂站ID
	 * @param czid
	 * @return
	 */
	public List<JSONObject> getMuXianByStation(String czid) {
		
		List<JSONObject> res = new ArrayList<JSONObject>();
		String strSql = " select id, mingzi, bianhao from ies_ms.MUXIAN t ";
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
	
}
