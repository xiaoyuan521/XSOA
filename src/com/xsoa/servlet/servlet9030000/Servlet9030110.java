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
import com.xsoa.pojo.custom.pojo_9030000.Pojo9030110;
import com.xsoa.service.service9030000.Service9030110;

/**
 *
 * @ClassName: Servlet9030110
 * @Package:com.xsoa.servlet.servlet9030000
 * @Description: 员工信息控制类
 * @author czl
 * @date 2015-3-12
 */
/**
 * Servlet implementation class Servlet9030110
 */
@WebServlet("/Servlet9030110")
public class Servlet9030110 extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/* 命令定义部分 */
	public static final String CMD_DATA = "CMD_DATA";
	public static final String CMD_UPDATE = "CMD_UPDATE";

	/* 本Servlet对应的Service */
	private Service9030110 service;

	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;

	/* 当前登录系统的用户对象 */
	Pojo_YHXX beanUser;

    public Servlet9030110() {
        super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service9030110();
		arrResult = new ArrayList<Object>();
		beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);

		String CMD = this.getString(inputdata, "CMD");

		if (CMD_DATA.equals(CMD)) {
		    getYhInfo(inputdata);
		} else if (CMD_UPDATE.equals(CMD)) {
			updateYhInfo(inputdata);
		}
	}
	/**
	 *
	 * @FunctionName: getYhInfo
	 * @Description: 获取用户信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2016-9-18
	 */
	private void getYhInfo(Map<String, String[]> inputdata) throws Exception {

		Pojo9030110 Data = null;
		try {
			Data = service.getYhInfo(beanUser.getYHXX_YHID());
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
		} finally {
			// 输出Ajax返回结果。
			print(arrResult);
		}
	}
	/**
	 *
	 * @FunctionName: updateYhInfo
	 * @Description: 修改用户信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2016-9-18
	 */
	private void updateYhInfo(Map<String, String[]> inputdata) throws Exception {
		Pojo9030110 beanIn = (Pojo9030110) this.getObject(inputdata, "BeanIn",Pojo9030110.class);
		beanIn.setYHXX_GXR(beanUser.getYHXX_YHID());
		boolean result = false;

		try {
			result = service.updateYhInfo(beanIn);
			if (result) {
				arrResult.add("SUCCESS");
			} else {
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
}