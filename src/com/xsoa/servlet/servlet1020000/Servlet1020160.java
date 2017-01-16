package com.xsoa.servlet.servlet1020000;

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
import com.xsoa.pojo.custom.pojo_1020000.Pojo1020160;
import com.xsoa.pojo.custom.pojo_1020000.Pojo1020161;
import com.xsoa.service.service1020000.Service1020160;

/**
 * 
 * @ClassName: Servlet1020160
 * @Package:com.xsoa.servlet.servlet1020000
 * @Description: 月度出勤控制类
 * @author czl
 * @date 2015-3-9
 */
/**
 * Servlet implementation class Servlet1020160
 */
@WebServlet("/Servlet1020160")
public class Servlet1020160 extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	public static final String CMD_INFO = "CMD_INFO";
	
	/* 本Servlet对应的Service */
	private Service1020160 service;
	
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;
	
	/* 当前登录系统的用户对象 */
	Pojo_YHXX beanUser;
	
    public Servlet1020160() {
        super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service1020160();
		arrResult = new ArrayList<Object>();
		beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);

		String CMD = this.getString(inputdata, "CMD");
		
		if (CMD_SELECT.equals(CMD)) {
			getDataList(inputdata);
		} else if (CMD_INFO.equals(CMD)) {
			DataInfo(inputdata);
		} 
	}
	/**
	 * 
	 * @FunctionName: getDataList
	 * @Description: 获取月度出勤数据个数及列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2015-3-9
	 */
	private void getDataList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo1020160 beanIn = (Pojo1020160) this.getObject(inputdata, "beanLoad",
				Pojo1020160.class);

		int TotalCount = 0;
		List<Pojo1020160> dataList = new ArrayList<Pojo1020160>();
		
		try {
			TotalCount = service.getDataCount(beanIn);
			dataList = service.getDataList(beanIn, page, limit, sort);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(TotalCount, dataList);
		}
	}
	/**
	 * 
	 * @FunctionName: DataInfo
	 * @Description: 获取数据列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2015-3-9
	 */
	private void DataInfo(Map<String, String[]> inputdata) throws Exception {
		
		Pojo1020161 beanIn = (Pojo1020161) this.getObject(inputdata, "beanLoad",
				Pojo1020161.class);
		Pojo1020161 resultList = null;
		try {
			resultList = service.getDataInfo(beanIn);
			arrResult.add("SUCCESS");
			arrResult.add(resultList);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			print(arrResult);
		}
	}	
}