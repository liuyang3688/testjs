package com.ieslab.base.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * 厂站功率风速框坐标逻辑实现类
 * 
 * @author Li Hao
 */
public class MapPosService {

	private File file;
	
	public MapPosService(String filePath){
		file = new File(filePath);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				System.out.println("创建坐标存储文件失败:" + e.getMessage());
				file = null;
			}
		}
	}
	
	
	public void addOrUpdatePos(String czid, String left, String top){
		SAXReader sr = new SAXReader();// 获取读取xml的对象。
		try {
			Document doc = sr.read(file);
			Node node = doc.selectSingleNode("/items/item[@czid='"+czid+"']");
			Element element = null;
			if(node == null){
				Element items = doc.getRootElement();
				element = items.addElement("item");
				element.addAttribute("czid", czid);
				element.addAttribute("left", left);
				element.addAttribute("top", top);
			}else{
				element = (Element)node;
				element.attribute("left").setData(left);
				element.attribute("top").setData(top);
			}
			
			try {
				XMLWriter output = new XMLWriter(new FileWriter(file));
				output.write( doc );  
				output.close();  
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public JSONArray getAllCzPos(){
		
		JSONArray res = new JSONArray();
		
		SAXReader sr = new SAXReader();// 获取读取xml的对象。
		try {
			Document doc = sr.read(file);// 得到xml所在位置。然后开始读取。并将数据放入doc中
			Element el_root = doc.getRootElement();// 向外取数据，获取xml的根节点。
			Iterator it = el_root.elementIterator();// 从根节点下依次遍历，获取根节点下所有子节点
			while (it.hasNext()) {// 遍历子节点
				JSONObject item = new JSONObject();
				Object o = it.next();
				Element el_row = (Element) o;
				
				item.put("czid", el_row.attribute("czid").getData());
				item.put("left", el_row.attribute("left").getData());
				item.put("top", el_row.attribute("top").getData());
				
				res.add(item);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return res;
	}
}
