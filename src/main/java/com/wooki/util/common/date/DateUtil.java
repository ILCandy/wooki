package com.wooki.util.common.date;

import com.wooki.system.base.Constant;
import org.apache.commons.lang3.time.DateUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

public class DateUtil extends DateUtils {
	
	/**
	 * 依据给定的开始时间、结束时间按求出相应的日期集合
	 * @param beginDate 开始时间
	 * @param endDate  结束时间
	 * @return 日期集合
	 */
	public static List<String> getBetweenDate(String beginDate,String endDate){
		
		  List<String> dateList=new ArrayList<String>();
		try{
		
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Date begin =sdf.parse(beginDate);
			Date end=sdf.parse(endDate);
			if(begin.after(end)){  //开始时间不能大于结束时间
				//System.out.println(beginDate+"不能大于"+endDate);
				return dateList;
			}
			Calendar  beginCalendar=GregorianCalendar.getInstance(); 
			beginCalendar.setTime(begin);
			Calendar  endCalendar=GregorianCalendar.getInstance(); 
			endCalendar.setTime(end);
			int beginYear=beginCalendar.get(Calendar.YEAR);   // 开始年份
			int beginMonth=beginCalendar.get(Calendar.MONTH)+1;  //开始月份
			int beginDay=beginCalendar.get(Calendar.DAY_OF_MONTH);  //开始天
			int endYear=endCalendar.get(Calendar.YEAR);
			int endMonth=endCalendar.get(Calendar.MONTH)+1;   //结束月份
			int endDay=endCalendar.get(Calendar.DAY_OF_MONTH);   //结束天
				for(int i=beginYear;i<=endYear;i++){
					int tempEndMonth=12;
					int tempBeginMonth=1;
					if(i==endYear)
						tempEndMonth=endMonth;
					if(i==beginYear)
						tempBeginMonth=beginMonth;
				    for(int j=tempBeginMonth;j<=tempEndMonth;j++){
                          int tempDays=getDays(i, j);
                          int tempBeginDays=1;
                          if(i==endYear&&j==tempEndMonth)
                        	  tempDays=endDay;
                          if(i==beginYear&&j==tempBeginMonth)
                        	  tempBeginDays=beginDay;
                          for(int k=tempBeginDays;k<=tempDays;k++){
                        	  dateList.add(i+"-"+(j<10?"0"+j:j)+"-"+(k<10?"0"+k:k));
                          }
				    }
				}
			
		}catch(Exception e){
			  dateList.clear();
			e.printStackTrace();
		}
		
		
		return dateList;
	}
	
	//判断是否闰年
	public static boolean isRunNian(int year){
		if((year%4==0&&year%100!=0)||year%400==0)
			return true;
		return false;
	}
	
	//根据年度，月份求出当月的天数
	public static int getDays(int year,int month){
		if(month==1 ||month==3||month==5||month==7||month==8||month==10||month==12)
			return 31;
		else if(month==2){
			if(isRunNian(year))
				return 29;
			else 
				return 28;
		}
		else 
			return 30;
	}
	
