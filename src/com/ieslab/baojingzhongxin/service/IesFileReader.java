package com.ieslab.baojingzhongxin.service;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
public class IesFileReader extends RandomAccessFile {
	public IesFileReader(String str, String mode)
	throws FileNotFoundException
	{
		super(str, mode) ;
	}
	public IesFileReader(File file, String mode)
	throws FileNotFoundException
	{
		super(file, mode) ;
	}
	public static int byteToInt(byte[] b, int offset)
	{
		int ret=0;
		for(int i=0; i<4; i++)
		{
			ret += (b[offset+i] & 0x000000ff) << (8*i);
		}
		return ret;
	}
	public static short byteToShort(byte[] b, int offset)
	{	
		short z = (short)(((b[offset+1] & 0x00FF) << 8) | (0x00FF & b[offset]));
		return z;
	}
}
