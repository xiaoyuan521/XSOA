package com.xsoa.servlet.servlet1010000;

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
import com.xsoa.pojo.custom.pojo_1010000.Pojo1010130;
import com.xsoa.service.service1010000.Service1010130;

/**
 * 
 * @ClassName: Servlet1010130
 * @Package:com.xsoa.servlet.servlet1010000
 * @Description: 调动查询控制类
 * @author ztz
 * @date 2015年2月27日 下午4:35:22
 */
/**
 * Servlet implementation class Servlet1010130
 */
@WebServlet("/Servlet1010130")
public class Servlet1010130 extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	
	/* 本Servlet对应的Service */
	private Service1010130 service;
	
    public Servlet1010130() {
        super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service1010130();

		String CMD = this.getString(inputdata, "CMD");
		
		if (CMD_SELECT.equals(CMD)) {
			getDataList(inputdata);
		}
	}
	/**
	 * 
	 * @FunctionName: getDataList
	 * @Description: 获取调动信息数据个数及列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年2月27日 下午4:36:10
	 */
	private void getDataList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo1010130 beanIn = (Pojo1010130) this.getObject(inputdata, "beanLoad",
				Pojo1010130.class);

		int TotalCount = 0;
		List<Pojo1010130> dataList = new ArrayList<Pojo1010130>();
		
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
}