package com.ieslab.base.service;

import com.ieslab.util.CreateUserRolePermissionTable;


/**
 * 定时更新缓存数据
 * @author duanhaobo
 *
 */
public class BaseModelUpdateThread implements Runnable{

	@Override
	public void run() {
		
//		CreateUserRolePermissionTable cu = new CreateUserRolePermissionTable();
//		cu.createTable();
		
		// TODO Auto-generated method stub	
		while(true){
			try {
				BaseModelCache.getInstance().updataCahce();
			} catch(Exception e){
				e.printStackTrace();
				System.out.println("更新缓存失败1"+e.getMessage());
			}
			try {
				Thread.sleep(1000*60*60);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	
}
