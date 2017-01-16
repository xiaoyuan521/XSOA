package com.framework.util;

import org.apache.commons.lang.StringUtils;

public class MyStringUtils extends StringUtils {

	/**
	 * @Method: safeToString
	 * @Description: 安全返回字符串
	 * @param arg
	 * @return 返回值说明
	 * @author misty
	 * @date 2014年5月19日 下午9:44:40
	 */
	public static String safeToString(Object arg) {
		if (arg == null)
			return "";
		else
			return String.valueOf(arg);
	}

	/**
     * @Method: safeToString
     * @Description: 给以逗号分隔的字符串加单引号
     * @param arg
     * @return 返回值说明
     * @author czl
     * @date 2016-9-27
     */
	public static String getStrWithDy(String bmids){
        StringBuffer strs = new StringBuffer();
        String[] temp = bmids.split(",");
        for (int i = 0; i < temp.length; i++) {
            if (!"".equals(temp[i]) && temp[i] != null) {
                strs.append("'" + temp[i] + "',");
            }
        }
        String result = strs.toString();
        String tp = result.substring(0, result.length() - 1);
        return tp;
    }

}
