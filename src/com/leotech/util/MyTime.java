///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.leotech.util;
//
//import java.sql.Timestamp;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.GregorianCalendar;
//import java.util.TimeZone;
//
///**
// *
// * @author houpeibin
// */
//public class MyTime {
//
//    public static String getNowTime(String dateformat) {
//        Date nowTime = new Date();
//        SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);//���Է�����޸����ڸ�ʽ
//        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
//        String nowTimeStr = dateFormat.format(nowTime);
//        return nowTimeStr;
//    }
//    
//    public static String getNowTime1(String dateformat) {
//        Date nowTime = new Date();
//        SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);//���Է�����޸����ڸ�ʽ      
//        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
//        String nowTimeStr = dateFormat.format(nowTime);
//        Date d = new Date();
//        d.setHours(d.getHours()-1);
//        return dateFormat.format(d);
//    }
//    
//    public static String FormatDate(Timestamp ts,String dateformat) {
//        SimpleDateFormat format = new SimpleDateFormat(dateformat);
//        String timestr = Timestamp.valueOf(format.format(ts.getTime())).toString();
//        int index = timestr.indexOf(".");
//        if(index>0){
//        	timestr = timestr.substring(0,index);
//        }
//        return timestr;
//    }
//    
//    public static String getYesterDay(String dateformat){
//    	String yesterDay = "";
//    	String today = MyTime.getNowTime("yyyy-MM-dd");
//    	try{
//    		Date date = null;
//            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//            GregorianCalendar gc = new GregorianCalendar();
//            date = df.parse(today);
//            gc.setTime(date);
//            gc.add(5,-1);        //���������Կ��ꡢ���¡�����
//            yesterDay = df.format(gc.getTime());    	
//    	}catch(Exception e){
//    		e.printStackTrace();
//    	}
//    	return yesterDay;
//    }
//    
//    public static String timeInfo()
//    {
//        return getNowTime("yyyy-MM-dd HH:mm:ss") + ":";
//    }
//    
//    public static long getQuot(GregorianCalendar gc1,GregorianCalendar gc2)
//    {
//    	return (gc2.getTimeInMillis()-gc1.getTimeInMillis())/1000;
//    }
//    
//    public static long getQuot(String time1, String time2){
//    	  long quot = 0;
//    	  SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
//    	  try {
//    	   Date date1 = ft.parse( time1 );
//    	   Date date2 = ft.parse( time2 );
//    	   quot = date1.getTime() - date2.getTime();
//    	   quot = quot / 1000 / 60 / 60 / 24;
//    	  } catch (Exception e) {
//    	   e.printStackTrace();
//    	  }
//    	  return quot;
//    }
//    
//    
//    //�����ʼʱ�䡢�ڼ����㡢ʱ����������ں�ʱ��
//    public String getTime(String startTime,int i,int interval,int n){  //��n�滻12
//    	String riqi = "";
//    	String selSShi="",selSFen="";
//    	int sHour=0,sMinute=0;
//    	
//    	selSShi = startTime.substring(11,13);
//        selSFen = startTime.substring(14,startTime.length()); 
//        try{
//           sHour = Integer.parseInt(selSShi);
// 	       sMinute = Integer.parseInt(selSFen);
// 	    
//    	   //���ʱ����
//           String timedate = "",time="";
//           int count = 0,k = 0;  
//           if(sMinute%interval==0){
//               if((sMinute/interval+ i)%n==0){  
//                   count = (sHour + (sMinute/interval + i)/n)%24;
//                   if(sHour==0){
//                       if(count==0){
//                          if(i>0&&(i%n==0)){
//                             k++;
//                             Date date = null;
//                             SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//                             GregorianCalendar gc = new GregorianCalendar();
//                             date = df.parse(startTime.substring(0,10));
//                             gc.setTime(date);
//                             gc.add(5,k);        //���������Կ��ꡢ���¡�����
//                             time = df.format(gc.getTime());
//                          }else{
//                             Date date = null;
//                             SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//                             GregorianCalendar gc = new GregorianCalendar();
//                             date = df.parse(startTime.substring(0,10));
//                             gc.setTime(date);
//                             gc.add(5,k);        //���������Կ��ꡢ���¡�����
//                             time = df.format(gc.getTime());
//                          }
//                          timedate = time;
//                       }else{
//                         Date date = null;
//                         SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//                         GregorianCalendar gc = new GregorianCalendar();
//                         date = df.parse(startTime.substring(0,10));
//                         gc.setTime(date);
//                         gc.add(5,k);        //���������Կ��ꡢ���¡�����
//                         time = df.format(gc.getTime());
//                         timedate = time;
//                       }  
//                   }else{
//                      if(count==0){
//                         k++;
//                         Date date = null;
//                         SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//                         GregorianCalendar gc = new GregorianCalendar();
//                         date = df.parse(startTime.substring(0,10));
//                         gc.setTime(date);
//                         gc.add(5,k);        //���������Կ��ꡢ���¡�����
//                         time = df.format(gc.getTime());
//                         timedate = time;
//                      }else{
//                         Date date = null;
//                         SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//                         GregorianCalendar gc = new GregorianCalendar();
//                         date = df.parse(startTime.substring(0,10));
//                         gc.setTime(date);
//                         gc.add(5,k);        //���������Կ��ꡢ���¡�����
//                         time = df.format(gc.getTime());
//                         timedate = time;
//                      }
//                   }
//               	   if(count < 10){
//               		  riqi=timedate + " " + "0" + count + ":0" + ((sMinute/interval + i)*interval)%60;
//               	   }else{
//               	      riqi=timedate + " " + count + ":0" + ((sMinute/interval + i)*interval)%60;
//               	   } 
//               }else{   
//                   count = (sHour + (sMinute/interval + i)/n)%24;
//                   if(sHour==0){
//                       if(count==0){
//                          if(i>0&&(i%n==0)){
//                             k++;
//                             Date date = null;
//                             SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//                             GregorianCalendar gc = new GregorianCalendar();
//                             date = df.parse(startTime.substring(0,10));
//                             gc.setTime(date);
//                             gc.add(5,k);        //���������Կ��ꡢ���¡�����
//                             time = df.format(gc.getTime());
//                          }else{
//                             Date date = null;
//                             SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//                             GregorianCalendar gc = new GregorianCalendar();
//                             date = df.parse(startTime.substring(0,10));
//                             gc.setTime(date);
//                             gc.add(5,k);        //���������Կ��ꡢ���¡�����
//                             time = df.format(gc.getTime());
//                          }
//                          timedate = time;
//                       }else{
//                         Date date = null;
//                         SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//                         GregorianCalendar gc = new GregorianCalendar();
//                         date = df.parse(startTime.substring(0,10));
//                         gc.setTime(date);
//                         gc.add(5,k);        //���������Կ��ꡢ���¡�����
//                         time = df.format(gc.getTime());
//                         timedate = time;
//                       }  
//                   }else{
//                      if(count==0){
//                         k++;
//                         Date date = null;
//                         SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//                         GregorianCalendar gc = new GregorianCalendar();
//                         date = df.parse(startTime.substring(0,10));
//                         gc.setTime(date);
//                         gc.add(5,k);        //���������Կ��ꡢ���¡�����
//                         time = df.format(gc.getTime());
//                         timedate = time;
//                      }else{
//                         Date date = null;
//                         SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//                         GregorianCalendar gc = new GregorianCalendar();
//                         date = df.parse(startTime.substring(0,10));
//                         gc.setTime(date);
//                         gc.add(5,k);        //���������Կ��ꡢ���¡�����
//                         time = df.format(gc.getTime());
//                         timedate = time;
//                      }
//                   }
//               	if(count < 10){
//               	    if(((sMinute/interval + i)*interval)%60 < 10){
//               	    	riqi=timedate + " " + "0" + count + ":" + "0" + ((sMinute/interval + i)*interval)%60;
//               	    }else{
//               	    	riqi=timedate + " " + "0" + count + ":" + ((sMinute/interval + i)*interval)%60;
//               	    }
//               	}else{
//               	    if(((sMinute/interval + i)*interval)%60 < 10){
//               	    	riqi=timedate + " " + count + ":" + "0" + ((sMinute/interval + i)*interval)%60;
//               	    }else{
//               	    	riqi=timedate + " " + count + ":" + ((sMinute/interval + i)*interval)%60;
//               	    }
//               	}
//              }
//           }else{  //sMinute%5!=0
//            if((sMinute/interval + 1 + i)%n==0){
//               	count = (sHour + (sMinute/interval + 1 + i)/n)%24;
//               	if(sHour==0){
//                       if(count==0){
//                          if(i>0&&(i%n==0)){
//                             k++;
//                             Date date = null;
//                             SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//                             GregorianCalendar gc = new GregorianCalendar();
//                             date = df.parse(startTime.substring(0,10));
//                             gc.setTime(date);
//                             gc.add(5,k);        //���������Կ��ꡢ���¡�����
//                             time = df.format(gc.getTime());
//                          }else{
//                             Date date = null;
//                             SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//                             GregorianCalendar gc = new GregorianCalendar();
//                             date = df.parse(startTime.substring(0,10));
//                             gc.setTime(date);
//                             gc.add(5,k);        //���������Կ��ꡢ���¡�����
//                             time = df.format(gc.getTime());
//                          }
//                          timedate = time;
//                       }else{
//                         Date date = null;
//                         SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//                         GregorianCalendar gc = new GregorianCalendar();
//                         date = df.parse(startTime.substring(0,10));
//                         gc.setTime(date);
//                         gc.add(5,k);        //���������Կ��ꡢ���¡�����
//                         time = df.format(gc.getTime());
//                         timedate = time;
//                       }  
//                   }else{
//                      if(count==0){
//                         k++;
//                         Date date = null;
//                         SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//                         GregorianCalendar gc = new GregorianCalendar();
//                         date = df.parse(startTime.substring(0,10));
//                         gc.setTime(date);
//                         gc.add(5,k);        //���������Կ��ꡢ���¡�����
//                         time = df.format(gc.getTime());
//                         timedate = time;
//                      }else{
//                         Date date = null;
//                         SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//                         GregorianCalendar gc = new GregorianCalendar();
//                         date = df.parse(startTime.substring(0,10));
//                         gc.setTime(date);
//                         gc.add(5,k);        //���������Կ��ꡢ���¡�����
//                         time = df.format(gc.getTime());
//                         timedate = time;
//                      }
//                   }
//               	if(count < 10){
//               		riqi=timedate + " " + "0" + count + ":0" + ((sMinute/interval + 1 + i)*interval)%60;
//               	}else{
//               		riqi=timedate + " " + count + ":0" + ((sMinute/5 + 1 + i)*5)%60;
//               	} 
//               }else{
//               	count = (sHour + (sMinute/interval + 1 + i)/n)%24;
//               	if(sHour==0){
//                       if(count==0){
//                          if(i>0&&(i%n==0)){
//                             k++;
//                             Date date = null;
//                             SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//                             GregorianCalendar gc = new GregorianCalendar();
//                             date = df.parse(startTime.substring(0,10));
//                             gc.setTime(date);
//                             gc.add(5,k);        //���������Կ��ꡢ���¡�����
//                             time = df.format(gc.getTime());
//                          }else{
//                             Date date = null;
//                             SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//                             GregorianCalendar gc = new GregorianCalendar();
//                             date = df.parse(startTime.substring(0,10));
//                             gc.setTime(date);
//                             gc.add(5,k);        //���������Կ��ꡢ���¡�����
//                             time = df.format(gc.getTime());
//                          }
//                          timedate = time;
//                       }else{
//                         Date date = null;
//                         SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//                         GregorianCalendar gc = new GregorianCalendar();
//                         date = df.parse(startTime.substring(0,10));
//                         gc.setTime(date);
//                         gc.add(5,k);        //���������Կ��ꡢ���¡�����
//                         time = df.format(gc.getTime());
//                         timedate = time;
//                       }  
//                   }else{
//                      if(count==0){
//                         k++;
//                         Date date = null;
//                         SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//                         GregorianCalendar gc = new GregorianCalendar();
//                         date = df.parse(startTime.substring(0,10));
//                         gc.setTime(date);
//                         gc.add(5,k);        //���������Կ��ꡢ���¡�����
//                         time = df.format(gc.getTime());
//                         timedate = time;
//                      }else{
//                         Date date = null;
//                         SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//                         GregorianCalendar gc = new GregorianCalendar();
//                         date = df.parse(startTime.substring(0,10));
//                         gc.setTime(date);
//                         gc.add(5,k);        //���������Կ��ꡢ���¡�����
//                         time = df.format(gc.getTime());
//                         timedate = time;
//                      }
//                   }
//               	if(count < 10){
//               	    if(((sMinute/interval + 1 + i)*interval)%60 < 10){
//               	    	riqi=timedate + " " + "0" + count + ":" + "0" + ((sMinute/interval + 1 + i)*interval)%60;
//               	    }else{
//               	    	riqi=timedate + " " + "0" + count + ":" + ((sMinute/interval + 1 + i)*interval)%60;
//               	    }
//               	}else{
//               	    if(((sMinute/interval + 1 + i)*interval)%60 < 10){
//               	    	riqi=timedate + " " + count + ":" + "0" + ((sMinute/interval + 1 + i)*interval)%60;
//               	    }else{
//               	    	riqi=timedate + " " + count + ":" + ((sMinute/interval + 1 + i)*interval)%60;
//               	    }
//               	} 
//              }
//           }
//        }catch(Exception e){
//  	       e.printStackTrace();
//  	    }
//    	return riqi;
//    }
//    
//    public static String formatGregorianCalendar(GregorianCalendar retTime){
//    	int year = retTime.get(GregorianCalendar.YEAR);
//        int month = retTime.get(GregorianCalendar.MONTH) + 1;
//        int day = retTime.get(GregorianCalendar.DAY_OF_MONTH);
//        int hour = retTime.get(GregorianCalendar.HOUR_OF_DAY);
//        int min = retTime.get(GregorianCalendar.MINUTE);
//        int sec = retTime.get(GregorianCalendar.SECOND);
//        int msec = retTime.get(GregorianCalendar.MILLISECOND);
//
//        String strVal = formatStr(year, 4) + "-" + formatStr(month, 2) + "-" + formatStr(day, 2) + " " + formatStr(hour, 2) + ":" + formatStr(min, 2) + ":" + formatStr(sec, 2);
//        return strVal;
//    }
//    
//    /***************************************************************************
//     * 功能：把整数 i 前补 0 至 len 长的字符串，如果 长度超过了 len ，不做处理直接返回 参数： v，被设置的值 len，长度 返回值：补
//     * 0 后的字符串
//     **************************************************************************/
//    public static String formatStr(int v, int len) {
//        String s = String.valueOf(v);
//        String r = s;
//
//        for (int i = s.length(); i < len; i++) {
//            r = "0" + r;
//        }
//
//        return r;
//    }
//    
//    public static void main(String[] args) {
//
//    }
//}
