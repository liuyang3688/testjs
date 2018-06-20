/********************************************************************
 *	版权所有 (C) 2009-2014 积成电子股份有限公司
 *	保留所有版权
 *	
 *	作者：	侯培彬
 *	日期：	2014-7-15
 *	摘要：	历史事项查询
 *  功能：      非基线版本历史事项查询、此主要用在重庆、深圳等地区 
 *          
 *
 *********************************************************************/
package com.ieslab.base.data.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.alibaba.fastjson.JSONObject;
import com.ieslab.util.DateUtil;
import com.ieslab.util.SpringContextBase;

public class HisDataDao{
	
	private static HisDataDao dao = null;
	public JdbcTemplate jdbcTemplate;
	public HisDataDao() {
		if (jdbcTemplate == null) {
			jdbcTemplate = (JdbcTemplate) SpringContextBase.getSpringContext()
					.getBean("ieslsdb");
		}
	}
	
	public static HisDataDao getInstance(){
		if(dao == null){
			dao = new HisDataDao();
		}
		return dao;
	}
	/**
	 * 获取某个场站的预测功率
	 * @param czid
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	public List<JSONObject> getYucePowerByCzid(String czid, String yuceType, String starttime, String endtime){
		
		List<JSONObject> list = new ArrayList<JSONObject>();
		
		StringBuffer sql = new StringBuffer();
		sql.append(" select * ");
		sql.append(" from YUCEPOWERTABLE t ");
		sql.append(" where t.yucetype = 1 ");
		sql.append(" 	and t.changzhanid = 1 ");
		sql.append(" 	and t.riqi <= to_date('"+endtime+"', 'yyyy-mm-dd:hh24-mi-ss')");
		sql.append(" 	and t.riqi >= to_date('"+starttime+"', 'yyyy-mm-dd:hh24-mi-ss')");
		
		list = jdbcTemplate.query(sql.toString(), new RowMapper<JSONObject>() {
			public JSONObject mapRow(ResultSet result, int index)
					throws SQLException {
				JSONObject obj = new JSONObject();
				obj.put("time", DateUtil.parseTimestamp(result.getTimestamp("riqi"), "yyyyMMddHHmmss"));
				obj.put("yucepower", result.getFloat("yucepower"));
				return obj;
			}
		});
		return list;
	}
	/**
	 * 查询实时监视下的“场站监视”“风机监视”的饼图统计信息
	 * @param time
	 * @param id
	 * @param tableName
	 * @return
	 */
	public List<JSONObject> queryShiShiJianShiTJData(final String cols, String time, String id, String tableName){
		List<JSONObject> list = new ArrayList<JSONObject>();
		StringBuffer sb = new StringBuffer();
		sb.append(" select  1 ");
		if(cols != null && cols.length() > 0){
			String colArr[] = cols.split(",");
			for(String col: colArr){
				sb.append(" , sum(" + col + ") " + col);
			}
		}
		sb.append(" from "+tableName+" t ");
		sb.append(" where t.riqi = to_date('"+time+"', 'yyyy-mm-dd:hh24-mi-ss') ");
		sb.append(" and t.id in (" + id + ")");
		
		list = jdbcTemplate.query(sb.toString(), new RowMapper<JSONObject>() {
			public JSONObject mapRow(ResultSet result, int index)
					throws SQLException {
				JSONObject obj = new JSONObject();
				
				if(cols != null && cols.length() > 0){
					String colArr[] = cols.split(",");
					for(String col: colArr){
						obj.put(col, result.getString(col));
					}
				}
				return obj;
			}
		});
		return list;
	}
}
