package com.xsoa.servlet.servlet1090000;

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
import com.xsoa.pojo.custom.pojo_1090000.Pojo1090110;
import com.xsoa.service.service1090000.Service1090110;

/**	
 * @ClassName: Servlet1090110	
 * @Package:com.xsoa.servlet.servlet1090000	
 * @Description: 流程配置Servlet	
 * @author ljg	
 * @date 2015年2月6日 上午10:51:12	
 * @update		
 */	
@WebServlet("/Servlet1090110")
public class Servlet1090110 extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	public static final String CMD_CHK_EXIST = "CMD_CHK_EXIST";
	public static final String CMD_INSERT = "CMD_INSERT";
	public static final String CMD_UPDATE = "CMD_UPDATE";
	public static final String CMD_DELETE = "CMD_DELETE";
       
	/* 本Servlet对应的Service */
	private Service1090110 service;
	
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;
	
    public Servlet1090110() {
        super();
    }

    @Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service1090110();
		arrResult = new ArrayList<Object>();
		
		String CMD = this.getString(inputdata, "CMD");
		
		if (CMD_SELECT.equals(CMD)) {
			getLCXXList(inputdata);
		} else if (CMD_CHK_EXIST.equals(CMD)) {
			chkLCXXExist(inputdata);
		} else if (CMD_INSERT.equals(CMD)) {
			insertLCXX(inputdata);
		} else if (CMD_UPDATE.equals(CMD)) {
			updateLCXX(inputdata);
		} else if (CMD_DELETE.equals(CMD)) {
			deleteLCXX(inputdata);
		}		
	}

	/**	
	 * @FunctionName: getLCXXList	
	 * @Description: 获取职务信息数据个数及列表	
	 * @param inputdata
	 * @throws Exception	
	 * @return void	
	 * @author ljg	
	 * @date 2015年2月5日 下午4:14:27	
	 */	
	private void getLCXXList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo1090110 beanIn = (Pojo1090110) this.getObject(inputdata, "beanLoad",
				Pojo1090110.class);

		int TotalCount = 0;
		List<Pojo1090110> dataList = new ArrayList<Pojo1090110>();
		
		try {
			TotalCount = service.getLCXXDataCount(beanIn);
			dataList = service.getLCXXDataList(beanIn, page, limit, sort);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(TotalCount, dataList);
		}
	}

	/**	
	 * @FunctionName: chkLCXXExist	
	 * @Description: 判断审批流程是否重复(申请人,关联功能)	
	 * @param inputdata
	 * @throws Exception	
	 * @return void	
	 * @author ljg	
	 * @date 2015年2月5日 下午4:14:48	
	 */	
	private void chkLCXXExist(Map<String, String[]> inputdata) throws Exception {
		Pojo1090110 beanIn = (Pojo1090110) this.getObject(inputdata, "BeanIn",Pojo1090110.class);

		try {
			int TotalCount = service.getCheckLCXXCount(beanIn);
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
	 * @FunctionName: insertLCXX	
	 * @Description: 新增职务信息	
	 * @param inputdata
	 * @throws Exception	
	 * @return void	
	 * @author ljg	
	 * @date 2015年2月5日 下午4:17:26	
	 */	
	private void insertLCXX(Map<String, String[]> inputdata) throws Exception {
		Pojo1090110 beanIn = (Pojo1090110) this.getObject(inputdata, "BeanIn",Pojo1090110.class);
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		beanIn.setLCXX_CJR(beanUser.getYHXX_YHID());
		beanIn.setLCXX_GXR(beanUser.getYHXX_YHID());
		boolean result = false;
		
		try {
			result = service.insertLCXX(beanIn);
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
	 * @FunctionName: updateLCXX	
	 * @Description: 修改职务信息	
	 * @param inputdata
	 * @throws Exception	
	 * @return void	
	 * @author ljg	
	 * @date 2015年2月5日 下午4:21:29	
	 */	
	private void updateLCXX(Map<String, String[]> inputdata) throws Exception {
		Pojo1090110 beanIn = (Pojo1090110) this.getObject(inputdata, "BeanIn",Pojo1090110.class);
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		beanIn.setLCXX_GXR(beanUser.getYHXX_YHID());
		boolean result = false;
		
		try {
			result = service.updateLCXX(beanIn);
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
	 * @FunctionName: deleteLCXX	
	 * @Description: 删除职务信息	
	 * @param inputdata
	 * @throws Exception	
	 * @return void	
	 * @author ljg	
	 * @date 2015年2月5日 下午4:21:59	
	 */	
	private void deleteLCXX(Map<String, String[]> inputdata) throws Exception {
		Pojo1090110 beanIn = (Pojo1090110) this.getObject(inputdata, "BeanIn",Pojo1090110.class);
		boolean result = false;
		
		try {
			result = service.deleteLCXX(beanIn);
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
