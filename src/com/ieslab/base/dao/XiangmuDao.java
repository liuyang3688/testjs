package com.ieslab.base.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.alibaba.fastjson.JSONObject;
import com.ieslab.base.service.BaseModelCache;
import com.ieslab.util.SpringContextBase;

public class XiangmuDao {
	
	private static XiangmuDao dao = null;
	@Autowired
	public JdbcTemplate jdbcTemplate;
	public XiangmuDao() {
		if (jdbcTemplate == null) {
			jdbcTemplate = (JdbcTemplate) SpringContextBase.getSpringContext()
					.getBean("jdbcTemplate");
		}
	}
	
	public static synchronized XiangmuDao instance(){
		if(dao == null){
			dao = new XiangmuDao();
		}
		return dao;
	}
	
	/**
	 * 获取全部项目信息
	 * @return
	 */
	public List<JSONObject> findAll() {
		List<JSONObject> list = new ArrayList<JSONObject>();
		try {
			String strSql = " select t.id, t.changzhanid, t.bianhao, t.mingzi, t.sctype from XIANGMU t  ";
			
			list = jdbcTemplate.query(strSql, new RowMapper<JSONObject>() {
				public JSONObject mapRow(ResultSet result, int index)
						throws SQLException {
					Map<String, JSONObject> czMap = BaseModelCache.getInstance().getCzMap();

					JSONObject fj = new JSONObject();
					fj.put("id", result.getString("id"));
					if(czMap.containsKey(result.getString("changzhanid"))){
						fj.put("powercorpid", czMap.get(result.getString("changzhanid")).getString("powercorpid"));
					}
					fj.put("bianhao", result.getString("bianhao"));
					fj.put("changzhanid", result.getString("changzhanid"));
					fj.put("mingzi", result.getString("mingzi"));
					fj.put("sctype", result.getString("sctype"));
					return fj;
				}
			});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}
	
	public String findXiangmuId(String jibie,String id) {
		String sql = "select id from ies_ms.xiangmu where changzhanid in";
		if(jibie.equals("2")){
			sql += "("+id+")";
		}else if(jibie.equals("1")){
			sql += "(select id from ies_ms.changzhan where powercorpid in("+id+"))";
		}
		List<Map<String, Object>> data =jdbcTemplate.queryForList(sql);
		String ids = "";
		if(data.size()!=0){
			for(int i = 0;i < data.size();i++){
				ids += data.get(i).get("id")+",";
			}
		}
		if(!ids.equals("")){
			ids = ids.substring(0, ids.length()-1);
		}
		return ids;
	}
}
