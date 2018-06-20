package com.ieslab.base.hbase.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.alibaba.fastjson.JSONObject;
import com.ieslab.util.SpringContextBase;

public class DianWangDao {
	
	
	private static DianWangDao dao = null;
	public static synchronized DianWangDao instance(){
		if(dao == null){
			dao = new DianWangDao();
		}
		return dao;
	}
	
	public JdbcTemplate jdbcTemplate;
	public DianWangDao() {
		if (jdbcTemplate == null) {
			jdbcTemplate = (JdbcTemplate) SpringContextBase.getSpringContext()
					.getBean("hbasejdbc");
		}
	}
	/**
	 * 获取电网数据
	 * @param bjcsids 参数信息，格式c201,c202
	 * @param bjid 部件ID
	 * @param starttime 格式 yyyyMMddHHmmss
	 * @param endtime 格式 yyyyMMddHHmmss
	 * @return
	 */
	public List<JSONObject> getDianWangData(final String bjcsids, final String bjid, String starttime, String endtime) {
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
		
		String sql = "select rowkey, "+bjcsids+" from ls_dianwang where rowkey > '"+bjid+"-"+starttime+"' and rowkey <  '"+bjid+"-"+endtime+"' and rowkey like '00001-%00' and c220 <= 49 order by rowkey ";
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
	
/*	public void testBj(){
		String yfgl_zl = "211", yfgl_cl = "212";
		String bjid = "1";
		String starttime = DateUtil.formatdate(new Date(), "yyyyMMdd") + "000000";
		String endtime = DateUtil.formatdate(new Date(), "yyyyMMddHHmmss");
		String bjcsids = "c211,c212";
		
		List<JSONObject> res = getKaiGuanByStation(bjcsids, bjid, starttime, endtime);
		
		for(JSONObject obj: res){
			
		}
		
	}*/
	
}
