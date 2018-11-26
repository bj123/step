package com.aiwsport.core.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数据类型转换
 * @author yangjian9
 * @date 2018年3月28日 下午11:17:06
 */
public final class DataTypeUtils {
	private static Logger logger = LoggerFactory.getLogger(DataTypeUtils.class);
	private DataTypeUtils(){
		
	}
	/* 日期样式 */
	private static final String[] DATE_PATTERNS = new String[] {
		"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "HH:mm:ss", "yyyyMMddHHmmss", "yyyyMMdd", "yyyyMM", "yyyyMMddHH", "yyyy", "yyyy-MM-dd'T'HH:mm:ss'Z'",
	};

	public static final String DATETIME_FORMAT = DATE_PATTERNS[0];
	public static final String DATE_FORMAT = DATE_PATTERNS[1];
	public static final String TIME_FORMAT = DATE_PATTERNS[2];
	public static final String DATA_FORMAT_YYYYMMDDHHMMSS = DATE_PATTERNS[3];
	public static final String DATA_FORMAT_YYYYMMDD = DATE_PATTERNS[4];
	public static final String DATA_FORMAT_YYYYMM = DATE_PATTERNS[5];
	public static final String DATA_FORMAT_YYYYMMDDHH = DATE_PATTERNS[6];
	public static final String DATA_FORMAT_YYYY = DATE_PATTERNS[7];
	public static final String DATA_FORMAT_yyyy_mm_dd_t_hhmmss_z = DATE_PATTERNS[8];
	public static final String DATA_FORMAT_YYYY_MM_DD_HH_MM_SS = DATETIME_FORMAT;
	public static final String DATA_FORMAT_YYYY_MM_DD = DATE_FORMAT;


	/**
	 * @return the datePatterns
	 */
	public static String[] getDatePatterns() {
		return DATE_PATTERNS.clone();
	}

	/**
	 * 整数转换为字符串
	 * @param number
	 * @return
	 */
	public static String toStr(long number) {
		DecimalFormat integerFormatter = new DecimalFormat("#");
		integerFormatter.setGroupingUsed(false);
		integerFormatter.setParseIntegerOnly(true);
		return integerFormatter.format(number);
	}
	
	/**
	 * 字符串转换为布尔型
	 * @param str
	 * @return
	 */
	public static boolean toBoolean(String str) {
		return "true".equalsIgnoreCase(str) || "yes".equalsIgnoreCase(str) || "on".equalsIgnoreCase(str) || "1".equals(str);
	}
		
	/**
	 * 字符串转换为整型
	 * @param str
	 * @param defaultValue
	 * @return
	 */
	public static int toInt(String str, int defaultValue) {
		return NumberUtils.toInt(str, defaultValue);
	}
	
	/**
	 * 字符串转换为整型
	 * @param str
	 * @return
	 */
	public static int toInt(String str) {
		return toInt(str, 0);
	}
	
	/**
	 * 转化为长整形对象
	 * @param value
	 * @return
	 */
	public static Long toLong(long value) {
		return Long.valueOf(value);
	}
	
	/**
	 * 如果value为空，则默认为0
	 * @param value
	 * @return
	 * @author liujun9
	 * @date 2015年8月17日 下午5:22:00
	 */
	public static Long toLong(Long value) {
		if (value == null) {
			return Long.valueOf(0);
		} else {
			return value;
		}
	}
	
	/**
	 * 如果value为空，则默认为0
	 * @param value
	 * @return
	 * @author yangjian5
	 * @date 2015年8月27日 下午7:22:00
	 */
	public static Integer toInteger(Integer value) {
		if (value == null) {
			return Integer.valueOf(0);
		} else {
			return value;
		}
	}
	public static Integer toInteger(Long value) {
		if (value == null) {
			return Integer.valueOf(0);
		} else {
			return Integer.valueOf(String.valueOf(value));
		}
	}
	
	/**
	 * 字符串转换为长整型
	 * @param str
	 * @param defaultValue
	 * @return
	 */
	public static long toLong(String str, long defaultValue) {
		return NumberUtils.toLong(str, defaultValue);
	}
	
	/**
	 * 字符串转换为长整型
	 * @param str
	 * @return
	 */
	public static long toLong(String str) {
		return toLong(str, 0L);
	}
	
	/**
	 * Object对象类型转换为长整型
	 * @param obj
	 * @return
	 */
	public static long toLong(Object obj) {
		return toLong(String.valueOf(obj));
	}
	
