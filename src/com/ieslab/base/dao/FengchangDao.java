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
import com.ieslab.util.SpringContextBase;
import com.ieslab.util.StringUtil;

public class FengchangDao {
	
	private static FengchangDao dao = null;
	@Autowired
	public JdbcTemplate jdbcTemplate;
	public FengchangDao() {
		if (jdbcTemplate == null) {
			jdbcTemplate = (JdbcTemplate) SpringContextBase.getSpringContext()
					.getBean("jdbcTemplate");
		}
	}
	
	public static synchronized FengchangDao instance(){
		if(dao == null){
			dao = new FengchangDao();
		}
		return dao;
	}
	
	public List<JSONObject> findAllCZ() {
		List<JSONObject> list = new ArrayList<JSONObject>();
		try {
			String sqlFilter = " where t.id<60001 ";
			String strSql = "select t.id, clfjnums, zlfjnums, zlfjxmcaps, " +
					"clfjxmcaps, zlgfxmcaps, clgfxmcaps, t.mingzi, " +
					"netid, powercorpid, subnetid, stationtype, " +
					"latitude, longtitude, scType, t.jieruflag  from ies_ms.changzhan t ";
			strSql += sqlFilter;
			list = jdbcTemplate.query(strSql, new RowMapper<JSONObject>() {
				public JSONObject mapRow(ResultSet result, int index)
						throws SQLException {
					JSONObject obj = new JSONObject();
					obj.put("id", result.getString("id"));
					obj.put("mingzi", result.getString("mingzi"));
					obj.put("netid", result.getString("netid"));
					
					obj.put("zlfjxmcaps", result.getString("zlfjxmcaps"));
					obj.put("clfjxmcaps", result.getString("clfjxmcaps"));
					obj.put("zlgfxmcaps", result.getString("zlgfxmcaps"));
					obj.put("clgfxmcaps", result.getString("clgfxmcaps"));
					
					obj.put("clfjnums" , result.getString("clfjnums"));
					obj.put("zlfjnums" , result.getString("zlfjnums"));
					obj.put("stationtype", result.getString("stationtype"));
					obj.put("latitude", result.getString("latitude"));
					obj.put("longtitude", result.getString("longtitude"));
					
					obj.put("scType" , result.getString("scType"));
					obj.put("jieruflag" , result.getString("jieruflag"));
					
					return obj;
				}
			});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}
	
	public String findfengchang(String czid) {
		List list = new ArrayList();
		String sql = "select CAPACITY,TOUYUNDATE,CZADDRESS,TELEPHONE from ies_ms.changzhan where id="+czid;
		list = jdbcTemplate.queryForList(sql); 
		return list.toString().replace("[{", "").replace("}]", "");		//CAPACITY,TOUYUNDATE,CZADDRESS,TELEPHONE
	}
	
//	/**
//	 * 根据公司id获取风场
//	 * @param comid
//	 * @return
//	 */
//	public List<Fengchang> findAllCZByCompany(String comid) {
//		List<Fengchang> list = new ArrayList<Fengchang>();
//		try {
//			String sqlFilter = " where id<60001 and stationtype=4 order by id"; // and netid="+comid+" 
//			String strSql = "select id,mingzi, netid, powercorpid, subnetid, stationtype, alarmgra, latitude, longtitude from ies_ms.changzhan ";
//			strSql += sqlFilter;
//			list = jdbcTemplate.query(strSql, new RowMapper<Fengchang>() {
//				public Fengchang mapRow(ResultSet result, int index)
//						throws SQLException {
//					Fengchang cz = new Fengchang();
//					cz.setId(result.getString("id"));
//					cz.setMingzi(result.getString("mingzi"));
//					cz.setNetid(result.getString("netid"));
//					cz.setPowercorpid(result.getString("powercorpid"));
//					cz.setSubnetid(result.getString("subnetid"));
//					cz.setStationtype(result.getString("stationtype"));
//					cz.setLatitude(result.getString("latitude"));
//					cz.setLongtitude(result.getString("longtitude"));
//					String graName = result.getString("alarmgra");
//					graName = graName.replace(".gra","GRA");
//					graName = StringUtil.encode(graName);
//					cz.setAlarmgra(graName);
//					return cz;
//				}
//			});
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}  select id from changzhan where powercorpid in (select id from ies_ms.powercorp where jigouid = 1)
//		return list;
//	}
	
	/**
	 * 根据公司id获取风场数量
	 * @param netid
	 * @return
	 */
	public int findCZNumber(int netid) {
		String strSql = "select count(*) from changzhan where netid = " + netid;
		int number = jdbcTemplate.queryForInt(strSql);
		return number;
	}
	
