package com.ieslab.baojingzhongxin.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ieslab.base.service.BaseModelCache;
import com.ieslab.util.DateUtil;
import com.ieslab.util.QueryConfig;

public class GuZhangLuBoService {

	/**
	 * 解析整体文件
	 */
	public ParseResult parseFile(String filename){
		ParseResult result = null;
		// 设置目录
		String path = QueryConfig.getInstance().getGuzhangluboFilePath() + filename;
		File file = new File(path);
        if (file != null && file.isFile()){
        	System.out.println("解析开始");
        	Date beginTime = new Date();
        	ParseFile parsor = new ParseFile();
        	result = parsor.readFileByLines(file.getPath());
        	Date endTime = new Date();
            long timeSpan = endTime.getTime() - beginTime.getTime();
        	System.out.println("故障录波解析总用时:"+timeSpan+"ms.");
        }
        return result;
	}
	/**
	 * 解析配置信息，不解析数据
	 * @param czids 格式1,2,3
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<JSONObject> parseStationPart(String czids, String startTime, String endTime){
		List<JSONObject> list = new ArrayList<JSONObject>();
		//获取风场的名字
		List<String> czNameList = new ArrayList<String>();
		String czidArr[] = czids.split(",");
		if(czidArr != null){
			for(String czid: czidArr){
				String czName = "";
				if(BaseModelCache.getInstance().getCzMap().containsKey(czid)){
					czName = BaseModelCache.getInstance().getCzMap().get(czid).getString("mingzi");
					if(czName != null){
						czName = czName.replaceAll("风场", "");
					}
					czNameList.add(czName);
				}
			}
		}
		// 设置目录
		String path = QueryConfig.getInstance().getGuzhangluboFilePath();
		File filePath = new File(path);
        if (filePath.exists() && filePath.isDirectory()) {
            File[] files = filePath.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    continue;
                } else {
                	String fileName = file.getName();
                	//解析文件名字判断时间
                	String arr[] = fileName.split("\\+");
            		if(arr.length == 2){
            			String dateStr = arr[0];
            			dateStr = dateStr.replaceAll("y", "").replaceAll("m", "").replaceAll("d", "").replaceAll("h", "").replaceAll("s", "");
            			dateStr = dateStr.substring(0, 14);
                		Date date = DateUtil.parseDateStr(dateStr, "yyyyMMddHHmmss");
                		String fileTime = DateUtil.formatdate(date, "yyyy-MM-dd HH:mm:ss");
            		
                		if(startTime.compareTo(fileTime) <= 0 && fileTime.compareTo(endTime) <= 0){
                			ParseFile parsor = new ParseFile();
                        	ParseResult result = parsor.parseStationPart(file.getPath());
                        	if(czNameList != null && czNameList.size() > 0){
                        		for(String czname: czNameList){
                                	if(result != null && result.cfg.StationName.indexOf(czname) >= 0){
                                		JSONObject obj = new JSONObject();
                                		obj.put("time", fileTime);
                                		obj.put("czname", czname + "风场");
                                		obj.put("filename", fileName);
                                		list.add(obj);
                                		break;
                                	}
                        		}
                        	}
                        	
                		}
            		}
                }
            }
        } else {
            System.out.println("指定路径不存在或者非目录");
        }
        
        return list;
	}
	
	
	public void parseFileToCache(String fileName){
		GuZhangLuBoService service = new GuZhangLuBoService();
		ParseResult parseResult = service.parseFile(fileName);
		
		int AChannelNum = parseResult.getCfg().getAChannelNum();
		int DChannelNum = parseResult.getCfg().getDChannelNum();
		
		JSONObject res = new JSONObject();
		
		//获取模拟通道数据
		for(int i = 0; i < AChannelNum; i++){
			JSONArray arr = new JSONArray();
			res.put("a" + i, arr);
		}
		//获取数字通道数据
		for(int i = 0; i < DChannelNum; i++){
			JSONArray arr = new JSONArray();
			res.put("d" + i, arr);
		}
		
		ArrayList<BinaryBlock> dataList = parseResult.getBllist();
		for(BinaryBlock b: dataList){
			float[] aValList = b.getaValList();
			for(int i = 0; i < aValList.length; i++){
				if(res.containsKey("a" + i)){
					JSONArray arr = (JSONArray) res.get("a" + i);
					arr.add(aValList[i]);
				}
			}
			
			byte[] dValList = b.getdValList();
			for(int i = 0; i < dValList.length; i++){
				if(res.containsKey("d" + i)){
					JSONArray arr = (JSONArray) res.get("d" + i);
					arr.add(dValList[i]);
				}
			}
		}
		
		//组织通道信息
		AChannel[] aChannel = parseResult.getCfg().getAChannelList();
		JSONArray aChannelArr = new JSONArray();
		if(aChannel != null){
			for(int i = 0; i < aChannel.length; i++){
				AChannel channel = aChannel[i];
				JSONObject obj = new JSONObject();
				obj.put("index", i);
				obj.put("name", channel.getChname());
				aChannelArr.add(obj);
			}
		}
		res.put("achannel", aChannelArr);
		
		DChannel[] dChannel = parseResult.getCfg().getDChannelList();
		JSONArray dChannelArr = new JSONArray();
		if(dChannel != null){
			for(int i = 0; i < dChannel.length; i++){
				DChannel channel = dChannel[i];
				JSONObject obj = new JSONObject();
				obj.put("index", i);
				obj.put("name", channel.getChname());
				dChannelArr.add(obj);
			}
		}
		res.put("dchannel", dChannelArr);
		
		if(!GuZhangLuBoCache.cacheMap.containsKey(fileName)){
			GuZhangLuBoCache.cacheMap.put(fileName, res);
		}
	}
}
