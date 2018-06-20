package com.ieslab.baojingzhongxin.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.alibaba.fastjson.JSONObject;
import com.ieslab.base.service.BaseModelCache;
import com.ieslab.util.SpringContextBase;
/**
 * 事故追忆
 * @author Administrator
 *
 */
public class ShiGuZhuiYiDao {
	
	public JdbcTemplate jdbcTemplate;
	public ShiGuZhuiYiDao() {
		if (jdbcTemplate == null) {
			jdbcTemplate = (JdbcTemplate) SpringContextBase.getSpringContext()
					.getBean("ieslsdb");
		}
	}
	/**
	 * 查询电气设备事件列表
	 * @param czid
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	public List<JSONObject> getDQEventInfo(String czids, String starttime, String endtime, String year){
		
		List<JSONObject> list = new ArrayList<JSONObject>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select t.id, t.bujianleixingid, t.bujianid, t.bujiancanshuid, t.sgtime, t.changzhanid, ");
		sql.append("  t.frontpointnum, t.backpointnum, t.datanum, t.alarmgra, n.mingzi ");
		sql.append(" from ies_ls.zyevent t, ies_ms.bujiancanshu n ");
		sql.append(" where t.changzhanid in ("+czids+") ");
		sql.append("  and t.sgtime >= to_date('"+starttime+"', 'yyyy-mm-dd:hh24-mi-ss') ");
		sql.append("  and t.sgtime <= to_date('"+endtime+"', 'yyyy-mm-dd:hh24-mi-ss') ");
		sql.append("  and t.bujianleixingid = n.bjlxid  ");
		sql.append("  and t.bujiancanshuid = n.id ");
		sql.append("  order by t.sgtime ");
		
		list = jdbcTemplate.query(sql.toString(), new RowMapper<JSONObject>() {
			public JSONObject mapRow(ResultSet result, int index)
					throws SQLException {
				
				JSONObject obj = new JSONObject();
				obj.put("id", result.getString("id"));
				obj.put("bjlxid", result.getString("bujianleixingid"));
				obj.put("bjid", result.getString("bujianid"));
				obj.put("bjcsid", result.getString("bujiancanshuid"));
				obj.put("sgtime", result.getString("sgtime"));
				obj.put("frontpointnum", result.getString("frontpointnum"));
				obj.put("backpointnum", result.getString("backpointnum"));
				obj.put("datanum", result.getString("datanum"));
				obj.put("alarmgra", result.getString("alarmgra").replace(".gra", ""));
				obj.put("csname", result.getString("mingzi"));
				obj.put("czid", result.getString("changzhanid"));
				String czid = result.getString("changzhanid");
				if(BaseModelCache.getInstance().getCzMap().containsKey(czid)){
					String czname = BaseModelCache.getInstance().getCzMap().get(czid).getString("mignzi");
					obj.put("czname", czname);
				}
				String bjlxid = result.getString("bujianleixingid");
				String bjid = result.getString("bujianid");
				JSONObject o = BaseModelCache.getInstance().getBjObjByBjTypeAndBjId(bjlxid, bjid);
				if(o != null){
					obj.put("bjname", o.getString("mingzi"));
				}
				return obj;
			}
		});
		return list;
	}
	/**
	 * 查询风电机组追忆数据
	 * @param fjids
	 * @param starttime
	 * @param endtime
	 * @param year
	 * @param type 类型201风机故障停机 401环境受累
	 * @return
	 */
	public List<JSONObject> getFJEventInfo(String fjids, String starttime, String endtime, String year, String type){
		
		List<JSONObject> list = new ArrayList<JSONObject>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select t.id, t.bujianleixingid, t.bujianid, t.bujiancanshuid, t.sgtime, t.changzhanid, ");
		sql.append("  t.frontpointnum, t.backpointnum, t.datanum, t.alarmgra, n.mingzi ");
		sql.append(" from ies_ls.FJZYEVENT t, ies_ms.bujiancanshu n ");
		sql.append(" where  ");
		
		if(fjids != null && fjids.length() > 0){
			sql.append(" ( ");
			String fjidsArr[] = fjids.split(",");
			for(String fjid: fjidsArr){
				sql.append(" t.bujianid in ("+fjid+") or ");
			}
			sql.append(" t.bujianid in (-1) ) ");
		}
		
		sql.append("  and t.sgtime >= to_date('"+starttime+"', 'yyyy-mm-dd:hh24-mi-ss') ");
		sql.append("  and t.sgtime <= to_date('"+endtime+"', 'yyyy-mm-dd:hh24-mi-ss') ");
		sql.append("  and t.bujianleixingid = n.bjlxid  ");
		sql.append("  and t.bujiancanshuid = n.id ");
		sql.append("  and t.triggervalue = "+type+" ");
		sql.append("  order by t.sgtime ");
		
		list = jdbcTemplate.query(sql.toString(), new RowMapper<JSONObject>() {
			public JSONObject mapRow(ResultSet result, int index)
					throws SQLException {
				
				JSONObject obj = new JSONObject();
				obj.put("id", result.getString("id"));
				obj.put("bjlxid", result.getString("bujianleixingid"));
				obj.put("bjid", result.getString("bujianid"));
				obj.put("bjcsid", result.getString("bujiancanshuid"));
				obj.put("sgtime", result.getString("sgtime"));
				obj.put("frontpointnum", result.getString("frontpointnum"));
				obj.put("backpointnum", result.getString("backpointnum"));
				obj.put("datanum", result.getString("datanum"));
				obj.put("alarmgra", result.getString("alarmgra"));
				obj.put("csname", result.getString("mingzi"));
				obj.put("czid", result.getString("changzhanid"));
				String czid = result.getString("changzhanid");
				if(BaseModelCache.getInstance().getCzMap().containsKey(czid)){
					String czname = BaseModelCache.getInstance().getCzMap().get(czid).getString("mignzi");
					obj.put("czname", czname);
				}
				String bjlxid = result.getString("bujianleixingid");
				String bjid = result.getString("bujianid");
				if("31".equals(bjlxid)){
					JSONObject o = BaseModelCache.getInstance().getFjMap().get(bjid);
					if(o != null){
						obj.put("bjname", o.getString("mingzi"));
						obj.put("fjlx", o.getString("fjleixing"));
					}
				}else{
					JSONObject o = BaseModelCache.getInstance().getBjObjByBjTypeAndBjId(bjlxid, bjid);
					if(o != null){
						obj.put("bjname", o.getString("mingzi"));
					}
				}
				
				return obj;
			}
		});
		return list;
	}
	/**
	 * 查询电气设备追忆详细数据
	 * @param bjlxid
	 * @param bjid
	 * @param csid
	 * @param eventid
	 * @param position
	 * @return
	 */
	public List<JSONObject> queryDQEventData(String bjlxid, String bjid, String csid, String eventid, String year, String index){
		List<JSONObject> list = new ArrayList<JSONObject>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select t.id, t.valuetype, t.bujianleixingid, t.bujianid, t.bujiancanshuid, t.position, ");
		sql.append("  	t.ycdata, t.yxdata ");
		sql.append(" from ZYEVENTDATA t ");
		sql.append(" where t.id = "+eventid+" ");
		sql.append("  	and t.bujianleixingid = "+bjlxid+" ");
		sql.append("    and t.bujianid = "+bjid+" ");
		sql.append("    and t.bujiancanshuid = "+csid+" ");
		sql.append("    and t.position <= "+index+" ");
		sql.append("    order by t.position ");
		
		list = jdbcTemplate.query(sql.toString(), new RowMapper<JSONObject>() {
			public JSONObject mapRow(ResultSet result, int index)
					throws SQLException {
				JSONObject obj = new JSONObject();
				obj.put("id", result.getString("id"));
				obj.put("valuetype", result.getString("valuetype"));
				obj.put("bujianleixingid", result.getString("bujianleixingid"));
				obj.put("bujianid", result.getString("bujianid"));
				obj.put("bujiancanshuid", result.getString("bujiancanshuid"));
				obj.put("position", result.getString("position"));
				obj.put("data", result.getString("ycdata"));
				return obj;
			}
		});
		
		return list;
	}
	/**
	 * 获取风电机组追忆详细数据
	 * @param bjlxid
	 * @param bjid
	 * @param csid
	 * @param eventid
	 * @param year
	 * @return
	 */
	public List<JSONObject> queryFJEventData(String bjlxid, String bjid, final String csid, String eventid, String year, String index){
		List<JSONObject> list = new ArrayList<JSONObject>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select * ");
		sql.append(" from FJZYEVENTDATA t ");
		sql.append(" where t.id = "+eventid+" ");
		sql.append("  	and t.bujianleixingid = "+bjlxid+" ");
		sql.append("    and t.position <= "+index+" ");
		sql.append("    order by t.position ");
		
		list = jdbcTemplate.query(sql.toString(), new RowMapper<JSONObject>() {
			public JSONObject mapRow(ResultSet result, int index)
					throws SQLException {
				JSONObject obj = new JSONObject();
				obj.put("id", result.getString("id"));
				obj.put("data", result.getString("c" + csid));
				obj.put("bujianleixingid", result.getString("bujianleixingid"));
				obj.put("bujianid", result.getString("bujianid"));
				obj.put("bujiancanshuid", result.getString("bujiancanshuid"));
				obj.put("position", result.getString("position"));
				return obj;
			}
		});
		
		return list;
	}
	/**
	 * 获取风机事故追忆实时数据
	 * @param bjlxid
	 * @param bjid
	 * @param csid
	 * @param eventid
	 * @param year
	 * @return
	 */
	public List<JSONObject> queryFJEventRtData(String bjlxid, String bjid, String csid, String eventid, String pos, String year){
		List<JSONObject> list = new ArrayList<JSONObject>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select * ");
		sql.append(" from FJZYEVENTDATA t ");
		sql.append(" where t.id = "+eventid+" ");
		sql.append("  	and t.bujianleixingid = "+bjlxid+" ");
		sql.append("    and t.bujianid = "+bjid+" ");
		sql.append("   	and t.position = "+pos+" ");
		final String arr[] = csid.split(",");
		list = jdbcTemplate.query(sql.toString(), new RowMapper<JSONObject>() {
			public JSONObject mapRow(ResultSet result, int index)
					throws SQLException {
				JSONObject obj = new JSONObject();
				obj.put("id", result.getString("id"));
				if(arr.length > 0){
					for(String t: arr){
						BigDecimal b = new BigDecimal(result.getString("c" + t));
						obj.put("c" + t, b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
					}
				}
				obj.put("bujianleixingid", result.getString("bujianleixingid"));
				obj.put("bujianid", result.getString("bujianid"));
				obj.put("bujiancanshuid", result.getString("bujiancanshuid"));
				obj.put("position", result.getString("position"));
				return obj;
			}
		});
		
		return list;
	}
}
