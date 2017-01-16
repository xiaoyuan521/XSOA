package com.framework.log;

import org.apache.log4j.Logger;

public class MyLogger {
		
	private static Logger log;
	
	/**
	 * 日志对象初始化
	 * @return
	 */
	public static void init(){
		log = Logger.getRootLogger();
	}
	
	/**
	 * 系统调试记入日志
	 * @param message
	 */
	public static void debug(Object message){
		log.debug(message);
	}
	/**
	 * 系统调试记入日志
	 * @param message
	 * @param t
	 */
	public static void debug(Object message, Throwable t){
		log.debug(message,t);
	}
	
	/**
	 * 系统信息记入日志
	 * @param message
	 */
	public static void info(Object message){
		log.info(message);
	}
	/**
	 * 系统信息记入日志
	 * @param message
	 * @param t
	 */
	public static void info(Object message, Throwable t){
		log.info(message,t);
	}
	
	/**
	 * 系统错误记入日志
	 * @param message
	 */
	public static void error(Object message){
		log.error(message);
	}
	
	/**
	 * 系统错误记入日志
	 * @param message
	 */
	public static void error(String classname,Exception e){
		log.error(getExceptionMessage(classname,e));
	}
	/**
	 * 系统错误记入日志
	 * @param message
	 * @param t
	 */
	public static void error(Object message, Throwable t){
		log.error(message,t);
	}
	
	/** 
	 * @Method: getTraceInfo 
	 * @Description: 从异常信息中定位错误行 
	 * @param e
	 * @return 返回值说明
	 * @author misty
	 * @date 2014年5月11日 下午10:46:30 
	 */
	public static StringBuffer getTraceInfo(String strClassName, Exception e) {
		StringBuffer sb = new StringBuffer();
		StackTraceElement[] stacks = e.getStackTrace();
		for (int i = 0; i < stacks.length; i++) {
			if (stacks[i].getClassName().contains(strClassName)) {
				sb.append("class: ").append(stacks[i].getClassName());
				sb.append("; method: ").append(stacks[i].getMethodName());
				sb.append("; line: ").append(stacks[i].getLineNumber());
				sb.append("; Exception: ");
				break;
			}
		}
		return sb;
	}

	/** 
	 * @Method: getExceptionMessage 
	 * @Description: 通过异常对象取得错误信息 
	 * @param strClassName
	 * @param e
	 * @return 返回值说明
	 * @author misty
	 * @date 2014年5月11日 下午10:49:04 
	 */
	public static String getExceptionMessage(String strClassName,Exception e) {
		String message = e.toString();
		if (message.lastIndexOf(":") != -1)
			message = message.substring(0, message.lastIndexOf(":"));
		return getTraceInfo(strClassName,e).append(message).toString();
	}

}
