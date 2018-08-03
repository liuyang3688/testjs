package com.leotech.rt;

import com.leotech.model.Triple;
import com.leotech.service.RtService;
import com.leotech.util.Constant;

public class RtThread implements Runnable {
	public void run() {
		while(!Thread.interrupted()) {
			// 空调
			RtService.setRtData(new Triple(Constant.BJLX_AIR, 1, Constant.BJCS_AIR_P1), Math.random()*20);
			RtService.setRtData(new Triple(Constant.BJLX_AIR, 1, Constant.BJCS_AIR_P2), 20+Math.random()*20);
			RtService.setRtData(new Triple(Constant.BJLX_AIR, 1, Constant.BJCS_AIR_P3), 40+Math.random()*20);
			RtService.setRtData(new Triple(Constant.BJLX_AIR, 2, Constant.BJCS_AIR_P1), Math.random()*20);
			RtService.setRtData(new Triple(Constant.BJLX_AIR, 2, Constant.BJCS_AIR_P2), 20+Math.random()*20);
			RtService.setRtData(new Triple(Constant.BJLX_AIR, 2, Constant.BJCS_AIR_P3), 40+Math.random()*20);
			RtService.setRtData(new Triple(Constant.BJLX_AIR, 3, Constant.BJCS_AIR_P1), Math.random()*20);
			RtService.setRtData(new Triple(Constant.BJLX_AIR, 3, Constant.BJCS_AIR_P2), 20+Math.random()*20);
			RtService.setRtData(new Triple(Constant.BJLX_AIR, 3, Constant.BJCS_AIR_P3), 40+Math.random()*20);
			// Fire
			RtService.setRtData(new Triple(Constant.BJLX_FIRE, 1, Constant.BJCS_FIRE_P1), 100+Math.random()*20);
			RtService.setRtData(new Triple(Constant.BJLX_FIRE, 1, Constant.BJCS_FIRE_P2), 120+Math.random()*20);
			RtService.setRtData(new Triple(Constant.BJLX_FIRE, 1, Constant.BJCS_FIRE_P3), 140+Math.random()*20);
			RtService.setRtData(new Triple(Constant.BJLX_FIRE, 2, Constant.BJCS_FIRE_P1), 100+Math.random()*20);
			RtService.setRtData(new Triple(Constant.BJLX_FIRE, 2, Constant.BJCS_FIRE_P2), 120+Math.random()*20);
			RtService.setRtData(new Triple(Constant.BJLX_FIRE, 2, Constant.BJCS_FIRE_P3), 140+Math.random()*20);
			RtService.setRtData(new Triple(Constant.BJLX_FIRE, 3, Constant.BJCS_FIRE_P1), 100+Math.random()*20);
			RtService.setRtData(new Triple(Constant.BJLX_FIRE, 3, Constant.BJCS_FIRE_P2), 120+Math.random()*20);
			RtService.setRtData(new Triple(Constant.BJLX_FIRE, 3, Constant.BJCS_FIRE_P3), 140+Math.random()*20);
			RtService.setRtData(new Triple(Constant.BJLX_FIRE, 4, Constant.BJCS_FIRE_P1), 100+Math.random()*20);
			RtService.setRtData(new Triple(Constant.BJLX_FIRE, 4, Constant.BJCS_FIRE_P2), 120+Math.random()*20);
			RtService.setRtData(new Triple(Constant.BJLX_FIRE, 4, Constant.BJCS_FIRE_P3), 140+Math.random()*20);
			// SERVER
			RtService.setRtData(new Triple(Constant.BJLX_SERVER, 1, Constant.BJCS_SERVER_P1), 200+Math.random()*20);
			RtService.setRtData(new Triple(Constant.BJLX_SERVER, 1, Constant.BJCS_SERVER_P2), 220+Math.random()*20);
			RtService.setRtData(new Triple(Constant.BJLX_SERVER, 1, Constant.BJCS_SERVER_P3), 240+Math.random()*20);
			// UPS
			RtService.setRtData(new Triple(Constant.BJLX_UPS, 1, Constant.BJCS_UPS_P1), 300+Math.random()*20);
			RtService.setRtData(new Triple(Constant.BJLX_UPS, 1, Constant.BJCS_UPS_P2), 320+Math.random()*20);
			RtService.setRtData(new Triple(Constant.BJLX_UPS, 1, Constant.BJCS_UPS_P3), 340+Math.random()*20);
			try{
				Thread.sleep(500);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}
