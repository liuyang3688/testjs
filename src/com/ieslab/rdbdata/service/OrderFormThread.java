package com.ieslab.rdbdata.service;

import com.alibaba.fastjson.JSONArray;
import com.ieslab.rdbdata.RdbwebservicePortType;
import com.ieslab.rdbdata.Rdbwebservice_ServiceLocator;
import com.ieslab.util.IesLogger;

/**
 * 这个是订单数据请求类，定期去实时服务取数据
 * @author Li Hao
 */
public class OrderFormThread implements Runnable{

	private OrderForm orderForm = null;
	
	public OrderFormThread(OrderForm order){
		orderForm = order;
	}
	
	
	@Override
	public void run() {
		// 在这个线程里头，定时更新订单的数据
		Rdbwebservice_ServiceLocator loc = new Rdbwebservice_ServiceLocator();
		while(true){
			try {
				RdbwebservicePortType service = loc.getrdbwebservice();
				String res = service.getRealData(orderForm.getSdsStr());
			
				JSONArray arr = JSONArray.parseArray(res);
				orderForm.setSdsArr(arr);
				
			}catch(Exception e){
				IesLogger.getInstance().error("刷新订单数据线程出错，错误原因：" + e.getMessage());
			}
			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