	//判断是否是周末
	public  static boolean isWeekend(String date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date time = null;
		try {
			time = df.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		int day = cal.get(Calendar.DAY_OF_WEEK);
		if (day == Calendar.SATURDAY || day == Calendar.SUNDAY) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 格式化日期 
	 * @param date   日期
	 * @param pattern  格式
	 * @return  日期字符串
	 */
	public static String formatDate(Date date,String pattern){
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		return df.format(date);
	}
	/**
	 * 格式化日期 格式为"yyyy-MM-dd"
	 * @param date 日期
	 * @return 日期字符串
	 */
	public static String formatDate(Date date){
		return formatDate(date,"yyyy-MM-dd");
	}
	
	/**
	 * 获得当前日期 指定格式为"yyyy-MM-dd"
	 * @return
	 */
	public static String getCurrentDateStr(){
		return formatDate(new Date());
	}
	
	public static String getCurrentDateStr(String pattern){
		return formatDate(new Date(),pattern);
	}
	
	/**
	 * 获得当前日期，不带时、分、秒
	 * @return
	 */
	public static Date getCurrentDate(){
		Date date =DateUtil.parseDate(DateUtil.formatDate(new Date()));
		return date;
	}
	
	/**
	 * 获得当前日期，带时、分、秒
	 * @return
	 */
	public static Date getCurrentDateTime()
	{
		return new Date();
	}
	
	
	/**
	 * 把字符串类型的日期转换成Date类型
	 * @param strDate
	 * @return
	 */
	public static Date parseDate(String strDate){
		return parseDate(strDate,"yyyy-MM-dd");
	}
	
	/**
	 * 把字符串类型的日期转换成Date类型
	 * @param strDate
	 * @param  转换日期格式 
	 * @return
	 */
	public static Date parseDate(String strDate,String pattern){
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Date date=null;
		try {
		   date=df.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date ;
	}
	
	public static int getYear(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}
	public static int getCurrentYear(){
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.YEAR);
	}
	public static int getLastYear(){
		return getCurrentYear()-1;
	}

	public static Date getLastYearDay1TimeZero(){
		Calendar calendar = aboutYear();
		calendar.add(Calendar.YEAR,-1);
		return calendar.getTime();
	}

	public static Date getCurrentYearDay1TimeZero(){
		Calendar calendar = aboutYear();
		return calendar.getTime();
	}

	public static Calendar aboutYear(){
		Calendar calendar = Calendar.getInstance();
		setZero(calendar);
		calendar.set(Calendar.MONTH,0);
		calendar.set(Calendar.DAY_OF_MONTH,1);
		return calendar;
	}

	
	public static int getMonth(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH)+1;
	}

	
	public static int getDay(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	public static int getCurrentDay(){
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	public static int getMinute(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MINUTE);
	}
	public static int getHour(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.HOUR_OF_DAY);
	}
	
	public static String addDay(String strDate,int days){
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtil.parseDate(strDate));
		cal.add(Calendar.DAY_OF_MONTH, days);
		return DateUtil.formatDate(cal.getTime());
	}
	
	public static String addHour(String strDate,int hours){
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtil.parseDate(strDate));
		cal.add(Calendar.HOUR_OF_DAY, hours);
		return DateUtil.formatDate(cal.getTime(),"yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 比较两个日期相差天数
	 * @param d1
	 * @param d2
	 * @return  两个日期相差的天数
	 */
	public static long getDateDiff(Date d1,Date d2){
	   return  (d1.getTime()   -   d2.getTime())   /   (24   *   60   *   60   *   1000); 
	}
	
	public static String getDateOfWeekMondy(){
		Calendar calendar=Calendar.getInstance(Locale.CHINA);
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return DateUtil.formatDate(calendar.getTime());
	}
	
	public static Map<String, List<String>> getAllWeekdays(Date indate,Date outdate)
	{
		Map<String, List<String>> resultMap = new HashMap<String, List<String>>();
		if (indate == null )
		{
			return resultMap;
		}
		if (outdate == null) //出院日期为空
			outdate = getCurrentDate();
		List<String> dateList = getBetweenDate(formatDate(indate),
				formatDate(outdate));
		int index = 0;
		List<String> list = new ArrayList<String>();
		for (String strdate : dateList)
		{
			index++;
			list.add(strdate);
			if (index > 0 && (index % 7 == 0 || index == dateList.size()))
			{

				if (list.size() != 7)
				{
					String lastdate = list.get(list.size() - 1);
					int count = 0;
					for (int i = list.size(); i < 7; i++)
					{
						count++;
						list.add(addDay(lastdate, count));
					}
				}
				resultMap
						.put((index % 7 == 0 ? index / 7 : index / 7 + 1) + "",
								list);
				list = new ArrayList<String>();
			}
		}

		return resultMap;
	}
	
	/**
	 * 根据时间Date得到秒数字符串
	 * @param date
	 * @return
	 */
	public static String getSecondsByDate(Date date){
		return ""+date.getTime()/1000;
	}

	/**
	 * 通过入院时间、出院时间查询病人住院周期
	 * @param indate
	 * @param outdate
	 * @return
	 */
	public static Map getWeekMap(Date indate, Date outdate)
	{	
		Map<String, String> map = new LinkedHashMap<String, String>();
		if (indate == null )
		{
			return map;
		}
		if (outdate == null) //出院日期为空
			outdate = getCurrentDate();
		List<String> dateList = getBetweenDate(formatDate(indate),formatDate(outdate));
		int days = dateList.size();
		for (int i = 1; i <= (days % 7 == 0 ? days / 7 : days / 7 + 1); i++)
		{
			map.put(i + "", i + "");
		}
		return map;
	}
	
	public static Object[] getOneWeekDays(Date indate, Date outdate, Date date)
	{
		Object[] obj = new Object[2];
		List<String> tempList = new ArrayList<String>();
		String week = "";
		Map<String, List<String>> resultMap = getAllWeekdays(indate, outdate);
		for (Iterator<Entry<String, List<String>>> it = resultMap.entrySet().iterator(); it.hasNext();)
		{
			Entry<String, List<String>> entry = it.next();
			boolean flag = false;
			for (String strdate : entry.getValue())
			{
				if (strdate.equals(formatDate(date)))
				{
					flag = true;
					break;
				}
			}
			if (flag)
			{
				week = entry.getKey();
				tempList = entry.getValue();
				break;
			}

		}
		obj[0] = week;
		obj[1] = tempList;
		return obj;
	}
	
	/**
	 * 把给定的日期转换成体温单需要的日期格式
	 * @param date
	 * @return
	 */
	public static List<String> getOneWeekDaysForTwd(List<String> dateList)
	{
		if(dateList==null||dateList.size()==0)
		{
			return new ArrayList<String>();
		}
		List<String> resultList=new ArrayList<String>() ;
		resultList.add(dateList.get(0));
		for(int i=0;i<dateList.size()-1;i++)
		{
		compareDate(dateList.get(i),dateList.get(i+1),resultList);
		}
		return resultList;
	}
	
	private static void compareDate(String strdate1 ,String strdate2 ,List<String> dateList)
	{
		Date date1=parseDate(strdate1);
		Date date2=parseDate(strdate2);
		int year1=getYear(date1);
		int month1=getMonth(date1);
		int day1=getDay(date1);
		int year2=getYear(date2);
		int month2=getMonth(date2);
		int day2=getDay(date2);
		if(year1!=year2)
		{
			dateList.add(formatDate(date2));
		}
		else if(year1==year2&&month1!=month2)
		{
			dateList.add(formatDate(date2,"MM-dd"));
		}
		else if(year1==year2&&month1==month2)
		{
			dateList.add(formatDate(date2,"dd"));
		}
	}
	 /**
     * @param date   
     * @param format 日期格式
     * @return String
     * @author zhangyong
     * @return String 
     */
	public static String dateToStr(Date date, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		} catch (Exception e) {
    		//System.out.println("Date 转 String 类型失败: " + e);
			return null;
		}
	}

