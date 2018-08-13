package com.leotech.rt;

public class ReqParam {
	public static final int DEVID_BYTE_COUNT = 1;
	public static final int CODE_BYTE_COUNT = 1;
	public static final int REQ_STARTADD_BYTE_COUNT = 2;
	public static final int REQ_DATANUM_BYTE_COUNT = 2;
	public static final int CRC_BYTE_COUNT = 2;
	public static final int RES_BYTENUM_BYTE_COUNT = 1;
	public int devId;
	public int code;
	public int startAdd;
	public int dataNum;
	public int dcount;
}
