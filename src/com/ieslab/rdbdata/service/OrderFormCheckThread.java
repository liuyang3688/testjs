package com.ieslab.rdbdata.service;

import java.util.Map;
import java.util.Set;

/**
 * 这个是订单检查类，检查每个订单的超时情况
 * @author Li Hao
 */
public class OrderFormCheckThread implements Runnable{

	@Override
	public void run() {
		while(true){
			try{
				Map<String, OrderForm> orderMap = OrderFormManager.getInstance().getOrderMap();
				if(orderMap != null){
					Set<String> keys = orderMap.keySet();
					if(keys != null && !keys.isEmpty()){
						for(String key: keys){
							//如果超时了
							if(OrderFormManager.getInstance().isOverTime(key)){
								OrderFormManager.getInstance().remove(key);
							}
						}
					}
				}
				
				try {
					Thread.sleep(20 * 1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					throw e;
				}
			}catch(Exception e){
				System.out.println("捕获到订单扫描线程报错，错误原因是：" + e.getMessage());
			}
			
		}
	}

}
