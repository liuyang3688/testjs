package com.ieslab.rdbdata.service_new;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 检测三段式对象是否超时
 * @author Li Hao
 *
 */
public class ParamCheckThread implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try{
				System.out.println("检测线程开始检测。。。");
				List<Map<String, ParamInfo>> mapList = ParamManager.getInstance().getDataList();
				for(int i = 0; i < mapList.size(); i++){
					Map<String, ParamInfo> map = mapList.get(i);
					
					Set<String> keys = map.keySet();
					
					for(String key: keys){
						ParamInfo info = map.get(key);
						
						//如果超时了，删除
						if(ParamManager.getInstance().isTimeOut(info)){
							map.remove(key);
//							System.out.println("超时---------------");
						}
					}
				}
			}catch (Exception e){
				System.out.println("检查订单线程报错：" + e.getMessage());
			}
			
			
			try {
				Thread.sleep(ParamManager.checkThreadTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	
}