	public static String dateToStr(Date date){
		try {
			String format = "yyyy-MM-dd";
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		} catch (Exception e) {
			//System.out.println("Date 转 String 类型失败: " + e);
			return null;
		}
	}
	/**
	 * 得到当前日期，格式yyyy-MM-dd。
	 * @return String 格式化的日期字符串
	 */
	public static String getCurrDate()
	{
		return getFormattedDate(getDateByString(""));
	}
	
	/**
	 * 对输入的日期按照默认的格式yyyy-MM-dd转换.
	 * @param dtDate 需要进行格式化的日期字符串
	 * @return String 经过格式化后的字符串
	 */
	public static String getFormattedDate(Timestamp dtDate)
	{
		return getFormattedDate(dtDate, "yyyy-MM-dd");
	}

	/**
	 * 根据传入的日期字符串转换成相应的日期对象，如果字符串为空或不符合日期格
	 * 式，则返回当前时间。
	 * @param strDate String 日期字符串
	 * @return java.sql.Timestamp 日期对象
	 * */
	public static Timestamp getDateByString(String strDate)
	{
		if (strDate.trim().equals(""))
		{
			return new Timestamp(System.currentTimeMillis());
		}
		try
		{
			strDate = getFormattedDate(strDate, "yyyy-MM-dd HH:mm:ss") + ".000000000";
			return Timestamp.valueOf(strDate);
		}
		catch (Exception ex)
		{
			return new Timestamp(System.currentTimeMillis());
		}
	}

