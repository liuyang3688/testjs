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
 * 型号表
 * @author Li Hao
 *
 */
public class XinghaoDao {
	
	private static XinghaoDao dao = null;
	@Autowired
	public JdbcTemplate jdbcTemplate;
	public XinghaoDao() {
		if (jdbcTemplate == null) {
			jdbcTemplate = (JdbcTemplate) SpringContextBase.getSpringContext()
					.getBean("jdbcTemplate");
		}
	}
	
	public static synchronized XinghaoDao instance(){
		if(dao == null){
			dao = new XinghaoDao();
		}
		return dao;
	}
	
	/**
	 * 获取全部型号信息
	 * @return
	 */
	public List<JSONObject> findAllXingHao() {
		List<JSONObject> list = new ArrayList<JSONObject>();
		try {
			String strSql = " select t.id, t.bianhao, t.mingzi, t.changjiaid from XINGHAO t ";
			
			list = jdbcTemplate.query(strSql, new RowMapper<JSONObject>() {
				public JSONObject mapRow(ResultSet result, int index)
						throws SQLException {
					JSONObject fj = new JSONObject();
					fj.put("id", result.getString("id"));
					fj.put("bianhao", result.getString("bianhao"));
					fj.put("changjiaid", result.getString("changjiaid"));
					fj.put("mingzi", result.getString("mingzi"));
					
					return fj;
				}
			});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}
	
	public List<JSONObject> xinghaoBychangzhan(){
		List<JSONObject> list = new ArrayList<JSONObject>();
		String sql = "SELECT DISTINCT(fj.changzhanid), fj.fjtypeid,xh.id, xh.mingzi FROM fengji fj LEFT JOIN xinghao xh ON fj.fjtypeid = xh.id";
		list = jdbcTemplate.query(sql, new RowMapper<JSONObject>(){
			public JSONObject mapRow(ResultSet result , int index){
				JSONObject obj = new JSONObject();
				try {
					obj.put("changzhanid", result.getString("changzhanid"));
					obj.put("fjtypeid", result.getString("fjtypeid"));
					obj.put("id", result.getString("id"));
					obj.put("mingzi", result.getString("mingzi"));
				} catch (SQLException e) {
					e.getMessage();
				}
				return obj;
			}

		});
		return list;
	}
	
}
