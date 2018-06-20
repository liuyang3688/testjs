package com.ieslab.base.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ieslab.util.GlobalPathUtil;

public class BujiancanshuService {
	public static Map<String, JSONObject> map = new HashMap<String, JSONObject>();
	public static void bujiancanshu(){
		 String path = GlobalPathUtil.path;
		 String filePath = path+"config"+File.separator+"bujiancanshuNew.json";
		 String json = readFile(filePath);
		 try {
			JSONArray jsonArray = new JSONArray(json);
			for(int i = 0; i < jsonArray.length(); i++){
				JSONObject t = (JSONObject)jsonArray.get(i);
				map.put(t.getString("code"), t);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public static void zhibiaoleixing(){
		 String path = GlobalPathUtil.path;
		 String filePath = path+"json"+File.separator+"zhibiao.json";
		 String json = readFile(filePath);
		 try {
			JSONObject obj = new JSONObject(json);
			map.put("zhibiaoleixing", obj);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public static String readFile(String Path){
		BufferedReader reader = null;
		String laststr = "";
		try{
			FileInputStream fileInputStream = new FileInputStream(Path);
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
			reader = new BufferedReader(inputStreamReader);
			String tempString = null;
			while((tempString = reader.readLine()) != null){
				laststr += tempString;
			}
			reader.close();
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if(reader != null){
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
			return laststr;
		}
}
