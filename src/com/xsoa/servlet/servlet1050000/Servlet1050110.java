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
import com.xsoa.pojo.custom.pojo_1050000.Pojo1050110;
import com.xsoa.pojo.custom.pojo_1050000.Pojo1050111;
import com.xsoa.service.service1050000.Service1050110;

/**
 * 
 * @ClassName: Servlet1050110
 * @Package:com.xsoa.servlet.servlet1050000
 * @Description: 日报管理控制类
 * @author czl
 * @date 2015-02-27
 */
/**
 * Servlet implementation class Servlet1050110
 */
@WebServlet("/Servlet1050110")
public class Servlet1050110 extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	public static final String CMD_INSERT = "CMD_INSERT";
	public static final String CMD_UPDATE = "CMD_UPDATE";
	public static final String CMD_DELETE = "CMD_DELETE";
	public static final String CMD_INFO = "CMD_INFO";
	public static final String CMD_DATA = "CMD_DATA";
	
	/* 本Servlet对应的Service */
	private Service1050110 service;
	
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;
	
	/* 当前登录系统的用户对象 */
	Pojo_YHXX beanUser;
	
    public Servlet1050110() {
        super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service1050110();
		arrResult = new ArrayList<Object>();
		beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		
		String CMD = this.getString(inputdata, "CMD");
		
		if (CMD_SELECT.equals(CMD)) {
			getDayReport(inputdata);
		} else if (CMD_INSERT.equals(CMD)) {
			insertDayReport(inputdata);
		} else if (CMD_UPDATE.equals(CMD)) {
			updateDayReport(inputdata);
		} else if (CMD_DELETE.equals(CMD)) {
			deleteDayReport(inputdata);
		} else if (CMD_INFO.equals(CMD)) {
			getTodayInfo(inputdata);
		} else if (CMD_DATA.equals(CMD)) {
			getXMandBM();
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
	 * @date 2015-02-27
	 */
	private void getDayReport(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo1050110 beanIn = (Pojo1050110) this.getObject(inputdata, "beanLoad",
				Pojo1050110.class);

		int TotalCount = 0;
		List<Pojo1050110> DataList = new ArrayList<Pojo1050110>();
		
		try {
			TotalCount = service.getDayReportCount(beanIn, beanUser.getYHXX_YHID());
			DataList = service.getDayReportList(beanIn, page, limit, sort, beanUser.getYHXX_YHID());
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(TotalCount, DataList);
		}
	}
	/**
	 * 
	 * @FunctionName: insertDayReport
	 * @Description: 新增日报信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2015-02-27
	 */
	private void insertDayReport(Map<String, String[]> inputdata) throws Exception {
		Pojo1050110 beanIn = (Pojo1050110) this.getObject(inputdata, "BeanIn",Pojo1050110.class);
		beanIn.setRBGL_CJR(beanUser.getYHXX_YHID());
		boolean result = false;
		try {
			result = service.insertDayReport(beanIn);
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
	 * @FunctionName: updateDayReport
	 * @Description: 修改日报信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2015-03-02
	 */
	private void updateDayReport(Map<String, String[]> inputdata) throws Exception {
		Pojo1050110 beanIn = (Pojo1050110) this.getObject(inputdata, "BeanIn",Pojo1050110.class);
		beanIn.setRBGL_GXR(beanUser.getYHXX_YHID());
		boolean result = false;
		
		try {
			result = service.updateDayReport(beanIn);
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
	 * @FunctionName: deleteDayReport
	 * @Description: 删除日报信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2015-02-27
	 */
	private void deleteDayReport(Map<String, String[]> inputdata) throws Exception {
		Pojo1050110 beanIn = (Pojo1050110) this.getObject(inputdata, "BeanIn",Pojo1050110.class);
		boolean result = false;
		
		try {
			result = service.deleteDayReport(beanIn);
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
	 * @FunctionName: getTodayInfo
	 * @Description: 获得日报信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2015-02-28
	 */
	private void getTodayInfo(Map<String, String[]> inputdata) throws Exception {
		Pojo1050110 beanIn = (Pojo1050110) this.getObject(inputdata, "BeanIn",Pojo1050110.class);
		Pojo1050110 DataInfo = null;
		Pojo1050111 DataThis = null;
		Pojo1050111 DataThat = null;
		Pojo1050111 DataNext = null;
		Pojo1050110 DataRbzt = null;
		List<Pojo1050111> PageData = new ArrayList<Pojo1050111>();
		try {
			DataInfo = service.getTodayInfo(beanUser.getYHXX_YHID(),beanIn.getRBGL_RBID(),beanIn.getRBGL_SQRQ());
			if(DataInfo != null)
			{
				PageData = service.getDayReportList(DataInfo.getRBGL_RBID());
				DataThis = service.getNext(DataInfo.getRBGL_RBID(),"this");
				DataThat = service.getNext(DataInfo.getRBGL_RBID(),"that");
				DataNext = service.getNext(DataInfo.getRBGL_RBID(),"next");
				DataRbzt = service.getRbzt();
				arrResult.add("SUCCESS");
				arrResult.add(DataInfo);
				arrResult.add(PageData);
				arrResult.add(DataThis);
				arrResult.add(DataNext);
				arrResult.add(DataRbzt);
				arrResult.add(DataThat);
			}else
			{
				arrResult.add("DATA_NULL");
				arrResult.add("");
				arrResult.add("");
				arrResult.add("");
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
	/**
	 * 
	 * @FunctionName: getXMandBM
	 * @Description: 获得上报人姓名和部门
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2015-02-27
	 */
	private void getXMandBM() throws Exception {
		Pojo1050110 Data = null;
		try {
			Data = service.getXMandBM(beanUser.getYHXX_YHID());	
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
}