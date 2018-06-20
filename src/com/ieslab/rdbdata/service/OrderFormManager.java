package com.ieslab.rdbdata.service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ieslab.util.DateUtil;
import com.ieslab.util.IesLogger;

/**
 * 实时数据订单管理类
 * @author Li Hao
 */
public class OrderFormManager {

	private static OrderFormManager mgr = new OrderFormManager();
	
	//初始化订单存放map
	private static Map<String, OrderForm> orderFormMap = new ConcurrentHashMap<String, OrderForm>();

	//当前刷数据线程存放的map
	private static Map<String, Thread> threadMap = new ConcurrentHashMap<String, Thread>();
	
	public static OrderFormManager getInstance(){
		
		return mgr;
	}
	/**
	 * 当前是否存在
	 * @param code
	 * @return
	 */
	public boolean isExists(String code){
		return orderFormMap.containsKey(code); 
	}
	/**
	 * 获取订单
	 * @param code
	 * @return
	 */
	public OrderForm getOrderForm(String code){
		return orderFormMap.get(code); 
	}

	/**
	 * 更新当前访问时间
	 * @param code
	 */
	public void updateTime(String code){
		if(isExists(code)){
			synchronized (orderFormMap.get(code)) {
				orderFormMap.get(code).setUpdateTime(new Date());
			}
		}
	}
	/**
	 * 判断某个订单是否超时
	 * @param code
	 * @return
	 */
	public boolean isOverTime(String code){
		if(isExists(code)){
			Date lastTime = orderFormMap.get(code).getUpdateTime();
			Date currentTime = new Date();
			
			long res = DateUtil.getlongDiff(lastTime, currentTime);
			
			//判断res是否已经超过允许订单存活时间
			if(res > 1000 * 20){
				return true;
			}
		}
		return false;
	}
	/**
	 * 删除某订单，一般是超时后
	 * @param code
	 * @return
	 */
	public void remove(String code){
		if(isExists(code)){
			IesLogger.getInstance().info("检测到实时数据订单超时，删除该订单，code:" + code);
			//先停止刷数的线程
			threadMap.get(code).stop();
			threadMap.remove(code);
			//删除订单
			orderFormMap.remove(code);
		}
	}
	/**
	 * 新增一个订单
	 * @param order
	 */
	public void addOrderForm(OrderForm order){
		if(!isExists(order.getCode())){
			orderFormMap.put(order.getCode(), order);
			OrderFormThread runnable = new OrderFormThread(order);
			Thread thread = new Thread(runnable);
			thread.start();
			threadMap.put(order.getCode(), thread);
		}
	}
	/**
	 * 获取订单的集合map
	 * @return
	 */
	public Map<String, OrderForm> getOrderMap(){
		return orderFormMap;
	}
}