	/**
	 * 对输入的日期进行格式化, 如果输入的日期是null则返回空串.
	 * @param dtDate java.sql.Timestamp 需要进行格式化的日期字符串
	 * @param strFormatTo String 要转换的日期格式
	 * @return String 经过格式化后的字符串
	 */
	public static String getFormattedDate(Timestamp dtDate, String strFormatTo)
	{
		if (dtDate == null)
		{
			return "";
		}
		if (dtDate.equals(new Timestamp(0)))
		{
			return "";
		}
		strFormatTo = strFormatTo.replace('/', '-');
		try
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
			if (Integer.parseInt(formatter.format(dtDate)) < 1900)
			{
				return "";
			}
			else
			{
				formatter = new SimpleDateFormat(strFormatTo);
				return formatter.format(dtDate);
			}
		}
		catch (Exception e)
		{
			//Common.printLog("转换日期字符串格式时出错;" + e.getMessage());
			return "";
		}
	}
	
	/**
	 * 对输入的日期字符串进行格式化,如果输入的是0000/00/00 00:00:00则返回空串.
	 * @param strDate String 需要进行格式化的日期字符串
	 * @param strFormatTo String 要转换的日期格式
	 * @return String 经过格式化后的字符串
	 */
	public static String getFormattedDate(String strDate, String strFormatTo)
	{
		if (strDate == null || strDate.trim().equals(""))
		{
			return "";
		}
		strDate = strDate.replace('/', '-');
		strFormatTo = strFormatTo.replace('/', '-');
		if (strDate.equals("0000-00-00 00:00:00") || strDate.equals("1800-01-01 00:00:00"))
		{
			return "";
		}
		String formatStr = strFormatTo; //"yyyyMMdd";
		if (strDate == null || strDate.trim().equals(""))
		{
			return "";
		}
		switch (strDate.trim().length())
		{
			case 6 :
				if (strDate.substring(0, 1).equals("0"))
				{
					formatStr = "yyMMdd";
				}
				else
				{
					formatStr = "yyyyMM";
				}
				break;
			case 8 :
				formatStr = "yyyyMMdd";
				break;
			case 10 :
				if (strDate.indexOf("-") == -1)
				{
					formatStr = "yyyy/MM/dd";
				}
				else
				{
					formatStr = "yyyy-MM-dd";
				}
				break;
			case 11 :
				if (strDate.getBytes().length == 14)
				{
					formatStr = "yyyy年MM月dd日";
				}
				else
				{
					return "";
				}
			case 14 :
				formatStr = "yyyyMMddHHmmss";
				break;
			case 19 :
				if (strDate.indexOf("-") == -1)
				{
					formatStr = "yyyy/MM/dd HH:mm:ss";
				}
				else
				{
					formatStr = "yyyy-MM-dd HH:mm:ss";
				}
				break;
			case 21 :
				if (strDate.indexOf("-") == -1)
				{
					formatStr = "yyyy/MM/dd HH:mm:ss.S";
				}
				else
				{
					formatStr = "yyyy-MM-dd HH:mm:ss.S";
				}
				break;
			default :
				return strDate.trim();
		}
		try
		{
			SimpleDateFormat formatter = new SimpleDateFormat(formatStr);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(formatter.parse(strDate));
			formatter = new SimpleDateFormat(strFormatTo);
			return formatter.format(calendar.getTime());
		}
		catch (Exception e)
		{
			//Common.printLog("转换日期字符串格式时出错;" + e.getMessage());
			return "";
		}
	}
	
	//根据出生日期计算年龄
	public static int getAge(Date birthDay) throws Exception { 
        Calendar cal = Calendar.getInstance(); 
  
        if (cal.before(birthDay)) { 
            throw new IllegalArgumentException( "The birthDay is before Now.It's unbelievable!"); 
        } 
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH); 
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); 
        cal.setTime(birthDay); 
  
        int yearBirth = cal.get(Calendar.YEAR); 
        int monthBirth = cal.get(Calendar.MONTH); 
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH); 
  
        int age = yearNow - yearBirth; 
  
        if (monthNow <= monthBirth) { 
            if (monthNow == monthBirth) { 
                if (dayOfMonthNow < dayOfMonthBirth) age--; 
            }else{ 
                age--; 
            } 
        } 
        return age; 
    }
	
	/**
	 * 获取系统时间Timestamp
	 * @return
	 */
	public static Date getSysTimestamp() {
		return new Timestamp(new Date().getTime());
	}
	
	public static void main(String[] args) {
		
		//Date d1 = parseDate("2014-08-03 23:59:59");
		//Date d2 = parseDate("2014-08-04 00:00:00");
		
		////System.out.println("相差天数： " + getDateDiff(d1,d2));
		
		////System.out.println(""+ dateToStr(new Date(),"HH时m分"));
		
		//System.out.println("时间："+DateUtil.dateToStr(new Date(1407340800000L), "yyyy-MM-dd HH:mm:ss"));
	}

	/**
	 * 获取当前月的1号0点
	 * @return
	 */
	public static Date getCurrentMonthDay1(){
		// 当月1号
		return transform().getTime();
	}
	//当前月
	public static int getCurrentMonth(){
//		Calendar calendar = transform();
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.get(calendar.MONTH) + 1;
	}

	// 昨天的年份
	public static int getLastMonthYear(){
		Calendar calendar = calcuLastDay();
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.get(calendar.YEAR);
	}
	// 昨天的月份
	public static int getMonthAboutLastDay(){
		Calendar calendar = calcuLastDay();
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.get(Calendar.MONTH) +1;
	}

	// 昨天
	public static int getLastDay(){
		Calendar calendar = calcuLastDay();
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.get(calendar.DAY_OF_MONTH);
	}

	public static Calendar calcuLastDay(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, 0);
