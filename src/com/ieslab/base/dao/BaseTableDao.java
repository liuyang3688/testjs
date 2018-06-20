package com.ieslab.base.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.alibaba.fastjson.JSONObject;
import com.ieslab.util.SpringContextBase;

public class BaseTableDao {
	
	public JdbcTemplate jdbcTemplate;
	public BaseTableDao() {
		if (jdbcTemplate == null) {
			jdbcTemplate = (JdbcTemplate) SpringContextBase.getSpringContext()
					.getBean("jdbcTemplate");
		}
	}
	private static BaseTableDao dao = null;
	public static synchronized BaseTableDao instance(){
		if(dao == null){
			dao = new BaseTableDao();
		}
		return dao;
	}
	
	/**
	 * 根据部件类型获取对应的表名
	 * @param bjType
	 * @return
	 */
	public String getTableNameByBjType(String bjType){
		if(bjType == null || bjType.length() == 0){
			return null;
		}
		List<JSONObject> list = new ArrayList<JSONObject>();
		
		String sql = "select t.biaoming from BUJIANLEIXING t where t.id = " + bjType;
		
		list = jdbcTemplate.query(sql, new RowMapper<JSONObject>() {
			public JSONObject mapRow(ResultSet result, int index)
					throws SQLException {
				
				JSONObject obj = new JSONObject();
				obj.put("biaoming", result.getString("biaoming"));
				
				return obj;
			}
		});
		if(list.size() == 0){
			return null;
		}else{
			return list.get(0).getString("biaoming");
		}
	}
	
	/**
	 * 获取某个表的所有数据
	 * @param tableName
	 * @return
	 */
	public List<JSONObject> getTableInfo(String tableName) {
		String sql = "select id, mingzi from " + tableName; 
		List<JSONObject> list = new ArrayList<JSONObject>();
		list = jdbcTemplate.query(sql, new RowMapper<JSONObject>() {
			public JSONObject mapRow(ResultSet result, int index)
					throws SQLException {
				JSONObject obj = new JSONObject();
				obj.put("id", result.getString("id"));
				obj.put("mingzi", result.getString("mingzi"));
				return obj;
			}
		});
		return list;
	}
	
	/**
	 * 获取部件类型表信息
	 * @return
	 */
	public List<JSONObject> getBjlxTable(){
		List<JSONObject> list = new ArrayList<JSONObject>();
		
		String sql = "select t.id, t.mingzi, t.biaoming  from BUJIANLEIXING t ";
		
		list = jdbcTemplate.query(sql, new RowMapper<JSONObject>() {
			public JSONObject mapRow(ResultSet result, int index)
					throws SQLException {
				
				JSONObject obj = new JSONObject();
				obj.put("id", result.getString("id"));
				obj.put("biaoming", result.getString("biaoming"));
				obj.put("mingzi", result.getString("mingzi"));
				
				return obj;
			}
		});
		return list;
	}
	
	/**
	 * 通过场站ID和表名获取这个表中的字段信息
	 * @param tableName
	 * @param czid
	 * @return
	 */
	public List<JSONObject> getTableInfoByCzid(String tableName, String czid) {
		String sql = "select id, mingzi from " + tableName + " where changzhanid = " + czid; 
		List<JSONObject> list = new ArrayList<JSONObject>();
		list = jdbcTemplate.query(sql, new RowMapper<JSONObject>() {
			public JSONObject mapRow(ResultSet result, int index)
					throws SQLException {
				JSONObject obj = new JSONObject();
				obj.put("id", result.getString("id"));
				obj.put("mingzi", result.getString("mingzi"));
				return obj;
			}
		});
		return list;
	}
	
	public List<JSONObject> getAreaTypeInfo(){
		
		List<JSONObject> list = new ArrayList<JSONObject>();
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT dwt.changzhanid, dwt.fieldid, dwt.name, ");
		sql.append(" 	dwbj.componenttype,  dwbj.componentid ");
		sql.append(" FROM dwtopo dwt, dwtopobujian dwbj ");
		sql.append(" WHERE dwt.changzhanid = dwbj.changzhanid ");
		sql.append(" 	and dwt.fieldid = dwbj.fieldid ");
		sql.append(" 	and dwbj.componenttype <> 0 ");
		sql.append(" 	and dwbj.componentid <> 0 ");
		sql.append(" 	order by dwt.changzhanid, dwt.fieldid, dwbj.componenttype, dwbj.componentid ");
		
		list = jdbcTemplate.query(sql.toString(), new RowMapper<JSONObject>() {
			public JSONObject mapRow(ResultSet result, int index)
					throws SQLException {
				JSONObject obj = new JSONObject();
				obj.put("changzhanid", result.getString("changzhanid"));
				obj.put("fieldid", result.getString("fieldid"));
				obj.put("name", result.getString("name"));
				obj.put("componenttype", result.getString("componenttype"));
				obj.put("componentid", result.getString("componentid"));
				return obj;
			}
		});
		
		return list;
	}
}
