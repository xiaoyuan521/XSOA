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
import com.xsoa.pojo.custom.pojo_1010000.Pojo1010110;
import com.xsoa.service.service1010000.Service1010110;

/**
 * 
 * @ClassName: Servlet1010110
 * @Package:com.xsoa.servlet.servlet1010000
 * @Description: 员工信息控制类
 * @author ztz
 * @date 2015年2月3日 下午5:16:58
 */
/**
 * Servlet implementation class Servlet1010110
 */
@WebServlet("/Servlet1010110")
public class Servlet1010110 extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	public static final String CMD_CHK_EXIST = "CMD_CHK_EXIST";
	public static final String CMD_INSERT = "CMD_INSERT";
	public static final String CMD_UPDATE = "CMD_UPDATE";
	public static final String CMD_DELETE = "CMD_DELETE";
	
	/* 本Servlet对应的Service */
	private Service1010110 service;
	
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;
	
	/* 当前登录系统的用户对象 */
	Pojo_YHXX beanUser;
	
    public Servlet1010110() {
        super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service1010110();
		arrResult = new ArrayList<Object>();
		beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		
		String CMD = this.getString(inputdata, "CMD");
		
		if (CMD_SELECT.equals(CMD)) {
			getYgInfoList(inputdata);
		} else if (CMD_CHK_EXIST.equals(CMD)) {
			chkYgInfoExist(inputdata);
		} else if (CMD_INSERT.equals(CMD)) {
			insertYgInfo(inputdata);
		} else if (CMD_UPDATE.equals(CMD)) {
			updateYgInfo(inputdata);
		} else if (CMD_DELETE.equals(CMD)) {
			deleteYgInfo(inputdata);
		}		
	}
	/**
	 * 
	 * @FunctionName: getYgInfoList
	 * @Description: 获取员工信息数据个数及列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年2月3日 下午5:19:25
	 * @update ztz at 2015年2月9日 下午1:46:00
	 */
	private void getYgInfoList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo1010110 beanIn = (Pojo1010110) this.getObject(inputdata, "beanLoad",
				Pojo1010110.class);

		int TotalCount = 0;
		List<Pojo1010110> roleDataList = new ArrayList<Pojo1010110>();
		
		try {
			TotalCount = service.getYgInfoCount(beanIn);
			roleDataList = service.getYgInfoList(beanIn, page, limit, sort);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(TotalCount, roleDataList);
		}
	}
	/**
	 * 
	 * @FunctionName: chkYgInfoExist
	 * @Description: 判断员工信息是否已存在
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年2月4日 下午2:18:55
	 * @update ztz at 2015年2月9日 下午1:46:00
	 */
	private void chkYgInfoExist(Map<String, String[]> inputdata) throws Exception {
		Pojo1010110 beanIn = (Pojo1010110) this.getObject(inputdata, "BeanIn",Pojo1010110.class);

		try {
			int ygxxCount = service.getYgInfoCount(beanIn);
			int yhxxCount = service.getYhxxCount(beanIn);
			int ygzwCount = 0;
			if (!"000".equals(beanIn.getZWXX_ZWID())) {
				ygzwCount = service.getYgzwCount(beanIn);
			}
			if (ygxxCount > 0 || yhxxCount > 0 || ygzwCount > 0) {
				if (ygxxCount > 0) {
					arrResult.add("YGXX_EXIST");
				} else if (yhxxCount > 0) {
					arrResult.add("YHXX_EXIST");
				} else if (ygzwCount > 0) {
					arrResult.add("YGZW_EXIST");
				}
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
	 * @FunctionName: insertYgInfo
	 * @Description: 新增员工信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年2月9日 下午1:45:27
	 */
	private void insertYgInfo(Map<String, String[]> inputdata) throws Exception {
		Pojo1010110 beanIn = (Pojo1010110) this.getObject(inputdata, "BeanIn",Pojo1010110.class);
		beanIn.setYGXX_CJR(beanUser.getYHXX_YHID());
		boolean result = false;
		
		try {
			result = service.insertYgInfo(beanIn);
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
	 * @FunctionName: updateYgInfo
	 * @Description: 修改员工信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年2月9日 下午1:45:48
	 */
	private void updateYgInfo(Map<String, String[]> inputdata) throws Exception {
		Pojo1010110 beanIn = (Pojo1010110) this.getObject(inputdata, "BeanIn",Pojo1010110.class);
		beanIn.setYGXX_GXR(beanUser.getYHXX_YHID());
		boolean result = false;
		
		try {
			result = service.updateYgInfo(beanIn);
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
	 * @FunctionName: deleteYgInfo
	 * @Description: 删除员工信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年2月9日 下午1:46:00
	 */
	private void deleteYgInfo(Map<String, String[]> inputdata) throws Exception {
		Pojo1010110 beanIn = (Pojo1010110) this.getObject(inputdata, "BeanIn",Pojo1010110.class);
		beanIn.setYGXX_GXR(beanUser.getYHXX_YHID());
		boolean result = false;
		
		try {
			result = service.deleteYgInfo(beanIn);
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