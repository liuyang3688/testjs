package com.ieslab.base.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.alibaba.fastjson.JSONObject;
import com.ieslab.util.SpringContextBase;

public class YuanShiCodeDao {
	
	private static YuanShiCodeDao dao = null;
	@Autowired
	public JdbcTemplate jdbcTemplate;
	public YuanShiCodeDao() {
		if (jdbcTemplate == null) {
			jdbcTemplate = (JdbcTemplate) SpringContextBase.getSpringContext()
					.getBean("jdbcTemplate");
		}
	}
	
	public static synchronized YuanShiCodeDao instance(){
		if(dao == null){
			dao = new YuanShiCodeDao();
		}
		return dao;
	}
	
	/**
	 * 获取全部原始状态码
	 * @return
	 */
	public Map<String, String> findAll() {
		List<JSONObject> list = new ArrayList<JSONObject>();
		try {
			String strSql = " select t.xinghaoid, t.fjstaterecv, t.showname from FJSTATESHOW t ";
			
			list = jdbcTemplate.query(strSql, new RowMapper<JSONObject>() {
				public JSONObject mapRow(ResultSet result, int index)
						throws SQLException {
					JSONObject cj = new JSONObject();
					cj.put("xinghaoid", result.getString("xinghaoid"));
					cj.put("fjstaterecv", result.getString("fjstaterecv"));
					cj.put("showname", result.getString("showname"));
					return cj;
				}
			});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		Map<String, String> map = new HashMap<String, String>();
		if(list != null && list.size() > 0){
			for(JSONObject obj: list){
				String xinghao = obj.getString("xinghaoid");
				String code = obj.getString("fjstaterecv");
				String name = obj.getString("showname");
				
				String codeArr[] = code.split(",");
				for(String codeA: codeArr){
					map.put(xinghao + "_" + codeA, name);
				}
			}
		}
//		Map<String, String> map = new HashMap<String, String>();
//		if(list != null && list.size() > 0){
//			for(JSONObject obj: list){
//				String xinghao = obj.getString("xinghaoid");
//				String code = obj.getString("fjstaterecv");
//				String name = obj.getString("showname");
//				
//				String codeArr[] = code.split(",");
//				for(String codeA: codeArr){
//					String codeNum[] = codeA.split("-");
//					if(codeNum.length == 1){
//						map.put(xinghao + "_" + codeNum[0], name);
//					}else if(codeNum.length == 2){
//						int startNum = Integer.parseInt(codeNum[0]);
//						int endNum = Integer.parseInt(codeNum[1]);
//						for(int i = startNum; i <= endNum; i++){
//							map.put(xinghao + "_" + i, name);
//						}
//					}
//				}
//			}
		return map;
	}
	
}
