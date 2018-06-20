package com.ieslab.jingjiyunxing.dao;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.alibaba.fastjson.JSONObject;
import com.ieslab.base.dao.ZhibiaoDao;
import com.ieslab.base.service.BaseModelCache;
import com.ieslab.util.SpringContextBase;

public class ShengChanZhiBiaoDao {
	private static ShengChanZhiBiaoDao dao = null;
	@Autowired
	public JdbcTemplate jdbcTemplate;
	public ShengChanZhiBiaoDao() {
		if (jdbcTemplate == null) {
			jdbcTemplate = (JdbcTemplate) SpringContextBase.getSpringContext()
					.getBean("jdbcTemplate");
		}
	}
	
	public static synchronized ShengChanZhiBiaoDao instance(){
		if(dao == null){
			dao = new ShengChanZhiBiaoDao();
		}
		return dao;
	}
	
	/**
	 * 获取指标数据List
	 * @return
	 */
	public List<JSONObject> findData(final String table, String ids, final String zbid, String startTime, String endTime, String timeType, String week, final String jibie) {
		String strSql = "select ";
		String zb = "";
		final String[] zbids = zbid.split(",");
		if(!"".equals(zbid)){
			if(timeType.equals("5") || (!jibie.equals("4") && table.indexOf("FJTJDATA")!=-1)){
		 		for(int i = 0;i < zbids.length;i++){
		 			if("CLFJXMCaps".equals(zbids[i].split("_")[0])){
		 				continue;
		 			}
		 			if(zbids[i].split("_")[1].equals("0")){
		 				zb += "sum(x."+zbids[i].split("_")[0]+") as "+zbids[i].split("_")[0]+",";
		 			}
		 			if(zbids[i].split("_")[1].equals("1")){
		 				zb += "avg(x."+zbids[i].split("_")[0]+") as "+zbids[i].split("_")[0]+",";
		 			}
		 			if(zbids[i].split("_")[1].equals("2")){
		 				zb += "max(x."+zbids[i].split("_")[0]+") as "+zbids[i].split("_")[0]+",";
		 			}
		 			if(zbids[i].split("_")[1].equals("3")){
		 				zb += "min(x."+zbids[i].split("_")[0]+") as "+zbids[i].split("_")[0]+",";
		 			}
				}
		 	}else{
		 		for(int i = 0;i < zbids.length;i++){
		 			if("CLFJXMCaps".equals(zbids[i].split("_")[0])){
		 				continue;
		 			}
					zb += "x."+zbids[i].split("_")[0]+",";
				}
		 	}
		}
		String id = "id";
		if(!jibie.equals("4") && table.indexOf("FJTJDATA")!=-1){
			id = "xiangmuid";
		}
		strSql += "x."+id;
		if(Integer.parseInt(jibie)==2){
			strSql += ",c.CLFJXMCaps";
		}
		if(!"".equals(zb)){
			strSql += ","+zb.substring(0, zb.length() - 1);
		}
 		strSql += " from ies_ls."+table+" x";
 		if(Integer.parseInt(jibie)==2){
			strSql += ",ies_ms.changzhan c";
		}
 		strSql += " where ";
 		if(Integer.parseInt(jibie)==2){
			strSql += "x.id = c.id and ";
		}
 		strSql += " x.riqi >= to_date('"+startTime+"', 'yyyy-mm-dd:hh24-mi-ss') ";
		strSql += "and x.riqi <= to_date('"+endTime+"', 'yyyy-mm-dd:hh24-mi-ss') ";
		
		if(ids.indexOf(";") >= 0){
			strSql += "and ";
			String[] idarr = ids.split(";");
			strSql += "xiangmuid in ("+idarr[0]+") and ";
			strSql += "(select fjtypeid from ies_ms.fengji where id = x.id) in ("+idarr[2]+") and ";
			strSql += "(select fjchangjia from ies_ms.fengji where id = x.id) in ("+idarr[1]+") ";
		}else{
			strSql += "and x."+id+" in("+ids+") ";
		}
		if(!"".equals(week) && !"0".equals(week) && week!=null){
			strSql += "and x.weeknum = "+week;
		}
		if(timeType.equals("5") || (!jibie.equals("4") && table.indexOf("FJTJDATA")!=-1)){
			strSql += "group by x."+id;
		}
		strSql += " order by x."+id;
		
		final Map<String, com.alibaba.fastjson.JSONObject> xmMap = BaseModelCache.getInstance().getXmMap();
		final Map<String, com.alibaba.fastjson.JSONObject> czMap = BaseModelCache.getInstance().getCzMap();
		final Map<String, com.alibaba.fastjson.JSONObject> gsMap = BaseModelCache.getInstance().getGsMap();
		final Map<String, com.alibaba.fastjson.JSONObject> fjMap = BaseModelCache.getInstance().getFjMap();
		final Map<String, com.alibaba.fastjson.JSONObject> zbMap = BaseModelCache.getInstance().getZbMap();
		final String key = id;
		List<JSONObject> list = new ArrayList<JSONObject>();
		list = jdbcTemplate.query(strSql, new RowMapper<JSONObject>() {
			public JSONObject mapRow(ResultSet result, int index) throws SQLException {
				JSONObject zb = new JSONObject();
				zb.put(key, result.getString(key));
				int bjlx = 0;
				if(Integer.parseInt(jibie)==1){
					bjlx = 1;
					
					zb.put("gongsiname", gsMap.get(result.getString(key)).getString("mingzi"));
					zb.put("gsid", gsMap.get(result.getString(key)).getString("id"));
					
					zb.put("name", gsMap.get(result.getString(key)).getString("mingzi"));
				}
				if(Integer.parseInt(jibie)==2){
					bjlx = 5;
					
					zb.put("gongsiname", gsMap.get(czMap.get(result.getString(key)).getString("powercorpid")).getString("mingzi"));
					zb.put("gsid", gsMap.get(czMap.get(result.getString(key)).getString("powercorpid")).getString("id"));
					
					zb.put("changzhanname", czMap.get(result.getString(key)).getString("mingzi"));
					zb.put("czid", result.getString(key));
					
					zb.put("name", czMap.get(result.getString(key)).getString("mingzi"));
				}
				if(Integer.parseInt(jibie)==3){
					bjlx = 34;
					if(table.indexOf("FJTJDATA")!=-1){
						bjlx = 31;
					}
					
					zb.put("gongsiname", gsMap.get(czMap.get(xmMap.get(result.getString(key)).getString("changzhanid")).getString("powercorpid")).getString("mingzi"));
					zb.put("gsid", gsMap.get(czMap.get(xmMap.get(result.getString(key)).getString("changzhanid")).getString("powercorpid")).getString("id"));
					
					zb.put("changzhanname", czMap.get(xmMap.get(result.getString(key)).getString("changzhanid")).getString("mingzi"));
					zb.put("czid", czMap.get(xmMap.get(result.getString(key)).getString("changzhanid")).getString("id"));
					
					zb.put("xiangmuname", xmMap.get(result.getString(key)).getString("mingzi"));
					zb.put("xmid", result.getString(key));
					
					zb.put("name", xmMap.get(result.getString(key)).getString("mingzi"));
				}
				if(Integer.parseInt(jibie)==4){
					bjlx = 31;
					
					zb.put("gongsiname", gsMap.get(czMap.get(fjMap.get(result.getString(key)).getString("changzhanid")).getString("powercorpid")).getString("mingzi"));
					zb.put("gsid", czMap.get(fjMap.get(result.getString(key)).getString("changzhanid")).getString("powercorpid"));
					
					zb.put("changzhanname", czMap.get(fjMap.get(result.getString(key)).getString("changzhanid")).getString("mingzi"));
					zb.put("czid", fjMap.get(result.getString(key)).getString("changzhanid"));
					
					zb.put("xiangmuname", xmMap.get(fjMap.get(result.getString(key)).getString("xiangmuid")).getString("mingzi"));
					zb.put("xmid", fjMap.get(result.getString(key)).getString("xiangmuid"));
					
					zb.put("fengjiname", fjMap.get(result.getString(key)).getString("mingzi"));
					zb.put("fjid", result.getString(key));
					
					zb.put("name", fjMap.get(result.getString(key)).getString("mingzi"));
				}
				if(!"".equals(zbid)){
					for(int i = 0;i < zbids.length;i++){
						String key = zbids[i].split("_")[0]+"-"+bjlx;
						if(jibie.equals("4")){
							/*if(zbMap.get(key).getFloat("displaycoe")!=100.0 && zbMap.get(key).getFloat("displaycoe")!=1.0){
								BigDecimal bd = new BigDecimal(result.getFloat(zbids[i].split("_")[0])); 
								zb.put(zbids[i].split(",")[0], bd.setScale(0,BigDecimal.ROUND_HALF_UP));	
							}else{*/
								float f = 0;
								if(zbMap.get(key)!=null && !zbMap.get(key).getString("danwei").equals("万kWh")){
									f = result.getFloat(zbids[i].split("_")[0])*zbMap.get(key).getFloat("displaycoe");
								}else{
									f = result.getFloat(zbids[i].split("_")[0]);
								}
								BigDecimal bd = new BigDecimal(f); 
								zb.put(zbids[i].split(",")[0], bd.setScale(2,BigDecimal.ROUND_HALF_UP));
							//}
						}else{
							float f = 0;
							if(zbMap.get(key)!=null){
								f = result.getFloat(zbids[i].split("_")[0])*zbMap.get(key).getFloat("displaycoe");
							}else{
								f = result.getFloat(zbids[i].split("_")[0]);
							}
							BigDecimal bd = new BigDecimal(f);
							zb.put(zbids[i].split(",")[0], bd.setScale(2,BigDecimal.ROUND_HALF_UP));	
						}
					}
				}
				return zb;	
			}
		});
		return list;
	}
	
