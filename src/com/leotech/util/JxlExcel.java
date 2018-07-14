//package com.leotech.util;
//
//import java.io.File;
//
//import jxl.Workbook;
//import jxl.write.Label;
//import jxl.write.WritableSheet;
//import jxl.write.WritableWorkbook;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//
//public class JxlExcel {
//	
//	public static void main(String[] args) {
//		String title = "时间,参数一,参数二";
//		String keys = "time,c1,fjmc";
//		String data = "[[{time:'2010-1', c1:01, c2:02, fjmc: '风机一'},{time:'2010-2', c1:03, c2:04, fjmc: '风机一'}],"
//				         + "[{time:'2011-1', c1:11, c2:12, fjmc: '风机二'},{time:'2011-2', c1:13, c2:14,fjmc: '风机二'}]"
//				         + "[{time:'2012-1', c1:21, c2:22, fjmc: '风机三'},{time:'2012-2', c1:23, c2:14,fjmc: '风机三'}]]";
//		JSONArray context = JSONArray.parseArray(data);
////		exportExcel(title,keys,context);
//	}
//	
//	
//	/**
//	 * 
//	 * @author: Duanhaobo
//	 * @time: 2017年12月11日下午3:53:05
//	 * @explain: 导出excel文件
//	 * @param title 列名 ，如果有多个列名用逗号分隔。
//	 * @param keys 参数，如果有多个列名用逗号分隔。
//	 * @param context 数据
//	 * @return: String 文件名
//	 */
//	public static String exportExcel(String title, String keys, JSONArray context, String fileName ) {
//		
//		
//		//文件保存路径
//		String path = GlobalPathUtil.path + "/" + fileName + ".xls";
//		
//		try {
//			
//			//创建一个文件
//			File file = new File(path);
//			//创建工作簿对象
//			WritableWorkbook workBook = Workbook.createWorkbook(file);
//			
//			//创建sheet页
//			WritableSheet sheet = workBook.createSheet("sheet1", 0);
//			
//			Label label = null;
//			
//			//表头名字
//			String[] titleName = title.split(",");
//			
//			//设置列名
//			for (int i = 0; i < titleName.length; i++) {
//				
//				//设置标题
//				label = new Label(i, 0, titleName[i]);
//				
//				//往sheet页中添加单元格
//				sheet.addCell(label);
//			}
//			
//			//参数
//			String[] canshu = keys.split(",");
//			
//			int rowNum = 1;
//			//添加内容
//				
//				//每个风机的参数
//				
//				//循环每个风机的数据
//				for ( int j = 0; j < context.size(); j++) {
//					
//					JSONObject obj = context.getJSONObject(j);
//					
//					//循环每个参数
//					for (int k = 0; k < canshu.length; k++) {
//						
//						String key = canshu[k];
//						String value = obj.getString(key);
//						
//						//参数：第几列,第几行,内容
//						label = new Label(k, rowNum, value);
//						sheet.addCell(label);
//					}
//					
//					rowNum++;
//					
//				}
//			
//			//写出数据
//			workBook.write();
//			workBook.close();
//			System.out.println("导出Excel文件成功");
//			
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return fileName;
//	}
//	
//	
//	/**
//	 * 
//	 * @author: Duanhaobo
//	 * @time: 2017年12月11日下午3:53:05
//	 * @explain: 导出excel文件
//	 * @param title 列名 ，如果有多个列名用逗号分隔。
//	 * @param keys 参数，如果有多个列名用逗号分隔。
//	 * @param context 数据
//	 * @return: String 文件名
//	 */
//	public static String exportExcel2(String title, String keys, JSONArray context ) {
//		
//		
//		//文件名
//		String fileName = "/趋势分析.xls";
//		//文件保存路径
//		String path = GlobalPathUtil.path + fileName;
//		
//		try {
//			
//			//创建一个文件
//			File file = new File(path);
//			//创建工作簿对象
//			WritableWorkbook workBook = Workbook.createWorkbook(file);
//			
//			//创建sheet页
//			WritableSheet sheet = workBook.createSheet("sheet1", 0);
//			
//			Label label = null;
//			
//			//表头名字
//			String[] titleName = title.split(",");
//			
//			//设置列名
//			for (int i = 0; i < titleName.length; i++) {
//				
//				//设置标题
//				label = new Label(i, 0, titleName[i]);
//				
//				//往sheet页中添加单元格
//				sheet.addCell(label);
//			}
//			
//			//参数
//			String[] canshu = keys.split(",");
//			
//			int rowNum = 1;
//			//添加内容
//			for (int i = 0; i < context.size(); i++) {
//				
//				//每个风机的参数
//				JSONArray data = context.getJSONArray(i);
//				
//				//循环每个风机的数据
//				for ( int j = 0; j < data.size(); j++) {
//					
//					JSONObject obj = data.getJSONObject(j);
//					
//					//循环每个参数
//					for (int k = 0; k < canshu.length; k++) {
//						
//						String key = canshu[k];
//						String value = obj.getString(key);
//						
//						//参数：第几列,第几行,内容
//						label = new Label(k, rowNum, value);
//						sheet.addCell(label);
//					}
//					
//					rowNum++;
//					
//				}
//			}
//			
//			//写出数据
//			workBook.write();
//			workBook.close();
//			System.out.println("导出Excel文件成功");
//			
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return fileName;
//	}
//}
