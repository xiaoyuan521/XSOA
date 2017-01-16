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
import com.framework.session.SessionAttribute;
import com.xsoa.pojo.basetable.Pojo_YHXX;
import com.xsoa.pojo.custom.pojo_1010000.Pojo1010140;
import com.xsoa.service.service1010000.Service1010140;

/**
 * 
 * @ClassName: Servlet1010140
 * @Package:com.xsoa.servlet.servlet1010000
 * @Description: 员工评价控制类
 * @author ztz
 * @date 2015年2月27日 下午4:38:33
 */
/**
 * Servlet implementation class Servlet1010140
 */
@WebServlet("/Servlet1010140")
public class Servlet1010140 extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	public static final String CMD_INSERT = "CMD_INSERT";
	public static final String CMD_UPDATE = "CMD_UPDATE";
	public static final String CMD_DELETE = "CMD_DELETE";
	public static final String CMD_GET_BASE = "CMD_GET_BASE";
	
	/* 本Servlet对应的Service */
	private Service1010140 service;
	
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;
	
	/* 当前登录系统的用户对象 */
	Pojo_YHXX beanUser;
	
    public Servlet1010140() {
        super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service1010140();
		arrResult = new ArrayList<Object>();
		beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		
		String CMD = this.getString(inputdata, "CMD");
		
		if (CMD_SELECT.equals(CMD)) {
			getDataList(inputdata);
		} else if (CMD_INSERT.equals(CMD)) {
			insertData(inputdata);
		} else if (CMD_UPDATE.equals(CMD)) {
			updateData(inputdata);
		} else if (CMD_DELETE.equals(CMD)) {
			deleteData(inputdata);
		} else if (CMD_GET_BASE.equals(CMD)) {
			getBaseData(inputdata);
		}
	}
	/**
	 * 
	 * @FunctionName: getDataList
	 * @Description: 获取评价信息数据个数及列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年2月27日 下午4:43:46
	 */
	private void getDataList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo1010140 beanIn = (Pojo1010140) this.getObject(inputdata, "beanLoad",
				Pojo1010140.class);

		int TotalCount = 0;
		List<Pojo1010140> dataList = new ArrayList<Pojo1010140>();
		
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
	 * @FunctionName: insertData
	 * @Description: 新增评价信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年2月27日 下午4:43:56
	 */
	private void insertData(Map<String, String[]> inputdata) throws Exception {
		Pojo1010140 beanIn = (Pojo1010140) this.getObject(inputdata, "BeanIn",Pojo1010140.class);
		beanIn.setYGPJ_PJR(beanUser.getYHXX_YGID());
		beanIn.setYGPJ_CJR(beanUser.getYHXX_YHID());
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
	 * @Description: 修改评价信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年2月27日 下午4:44:04
	 */
	private void updateData(Map<String, String[]> inputdata) throws Exception {
		Pojo1010140 beanIn = (Pojo1010140) this.getObject(inputdata, "BeanIn",Pojo1010140.class);
		beanIn.setYGPJ_GXR(beanUser.getYHXX_YHID());
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
	 * @Description: 删除评价信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年2月27日 下午4:44:15
	 */
	private void deleteData(Map<String, String[]> inputdata) throws Exception {
		Pojo1010140 beanIn = (Pojo1010140) this.getObject(inputdata, "BeanIn",Pojo1010140.class);
		beanIn.setYGPJ_GXR(beanUser.getYHXX_YHID());
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
	/**
	 * 
	 * @FunctionName: getBaseData
	 * @Description: 获取当前登陆用户的基础数据（部门ID，管理部门数量，员工ID等）
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年3月19日 下午12:03:04
	 */
	private void getBaseData(Map<String, String[]> inputdata) throws Exception {
		Pojo1010140 beanIn = new Pojo1010140();
		
		try {
			String bmid = service.getBmid(beanUser.getYHXX_YGID());//部门ID
			beanIn.setSFZJL("B101010".equals(bmid)?"true":"false");
			int bmsl = service.getBmsl(beanUser.getYHXX_YGID());//部门数量
			beanIn.setGLBMSL(String.valueOf(bmsl));
			beanIn.setYGID(beanUser.getYHXX_YGID());
			if (bmsl > 0) {
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