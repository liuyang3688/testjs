package com.leotech.rt;

import com.leotech.model.Triple;
import com.leotech.service.RtService;
import com.leotech.util.Constant;
import com.sun.org.apache.xpath.internal.operations.Bool;
import gnu.io.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import com.leotech.util.CommonFunc;
import static java.util.Arrays.copyOfRange;

public class AirCondComThread implements Runnable {
	private static final int BUFFER_SIZE = 1024;
	private volatile Boolean running = true;
//	private static int[][][] intVal = new int[4][10][1000];
//	private static float[][][] floatVal = new float[4][10][1000];
	private SerialParam conf;
	static {
	}
	public void stopEx() {
		this.running = false;
	}
	public AirCondComThread() {
		conf = new SerialParam();
	}
	public void readConfig(){
		// 读取配置文件
		Properties prop = new Properties();
		try {
			InputStream in = AirCondComThread.class.getResourceAsStream("comm.properties");
			prop.load(in);
			conf.setCommId(prop.getProperty("airCond.commId"));
			conf.setBaudRate(Integer.parseInt(prop.getProperty("airCond.baudRate", "9600")));
			conf.setDataBit(Integer.parseInt(prop.getProperty("airCond.dataBit", "8")));
			conf.setStopBit(Integer.parseInt(prop.getProperty("airCond.stopBit", "1")));
			conf.setParity(Integer.parseInt(prop.getProperty("airCond.parity", "0")));
			conf.setDTR(Boolean.parseBoolean(prop.getProperty("airCond.dtr", "false")));
			conf.setRTS(Boolean.parseBoolean(prop.getProperty("airCond.rts", "false")));
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found: comm.properties");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void run() {
		readConfig();
		CommPortIdentifier portId = null;
		CommPortIdentifier com = null;
		SerialPort serialCom = null;
		byte[] buffer = new byte[1024];
		ReqParam reqParam = new ReqParam();
		while(running) {
			try {
				com = CommPortIdentifier.getPortIdentifier(conf.getCommId());
				serialCom = (SerialPort) com.open("Main", 1000);
				serialCom.setSerialPortParams(
						conf.getBaudRate(),
						conf.getDataBit(),
						conf.getStopBit(),
						conf.getParity()
				);
				System.out.println(conf.getCommId() + "：端口已监听");
				// 循环读取
				InputStream inputStream = serialCom.getInputStream();
				Arrays.fill(buffer, (byte)0);
				int availableBytes = 0;
				int writePos = 0;
				int readPos = 0;
				int needReadNum = 8;
				Boolean needReq = true;

				while (running) {
					availableBytes = inputStream.available();
					while (availableBytes > 0) {
						// 如果写入位置已到最后，则回溯起始
						if (writePos >= BUFFER_SIZE) {
							writePos = 0;
							readPos = 0;
						}

						// 从写入位置开始读取
						int realReadCount = inputStream.read(buffer, writePos, BUFFER_SIZE-writePos);
						availableBytes -= realReadCount;
						writePos += realReadCount;
						if (needReq) {
							// 取字节
							while (writePos - readPos >= needReadNum) {
								// 取前六个字节 计算CRC
								byte[] reqData = Arrays.copyOfRange(buffer, readPos, readPos+6);
								byte[] reqCrc = Arrays.copyOfRange(buffer, readPos+6, readPos+8);
								int nReqCrc = CommonFunc.WordToInt(reqCrc[1], reqCrc[0]);
								int nCalcCrc = CommonFunc.CRC_INT(reqData);
								if (nCalcCrc == nReqCrc){
									// CRC校验通过
									Boolean bParsed = true;
									reqParam.devId = (int)reqData[0];
									reqParam.code = (int)reqData[1];
									reqParam.startAdd = CommonFunc.WordToInt(reqData[2], reqData[3]);
									reqParam.dataNum = CommonFunc.WordToInt(reqData[4], reqData[5]);

									int dataByteCount = 0;
									switch (reqParam.code) {
										case 1:
										case 2:
											dataByteCount = (reqParam.dataNum-1)/8 + 1;
											break;
										case 3:
										case 4:
											dataByteCount = reqParam.dataNum * 2;
											break;
										default:
											bParsed = false;
									}
									if (bParsed) {
										// 1位
										reqParam.dcount = dataByteCount;
										needReadNum = ReqParam.DEVID_BYTE_COUNT
												+ ReqParam.CODE_BYTE_COUNT
												+ ReqParam.RES_BYTENUM_BYTE_COUNT
												+ dataByteCount
												+ ReqParam.CRC_BYTE_COUNT;
										needReq = false;
										readPos += 8;
									} else {
										readPos ++;
									}
								} else {
									// CRC校验不通过
									readPos ++;
								}
							}
						}
						if (!needReq){
							while (writePos - readPos >= needReadNum) {
								int devId = buffer[readPos+0];
								int code = buffer[readPos+1];
								int DCount = buffer[readPos+2];
								int BCount = 3 + DCount;
								int ACount = BCount + 2;
								if (devId == reqParam.devId && code==reqParam.code && DCount==reqParam.dcount) {
									//帧头对应 继续判断CRC
									byte[] resBData = Arrays.copyOfRange(buffer, readPos, readPos+BCount);
									byte[] resCrc = Arrays.copyOfRange(buffer, readPos + BCount, readPos + ACount);
									int nResCrc = CommonFunc.WordToInt(resCrc[1], resCrc[0]);
									int nCalcCrc = CommonFunc.CRC_INT(resBData);
									if (nCalcCrc == nResCrc) {
										// CRC校验通过
										Boolean bParsed = true;
										if (reqParam.code == 1 || reqParam.code == 2) {
											//如果code 是1/2 按位解析
											for (int pos=0; pos < DCount; ++pos ) {
												byte data = resBData[3+pos];
												RtService.setRtData_YX(new Triple(Constant.BJLX_AIR, devId, reqParam.startAdd + pos*8 + 0), (int)(data>>0 & 0x01));
												RtService.setRtData_YX(new Triple(Constant.BJLX_AIR, devId, reqParam.startAdd + pos*8 + 1), (int)(data>>1 & 0x01));
												RtService.setRtData_YX(new Triple(Constant.BJLX_AIR, devId, reqParam.startAdd + pos*8 + 2), (int)(data>>2 & 0x01));
												RtService.setRtData_YX(new Triple(Constant.BJLX_AIR, devId, reqParam.startAdd + pos*8 + 3), (int)(data>>3 & 0x01));
												RtService.setRtData_YX(new Triple(Constant.BJLX_AIR, devId, reqParam.startAdd + pos*8 + 4), (int)(data>>4 & 0x01));
												RtService.setRtData_YX(new Triple(Constant.BJLX_AIR, devId, reqParam.startAdd + pos*8 + 5), (int)(data>>5 & 0x01));
												RtService.setRtData_YX(new Triple(Constant.BJLX_AIR, devId, reqParam.startAdd + pos*8 + 6), (int)(data>>6 & 0x01));
												RtService.setRtData_YX(new Triple(Constant.BJLX_AIR, devId, reqParam.startAdd + pos*8 + 7), (int)(data>>7 & 0x01));
											}
										} else if (reqParam.code == 3 || reqParam.code == 4) {
											// 如果code 是3/4 两字节解析
											for (int pos=0; pos < DCount; pos+=2 ) {
												int val = CommonFunc.WordToInt(resBData[3 + pos], resBData[4 + pos]);
												RtService.setRtData_YC(new Triple(Constant.BJLX_AIR, devId, reqParam.startAdd + pos/2), (double)(val * 0.1));
											}
										} else {
											bParsed = false;
										}
										// buf移位
										if (bParsed) {
											CommonFunc.ArrayShiftToLeft(buffer, readPos + ACount);
											writePos -= (readPos+ACount);
											readPos = 0;
										} else {// 解析错误
											readPos ++;
										}
										needReq = true;
										needReadNum = 8;
									} else {
										// CRC校验不通过
										readPos ++;
										needReq = true;
										needReadNum = 8;
									}
								} else {
									//帧头不对应
									readPos ++;
									needReq = true;
									needReadNum = 8;
								}
							}
						}
					}
					Thread.sleep(5000);
					System.out.println("readPos:" + readPos + " writePos:" + writePos);
					System.out.println(CommonFunc.bytesToHex(buffer, 100));
//					for (int j=0; j<100; ++j) {
//						System.out.print(j+":"+intVal[1][2][j]+" ");
//					}
//					System.out.println("");
//					for (int j=0; j<100; ++j) {
//						System.out.print(j+":"+floatVal[1][1][j]+" ");
//					}
//					System.out.println("");
					System.out.println("========================================");

				}
			} catch (NoSuchPortException e) {
				System.out.println(conf.getCommId() + ":No such port");
			} catch (PortInUseException e) {
				System.out.println(conf.getCommId() + ":No such port");
			} catch (UnsupportedCommOperationException e) {
				System.out.println(conf.getCommId() + ":Unsupported comm operation.");
			} catch (IOException e) {
				System.out.println(conf.getCommId() + ":IO error.");
			} catch (Exception e){
				System.out.printf(conf.getCommId() + ":unknown exception.");
			} finally {
				if (serialCom != null) {
					serialCom.close();
					serialCom = null;
				}
				try {
					Thread.sleep(2000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
