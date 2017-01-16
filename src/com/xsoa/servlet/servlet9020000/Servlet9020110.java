package com.xsoa.servlet.servlet9020000;

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
import com.xsoa.pojo.custom.pojo_9020000.Pojo9020110;
import com.xsoa.service.service9020000.Service9020110;

/**	
 * @ClassName: Servlet9020110	
 * @Package:com.xsoa.servlet.Servlet9020110	
 * @Description: 登录日志Servlet	
 * @author czl	
 * @date 2015-3-20	
 */	
@WebServlet("/Servlet9020110")
public class Servlet9020110 extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	
	/* 本Servlet对应的Service */
	private Service9020110 service;
	
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;
	
    public Servlet9020110() {
        super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service9020110();
		arrResult = new ArrayList<Object>();
		
		String CMD = this.getString(inputdata, "CMD");
		
		if (CMD_SELECT.equals(CMD)) {
			getDLRZList(inputdata);
		} 
	}

	/**	
	 * @FunctionName: getDLRZList	
	 * @Description: 获取登录日志数据个数及列表	
	 * @param inputdata
	 * @throws Exception	
	 * @return void	
	 * @author czl	
	 * @date 2015-3-20
	 */	
	private void getDLRZList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo9020110 beanIn = (Pojo9020110) this.getObject(inputdata, "beanLoad",
				Pojo9020110.class);

		int TotalCount = 0;
		List<Pojo9020110> dataList = new ArrayList<Pojo9020110>();
		
		try {
			TotalCount = service.getDLRZDataCount(beanIn);
			dataList = service.getDLRZDataList(beanIn, page, limit, sort);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(TotalCount, dataList);
		}
	}
}