	/**
	 * 获取历史数据
	 * @param table 
	 * @param ids 
	 * @param zbid 
	 * @param jibie 
	 * @return
	 */
	public List<JSONObject> findHisData(String table, String ids, final String zbid, final String jibie,final String bjlx){
		String strSql = "";
		strSql = "select ";
		strSql += "riqi,sum("+zbid.split("_")[0]+") as "+zbid.split("_")[0];
		strSql += " from ";
		strSql += "ies_ls."+table;
		strSql += " where ";
		strSql += "id in("+ids+")";
		strSql += " group by riqi ";
		strSql += "order by riqi"; 
		final Map<String, JSONObject> zbMap = BaseModelCache.getInstance().getZbMap();
		List<JSONObject> list = new ArrayList<JSONObject>();
		list = jdbcTemplate.query(strSql, new RowMapper<JSONObject>() {
			public JSONObject mapRow(ResultSet result, int index)
					throws SQLException {
				JSONObject zb = new JSONObject();
				zb.put("riqi", result.getString("riqi"));

				String key = zbid.split("_")[0]+"-"+bjlx;
				if(jibie.equals("4")){
					if(zbMap.get(key).getFloat("displaycoe")!=100.0 && zbMap.get(key).getFloat("displaycoe")!=1.0){
						BigDecimal bd = new BigDecimal(result.getFloat(zbid.split("_")[0])); 
						zb.put("value", bd.setScale(0,BigDecimal.ROUND_HALF_UP));	
					}else{
						float f = 0;
						if(zbMap.get(key)!=null){
							f = result.getFloat(zbid.split("_")[0])*zbMap.get(key).getFloat("displaycoe");
						}else{
							f = result.getFloat(zbid.split("_")[0]);
						}
						BigDecimal bd = new BigDecimal(f);
						zb.put("value", bd.setScale(2,BigDecimal.ROUND_HALF_UP));
					}
				}else{
					float f = 0;
					if(zbMap.get(key)!=null){
						f = result.getFloat(zbid.split("_")[0])*zbMap.get(key).getFloat("displaycoe");
					}else{
						f = result.getFloat(zbid.split("_")[0]);
					}
					BigDecimal bd = new BigDecimal(f);
					zb.put("value", bd.setScale(2,BigDecimal.ROUND_HALF_UP));	
				}
				return zb;
			}
		});
		
		return list;
	}
}
