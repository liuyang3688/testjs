package com.ieslab.baojingzhongxin.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.alibaba.fastjson.JSONObject;
import com.ieslab.util.SpringContextBase;
/**
 * 事故追忆
 * @author Administrator
 *
 */
public class DaibangongzuoDao {
	
	public JdbcTemplate jdbcTemplate;
	public DaibangongzuoDao() {
		if (jdbcTemplate == null) {
			jdbcTemplate = (JdbcTemplate) SpringContextBase.getSpringContext()
					.getBean("jdbcTemplate");
		}
	}

	public List<JSONObject> getJXbuwei(){
		
		List<JSONObject> list = new ArrayList<JSONObject>();
		String sql = "select * from ies_ms.STATICLISTINFO t where listid=281 order by id";
		
		list = jdbcTemplate.query(sql, new RowMapper<JSONObject>() {
			public JSONObject mapRow(ResultSet result, int index)
					throws SQLException {
				
				JSONObject obj = new JSONObject();
				obj.put("id", result.getString("id"));
				obj.put("mingzi", result.getString("EXPLAIN"));
				return obj;
			}
		});
		return list;
	}
}
