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

public class DianLiangGuanLiDao {
	private static DianLiangGuanLiDao dao = null;
	@Autowired
	public JdbcTemplate jdbcTemplate;
	public DianLiangGuanLiDao() {
		if (jdbcTemplate == null) {
			jdbcTemplate = (JdbcTemplate) SpringContextBase.getSpringContext()
					.getBean("jdbcTemplate");
		}
	}
	
	public static synchronized DianLiangGuanLiDao instance(){
		if(dao == null){
			dao = new DianLiangGuanLiDao();
		}
		return dao;
	}
	
	/**
	 * 获取细化数据List
	 * @param jibie 
	 * @param week 
	 * @param endTime 
	 * @param startTime 
	 * @param zbsds 
	 * @param fjid 
	 * @param xmid 
	 * @param czid 
	 * @param gsid 
	 * @param string 
	 * @return
	 */
	public JSONObject findData(final String gsid, final String czid, final String xmid, final String fjid, 
			String zbsds, String startTime, String endTime, String week, String jibie, String dw, final String buwei, String validflag) {
		
		if(zbsds == null || "".equals(zbsds)){
			return null;
		}
		final String[] dws = dw.split(",");
		String col = "";
		String conditional = "";
		String bjcs = "";
		if(gsid != null){
			col += "t.PCID, ";
			conditional += "t.pcid in ("+gsid+") ";
			bjcs += "t.PCID,";
		}
		if(czid != null){
			col += "t.CZID, ";
			conditional += "and t.czid in ("+czid+") ";
			bjcs += "t.CZID,";
		}
		if(xmid != null){
			col += "t.XMID, ";
			conditional += "and t.xmid in ("+xmid+") ";
			bjcs += "t.XMID,";
		}
		if(fjid != null){
			col += "t.FJID, ";
			conditional += "and (select fjtypeid from ies_ms.fengji where id = t.fjid) in("+fjid.split(";")[1]+") ";
			conditional += "and (select fjchangjia from ies_ms.fengji where id = t.fjid) in("+fjid.split(";")[0]+") ";
			bjcs += "t.FJID,";
		}
		if(buwei != null && !"".equals(buwei)){
			col += "jxbuwei, ";
		}else{
			col += "leibie, jxyuanyin, ";
		}
		for(String str : dws){
			col += "sum(t."+str+") as "+str+",";
		}
		col = col.substring(0, col.length()-1);
		conditional += "and t.riqi >= to_date('"+startTime+"', 'yyyy-mm-dd:hh24-mi-ss') ";
		conditional += "and t.riqi <= to_date('"+endTime+"', 'yyyy-mm-dd:hh24-mi-ss') ";
		if(week != null && !"".equals(week)){
			conditional += "and weeknum = "+week;
		}
		String leibie = "";
		String jxyuanyin = "";
		String[] zbs = zbsds.split(";");
		for(String zb : zbs){
			String[] s = zb.split("_");
			leibie += s[0] + ",";
			jxyuanyin += s[1] + ",";
		}
		if(leibie.length()!=0){
			leibie = leibie.substring(0,leibie.length()-1);
		}
		if(jxyuanyin.length()!=0){
			jxyuanyin = jxyuanyin.substring(0,jxyuanyin.length()-1);
		}
		if(validflag != null){
			conditional += " and validflag = "+validflag;
		}
		conditional += " and leibie in ("+leibie+") and jxyuanyin in ("+jxyuanyin+")";
		String group = "";
		if(buwei != null && !"".equals(buwei)){
			conditional += " and jxbuwei in ("+buwei+")";
			group = " group by "+bjcs+"jxbuwei";
		}else{
			group = " group by "+bjcs+"leibie, jxyuanyin";
		}
		String order = " order by "+bjcs.substring(0, bjcs.length()-1);
		String sqlStr = "select "+col+" from ies_ls.fjtjdataxihua t where "+conditional;
		sqlStr += group + order;
		List<JSONObject> list = new ArrayList<JSONObject>();
			list = jdbcTemplate.query(sqlStr, new RowMapper<JSONObject>() {
			public JSONObject mapRow(ResultSet result, int index) throws SQLException {
				JSONObject o = new JSONObject();
				if(gsid != null){
					o.put("gsid", result.getString("PCID"));
				}
				if(czid != null){
					o.put("czid", result.getString("CZID"));
				}
				if(xmid != null){
					o.put("xmid", result.getString("XMID"));
				}
				if(fjid != null){
					o.put("fjid", result.getString("FJID"));
				}
				if(buwei != null && !"".equals(buwei)){
					o.put("jxbuwei", result.getString("JXBUWEI"));
				}else{
					o.put("leibie", result.getString("LEIBIE"));
					o.put("jxyuanyin", result.getString("JXYUANYIN"));
				}
				for(String str : dws){
					o.put(str, result.getString(str));
				}
				return o;
			}
		});
		
		//将参数组成key值
		JSONObject obj = new JSONObject();
		for(JSONObject o : list){
			String key = "";
			if(gsid != null){
				key += o.getString("gsid")+"_";
			}
			if(czid != null){
				key += o.getString("czid")+"_";
			}
			if(xmid != null){
				key += o.getString("xmid")+"_";
			}
			if(fjid != null){
				key += o.getString("fjid")+"_";
			}
			if(buwei != null && !"".equals(buwei)){
				key += o.getString("jxbuwei");
			}else{
				key += o.getString("leibie")+"_";
				key += o.getString("jxyuanyin");
			}
			for(String str : dws){
				float f = o.getFloat(str);
				if(!jibie.equals("4") && str.equals("LOSSDL")){
					f = f / 10000;
				}
				if(str.equals("LASTTIME")){
					f = f / 3600;
				}
				BigDecimal bd = new BigDecimal(f);
				obj.put(str+"_"+key, bd.setScale(2,BigDecimal.ROUND_HALF_UP));
			}
		}
		return obj;
	}	
	
