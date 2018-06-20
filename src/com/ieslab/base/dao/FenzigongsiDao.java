package com.ieslab.base.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.alibaba.fastjson.JSONObject;
import com.ieslab.util.SpringContextBase;
public class FenzigongsiDao {
	
	private static FenzigongsiDao dao = null;
	@Autowired
	public JdbcTemplate jdbcTemplate;
	public FenzigongsiDao() {
		if (jdbcTemplate == null) {
			jdbcTemplate = (JdbcTemplate) SpringContextBase.getSpringContext()
					.getBean("jdbcTemplate");
		}
	}
	
	public static synchronized FenzigongsiDao instance(){
		if(dao == null){
			dao = new FenzigongsiDao();
		}
		return dao;
	}
	/**
	 * 缓存调用该方法,查询全部的分子公司
	 * @param JiTuanID
	 * @param type
	 * @return
	 */
	public List<JSONObject> findAllfenCompany() {
		List<JSONObject> res = new ArrayList<JSONObject>();
		try {
			String sqlFilter = "";
			String strSql = "select ID, MingZi, CLFJCaps, ZLFJCaps, CLGFCaps, ZLGFCaps, SCType," +
					" clfjnums, zlfjnums,  Jigouid, JGType, Address, Latitude, Longtitude from powercorp";
			strSql += sqlFilter;
			res = jdbcTemplate.query(strSql, new RowMapper<JSONObject>() {
				public JSONObject mapRow(ResultSet result, int index)
						throws SQLException {
					JSONObject obj = new JSONObject();
					obj.put("id", result.getString("ID"));
					obj.put("mingzi" , result.getString("MingZi"));
					obj.put("CLFJCaps" , result.getInt("CLFJCaps"));
					obj.put("ZLFJCaps" ,result.getInt("ZLFJCaps"));
					obj.put("CLGFCaps" ,result.getInt("CLGFCaps"));
					obj.put("ZLGFCaps" ,result.getInt("ZLGFCaps"));
					obj.put("jigouId" , result.getString("Jigouid"));
					obj.put("jgType" , result.getInt("JGType"));
					obj.put("scType" , result.getInt("SCType"));
					obj.put("latitude" , result.getDouble("Latitude"));
					obj.put("longtitude" , result.getDouble("longtitude"));
					obj.put("address" , result.getString("Address"));
					obj.put("clfjnums" , result.getString("clfjnums"));
					obj.put("zlfjnums" , result.getString("zlfjnums"));
					obj.put("zongZjrl", result.getInt("CLFJCaps")+result.getInt("ZLFJCaps")+result.getInt("ZLGFCaps")+result.getInt("CLGFCaps"));
					return obj;
				}
			});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Collections.sort(res, new Comparator<JSONObject>(){
			
			public int compare(JSONObject a, JSONObject b){
				int valA_1 = a.getInteger("zongZjrl");
				int valB_1 = b.getInteger("zongZjrl");
			    return (valB_1 < valA_1) ? -1 : ((valA_1 == valB_1) ? 0 : 1);
			}
		});
		
		
		return res;
	}
}
