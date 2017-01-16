package com.xsoa.servlet.servlet1040000;

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
import com.xsoa.pojo.custom.pojo_1040000.Pojo1040110;
import com.xsoa.service.service1040000.Service1040110;

/**
 * 
 * @ClassName: Servlet1040110
 * @Package:com.xsoa.servlet.servlet1040000
 * @Description: 交易客户控制类
 * @author ztz
 * @date 2015年2月27日 下午5:03:39
 */
/**
 * Servlet implementation class Servlet1040110
 */
@WebServlet("/Servlet1040110")
public class Servlet1040110 extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	public static final String CMD_CHK_EXIST = "CMD_CHK_EXIST";
	public static final String CMD_INSERT = "CMD_INSERT";
	public static final String CMD_UPDATE = "CMD_UPDATE";
	public static final String CMD_DELETE = "CMD_DELETE";
	
	/* 本Servlet对应的Service */
	private Service1040110 service;
	
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;
	
	/* 当前登录系统的用户对象 */
	Pojo_YHXX beanUser;
	
    public Servlet1040110() {
        super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service1040110();
		arrResult = new ArrayList<Object>();
		beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		
		String CMD = this.getString(inputdata, "CMD");
		
		if (CMD_SELECT.equals(CMD)) {
			getDataList(inputdata);
		} else if (CMD_CHK_EXIST.equals(CMD)) {
			chkDataExist(inputdata);
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
	 * @Description: 获取客户信息数据个数及列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年2月27日 下午5:06:05
	 */
	private void getDataList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo1040110 beanIn = (Pojo1040110) this.getObject(inputdata, "beanLoad",
				Pojo1040110.class);

		int TotalCount = 0;
		List<Pojo1040110> dataList = new ArrayList<Pojo1040110>();
		
		try {
			Pojo1040110 baseBean = service.getBaseData(beanUser.getYHXX_YHID());
			beanIn.setDQYH_YHID(baseBean.getDQYH_YHID());
			beanIn.setDQYH_BMID(baseBean.getDQYH_BMID());
			beanIn.setDQYH_ZWDJ(baseBean.getDQYH_ZWDJ());
			TotalCount = service.getDataCount(beanIn, "list");
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
	 * @FunctionName: chkDataExist
	 * @Description: 判断客户信息是否已存在
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年2月27日 下午5:06:14
	 */
	private void chkDataExist(Map<String, String[]> inputdata) throws Exception {
		Pojo1040110 beanIn = (Pojo1040110) this.getObject(inputdata, "BeanIn",Pojo1040110.class);

		try {
			int TotalCount = service.getDataCount(beanIn, "check");
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
	 * 
	 * @FunctionName: insertData
	 * @Description: 新增客户信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年2月27日 下午5:06:23
	 */
	private void insertData(Map<String, String[]> inputdata) throws Exception {
		Pojo1040110 beanIn = (Pojo1040110) this.getObject(inputdata, "BeanIn",Pojo1040110.class);
		beanIn.setKHXX_CJR(beanUser.getYHXX_YHID());
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
	 * @Description: 修改客户信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年2月27日 下午5:06:32
	 */
	private void updateData(Map<String, String[]> inputdata) throws Exception {
		Pojo1040110 beanIn = (Pojo1040110) this.getObject(inputdata, "BeanIn",Pojo1040110.class);
		beanIn.setKHXX_GXR(beanUser.getYHXX_YHID());
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
	 * @Description: 删除客户信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年2月27日 下午5:06:42
	 */
	private void deleteData(Map<String, String[]> inputdata) throws Exception {
		Pojo1040110 beanIn = (Pojo1040110) this.getObject(inputdata, "BeanIn",Pojo1040110.class);
		beanIn.setKHXX_GXR(beanUser.getYHXX_YHID());
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