//		calendar.add(Calendar.MONTH,2);
//		calendar.set(Calendar.DAY_OF_MONTH,1);
		calendar.add(Calendar.DAY_OF_MONTH,-1);
		return calendar;
	}

	// 下个月
	public static int getNextMonth(){
//		Calendar calendar = transform();
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.MONTH,1);
		return calendar.get(calendar.MONTH) + 1;
	}

	// 直接获取上个月
	public static int getLastMonth(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.MONTH,-1);
		return calendar.get(calendar.MONTH) + 1;
	}

	// 获取上个月年份
	public static int getYearAboutLastMonth(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.MONTH,-1);
		return calendar.get(calendar.YEAR);
	}


	/**
	 * 获取下个月1号0点
	 * @return
	 */
	public static Date getNextMonthDay1TimeZero(){
		// 下月1号
		Calendar calendar = transform();
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.MONTH, 1);
//		//System.out.println(calendar.getTimeStr());
		return calendar.getTime();
	}

	/**
	 * 上个月一号
	 * @return
	 */
	public static String getLastMonthDay1(String format){
		Calendar calendar = transform();
		calendar.add(Calendar.MONTH,-1);
		calendar.set(Calendar.MILLISECOND, 0);
//		//System.out.println(calendar.getTimeStr());
		SimpleDateFormat s = new SimpleDateFormat(format);
		return s.format(calendar.getTime());
	}
	/**
	 * 上个月一号
	 * @return
	 */
