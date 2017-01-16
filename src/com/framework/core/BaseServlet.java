package com.framework.core;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.framework.session.SessionAttribute;
import com.framework.util.MyNumberUtils;
import com.framework.util.MyStringUtils;

/**
 * Servlet基础类
 *
 * @File_name BaseServlet.java
 * @Package com.framework.core
 * @author misty
 * @date 2014年2月18日 下午7:20:00
 * @version V1.00.00
 */

public abstract class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(this.getClass().getName());
	private HttpServletRequest request;
	private HttpServletResponse response;

	public String CMD = "";
	public static final String CMD_OK = "CMD_OK"; // 默认的操作成功标志
	public static final String CMD_NG = "CMD_NG"; // 默认的操作失败标志
	public static final String CMD_NODATA = "CMD_NODATA"; // 默认的操作无返回数据

	public BaseServlet() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.request = request;
		this.response = response;
		execute();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.request = request;
		this.response = response;
		execute();
	}

	private void execute() throws IOException, ServletException {
		CMD = request.getParameter("CMD");

		Enumeration<?> e = request.getParameterNames();
		Map<String, String[]> map = new HashMap<String, String[]>();
		while (e.hasMoreElements()) {
			String name = (String) e.nextElement();
			String[] values = request.getParameterValues(name);
			map.put(name, values);
			log.info("InputData-> name=" + name + " values=" + values[0]);
		}

		try {
			this.process(request, response, map);

		} catch (Exception exp) {
			log.error("BaseServlet", exp);
		}
	}

	public abstract void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception;

	/**
	 * @Method: getString
	 * @Description: 获得字符串
	 * @param inputdata
	 * @param key
	 * @return
	 * @throws UnsupportedEncodingException
	 * @author misty
	 * @date 2014年2月18日 下午7:48:03
	 */
	public String getString(Map<String, String[]> inputdata, String key)
			throws UnsupportedEncodingException {
		String[] values = inputdata.get(key);
		if (values != null && values.length > 0) {
			return values[0];
		}
		return "";
	}

	/**
	 * @Method: getInt
	 * @Description: 获得整型数字
	 * @param inputdata
	 * @param key
	 * @return
	 * @throws UnsupportedEncodingException
	 * @author misty
	 * @date 2014年2月18日 下午8:25:22
	 */
	public int getInt(Map<String, String[]> inputdata, String key)
			throws UnsupportedEncodingException {
		return MyNumberUtils.safeToInt(getString(inputdata, key));
	}

	/**
	 * @Method: getObject
	 * @Description: 获得对象
	 * @param inputdata
	 * @param key
	 * @param pojoCalss
	 * @return
	 * @throws Exception
	 * @author misty
	 * @date 2014年2月18日 下午8:25:57
	 */
	public Object getObject(Map<String, String[]> inputdata, String key,
			Class<?> pojoCalss) throws Exception {
		String jsonString = getString(inputdata, key);
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		Object pojo = JSONObject.toBean(jsonObject, pojoCalss);

		return pojo;
	}

	/**
	 * @Method: getArray
	 * @Description: 获得Json数组
	 * @param inputdata
	 * @param key
	 * @return
	 * @throws Exception
	 * @author misty
	 * @date 2014年2月18日 下午8:26:21
	 */
	public JSONArray getArray(Map<String, String[]> inputdata, String key)
			throws Exception {
		String jsonString = getString(inputdata, key);
		JSONArray jsonArray = JSONArray.fromObject(jsonString);

		return jsonArray;
	}

	/**
	 * @Method: getPrintPath
	 * @Description: 获取印刷路径
	 * @return
	 * @author misty
	 * @date 2014年2月18日 下午8:27:36
	 */
	public String getPrintPath() {
		return this.request.getSession().getServletContext()
				.getRealPath("//bin//print")
				+ "//";
	}

	/**
	 * @Method: setSessionObject
	 * @Description: 设置Session值
	 * @param arg0
	 * @param arg1
	 * @author misty
	 * @date 2014年2月18日 下午8:28:27
	 */
	public void setSessionObject(String arg0, Object arg1) {
		request.getSession().setAttribute(arg0, arg1);
		log.info("SessionInput-> key=" + arg0 + " values="
				+ MyStringUtils.safeToString(arg1));
	}

	/**
	 * @Method: getSessionObject
	 * @Description: 取得Session值
	 * @param arg0
	 * @return
	 * @author misty
	 * @date 2014年2月18日 下午8:28:45
	 */
	public Object getSessionObject(String arg0) {
		return request.getSession().getAttribute(arg0);
	}

	/**
	 * @Method: clearSession
	 * @Description: 清空Session
	 * @author misty
	 * @date 2014年2月18日 下午8:29:04
	 */
	public void clearSession() {
		request.getSession().removeAttribute(SessionAttribute.LOGIN_USER);
		request.getSession().removeAttribute(SessionAttribute.LOGIN_MENU);
		request.getSession().removeAttribute(SessionAttribute.LOGIN_MENU_JSON);
	}

	public void forward(String path) throws ServletException, IOException {
		this.request.getRequestDispatcher(path).forward(this.request,
				this.response);
	}

	public void redirect(String path) throws IOException {
		response.sendRedirect(path);
	}

	/**
	 * @Method: print
	 * @Description: 输出一维数组
	 * @param obj
	 * @throws ServletException
	 * @throws IOException
	 * @author misty
	 * @date 2014年2月18日 下午8:31:25
	 */
	public void print(ArrayList<Object> obj) throws ServletException,
			IOException {
		// log.info("OutputData->" + JSONArray.fromObject(obj).toString());
		response.getWriter().print(JSONArray.fromObject(obj).toString());
	}

	/**
	 * @Method: print2
	 * @Description: 输出二维数组
	 * @param obj
	 * @throws ServletException
	 * @throws IOException
	 * @author misty
	 * @date 2014年2月18日 下午8:32:59
	 */
	public void print2(List<Object[]> obj) throws ServletException, IOException {
		// log.info("OutputData->" + JSONArray.fromObject(obj).toString());
		response.getWriter().print(JSONArray.fromObject(obj).toString());
	}

	/**
	 * @Method: printGrid
	 * @Description: 输出表格数据
	 * @param totalCount
	 *            --记录总数
	 * @param obj
	 *            --表格数据
	 * @throws ServletException
	 * @throws IOException
	 * @author misty
	 * @date 2014年2月18日 下午8:33:38
	 */
	public void printGrid(int totalCount, Object obj) throws ServletException,
			IOException {
		log.info("totalCount->" + totalCount);
		log.info("gridData->" + JSONArray.fromObject(obj).toString());
		StringBuffer sbf = new StringBuffer();
		sbf.append("{");
		sbf.append("\"totalCount\": " + totalCount + ",");
		sbf.append("\"items\": ");
		sbf.append(JSONArray.fromObject(obj).toString());
		sbf.append("}");
		response.getWriter().print(sbf.toString());
	}

	/**
	 * 输出对象
	 *
	 * @author baiyang
	 * @date 2013-4-23 上午9:16:21
	 * @version V1.00.00
	 */
	public void print(Object obj) throws ServletException, IOException {
		response.getWriter().print(JSONObject.fromObject(obj).toString());
	}

	/**
	 * @Method: print
	 * @Description: 输出字符串
	 * @param str
	 * @throws ServletException
	 * @throws IOException
	 * @author misty
	 * @date 2014年2月18日 下午8:36:53
	 */
	public void print(String str) throws ServletException, IOException {
		response.getWriter().print(str);
	}

	/**
	 * @Method: print
	 * @Description: 输出布尔值
	 * @param bln
	 * @throws ServletException
	 * @throws IOException
	 * @author misty
	 * @date 2014年2月18日 下午8:37:18
	 */
	public void print(boolean bln) throws ServletException, IOException {
		response.getWriter().print(bln);
	}

	/**
	 * @Method: print
	 * @Description: 输出CHAR
	 * @param chr
	 * @throws ServletException
	 * @throws IOException
	 * @author misty
	 * @date 2014年2月18日 下午8:37:33
	 */
	public void print(char chr) throws ServletException, IOException {
		response.getWriter().print(chr);
	}

	/**
	 * @Method: print
	 * @Description: 输出整数
	 * @param i
	 * @throws ServletException
	 * @throws IOException
	 * @author misty
	 * @date 2014年2月18日 下午8:42:43
	 */
	public void print(int i) throws ServletException, IOException {
		response.getWriter().print(i);
	}

	/**
	 * @Method: print
	 * @Description: 输出长整数
	 * @param l
	 * @throws ServletException
	 * @throws IOException
	 * @author misty
	 * @date 2014年2月18日 下午8:43:22
	 */
	public void print(long l) throws ServletException, IOException {
		response.getWriter().print(l);
	}

	/**
	 * @Method: print
	 * @Description: 输出浮点小数
	 * @param f
	 * @throws ServletException
	 * @throws IOException
	 * @author misty
	 * @date 2014年2月18日 下午8:43:42
	 */
	public void print(float f) throws ServletException, IOException {
		response.getWriter().print(f);
	}

	/**
	 * @Method: print
	 * @Description: 输出双精小数
	 * @param d
	 * @throws ServletException
	 * @throws IOException
	 * @author misty
	 * @date 2014年2月18日 下午8:43:56
	 */
	public void print(double d) throws ServletException, IOException {
		response.getWriter().print(d);
	}

	/**
	 * @Method: getIpAddr
	 * @Description: 客户端IP地址获得。
	 * @return IP Address.
	 * @author czl
	 * @date 2016-9-6
	 */
	public String getIpAddr() {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

}