	/**
	 * 根据公司获取风场
	 * @param netid
	 * @return
	 */
	public String findCZID(String netid,String province) {
		String strSql = "select province,id from changzhan where powercorpid in (select id from ies_ms.powercorp where jigouid = "+netid+")";
		List<Map<String, Object>> data =jdbcTemplate.queryForList(strSql);
		String ids = "";
		if(data.size()!=0){
			for(int i = 0;i < data.size();i++){
				if(province!=null){
					if(data.get(i).get("PROVINCE").toString().equals(province)){
						ids += data.get(i).get("ID")+",";
					}
				}else{
					ids += data.get(i).get("ID")+",";
				}
			}
		}
		if(!ids.equals("")){
			ids = ids.substring(0, ids.length()-1);
		}
		return ids;
	}
	
	/**
	 * @param netid
	 * @return
	 */
	public List<JSONObject> findAllExtCZ(String netid, String startTime,String endTime) {
		List<JSONObject> list = new ArrayList<JSONObject>();
		try {
			String strSql = "select d.changzhanid,p.mingzi,sum(d.capacity) as capacity,sum(d.fadianliang) as fadianliang," +
					"sum(d.xiandianliang) as xiandianliang from ies_ls.Extchangzhandata d,ies_ms.extchangzhan c,ies_ms.province p " +
					"where d.changzhanid in(select id from ies_ms.Extchangzhan where jituanid = "+netid+") " +
					"and riqi >= to_date('"+startTime+"', 'yyyy-mm-dd:hh24-mi-ss') " +
					"and riqi <= to_date('"+endTime+"', 'yyyy-mm-dd:hh24-mi-ss') " +
					"and d.changzhanid = c.id and c.provinceid = p.id " +
					"group by d.changzhanid,p.mingzi";
			list = jdbcTemplate.query(strSql, new RowMapper<JSONObject>() {
				public JSONObject mapRow(ResultSet result, int index)
						throws SQLException {
					JSONObject obj = new JSONObject();
					obj.put("changzhanid", result.getString("changzhanid"));
					obj.put("capacity", result.getString("capacity"));
					obj.put("fadianliang", result.getString("fadianliang"));
					obj.put("xiandianliang", result.getString("xiandianliang"));
					String province = result.getString("mingzi");
					province = province.replaceAll("省", "");
					province = province.replaceAll("自治区", "");
					province = province.replaceAll("回族", "");
					province = province.replaceAll("维吾尔", "");
					province = province.replaceAll("壮族", "");
					province = province.replaceAll("特别行政区", "");
					province = province.replaceAll("市", "");
					obj.put("province", province);
					return obj;
				}
			});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}
	
	/**
	 * @param netid
	 * @return
	 */
	public List<JSONObject> findAllExtCZHisData(String netid, String sfid,final String ziduanming) {
		String sqlStr = "select riqi, sum(d."+ziduanming+") as " + ziduanming +
 	                    " from ies_ls.extchangzhandata d where " +
	                    "changzhanid in (select id from ies_ms.extchangzhan where CompanyID = " + netid +
	                    " and provinceid = "+sfid+") group by riqi order by riqi";
		List<JSONObject> list = new ArrayList<JSONObject>();
		list = jdbcTemplate.query(sqlStr, new RowMapper<JSONObject>() {
			public JSONObject mapRow(ResultSet result, int index)
					throws SQLException {
				JSONObject obj = new JSONObject();
				obj.put("riqi", result.getString("riqi"));
				obj.put("value", result.getString(ziduanming));
				return obj;
			}
		});
		return list;	
	}
	
	/**
	 * @param netid
	 * @return
	 */
	public String findAllShengFen() {
		String strSql = "select mingzi from ies_ms.province";
		List<Map<String, Object>> data =jdbcTemplate.queryForList(strSql);
		String province = "";
		if(data.size()!=0){
			for(int i = 0;i < data.size();i++){
				province += data.get(i).get("mingzi")+",";
			}
		}
		province = province.substring(0, province.length()-1);
		return province;
	}
	
	/**
	 * @param Mingzi
	 * @return
	 */
	public String getShengfenIdByMingzi(String Mingzi) {
		String strSql = "select id from ies_ms.province where mingzi = '"+Mingzi+"'";
		List<Map<String, Object>> data =jdbcTemplate.queryForList(strSql);
		String province = "";
		if(data.size()!=0){
			for(int i = 0;i < data.size();i++){
				province += data.get(i).get("id")+",";
			}
		}
		province = province.substring(0, province.length()-1);
		return province;
	}
	
	/**
	 * @param Mingzi
	 * @return
	 */
	public float getZhuangjiIdByShengfen(String sfid) {
		String strSql = "select sum(CLFJXMCaps) + sum(ZLFJXMCaps) + sum(CLGFXMCaps) + sum(ZLGFXMCaps)";
			   strSql +="from ies_ms.changzhan where province = "+sfid;
			   float zongzhuangji = jdbcTemplate.queryForLong(strSql);
		return zongzhuangji;
	}
}