//	public static Date getLastMonthDay1(){
//		return lastMonthDay1(-1).getTime();
//	}
	// 上月一号零点
	public static Date getLastMonthDay1TimeZero(){
		Calendar calendar = lastMonthDay1(-1);
		calendar.set(Calendar.MILLISECOND, 0);
		setTimeZero(calendar);
		return calendar.getTime();
	}

	// 本月一号零点
	public static Date getCurrentMonthDay1TimeZero(){
		Calendar calendar = lastMonthDay1(0);
		calendar.set(Calendar.MILLISECOND, 0);
		setTimeZero(calendar);
		return calendar.getTime();
	}
	// 一号零点前一秒
	public static Date getCurrentMonthDay1TimeZeroBeforeOneSecond(){
		Date date = getCurrentMonthDay1TimeZero();
		date.setTime(date.getTime()-1000);
		return date;
	}

	public static Date getCurrentMonthDay1TimeZeroBeforeTime(Long time){
		Date date = getCurrentMonthDay1TimeZero();
		date.setTime(date.getTime()-time);
		return date;
	}

	//
	private static Calendar lastMonthDay1(int del){
		Calendar calendar = transform();
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.MONTH,del);
		return calendar;
	}

	// 转化
	public static Calendar transform(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return setZero(calendar);
	}

	// 时间零点
	public static Calendar setZero(Calendar calendar){
		return setTimeZero(calendar);
	}

	// 00:00:00
	public static Calendar setTimeZero(Calendar calendar){
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.HOUR_OF_DAY,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		return calendar;
	}

	/**
	 * 昨天零点
	 * @return
	 */
	public static Date getLastDayZero(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.DAY_OF_MONTH,-1);
		return setTimeZero(calendar).getTime();
	}

	/**
	 * 今天零点
	 */
	public static Date getCurrentDayZero(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, 0);
		return setTimeZero(calendar).getTime();
	}

	public static Date getNextDayZero(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.DAY_OF_MONTH,1);
		return setTimeZero(calendar).getTime();
	}

	public static Date getNextMonthBefore5Min(Date date){
//		Calendar calendar = transform();
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.setTime(date);
		// 时间设0
		setTimeZero(calendar);
		// 下个月
		calendar.add(Calendar.MONTH,1);
		// 一号
		calendar.set(Calendar.DAY_OF_MONTH,1);
		// 五分钟前
		calendar.add(Calendar.SECOND,-60 * 5);
		return calendar.getTime();
	}
	public static Date getNextMonthAboutDate(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MILLISECOND, 0);
		// 时间设0
		setTimeZero(calendar);
		// 下个月
		calendar.add(Calendar.MONTH,1);
		// 一号
		calendar.set(Calendar.DAY_OF_MONTH,1);
		return calendar.getTime();
	}

	public static Date getCurrentMonthAboutDate(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MILLISECOND, 0);
		// 时间设0
		setTimeZero(calendar);
		// 下个月
		calendar.add(Calendar.MONTH,0);
		// 一号
		calendar.set(Calendar.DAY_OF_MONTH,1);
		
		return calendar.getTime();
	}

	// 昨天的日期字符串
	public static String getLastDayStr(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH,-1);
		return dateToStr(calendar.getTime());
	}

	public static Date getBefore7DayTimeZero(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// 7天前的日期
		calendar.add(Calendar.DAY_OF_MONTH,-7);
		calendar.set(Calendar.MILLISECOND,0);
		calendar.set(Calendar.HOUR_OF_DAY,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		return calendar.getTime();
	}

	public static Long currentDayTimeZero(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		calendar.set(Calendar.MILLISECOND,0);
		Long time = calendar.getTime().getTime();
		return time;
	}

	public static Long nowTime(){
		Long time = new Date().getTime();
		System.out.println(time);
		return time;
	}

	// 什么时间点的时间戳
	public static Long taskTime(String timeStr){
		System.out.println("timeStr = "+timeStr);
		// 获取日期的 时间戳
		Timestamp timestamp = getDateByString(timeStr);
		String zeroTimeStr = timeStr.split(" ")[0] + " 00:00:00";
		Timestamp timestamp2 = getDateByString(zeroTimeStr);
		// 减去当天零点
		return timestamp.getTime()-timestamp2.getTime();
	}

	// 当天已经过的时间戳
	public static Long nowDayTime(){
		return nowTime() - currentDayTimeZero();
	}

	public static Integer getDay(){
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	// 将周一到周末转换成 数字字符串 1,2,3,4,5,6,7
	public static String getDaysToNumStr(String dayStr){
		// 分割
		String[] days = dayStr.split(",");
		StringBuffer stringBuffer = new StringBuffer();
		for(int i = 0;i<days.length;i++){
			// 星期一 -> 2 . .......
			stringBuffer.append(Constant.map.get(days[i]));
			if(i!=days.length-1)
				stringBuffer.append(",");
		}
		// 1,2,3,4,5,6,7
		return stringBuffer.toString();
	}

	// 将周一到周末转换成 数字 数组 [1,2,3,4,5,6,7]
	public static String[] getDaysToArr(String dayStr){
		// 分割
		String[] days = dayStr.split(",");
		String[] newDays = new String[days.length];
		for(int i = 0;i<days.length;i++){
			// 星期一 -> 2 . .......
			newDays[i] = Constant.map.get(days[i]);
			System.out.print(newDays[i]+" ");
		}

		// [1,2,3,4,5,6,7]
		return newDays;
	}

	public static String dealDateFormat(String oldDateStr) throws ParseException{
		//此格式只有  jdk 1.7才支持  yyyy-MM-dd'T'HH:mm:ss.SSSXXX
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");  //yyyy-MM-dd'T'HH:mm:ss.SSSZ
		Date  date = df.parse(oldDateStr);
		SimpleDateFormat df1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
		Date date1 =  df1.parse(date.toString());
		DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//  Date date3 =  df2.parse(date1.toString());
		return df2.format(date1);
	}

//	public
}
