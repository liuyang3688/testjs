package com.ieslab.rdbdata.service_new;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ieslab.util.DateUtil;

/**
 * 三段式管理类
 * 
 * 这个类存储若干个map，每个map的key值是bjlxid_bjid_csid，value值是三段式的ParamInfo 逐步实现增删改查
 * 
 * @author Li Hao
 * 
 */
public class ParamManager {

	//定义一个list，存储map
	private static List<Map<String, ParamInfo>> mapList = Collections.synchronizedList(new ArrayList<Map<String, ParamInfo>>());

	// 定义每个map的长度
	public static final int length = 500;
	
	//定义每个三段式对象的存活时间，一分钟
	public static final int aliveTime = 1000 * 60;
	
	//定义检测三段式是否超时线程的循环时间
	public static final int checkThreadTime = 1000 * 60;

	private static ParamManager mgr = null;

	/**
	 * 单例实现
	 * 
	 * @return
	 */
	public static ParamManager getInstance() {
		if (mgr == null) {
			mgr = new ParamManager();
		}
		return mgr;
	}
	/**
	 * 判断某个三段式是否存在于当前的map中
	 * @param info
	 * @return true：已经存在 false:未存在
	 */
	public boolean isExists(ParamInfo info){
		String sdsKey = getParamKey(info);
		if(mapList != null){
			for(int i = 0; i < mapList.size(); i++){
				Map<String, ParamInfo> map = mapList.get(i);
				if(map.containsKey(sdsKey)){
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * 判断当前的list中的map是否已经满了
	 * @return 可插入新成员的map
	 */
	public Map<String, ParamInfo> isFullMap(){
		
		//遍历整个list，查看是否有不到满员的map，如果有返回这个map。
		for(int i = 0; i < mapList.size(); i++){
			Map<String, ParamInfo> map = mapList.get(i);
			if(map.size() < length){
				return map;
			}
		}
		return null;
	}
	/**
	 * 通过三段式对象或者这个对象的三段式key值用于map的key
	 * @param info
	 * @return
	 */
	public String getParamKey(ParamInfo info){
		String sdsKey = info.getBjlxid() + "," + info.getCsid() + "," + info.getBjid();
		return sdsKey;
	}
	/**
	 * 添加三段式
	 * @param info
	 */
	public void addParam(ParamInfo info){
		//当前的map中不存在这个三段式
		if(!isExists(info)){
			String sdsKey = getParamKey(info);
			//找到这个可以插入新三段式的map
			Map<String, ParamInfo> map = isFullMap();
			if(map == null){//如果这个map是空的，初始化一个新的map，加入list
				map = new ConcurrentHashMap<String, ParamInfo>();
				mapList.add(map);
				
				//新建一个新的map的时候需要启动一个刷新数据的线程
				ParamThread run = new ParamThread(map);
				Thread thread = new Thread(run);
				thread.start();
			}
			map.put(sdsKey, info);
		}
	}
	/**
	 * 通过一个三段式的key获取对应的对象
	 * @param sdsStr
	 * @return
	 */
	public ParamInfo getParam(String sdsStr){
		for(int i = 0; i < mapList.size(); i++){
			Map<String, ParamInfo> map = mapList.get(i);
			if(map.containsKey(sdsStr)){
				ParamInfo info =  map.get(sdsStr);
				info.setUpdateTime(new Date());
				return info;
			}
		}
		return null;
	}
	/**
	 * 批量加入三段式
	 * @param sdsStr
	 */
	public void addParams(String sdsStr){
		if(sdsStr != null && sdsStr.length() > 0){
			String sdsArr[] = sdsStr.split(";");
			for(String temp: sdsArr){
				String tempArr[] = temp.split(",");
				if(tempArr.length == 3){
					ParamInfo info = new ParamInfo();
					info.setBjlxid(tempArr[0]);
					info.setCsid(tempArr[1]);
					info.setBjid(tempArr[2]);
					info.setValue("0");
					info.setUpdateTime(new Date());
					addParam(info);
				}
			}
		}
	}
	/**
	 * 判断某个三段式是否已经超时
	 * @param info
	 * @return
	 */
	public boolean isTimeOut(ParamInfo info){
		if(info != null){
			long time = DateUtil.getlongDiff(info.getUpdateTime(), new Date());
			if(time > aliveTime){
				return true;
			}
		}
		return false;
	}
	/**
	 * 获取list
	 * @return
	 */
	public List<Map<String, ParamInfo>> getDataList(){
		return mapList;
	}
}
