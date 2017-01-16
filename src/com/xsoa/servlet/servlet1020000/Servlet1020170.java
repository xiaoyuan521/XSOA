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
import com.xsoa.pojo.custom.pojo_1020000.Pojo1020130;
import com.xsoa.pojo.custom.pojo_1020000.Pojo1020170;
import com.xsoa.service.service1020000.Service1020170;

/**
 * Servlet implementation class Servlet1020170
 */
@WebServlet("/Servlet1020170")
public class Servlet1020170 extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	public static final String CMD_SAVE = "CMD_SAVE";
	public static final String CMD_GETXJXX = "CMD_GETXJXX";
	
	/* 本Servlet对应的Service */
	private Service1020170 service;
	
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;
	
    public Servlet1020170() {
        super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service1020170();
		arrResult = new ArrayList<Object>();
		
		String CMD = this.getString(inputdata, "CMD");
		
		if (CMD_SELECT.equals(CMD)) {
			getXJSQList(inputdata);
		} else if (CMD_SAVE.equals(CMD)) {
			saveXJSQ(inputdata);
		} else if (CMD_GETXJXX.equals(CMD)) {
			getXJXXRow(inputdata);
		}
		
	}
	
	/**	
	 * @FunctionName: getXJSQList	
	 * @Description: 获取休假申请数据个数及列表	
	 * @param inputdata
	 * @throws Exception	
	 * @return void	
	 * @author ljg	
	 * @date 2015年3月7日 上午10:06:00	
	 */	
	private void getXJSQList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo1020130 beanIn = (Pojo1020130) this.getObject(inputdata, "beanLoad",Pojo1020130.class);
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		beanIn.setXJSQ_XYSP(beanUser.getYHXX_YGID());
		
		int TotalCount = 0;
		List<Pojo1020130> dataList = new ArrayList<Pojo1020130>();
		
		try {
			TotalCount = service.getXJSQDataCount(beanIn);
			dataList = service.getXJSQDataList(beanIn, page, limit, sort);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(TotalCount, dataList);
		}
	}
	
	/**	
	 * @FunctionName: saveXJSQ	
	 * @Description: 保存审批休假信息	
	 * @param inputdata
	 * @throws Exception	
	 * @return void	
	 * @author ljg	
	 * @date 2015年3月7日 上午10:06:30	
	 */	
	private void saveXJSQ(Map<String, String[]> inputdata) throws Exception {
		Pojo1020170 beanIn = (Pojo1020170) this.getObject(inputdata, "BeanIn",Pojo1020170.class);
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		beanIn.setXJMX_CZR(beanUser.getYHXX_YGID());
		beanIn.setXJMX_CJR(beanUser.getYHXX_YHID());
		beanIn.setXJMX_GXR(beanUser.getYHXX_YHID());
		boolean result = false;
		
		try {
			result = service.updateXJSQ(beanIn);
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
	 * @FunctionName: getXJXXRow	
	 * @Description: 取得单条休假信息	
	 * @param inputdata
	 * @throws Exception	
	 * @return void	
	 * @author ljg	
	 * @date 2015年3月7日 上午10:07:10	
	 */	
	private void getXJXXRow(Map<String, String[]> inputdata) throws Exception{
		Pojo1020130 beanIn = (Pojo1020130) this.getObject(inputdata, "BeanIn",Pojo1020130.class);
		
		try {
			beanIn = service.getDataBySQID(beanIn);
			if (beanIn!=null) {
				arrResult.add("SUCCESS");
				arrResult.add(beanIn);
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