	/**
	 * 字符串转换为双精度
	 * @param str
	 * @param defaultValue
	 * @return
	 */
	public static double toDouble(String str, double defaultValue) {
		return NumberUtils.toDouble(str, defaultValue);
	}
	
	/**
	 * 字符串转换为双精度
	 * @param str
	 * @return
	 */
	public static double toDouble(String str) {
		return toDouble(str, 0.0D);
	}
	
	/**
	 * 字符串转换为浮点数
	 * @param str
	 * @param defaultValue
	 * @return
	 */
	public static float toFloat(String str, float defaultValue) {
		return NumberUtils.toFloat(str, defaultValue);
	}
	
	/**
	 * 字符串转换为浮点数
	 * @param str
	 * @return
	 */
	public static float toFloat(String str) {
		return toFloat(str, 0.0f);
	}
	
	/**
	 * 字符串转换为时间Timestamp
	 * @param str
	 * @return
	 */
	public static Timestamp toTimestamp(String str) {
		long millis = 0L;
		
		try {
			millis = DateUtils.parseDate(str, DATE_PATTERNS).getTime();
		} catch (Exception e) {
			millis = System.currentTimeMillis();
			logger.error(e.getMessage(),e);
		}
		return new Timestamp(millis);
	}
	
	/**
	 * Date为时间Timestamp
	 * @param date
	 * @return
	 */
	public static Timestamp toTimestamp(Date date){
		return toTimestamp(formatDate(date, DATE_PATTERNS[0] ));
	}
	
	/**
	 * 字符串转换为时间Date
	 * @param str
	 * @return
	 */
	public static Date toDate(String str) {
		Date date = null;
		
		try {
			date = DateUtils.parseDate(str, DATE_PATTERNS);
		} catch (Exception e) {
			date = new Date();
			logger.error(e.getMessage(),e);
		}
		
		return date;
	}
	
	/**
	 * 格式化日期对象
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		return new SimpleDateFormat(DATE_FORMAT,Locale.getDefault()).format(date);
	}
	
	/**
	 * 格式化日期对象 yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @return
	 */
	public static String formatDate_yyyy_mm_dd_hh_mm_ss(Date date){
		return new SimpleDateFormat(DATETIME_FORMAT,Locale.getDefault()).format(date);
	}
	
	/**
	 * 格式化时间对象
	 * @param time
	 * @return
	 */
	public static String formatTime(Time time) {
		return new SimpleDateFormat(TIME_FORMAT,Locale.getDefault()).format(time);
	}

	/**
	 * 格式化时间戳对象
	 * @param timestamp
	 * @return
	 */
	public static String formatDateTime(Timestamp timestamp) {
		return new SimpleDateFormat(DATETIME_FORMAT,Locale.getDefault()).format(timestamp);
	}

	/**
	 * 格式化时间戳对象
	 * @param timestamp
	 * @return
	 */
	public static String formatDateTime_yyyy_mm_dd_hh_mm_ss(Timestamp timestamp) {
		return formatDateTime(timestamp);
	}
	
	/**
	 * 格式化日期对象
	 * @param date
	 * @param format
	 * @return 
	 */
	public static String formatDate(Date date, SimpleDateFormat format) {
		return format.format(date);
	}

	/**
	 * 格式化日期对象
	 * @param date
	 * @param format
	 * @return 
	 */
	public static String formatDate(String date, SimpleDateFormat format) {
		return new SimpleDateFormat(format.toPattern(),Locale.getDefault()).format(toDate(date));
	}
	
	/**
	 * 格式化日期对象
	 * @param date
	 * @param pattern
	 * @return 
	 */
	public static String formatDate(Date date, String pattern) {
		return new SimpleDateFormat(pattern,Locale.getDefault()).format(date);
	}
	
	/**
	 * 格式化日期对象
	 * @param date
	 * @param pattern
	 * @return 
	 */
	public static String formatDate(String date, String pattern) {
		return new SimpleDateFormat(pattern,Locale.getDefault()).format(toTimestamp(date));
	}

	/**
     * 获取当前系统时间
     * @return 返回值为Timestamp类型
     * @throws Exception
     */
    public static Timestamp getCurrentDate() throws Exception {
        return new Timestamp(System.currentTimeMillis());
    }
    
