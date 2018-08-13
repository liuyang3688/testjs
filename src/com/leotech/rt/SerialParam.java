package com.leotech.rt;
import com.sun.org.apache.xpath.internal.operations.Bool;
import gnu.io.SerialPort;
public class SerialParam {
	private String      commId;
	private int         baudRate;
	private int         dataBit;
	private int         stopBit;
	private int         parity;
	private Boolean     DTR;
	private Boolean     RTS;

	public SerialParam() {
		this.commId = "COM1";
		this.baudRate = 9600;
		this.dataBit = SerialPort.DATABITS_8;
		this.stopBit = SerialPort.STOPBITS_1;
		this.parity = SerialPort.PARITY_NONE;
		this.DTR = false;
		this.RTS = false;
	}
	public SerialParam(String commId, int baudRate, int dataBit, int stopBit, int parity) {
		this.commId = commId;
		this.baudRate = baudRate;
		this.dataBit = dataBit;
		this.stopBit = stopBit;
		this.parity = parity;
	}
	public SerialParam(String commId, int baudRate, int dataBit, int stopBit, int parity, Boolean dtr, Boolean rts) {
		this.commId = commId;
		this.baudRate = baudRate;
		this.dataBit = dataBit;
		this.stopBit = stopBit;
		this.parity = parity;
		this.DTR = dtr;
		this.RTS = rts;
	}

	public String getCommId() {
		return commId;
	}

	public void setCommId(String commId) {
		this.commId = commId;
	}

	public int getBaudRate() {
		return baudRate;
	}

	public void setBaudRate(int baudRate) {
		this.baudRate = baudRate;
	}

	public int getDataBit() {
		return dataBit;
	}

	public void setDataBit(int dataBit) {
		this.dataBit = dataBit;
	}

	public int getStopBit() {
		return stopBit;
	}

	public void setStopBit(int stopBit) {
		this.stopBit = stopBit;
	}

	public int getParity() {
		return parity;
	}

	public void setParity(int parity) {
		this.parity = parity;
	}

	public Boolean getDTR() {
		return DTR;
	}

	public void setDTR(Boolean DTR) {
		this.DTR = DTR;
	}

	public Boolean getRTS() {
		return RTS;
	}

	public void setRTS(Boolean RTS) {
		this.RTS = RTS;
	}
}
