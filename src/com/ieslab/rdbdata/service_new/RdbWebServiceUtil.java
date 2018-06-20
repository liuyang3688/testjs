package com.ieslab.rdbdata.service_new;

import com.ieslab.rdbdata.RdbwebservicePortType;
import com.ieslab.rdbdata.Rdbwebservice_ServiceLocator;
import com.ieslab.util.QueryConfig;

public class RdbWebServiceUtil {
	/**
	 * 获取当前可以使用实时服务webservice
	 * @return
	 */
	public static RdbwebservicePortType getRdbWebService(){
		RdbwebservicePortType res = null;
		try {
			Rdbwebservice_ServiceLocator dd = new Rdbwebservice_ServiceLocator();
			dd.setRdbwebservice_address(QueryConfig.getInstance().getRtdatawebservice1());
			res = dd.getrdbwebservice();
			String ss = res.webServiceTest("1");
			if("Error".equalsIgnoreCase(ss)){
				dd.setRdbwebservice_address(QueryConfig.getInstance().getRtdatawebservice2());
				res = dd.getrdbwebservice();
				res.webServiceTest("1");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			try{
				Rdbwebservice_ServiceLocator dd = new Rdbwebservice_ServiceLocator();
				dd.setRdbwebservice_address(QueryConfig.getInstance().getRtdatawebservice2());
				res = dd.getrdbwebservice();
				res.webServiceTest("1");
			}catch (Exception ex) {
				return null;
			}
		}
		return res;
	}
	
	/**
	 * 获取当前主实时webService服务
	 * @return
	 */
	public static RdbwebservicePortType getRdbWebServiceMain(){
		RdbwebservicePortType res = null;
		try {
			Rdbwebservice_ServiceLocator dd = new Rdbwebservice_ServiceLocator();
			dd.setRdbwebservice_address(QueryConfig.getInstance().getRtdatawebservice1());
			res = dd.getrdbwebservice();
			String ss = res.webServiceTest("1");
			if("Error".equalsIgnoreCase(ss)){
				return null;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
		return res;
	}
	
	/**
	 * 获取当前辅实时webService服务
	 * @return
	 */
	public static RdbwebservicePortType getRdbWebServiceFu(){
		RdbwebservicePortType res = null;
		try {
			Rdbwebservice_ServiceLocator dd = new Rdbwebservice_ServiceLocator();
			dd.setRdbwebservice_address(QueryConfig.getInstance().getRtdatawebservice2());
			res = dd.getrdbwebservice();
			String ss = res.webServiceTest("1");
			if("Error".equalsIgnoreCase(ss)){
				return null;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
		return res;
	}
}
