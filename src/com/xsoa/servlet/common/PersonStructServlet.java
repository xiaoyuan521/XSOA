package com.xsoa.servlet.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.framework.core.BaseServlet;
import com.framework.log.MyLogger;
import com.xsoa.service.common.PersonStructService;

/**
 * @ClassName: PersonStructServlet
 * @Package:com.xsoa.servlet.common
 * @Description: 员工组织结构Servlet
 * @author ljg
 * @date 2015年2月10日 下午3:55:07
 * @update
 */
@WebServlet("/PersonStructServlet")
public class PersonStructServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	// 命令定义部分
	public static final String CMD_BM = "CMD_BM";
	public static final String CMD_JS = "CMD_JS";
	public static final String CMD_ZW = "CMD_ZW";
	public static final String CMD_YG = "CMD_YG";

	// 本Servlet对应的Service
	private PersonStructService service;

	public PersonStructServlet() {
		super();
	}

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new PersonStructService();

		CMD = this.getString(inputdata, "type");

		if (CMD.equals("")) {
			// 加载部门
			loadBM(inputdata);
		} else if (CMD.equals(CMD_BM)) {
			// 加载角色
			loadJS(inputdata);
		} else if (CMD.equals(CMD_JS)) {
			// 加载职务
			loadZW(inputdata);
		} else if (CMD.equals(CMD_ZW)) {
			// 加载员工
			loadPerson(inputdata);
		}

	}

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

	public void loadZW(Map<String, String[]> inputdata) throws Exception {

		ArrayList<Object> result = null;
		try{
			String strBMID = this.getString(inputdata, "pId");
			String strJSID = this.getString(inputdata, "id");
			result = service.getZWList(strBMID,strJSID);
		}catch(Exception e){
			MyLogger.error(this.getClass().getName(), e);
		}finally{
			this.print(result);
		}
	}

	public void loadPerson(Map<String, String[]> inputdata) throws Exception {

		ArrayList<Object> result = null;
		try{
			String strZWID = this.getString(inputdata, "id");
			result = service.getYGList(strZWID);
		}catch(Exception e){
			MyLogger.error(this.getClass().getName(), e);
		}finally{
			this.print(result);
		}
	}

}
