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
import com.ieslab.base.model.Fengji;
import com.ieslab.base.model.Fjguzhangma;
import com.ieslab.base.service.BaseModelCache;
import com.ieslab.util.SpringContextBase;

public class FengjiDao {
	
	private static FengjiDao dao = null;
	@Autowired
	public JdbcTemplate jdbcTemplate;
	public FengjiDao() {
		if (jdbcTemplate == null) {
			jdbcTemplate = (JdbcTemplate) SpringContextBase.getSpringContext()
					.getBean("jdbcTemplate");
		}
	}
	
	public static synchronized FengjiDao instance(){
		if(dao == null){
			dao = new FengjiDao();
		}
		return dao;
	}
	
	//根据场站取全部风机
	public List<Fengji> findAllfengji(String czid) {
		List<Fengji> list = new ArrayList<Fengji>();
		try {
			String sqlFilter = " where changzhanid="+czid+" order by id";
			String strSql = "select id, mingzi, bianhao, changzhanid  from ies_ms.fengji ";
			if(czid != null && czid.length() > 0){
				strSql += sqlFilter;
			}else{
				strSql += " order by id ";
			}
			
			list = jdbcTemplate.query(strSql, new RowMapper<Fengji>() {
				public Fengji mapRow(ResultSet result, int index)
						throws SQLException {
					Fengji fj = new Fengji();
					
					fj.setId(result.getInt("id"));
					fj.setBianHao(result.getString("bianhao"));
					fj.setChangzhanid(result.getInt("changzhanid"));
					fj.setMingZi(result.getString("mingzi"));
					return fj;
				}
			});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}
	
	public List<JSONObject> findAll(String czid) {
		List<JSONObject> list = new ArrayList<JSONObject>();
		try {

			String sqlFilter = " where t.changzhanid="+czid+" and t.fjtypeid = m.id order by t.id";
			String strSql = "select distinct(t.id), t.mingzi, t.bianhao, t.changzhanid,  t.xiangmuid, t.jidianxianid, t.fjtypeid, t.yangbanjiflag, " +
					" t.fjchangjia, t.longtitude, t.latitude,  m.fjleixing from ies_ms.fengji t, ies_ms.XINGHAO m ";

			if(czid != null && czid.length() > 0){
				strSql += sqlFilter;
			}else{
				strSql += " where t.fjtypeid = m.id order by t.id ";
			}
			
			list = jdbcTemplate.query(strSql, new RowMapper<JSONObject>() {
				public JSONObject mapRow(ResultSet result, int index)
						throws SQLException {
					Map<String, JSONObject> czMap = BaseModelCache.getInstance().getCzMap();
					JSONObject fj = new JSONObject();
					fj.put("id", result.getString("id"));
					fj.put("bianhao", result.getString("bianhao"));
					fj.put("changzhanid", result.getString("changzhanid"));
					fj.put("powercorpid", czMap.get(result.getString("changzhanid")).getString("powercorpid"));
					fj.put("mingzi", result.getString("mingzi"));
					fj.put("xiangmuid", result.getString("xiangmuid"));
					fj.put("jidianxianid", result.getString("jidianxianid"));
					fj.put("fjtypeid", result.getString("fjtypeid"));
					fj.put("fjchangjia", result.getString("fjchangjia"));
					fj.put("fjleixing", result.getString("fjleixing"));
					fj.put("fjchangjia", result.getString("fjchangjia"));
					fj.put("longtitude", result.getString("longtitude"));
					fj.put("latitude", result.getString("latitude"));
					fj.put("yangbanjiflag", result.getString("yangbanjiflag"));
					return fj;
				}
			});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}
	
	/**
	 * 获取风机故障码
	 */
	public List<Fjguzhangma> findAllFJGZM() {
		List<Fjguzhangma> list = new ArrayList<Fjguzhangma>();
		try {
			String strSql = "select * from ies_ms.fjfaultdef";
			list = jdbcTemplate.query(strSql, new RowMapper<Fjguzhangma>() {
				public Fjguzhangma mapRow(ResultSet result, int index)
						throws SQLException {
					Fjguzhangma gzm = new Fjguzhangma();
					gzm.setId(result.getInt("id"));
					gzm.setXinghaoid(result.getInt("xinghaoid"));
					gzm.setExplain(result.getString("explain"));
					gzm.setAlarmtype(result.getInt("alarmtype"));
					gzm.setAlarmcode(result.getInt("alarmcode"));
					gzm.setAlarmlevel(result.getInt("alarmlevel"));
					gzm.setFjbujian(result.getInt("fjbujian"));
					gzm.setAddtioninfo(result.getString("addtioninfo"));
					return gzm;
				}
			});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}
	
	public String findAllFJID(String czid,String xmid,String cjid,String jxid) {
		String sqlStr = "";
		sqlStr += "select f.id from ";
		sqlStr += "ies_ms.fengji f where ";
		sqlStr += "f.fjtypeid in("+jxid+") and f.fjchangjia in("+cjid+") and ";
		sqlStr += "f.xiangmuid in("+xmid+") and f.changzhanid in ("+czid+") ";
		sqlStr += "order by f.id";
		List<Map<String, Object>> data =jdbcTemplate.queryForList(sqlStr);
		String ids = "";
		if(data.size()!=0){
			for(int i = 0;i < data.size();i++){
				ids += data.get(i).get("ID")+",";
			}
		}
		if(!ids.equals("")){
			ids = ids.substring(0, ids.length()-1);
		}
		return ids;	
	}
	
	/**
	 * 获取风机故障种类
	 */
	public List<JSONObject> findAllGZZL() {
		List<JSONObject> list = new ArrayList<JSONObject>();
		try {
			String strSql = "select * from ies_ms.staticlistinfo t where t.listid = "+281;
			strSql += " order by id"; 
			list = jdbcTemplate.query(strSql, new RowMapper<JSONObject>() {
				public JSONObject mapRow(ResultSet result, int index)
						throws SQLException {
					JSONObject gzm = new JSONObject();
					gzm.put("id", result.getString("id"));
					gzm.put("listid", result.getString("listid"));
					gzm.put("explain", result.getString("explain"));
					return gzm;
				}
			});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}
	
	/**
	 * 获取风机部件参数
	 */
	public List<Integer> findBjcsByFjid(String fjids) {
		List<Integer> list = new ArrayList<Integer>();
		//风机id多选
		List<String> fjisList = new ArrayList<String>();
		String arrids[] = fjids.split(","); 
		if(arrids.length > 1000){
			int num = (arrids.length-arrids.length%1000)/1000;
			for (int j = 0; j <= num; j++) {
				//1000 个为一组，最后剩下的为一组
				int changdu = 1000;
				if(j == num){
					changdu = arrids.length%1000;
				}
				String fjids_fenzu = "";
				for(int i = j*1000; i < j*1000 + changdu; i++){
					fjids_fenzu = fjids_fenzu + arrids[i] +",";
				}
				fjisList.add(fjids_fenzu.substring(0, fjids_fenzu.length()-1));
			}
		}else{
			fjisList.add(fjids);
		}
		
		
		StringBuffer strSql = new StringBuffer();
		strSql.append("select distinct bjcs from ies_ms.comfloatdata where bjlx = 31 ");
		strSql.append("and (bjid in ("+fjisList.get(0)+") ");
		if(arrids.length > 1000){
			for(int i = 1; i<fjisList.size();i++){
				strSql.append(" or bjid in ("+fjisList.get(i)+") ");
			}
		}
		strSql.append(")");
		try {
			list = jdbcTemplate.query(strSql.toString(), new RowMapper<Integer>() {
				public Integer mapRow(ResultSet result, int index)
						throws SQLException {
					return result.getInt("bjcs");
				}
			});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}
}
