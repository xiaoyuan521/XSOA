package com.xsoa.servlet.servlet1050000;

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
import com.xsoa.pojo.custom.pojo_1050000.Pojo1050120;
import com.xsoa.pojo.custom.pojo_1050000.Pojo1050121;
import com.xsoa.service.service1050000.Service1050120;

/**
 * 
 * @ClassName: Servlet1050120
 * @Package:com.xsoa.servlet.servlet1050000
 * @Description: 日报管理控制类
 * @author czl
 * @date 2015-03-02
 */
/**
 * Servlet implementation class Servlet1050120
 */
@WebServlet("/Servlet1050120")
public class Servlet1050120 extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	public static final String CMD_APPROVE = "CMD_APPROVE";
	public static final String CMD_SFZJ = "CMD_SFZJ";
	public static final String CMD_INFO = "CMD_INFO";

	
	/* 本Servlet对应的Service */
	private Service1050120 service;
	
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;
	
	/* 当前登录系统的用户对象 */
	Pojo_YHXX beanUser;
	
    public Servlet1050120() {
        super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service1050120();
		arrResult = new ArrayList<Object>();
		beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		
		String CMD = this.getString(inputdata, "CMD");
		
		if (CMD_SELECT.equals(CMD)) {
			getDayReport(inputdata);
		} else if (CMD_APPROVE.equals(CMD)) {
			approveDayReport(inputdata);
		} else if (CMD_SFZJ.equals(CMD)) {
			getSFZJ(inputdata);
		} else if (CMD_INFO.equals(CMD)) {
			getTodayInfo(inputdata);
		}		
	}
	/**
	 * 
	 * @FunctionName: getDayReport
	 * @Description: 获取日报信息数据个数及列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2015-03-02
	 */
	private void getDayReport(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo1050120 beanIn = (Pojo1050120) this.getObject(inputdata, "beanLoad",
				Pojo1050120.class);

		int TotalCount = 0;
		List<Pojo1050120> DataList = new ArrayList<Pojo1050120>();
		
		try {
			TotalCount = service.getDayReportCount(beanIn, beanUser.getYHXX_YGID());
			DataList = service.getDayReportList(beanIn, page, limit, sort, beanUser.getYHXX_YGID());
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(TotalCount, DataList);
		}
	}
	/**
	 * 
	 * @FunctionName: approveDayReport
	 * @Description: 审批日报信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2015-03-02
	 */
	private void approveDayReport(Map<String, String[]> inputdata) throws Exception {
		Pojo1050121 beanIn = (Pojo1050121) this.getObject(inputdata, "BeanIn",Pojo1050121.class);
		beanIn.setRBMX_GXR(beanUser.getYHXX_YHID());
		boolean result = false;
		
		try {
			result = service.approveDayReport(beanIn, beanUser.getYHXX_YGID());
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
	 * 
	 * @FunctionName: getSFZJ
	 * @Description: 获得审批人是否有终结权限
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2015-03-09
	 */
	private void getSFZJ(Map<String, String[]> inputdata) throws Exception {
		Pojo1050121 Data = null;
		String sqrid = this.getString(inputdata, "SQR");
		try {
			Data = service.getSFZJ(sqrid,beanUser.getYHXX_YGID());	
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
	 * 
	 * @FunctionName: getTodayInfo
	 * @Description: 获得日报信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2015-03-04
	 */
	private void getTodayInfo(Map<String, String[]> inputdata) throws Exception {
		Pojo1050121 beanIn = (Pojo1050121) this.getObject(inputdata, "BeanIn",Pojo1050121.class);
		Pojo1050120 DataInfo = null;
		Pojo1050121 DataNext = null;
		List<Pojo1050121> PageData = new ArrayList<Pojo1050121>();
		try {
			DataInfo = service.getTodayInfo(beanIn.getRBMX_RBID());
			if(DataInfo != null)
			{
				PageData = service.getDayReportList(beanIn.getRBMX_RBID());
				DataNext = service.getNext(beanIn.getRBMX_RBID());
				arrResult.add("SUCCESS");
				arrResult.add(DataInfo);
				arrResult.add(PageData);
				arrResult.add(DataNext);
			}else
			{
				arrResult.add("DATA_NULL");
				arrResult.add("");
				arrResult.add("");
				arrResult.add("");
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			print(arrResult);
		}
	}
}