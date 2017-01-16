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
import com.xsoa.pojo.custom.pojo_1020000.Pojo1020150;
import com.xsoa.service.service1020000.Service1020150;

/**
 * 
 * @ClassName: Servlet1020150
 * @Package:com.xsoa.servlet.servlet1020000
 * @Description: 出差申请控制类
 * @author ztz
 * @date 2015年3月6日 下午4:51:43
 */
/**
 * Servlet implementation class Servlet1020150
 */
@WebServlet("/Servlet1020150")
public class Servlet1020150 extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	public static final String CMD_INIT_INFO = "CMD_INIT_INFO";
	public static final String CMD_DAY_DIFFER = "CMD_DAY_DIFFER";
	public static final String CMD_INSERT = "CMD_INSERT";
	public static final String CMD_UPDATE = "CMD_UPDATE";
	public static final String CMD_DELETE = "CMD_DELETE";
	
	/* 本Servlet对应的Service */
	private Service1020150 service;
	
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;
	
	/* 当前登录系统的用户对象 */
	Pojo_YHXX beanUser;
	
    public Servlet1020150() {
        super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service1020150();
		arrResult = new ArrayList<Object>();
		beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);

		String CMD = this.getString(inputdata, "CMD");
		
		if (CMD_SELECT.equals(CMD)) {
			getDataList(inputdata);
		} else if (CMD_INIT_INFO.equals(CMD)) {
			initData(inputdata);
		} else if (CMD_DAY_DIFFER.equals(CMD)) {
			getDayDiffer(inputdata);
		} else if (CMD_INSERT.equals(CMD)) {
			insertData(inputdata);
		} else if (CMD_UPDATE.equals(CMD)) {
			updateData(inputdata);
		} else if (CMD_DELETE.equals(CMD)) {
			deleteData(inputdata);
		}
	}
	/**
	 * 
	 * @FunctionName: getDataList
	 * @Description: 获取出差申请数据个数及列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年3月3日 上午10:13:29
	 */
	private void getDataList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo1020150 beanIn = (Pojo1020150) this.getObject(inputdata, "beanLoad",
				Pojo1020150.class);
		beanIn.setCCSQ_SQR(beanUser.getYHXX_YGID());

		int TotalCount = 0;
		List<Pojo1020150> dataList = new ArrayList<Pojo1020150>();
		
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
	 * @FunctionName: initData
	 * @Description: 获取出差申请初始化部分数据
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年3月3日 上午10:23:02
	 */
	private void initData(Map<String, String[]> inputdata) throws Exception {
		Pojo1020150 beanIn = null;
		
		try {
			beanIn = service.initData(beanUser.getYHXX_YHID());
			if (beanIn != null) {
				arrResult.add("SUCCESS");
				arrResult.add(beanIn);
			} else {
				arrResult.add("FAILURE");
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			arrResult.add("EXCEPTION");
		} finally {
			print(arrResult);
		}
	}
	/**
	 * 
	 * @FunctionName: getDayDiffer
	 * @Description: 计算两个日期相差的天数
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年3月6日 上午11:30:10
	 */
	private void getDayDiffer(Map<String, String[]> inputdata) throws Exception {
		Pojo1020150 beanIn = (Pojo1020150) this.getObject(inputdata, "BeanIn",Pojo1020150.class);
		int dayDiffer = 0;
		
		try {
			dayDiffer = service.dayDiffer(beanIn.getCCSQ_KSSJ(), beanIn.getCCSQ_JSSJ());
			if (dayDiffer > 0) {
				arrResult.add("SUCCESS");
				arrResult.add(dayDiffer);
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
	 * @FunctionName: insertData
	 * @Description: 新增出差申请信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年3月4日 上午9:59:39
	 */
	private void insertData(Map<String, String[]> inputdata) throws Exception {
		Pojo1020150 beanIn = (Pojo1020150) this.getObject(inputdata, "BeanIn",Pojo1020150.class);
		beanIn.setCCSQ_CJR(beanUser.getYHXX_YHID());
		boolean result = false;
		
		try {
			result = service.insertData(beanIn);
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
	 * @FunctionName: updateData
	 * @Description: 修改出差申请信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年3月4日 上午9:59:51
	 */
	private void updateData(Map<String, String[]> inputdata) throws Exception {
		Pojo1020150 beanIn = (Pojo1020150) this.getObject(inputdata, "BeanIn",Pojo1020150.class);
		beanIn.setCCSQ_GXR(beanUser.getYHXX_YHID());
		boolean result = false;
		
		try {
			result = service.updateData(beanIn);
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
	 * @FunctionName: deleteData
	 * @Description: 删除出差申请信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年3月4日 上午10:00:06
	 */
	private void deleteData(Map<String, String[]> inputdata) throws Exception {
		Pojo1020150 beanIn = (Pojo1020150) this.getObject(inputdata, "BeanIn",Pojo1020150.class);
		beanIn.setCCSQ_GXR(beanUser.getYHXX_YHID());
		boolean result = false;
		
		try {
			result = service.deleteData(beanIn);
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