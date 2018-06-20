	package com.ieslab.base.controller;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.ieslab.base.dao.FengchangDao;
import com.ieslab.base.dao.FengjiDao;
import com.ieslab.base.dao.ZhibiaoDao;
import com.ieslab.base.service.BaseModelCache;
import com.ieslab.base.service.ZhibiaoService;
import com.ieslab.util.DateUtil;


@Controller
@RequestMapping("zhibiao")
public class ZhibiaoController {
	
	@RequestMapping("getALLZhibiaoleixing")
	public void getALLZhibiaoleixing(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		
		List<com.alibaba.fastjson.JSONObject> zblist = ZhibiaoDao.instance().findAllZhiBiaoLieBie();
		try {
			response.getWriter().print(zblist);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping("getALLZhibiaocanshu")
	public void getALLZhibiaocanshu(HttpServletRequest request, HttpServletResponse response) throws JSONException, Exception{
		String jibieid = request.getParameter("jibieid");
		String leixingid = request.getParameter("leixingid");
		if(jibieid.equals("0")){
			jibieid = "1";
		}else if(jibieid.equals("1")){
			jibieid = "3";
		}else if(jibieid.equals("2")){
			jibieid = "5";
		}else if(jibieid.equals("3")){
			jibieid = "34";
		}else if(jibieid.equals("4")){
			jibieid = "31";
		}
		
		JSONArray res = new JSONArray();
		if(leixingid!=null && leixingid.length()!=0){
			List<JSONObject> zblist = BaseModelCache.getInstance().getZhibiaoList();
			for(JSONObject obj : zblist){
				//for(int i = 0;i < leixingids.length;i++){
					if(leixingid.indexOf(obj.getString("zhibiaotype"))!=-1 && obj.getString("bjlx").equals(jibieid)){
						com.alibaba.fastjson.JSONObject o = new com.alibaba.fastjson.JSONObject();
						o.put("name", obj.get("zhibiaoming"));
						o.put("id", obj.get("ziduanming"));
						o.put("biaoming", obj.get("biaoming"));
						o.put("tjtype", obj.get("tjtype"));
						o.put("danwei", obj.get("danwei"));
						res.add(o);
					}
				//}
			}
		}
		/*JSONArray res = new JSONArray();System.out.println(leixingid);
		if(leixingid != null && !leixingid.equals("")){
			String[] leixingids = leixingid.split(",");
			for(int i = 0;i < leixingids.length;i++){
				for(JSONObject obj : ZhibiaoService.map.get("zhibiaocanshu")){
					if(obj.getString("jibie").equals(jibieid)&&obj.getString("leixing").equals(leixingids[i])){
						com.alibaba.fastjson.JSONObject o = new com.alibaba.fastjson.JSONObject();
						o.put("name", obj.get("name"));
						o.put("id", obj.get("id"));
						o.put("fenlei", obj.get("fenlei"));
						res.add(o);
					}
				}
			}
		}*/
		try {
			response.getWriter().print(res);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping("getALLDataByZhibiao")
	public void getALLDataByZhibiao(HttpServletRequest request, HttpServletResponse response) throws JSONException, Exception{
		String jibie = request.getParameter("jibieid");
		String jxid = request.getParameter("jxid");
		String gsid = request.getParameter("gsid");
		String czid = request.getParameter("czid");
		String xmid = request.getParameter("xmid");
		String cjid = request.getParameter("cjid");
		String zbid = request.getParameter("zbid");
		String zbsds = request.getParameter("zbsds");
		String zclb = request.getParameter("zclb");
		String timeType = request.getParameter("timetype");
		String startTime = request.getParameter("starttime");
		String endTime = request.getParameter("endtime");
		String selectedfield = request.getParameter("selectedfield");//对标数据列
		String querytype = "";
		
		String pagename = request.getParameter("pagename");
		String jxbuwei = request.getParameter("jxbuwei");
		//System.out.println(gsid+"  "+czid+"  "+xmid+"  "+cjid+"  "+jxid);
		//选择的参数，用作查询趋势对标数据
		String selectgs = request.getParameter("selectgs");
		String selectcz = request.getParameter("selectcz");
		String selectxm = request.getParameter("selectxm");
		String selectjz = request.getParameter("selectjz");
		String selectTime = request.getParameter("selectTime");
		String selectzclb = request.getParameter("selectzclb");
		
		String duibiaotype = request.getParameter("duibiaotype");
		String weeknum = "-1";
		//String qsdbid = "";
		String Colname = request.getParameter("Colname");
		String dw = request.getParameter("dw");
		String dbStartTime = "";
		String dbEndTime = "";
		String danweiStr =  "";
		String dbweeknum = "";
		
		String buweiname = "";
		if(jxbuwei!=null){
			if(jxbuwei.equals("all")){
				String s = "";
				if(dw.equals("CNT")){
					s = "(次)";
				}
				if(dw.equals("LOSSDL")){
					if(jibie.equals("4")){
						s = "(kwh)";
					}else{
						s = "(万kwh)";
					}
				}
				if(dw.equals("LASTTIME")){
					s = "(小时)";
				}
				jxbuwei = "";
				List<JSONObject> bwLst = FengjiDao.instance().findAllGZZL();
				for(JSONObject obj : bwLst){
					if(!obj.getString("id").equals("1")){
						jxbuwei += obj.getString("id")+",";
						buweiname += obj.getString("explain")+s+",";
					}
				}
				jxbuwei = jxbuwei.substring(0, jxbuwei.length()-1);
				buweiname = buweiname.substring(0, buweiname.length()-1);
			}
		}
		
		if( (startTime.equals("") || startTime==null ) && (endTime==null || endTime.equals("")) ){
			JSONObject error = new JSONObject();
			error.put("error", "未选择日期");
			response.getWriter().print(error);
			return;
		}
		try {
			if(!duibiaotype.equals("0")){
				if(dw!=null && !dw.equals("")){
					String[] field = selectedfield.split("_");
					boolean b = false;
					if(dw!=null && !dw.equals(""))
					for(int i = 0;i < dw.split(",").length;i++){
						if(field[0].endsWith(dw.split(",")[i])){
							b = true;
						}
					}
					if(b){
						zbid = "";
						zbsds = selectedfield;
					}else{
						zbid = selectedfield;
						zbsds = "";
					}
				}
			}
			JSONArray res = new JSONArray();
			//趋势对标处理
			if(duibiaotype.equals("qushi")){
				String tjjb = "";
				String tb = "";
				String sbjid = "";
				String csarr = selectgs+"-"+selectcz+"-"+selectxm+"-"+selectjz;
				if(selectjz==null && selectxm==null && selectcz==null){
					tjjb = "1";
					tb = "PCTJDATA";
					sbjid = selectgs;
				}else if(selectjz==null && selectxm==null){
					tjjb = "2";
					tb = "CZTJDATA";
					sbjid = selectcz;
				}else if(selectjz==null){
					tjjb = "3";
					tb = "XMTJDATA";
					sbjid = selectxm;
				}else{
					tjjb = "4";
					tb = "FJTJDATA";
					sbjid = selectjz;
				}
				String[] cs = csarr.split("-");
				if("0".equals(selectTime)){
					tb += "day";  
				}else if("1".equals(selectTime)){
					tb += "day";
				}else if("2".equals(selectTime)){
					tb += "week";
				}else if("3".equals(selectTime)){
					tb += "month";
				}else if("4".equals(selectTime)){
					tb += "year";
				}else if("5".equals(selectTime)){
					tb += "day";
				}
				
				/*if(selectzclb!=null && selectzclb.length()!=0 && !tjjb.equals("4")){
					String[] selectzclbs = zclb.split(",");
					if(selectzclbs.length == 1){
						if(selectzclbs[0].equals("0")){
							tb += "_CL";
						}
						if(selectzclbs[0].equals("1")){
							tb += "_ZL";
						}
					}
				}*/
				int bjlx = 0;
				if(jibie.equals("1")){
					bjlx = 1;
				}else if(jibie.equals("2")){
					bjlx = 5;
				}else if(jibie.equals("3")){
					bjlx = 34;
				}else if(jibie.equals("4")){
					bjlx = 31;
				}
				if(pagename.equals("kekaoxingguanli") && zbid!=null && !zbid.equals("")){
					bjlx = 31;
				}
				List<JSONObject> zbdatalists = null;
				if(zbid!=null && !zbid.equals("")){
					zbdatalists = ZhibiaoService.getHisZhibiaoData(tb, selectedfield, sbjid);
				}
				if(zbsds!=null && !zbsds.equals("")){
					bjlx = 0;
					String d = zbsds.split("_")[0];
					String s = zbsds.split("_")[1];
					if(!jxbuwei.equals("") && jxbuwei!=null){
						Colname = "jxbuwei";
					}
					zbdatalists = ZhibiaoService.getHisZhibiaoXihua(cs,Colname,s,d,jibie);
				}
				Map<String, JSONObject> zbMap = BaseModelCache.getInstance().getZbMap();
				for(JSONObject obj: zbdatalists){
					if(bjlx!=0){
						if(zbMap.get(selectedfield.split("_")[0]+"-"+bjlx).getFloatValue("displaycoe")!=1.0 && 
						   zbMap.get(selectedfield.split("_")[0]+"-"+bjlx).getFloatValue("displaycoe")!=100.0){
							if(jibie.equals("4")){
								obj.put("value", obj.getFloatValue("value"));
							}else{
								obj.put("value", obj.getFloatValue("value")*zbMap.get(selectedfield.split("_")[0]+"-"+bjlx).getFloatValue("displaycoe"));
							}
						}else{
							obj.put("value", obj.getFloatValue("value")*zbMap.get(selectedfield.split("_")[0]+"-"+bjlx).getFloatValue("displaycoe"));
						}
					}else{
						obj.put("value", obj.getFloatValue("value"));
					}
					res.add(obj);
				}
			}else{
				if("0".equals(timeType)){
					querytype = "day";  
					if(duibiaotype.equals("huanbi")){
						dbStartTime = DateUtil.formatdate(DateUtil.adddays(new Date(), -2), "yyyy-MM-dd HH:mm:ss");
						dbEndTime = DateUtil.formatdate(DateUtil.adddays(new Date(), -1), "yyyy-MM-dd HH:mm:ss");
					}
					if(duibiaotype.equals("tongbi")){
						dbStartTime = DateUtil.formatdate(DateUtil.addYear(DateUtil.adddays(new Date(), -1), -1),"yyyy-MM-dd HH:mm:ss");
						dbEndTime = DateUtil.formatdate(DateUtil.addYear(new Date(), -1),"yyyy-MM-dd HH:mm:ss");
					}else{
						startTime = DateUtil.formatdate(DateUtil.adddays(new Date(), -1), "yyyy-MM-dd HH:mm:ss");
						endTime = DateUtil.formatdate(new Date(), "yyyy-MM-dd HH:mm:ss");
					}
				}else if("1".equals(timeType)){//按天查询
					if(startTime == null || startTime.length() == 0){
						return;
					}
					querytype = "day";
					if(duibiaotype.equals("huanbi")){
						dbStartTime = DateUtil.formatdate(DateUtil.adddays(DateUtil.parseDateStr(startTime), -1), "yyyy-MM-dd");
					}
					if(duibiaotype.equals("tongbi")){
						dbStartTime = DateUtil.formatdate(DateUtil.addYear(DateUtil.parseDateStr(startTime),-1), "yyyy-MM-dd");
					}
					dbEndTime = dbStartTime + " 23:59:59";
					dbStartTime = dbStartTime + " 00:00:00";
					endTime = startTime + " 23:59:59";
					startTime = startTime + " 00:00:00";
				}else if("2".equals(timeType)){//按周查询
					if(startTime == null || startTime.length() == 0){
						return;
					}
					querytype = "week";
					weeknum = endTime;
					endTime = startTime + "-12-28 23:59:59";
					startTime = Integer.parseInt(startTime)-1 + "-12-29 00:00:00";
					
					if(duibiaotype.equals("huanbi")){
						if(weeknum.equals("1")){
							dbweeknum = "52";
							endTime = Integer.parseInt(startTime.split("-")[0])-1 + "-12-28 23:59:59";
							startTime = Integer.parseInt(startTime.split("-")[0])-2 + "-12-29 00:00:00";
						}else{
							dbweeknum = Integer.parseInt(weeknum)-1+"";
						}
						dbEndTime = endTime;
						dbStartTime = startTime;
					}
					if(duibiaotype.equals("tongbi")){
						endTime = Integer.parseInt(startTime.split("-")[0])-1 + "-12-28 23:59:59";
						startTime = Integer.parseInt(startTime.split("-")[0])-2 + "-12-29 00:00:00";
						
						dbEndTime = startTime;
						dbStartTime = endTime;
					}
				}else if("3".equals(timeType)){//按月查询
					if(startTime == null || startTime.length() == 0){
						return;
					}
					querytype = "month";
					if(duibiaotype.equals("huanbi")){
						dbStartTime = DateUtil.formatdate(DateUtil.addmonth(DateUtil.parseDateStr(startTime,"yyyy-MM"), -1), "yyyy-MM");
					}
					if(duibiaotype.equals("tongbi")){
						dbStartTime = DateUtil.formatdate(DateUtil.addYear(DateUtil.parseDateStr(startTime,"yyyy-MM"), -1), "yyyy-MM");
					}
					//获取这个月的天数
					long days = DateUtil.getDaysofMonth(DateUtil.parseDateStr(startTime, "yyyy-MM"));
					endTime = startTime + "-" + days +" 23:59:59";
					startTime = startTime + "-01 00:00:00";
					if(duibiaotype.equals("tongbi") || duibiaotype.equals("huanbi")){
						long days1 = DateUtil.getDaysofMonth(DateUtil.parseDateStr(dbStartTime, "yyyy-MM"));
						dbEndTime = dbStartTime + "-" + days1 +" 23:59:59";
						dbStartTime = dbStartTime + "-01 00:00:00";
					}
				}else if("4".equals(timeType)){
					if(startTime == null || startTime.length() == 0){
						return;
					}
					querytype = "year";
					if(duibiaotype.equals("huanbi")){
						dbStartTime = DateUtil.formatdate(DateUtil.addYear(DateUtil.parseDateStr(startTime,"yyyy"), -1), "yyyy");
					}
					if(duibiaotype.equals("tongbi")){
						dbStartTime = DateUtil.formatdate(DateUtil.addYear(DateUtil.parseDateStr(startTime,"yyyy"), -1), "yyyy");
					}
					endTime = startTime + "-12-31 23:59:59";
					startTime = startTime + "-01-01 00:00:00";
					
					dbEndTime = dbStartTime + "-12-31 23:59:59";
					dbStartTime = dbStartTime + "-01-01 00:00:00";
				}else if("5".equals(timeType)){
					if(startTime == null || startTime.length() == 0){
						return;
					}
					querytype = "day";
				}
				if(startTime == null || startTime.length() == 0){
					return;
				}
				//System.out.println("startTime: "+startTime);
				//System.out.println("endTime: "+endTime);
				//System.out.println("weeknum: "+weeknum);
				//System.out.println("dbStartTime: "+dbStartTime);
				//System.out.println("dbEndTime: "+dbEndTime);
				JSONArray arr = new JSONArray();
				JSONObject DataObj = new JSONObject();
				//统计级别为机型
				String ids = "";
				String table = "";
				String name = "";
				int bjlx = 0;
				Map<String, com.alibaba.fastjson.JSONObject> zbcsMap = null;
				if(jibie.equals("4")){
					//id不为空
					if(jxid!=null && jxid.length()!=0 &&
					xmid!=null && xmid.length()!=0 && cjid!=null && cjid.length()!=0 &&
					czid!=null && czid.length()!=0){
						name = "mingzi";
						table = "FJTJDATA"+querytype;
						ids = xmid+";"+cjid+";"+jxid;
						bjlx = 31;
					}
					zbcsMap = BaseModelCache.getInstance().getFjMap();
				}else if(jibie.equals("3")){
					if(xmid!=null && xmid.length()!=0 && czid!=null && czid.length()!=0 &&
					zclb!=null && zclb.length()!=0){
						name = "xiangmuname";
						table = "XMTJDATA"+querytype;
						ids = xmid;
						bjlx = 34;
					}
					zbcsMap = BaseModelCache.getInstance().getXmMap();
				}else if(jibie.equals("2")){
					if(czid!=null && czid.length()!=0 && zclb!=null && zclb.length()!=0){
						name = "changzhanname";
						table = "CZTJDATA"+querytype;
						ids = czid;
						bjlx = 5;
					}
					zbcsMap = BaseModelCache.getInstance().getCzMap();
				}else if(jibie.equals("1")){
					if(gsid!=null && gsid.length()!=0 && zclb!=null && zclb.length()!=0){
						name = "gongsiname";
						table = "PCTJDATA"+querytype;
						ids = gsid;
						bjlx = 1;
					}
					zbcsMap = BaseModelCache.getInstance().getGsMap();
				}
				
				Map<String, com.alibaba.fastjson.JSONObject> zb = BaseModelCache.getInstance().getZbMap();
				if(pagename.equals("shengchanzhibiao")){
					for(int i = 0;i < zbid.split(",").length;i++){
						String key = zbid.split(",")[i].split("_")[0]+"-"+bjlx;
						danweiStr += zb.get(key).getString("danwei") + ",";
					}
					if(danweiStr.length()!=0){
						danweiStr = danweiStr.substring(0, danweiStr.length()-1);
					}
				}
				/*
				 先通过
				 * */
				/*if(zclb!=null && zclb.length()!=0 && !zclb.equals("-1") && !jibie.equals("4")){
					String[] zclbs = zclb.split(",");
					if(zclbs.length == 1){
						if(zclbs[0].equals("0")){
							table += "_CL";
						}
						if(zclbs[0].equals("1")){
							table += "_ZL";
						}
					}
				}*/
				//System.out.println("DataObj: "+DataObj);
				//System.out.println("arr: "+arr);
				//System.out.println(table+"   "+zbid+"   "+ids+"   "+startTime+"   "+endTime+"   "+weeknum+"   "+timeType);
				Map<String, com.alibaba.fastjson.JSONObject> zbMap = BaseModelCache.getInstance().getZbMap();
				if(zbid!=null && zbid.length()!=0){
					if(ids!=null && ids.length()!=0){
						//String[] zbids = zbid.split(",");
						String week;
						if(!duibiaotype.equals("0") && duibiaotype!=null){
							week = Integer.parseInt(weeknum)+1+"";
							zbid = selectedfield;
						}else{
							week = weeknum;
						}
						List<JSONObject> zbdatalist = ZhibiaoService.getZhibiaoData(table, zbid, ids, startTime, endTime, week, timeType, jibie, zclb, pagename, duibiaotype);
						
						//环比、同比对标
						List<JSONObject> dbdatalist = null;
						JSONObject obj = new JSONObject();
						Map<String, JSONObject> db_Map = new ConcurrentHashMap<String, JSONObject>();
						if(!duibiaotype.equals("0") && duibiaotype!=null){
							res.clear();
							dbdatalist = ZhibiaoService.getZhibiaoData(table, zbid, ids, dbStartTime, dbEndTime, weeknum, timeType, jibie, zclb, pagename, duibiaotype);
		
							if(dbdatalist==null){
								response.getWriter().print(res);
								return;
							}else if(dbdatalist.size()==0){
								response.getWriter().print(res);
								return;
							}
							for(JSONObject dbobj : dbdatalist){
								db_Map.put(dbobj.getString("id"), dbobj);
							}
						}
						if(zbdatalist.size()!=0 && zbdatalist!=null){
							for(int j = 0; j < zbdatalist.size(); j++){
								JSONArray jarr = new JSONArray();
								if(duibiaotype!=null && !duibiaotype.equals("0") && duibiaotype.length()!=0){
									if(zbdatalist.get(j).getFloat(selectedfield)!=null){
										if(db_Map.get(zbdatalist.get(j).getString("id"))!=null){
											float f1 = zbdatalist.get(j).getFloat(selectedfield);
											float f2 = db_Map.get(zbdatalist.get(j).getString("id")).getFloat(selectedfield);
											
											//float f3 = f1-f2;
											float f = f1-f2;
											if(bjlx != 31){
												String keyStr = selectedfield.split("_")[0]+"-"+bjlx;
												JSONObject o = zbMap.get(keyStr);
												if(o.getString("danwei").equals("万kWh")){
													f = f * o.getFloat("displaycoe");
												}
											}
											jarr.add(0, zbdatalist.get(j).getString(name));
											jarr.add(1, f);
											res.add(jarr);	
										}
									}
								}else{
									res.add(zbdatalist.get(j));
								}
							}
						}
					}
				}else{
					String week;
					if(!duibiaotype.equals("0") && duibiaotype!=null){
						week = Integer.parseInt(weeknum)+1+"";
						zbid = selectedfield;
					}else{
						week = weeknum;
					}
					List<JSONObject> zbdatalist = ZhibiaoService.getZhibiaoData(table, "", ids, startTime, endTime, week, timeType, jibie, zclb, pagename, duibiaotype);
					if(zbdatalist.size()!=0 && zbdatalist!=null){
						for(int j = 0; j < zbdatalist.size(); j++){
							res.add(zbdatalist.get(j));
						}
					}
				}
				if(zbsds!=null && zbsds.length()!=0){
					String [] sds = zbsds.split(";");
					if(res.size()==0 || res==null){
						res = arr;
					}
					String gongsid = "null";
					String changzhanid = "null";
					String xiangmuid = "null";
					String fengjiid = "null";
					
					if(Integer.parseInt(jibie)>=1){
						gongsid = gsid;
					}
					if(Integer.parseInt(jibie)>=2){
						changzhanid = czid;
					}
					if(Integer.parseInt(jibie)>=3){
						xiangmuid = xmid;
					}
					if(Integer.parseInt(jibie)>=4){
						fengjiid = cjid+";"+jxid;
					}

					//  !!--  多个条件的值不能为null，否则会影响datagrid列布局
					String ddd = "";
					if(!duibiaotype.equals("0")){
						String s = "";
						for(int i = 0;i < sds.length;i++){
							s += sds[i].split("_")[1]+";";
							ddd += sds[i].split("_")[0]+";";
						}
						s = s.substring(0, s.length()-1);
						dw = ddd.substring(0, ddd.length()-1);
						sds = s.split(";");
						
						if(pagename.equals("xihuaduibiao") && !jxbuwei.equals("") && jxbuwei!=null && jxbuwei.split(",").length==1){
							sds = "1".split(",");
						}
					}
					int a;
					if(!duibiaotype.equals("0") && duibiaotype!=null){
						a = Integer.parseInt(weeknum)+1;
					}else{
						a = Integer.parseInt(weeknum);
					}
					//System.out.println(res);
					JSONObject xhdata = ZhibiaoService.getXihuaData(Colname,sds,startTime,endTime,gongsid,changzhanid,xiangmuid,fengjiid,dw,a+"",timeType,jxbuwei,duibiaotype,jibie);
					
					//环比、同比对标
					JSONArray res1 = new JSONArray();
					JSONObject dbdata = null;
					String[] dws = dw.split(",");
					//System.out.println(startTime+"   "+weeknum+"   "+endTime);
					if(!duibiaotype.equals("0") && duibiaotype!=null){
						//res.clear();
						String[] idArr = null;
						dbdata = ZhibiaoService.getXihuaData(Colname,sds,dbStartTime,dbEndTime,gongsid,changzhanid,xiangmuid,fengjiid,dw,dbweeknum,timeType,jxbuwei,duibiaotype,jibie);
						
						if(dbdata==null){
							res.clear();
							response.getWriter().print(res);
							return;
						}else if(dbdata.size()==0){
							res.clear();
							response.getWriter().print(res);
							return;
						}
					}
					if(xhdata.size()!=0 && xhdata!=null){
						int fixed = 2;
						if(jibie.equals("4") && pagename.equals("dianliangguanli")){
							fixed = 0;
						}
						for(int i = 0;i < res.size();i++){
							String keyStr = "";
							if(Integer.parseInt(jibie)>=1){
								keyStr += res.getJSONObject(i).getString("gsid")+"_";
							}
							if(Integer.parseInt(jibie)>=2){
								keyStr += res.getJSONObject(i).getString("czid")+"_";
							}
							if(Integer.parseInt(jibie)>=3){
								keyStr += res.getJSONObject(i).getString("xmid")+"_";
							}
							if(Integer.parseInt(jibie)>=4){
								keyStr += res.getJSONObject(i).getString("fjid")+"_";
							}
							String[] jxbuweis;
							String saveKey = keyStr;
							if(jxbuwei!=null && !jxbuwei.equals("")){
								jxbuweis = jxbuwei.split(",");
							}else{
								jxbuweis = "".split(",");
							}
							for(int e = 0;e < jxbuweis.length;e++){
								if(!jxbuweis[e].equals("")){
									keyStr = saveKey;
									keyStr += jxbuweis[e]+"_";
								}
								for(int t = 0;t < dws.length;t++){
									for(int j = 0;j < sds.length;j++){
										String sdsT = sds[j];
										String tempKey = keyStr;
										JSONArray jarr = new JSONArray();
										if(sdsT.indexOf(",") > 0){
											int z = 0;
											if(sdsT.indexOf("with") > 0){
												String dd[] = sdsT.split("with");
												String dA[] = dd[1].split(",");
												Float value = (float) 0;
												Float value2 = (float) 0;
												for(String d: dA){
													String k = tempKey + dd[0]+"_"+d;
													if(xhdata.getFloat(dws[t]+"_"+k)!=null){
														value += xhdata.getFloat(dws[t]+"_"+k);
														if(dbdata!=null && !duibiaotype.equals("0") && duibiaotype!=null){
															if(dbdata.size()!=0){
																if(dbdata.getFloat(dws[t]+"_"+tempKey)==null){
																	value2 += 0;
																}else{
																	value2 += dbdata.getFloat(dws[t]+"_"+tempKey);
																}
															}
														}
													}else{
														z+=1;
													}
												}
												if(z==dA.length){
													value = null;
													z = 0;
												}
												if(dbdata!=null && !duibiaotype.equals("0") && duibiaotype!=null && value2!=null && value2!=(float)0){
													if(dbdata.size()!=0){
														value = value-value2;
														jarr.add(0,zbcsMap.get(keyStr.split("_")[Integer.parseInt(jibie)-1]).getString("mingzi"));
														jarr.add(1,value);
														res1.add(jarr);
													}
												}else{
													String ss = (dws[t]+"_"+sds[j]).replaceAll(",", "and");
													if(jxbuwei!=null && !jxbuwei.equals("")){
														ss = dws[t]+"_"+jxbuweis[e];
													}
													if(value!=null){
														BigDecimal b = new BigDecimal(value);
														value = b.setScale(fixed,BigDecimal.ROUND_HALF_UP).floatValue();
													}
													res.getJSONObject(i).put(ss, value);
												}
											}else{
												String dA[] = sdsT.split(",");
												Float value = (float) 0;
												Float value2 = (float) 0;
												for(String d: dA){
													String k = tempKey + "_"+d;
													if(xhdata.getFloat(dws[t]+"_"+k)!=null){
														value += xhdata.getFloat(k);
														if(dbdata!=null && !duibiaotype.equals("0") && duibiaotype!=null){
															if(dbdata.size()!=0){
																value2 += dbdata.getFloat(dws[t]+"_"+k);
															}
														}
													}else{
														z+=1;
													}
												}
												if(z==dA.length){
													value = null;
													z = 0;
												}
												if(dbdata!=null && !duibiaotype.equals("0") && duibiaotype!=null && value2!=null && value2!=(float)0){
													if(dbdata.size()!=0){
														value = value-value2;
														jarr.add(0,zbcsMap.get(keyStr.split("_")[Integer.parseInt(jibie)-1]).getString("mingzi"));
														jarr.add(1,value);
														res1.add(jarr);
													}
												}else{
													String ss = (dws[t]+"_"+sds[j]).replaceAll(",", "and");
													if(jxbuwei!=null && !jxbuwei.equals("")){
														ss = dws[t]+"_"+jxbuweis[e];
													}
													if(value!=null){
														BigDecimal b = new BigDecimal(value);
														value = b.setScale(fixed,BigDecimal.ROUND_HALF_UP).floatValue();
													}
													res.getJSONObject(i).put(ss, value);
												}
											}
										}else{
											tempKey += sdsT.replaceAll("with", "_");
											Float value = (float) 0;
											Float value2 = (float) 0;
											if(xhdata.getFloat(dws[t]+"_"+tempKey)!=null){
												value = xhdata.getFloat(dws[t]+"_"+tempKey);
												if(dbdata!=null && !duibiaotype.equals("0") && duibiaotype!=null){
													if(dbdata.size()!=0){
														if(dbdata.getFloat(dws[t]+"_"+tempKey)==null){
															value2 += 0;
														}else{
															value2 += dbdata.getFloat(dws[t]+"_"+tempKey);
														}
													}
												}
											}else{
												value = null;
											}
											if(dbdata!=null && !duibiaotype.equals("0") && duibiaotype!=null && value2!=null && value2!=(float)0){
												if(dbdata.size()!=0){
													value = value-value2;
													jarr.add(0,zbcsMap.get(keyStr.split("_")[Integer.parseInt(jibie)-1]).getString("mingzi"));
													jarr.add(1,value);
													res1.add(jarr);
												}
											}else{
												String ss = (dws[t]+"_"+sds[j]).replaceAll(",", "and");
												if(jxbuwei!=null && !jxbuwei.equals("")){
													ss = dws[t]+"_"+jxbuweis[e];
												}
												if(value!=null){
													BigDecimal b = new BigDecimal(value);
													value = b.setScale(fixed,BigDecimal.ROUND_HALF_UP).floatValue();
												}
												res.getJSONObject(i).put(ss, value);
											}
										}
									}
								}
							}
						}
					}
					//System.out.println(res);
					if(!duibiaotype.equals("0") && duibiaotype!=null){
						res.clear();
						res = res1;
					}else{
						if(jxbuwei!=null && !jxbuwei.equals("")){
							for(int z = 0;z < res.size();z++){
								for(int d = 0;d < dw.split(",").length;d++){
									for(int b = 0;b < jxbuwei.split(",").length;b++){
										String key = dw.split(",")[d]+"_"+(jxbuwei.split(",")[b]);
										if(res.getJSONObject(z).get(key)==null){
											res.getJSONObject(z).put(key, 0);
										}
									}
								}
							}
						}else{
							for(int z = 0;z < res.size();z++){
								for(int d = 0;d < dw.split(",").length;d++){
									for(int b = 0;b < zbsds.split(";").length;b++){
										String key = dw.split(",")[d]+"_"+(zbsds.split(";")[b].replaceAll(",", "and"));
										if(res.getJSONObject(z).get(key)==null){
											res.getJSONObject(z).put(key, 0);
										}
									}
								}
							}
						}
					}
				}
			}
			JSONObject data = new JSONObject();
			data.put("data", res);
			if(!danweiStr.equals("")){
				data.put("danwei", danweiStr);
			}
			//System.out.println("res: "+res);
			if(jxbuwei!=null){
				if(!jxbuwei.equals("")){
					data.put("buwei", jxbuwei);
					data.put("buweiname", buweiname);
				}
			}
			response.getWriter().print(data);
		} catch (IOException e) {
			System.out.println("指标数据查询失败！");
			e.printStackTrace();
		}
	}
	
	/** 
	* 获取电网、集团对标数据
	* 省份： 根据changzhan表中的Province字段可对各场站按省进行查询分组，
	*       省份名字根据Province字段取PROVINCE表中的MingZi字段
	     单位：  默认取dianwang表中的MingZi，即大唐新能源公司；
    期末装机：取changzhan表中CLFJXMCaps,ZLFJXMCaps,CLGFXMCaps,ZLGFXMCaps四个字段的加和，
                                         计算出该省份所有的装机容量；
    利用小时：按省份将CZTJDataDay对应场站中开始时间和结束时间的ShiJiFDL进行累加，
                                         计算出该省份对应的总发电量，则利用小时=总发电量/期末装机；
    限电率：按省份将CZTJDataDay对应场站中开始时间和结束时间的XDLOSSDL（限电电量）进行累加，
                                    则限电率=XDLOSSDL/(XDLOSSDL+ShiJiFDL) 
	*/ 
	@RequestMapping("getALLDuibiaoData")
	public void getALLDuibiaoData(HttpServletRequest request, HttpServletResponse response) throws JSONException, Exception{
		String duibiaotype = request.getParameter("duibiaotype");
		String startTime = request.getParameter("starttime");
		String endTime = request.getParameter("endtime");
		String timeType = request.getParameter("timetype");
		String zbid = request.getParameter("zbid");
		String jtid = request.getParameter("jtid");
		String pagename = request.getParameter("pagename");
		String selectedfield = request.getParameter("selectedfield");
		String SelectProvince = request.getParameter("province");
		String selectcz = request.getParameter("selectcz");
		String dbStartTime = "";
		String dbEndTime = "";
		String weeknum = "-1";
		if("0".equals(timeType)){  
			if(duibiaotype.equals("huanbi")){
				dbStartTime = DateUtil.formatdate(DateUtil.adddays(new Date(), -2), "yyyy-MM-dd HH:mm:ss");
				dbEndTime = DateUtil.formatdate(DateUtil.adddays(new Date(), -1), "yyyy-MM-dd HH:mm:ss");
			}
			if(duibiaotype.equals("tongbi")){
				dbStartTime = DateUtil.formatdate(DateUtil.addYear(DateUtil.adddays(new Date(), -1), -1),"yyyy-MM-dd HH:mm:ss");
				dbEndTime = DateUtil.formatdate(DateUtil.addYear(new Date(), -1),"yyyy-MM-dd HH:mm:ss");
			}else{
				startTime = DateUtil.formatdate(DateUtil.adddays(new Date(), -1), "yyyy-MM-dd HH:mm:ss");
				endTime = DateUtil.formatdate(new Date(), "yyyy-MM-dd HH:mm:ss");
			}
		}else if("1".equals(timeType)){//按天查询
			if(startTime == null || startTime.length() == 0){
				return;
			}
			if(duibiaotype.equals("huanbi")){
				dbStartTime = DateUtil.formatdate(DateUtil.adddays(DateUtil.parseDateStr(startTime), -1), "yyyy-MM-dd");
			}
			if(duibiaotype.equals("tongbi")){
				dbStartTime = DateUtil.formatdate(DateUtil.addYear(DateUtil.parseDateStr(startTime),-1), "yyyy-MM-dd");
			}
			dbEndTime = dbStartTime + " 23:59:59";
			dbStartTime = dbStartTime + " 00:00:00";
			endTime = startTime + " 23:59:59";
			startTime = startTime + " 00:00:00";
		}else if("2".equals(timeType)){//按周查询
			if(startTime == null || startTime.length() == 0){
				return;
			}
			weeknum = endTime;
			endTime = startTime + "-12-28 23:59:59";
			startTime = Integer.parseInt(startTime)-1 + "-12-29 00:00:00";
			
			if(duibiaotype.equals("huanbi")){
				if(weeknum.equals("1")){
					weeknum = "52";
					endTime = Integer.parseInt(startTime.split("-")[0])-1 + "-12-28 23:59:59";
					startTime = Integer.parseInt(startTime.split("-")[0])-2 + "-12-29 00:00:00";
				}else{
					weeknum = Integer.parseInt(weeknum)-1+"";
				}
				dbEndTime = endTime;
				dbStartTime = startTime;
			}
			if(duibiaotype.equals("tongbi")){
				endTime = Integer.parseInt(startTime.split("-")[0])-1 + "-12-28 23:59:59";
				startTime = Integer.parseInt(startTime.split("-")[0])-2 + "-12-29 00:00:00";
				
				dbEndTime = startTime;
				dbStartTime = endTime;
			}
		}else if("3".equals(timeType)){//按月查询
			if(startTime == null || startTime.length() == 0){
				return;
			}
			if(duibiaotype.equals("huanbi")){
				dbStartTime = DateUtil.formatdate(DateUtil.addmonth(DateUtil.parseDateStr(startTime,"yyyy-MM"), -1), "yyyy-MM");
			}
			if(duibiaotype.equals("tongbi")){
				dbStartTime = DateUtil.formatdate(DateUtil.addYear(DateUtil.parseDateStr(startTime,"yyyy-MM"), -1), "yyyy-MM");
			}
			//获取这个月的天数
			long days = DateUtil.getDaysofMonth(DateUtil.parseDateStr(startTime, "yyyy-MM"));
			endTime = startTime + "-" + days +" 23:59:59";
			startTime = startTime + "-01 00:00:00";
			if(duibiaotype.equals("tongbi") && duibiaotype.equals("huanbi")){
				long days1 = DateUtil.getDaysofMonth(DateUtil.parseDateStr(dbStartTime, "yyyy-MM"));
				dbEndTime = dbStartTime + "-" + days1 +" 23:59:59";
				dbStartTime = dbStartTime + "-01 00:00:00";
			}
		}else if("4".equals(timeType)){
			if(startTime == null || startTime.length() == 0){
				return;
			}
			if(duibiaotype.equals("huanbi")){
				dbStartTime = DateUtil.formatdate(DateUtil.addYear(DateUtil.parseDateStr(startTime,"yyyy"), -1), "yyyy");
			}
			if(duibiaotype.equals("tongbi")){
				dbStartTime = DateUtil.formatdate(DateUtil.addYear(DateUtil.parseDateStr(startTime,"yyyy"), -1), "yyyy");
			}
			endTime = startTime + "-12-31 23:59:59";
			startTime = startTime + "-01-01 00:00:00";
			
			dbEndTime = dbStartTime + "-12-31 23:59:59";
			dbStartTime = dbStartTime + "-01-01 00:00:00";
		}else if("5".equals(timeType)){
			if(startTime == null || startTime.length() == 0){
				return;
			}
		}
		if(startTime == null || startTime.length() == 0){
			return;
		}
		if(jtid == null || jtid.length() == 0){
			return;
		}
		//System.out.println("startTime: "+startTime);
		//System.out.println("endTime: "+endTime);
		//System.out.println("weeknum: "+weeknum);
		//System.out.println("dbStartTime: "+dbStartTime);
		//System.out.println("dbEndTime: "+dbEndTime);
		//System.out.println("selectedfield: "+selectedfield);
		JSONArray res = new JSONArray();
		//细化对标传进来一个省份参数和指标参数
		//细化对标不执行按省份分组统计那一步，直接按传过来的参数过滤，然后传到前台。细化对标的环比、同比对标同理
		//细化对标的趋势对标需要单独处理
		JSONArray arr = new JSONArray();
		JSONObject ob = new JSONObject();
		ob.put("startTime", startTime);
		ob.put("endTime", endTime);
		ob.put("weeknum", weeknum);
		if(pagename.equals("xihuaduibiao")){
			ob.put("zbid", selectedfield.split("_")[0]);
		}else{
			ob.put("zbid", zbid);
		}
		ob.put("jtid", jtid);
		arr.add(0,ob);
		
		if(duibiaotype.equals("huanbi") || duibiaotype.equals("tongbi")){
			arr.getJSONObject(0).put("zbid", selectedfield.split("_")[0]);
			arr.getJSONObject(0).put("jtid", selectedfield.split("_")[1]);
			JSONObject ob2 = new JSONObject();
			ob2.put("zbid", selectedfield.split("_")[0]);
			ob2.put("jtid", selectedfield.split("_")[1]);
			ob2.put("weeknum", weeknum);
			ob2.put("startTime", dbStartTime);
			ob2.put("endTime", dbEndTime);
			arr.add(1,ob2);
		}
		
		String[] province;
		if(duibiaotype.equals("qushi")){
			province = SelectProvince.split(",");
			arr.clear();
			JSONObject objQs = new JSONObject();
			objQs.put("startTime", null);
			objQs.put("endTime", null);
			objQs.put("weeknum", weeknum);
			objQs.put("zbid", selectedfield.split("_")[0]);
			if(pagename.equals("xihuaduibiao")){
				
			}
			//装机容量没有历史数据
			if(selectedfield.split("_")[0].equals("province") || selectedfield.split("_")[0].equals("qimozhuangji") || selectedfield.split("_")[0].equals("zhuangjizhanbi")){
				response.getWriter().print(res);
				return;
			}
			objQs.put("jtid", selectedfield.split("_")[1]);
			arr.add(0,objQs);
		}else{
			province = FengchangDao.instance().findAllShengFen().split(",");
		}
		JSONArray r1 = new JSONArray();
		JSONArray r2 = new JSONArray();
		List<JSONObject> czLst = BaseModelCache.getInstance().getStaList();
		Map<String, JSONObject> czMap = BaseModelCache.getInstance().getCzMap();
		Map<String, JSONObject> gsMap = BaseModelCache.getInstance().getGsMap();
		for(int q = 0;q < arr.size();q++){
			Map<String, JSONObject> map = new ConcurrentHashMap<String, JSONObject>();
			String[] jtids = arr.getJSONObject(q).getString("jtid").split(",");
			for(int i = 0;i < jtids.length;i++){
				float zongzhuangji = 0;
				float xhzhuangji = 0;
				if(jtids[i].equals("1")){
					String ids = "";
					if(duibiaotype.equals("qushi")){
						String sfid = FengchangDao.instance().getShengfenIdByMingzi(province[0]);
						ids = FengchangDao.instance().findCZID(jtids[i],sfid);
					}else if(pagename.equals("xihuaduibiao")){
						String sfid = FengchangDao.instance().getShengfenIdByMingzi(SelectProvince);
						ids = FengchangDao.instance().findCZID(jtids[i],sfid);
						xhzhuangji = FengchangDao.instance().getZhuangjiIdByShengfen(sfid);
					}else{
						ids = FengchangDao.instance().findCZID(jtids[i],null);
					}
					List<JSONObject> Lst = null;
					if(duibiaotype.equals("qushi")){
						if(pagename.equals("xihuaduibiao")){
							ids = selectcz;
						}
						Lst = ZhibiaoDao.instance().findHisZhibiaoData("CZTJDataDay", "ShiJiFDL_0", ids, "1");
						List<JSONObject> Lst2 = ZhibiaoDao.instance().findHisZhibiaoData("CZTJDataDay", "XDLOSSDL_0", ids, "1");
						for(int t = 0;t < Lst.size();t++){
							Lst.get(t).put("ShiJiFDL", Lst.get(t).get("value"));
							Lst.get(t).put("XDLOSSDL", Lst2.get(t).get("value"));
						}
						for(int z = 0;z < ids.split(",").length;z++){
							zongzhuangji += czMap.get(ids.split(",")[z]).getFloatValue("zlfjxmcaps");
							zongzhuangji += czMap.get(ids.split(",")[z]).getFloatValue("clfjxmcaps");
							zongzhuangji += czMap.get(ids.split(",")[z]).getFloatValue("zlgfxmcaps");
							zongzhuangji += czMap.get(ids.split(",")[z]).getFloatValue("clgfxmcaps");
						}
						for(JSONObject obj : Lst){
							JSONObject qs = new JSONObject();
							if(arr.getJSONObject(q).getString("zbid").indexOf("liyongxiaoshi")>=0){
								float value = 0;
								if(zongzhuangji!=0){
									value = obj.getFloatValue("ShiJiFDL")/zongzhuangji;
								}
								BigDecimal bd = new BigDecimal(value);
								qs.put("riqi", obj.getString("riqi"));
								qs.put("value", bd.setScale(2,BigDecimal.ROUND_HALF_UP));
								res.add(qs);
							}
							if(arr.getJSONObject(q).getString("zbid").indexOf("xiandianlv")>=0){
								float value = 0;
								if((obj.getFloatValue("ShiJiFDL")+obj.getFloatValue("XDLOSSDL"))!=0){
									value = obj.getFloatValue("XDLOSSDL")/(obj.getFloatValue("ShiJiFDL")+obj.getFloatValue("XDLOSSDL"));
								}
								BigDecimal bd = new BigDecimal(value);
								qs.put("riqi", obj.getString("riqi"));
								qs.put("value", bd.setScale(2,BigDecimal.ROUND_HALF_UP));
								res.add(qs);
							}
						}
						JSONObject obj = new JSONObject();
						
						obj.put("data", res);
						response.getWriter().print(obj);
						return;
					}else{
						if(pagename.equals("xihuaduibiao")){
							String idsByProvince = "";
							for(JSONObject obj : czLst){
								if(obj.getString("province").equals(SelectProvince)){
									idsByProvince += obj.getString("id")+",";
								}
							}
							ids = idsByProvince.substring(0, idsByProvince.length()-1);
							Lst = ZhibiaoDao.instance().findZhibiaoData("CZTJDataDay", "ShiJiFDL_0,XDLOSSDL_0", ids, arr.getJSONObject(q).getString("startTime"), arr.getJSONObject(q).getString("endTime"), weeknum, "5",null,null,pagename,duibiaotype);
						}else{
							Lst = ZhibiaoDao.instance().findZhibiaoData("CZTJDataDay", "ShiJiFDL_0,XDLOSSDL_0", ids, arr.getJSONObject(q).getString("startTime"), arr.getJSONObject(q).getString("endTime"), weeknum, "5",null,null,pagename,duibiaotype);
						}
					}
					Map<String, JSONObject> zbMap = new ConcurrentHashMap<String, JSONObject>();
					for(JSONObject obj : Lst){
						if(pagename.equals("xihuaduibiao")){
							obj.put("changzhanname", czMap.get(obj.getString("id")).getString("mingzi"));
							obj.put("czid", obj.getString("id"));
							obj.put("gongsiname", gsMap.get(czMap.get(obj.getString("id")).getString("powercorpid")).getString("mingzi"));
							float qimozhuangji = 0;
							qimozhuangji = czMap.get(obj.getString("id")).getFloat("zlfjxmcaps")+
										   czMap.get(obj.getString("id")).getFloat("clfjxmcaps")+
										   czMap.get(obj.getString("id")).getFloat("zlgfxmcaps")+
										   czMap.get(obj.getString("id")).getFloat("clgfxmcaps");
							if(arr.getJSONObject(q).getString("zbid").indexOf("qimozhuangji")>=0){
								obj.put("qimozhuangji_"+jtids[i], qimozhuangji);
							}
							if(arr.getJSONObject(q).getString("zbid").indexOf("zhuangjizhanbi")>=0){
								if(xhzhuangji!=0){
									BigDecimal bd = new BigDecimal(qimozhuangji/xhzhuangji * 100);
									obj.put("zhuangjizhanbi_"+jtids[i], bd.setScale(2,BigDecimal.ROUND_HALF_UP)+"%");
								}
							}
							if(arr.getJSONObject(q).getString("zbid").indexOf("liyongxiaoshi")>=0){
								float zfdl = obj.getFloatValue("ShiJiFDL_0");
								if(qimozhuangji!=0){
									BigDecimal bd = new BigDecimal(zfdl/qimozhuangji);
									obj.put("liyongxiaoshi_"+jtids[i], bd.setScale(2,BigDecimal.ROUND_HALF_UP));
								}
							}
							if(arr.getJSONObject(q).getString("zbid").indexOf("xiandianlv")>=0){
								float ShiJiFDL = obj.getFloatValue("ShiJiFDL_0");
								float XDLOSSDL = obj.getFloatValue("XDLOSSDL_0");
								if((XDLOSSDL+ShiJiFDL)!=0){
									BigDecimal bd = new BigDecimal(XDLOSSDL/(XDLOSSDL+ShiJiFDL));
									obj.put("xiandianlv_"+jtids[i], bd.setScale(2,BigDecimal.ROUND_HALF_UP));
								}
							}
							if(duibiaotype.equals("huanbi") || duibiaotype.equals("tongbi")){
								if(q==0){
									r1.add(obj);
								}else{
									r2.add(obj);
								}
							}
						}else{
							zbMap.put(obj.getString("id"), obj);
						}
					}
					if(pagename.equals("xihuaduibiao")){
						if(duibiaotype.equals("huanbi") || duibiaotype.equals("tongbi")){
							continue;
						}else{
							JSONObject obj = new JSONObject();
							obj.put("data", Lst);
							response.getWriter().print(obj);
							return;
						}
					}
					for(JSONObject obj : czLst){
						if(obj.getString("jigouid").equals(jtids[i])){
							float qimozhuangji = 0;
							qimozhuangji = obj.getFloatValue("zlfjxmcaps") + obj.getFloatValue("clfjxmcaps") 
									     + obj.getFloatValue("zlgfxmcaps") + obj.getFloatValue("clgfxmcaps");
							zongzhuangji += qimozhuangji;
							JSONObject o = new JSONObject();
							o.put("province", obj.getString("province"));
							o.put("qmzj_"+jtids[i], qimozhuangji);
							if(zbMap.containsKey(obj.getString("id"))){
								o.put("ShiJiFDL_"+jtids[i], zbMap.get(obj.getString("id")).getFloatValue("ShiJiFDL_0"));
								o.put("XDLOSSDL_"+jtids[i], zbMap.get(obj.getString("id")).getFloatValue("XDLOSSDL_0"));
							}else{
								o.put("ShiJiFDL_"+jtids[i], 0);
								o.put("XDLOSSDL_"+jtids[i], 0);
							}
							if(map.containsKey(obj.getString("province"))){
								map.get(obj.getString("province")).put("qmzj_"+jtids[i], map.get(obj.getString("province")).getFloatValue("qmzj_"+jtids[i])+qimozhuangji);
								map.get(obj.getString("province")).put("ShiJiFDL_"+jtids[i], map.get(obj.getString("province")).getFloatValue("ShiJiFDL_"+jtids[i])+o.getFloatValue("ShiJiFDL_"+jtids[i]));
								map.get(obj.getString("province")).put("XDLOSSDL_"+jtids[i], map.get(obj.getString("province")).getFloatValue("XDLOSSDL_"+jtids[i])+o.getFloatValue("XDLOSSDL_"+jtids[i]));
							}else{
						    	map.put(obj.getString("province"), o);
						    }
						}
					}
					for(int n = 0;n < province.length;n++){
						if(map.containsKey(province[n])){
							if(arr.getJSONObject(q).getString("zbid").indexOf("qimozhuangji")>=0){
								map.get(province[n]).put("qimozhuangji_"+jtids[i], map.get(province[n]).getFloatValue("qmzj_"+jtids[i]));				
							}
							if(arr.getJSONObject(q).getString("zbid").indexOf("zhuangjizhanbi")>=0){
								float value = 0;
								if(zongzhuangji!=0){
									value = (map.get(province[n]).getFloatValue("qmzj_"+jtids[i])/zongzhuangji)*100;
								}
								BigDecimal bd = new BigDecimal(value);
								map.get(province[n]).put("zhuangjizhanbi_"+jtids[i], bd.setScale(2,BigDecimal.ROUND_HALF_UP)+"%");				
							}
							if(arr.getJSONObject(q).getString("zbid").indexOf("liyongxiaoshi")>=0){
								float value = 0;
								if(map.get(province[n]).getFloatValue("qmzj_"+jtids[i])!=0){
									value = map.get(province[n]).getFloatValue("ShiJiFDL_"+jtids[i])/map.get(province[n]).getFloatValue("qmzj_"+jtids[i]);
								}
								BigDecimal bd = new BigDecimal(value);
								map.get(province[n]).put("liyongxiaoshi_"+jtids[i],  bd.setScale(2,BigDecimal.ROUND_HALF_UP));				
							}
							if(arr.getJSONObject(q).getString("zbid").indexOf("xiandianlv")>=0){
								float value = 0;
								if((map.get(province[n]).getFloatValue("XDLOSSDL_"+jtids[i])+map.get(province[n]).getFloatValue("ShiJiFDL_"+jtids[i]))!=0){
									value += (map.get(province[n]).getFloatValue("XDLOSSDL_"+jtids[i])/(map.get(province[n]).getFloatValue("XDLOSSDL_"+jtids[i])+map.get(province[n]).getFloatValue("ShiJiFDL_"+jtids[i])))*100;
								}
								BigDecimal bd = new BigDecimal(value);
								map.get(province[n]).put("xiandianlv_"+jtids[i],  bd.setScale(2,BigDecimal.ROUND_HALF_UP)+"%");				
							}
						}
					}
				}else{
					String sfid = FengchangDao.instance().getShengfenIdByMingzi(province[0]);
					//System.out.println(arr.getJSONObject(q).getString("zbid"));
					List<JSONObject> extData = null;
					if(duibiaotype.equals("qushi")){
						extData = FengchangDao.instance().findAllExtCZHisData(jtids[i], sfid, arr.getJSONObject(q).getString("zbid"));
						if(extData!=null && extData.size()!=0){
							for(JSONObject obj : extData){
								res.add(obj);
							}
						}
						
					}else{
						extData = FengchangDao.instance().findAllExtCZ(jtids[i], arr.getJSONObject(q).getString("startTime"), arr.getJSONObject(q).getString("endTime"));
					}
					if(extData!=null && extData.size()!=0){
						if(pagename.equals("xihuaduibiao")){
							Map<String, JSONObject> gs = BaseModelCache.getInstance().getExtGsMap();
							for(JSONObject obj : extData){
								xhzhuangji += obj.getFloatValue("capacity");
							}
							for(JSONObject obj : extData){
								obj.put("changzhanname", obj.getString("mingzi"));
								obj.put("czid", obj.getString("id"));
								obj.put("gongsiname", gs.get(jtids[i]).getString("mingzi"));
								if(arr.getJSONObject(q).getString("zbid").indexOf("qimozhuangji")>=0){
									obj.put("qimozhuangji_"+jtids[i], obj.getFloatValue("capacity"));
								}
								if(arr.getJSONObject(q).getString("zbid").indexOf("zhuangjizhanbi")>=0){
									if(xhzhuangji!=0){
										BigDecimal bd = new BigDecimal(obj.getFloatValue("capacity")/xhzhuangji * 100);
										obj.put("zhuangjizhanbi_"+jtids[i], bd.setScale(2,BigDecimal.ROUND_HALF_UP)+"%");
									}
								}
								if(arr.getJSONObject(q).getString("zbid").indexOf("liyongxiaoshi")>=0){
									float zfdl = obj.getFloatValue("fadianliang");
									if(obj.getFloatValue("capacity")!=0){
										BigDecimal bd = new BigDecimal(zfdl/obj.getFloatValue("capacity"));
										obj.put("liyongxiaoshi_"+jtids[i], bd.setScale(2,BigDecimal.ROUND_HALF_UP));
									}
								}
								if(arr.getJSONObject(q).getString("zbid").indexOf("xiandianlv")>=0){
									float ShiJiFDL = obj.getFloatValue("fadianliang");
									float XDLOSSDL = obj.getFloatValue("xiandianliang");
									if((XDLOSSDL+ShiJiFDL)!=0){
										BigDecimal bd = new BigDecimal(XDLOSSDL/(XDLOSSDL+ShiJiFDL));
										obj.put("xiandianlv_"+jtids[i], bd.setScale(2,BigDecimal.ROUND_HALF_UP));
									}
								}
							}
							JSONObject obj = new JSONObject();
							obj.put("data", extData);
							response.getWriter().print(obj);
							return;
							
						}
						for(JSONObject obj : extData){
							JSONObject o = new JSONObject();
							zongzhuangji += obj.getFloatValue("capacity");
							o.put("capacity_"+jtids[i], obj.getFloatValue("capacity"));
							o.put("fadianliang_"+jtids[i], obj.getFloatValue("fadianliang"));
							o.put("xiandianliang_"+jtids[i], obj.getFloatValue("xiandianliang"));
							if(map.containsKey(obj.getString("province"))){
								map.get(obj.getString("province")).put("capacity_"+jtids[i], map.get(obj.getString("province")).getFloatValue("capacity_"+jtids[i])+obj.getFloatValue("capacity"));
								map.get(obj.getString("province")).put("fadianliang_"+jtids[i], map.get(obj.getString("province")).getFloatValue("fadianliang_"+jtids[i])+o.getFloatValue("fadianliang"));
								map.get(obj.getString("province")).put("xiandianliang_"+jtids[i], map.get(obj.getString("province")).getFloatValue("xiandianliang_"+jtids[i])+o.getFloatValue("xiandianliang"));
							}else{
						    	map.put(obj.getString("province"), o);
						    }
						}
						for(int n = 0;n < province.length;n++){
							if(map.containsKey(province[n])){
								if(arr.getJSONObject(q).getString("zbid").indexOf("qimozhuangji")>=0){
									map.get(province[n]).put("qimozhuangji_"+jtids[i], map.get(province[n]).getFloatValue("capacity_"+jtids[i]));				
								}
								if(arr.getJSONObject(q).getString("zbid").indexOf("zhuangjizhanbi")>=0){
									float value = 0;
									if(zongzhuangji!=0){
										value = (map.get(province[n]).getFloatValue("capacity_"+jtids[i])/zongzhuangji)*100;
									}
									BigDecimal bd = new BigDecimal(value);
									map.get(province[n]).put("zhuangjizhanbi_"+jtids[i], bd.setScale(2,BigDecimal.ROUND_HALF_UP)+"%");				
								}
								if(arr.getJSONObject(q).getString("zbid").indexOf("liyongxiaoshi")>=0){
									float value = 0;
									if(map.get(province[n]).getFloatValue("capacity_"+jtids[i])!=0){
										value =  map.get(province[n]).getFloatValue("fadianliang_"+jtids[i])/map.get(province[n]).getFloatValue("capacity_"+jtids[i]);
									}
									BigDecimal bd = new BigDecimal(value);
									map.get(province[n]).put("liyongxiaoshi_"+jtids[i],bd.setScale(2,BigDecimal.ROUND_HALF_UP));				
								}
								if(arr.getJSONObject(q).getString("zbid").indexOf("xiandianlv")>=0){
									float value = 0;
									if((map.get(province[n]).getFloatValue("xiandianliang_"+jtids[i])+map.get(province[n]).getFloatValue("fadianliang_"+jtids[i]))!=0){
										value =  (map.get(province[n]).getFloatValue("xiandianliang_"+jtids[i])/(map.get(province[n]).getFloatValue("xiandianliang_"+jtids[i])+map.get(province[n]).getFloatValue("fadianliang_"+jtids[i])))*100;
									}
									BigDecimal bd = new BigDecimal(value);
									map.get(province[n]).put("xiandianlv_"+jtids[i], bd.setScale(2,BigDecimal.ROUND_HALF_UP)+"%");				
								}
							}
						}
					}
				}
				zongzhuangji = 0;
			}
			for(int n = 0;n < province.length;n++){
				if(map.containsKey(province[n])){
					if(duibiaotype.equals("huanbi") || duibiaotype.equals("tongbi")){
						if(q==1){
							r2.add(map.get(province[n]));//上次数据
						}else{
							r1.add(map.get(province[n]));//本次数据
						}
					}else{
						res.add(map.get(province[n]));
					}
				}
			}
		}
		if(duibiaotype.equals("huanbi") || duibiaotype.equals("tongbi")){
			String key = arr.getJSONObject(0).getString("zbid")+"_"+arr.getJSONObject(0).getString("jtid");
			if(r1.size()==0 || r2.size()==0){
				response.getWriter().print(res);
				return;
			}
			for(int n = 0;n < r1.size();n++){
				JSONArray r = new JSONArray();
				float a = 0;
				float b = 0;
				float value = 0;
				if(pagename.equals("xihuaduibiao")){
					r.add(0, r1.getJSONObject(n).getString("changzhanname"));
				}else{
					r.add(0, r1.getJSONObject(n).getString("province"));
				}
				if(r1.getJSONObject(n).getString(key).indexOf("%")>=0){
					a = Float.parseFloat(r1.getJSONObject(n).getString(key).replace("%",""));
				}else{
					a = r1.getJSONObject(n).getFloat(key);
				}
				if(r2.getJSONObject(n).getString(key).indexOf("%")>=0){
					b = Float.parseFloat(r2.getJSONObject(n).getString(key).replace("%",""));
				}else{
					b = r2.getJSONObject(n).getFloat(key);
				}
				if(b!=0){
					value = a-b;
				}
				BigDecimal bd = new BigDecimal(value);
				r.add(1, bd.setScale(2,BigDecimal.ROUND_HALF_UP));
				res.add(r);
			}
		}
		JSONObject obj = new JSONObject();
		obj.put("data", res);
		try {
			response.getWriter().print(obj);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("getWeekNum")
	public void getWeekNum(HttpServletRequest request, HttpServletResponse response) throws JSONException, Exception{
		String year = request.getParameter("year");
		
		try {
			int weeknum = ZhibiaoDao.instance().findWeenNum(year);
			response.getWriter().print(weeknum);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	* Java判断字符串数组中是否包含某字符串元素 
	* 
	* @param substring 某字符串 
	* @param source 数组 
	* @return 包含则返回true，否则返回false 
	*/ 
	public static boolean isIn(String substring, String source) { 
		if (source.equals("") || source.length() == 0) { 
			return false; 
		}else{
			if(substring==null){
				return true; 
			}
			String [] sources = source.split(",");
			for (int i = 0; i < sources.length; i++) { 
				String aSource = sources[i]; 
				if (aSource.equals(substring)) { 
					return true; 
				} 
			} 
			return false; 
		}
	}
}
