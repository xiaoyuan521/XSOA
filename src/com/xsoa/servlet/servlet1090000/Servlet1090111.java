package com.xsoa.servlet.servlet1090000;

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
import com.xsoa.pojo.custom.pojo_1090000.Pojo1090111;
import com.xsoa.service.service1090000.Service1090111;

/**	
 * @ClassName: Servlet1090111	
 * @Package:com.xsoa.servlet.servlet1090000	
 * @Description: 审批设定Servlet	
 * @author ljg	
 * @date 2015年2月13日 上午10:58:12	
 * @update		
 */	
@WebServlet("/Servlet1090111")
public class Servlet1090111 extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	public static final String CMD_CHK_EXIST = "CMD_CHK_EXIST";
	public static final String CMD_INSERT = "CMD_INSERT";
	public static final String CMD_UPDATE = "CMD_UPDATE";
	public static final String CMD_DELETE = "CMD_DELETE";
       
	/* 本Servlet对应的Service */
	private Service1090111 service;
	
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;
	
    public Servlet1090111() {
        super();
    }

    @Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service1090111();
		arrResult = new ArrayList<Object>();
		
		String CMD = this.getString(inputdata, "CMD");
		
		if (CMD_SELECT.equals(CMD)) {
			getLCMXList(inputdata);
		} else if (CMD_CHK_EXIST.equals(CMD)) {
			chkLCMXExist(inputdata);
		} else if (CMD_INSERT.equals(CMD)) {
			insertLCMX(inputdata);
		} else if (CMD_UPDATE.equals(CMD)) {
			updateLCMX(inputdata);
		} else if (CMD_DELETE.equals(CMD)) {
			deleteLCMX(inputdata);
		}		
	}

	/**	
	 * @FunctionName: getLCMXList	
	 * @Description: 获取审批设定信息数据个数及列表	
	 * @param inputdata
	 * @throws Exception	
	 * @return void	
	 * @author ljg	
	 * @date 2015年2月5日 下午4:14:27	
	 */	
	private void getLCMXList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo1090111 beanIn = (Pojo1090111) this.getObject(inputdata, "beanLoad",
				Pojo1090111.class);

		int TotalCount = 0;
		List<Pojo1090111> dataList = new ArrayList<Pojo1090111>();
		
		try {
			TotalCount = service.getLCMXDataCount(beanIn);
			dataList = service.getLCMXDataList(beanIn, page, limit, sort);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(TotalCount, dataList);
		}
	}

	/**	
	 * @FunctionName: chkLCMXExist	
	 * @Description: 判断审批流程是否重复(流程ID,审批人)	
	 * @param inputdata
	 * @throws Exception	
	 * @return void	
	 * @author ljg	
	 * @date 2015年2月5日 下午4:14:48	
	 */	
	private void chkLCMXExist(Map<String, String[]> inputdata) throws Exception {
		Pojo1090111 beanIn = (Pojo1090111) this.getObject(inputdata, "BeanIn",Pojo1090111.class);

		try {
			int TotalCount = service.getCheckLCMXCount(beanIn);
			if (TotalCount > 0) {
				arrResult.add("DATA_EXIST");
			} else {
				arrResult.add("DATA_NOEXIST");
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
	 * @FunctionName: insertLCMX	
	 * @Description: 新增审批设定信息	
	 * @param inputdata
	 * @throws Exception	
	 * @return void	
	 * @author ljg	
	 * @date 2015年2月5日 下午4:17:26	
	 */	
	private void insertLCMX(Map<String, String[]> inputdata) throws Exception {
		Pojo1090111 beanIn = (Pojo1090111) this.getObject(inputdata, "BeanIn",Pojo1090111.class);
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		beanIn.setLCMX_CJR(beanUser.getYHXX_YHID());
		beanIn.setLCMX_GXR(beanUser.getYHXX_YHID());
		boolean result = false;
		
		try {
			result = service.insertLCMX(beanIn);
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

	/**	
	 * @FunctionName: updateLCMX	
	 * @Description: 修改审批设定信息	
	 * @param inputdata
	 * @throws Exception	
	 * @return void	
	 * @author ljg	
	 * @date 2015年2月5日 下午4:21:29	
	 */	
	private void updateLCMX(Map<String, String[]> inputdata) throws Exception {
		Pojo1090111 beanIn = (Pojo1090111) this.getObject(inputdata, "BeanIn",Pojo1090111.class);
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		beanIn.setLCMX_GXR(beanUser.getYHXX_YHID());
		boolean result = false;
		
		try {
			result = service.updateLCMX(beanIn);
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

	/**	
	 * @FunctionName: deleteLCMX	
	 * @Description: 删除审批设定信息	
	 * @param inputdata
	 * @throws Exception	
	 * @return void	
	 * @author ljg	
	 * @date 2015年2月5日 下午4:21:59	
	 */	
	private void deleteLCMX(Map<String, String[]> inputdata) throws Exception {
		Pojo1090111 beanIn = (Pojo1090111) this.getObject(inputdata, "BeanIn",Pojo1090111.class);
		boolean result = false;
		
		try {
			result = service.deleteLCMX(beanIn);
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
