package com.xsoa.servlet.servlet9010000;

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
import com.xsoa.pojo.custom.pojo_9010000.Pojo9010110;
import com.xsoa.service.service9010000.Service9010110;

/**
 *
 * @ClassName: Servlet9010110
 * @Package:com.xsoa.servlet.servlet9010000
 * @Description: 用户管理控制类
 * @author ztz
 * @date 2015年1月29日 下午3:09:10
 */
/**
 * Servlet implementation class Servlet9010110
 */
@WebServlet("/Servlet9010110")
public class Servlet9010110 extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	public static final String CMD_CHK_EXIST = "CMD_CHK_EXIST";
	public static final String CMD_INSERT = "CMD_INSERT";
	public static final String CMD_UPDATE = "CMD_UPDATE";
	public static final String CMD_DELETE = "CMD_DELETE";
	public static final String CMD_RECOVERY = "CMD_RECOVERY";

	/* 本Servlet对应的Service */
	private Service9010110 service;
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;

    public Servlet9010110() {
    	super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service9010110();
		arrResult = new ArrayList<Object>();

		String CMD = this.getString(inputdata, "CMD");

		if (CMD_SELECT.equals(CMD)) {
			getUserList(inputdata);
		} else if (CMD_CHK_EXIST.equals(CMD)) {
			chkUserExist(inputdata);
		} else if (CMD_INSERT.equals(CMD)) {
			insertUser(inputdata);
		} else if (CMD_UPDATE.equals(CMD)) {
			updateUser(inputdata);
		} else if (CMD_DELETE.equals(CMD)) {
			deleteUser(inputdata);
		} else if (CMD_RECOVERY.equals(CMD)) {
			recoveryUser(inputdata);
		}
	}
	/**
	 *
	 * @FunctionName: getUserList
	 * @Description: 获取用户信息数据个数及列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年1月29日 下午3:10:00
	 */
	private void getUserList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo9010110 beanIn = (Pojo9010110) this.getObject(inputdata, "beanLoad",
				Pojo9010110.class);

		int TotalCount = 0;
		List<Pojo9010110> PageData = new ArrayList<Pojo9010110>();

		try {
			TotalCount = service.getUserCount(beanIn);
			PageData = service.getUserList(beanIn, page, limit, sort);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(TotalCount, PageData);
		}
	}
	/**
	 *
	 * @FunctionName: chkUserExist
	 * @Description: 判断用户信息是否已存在
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年1月30日 下午2:47:50
	 */
	private void chkUserExist(Map<String, String[]> inputdata) throws Exception {
		Pojo9010110 beanIn = (Pojo9010110) this.getObject(inputdata, "BeanIn",Pojo9010110.class);

		try {
			int TotalCount = service.getUserCount(beanIn);
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
	 * @FunctionName: insertUser
	 * @Description: 新增用户信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2016-9-6
	 */
	private void insertUser(Map<String, String[]> inputdata) throws Exception {
		Pojo9010110 beanIn = (Pojo9010110) this.getObject(inputdata, "BeanIn",Pojo9010110.class);
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		beanIn.setYHXX_CJR(beanUser.getYHXX_YHID());
		beanIn.setYHXX_GXR(beanUser.getYHXX_YHID());
		boolean result = false;

		try {
			result = service.insertUser(beanIn);
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
	 * @FunctionName: updateUser
	 * @Description: 修改用户信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年1月30日 上午10:04:18
	 */
	private void updateUser(Map<String, String[]> inputdata) throws Exception {
		Pojo9010110 beanIn = (Pojo9010110) this.getObject(inputdata, "BeanIn",Pojo9010110.class);
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		beanIn.setYHXX_CJR(beanUser.getYHXX_YHID());
		beanIn.setYHXX_GXR(beanUser.getYHXX_YHID());
		boolean result = false;

		try {
			result = service.updateUser(beanIn);
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
	 * @FunctionName: deleteUser
	 * @Description: 删除用户信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年1月30日 上午10:04:27
	 */
	private void deleteUser(Map<String, String[]> inputdata) throws Exception {
		Pojo_YHXX beanIn = (Pojo_YHXX) this.getObject(inputdata, "BeanIn",Pojo_YHXX.class);
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		beanIn.setYHXX_GXR(beanUser.getYHXX_YHID());
		boolean result = false;

		try {
			result = service.deleteUser(beanIn);
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
	 * @FunctionName: recoveryUser
	 * @Description: 恢复删除的用户信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年1月30日 上午10:04:38
	 */
	private void recoveryUser(Map<String, String[]> inputdata) throws Exception {
		Pojo_YHXX beanIn = (Pojo_YHXX) this.getObject(inputdata, "BeanIn",Pojo_YHXX.class);
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		beanIn.setYHXX_GXR(beanUser.getYHXX_YHID());
		boolean result = false;

		try {
			result = service.recoveryUser(beanIn);
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