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
import com.xsoa.pojo.custom.pojo_1050000.Pojo1050130;
import com.xsoa.pojo.custom.pojo_1050000.Pojo1050131;
import com.xsoa.service.service1050000.Service1050130;

/**
 * 
 * @ClassName: Servlet1050130
 * @Package:com.xsoa.servlet.servlet1050000
 * @Description: 日报管理控制类
 * @author czl
 * @date 2015-03-06
 */
/**
 * Servlet implementation class Servlet1050130
 */
@WebServlet("/Servlet1050130")
public class Servlet1050130 extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	public static final String CMD_INFO = "CMD_INFO";
	public static final String CMD_DATA = "CMD_DATA";
	
	/* 本Servlet对应的Service */
	private Service1050130 service;
	
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;
	
	/* 当前登录系统的用户对象 */
	Pojo_YHXX beanUser;
	
    public Servlet1050130() {
        super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service1050130();
		arrResult = new ArrayList<Object>();
		beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		
		String CMD = this.getString(inputdata, "CMD");
		
		if (CMD_SELECT.equals(CMD)) {
			getDayReport(inputdata);
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
	 * @date 2015-03-06
	 */
	private void getDayReport(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo1050130 beanIn = (Pojo1050130) this.getObject(inputdata, "beanLoad",
				Pojo1050130.class);

		int TotalCount = 0;
		List<Pojo1050130> DataList = new ArrayList<Pojo1050130>();
		
		try {
			TotalCount = service.getDayReportCount(beanIn);
			DataList = service.getDayReportList(beanIn, page, limit, sort);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(TotalCount, DataList);
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
	 * @date 2015-03-06
	 */
	private void getTodayInfo(Map<String, String[]> inputdata) throws Exception {
		Pojo1050131 beanIn = (Pojo1050131) this.getObject(inputdata, "BeanIn",Pojo1050131.class);
		Pojo1050130 DataInfo = null;
		Pojo1050131 DataNext = null;
		List<Pojo1050131> PageData = new ArrayList<Pojo1050131>();
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