	/**
	 * 获取历史数据
	 * @param gsid
	 * @param czid
	 * @param xmid
	 * @param fjid
	 * @param zbsds 
	 * @return
	*/
	public List<JSONObject> findXihuaHisData(String gsid, String czid, String xmid, String fjid, String zbsds, final String jibie,final String dbdw, String buwei, String validflag) {
		if(zbsds == null || "".equals(zbsds)){
			return null;
		}
		String[] zb = zbsds.split("_");
		String sqlStr = "select RIQI,sum("+dbdw+") as "+dbdw+" from ies_ls.fjtjdataxihua where";
		String conditional = "";
		conditional += " leibie = "+zb[0];
		conditional += " and jxyuanyin in("+zb[1]+")";
		if(buwei != null && !"".equals(buwei)){
			conditional += " and jxbuwei in("+buwei+")";
		}
		if(gsid != null && !"".equals(gsid)){
			conditional += " and PCID = "+gsid;
		}
		if(czid != null && !"".equals(czid)){
			conditional += " and CZID = "+czid;
		}
		if(xmid != null && !"".equals(xmid)){
			conditional += " and XMID = "+xmid;
		}
		if(fjid != null && !"".equals(fjid)){
			conditional += " and FJID = "+fjid;
		}
		if(validflag != null){
			conditional += " and validflag = "+validflag;
		}
		//conditional += " and validflag = 1 ";
		sqlStr += conditional;
		sqlStr += " group by RIQI order by RIQI";
		List<JSONObject> list = new ArrayList<JSONObject>();
		list = jdbcTemplate.query(sqlStr, new RowMapper<JSONObject>() {
			public JSONObject mapRow(ResultSet result, int index) throws SQLException {
				JSONObject o = new JSONObject();
				o.put("riqi", result.getString("RIQI"));
				float f = result.getFloat(dbdw);
				if(!jibie.equals("4") && dbdw.equals("LOSSDL")){
					f = f / 10000;
				}
				if(dbdw.equals("LASTTIME")){
					f = f / 3600;
				}
				o.put("value", f);
				return o;
			}
		});
		return list;
	}
}