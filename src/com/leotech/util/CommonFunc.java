package com.leotech.util;

import java.util.Arrays;

public class CommonFunc {
	public static void ArrayShiftToLeft(byte[] buffer, int shiftNum) {
		if (shiftNum >= buffer.length) {
			Arrays.fill(buffer, (byte) 0);
			return;
		}
		for (int i = shiftNum; i < buffer.length; ++i) {
			buffer[i - shiftNum] = buffer[i];
		}
		Arrays.fill(buffer, buffer.length - shiftNum, buffer.length, (byte) 0);
	}
	public static int WordToInt(byte high, byte low) {
		return (high<<8 & 0xFF00) + (low & 0x00FF);
	}
	public static int CRC_INT(byte[] bytes) {
		int CRC = 0x0000ffff;
		int POLYNOMIAL = 0x0000a001;
		int i, j;
		for (i = 0; i < bytes.length; i++) {
			CRC ^= ((int) bytes[i] & 0x000000ff);
			for (j = 0; j < 8; j++) {
				if ((CRC & 0x00000001) != 0) {
					CRC >>= 1;
					CRC ^= POLYNOMIAL;
				} else {
					CRC >>= 1;
				}
			}
		}
		return CRC;
	}
	public static String CRC(byte[] bytes) {
		int CRC = 0x0000ffff;
		int POLYNOMIAL = 0x0000a001;
		int i, j;
		for (i = 0; i < bytes.length; i++) {
			CRC ^= ((int) bytes[i] & 0x000000ff);
			for (j = 0; j < 8; j++) {
				if ((CRC & 0x00000001) != 0) {
					CRC >>= 1;
					CRC ^= POLYNOMIAL;
				} else {
					CRC >>= 1;
				}
			}
		}
		return Integer.toHexString(CRC);
	}
	public static int Hex2Int(String hexString) {
		return Integer.parseInt(hexString, 16);
	}
	public static String bytesToHex(byte[] byteArray, int count) {
		final StringBuilder hexString = new StringBuilder("");
		if (byteArray == null || byteArray.length <= 0)
			return "";
		for (int i = 0; i < count; i++) {
			int v = byteArray[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				hexString.append(0);
			}
			hexString.append(hv + " ");
		}
		return hexString.toString().toUpperCase();
	}
}
