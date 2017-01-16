package com.xsoa.servlet.servlet9010000;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.framework.core.BaseServlet;
import com.framework.log.MyLogger;
import com.framework.session.SessionAttribute;
import com.xsoa.pojo.basetable.Pojo_YHXX;
import com.xsoa.pojo.custom.pojo_9010000.Pojo9010130;
import com.xsoa.pojo.custom.pojo_9010000.Pojo9010131;
import com.xsoa.service.service9010000.Service9010130;

/**
 *
 * @ClassName: Servlet9010130
 * @Package:com.xsoa.servlet.servlet9010000
 * @Description: 角色权限控制类
 * @author czl
 * @date 2015-3-20
 */
/**
 * Servlet implementation class Servlet9010130
 */
@WebServlet("/Servlet9010130")
public class Servlet9010130 extends BaseServlet {
private static final long serialVersionUID = 1L;

	/* 命令定义部分 */
	public static final String CMD_BM = "CMD_BM";
	public static final String CMD_JS = "CMD_JS";
	public static final String CMD_MENU_LIST = "CMD_MENU_LIST";
	public static final String CMD_JSMENU_LIST = "CMD_JSMENU_LIST";
	public static final String CMD_RELATION = "CMD_RELATION";

	/* 本SERVLET对应的Service */
	private Service9010130 service;

	/* AJAX返回前台的结果集 */
	private ArrayList<Object> result;

	/* 当前登录系统的用户对象 */
	Pojo_YHXX beanUser;

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service9010130();
		result = new ArrayList<Object>();
		beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		CMD = this.getString(inputdata, "type");
		if (CMD.equals("")) {
			loadBM(inputdata);
		} else if (CMD.equals(CMD_BM)) {
			loadJS(inputdata);
		} else if (CMD_MENU_LIST.equals(CMD)) {
			getMenuList(inputdata);
		} else if (CMD_JSMENU_LIST.equals(CMD)) {
			getJsMenuList(inputdata);
		} else if (CMD_RELATION.equals(CMD)) {
			relationMenu(inputdata);
		}
	}

	/**
	 * Constructor of the object.
	 */
	public Servlet9010130() {
		super();
	}
	/**
	 * @FunctionName: loadBM
	 * @Description: 加载部门树
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2015-3-20
	 */
	public void loadBM(Map<String, String[]> inputdata) throws Exception {
		ArrayList<Object> result = null;
		try{
			result = service.getBMList();
		}catch(Exception e){
			MyLogger.error(this.getClass().getName(), e);
		}finally{
			this.print(result);
		}
	}
	/**
	 * @FunctionName: loadJS
	 * @Description: 加载角色树
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2015-3-20
	 */
	public void loadJS(Map<String, String[]> inputdata) throws Exception {
		ArrayList<Object> result = null;
		try{
			String strBMID = this.getString(inputdata, "id");
			result = service.getJSList(strBMID);
		}catch(Exception e){
			MyLogger.error(this.getClass().getName(), e);
		}finally{
			this.print(result);
		}
	}
	/**
	 *
	 * @FunctionName: getMenuList
	 * @Description: 获取菜单信息列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2015-3-20
	 */
	private void getMenuList(Map<String, String[]> inputdata) throws Exception {
		List<Pojo9010130> menuList = new ArrayList<Pojo9010130>();
		try {
			menuList = service.getMenuList();
			result.add(menuList);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			print(result);
		}
	}
	/**
	 *
	 * @FunctionName: getJsMenuList
	 * @Description: 获取角色菜单信息列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2015-3-20
	 */
	private void getJsMenuList(Map<String, String[]> inputdata) throws Exception {
		String strJSID = this.getString(inputdata, "JSID");// 角色ID
		List<Pojo9010131> jsmenuList = new ArrayList<Pojo9010131>();
		try {
			jsmenuList = service.getJsMenuList(strJSID);
			result.add(jsmenuList);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			print(result);
		}
	}
	/**
	 *
	 * @FunctionName: relationMenu
	 * @Description: 角色关联菜单
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2015-3-20
	 */
	private void relationMenu(Map<String, String[]> inputdata) throws Exception {
		String jsid = this.getString(inputdata, "JSID");
		String cdids = this.getString(inputdata, "MENUS");
		cdids = cdids.substring(0, cdids.length() - 1);

		try {
			int ret = service.relationMenu(jsid, cdids, beanUser);
			if(ret>0) {
				result.add("CMD_OK");
			} else {
				result.add("CMD_ERROR");
			}
		} catch (Exception e) {
			result.add("CMD_EXCEPTION");
		} finally {
			// 输出Ajax返回结果。
			print(result);
		}
	}
}