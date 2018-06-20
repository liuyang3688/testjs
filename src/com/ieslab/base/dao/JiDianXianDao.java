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

public class JiDianXianDao {
	
	private static JiDianXianDao dao = null;
	@Autowired
	public JdbcTemplate jdbcTemplate;
	public JiDianXianDao() {
		if (jdbcTemplate == null) {
			jdbcTemplate = (JdbcTemplate) SpringContextBase.getSpringContext()
					.getBean("jdbcTemplate");
		}
	}
	
	public static synchronized JiDianXianDao instance(){
		if(dao == null){
			dao = new JiDianXianDao();
		}
		return dao;
	}
	
	/**
	 * 获取全部线路信息
	 * @return
	 */
	public List<JSONObject> findAll() {
		List<JSONObject> list = new ArrayList<JSONObject>();
		try {
			String strSql = " select t.id, t.changzhanid, t.xiangmuid, t.bianhao, t.mingzi, t.jdxkgid from JIDIANXIAN t  ";
			
			list = jdbcTemplate.query(strSql, new RowMapper<JSONObject>() {
				public JSONObject mapRow(ResultSet result, int index)
						throws SQLException {
					Map<String, JSONObject> czMap = BaseModelCache.getInstance().getCzMap();

					JSONObject fj = new JSONObject();
					fj.put("id", result.getString("id"));
					fj.put("bianhao", result.getString("bianhao"));
					fj.put("changzhanid", result.getString("changzhanid"));
					fj.put("mingzi", result.getString("mingzi"));
					fj.put("powercorpid", czMap.get(result.getString("changzhanid")).getString("powercorpid"));
					fj.put("xiangmuid", result.getString("xiangmuid"));
					fj.put("jdxkgid", result.getString("jdxkgid"));
					
					return fj;
				}
			});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}
	
}