    /**
     * 获取当前系统时间，格式为：yyyy-MM-dd HH:mm:ss
     * @return 
     * @throws Exception 
     */
    public static String formatCurDateTime() throws Exception {
    	return formatDate(getCurrentDate(), new SimpleDateFormat(DATETIME_FORMAT,Locale.getDefault()));
    }
    
    /**
     * 获取当前系统时间，格式为：yyyymmddhhmmss
     * @return 
     * @throws Exception 
     */
    public static String formatCurDateTime_yyyymmddhhmmss() throws Exception {
    	return formatDate(getCurrentDate(), new SimpleDateFormat(DATA_FORMAT_YYYYMMDDHHMMSS,Locale.getDefault()));
    }
    
    /**
     * 获取当前时间，格式为：yyyy-MM-dd
     * @return 
     */
    public static String formatCurDateTime_yyyy_mm_dd() {
    	return formatDate(new Date(), new SimpleDateFormat(DATA_FORMAT_YYYY_MM_DD,Locale.getDefault()));
    }
    
    /**
     * 获取当前时间，格式为：yyyyMMdd
     * @return 
     */
    public static String formatCurDateTime_yyyymmdd() {
    	return formatDate(new Date(), new SimpleDateFormat(DATA_FORMAT_YYYYMMDD,Locale.getDefault()));
    }
    
    /**
     * 格式化时间为：yyyy
     * @param timestamp
     * @return 
     */
    public static String formatDate_yyyy(Timestamp timestamp) {
    	return formatDate(timestamp, new SimpleDateFormat(DATA_FORMAT_YYYY,Locale.getDefault()));
    }
    
    /**
     * 格式化时间为：yyyymm
     * @param timestamp
     * @return 
     */
    public static String formatDate_yyyymm(Timestamp timestamp) {
    	return formatDate(timestamp, new SimpleDateFormat(DATA_FORMAT_YYYYMM,Locale.getDefault()));
    }
    
    /**
     * 格式化时间为yyyy-mm-dd
     * @param timestamp
     * @return
     */
    public static String formatTimeStamp_yyyy_mm_dd(Timestamp timestamp) {
    	return new SimpleDateFormat(DATA_FORMAT_YYYY_MM_DD,Locale.getDefault()).format(timestamp);
    }
    
