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
import com.xsoa.pojo.basetable.Pojo_MENU;
import com.xsoa.pojo.basetable.Pojo_YHXX;
import com.xsoa.pojo.custom.pojo_9010000.Pojo9010140;
import com.xsoa.service.service9010000.Service9010140;

/**
 *
 * @ClassName: Servlet9010140
 * @Package:com.xsoa.servlet.servlet9010000
 * @Description: 菜单管理控制类
 * @author ztz
 * @date 2014年10月30日 上午11:15:58
 * @update
 */
/**
 * Servlet implementation class Servlet9010110
 */
@WebServlet("/Servlet9010140")
public class Servlet9010140 extends BaseServlet {
private static final long serialVersionUID = 1L;

	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	public static final String CMD_INSERT = "CMD_INSERT";
	public static final String CMD_UPDATE = "CMD_UPDATE";
	public static final String CMD_DELETE = "CMD_DELETE";
	public static final String CMD_CHK_EXIST = "CMD_CHK_EXIST";
	public static final String CMD_RECOVERY = "CMD_RECOVERY";

	/* 本Servlet对应的Service */
	private Service9010140 service;
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;

    public Servlet9010140() {
    	super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service9010140();
		arrResult = new ArrayList<Object>();

		String CMD = this.getString(inputdata, "CMD");

		if (CMD_SELECT.equals(CMD)) {
			getMenuList(inputdata);
		} else if (CMD_CHK_EXIST.equals(CMD)) {
			chkMenuExist(inputdata);
		} else if (CMD_INSERT.equals(CMD)) {
			insertMenu(inputdata);
		} else if (CMD_UPDATE.equals(CMD)) {
			updateMenu(inputdata);
		} else if (CMD_DELETE.equals(CMD)) {
			deleteMenu(inputdata);
		} else if (CMD_RECOVERY.equals(CMD)) {
			recoveryMenu(inputdata);
		}
	}
	/**
	 *
	 * @FunctionName: getMenuList
	 * @Description: 获取菜单信息数据个数和列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年2月2日 下午4:22:43
	 */
	private void getMenuList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo9010140 beanIn = (Pojo9010140) this.getObject(inputdata, "beanLoad",
				Pojo9010140.class);

		int TotalCount = 0;
		List<Pojo9010140> PageData = new ArrayList<Pojo9010140>();

		try {
			TotalCount = service.getMenuList_TotalCount(beanIn);
			PageData = service.getMenuList_PageData(beanIn, page, limit, sort);

		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(TotalCount, PageData);
		}
	}
	/**
	 *
	 * @FunctionName: chkMenuExist
	 * @Description: 判断菜单信息是否存在
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年2月2日 下午4:23:07
	 */
	private void chkMenuExist(Map<String, String[]> inputdata) throws Exception {
		Pojo_MENU beanIn = (Pojo_MENU) this.getObject(inputdata, "BeanIn",Pojo_MENU.class);

		try {
			int TotalCount = service.getMenuList_TotalCount(beanIn);
			if(TotalCount > 0){
				arrResult.add("DATA_EXIST");
			}else{
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
	 * @FunctionName: insertMenu
	 * @Description: 新增菜单信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2016-12-27
	 */
	private void insertMenu(Map<String, String[]> inputdata) throws Exception {
		Pojo_MENU beanIn = (Pojo_MENU) this.getObject(inputdata, "BeanIn",Pojo_MENU.class);
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		beanIn.setMENU_CJR(beanUser.getYHXX_YHID());
		beanIn.setMENU_GXR(beanUser.getYHXX_YHID());
		int ret = 0;

		try {
			ret = service.insertMenu(beanIn);
			if(ret>0){
				arrResult.add("SUCCESS");
			}else{
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
	 * @FunctionName: updateMenu
	 * @Description: 修改菜单信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年2月2日 下午4:23:42
	 */
	private void updateMenu(Map<String, String[]> inputdata) throws Exception {
		Pojo_MENU beanIn = (Pojo_MENU) this.getObject(inputdata, "BeanIn",Pojo_MENU.class);
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		beanIn.setMENU_GXR(beanUser.getYHXX_YHID());
		int ret = 0;

		try {
			ret = service.updateMenu(beanIn);
			if(ret>0){
				arrResult.add("SUCCESS");
			}else{
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
	 * @FunctionName: deleteMenu
	 * @Description: 删除菜单信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年2月2日 下午4:23:55
	 */
	private void deleteMenu(Map<String, String[]> inputdata) throws Exception {
		Pojo_MENU beanIn = (Pojo_MENU) this.getObject(inputdata, "BeanIn",Pojo_MENU.class);
		int ret = 0;

		try {
			ret = service.deleteMenu(beanIn);
			if(ret>0){
				arrResult.add("SUCCESS");
			}else{
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
	 * @FunctionName: recoveryMenu
	 * @Description: 恢复删除的菜单信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年2月2日 下午4:24:09
	 * @update ztz at 2015年2月2日 下午4:24:09
	 */
	private void recoveryMenu(Map<String, String[]> inputdata) throws Exception {
		Pojo_MENU beanIn = (Pojo_MENU) this.getObject(inputdata, "BeanIn",Pojo_MENU.class);
		int ret = 0;

		try {
			ret = service.recoveryMenu(beanIn);
			if(ret>0){
				arrResult.add("SUCCESS");
			}else{
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