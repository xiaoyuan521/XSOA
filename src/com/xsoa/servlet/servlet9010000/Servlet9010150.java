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
import com.xsoa.pojo.basetable.Pojo_BMXX;
import com.xsoa.pojo.basetable.Pojo_YHXX;
import com.xsoa.pojo.custom.pojo_9010000.Pojo9010150;
import com.xsoa.service.service9010000.Service9010150;


/**
 *
 * @ClassName: Servlet9040110
 * @Package:com.xsoa.servlet.servlet9010000
 * @Description: 部门管理控制类
 * @author czl
 * @date 2016-12-27
 */
/**
 * Servlet implementation class Servlet9010150
 */
@WebServlet("/Servlet9010150")
public class Servlet9010150 extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	public static final String CMD_CHK_EXIST = "CMD_CHK_EXIST";
	public static final String CMD_INSERT = "CMD_INSERT";
	public static final String CMD_UPDATE = "CMD_UPDATE";
	public static final String CMD_DELETE = "CMD_DELETE";

	/* 本Servlet对应的Service */
	private Service9010150 service;

	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;

    public Servlet9010150() {
        super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service9010150();
		arrResult = new ArrayList<Object>();

		String CMD = this.getString(inputdata, "CMD");

		if (CMD_SELECT.equals(CMD)) {
			getBmxxList(inputdata);
		} else if (CMD_CHK_EXIST.equals(CMD)) {
			chkBmxxExist(inputdata);
		} else if (CMD_INSERT.equals(CMD)) {
			insertBmxx(inputdata);
		} else if (CMD_UPDATE.equals(CMD)) {
			updateBmxx(inputdata);
		} else if (CMD_DELETE.equals(CMD)) {
			deleteBmxx(inputdata);
		}
	}
	/**
	 *
	 * @FunctionName: getBmxxList
	 * @Description: 获取部门信息数据个数及列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2016-12-27
	 */
	private void getBmxxList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo9010150 beanIn = (Pojo9010150) this.getObject(inputdata, "beanLoad",
				Pojo9010150.class);

		int TotalCount = 0;
		List<Pojo9010150> roleDataList = new ArrayList<Pojo9010150>();

		try {
			TotalCount = service.getBmxxDataCount(beanIn);
			roleDataList = service.getBmxxDataList(beanIn, page, limit, sort);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(TotalCount, roleDataList);
		}
	}
	/**
	 *
	 * @FunctionName: chkBmxxExist
	 * @Description: 判断部门信息是否已存在
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2016-12-27
	 */
	private void chkBmxxExist(Map<String, String[]> inputdata) throws Exception {
		Pojo9010150 beanIn = (Pojo9010150) this.getObject(inputdata, "BeanIn",Pojo9010150.class);

		try {
			int TotalCount = service.getBmxxDataCount(beanIn);
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
	 * @FunctionName: insertBmxx
	 * @Description: 新增部门信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2016-12-27
	 */
	private void insertBmxx(Map<String, String[]> inputdata) throws Exception {
		Pojo_BMXX beanIn = (Pojo_BMXX) this.getObject(inputdata, "BeanIn",Pojo_BMXX.class);
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		beanIn.setBMXX_CJR(beanUser.getYHXX_YHID());
		beanIn.setBMXX_GXR(beanUser.getYHXX_YHID());
		boolean result = false;

		try {
			result = service.insertBmxx(beanIn);
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
	 * @FunctionName: updateBmxx
	 * @Description: 修改部门信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2016-12-27
	 */
	private void updateBmxx(Map<String, String[]> inputdata) throws Exception {
		Pojo_BMXX beanIn = (Pojo_BMXX) this.getObject(inputdata, "BeanIn",Pojo_BMXX.class);
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		beanIn.setBMXX_GXR(beanUser.getYHXX_YHID());
		boolean result = false;

		try {
			result = service.updateBmxx(beanIn);
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
	 * @FunctionName: deleteBmxx
	 * @Description: 删除部门信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2016-12-27
	 */
	private void deleteBmxx(Map<String, String[]> inputdata) throws Exception {
		Pojo_BMXX beanIn = (Pojo_BMXX) this.getObject(inputdata, "BeanIn",Pojo_BMXX.class);
		boolean result = false;

		try {
			result = service.deleteBmxx(beanIn);
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