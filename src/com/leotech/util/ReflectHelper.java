//package com.leotech.util;
//
///********************************************************************
// *	版权所有 (C) 2009-2011 积成电子股份有限公司
// *	保留所有版权
// *
// *	作者：	侯培彬
// *	日期：	2010-12-25
// *	摘要：	通过反射获取类的信息
// *  功能：      通过反射获取类的信息
// *
// *********************************************************************/
//
//
//import java.lang.reflect.AccessibleObject;
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.util.Map;
//import java.util.TreeMap;
//
//
//public class ReflectHelper {
//	private Object classObj;
//
//
//	public ReflectHelper() {
//	}
//
//
//	/**
//	 * 获取类的公有方法名,包括从父类集成的方法
//	 *
//	 * @param theClass
//	 * @return
//	 */
//	public static String[] getMethods(Object theClass) {
//		Class cls = theClass.getClass();
//		Method[] methods = cls.getMethods();
//		String[] strMethods = new String[methods.length];
//		for (int i = 0; i < methods.length; i++) {
//			strMethods[i] = methods[i].getName();
//		}
//		return strMethods;
//	}
//
//	/**
//	 * 获取类的所有方法,包括公有,私有,保护和默认
//	 *
//	 * @return
//	 */
//	public String[] getAllMethods(Object theClass) {
//		Class cls = theClass.getClass();
//		Method[] methods = cls.getMethods();
//		AccessibleObject.setAccessible(methods, true);
//		String[] strMethods = new String[methods.length];
//		for (int i = 0; i < methods.length; i++) {
//			strMethods[i] = methods[i].getName();
//		}
//		return strMethods;
//	}
//
//	/**
//	 * 获取类及其父类的所有public字段
//	 *
//	 * @param theClass
//	 * @return
//	 * @throws java.lang.Exception
//	 */
//	public static String[][] getFields(Object theClass) throws Exception {
//		Class cls = theClass.getClass();
//		Field[] fields = cls.getFields();
//		// AccessibleObject.setAccessible(fields, true);
//		String[][] strFields = new String[fields.length][2];
//		Object objTemp = null;
//		for (int i = 0; i < fields.length; i++) {
//			strFields[i][0] = fields[i].getName();
//			objTemp = fields[i].get(theClass);
//			if (objTemp != null) {
//				strFields[i][1] = objTemp.toString();
//			}
//		}
//		return strFields;
//	}
//	
//	/**
//	 * 获取类及其父类的所有public字段
//	 *
//	 * @param theClass
//	 * @return
//	 * @throws java.lang.Exception
//	 */
//	public static String[] getFields(Class cls) throws Exception {
//		Field[] fields = cls.getFields();
//		// AccessibleObject.setAccessible(fields, true);
//		String[] strFields = new String[fields.length];
//		Object objTemp = null;
//		for (int i = 0; i < fields.length; i++) {
//			strFields[i] = fields[i].getName();
//		}
//		return strFields;
//	}
//
//	/**
//	 * 获取在当前类中定义的所有字段,包括扩私有,公有,保护和默认.不包括父类的字段
//	 *
//	 * @param theClass
//	 * @return
//	 * @throws java.lang.Exception
//	 */
//	public String[][] getAllFields(Object theClass) throws Exception {
//		Class cls = theClass.getClass();
//		Field[] fields = cls.getDeclaredFields();
//		AccessibleObject.setAccessible(fields, true);
//		String[][] strFields = new String[fields.length][2];
//		Object objTemp = null;
//		Map tempMap = new TreeMap<String, String>();
//		for (int i = 0; i < fields.length; i++) {
//			fields[i].setAccessible(true);
//			strFields[i][0] = fields[i].getName();
//			objTemp = fields[i].get(theClass);
//			if (objTemp != null) {
//				strFields[i][1] = "";
//			}
//		}
//		return strFields;
//	}
//
//}
