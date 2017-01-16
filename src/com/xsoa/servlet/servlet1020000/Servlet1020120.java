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
import com.xsoa.pojo.basetable.Pojo_BMXX;
import com.xsoa.pojo.basetable.Pojo_YHXX;
import com.xsoa.pojo.custom.pojo_1020000.Pojo1020120;
import com.xsoa.service.service1020000.Service1020120;

/**
 * 
 * @ClassName: Servlet1020120
 * @Package:com.xsoa.servlet.servlet1020000
 * @Description: 签到记录控制类
 * @author ztz
 * @date 2015年2月28日 下午2:35:13
 */
/**
 * Servlet implementation class Servlet1020120
 */
@WebServlet("/Servlet1020120")
public class Servlet1020120 extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	public static final String CMD_GET_BASE = "CMD_GET_BASE";
	
	/* 本Servlet对应的Service */
	private Service1020120 service;
	
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;
	
	/* 当前登录系统的用户对象 */
	Pojo_YHXX beanUser;
	
    public Servlet1020120() {
        super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service1020120();
		arrResult = new ArrayList<Object>();
		beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);

		String CMD = this.getString(inputdata, "CMD");
		
		if (CMD_SELECT.equals(CMD)) {
			getDataList(inputdata);
		} else if (CMD_GET_BASE.equals(CMD)) {
			getBaseData(inputdata);
		}
	}
	/**
	 * 
	 * @FunctionName: getDataList
	 * @Description: 获取签到记录数据个数及列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年2月28日 下午2:38:36
	 */
	private void getDataList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo1020120 beanIn = (Pojo1020120) this.getObject(inputdata, "beanLoad",
				Pojo1020120.class);

		int TotalCount = 0;
		List<Pojo1020120> dataList = new ArrayList<Pojo1020120>();
		
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
	 * @FunctionName: getBaseData
	 * @Description: 获取当前登陆用户的基础数据（管理部门数量，员工ID等）
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年3月20日 下午2:52:22
	 */
	private void getBaseData(Map<String, String[]> inputdata) throws Exception {
		Pojo1020120 beanIn = new Pojo1020120();
		
		try {
			Pojo_BMXX bmxx = service.getBmxx(beanUser.getYHXX_YGID());//部门ID
			beanIn.setBMXX_BMID(bmxx.getBMXX_BMID());
			beanIn.setBMXX_BMMC(bmxx.getBMXX_BMMC());
			beanIn.setYGID(beanUser.getYHXX_YGID());//员工ID
			beanIn.setYGXM(beanUser.getYHXX_YHMC());//员工姓名
			String zwdj = service.getZwdj(beanUser.getYHXX_YGID());//职务等级
			beanIn.setZWDJ(zwdj);
			arrResult.add("SUCCESS");
			arrResult.add(beanIn);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			arrResult.add("EXCEPTION");
		} finally {
			// 输出Ajax返回结果。
			print(arrResult);
		}
	}
}