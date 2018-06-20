package com.ieslab.base.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.shiro.SecurityUtils;

import com.alibaba.fastjson.JSONObject;
import com.ieslab.base.dao.BaseTableDao;
import com.ieslab.base.dao.ChangjiaDao;
import com.ieslab.base.dao.FengchangDao;
import com.ieslab.base.dao.FengjiDao;
import com.ieslab.base.dao.FengjicanshuDao;
import com.ieslab.base.dao.FenzigongsiDao;
import com.ieslab.base.dao.GongsiDao;
import com.ieslab.base.dao.JiDianXianDao;
import com.ieslab.base.dao.KaiGuanDao;
import com.ieslab.base.dao.XiangmuDao;
import com.ieslab.base.dao.XinghaoDao;
import com.ieslab.base.dao.YuanShiCodeDao;
import com.ieslab.base.dao.ZhibiaoDao;
import com.ieslab.base.model.Fjguzhangma;
import com.ieslab.base.model.Gongsi;


public class BaseModelCache extends Object implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private static BaseModelCache _instance = null;
    
    /**
     * 创建一个公司list的缓存容器。
     */
    private List<Gongsi> jituanList = new ArrayList<Gongsi>();
    private List<JSONObject> gongsiList = new ArrayList<JSONObject>();
    private List<JSONObject> czList = new ArrayList<JSONObject>();
    private List<JSONObject> fengjiList = new ArrayList<JSONObject>();
    private List<JSONObject> xinghaoList = new ArrayList<JSONObject>();
    private List<JSONObject> xiangmuList = new ArrayList<JSONObject>();
    private List<JSONObject> jiDianXianList = new ArrayList<JSONObject>();
    private List<Fjguzhangma> guzhangmaList = new ArrayList<Fjguzhangma>();
    private List<JSONObject> kaiguanList = new ArrayList<JSONObject>();
    private List<JSONObject> changjiaList = new ArrayList<JSONObject>();
	private List<JSONObject> zhibiaoList = new ArrayList<JSONObject>();
	private List<JSONObject> bujiancanshu = new ArrayList<JSONObject>();
	private List<JSONObject> extczList = new ArrayList<JSONObject>();
	private List<JSONObject> extgsList = new ArrayList<JSONObject>();
	private List<JSONObject> extjtList = new ArrayList<JSONObject>();
	
	/**
     * map信息
     */
    //厂站项目关联map,key：czid value:项目List
    private Map<String, List<JSONObject>> czXiangmuMap = new ConcurrentHashMap<String, List<JSONObject>>();
    
    //厂站集电线关联map,key：czid value:集电线List
    private Map<String, List<JSONObject>> czJiDianXianMap = new ConcurrentHashMap<String, List<JSONObject>>();
    //项目集电线关联map,key：xmid value:集电线List
    private Map<String, List<JSONObject>> xmJiDianXianMap = new ConcurrentHashMap<String, List<JSONObject>>();
    //部件类型map, key:bjtype, value: 故障码list
    private Map<String, List<Fjguzhangma>> fjguzhangmaMap = new ConcurrentHashMap<String, List<Fjguzhangma>>();
    
    private Map<String, Fjguzhangma> gzmFjTypeCodeMap = new ConcurrentHashMap<String, Fjguzhangma>();
    private Map<String, Fjguzhangma> gzmALARMCodeMap = new ConcurrentHashMap<String, Fjguzhangma>();
	private Map<String, JSONObject> gzmALLFJGZZL = new ConcurrentHashMap<String, JSONObject>();
    
    //集团map
    private Map<String, Gongsi> jtMap = new ConcurrentHashMap<String, Gongsi>();
    //厂站map，key:czid,value:czObj
    private Map<String, JSONObject> gsMap = new ConcurrentHashMap<String, JSONObject>();
    //项目map，key:xmid,value:xmobj
    private Map<String, JSONObject> xmMap = new ConcurrentHashMap<String, JSONObject>();
    //厂站map，key:czid,value:czObj
    private Map<String, JSONObject> czMap = new ConcurrentHashMap<String, JSONObject>();
    //风机map，key:fjid,value:fjObj
    private Map<String, JSONObject> fjMap = new ConcurrentHashMap<String, JSONObject>();
    //开关map，key:fjid,value:fjObj
    private Map<String, JSONObject> kgMap = new ConcurrentHashMap<String, JSONObject>();
    //厂家map，key:fjid,value:fjObj
    private Map<String, JSONObject> cjMap = new ConcurrentHashMap<String, JSONObject>();
    //指标map，key:zhibiaoid，value:zbObj
    private Map<String, JSONObject> zbMap = new ConcurrentHashMap<String, JSONObject>();
    //型号map，key:zhibiaoid，value:zbObj
    private Map<String, JSONObject> xhMap = new ConcurrentHashMap<String, JSONObject>();
    //部件参数map，key:zhibiaoid，value:zbObj
    private Map<String, JSONObject> bjcsMap = new ConcurrentHashMap<String, JSONObject>();
    //部件类型map, key:bjtype, value: bjObj
    private Map<String, Map<String, JSONObject>> bjTypeMap = new ConcurrentHashMap<String, Map<String,JSONObject>>();
    private List<JSONObject> bjTypeList = new ArrayList<JSONObject>();
    
    private Map<String, JSONObject> ExtCzMap = new ConcurrentHashMap<String, JSONObject>();
    private Map<String, JSONObject> ExtGsMap = new ConcurrentHashMap<String, JSONObject>();
    private Map<String, JSONObject> ExtJtMap = new ConcurrentHashMap<String, JSONObject>();

    private Map<String, String> fjStateCodeMap = new ConcurrentHashMap<String, String>();
    private Map<String, String> yuanshiCodeMap = new ConcurrentHashMap<String, String>();

    // 厂站风机Map key:czid,value:fjObj
    private Map< Integer, List<JSONObject>> czFJMap = new ConcurrentHashMap<Integer, List<JSONObject>>();
    
	BaseModelCacheService baseModelCacheService = null;
    
    //域类型信息
    private List<JSONObject> areaTypeList = new ArrayList<JSONObject>();
    Map<String, JSONObject> areaType = new ConcurrentHashMap<String, JSONObject>();
    
    private BaseModelCache() {
    	//后续需要改成从配置文件读取
    	//serverCacheMax = 10000;
    	baseModelCacheService = new BaseModelCacheService();
    }

	/**
	 * 外部调用的初始化方法 获得唯一实例
	 * @return
	 */
    public static BaseModelCache getInstance(){
		if(_instance == null){
			synchronized(BaseModelCache.class){
				if(_instance == null){
					_instance = new BaseModelCache();
				}
			}			
		}
		return _instance;
	}
    
    /**
	 * 查询数据库更新缓存
	 */
	public void updataCahce(){
		jituanList = GongsiDao.instance().findAll();
		if(jituanList != null){
			jtMap.clear();
			for(Gongsi obj: jituanList){
				jtMap.put(obj.getID()+"", obj);
			}
		}
		
		//初始化风场
		czList = FengchangDao.instance().findAllCZ();
		if(czList != null){
			czMap.clear();
			for(JSONObject obj: czList){
				czMap.put(obj.getString("id"), obj);
			}
		}
		
//		//初始化风场
//		bujiancanshu = FengjicanshuDao.instance().findAllbjcs();
//		if(bujiancanshu != null){
//			bjcsMap.clear();
//			for(JSONObject obj: bujiancanshu){
//				bjcsMap.put(obj.getString("id"), obj);
//			}
//		}
//		
		//初始化风机
		fengjiList = FengjiDao.instance().findAll(null);
		if(fengjiList != null){
			fjMap.clear();
			for(JSONObject obj: fengjiList){
				fjMap.put(obj.getString("id"), obj);
			}
		}
//		
//		changjiaList = ChangjiaDao.instance().findAll();
//		if(changjiaList != null){
//			cjMap.clear();
//			for(JSONObject obj: changjiaList){
//				cjMap.put(obj.getString("id"), obj);
//			}
//		}
//		
//		
//		xinghaoList = XinghaoDao.instance().findAllXingHao();
//		if(xinghaoList != null){
//			xhMap.clear();	
//			for(JSONObject obj: xinghaoList){
//				xhMap.put(obj.getString("id"), obj);
//			}
//		}
//		xiangmuList = XiangmuDao.instance().findAll();
//		
//		if(xiangmuList != null){
//			czXiangmuMap.clear();
//			xmMap.clear();
//			for(JSONObject obj: xiangmuList){
//				if(czXiangmuMap.containsKey(obj.getString("changzhanid"))){
//					String ss = obj.getString("changzhanid");
//					czXiangmuMap.get(ss).add(obj);
//					
//				}else{
//					List<JSONObject> list = new ArrayList<JSONObject>();
//					list.add(obj);
//					czXiangmuMap.put(obj.getString("changzhanid"), list);
//				}
//				xmMap.put(obj.getString("id"), obj);
//			}
//		}
//		
//		//集电线
//		jiDianXianList = JiDianXianDao.instance().findAll();
//		if(jiDianXianList != null){
//			czJiDianXianMap.clear();
//			xmJiDianXianMap.clear();
//			for(JSONObject obj: jiDianXianList){
//				//加入厂站集电线map
//				if(czJiDianXianMap.containsKey(obj.getString("changzhanid"))){
//					czJiDianXianMap.get(obj.getString("changzhanid")).add(obj);
//				}else{
//					List<JSONObject> list = new ArrayList<JSONObject>();
//					list.add(obj);
//					czJiDianXianMap.put(obj.getString("changzhanid"), list);
//				}
//				//加入项目集电线map
//				if(xmJiDianXianMap.containsKey(obj.getString("xiangmuid"))){
//					xmJiDianXianMap.get(obj.getString("xiangmuid")).add(obj);
//				}else{
//					List<JSONObject> list = new ArrayList<JSONObject>();
//					list.add(obj);
//					xmJiDianXianMap.put(obj.getString("xiangmuid"), list);
//				}
//			}
//		}
//		
//		//风机故障码
//		guzhangmaList = FengjiDao.instance().findAllFJGZM();
//		if(guzhangmaList != null){
//			gzmALARMCodeMap.clear();
//			gzmFjTypeCodeMap.clear();
//			fjguzhangmaMap.clear();
//			for(Fjguzhangma gzm: guzhangmaList){
//				gzmALARMCodeMap.put(gzm.getAlarmcode()+"", gzm);
//				gzmFjTypeCodeMap.put(gzm.getXinghaoid() + "_" + gzm.getAlarmcode(), gzm);
//				
//				if(fjguzhangmaMap.containsKey(Integer.toString(gzm.getId()))){
//					fjguzhangmaMap.get(Integer.toString(gzm.getId())).add(gzm);
//				}else{
//					List<Fjguzhangma> list = new ArrayList<Fjguzhangma>();
//					list.add(gzm);
//					fjguzhangmaMap.put(Integer.toString(gzm.getId()), list);
//				}
//			}
//		}
//		List<JSONObject> gzzlObj = FengjiDao.instance().findAllGZZL();
//		gzmALLFJGZZL.clear();
//		for(JSONObject obj : gzzlObj){			
//			if(!gzmALLFJGZZL.containsKey(obj.getString("id"))){
//				gzmALLFJGZZL.put(obj.getString("id"), obj);
//			}
//		}
//			
//		
//		//获取部件类型
//		bjTypeList = BaseTableDao.instance().getBjlxTable();
//		
//		//域类型
//		areaTypeList = BaseTableDao.instance().getAreaTypeInfo();
//		if(areaTypeList != null){
//			areaType.clear();
//			for(JSONObject obj: areaTypeList){
//				String key = obj.getString("changzhanid") + "_" + obj.getString("fieldid");
//				if(!areaType.containsKey(key)){
//					areaType.put(key, obj);
//				}
//			}
//			
//		}
//		//初始化指标
//		zhibiaoList = ZhibiaoDao.instance().findAllZhibiao();
//		if(zhibiaoList != null){
//			zbMap.clear();
//			for(JSONObject obj: zhibiaoList){
//				zbMap.put(obj.getString("ziduanming")+"-"+obj.getString("bjlx"), obj);
//			}
//		}
//		if(areaTypeList != null){
//			areaType.clear();
//			for(JSONObject obj: areaTypeList){
//				String key = obj.getString("changzhanid") + "_" + obj.getString("fieldid");
//				if(!areaType.containsKey(key)){
//					areaType.put(key, obj);
//				}
//			}
//			
//		}
//		//开关
//		kaiguanList = KaiGuanDao.instance().getAllkaiguan();
//		if(fengjiList != null){
//			kgMap.clear();
//			for(JSONObject obj: kaiguanList){
//				kgMap.put(obj.getString("id"), obj);
//			}
//		}
//		
//		/*extczList = ExtDao.instance().getALLExtChangZhan();
//		if(extczList != null){
//			ExtCzMap.clear();
//			for(JSONObject obj: extczList){
//				ExtCzMap.put(obj.getString("id"), obj);	
//			}
//		}
//		
//		extgsList = ExtDao.instance().getALLExtCompany();
//		if(extgsList != null){
//			ExtGsMap.clear();
//			for(JSONObject obj: extgsList){
//				ExtGsMap.put(obj.getString("id"), obj);
//			}
//		}*/
//		
//		/*extjtList = ExtDao.instance().getALLExtJiTuan();
//		if(extjtList != null){
//			for(JSONObject obj: extjtList){
//				ExtJtMap.put(obj.getString("mingzi"), obj);
//			}
//		}*/
//		//初始化风机状态码map，写死
//		fjStateCodeMap.put("1", "风机待风");
//		fjStateCodeMap.put("101", "正常发电");
//		fjStateCodeMap.put("102", "限电降出力");
//		fjStateCodeMap.put("103", "缺陷降出力");
//		fjStateCodeMap.put("104", "不明降出力");
//		
//		fjStateCodeMap.put("201", "限电停机");
//		fjStateCodeMap.put("202", "故障停机");
//		fjStateCodeMap.put("203", "手动停机");
//		fjStateCodeMap.put("204", "不明停机");
//		
//		fjStateCodeMap.put("301", "限电维护");
//		fjStateCodeMap.put("302", "故障维护");
//		fjStateCodeMap.put("303", "主动维护");
//		fjStateCodeMap.put("304", "不明维护");
//		
//		fjStateCodeMap.put("401", "大风受累");
//		fjStateCodeMap.put("402", "高温受累");
//		fjStateCodeMap.put("403", "低温受累");
//		fjStateCodeMap.put("404", "雷电受累");
//		fjStateCodeMap.put("405", "冰冻受累");
//		
//		fjStateCodeMap.put("501", "未知停机");
//		fjStateCodeMap.put("601", "采集中断");
//		fjStateCodeMap.put("602", "内部中断");
//		
//		//初始化原始状态码map
//		yuanshiCodeMap = YuanShiCodeDao.instance().findAll();
	}
    
	public Map<String, Fjguzhangma> getGzmALARMCodeMap() {
		return gzmALARMCodeMap;
	}

	public void setGzmALARMCodeMap(Map<String, Fjguzhangma> gzmALARMCodeMap) {
		this.gzmALARMCodeMap = gzmALARMCodeMap;
	}

	public List<Fjguzhangma> getGuzhangmaList() {
		return guzhangmaList;
	}

	public void setGuzhangmaList(List<Fjguzhangma> guzhangmaList) {
		this.guzhangmaList = guzhangmaList;
	}

	public Map<String, List<Fjguzhangma>> getFjguzhangmaMap() {
		return fjguzhangmaMap;
	}

	public void setFjguzhangmaMap(Map<String, List<Fjguzhangma>> fjguzhangmaMap) {
		this.fjguzhangmaMap = fjguzhangmaMap;
	}

	//通过部件类型和部件ID获取某个部件
    public JSONObject getBjObjByBjTypeAndBjId(String bjtype, String bjid){
    	
    	return baseModelCacheService.getBaseObject(bjtype, bjid);
    	
    }
    
    /**
     * 取缓存里面的数据的方法
     * @return
     */
    public float getDWCapacity()
    {
    	if(jituanList.size() == 0)
    		return 0;
    	return jituanList.get(0).getCLFJCaps();
    }
	public List<JSONObject> getGongsiList(){
		
		//判断当前已经登录
		if(SecurityUtils.getSubject().isAuthenticated()){
			String gsid = (String) SecurityUtils.getSubject().getSession().getAttribute("gsid");
			String czid = (String) SecurityUtils.getSubject().getSession().getAttribute("czid");
			if("0".equals(gsid)){
				return gongsiList;
			}else{
				List<JSONObject> t_list = new ArrayList<JSONObject>();
				for(JSONObject obj: gongsiList){
					if(obj.getString("id").equals(gsid)){
						t_list.add(obj);
					}
				}
				return t_list;
			}
		}
		return gongsiList;
	}
	
	public void setGongsiList(List<JSONObject> gongsiList) {
		this.gongsiList = gongsiList;
	}
	 //CZNamemap，key:czid，value:String
    private Map<Integer, String> czNameMap = new ConcurrentHashMap<Integer, String>();
	public String getCZName(int czid){
		if(!czNameMap.containsKey(czid))
		{
			for(JSONObject obj : czList)
			{
				if(obj.getString("id").equals(czid+""))
				{
					czNameMap.put(czid, obj.getString("mingzi"));
				}
			}
		}
		if(!czNameMap.containsKey(czid))
			return "未知";
		else
			return czNameMap.get(czid);
	}
	private Map<Integer, Float> czCapsMap = new ConcurrentHashMap<Integer, Float>();
	public float getCZCapacity(int czid)
	{
		if(!czCapsMap.containsKey(czid))
		{
			for(JSONObject obj : czList)
			{
				if(obj.getString("id").equals(czid+""))
				{
					czCapsMap.put(czid, obj.getFloat("clfjxmcaps"));
				}
			}
		}
		if(!czCapsMap.containsKey(czid))
			return 0.0f;
		else
			return czCapsMap.get(czid);
	}
	
	public List<JSONObject> getCZAll(){
		return czList;
	}
	
	public String getCZNameById(int id)
	{
		return "";
	}
	
	public List<JSONObject> getStaList() {
		//判断当前已经登录
		//if(SecurityUtils.getSubject().isAuthenticated()){
		String gsid = (String) SecurityUtils.getSubject().getSession().getAttribute("gsid");
		String czid = (String) SecurityUtils.getSubject().getSession().getAttribute("czid");
		if("0".equals(czid)){
			return czList;
		}else{
			List<JSONObject> t_list = new ArrayList<JSONObject>();
			for(JSONObject obj: czList){
				if(("," + czid + ",").indexOf("," + obj.getString("id") + ",")>=0){
					t_list.add(obj);
				}
			}
			return t_list;
		}
	}

	public void setFengchang(List<JSONObject> fengchang) {
		this.czList = fengchang;
	}

	public List<JSONObject> getFengjiList() {
		//判断当前已经登录
		if(SecurityUtils.getSubject().isAuthenticated()){
			String gsid = (String) SecurityUtils.getSubject().getSession().getAttribute("gsid");
			String czid = (String) SecurityUtils.getSubject().getSession().getAttribute("czid");
			if("0".equals(czid)){
				return fengjiList;
			}else{
				List<JSONObject> t_list = new ArrayList<JSONObject>();
				for(JSONObject obj: fengjiList){
					if(("," + czid + ",").indexOf("," + obj.getString("changzhanid") + ",")>=0){
						t_list.add(obj);
					}
				}
				return t_list;
			}
		}
		return fengjiList;
	}
	
	public List<JSONObject> getFengjiList(int czid) {
		if(czFJMap.containsKey(czid))
		{
			return czFJMap.get(czid);
		}
		else
		{
			if("0".equals(czid)){
				return fengjiList;
			}else{
				List<JSONObject> t_list = new ArrayList<JSONObject>();
				for(JSONObject obj: fengjiList){
					if(obj.getString("changzhanid").equals(czid+"")){
						t_list.add(obj);
					}
				}
				czFJMap.put(czid, t_list);
				return t_list;
			}
		}
	}

	public void setFengjiList(List<JSONObject> fengjiList) {
		this.fengjiList = fengjiList;
	}

	public List<JSONObject> getXinghaoList() {
		return xinghaoList;
	}

	public void setXinghaoList(List<JSONObject> xinghaoList) {
		this.xinghaoList = xinghaoList;
	}

	public List<JSONObject> getXiangmuList() {
		if(SecurityUtils.getSubject().isAuthenticated()){
			String gsid = (String) SecurityUtils.getSubject().getSession().getAttribute("gsid");
			String czid = (String) SecurityUtils.getSubject().getSession().getAttribute("czid");
			if("0".equals(czid)){
				return xiangmuList;
			}else{
				List<JSONObject> t_list = new ArrayList<JSONObject>();
				for(JSONObject obj: xiangmuList){
					if(("," + czid + ",").indexOf("," + obj.getString("changzhanid") + ",")>=0){
						t_list.add(obj);
					}
				}
				return t_list;
			}
		}
		return xiangmuList;
	}

	public void setXiangmuList(List<JSONObject> xiangmuList) {
		this.xiangmuList = xiangmuList;
	}

	public Map<String, List<JSONObject>> getCzXiangmuMap() {
		return czXiangmuMap;
	}

	public void setCzXiangmuMap(Map<String, List<JSONObject>> czXiangmuMap) {
		this.czXiangmuMap = czXiangmuMap;
	}

	public List<JSONObject> getJiDianXianList() {
		if(SecurityUtils.getSubject().isAuthenticated()){
			String gsid = (String) SecurityUtils.getSubject().getSession().getAttribute("gsid");
			String czid = (String) SecurityUtils.getSubject().getSession().getAttribute("czid");
			if("0".equals(czid)){
				return jiDianXianList;
			}else{
				List<JSONObject> t_list = new ArrayList<JSONObject>();
				for(JSONObject obj: jiDianXianList){
					if(("," + czid + ",").indexOf("," + obj.getString("changzhanid") + ",")>=0){
						t_list.add(obj);
					}
				}
				return t_list;
			}
		}
		return jiDianXianList;
	}

	public void setJiDianXianList(List<JSONObject> jiDianXianList) {
		this.jiDianXianList = jiDianXianList;
	}

	public Map<String, List<JSONObject>> getCzJiDianXianMap() {
		return czJiDianXianMap;
	}

	public void setCzJiDianXianMap(Map<String, List<JSONObject>> czJiDianXianMap) {
		this.czJiDianXianMap = czJiDianXianMap;
	}

	public Map<String, List<JSONObject>> getXmJiDianXianMap() {
		return xmJiDianXianMap;
	}
 
	public void setXmJiDianXianMap(Map<String, List<JSONObject>> xmJiDianXianMap) {
		this.xmJiDianXianMap = xmJiDianXianMap;
	}

	public Map<String, JSONObject> getCzMap() {
		return czMap;
	}

	public void setCzMap(Map<String, JSONObject> czMap) {
		this.czMap = czMap;
	}

	public Map<String, JSONObject> getFjMap() {
		return fjMap;
	}

	public void setFjMap(Map<String, JSONObject> fjMap) {
		this.fjMap = fjMap;
	}

	public Map<String, Map<String, JSONObject>> getBjTypeMap() {
		return bjTypeMap;
	}

	public void setBjTypeMap(Map<String, Map<String, JSONObject>> bjTypeMap) {
		this.bjTypeMap = bjTypeMap;
	}

	public Map<String, JSONObject> getGsMap() {
		return gsMap;
	}

	public void setGsMap(Map<String, JSONObject> gsMap) {
		this.gsMap = gsMap;
	}

	public Map<String, JSONObject> getXmMap() {
		return xmMap;
	}

	public void setXmMap(Map<String, JSONObject> xmMap) {
		this.xmMap = xmMap;
	}
	
	public List<JSONObject> getBjTypeList() {
		return bjTypeList;
	}

	public void setBjTypeList(List<JSONObject> bjTypeList) {
		this.bjTypeList = bjTypeList;
	}

	public List<JSONObject> getAreaTypeList() {
		return areaTypeList;
	}

	public void setAreaTypeList(List<JSONObject> areaTypeList) {
		this.areaTypeList = areaTypeList;
	}

	public Map<String, JSONObject> getAreaType() {
		return areaType;
	}

	public void setAreaType(Map<String, JSONObject> areaType) {
		this.areaType = areaType;
	}
	public List<JSONObject> getChangjiaList() {
		return changjiaList;
	}

	public void setChangjiaList(List<JSONObject> changjiaList) {
		this.changjiaList = changjiaList;
	}

	public Map<String, JSONObject> getCjMap() {
		return cjMap;
	}
	public List<JSONObject> getKaiguanList() {
		if(SecurityUtils.getSubject().isAuthenticated()){
			String gsid = (String) SecurityUtils.getSubject().getSession().getAttribute("gsid");
			String czid = (String) SecurityUtils.getSubject().getSession().getAttribute("czid");
			if("0".equals(czid)){
				return kaiguanList;
			}else{
				List<JSONObject> t_list = new ArrayList<JSONObject>();
				for(JSONObject obj: kaiguanList){
					if(("," + czid + ",").indexOf("," + obj.getString("changzhanid") + ",")>=0){
						t_list.add(obj);
					}
				}
				return t_list;
			}
		}
		return kaiguanList;
	}

	public void setKaiguanList(List<JSONObject> kaiguanList) {
		this.kaiguanList = kaiguanList;
	}

	public Map<String, JSONObject> getKgMap() {
		return kgMap;
	}

	public void setKgMap(Map<String, JSONObject> kgMap) {
		this.kgMap = kgMap;
	}
	public void setCjMap(Map<String, JSONObject> cjMap) {
		this.cjMap = cjMap;
	}

	public List<JSONObject> getZhibiaoList() {
		return zhibiaoList;
	}

	public void setZhibiaoList(List<JSONObject> zhibiaoList) {
		this.zhibiaoList = zhibiaoList;
	}

	public Map<String, JSONObject> getZbMap() {
		return zbMap;
	}

	public void setZbMap(Map<String, JSONObject> zbMap) {
		this.zbMap = zbMap;
	}

	public Map<String, JSONObject> getXhMap() {
		return xhMap;
	}

	public void setXhMap(Map<String, JSONObject> xhMap) {
		this.xhMap = xhMap;
	}

	public List<JSONObject> getBujiancanshu() {
		return bujiancanshu;
	}

	public void setBujiancanshu(List<JSONObject> bujiancanshu) {
		this.bujiancanshu = bujiancanshu;
	}

	public Map<String, JSONObject> getBjcsMap() {
		return bjcsMap;
	}

	public void setBjcsMap(Map<String, JSONObject> bjcsMap) {
		this.bjcsMap = bjcsMap;
	}

	public Map<String, Fjguzhangma> getGzmFjTypeCodeMap() {
		return gzmFjTypeCodeMap;
	}

	public void setGzmFjTypeCodeMap(Map<String, Fjguzhangma> gzmFjTypeCodeMap) {
		this.gzmFjTypeCodeMap = gzmFjTypeCodeMap;
	}
	public Map<String, JSONObject> getExtCzMap() {
		return ExtCzMap;
	}

	public void setExtCzMap(Map<String, JSONObject> extCzMap) {
		ExtCzMap = extCzMap;
	}

	public Map<String, JSONObject> getExtGsMap() {
		return ExtGsMap;
	}

	public void setExtGsMap(Map<String, JSONObject> extGsMap) {
		ExtGsMap = extGsMap;
	}

	public Map<String, JSONObject> getExtJtMap() {
		return ExtJtMap;
	}

	public void setExtJtMap(Map<String, JSONObject> extJtMap) {
		ExtJtMap = extJtMap;
	}
	public List<JSONObject> getExtczList() {
		return extczList;
	}

	public void setExtczList(List<JSONObject> extczList) {
		this.extczList = extczList;
	}

	public List<JSONObject> getExtgsList() {
		return extgsList;
	}

	public void setExtgsList(List<JSONObject> extgsList) {
		this.extgsList = extgsList;
	}

	public List<JSONObject> getExtjtList() {
		return extjtList;
	}

	public void setExtjtList(List<JSONObject> extjtList) {
		this.extjtList = extjtList;
	}
	
	public List<Gongsi> getJituanList() {
		return jituanList;
	}

	public void setJituanList(List<Gongsi> jituanList) {
		this.jituanList = jituanList;
	}

	public Map<String, Gongsi> getJtMap() {
		return jtMap;
	}

	public void setJtMap(Map<String, Gongsi> jtMap) {
		this.jtMap = jtMap;
	}

	public Map<String, String> getFjStateCodeMap() {
		return fjStateCodeMap;
	}

	public void setFjStateCodeMap(Map<String, String> fjStateCodeMap) {
		this.fjStateCodeMap = fjStateCodeMap;
	}

	public Map<String, String> getYuanshiCodeMap() {
		return yuanshiCodeMap;
	}

	public void setYuanshiCodeMap(Map<String, String> yuanshiCodeMap) {
		this.yuanshiCodeMap = yuanshiCodeMap;
	}
    public Map<String, JSONObject> getGzmALLFJGZZL() {
		return gzmALLFJGZZL;
	}

	public void setGzmALLFJGZZL(Map<String, JSONObject> gzmALLFJGZZL) {
		this.gzmALLFJGZZL = gzmALLFJGZZL;
	}
}
