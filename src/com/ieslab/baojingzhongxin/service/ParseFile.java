package com.ieslab.baojingzhongxin.service;

import java.io.IOException;
import java.util.ArrayList;

public class ParseFile {
	public ParseResult readFileByLines(String fileName) {
        //File file = new File(fileName);
        ParseResult result = new ParseResult();
		IesFileReader reader = null;
        try {
            reader = new IesFileReader(fileName, "r");
            
			//////////////////////////////////////////////
			// 读取配置文件
			//////////////////////////////////////////////
            
            //过滤<RecorderReport></RecorderReport>部分
            while( !reader.readLine().equals("</RecorderReport>") ){}
            //读取站名，装置，年份
            ComTradeCfg cfg = result.cfg = new ComTradeCfg();
            String temp = reader.readLine();
            String[] strList = temp.split(",");
            if(strList.length != 3)
            {
            	reader.close();
            	throw new IOException("ERROR：配置文件格式错误，行：站名,装置,年份");
            }
            cfg.StationName = new String(strList[0].getBytes("ISO-8859-1"),"GBK");
            cfg.RecDevName = new String(strList[1].getBytes("ISO-8859-1"),"GBK");
            cfg.RevYear = strList[2];
            //读取通道数
            temp = reader.readLine();
            strList = temp.split(",");
            if(strList.length != 3)
            {
            	reader.close();
            	throw new IOException("ERROR：配置文件格式错误，行：总通道数,模拟通道数,数字通道数");
            }
            cfg.TotalChannelNum = Integer.parseInt(strList[0]);
            cfg.AChannelNum = Integer.parseInt( strList[1].substring(0, strList[1].length()-1) );
            cfg.DChannelNum = Integer.parseInt( strList[2].substring(0, strList[2].length()-1) );
            cfg.AChannelList = new AChannel[cfg.AChannelNum];
            cfg.DChannelList = new DChannel[cfg.DChannelNum];
            //循环读取模拟通道
            for(int i=0; i<cfg.AChannelNum; ++i)
            {
            	cfg.AChannelList[i] = new AChannel();
            	temp = reader.readLine();
                strList = temp.split(",");
                if(strList.length != 13)
                {
                	continue;
                }
                cfg.AChannelList[i].id = Integer.parseInt(strList[0]);
                cfg.AChannelList[i].chname = new String(strList[1].getBytes("ISO-8859-1"),"GBK");
                cfg.AChannelList[i].ph = strList[2];
                cfg.AChannelList[i].ccbm = strList[3];
                cfg.AChannelList[i].uu = strList[4];
                cfg.AChannelList[i].a = Float.parseFloat(strList[5]);
                cfg.AChannelList[i].b = Float.parseFloat(strList[6]);
                cfg.AChannelList[i].skew = Float.parseFloat(strList[7]);
                cfg.AChannelList[i].min = Float.parseFloat(strList[8]);
                cfg.AChannelList[i].max = Float.parseFloat(strList[9]);
                cfg.AChannelList[i].primary = Float.parseFloat(strList[10]);
                cfg.AChannelList[i].secondary = Float.parseFloat(strList[11]);
                cfg.AChannelList[i].ps = strList[12];
            }
            //循环读取数字通道
            for(int i=0; i<cfg.DChannelNum; ++i)
            {
            	cfg.DChannelList[i] = new DChannel();
            	temp = reader.readLine();
                strList = temp.split(",");
                if(strList.length != 5)
                {
                	continue;
                }
                cfg.DChannelList[i].id = Integer.parseInt(strList[0]);
                cfg.DChannelList[i].chname = new String(strList[1].getBytes("ISO-8859-1"),"GBK");
                cfg.DChannelList[i].ph = strList[2];
                cfg.DChannelList[i].ccbm = strList[3];
                cfg.DChannelList[i].y = Integer.parseInt(strList[4]);
            }
            //读取频率
            temp = reader.readLine();
            cfg.PL = Float.parseFloat(temp);
            //读取采样频率数
            temp = reader.readLine();
            cfg.NRATES = Integer.parseInt(temp);
            cfg.SampDescList = new SampDesc[cfg.NRATES];
            //根据采样频率数，读取采样描述
            for(int i=0; i<cfg.NRATES; i++)
            {
            	cfg.SampDescList[i] = new SampDesc();
            	temp = reader.readLine();
            	strList = temp.split(",");
                if(strList.length != 2)
                {
                	continue;
                }
                cfg.SampDescList[i].samppl = Integer.parseInt(strList[0]);
                cfg.SampDescList[i].endsamp = Integer.parseInt(strList[1]);
            }
            //读取起始时间
            temp = reader.readLine();
            cfg.StartTime = temp;
            //读取结束时间
            temp = reader.readLine();
            cfg.EndTime = temp;
            //读取文件类型
            temp = reader.readLine();
            cfg.FileType = temp;
            //读取时间乘数
            temp = reader.readLine();
            cfg.Timemult = Integer.parseInt(temp);
            
            //////////////////////////////////////////////
            // 读取数据文件
            //////////////////////////////////////////////
            int block_size = 4 + 4 + 2*cfg.AChannelNum + (cfg.DChannelNum + 7)/8;
            byte[] cbuf = new byte[block_size];
            ArrayList<BinaryBlock> bllist = result.bllist = new ArrayList<BinaryBlock>();
            // 起始5字节，过滤掉
            reader.read(cbuf, 0, 5);
            while( reader.read(cbuf, 0, block_size) != -1)
            {
            	BinaryBlock bl = new BinaryBlock();
            	bl.aValList = new float[cfg.AChannelNum];
                bl.dValList = new byte[cfg.DChannelNum];
            	int offset = 0;
            	bl.no = IesFileReader.byteToInt(cbuf, offset);
            	offset += 4;
            	bl.ts = IesFileReader.byteToInt(cbuf, offset);
            	offset += 4;
            	for(int i=0; i<cfg.AChannelNum; i++)
            	{
            		short val = IesFileReader.byteToShort(cbuf, offset);
            		float fVal = val * cfg.AChannelList[i].a + cfg.AChannelList[i].b;
            		if(cfg.AChannelList[i].ps.equals("S"))
            		{
            			fVal = fVal * cfg.AChannelList[i].primary / cfg.AChannelList[i].secondary;
            		}
            		else if(cfg.AChannelList[i].ps.equals("P"))
            		{
            			fVal = fVal * cfg.AChannelList[i].secondary / cfg.AChannelList[i].primary;
            		}
            		bl.aValList[i] = fVal;
            		offset += 2;
            	}
            	for(int i=0; i<cfg.DChannelNum; i++)
            	{
            		byte val = cbuf[offset + i/8];
            		bl.dValList[i] = (byte)( 0x01 & (val >> (i%8) ));
            	}
            	bllist.add(bl);
            }
            reader.close();
        } catch (IOException e) {
        	e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return result;
    }
	
	public ParseResult parseStationPart(String fileName) {
        //File file = new File(fileName);
        ParseResult result = new ParseResult();
		IesFileReader reader = null;
        try {
            reader = new IesFileReader(fileName, "r");
            
			//////////////////////////////////////////////
			// 读取配置文件
			//////////////////////////////////////////////
            
            //过滤<RecorderReport></RecorderReport>部分
            while( !reader.readLine().equals("</RecorderReport>") ){}
            //读取站名，装置，年份
            ComTradeCfg cfg = result.cfg = new ComTradeCfg();
            String temp = reader.readLine();
            String[] strList = temp.split(",");
            if(strList.length != 3)
            {
            	reader.close();
            	throw new IOException("ERROR：配置文件格式错误，行：站名,装置,年份");
            }
            cfg.StationName = new String(strList[0].getBytes("ISO-8859-1"),"GBK");
            cfg.RecDevName = new String(strList[1].getBytes("ISO-8859-1"),"GBK");
            cfg.RevYear = strList[2];
            //读取通道数
            temp = reader.readLine();
            strList = temp.split(",");
            if(strList.length != 3)
            {
            	reader.close();
            	throw new IOException("ERROR：配置文件格式错误，行：总通道数,模拟通道数,数字通道数");
            }
            cfg.TotalChannelNum = Integer.parseInt(strList[0]);
            cfg.AChannelNum = Integer.parseInt( strList[1].substring(0, strList[1].length()-1) );
            cfg.DChannelNum = Integer.parseInt( strList[2].substring(0, strList[2].length()-1) );
            cfg.AChannelList = new AChannel[cfg.AChannelNum];
            cfg.DChannelList = new DChannel[cfg.DChannelNum];
            //循环读取模拟通道
            for(int i=0; i<cfg.AChannelNum; ++i)
            {
            	cfg.AChannelList[i] = new AChannel();
            	temp = reader.readLine();
                strList = temp.split(",");
                if(strList.length != 13)
                {
                	continue;
                }
                cfg.AChannelList[i].id = Integer.parseInt(strList[0]);
                cfg.AChannelList[i].chname = new String(strList[1].getBytes("ISO-8859-1"),"GBK");
                cfg.AChannelList[i].ph = strList[2];
                cfg.AChannelList[i].ccbm = strList[3];
                cfg.AChannelList[i].uu = strList[4];
                cfg.AChannelList[i].a = Float.parseFloat(strList[5]);
                cfg.AChannelList[i].b = Float.parseFloat(strList[6]);
                cfg.AChannelList[i].skew = Float.parseFloat(strList[7]);
                cfg.AChannelList[i].min = Float.parseFloat(strList[8]);
                cfg.AChannelList[i].max = Float.parseFloat(strList[9]);
                cfg.AChannelList[i].primary = Float.parseFloat(strList[10]);
                cfg.AChannelList[i].secondary = Float.parseFloat(strList[11]);
                cfg.AChannelList[i].ps = strList[12];
            }
            //循环读取数字通道
            for(int i=0; i<cfg.DChannelNum; ++i)
            {
            	cfg.DChannelList[i] = new DChannel();
            	temp = reader.readLine();
                strList = temp.split(",");
                if(strList.length != 5)
                {
                	continue;
                }
                cfg.DChannelList[i].id = Integer.parseInt(strList[0]);
                cfg.DChannelList[i].chname = new String(strList[1].getBytes("ISO-8859-1"),"GBK");
                cfg.DChannelList[i].ph = strList[2];
                cfg.DChannelList[i].ccbm = strList[3];
                cfg.DChannelList[i].y = Integer.parseInt(strList[4]);
            }
            //读取频率
            temp = reader.readLine();
            cfg.PL = Float.parseFloat(temp);
            //读取采样频率数
            temp = reader.readLine();
            cfg.NRATES = Integer.parseInt(temp);
            cfg.SampDescList = new SampDesc[cfg.NRATES];
            //根据采样频率数，读取采样描述
            for(int i=0; i<cfg.NRATES; i++)
            {
            	cfg.SampDescList[i] = new SampDesc();
            	temp = reader.readLine();
            	strList = temp.split(",");
                if(strList.length != 2)
                {
                	continue;
                }
                cfg.SampDescList[i].samppl = Integer.parseInt(strList[0]);
                cfg.SampDescList[i].endsamp = Integer.parseInt(strList[1]);
            }
            //读取起始时间
            temp = reader.readLine();
            cfg.StartTime = temp;
            //读取结束时间
            temp = reader.readLine();
            cfg.EndTime = temp;
            //读取文件类型
            temp = reader.readLine();
            cfg.FileType = temp;
            //读取时间乘数
            temp = reader.readLine();
            cfg.Timemult = Integer.parseInt(temp);
            reader.close();
        } catch (IOException e) {
        	e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return result;
    }
}
