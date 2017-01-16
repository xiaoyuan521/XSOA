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
import com.xsoa.pojo.basetable.Pojo_YHJS;
import com.xsoa.pojo.basetable.Pojo_YHXX;
import com.xsoa.pojo.custom.pojo_9010000.Pojo9010120;
import com.xsoa.service.service9010000.Service9010120;

/**
 * 
 * @ClassName: Servlet9010120
 * @Package:com.xsoa.servlet.servlet9010000
 * @Description: 角色管理控制类
 * @author ztz
 * @date 2015年1月30日 下午2:46:41
 * @update ztz at 2015年1月30日 下午2:46:41
 */
/**
 * Servlet implementation class Servlet9010110
 */
@WebServlet("/Servlet9010120")
public class Servlet9010120 extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	public static final String CMD_CHK_EXIST = "CMD_CHK_EXIST";
	public static final String CMD_INSERT = "CMD_INSERT";
	public static final String CMD_UPDATE = "CMD_UPDATE";
	public static final String CMD_DELETE = "CMD_DELETE";
	
	/* 本Servlet对应的Service */
	private Service9010120 service;
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;
	
    public Servlet9010120() {
        super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service9010120();
		arrResult = new ArrayList<Object>();
		
		String CMD = this.getString(inputdata, "CMD");
		
		if (CMD_SELECT.equals(CMD)) {
			getRoleList(inputdata);
		} else if (CMD_CHK_EXIST.equals(CMD)) {
			chkRoleExist(inputdata);
		} else if (CMD_INSERT.equals(CMD)) {
			insertRole(inputdata);
		} else if (CMD_UPDATE.equals(CMD)) {
			updateRole(inputdata);
		} else if (CMD_DELETE.equals(CMD)) {
			deleteRole(inputdata);
		}		
	}
	/**
	 * 
	 * @FunctionName: getRoleList
	 * @Description: 获取角色信息数据个数及列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2016-9-6
	 */
	private void getRoleList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo9010120 beanIn = (Pojo9010120) this.getObject(inputdata, "beanLoad",
				Pojo9010120.class);

		int TotalCount = 0;
		List<Pojo9010120> roleDataList = new ArrayList<Pojo9010120>();
		
		try {
			TotalCount = service.getRoleDataCount(beanIn);
			roleDataList = service.getRoleDataList(beanIn, page, limit, sort);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(TotalCount, roleDataList);
		}
	}
	/**
	 * 
	 * @FunctionName: chkRoleExist
	 * @Description: 判断角色信息是否已存在
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年1月30日 下午2:48:09
	 */
	private void chkRoleExist(Map<String, String[]> inputdata) throws Exception {
		Pojo9010120 beanIn = (Pojo9010120) this.getObject(inputdata, "BeanIn",Pojo9010120.class);

		try {
			int TotalCount = service.getRoleDataCount(beanIn);
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
	 * @FunctionName: insertRole
	 * @Description: 新增角色信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年1月30日 下午2:50:09
	 */
	private void insertRole(Map<String, String[]> inputdata) throws Exception {
		Pojo_YHJS beanIn = (Pojo_YHJS) this.getObject(inputdata, "BeanIn",Pojo_YHJS.class);
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		beanIn.setYHJS_CJR(beanUser.getYHXX_YHID());
		beanIn.setYHJS_GXR(beanUser.getYHXX_YHID());
		boolean result = false;
		
		try {
			result = service.insertRole(beanIn);
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
	 * @FunctionName: updateRole
	 * @Description: 修改角色信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年1月30日 下午2:50:33
	 */
	private void updateRole(Map<String, String[]> inputdata) throws Exception {
		Pojo_YHJS beanIn = (Pojo_YHJS) this.getObject(inputdata, "BeanIn",Pojo_YHJS.class);
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		beanIn.setYHJS_GXR(beanUser.getYHXX_YHID());
		boolean result = false;
		
		try {
			result = service.updateRole(beanIn);
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
	 * @FunctionName: deleteRole
	 * @Description: 删除角色信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年1月30日 下午2:50:47
	 */
	private void deleteRole(Map<String, String[]> inputdata) throws Exception {
		Pojo_YHJS beanIn = (Pojo_YHJS) this.getObject(inputdata, "BeanIn",Pojo_YHJS.class);
		boolean result = false;
		
		try {
			result = service.deleteRole(beanIn);
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