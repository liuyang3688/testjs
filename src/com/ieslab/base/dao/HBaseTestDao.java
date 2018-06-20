package com.ieslab.base.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.alibaba.fastjson.JSONObject;
import com.ieslab.util.DateUtil;
import com.ieslab.util.SpringContextBase;

public class HBaseTestDao {
	
	public JdbcTemplate jdbcTemplate;
	public HBaseTestDao() {
		if (jdbcTemplate == null) {
			jdbcTemplate = (JdbcTemplate) SpringContextBase.getSpringContext()
					.getBean("hbasejdbc");
		}
	}
	
	public void test(){
		String sql = "select count(*) from ls_dianwang";
		int dd = this.jdbcTemplate.queryForInt(sql);
	}
	
	/**
	 * 
	 * @param bjcsids c1,c2,c3
	 * @param bjid 1
	 * @param starttime yyyyMMddHHmmss 201706020909
	 * @param endtime yyyyMMddHHmmss 201706020909
	 * @return
	 */
	public List<JSONObject> getKaiGuanByStation(final String bjcsids, final String bjid, String starttime, String endtime) {
		if(bjcsids == null || bjcsids.length() == 0){
			return null;
		}
		if(bjid == null || bjid.length() == 0){
			return null;
		}
		if(starttime == null || starttime.length() == 0){
			return null;
		}
		if(endtime == null || endtime.length() == 0){
			return null;
		}
		List<JSONObject> res = new ArrayList<JSONObject>();
		
		String sql = "select rowkey, "+bjcsids+" from ls_dianwang where rowkey > '"+bjid+"-"+starttime+"' and rowkey <  '"+bjid+"-"+endtime+"'";
		try {
			
			res = jdbcTemplate.query(sql, new RowMapper<JSONObject>() {
				public JSONObject mapRow(ResultSet result, int index)
						throws SQLException {
					JSONObject obj = new JSONObject();

					if(bjcsids.length() > 0){
						String csArr[] = bjcsids.split(",");
						for(String csid: csArr){
							obj.put(csid, result.getFloat(csid));
						}
					}
					String timeStr =  result.getString("rowkey");
					if(timeStr != null && timeStr.length() > 0 && timeStr.indexOf("-") >= 0){
						obj.put("time", timeStr.split("-")[1]);
					}
					obj.put("bjid", bjid);
					return obj;
				}
			});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return res;
	}
	
	
	public void testBj(){
		String yfgl_zl = "211", yfgl_cl = "212";
		String bjid = "1";
		String starttime = DateUtil.formatdate(new Date(), "yyyyMMdd") + "000000";
		String endtime = DateUtil.formatdate(new Date(), "yyyyMMddHHmmss");
		String bjcsids = "c211,c212";
		
		List<JSONObject> res = getKaiGuanByStation(bjcsids, bjid, starttime, endtime);
		
		/*for(JSONObject obj: res){
			
		}*/
		
	}
}
