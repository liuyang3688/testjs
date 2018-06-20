package com.ieslab.base.dao;

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
import com.ieslab.base.service.BaseModelCache;
import com.ieslab.util.SpringContextBase;
/**
 * 型号表
 * @author Li Hao
 *
 */
public class ZhibiaoDao {
	
	private static ZhibiaoDao dao = null;
	@Autowired
	public JdbcTemplate jdbcTemplate;
	public ZhibiaoDao() {
		if (jdbcTemplate == null) {
			jdbcTemplate = (JdbcTemplate) SpringContextBase.getSpringContext()
					.getBean("jdbcTemplate");
		}
	}
	
	public static synchronized ZhibiaoDao instance(){
		if(dao == null){
			dao = new ZhibiaoDao();
		}
		return dao;
	}
	
	/**
	 * 获取全部指标
	 * @return
	 */
	public List<JSONObject> findAllZhibiao() {
		List<JSONObject> list = new ArrayList<JSONObject>();
		try {
			String strSql = "select * from ies_ms.tjzhibiaosave  order by zhibiaotype, zhibiaoid";
			
			list = jdbcTemplate.query(strSql, new RowMapper<JSONObject>() {
				public JSONObject mapRow(ResultSet result, int index)
						throws SQLException {
					JSONObject zb = new JSONObject();
					zb.put("bjlx", result.getString("bjlx"));
					zb.put("biaoming", result.getString("biaoming"));
					zb.put("zhibiaoid", result.getString("zhibiaoid"));
					zb.put("zhibiaoming", result.getString("zhibiaoming"));
					zb.put("ziduanming", result.getString("ziduanming"));
					zb.put("zhibiaotype", result.getString("zhibiaotype"));
					zb.put("tjtype", result.getString("tjtype"));
					zb.put("danwei", result.getString("danwei"));
					zb.put("displaycoe", result.getString("displaycoe"));
					return zb;
				}
			});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		JSONObject obj = new JSONObject();
		obj.put("bjlx", "5");
		obj.put("biaoming", "changzhan");
		obj.put("zhibiaoming", "装机容量");
		obj.put("ziduanming", "CLFJXMCaps");
		obj.put("zhibiaotype", "2");
		obj.put("danwei", "万kWh");
		obj.put("tjtype", "0");
		list.add(obj);
		return list;
	}
	
	/**
	 * 获取指标数据
	 * @param endtime 
	 * @param starttime 
	 * @param id 
	 * @param ziduanming 
	 * @param biaoming 
	 * @param weeknum 
	 * @param timeType 
	 * @param zclb 
	 * @param pagename 
	 * @param duibiaotype 
	 * @return
	 */
	public List<JSONObject> findZhibiaoData(String biaoming, final String ziduanming, String id, String starttime, String endtime, String weeknum, final String timeType, final String jibie, final String zclb, final String pagename, final String duibiaotype) {
		List<JSONObject> list = new ArrayList<JSONObject>();
		try {
			final String[] ziduanmings = ziduanming.split(",");
			String zb = "";
		 	String strSql = "select ";
		 	if(!ziduanming.equals("")){
		 		if(timeType.equals("5")){
			 		for(int i = 0;i < ziduanmings.length;i++){
			 			if(ziduanmings[i].split("_")[1].equals("0")){
			 				zb += "sum(x."+ziduanmings[i].split("_")[0]+") as "+ziduanmings[i].split("_")[0]+",";
			 			}
			 			if(ziduanmings[i].split("_")[1].equals("1")){
			 				zb += "avg(x."+ziduanmings[i].split("_")[0]+") as "+ziduanmings[i].split("_")[0]+",";
			 			}
			 			if(ziduanmings[i].split("_")[1].equals("2")){
			 				zb += "max(x."+ziduanmings[i].split("_")[0]+") as "+ziduanmings[i].split("_")[0]+",";
			 			}
			 			if(ziduanmings[i].split("_")[1].equals("3")){
			 				zb += "min(x."+ziduanmings[i].split("_")[0]+") as "+ziduanmings[i].split("_")[0]+",";
			 			}
					}
			 	}else{
			 		for(int i = 0;i < ziduanmings.length;i++){
						zb += "x."+ziduanmings[i].split("_")[0]+",";
					}
			 	}
		 		strSql += "x.id,"+zb.substring(0, zb.length() - 1);
		 	}
		 	if(ziduanming.equals("")){
		 		strSql += "x.id";
		 	}
 			strSql += " from ";
 			strSql += "ies_ls."+biaoming+" x ";
 			strSql += " where ";
 			strSql += " x.riqi >= to_date('"+starttime+"', 'yyyy-mm-dd:hh24-mi-ss') ";
 			strSql += "and x.riqi <= to_date('"+endtime+"', 'yyyy-mm-dd:hh24-mi-ss') ";
 			if(id.indexOf(";") >= 0){
 				strSql += "and ";
 				String[] idarr = id.split(";");
 				strSql += "xiangmuid in ("+idarr[0]+") and ";
 				strSql += "(select fjtypeid from ies_ms.fengji where id = x.id) in ("+idarr[2]+") and ";
 				strSql += "(select fjchangjia from ies_ms.fengji where id = x.id) in ("+idarr[1]+") ";
/* 				for(int i = 0;i < idarr.length;i+=999){
 					if((i+999)>idarr.length){
 						strSql += "x.id in("+StringUtils.join(",",Arrays.copyOfRange(idarr, i, idarr.length))+") or ";
 					}else{
 						strSql += "x.id in("+StringUtils.join(",",Arrays.copyOfRange(idarr, i, i+999))+") or ";
 					}
 				}*/
 			}else{
/* 				if(pagename.equals("kekaoxingguanli") && Integer.parseInt(jibie)<4){
 					strSql += "and x.xiangmuid in("+id+") ";
 				}else{*/
 					strSql += "and x.id in("+id+") ";
 				//}
 			}
 			if(!weeknum.equals("-1") && !weeknum.equals("0")){
 				strSql += "and x.weeknum = "+weeknum;
 			}
 			if(timeType.equals("5")){
 				/*if(pagename.equals("kekaoxingguanli") && Integer.parseInt(jibie)<4){
 					strSql += "group by x.xiangmuid";
 				}else{*/
 					strSql += "group by x.id";
 				//}
 			}
 			strSql += " order by id";
 			
 			final Map<String, com.alibaba.fastjson.JSONObject> xmMap = BaseModelCache.getInstance().getXmMap();
 			final Map<String, com.alibaba.fastjson.JSONObject> czMap = BaseModelCache.getInstance().getCzMap();
			final Map<String, com.alibaba.fastjson.JSONObject> gsMap = BaseModelCache.getInstance().getGsMap();
			final Map<String, com.alibaba.fastjson.JSONObject> fjMap = BaseModelCache.getInstance().getFjMap();
			final Map<String, com.alibaba.fastjson.JSONObject> zbMap = BaseModelCache.getInstance().getZbMap();
 			//final String[] ziduanmings = ziduanming.split(",");
			list = jdbcTemplate.query(strSql, new RowMapper<JSONObject>() {
				public JSONObject mapRow(ResultSet result, int index)
						throws SQLException {
					JSONObject zb = new JSONObject();
					int bjlx = 0;
					zb.put("id", result.getString("id"));
					if(jibie!=null){
						zb.put("timeType", timeType);
						zb.put("zclb", zclb);
						//System.out.println(result.getString("id"));
						if(Integer.parseInt(jibie)==1){
							bjlx = 1;
							
							zb.put("gongsiname", gsMap.get(result.getString("id")).getString("mingzi"));
							zb.put("gsid", gsMap.get(result.getString("id")).getString("id"));
						}
						if(Integer.parseInt(jibie)==2){
							bjlx = 5;
							
							zb.put("gongsiname", gsMap.get(czMap.get(result.getString("id")).getString("powercorpid")).getString("mingzi"));
							zb.put("gsid", gsMap.get(czMap.get(result.getString("id")).getString("powercorpid")).getString("id"));
							
							zb.put("changzhanname", czMap.get(result.getString("id")).getString("mingzi"));
							zb.put("czid", result.getString("id"));
						}
						if(Integer.parseInt(jibie)==3){
							bjlx = 34;
							
							zb.put("gongsiname", gsMap.get(czMap.get(xmMap.get(result.getString("id")).getString("changzhanid")).getString("powercorpid")).getString("mingzi"));
							zb.put("gsid", gsMap.get(czMap.get(xmMap.get(result.getString("id")).getString("changzhanid")).getString("powercorpid")).getString("id"));
							
							zb.put("changzhanname", czMap.get(xmMap.get(result.getString("id")).getString("changzhanid")).getString("mingzi"));
							zb.put("czid", czMap.get(xmMap.get(result.getString("id")).getString("changzhanid")).getString("id"));
							
							zb.put("xiangmuname", xmMap.get(result.getString("id")).getString("mingzi"));
							zb.put("xmid", result.getString("id"));
						}
						if(Integer.parseInt(jibie)==4){
							bjlx = 31;
							
							zb.put("gongsiname", gsMap.get(czMap.get(fjMap.get(result.getString("id")).getString("changzhanid")).getString("powercorpid")).getString("mingzi"));
							zb.put("gsid", czMap.get(fjMap.get(result.getString("id")).getString("changzhanid")).getString("powercorpid"));
							
							zb.put("changzhanname", czMap.get(fjMap.get(result.getString("id")).getString("changzhanid")).getString("mingzi"));
							zb.put("czid", fjMap.get(result.getString("id")).getString("changzhanid"));
							
							zb.put("xiangmuname", xmMap.get(fjMap.get(result.getString("id")).getString("xiangmuid")).getString("mingzi"));
							zb.put("xmid", fjMap.get(result.getString("id")).getString("xiangmuid"));
							
							zb.put("mingzi", fjMap.get(result.getString("id")).getString("mingzi"));
							zb.put("fjid", result.getString("id"));
						}
						if(!pagename.equals("kekaoxingguanli") && !pagename.equals("shengchanzhibiao") && !pagename.equals("tongxunyuce") && !pagename.equals("dianliangguanli")){
							bjlx = 0;
						}
						/*if(pagename.equals("kekaoxingguanli")){
							bjlx = 31;
						}*/
					}
					if(!ziduanming.equals("")){
						for(int i = 0; i < ziduanmings.length; i++){
							if(bjlx!=0 && duibiaotype.equals("0")){
								String key = ziduanmings[i].split("_")[0]+"-"+bjlx;
								if(jibie.equals("4") && pagename.equals("dianliangguanli")){
									BigDecimal bd = new BigDecimal(result.getFloat(ziduanmings[i].split("_")[0])); 
									zb.put(ziduanmings[i].split(",")[0], bd.setScale(0,BigDecimal.ROUND_HALF_UP));	
								}else{
									if(jibie.equals("4")){
										if(zbMap.get(key).getFloat("displaycoe")!=100.0 && zbMap.get(key).getFloat("displaycoe")!=1.0){
											float value = 0;
											if(zbMap.get(key).getString("danwei").equals("小时")){
												value = result.getFloat(ziduanmings[i].split("_")[0]) * zbMap.get(key).getFloat("displaycoe");
											}else{
												value = result.getFloat(ziduanmings[i].split("_")[0]);
											}
											BigDecimal bd = new BigDecimal(value); 
											zb.put(ziduanmings[i].split(",")[0], bd.setScale(0,BigDecimal.ROUND_HALF_UP));	
										}else{
											BigDecimal bd = new BigDecimal(result.getFloat(ziduanmings[i].split("_")[0])*zbMap.get(key).getFloat("displaycoe")); 
											zb.put(ziduanmings[i].split(",")[0], bd.setScale(2,BigDecimal.ROUND_HALF_UP));	
										}
									}else{
										BigDecimal bd = new BigDecimal(result.getFloat(ziduanmings[i].split("_")[0])*zbMap.get(key).getFloat("displaycoe")); 
										zb.put(ziduanmings[i].split(",")[0], bd.setScale(2,BigDecimal.ROUND_HALF_UP));	
									}
								}
							}else{
								BigDecimal bd = new BigDecimal(result.getString(ziduanmings[i].split("_")[0])); 
								zb.put(ziduanmings[i].split(",")[0], bd.setScale(2,BigDecimal.ROUND_HALF_UP));
							}
						}
					}
					return zb;
				}
			});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}
	
	public List<JSONObject> findZhibiaoDataKKXGL(String biaoming, final String ziduanming, String id, String starttime, String endtime, String weeknum, final String timeType, final String jibie, final String zclb, final String pagename, final String duibiaotype) {
		String strSql = "select ";
		final String[] zdm = ziduanming.split(",");
		strSql += "xiangmuid,";
		for(int i = 0;i < zdm.length;i++){
			strSql += "sum("+zdm[i].split("_")[0]+") as "+ zdm[i].split("_")[0]+",";
		}
		strSql = strSql.substring(0, strSql.length()-1);
		strSql += " from ies_ls.FJ"+biaoming.substring(2, biaoming.length());
		strSql += " where riqi >= to_date('"+starttime+"', 'yyyy-mm-dd:hh24-mi-ss') ";
		strSql += " and riqi <= to_date('"+endtime+"', 'yyyy-mm-dd:hh24-mi-ss') ";
		if(!weeknum.equals("") && !weeknum.equals("-1") && weeknum!=null && !weeknum.equals("0")){
			strSql += " and weeknum = "+weeknum;
		}
		strSql += " and xiangmuid in ("+id+") ";
		strSql += " group by xiangmuid order by xiangmuid ";
		final String bjlx = "31";
		
		final Map<String, com.alibaba.fastjson.JSONObject> xmMap = BaseModelCache.getInstance().getXmMap();
		final Map<String, com.alibaba.fastjson.JSONObject> czMap = BaseModelCache.getInstance().getCzMap();
		final Map<String, com.alibaba.fastjson.JSONObject> gsMap = BaseModelCache.getInstance().getGsMap();
		final Map<String, com.alibaba.fastjson.JSONObject> zbMap = BaseModelCache.getInstance().getZbMap();
			
		List<JSONObject> list = new ArrayList<JSONObject>();
		try{
			list = jdbcTemplate.query(strSql, new RowMapper<JSONObject>() {
				public JSONObject mapRow(ResultSet result, int index)
						throws SQLException {
					JSONObject zb = new JSONObject();
					zb.put("timeType", timeType);
					zb.put("zclb", zclb);
					zb.put("xmid", result.getString("xiangmuid"));
					zb.put("xiangmuname", xmMap.get(result.getString("xiangmuid")).getString("mingzi"));
					zb.put("czid", xmMap.get(result.getString("xiangmuid")).getString("changzhanid"));
					zb.put("changzhanname", czMap.get(xmMap.get(result.getString("xiangmuid")).getString("changzhanid")).getString("mingzi"));
					zb.put("gsid", czMap.get(xmMap.get(result.getString("xiangmuid")).getString("changzhanid")).getString("powercorpid"));
					zb.put("gongsiname", gsMap.get(czMap.get(xmMap.get(result.getString("xiangmuid")).getString("changzhanid")).getString("powercorpid")).getString("mingzi"));
					for(int i = 0;i < zdm.length;i++){
						zb.put(zdm[i], result.getFloat(zdm[i].split("_")[0])*zbMap.get(zdm[i].split("_")[0]+"-"+bjlx).getFloat("displaycoe"));
					}
					return zb;
				}
			});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}
		
			
	
	/**
	 * 获取历史指标数据
	 * @param id 
	 * @param ziduanming 
	 * @param biaoming 
	 * @return
	 */
	public List<JSONObject> findHisZhibiaoData(String biaoming, final String ziduanming, String id, final String queryType) {
		List<JSONObject> list = new ArrayList<JSONObject>();
		try {
			String strSql = "";
			if(queryType.equals("0")){
				strSql = "select id,";
	 			strSql += "riqi,"+ziduanming.split("_")[0];
	 			strSql += " from ";
	 			strSql += "ies_ls."+biaoming;
	 			strSql += " where ";
	 			strSql += "id in("+id+")";
	 			strSql += "order by riqi"; 
			}else{
				strSql = "select ";
	 			strSql += "riqi,sum("+ziduanming.split("_")[0]+") as "+ziduanming.split("_")[0];
	 			strSql += " from ";
	 			strSql += "ies_ls."+biaoming;
	 			strSql += " where ";
	 			strSql += "id in("+id+")";
	 			strSql += " group by riqi ";
	 			strSql += "order by riqi"; 
			}
			list = jdbcTemplate.query(strSql, new RowMapper<JSONObject>() {
				public JSONObject mapRow(ResultSet result, int index)
						throws SQLException {
					JSONObject zb = new JSONObject();
					if(queryType.equals("0")){
						zb.put("id", result.getString("id"));
					}
					zb.put("riqi", result.getString("riqi"));
					zb.put("value", result.getString(ziduanming.split("_")[0]));
					return zb;
				}
			});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}
	
	/**
	 * 获取细化表数据
	 * @param fjid 
	 * @param xmid 
	 * @param czid 
	 * @param gsid 
	 * @param endTime 
	 * @param startTime 
	 * @param zbsds 
	 * @param dw 
	 * @param jxbuwei 
	 * @param duibiaotype 
	 * @param weeknum 
	 * @param jibie 
	 * @return
	 */
	public JSONObject findXihuaData(String Colname, String[] zbsds, String startTime, String endTime, final String gsid, final String czid, final String xmid, final String fjid, String dw, final String jxbuwei, final String duibiaotype, String weeknum, final String jibie) {
		String strSql = "select ";
		String Sql = "";
		String group = "group by ";
		String bjcs = "";
		final String[] Colnames = Colname.split(",");
		if(!gsid.equals("null")){
			strSql += "t.PCID, ";
			Sql += "t.pcid in ("+gsid+") ";
			bjcs += "t.PCID,";
		}
		if(!czid.equals("null")){
			strSql += "t.CZID, ";
			Sql += "and t.czid in ("+czid+") ";
			bjcs += "CZID,";
		}
		if(!xmid.equals("null")){
			strSql += "t.XMID, ";
			Sql += "and t.xmid in ("+xmid+") ";
			bjcs += "t.XMID,";
		}
		if(!fjid.equals("null")){
			strSql += "t.FJID, ";
			Sql += "and (select fjtypeid from ies_ms.fengji where id = t.fjid) in(1,2,3,4,5,6,7)";
			Sql += "and (select fjchangjia from ies_ms.fengji where id = t.fjid) in(1,2,3,4,5,6)";
			bjcs += "t.FJID,";
		}
		if(!jxbuwei.equals("") && jxbuwei!=null){
			strSql += "t.jxbuwei, ";
			group += "t.jxbuwei, ";
		}
		group = group + bjcs + Colname;
		String dwSql = "";
		final String[] dws = dw.split(",");
		for(int i = 0;i < dws.length;i++){
			if(dws[i].equals("LASTTIME")){
				dwSql += "sum(case when t.validflag=1 then t.lasttime else 0 end) as "+dws[i]+",";
			}else{
				dwSql += "sum(t."+dws[i]+") as "+dws[i]+",";
			}
		}
		dwSql = dwSql.substring(0, dwSql.length()-1);
		strSql += Colname+", "+dwSql+" from ies_ls.fjtjdataxihua t where ";
		strSql += Sql;
		if(!jxbuwei.equals("") && jxbuwei!=null){
			strSql += "and t.jxbuwei in("+jxbuwei+") ";
		}
		if(!weeknum.equals("") && !weeknum.equals("0") && weeknum!=null && !weeknum.equals("-1")){
			strSql += "and weeknum = "+weeknum+" ";
		}
		strSql += "and t.riqi >= to_date('"+startTime+"', 'yyyy-mm-dd:hh24-mi-ss') ";
		strSql += "and t.riqi <= to_date('"+endTime+"', 'yyyy-mm-dd:hh24-mi-ss') and(";
		String[][] sql = new String[Colnames.length][Colnames.length];
		for(int n = 0;n < zbsds.length;n++){
			for(int m = 0;m < zbsds[n].split("with").length;m++){
				if(sql[zbsds[n].split("with").length-1][m]==null || sql[zbsds[n].split("with").length-1][m].equals("")){
					sql[zbsds[n].split("with").length-1][m] = zbsds[n].split("with")[m] + ",";
				}else{
					String[] sqlarr = sql[zbsds[n].split("with").length-1][m].split(",");
					String[] arr = zbsds[n].split("with")[m].split(",");
					for(int z = 0;z < arr.length;z++){
						boolean b = true;
						for(int l = 0;l < sqlarr.length;l++){
							if(sqlarr[l].equals(arr[z])){
								b = false;
							}
						}
						if(b){
							sql[zbsds[n].split("with").length-1][m] += arr[z] + ",";
						}
					}
				}
			}
		}
		
		String s = "";
		for(int i = 0;i < sql.length;i++){
			String sq = "(";
			for(int n = 0;n < sql[i].length;n++){
				if(sql[i][n]!=null){
					sq += Colnames[n] + " in("+sql[i][n].substring(0, sql[i][n].length()-1)+")";
					if(n == sql[i].length-1 || sql[i][n+1]==null){
						sq += ")";
					}else{
						sq += " and ";
					}
				}
			}
			sq += " or ";
			if(sq.equals("( or ")){
				sq = "";
			}
			s += sq;
		}
		s = s.substring(0, s.length()-3);
		strSql += s+")";
		strSql += group;
		strSql += " order by "+bjcs.substring(0, bjcs.length()-1); 
		System.out.println("xihua: "+strSql);
		JSONObject obj = new JSONObject();
		List<JSONObject> list = new ArrayList<JSONObject>();
		try {		
			list = jdbcTemplate.query(strSql, new RowMapper<JSONObject>() {
				public JSONObject mapRow(ResultSet result, int index) throws SQLException {
					JSONObject obj = new JSONObject();
					if(!gsid.equals("null")){
						obj.put("PCID", result.getString("PCID"));
					}
					if(!czid.equals("null")){
						obj.put("CZID", result.getString("CZID"));
					}
					if(!xmid.equals("null")){
						obj.put("XMID", result.getString("XMID"));
					}
					if(!fjid.equals("null")){
						obj.put("FJID", result.getString("FJID"));
					}
					if(!jxbuwei.equals("") && jxbuwei!=null){
						obj.put("jxbuwei", result.getString("jxbuwei"));
					}
					for(int i = 0;i < Colnames.length;i++){
						obj.put(Colnames[i], result.getString(Colnames[i]));
					}
					for(int m = 0;m < dws.length;m++){
						obj.put(dws[m], result.getString(dws[m]));
					}
					return obj;
				}
			});
			/*
			 把部件id相同的（判断包含）做和，以sds为key
			 * */
			for(int i = 0;i < list.size();i++){
				String key = "";
				String tjKey = "";
				if(!gsid.equals("null")){
					key += list.get(i).getString("PCID") + "_";
				}
				if(!czid.equals("null")){
					key += list.get(i).getString("CZID") + "_";
				}
				if(!xmid.equals("null")){
					key += list.get(i).getString("XMID") + "_";
				}
				if(!fjid.equals("null")){
					key += list.get(i).getString("FJID") + "_";
				}
				if(!jxbuwei.equals("") && jxbuwei!=null){
					key += list.get(i).getString("jxbuwei") + "_";
				}
				tjKey = key;
				tjKey = tjKey + list.get(i).getString(Colnames[0]);
				for(int n = 0;n < Colnames.length;n++){
					key += list.get(i).getString(Colnames[n]) + "_";
				}
				key = key.substring(0, key.length()-1);
				for(int m = 0;m < dws.length;m++){
					float a = 0;
					if(dws[m].equals("LASTTIME")){
						a = list.get(i).getFloat(dws[m])/3600;
					}else if(dws[m].equals("LOSSDL")){
						if(jibie.equals("4")){
							a = list.get(i).getFloat(dws[m]);
						}else{
							a = list.get(i).getFloat(dws[m])/10000;
						}
					}else{
						a = list.get(i).getFloat(dws[m]);
					}
					obj.put(dws[m]+"_"+key, a);
				}
				if(obj.containsKey(tjKey)){
					for(int m = 0;m < dws.length;m++){
						float a = 0;
						if(dws[m].equals("LASTTIME")){
							a = (obj.getFloat(dws[m]+"_"+tjKey) + Float.parseFloat(list.get(i).getString(dws[m])))/3600;
						}else if(dws[m].equals("LOSSDL")){
							if(jibie.equals("4")){
								a = obj.getFloat(dws[m]+"_"+tjKey) + Float.parseFloat(list.get(i).getString(dws[m]));
							}else{
								a = obj.getFloat(dws[m]+"_"+tjKey) + Float.parseFloat(list.get(i).getString(dws[m]))/10000;
							}
						}else{
							a = obj.getFloat(dws[m]+"_"+tjKey) + Float.parseFloat(list.get(i).getString(dws[m]));
						}
						obj.put(dws[m]+"_"+tjKey, a);
					}
				}else{
					for(int m = 0;m < dws.length;m++){
						float a = 0;
						if(dws[m].equals("LASTTIME")){
							a = Float.parseFloat(list.get(i).getString(dws[m]))/3600;
						}else if(dws[m].equals("LOSSDL")){
							if(jibie.equals("4")){
								a = Float.parseFloat(list.get(i).getString(dws[m]));
							}else{
								a = Float.parseFloat(list.get(i).getString(dws[m]))/10000;
							}
						}else{
							a = Float.parseFloat(list.get(i).getString(dws[m]));
						}
						obj.put(dws[m]+"_"+tjKey, a);
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return obj;
	}
	
	/**
	 * 获取细化表数据历史数据
	 * @param fjid 
	 * @param xmid 
	 * @param czid 
	 * @param gsid 
	 * @param endTime 
	 * @param startTime 
	 * @param zbsds 
	 * @param jibie 
	 * @return
	 */
	public List<JSONObject> findXihuaDataHis(String[] cs,String Colname,String zbsds,final String dw, final String jibie) {
		String[] col = {"PCID","CZID","XMID","FJID"};
		String sqlStr = "";
		sqlStr += "select RIQI,";
		String colStr = "";
		String whereStr = "";
		for(int i = 0;i < cs.length;i++){
			if(!cs[i].equals("null")){
				colStr += col[i]+",";
				whereStr += col[i] + " in("+cs[i]+") and ";
			}
		}
		sqlStr += colStr+Colname+",sum("+dw+") as "+dw+" FROM ies_ls.fjtjdataxihua where "+whereStr;
		
		String[] Colnames = Colname.split(",");
		String[] sds = zbsds.split("with");
		String wStr = "";
		for(int i = 0;i < Colnames.length;i++){
			wStr += Colnames[i]+" in("+sds[i]+") and ";
		}
		wStr = wStr.substring(0, wStr.length()-4);
		sqlStr += wStr+" group by RIQI,"+colStr+Colname+" order by RIQI";
		List<JSONObject> list = new ArrayList<JSONObject>();
		//System.out.println("sqlStr: "+sqlStr);
		try {
			list = jdbcTemplate.query(sqlStr, new RowMapper<JSONObject>() {
				public JSONObject mapRow(ResultSet result, int index)
						throws SQLException {
					JSONObject obj = new JSONObject();
					obj.put("riqi", result.getString("RIQI"));
					if(dw.equals("LASTTIME")){
						obj.put("value", result.getFloat(dw)/3600);
					}else if(dw.equals("LOSSDL")){
						float a = 1;
						if(!jibie.equals("4")){
							a = 10000;
						}
						obj.put("value", result.getFloat(dw)/a);
					}else{
						obj.put("value", result.getFloat(dw));
					}
					return obj;
				}
			});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}

	public List<JSONObject> findAllZhiBiaoLieBie() {
		String sqlStr = "select * from ies_ms.staticlistinfo where listid = 299 and id!=7";
		List<JSONObject> list = new ArrayList<JSONObject>();
		try {
			list = jdbcTemplate.query(sqlStr, new RowMapper<JSONObject>() {
				public JSONObject mapRow(ResultSet result, int index)
						throws SQLException {
					JSONObject obj = new JSONObject();
					obj.put("name", result.getString("explain"));
					obj.put("value", result.getString("id"));
					return obj;
				}
			});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}
	
	public int findWeenNum(String year) {
		String sql = "select max(weeknum) as weeknum from ies_ls.fjtjdataxihua where";
		sql += " riqi >= to_date('"+year+"-01-01 00:00:00', 'yyyy-mm-dd:hh24-mi-ss') ";
		sql += "and riqi <= to_date('"+year+"-12-29 23:59:59', 'yyyy-mm-dd:hh24-mi-ss') ";
		int weeknum = 0;
		try {
			weeknum = jdbcTemplate.queryForInt(sql);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return weeknum;	
	}
	
	/**
	 * 获取指标数据
	 * @param endtime 
	 * @param starttime 
	 * @param id 
	 * @param ziduanming 
	 * @param biaoming 
	 * @param weeknum 
	 * @param timeType 
	 * @param zclb 
	 * @param pagename 
	 * @param duibiaotype 
	 * @return
	 */
	public List<JSONObject> findTJZhibiaoData(String tableName, String startTime, String endTime, String tabletype, final String tablestyle, final String bjlx, String ziduanming, String ids) {
		String strSql = "select ";
		if(tablestyle.equals("1")){
			strSql += "id,";
		}else{
			strSql += "riqi,";
		}
		final String[] ziduanmings = ziduanming.split(",");
		for(int i = 0;i < ziduanmings.length;i++){
			if(ziduanmings[i].split("_")[1].equals("0")){
				strSql += "sum("+ziduanmings[i].split("_")[0]+") as "+ziduanmings[i].split("_")[0]+",";
			}else if(ziduanmings[i].split("_")[1].equals("1")){
				strSql += "avg("+ziduanmings[i].split("_")[0]+") as "+ziduanmings[i].split("_")[0]+",";
			}else if(ziduanmings[i].split("_")[1].equals("2")){
				strSql += "max("+ziduanmings[i].split("_")[0]+") as "+ziduanmings[i].split("_")[0]+",";
			}else if(ziduanmings[i].split("_")[1].equals("3")){
				strSql += "min("+ziduanmings[i].split("_")[0]+") as "+ziduanmings[i].split("_")[0]+",";
			}
		}
		strSql = strSql.substring(0, strSql.length()-1);
		strSql += " from ies_ls."+tableName;
		strSql += " where riqi >= to_date('"+startTime+"', 'yyyy-mm-dd:hh24-mi-ss') and";
		strSql += " riqi <= to_date('"+endTime+"', 'yyyy-mm-dd:hh24-mi-ss') and ";
		strSql += " id in("+ids+") ";
		strSql += " group by ";
		if(tablestyle.equals("1")){
			strSql += "id ";
			strSql += "order by id";
		}else{
			strSql += "riqi ";
			strSql += "order by riqi";
		}
		
		final Map<String, JSONObject> GSmap = BaseModelCache.getInstance().getGsMap();
		final Map<String, JSONObject> CZmap = BaseModelCache.getInstance().getCzMap();
		final Map<String, JSONObject> XMmap = BaseModelCache.getInstance().getXmMap();
		final Map<String, JSONObject> FJmap = BaseModelCache.getInstance().getFjMap();
		
		List<JSONObject> list = new ArrayList<JSONObject>();
		list = jdbcTemplate.query(strSql, new RowMapper<JSONObject>() {
			public JSONObject mapRow(ResultSet result, int index)
					throws SQLException {
				JSONObject obj = new JSONObject();
				if(tablestyle.equals("1")){
					String name = "";
					if(bjlx.equals("3")){
						name = GSmap.get(result.getString("id")).getString("mingzi");
					}else if(bjlx.equals("5")){
						name = CZmap.get(result.getString("id")).getString("mingzi");
					}else if(bjlx.equals("34")){
						name = XMmap.get(result.getString("id")).getString("mingzi");
					}else if(bjlx.equals("31")){
						name = FJmap.get(result.getString("id")).getString("mingzi");
					}
					obj.put("name", name);
				}else{
					String riqi = result.getString("RIQI");
					obj.put("riqi", riqi.split(" ")[0]);
				}
				
				for(int i = 0;i < ziduanmings.length;i++){
					BigDecimal bd = new BigDecimal(result.getString(ziduanmings[i].split("_")[0]));
					float value = bd.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
					obj.put(ziduanmings[i], value);
				}
				return obj;
			}
		});
		
		return list;
	}
}