	/**
	 * 增加或减去指定月数
	 * @param timestamp 时间
	 * @param month 指定的月数量
	 * @return
	 */
	public static Timestamp addOrMinusMonth(Timestamp timestamp, int month) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(timestamp);
		cal.add(Calendar.MONTH, month);
		return new Timestamp(cal.getTimeInMillis());
	}
	
	/**
	 * 增加或减去指定小时数
	 * @param timestamp 指定时间
	 * @param hour 指定的小时
	 * @return 
	 */
	public static Timestamp addOrMinusHour(Timestamp timestamp, int hour) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(timestamp);
		cal.add(Calendar.HOUR, hour);
		return new Timestamp(cal.getTimeInMillis());
	}
    
    /**
     * 增加或减去指定天数
     * @param timestamp 指定时间
     * @param day 指定的天数
     * @return 
     */
    public static Timestamp addOrMinusDay(Timestamp timestamp, int day) {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(timestamp);
    	cal.add(Calendar.DAY_OF_MONTH, day);
    	return new Timestamp(cal.getTimeInMillis());
    }
    
	/**
	 * 两个时间相差秒数
	 * @param start 开始时间
	 * @param end 结束时间
	 * @return
	 */
	public static long betweenSecond(Date start, Date end) {
		return (end.getTime() - start.getTime()) / 1000;
	}

	/**
	 * 两个时间相差分钟数
	 * @param start 开始时间
	 * @param end 结束时间
	 * @return
	 */
	public static long betweenMinute(Date start, Date end) {
		return (end.getTime() - start.getTime()) / 60 / 1000;
	}

	/**
	 * 两个时间相差小时数
	 * @param start 开始时间
	 * @param end 结束时间
	 * @return
	 */
	public static long betweenHour(Date start, Date end) {
		return (end.getTime() - start.getTime()) / 3600 / 1000;
	}
	
	/**
	 * 两个时间相差天数
	 * @param start 开始时间
	 * @param end 结束时间
	 * @return
	 */
	public static long betweenDay(Date start, Date end) {
		return (end.getTime() - start.getTime()) / 3600 / 24 / 1000;
	}
	
	/**
	 * 根据指定日期获取下一天的开始的时间点
	 * @param date
	 * @return 
	 */
	public static Timestamp getStartMillSecondOfNextDay(Date date) {
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(date);
		rightNow.set(Calendar.DAY_OF_MONTH, rightNow.get(Calendar.DAY_OF_MONTH) + 1);
		rightNow.set(Calendar.HOUR_OF_DAY, 0);
		rightNow.set(Calendar.MILLISECOND, 0);
		rightNow.set(Calendar.SECOND, 0);
		rightNow.set(Calendar.MINUTE, 0);
		rightNow.set(Calendar.MONTH, rightNow.get(Calendar.MONTH));
		return new Timestamp(rightNow.getTimeInMillis());
	}
	
	/**
	 * 得到本月最后一天
	 * @param timestamp
	 * @return 
	 */
	public static Timestamp getLastDayOfMonth(Timestamp timestamp) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(timestamp);
		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.DAY_OF_MONTH, 0);
		return new Timestamp(cal.getTimeInMillis());
	}
	
	/**
	 * 在指定日期增加某个天数后日期
	 * @param date 指定时间
	 * @param addDay 待增加的天数
	 * @param pattern 格式化方式
	 * @return 
	 */
	public static String addOrMinusDays(String date, int addDay, String pattern) {
		Timestamp tsDate = Timestamp.valueOf(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(tsDate);
		cal.add(Calendar.DAY_OF_MONTH, addDay);
		
		Timestamp tsEndDateAdd = new Timestamp(cal.getTimeInMillis());
		return formatDate(tsEndDateAdd, pattern);
	}
	
	/**
	 * 根据指定日期获取该月的最后一天的最后时间点
	 * @param date 指定时间
	 * @return 
	 */
	public static Timestamp getLastMillSecondOfMonth(Date date) {
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(date);
		rightNow.set(Calendar.MONTH, rightNow.get(Calendar.MONTH));
		rightNow.set(Calendar.DAY_OF_MONTH, rightNow.getActualMaximum(Calendar.DAY_OF_MONTH));
		rightNow.set(Calendar.HOUR_OF_DAY, 23);
		rightNow.set(Calendar.MINUTE, 59);
		rightNow.set(Calendar.SECOND, 59);
		rightNow.set(Calendar.MILLISECOND, 59);
		return new Timestamp(rightNow.getTimeInMillis());
	}
	
	/**
	 * 获得指定时间的当前天的开始时间
	 * @param date 指定时间
	 * @return 
	 */
	public static Date getStartMillSecondOfDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
	
	/**
	 * 获得指定时间的当前天最后时间
	 * @param date 指定时间
	 * @return 
	 */
	public static Timestamp getLastMillSecondOfDay(Date date) {
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(date);
		rightNow.set(Calendar.HOUR_OF_DAY, 23);
		rightNow.set(Calendar.MINUTE, 59);
		rightNow.set(Calendar.SECOND, 59);
		rightNow.set(Calendar.MILLISECOND, 59);
		return new Timestamp(rightNow.getTimeInMillis());
	}

	/**
	 * 获得指定时间的当前小时开始时间
	 * @param date 指定时间
	 * @return 
	 */
	public static Date getStartMillSecondOfHour(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
	
	/**
	 * 在一个时间上加上对应的月份数
	 * @param ti long
	 * @param i int
	 * @return Date
	 * @throws Exception
	 */
	public static Date addOrMinusMonth(long ti, int i) throws Exception {
		Date rtn = null;
		GregorianCalendar cal = new GregorianCalendar();
		Date date = new Date(ti);
		cal.setTime(date);
		cal.add(GregorianCalendar.MONTH, i);
		rtn = cal.getTime();
		return rtn;
	}
	
	/**
	 * 在一个时间上加上或减去天数
	 * @param ti long
	 * @param i int
	 * @return Date
	 */
	public static Date addOrMinusDays(long ti, int i) {
		Date rtn = null;
		GregorianCalendar cal = new GregorianCalendar();
		Date date = new Date(ti);
		cal.setTime(date);
		cal.add(GregorianCalendar.DAY_OF_MONTH, i);
		rtn = cal.getTime();
		return rtn;
	}
	
	/**
	 * 在一个时间上加上或减去小时
	 * @param ti long
	 * @param i int
	 * @return Date
	 */
	public static Date addOrMinusHours(long ti, int i) {
		Date rtn = null;
		GregorianCalendar cal = new GregorianCalendar();
		Date date = new Date(ti);
		cal.setTime(date);
		cal.add(GregorianCalendar.HOUR, i);
		rtn = cal.getTime();
		return rtn;
	}

	/**
	 * 在一个时间上加上或减去分钟
	 * @param ti long
	 * @param i int
	 * @return Date
	 */
	public static Date addOrMinusMinutes(long ti, int i) {
		Date rtn = null;
		GregorianCalendar cal = new GregorianCalendar();
		Date date = new Date(ti);
		cal.setTime(date);
		cal.add(GregorianCalendar.MINUTE, i);
		rtn = cal.getTime();
		return rtn;
	}
	
	/**
	 * 在一个时间上加上或减去分钟
	 * @param timestamp Timestamp
	 * @param i int
	 * @return Date
	 */
	public static Timestamp addOrMinusMinutes(Timestamp timestamp, int i) {
		return toTimestamp(addOrMinusMinutes(timestamp.getTime(), i));
	}

	/**
	 * 在一个时间上加上或减去秒数
	 * @param ti long
	 * @param i int
	 * @return Date
	 */
	public static Date addOrMinusSecond(long ti, int i) {
		Date rtn = null;
		GregorianCalendar cal = new GregorianCalendar();
		Date date = new Date(ti);
		cal.setTime(date);
		cal.add(GregorianCalendar.SECOND, i);
		rtn = cal.getTime();
		return rtn;
	}

	/**
	 * 获取一天的开始
	 * @param ts
	 * @return
	 */
	public static Timestamp getStartOfDay(Timestamp ts) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(ts);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return new Timestamp(cal.getTimeInMillis());
	}

	/**
	 * 获取一天的结束
	 * @param ts
	 * @return
	 */
	public static Timestamp getEndOfDay(Timestamp ts) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(ts);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.add(Calendar.SECOND, -1);
		return new Timestamp(cal.getTimeInMillis());
	}

	/**
	 * 获取指定时间当前周的星期一的日期
	 * @param cal
	 * @return
	 */
	public static Calendar getCurrentMonday(Calendar cal) {
		int dow = cal.get(Calendar.DAY_OF_WEEK);

		dow--;
		if (dow <= 0) {
			dow = 7;
		}

		if (dow > 1) {
			cal.add(Calendar.DAY_OF_MONTH, 1 - dow);
		}
		return cal;
	}

	/**
	 * 获取执行时间当前周的星期一
	 * @param ts
	 * @return
	 */
	public static Timestamp getCurrentMonday(Timestamp ts) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(ts);

		cal = getCurrentMonday(cal);

		return new Timestamp(cal.getTimeInMillis());
	}

	/**
	 * 获取指定时间的上周的星期一
	 * @param ts
	 * @return
	 */
	public static Timestamp getPrevMonday(Timestamp ts) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(ts);

		cal = getCurrentMonday(cal);

		cal.add(Calendar.DAY_OF_MONTH, -7);

		return new Timestamp(cal.getTimeInMillis());
	}

	/**
	 * 获取指定时间上周日
	 * @param today
	 * @return
	 */
	public static Timestamp getPrevSunday(Timestamp today) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);

		cal = getCurrentMonday(cal);

		cal.add(Calendar.DAY_OF_MONTH, -1);

		return new Timestamp(cal.getTimeInMillis());
	}

	/**
	 * 获取指定时间加上addMonth的那个月的开始时间
	 * @param ts
	 * @return
	 */
	public static Timestamp getStartOfMonth(Timestamp ts, int addMonth) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(ts);
		cal.add(Calendar.MONTH, addMonth);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return new Timestamp(cal.getTimeInMillis());
	}
	
	/**
	 * 获取本月第一天开始时间
	 * @return
	 * @throws Exception 
	 */
	public static Timestamp getStartOfCurrMonth() throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getCurrentDate());
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return new Timestamp(cal.getTimeInMillis());
	}

	/**
	 * 获取指定时间加上addMonth的那个月的结束时间
	 * @param ts
	 * @return
	 */
	public static Timestamp getEndOfMonth(Timestamp ts, int addMonth) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(ts);
		cal.add(Calendar.MONTH, addMonth);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return new Timestamp(cal.getTimeInMillis());
	}

	/**
	 * 将clob转换为string
	 * @param clob
	 * @return
	 */
	public static String clobToString(Clob clob) {
		Reader inStreamDoc = null;
		try {
			// 以 java.io.Reader 对象形式（或字符流形式）
			// 检索此 Clob 对象指定的 CLOB 值 --Clob的转换
			if (clob != null) {
				// 取得clob的长度
				char[] tempDoc = new char[(int) clob.length()];
				
				inStreamDoc = clob.getCharacterStream();	
				while((inStreamDoc.read(tempDoc))==-1){
					return new String(tempDoc);
				}
			}
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		} catch (SQLException es) {
			logger.error(es.getMessage(),es);
		} catch (Exception e) {
			logger.error("clobToString caught Exception :", e);
		} finally {
			if (inStreamDoc != null) {
				try {
					inStreamDoc.close();
				} catch (IOException e) {
					logger.error(e.getMessage(),e);
				}
			}
		}
		return null;
	}
		
	/**
	 * 判断是否合法EMAIL
	 * @param email
	 * @return 
	 */
	public static boolean isValidEmail(String email) {
		// p{Alpha}:内容是必选的，和字母字符[\p{Lower}\p{Upper}]等价。如：200896@163.com不是合法的。
		// w{2,15}: 2~15个[a-zA-Z_0-9]字符；w{}内容是必选的。 如：dyh@152.com是合法的。
		// [a-z0-9]{3,}：至少三个[a-z0-9]字符,[]内的是必选的。如：dyh200896@16.com是不合法的。
		// [.]:'.'号时必选的。如：dyh200896@163com是不合法的。
		// p{Lower}{2,}小写字母，两个以上。如：dyh200896@163.c是不合法的。
		String regex = "\\p{Alpha}\\w{2,15}[@][a-z0-9]{3,}[.]\\p{Lower}{2,}";
		Pattern mailPattern = Pattern.compile(regex);
		Matcher matcher = mailPattern.matcher(email);
		return matcher.matches();
	}
	
	/**
	 * 获取开始时间到结束时间的年月列表，yyyymm格式
	 * @param startTime
	 * @param endTime
	 * @return 
	 */
	public static List<String> getTimeBetween(String startTime, String endTime) {
		List<String> result = new ArrayList<String>();
		if (StringUtils.isBlank(startTime) && StringUtils.isBlank(endTime)) {
			return result;
		}
		
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
			start.setTime(DataTypeUtils.toDate(startTime.split(" ")[0]));
			start.set(Calendar.DAY_OF_MONTH, 1);
			end.setTime(DataTypeUtils.toDate(endTime));
		} else if (StringUtils.isNotBlank(startTime)) {
			start.setTime(DataTypeUtils.toDate(startTime.split(" ")[0]));
			start.set(Calendar.DAY_OF_MONTH, 1);
			try {
				end.setTime(new Date(DataTypeUtils.getCurrentDate().getTime()));
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
		} else {
			start.setTime(DataTypeUtils.toDate(endTime));
			start.set(Calendar.DAY_OF_MONTH, 1);
		}

		while (start.before(end)) {
			result.add(DataTypeUtils.formatDate(start.getTime(), new SimpleDateFormat(DataTypeUtils.DATA_FORMAT_YYYYMM,Locale.getDefault())));
			start.add(Calendar.MONTH, 1);
		}
		return result;
	}
	
	/**
	 * turn long[] to string like “[0,0,0,0,0]”
	 * @param longList
	 * @return string
	 */
	public static String list2String(long[] longList){
		StringBuffer re = new StringBuffer("[");
		for(int i=0;i<longList.length;i++){
			if(i==0)re.append(longList[i]);
			else re.append(",").append(longList[i]);
		}
		re.append("]");
		return re.toString();
	}

	/**
	 *
	 * @param nowDate   要比较的时间
	 * @param startDate   开始时间
	 * @param endDate   结束时间
	 * @return   true在时间段内，false不在时间段内
	 * @throws Exception
	 */
	public static boolean hourMinuteBetween(String nowDate, String startDate, String endDate) throws Exception{

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date now = format.parse(nowDate);
		Date start = format.parse(startDate);
		Date end = format.parse(endDate);

		long nowTime = now.getTime();
		long startTime = start.getTime();
		long endTime = end.getTime();

		return nowTime >= startTime && nowTime <= endTime;
	}

	public static void main(String[] args){
		try {
			System.out.println(hourMinuteBetween("2018-10-13 05:53:00", "2018-10-13 04:00:00", "2018-10-13 06:00:00"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
