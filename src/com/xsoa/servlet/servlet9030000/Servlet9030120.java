package com.xsoa.servlet.servlet9030000;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.framework.core.BaseServlet;
import com.framework.log.MyLogger;
import com.framework.session.SessionAttribute;
import com.xsoa.pojo.basetable.Pojo_YHXX;
import com.xsoa.pojo.custom.pojo_9030000.Pojo9030120;
import com.xsoa.service.service9030000.Service9030120;

/**
 * 
 * @ClassName: Servlet9030120
 * @Package:com.xsjy.servlet.servlet9030120
 * @Description: 个人信息
 * @author czl
 * @date 2015-3-12
 * @update
 */
/**
 * Servlet implementation class Servlet9030120
 */
@WebServlet("/Servlet9030120")
public class Servlet9030120 extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	/* 命令定义部分 */
	public static final String CMD_INFO = "CMD_INFO";
	public static final String CMD_UPDATE = "CMD_UPDATE";
	public static final String CMD_VALIDATE = "CMD_VALIDATE";
	
	/* 本Servlet对应的Service */
	private Service9030120 service;
	
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;
	
	/* 当前登录系统的用户对象 */
	Pojo_YHXX beanUser;

    public Servlet9030120() {
    	super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service9030120();
		arrResult = new ArrayList<Object>();
		beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		
		String CMD = this.getString(inputdata, "CMD");
		
		if (CMD_INFO.equals(CMD)) {
			getInfo();
		}else if (CMD_UPDATE.equals(CMD)) {
			updateInfo(inputdata);
		}else if (CMD_VALIDATE.equals(CMD)) {
			validatePas(inputdata);
		}
	}
	/**
	 * @FunctionName: getInfo
	 * @Description: 获取个人信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2015-3-12
	 */
	private void getInfo() throws Exception {
		Pojo9030120 Data = null;
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		try {
			Data = service.getInfo(beanUser.getYHXX_YHID());	
			if(Data != null)
			{
				arrResult.add("SUCCESS");
				arrResult.add(Data);
			}else
			{
				arrResult.add("DATA_NULL");
				arrResult.add("");
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			arrResult.add("CMD_EXCEPTION");
		} finally {
			// 输出Ajax返回结果。
			print(arrResult);
		}
	}
	/**
	 * @FunctionName: updateInfo
	 * @Description: 修改个人信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2015-3-12
	 */
	private void updateInfo(Map<String, String[]> inputdata) throws Exception {
		Pojo9030120 beanIn = (Pojo9030120) this.getObject(inputdata, "BeanIn",Pojo9030120.class);
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		beanIn.setYHXX_GXR(beanUser.getYHXX_YHID());
		int ret = 0;
		
		try {
			ret = service.updateInfo(beanIn);
			if(ret>0){
				arrResult.add("SUCCESS");
			}else{
				arrResult.add("FAILURE");
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			arrResult.add("EXCEPTION");
		} finally {
			// 输出Ajax返回结果。
			print(arrResult);
		}
	}
	/**
	 * @FunctionName: validatePas
	 * @Description: 验证原密码
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2015-3-12
	 */
	private void validatePas(Map<String, String[]> inputdata) throws Exception {
		Pojo9030120 Data = null;
		Pojo9030120 beanIn = (Pojo9030120) this.getObject(inputdata, "BeanIn",Pojo9030120.class);
		try {
			Data = service.validatePas(beanIn);	
			if(Data != null)
			{
				arrResult.add("SUCCESS");
			}else
			{
				arrResult.add("NOPASSWORD");
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			arrResult.add("CMD_EXCEPTION");
		} finally {
			// 输出Ajax返回结果。
			print(arrResult);
		}
	}
}