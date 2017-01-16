package com.xsoa.servlet.servlet9040000;

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
import com.xsoa.pojo.basetable.Pojo_ZWXX;
import com.xsoa.service.service9040000.Service9040130;

/**	
 * @ClassName: Servlet9040130	
 * @Package:com.xsoa.servlet.servlet9040000	
 * @Description: 职务信息Servlet	
 * @author ljg	
 * @date 2015年2月5日 下午4:10:42	
 * @update		
 */	
@WebServlet("/Servlet9040130")
public class Servlet9040130 extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	public static final String CMD_CHK_EXIST = "CMD_CHK_EXIST";
	public static final String CMD_INSERT = "CMD_INSERT";
	public static final String CMD_UPDATE = "CMD_UPDATE";
	public static final String CMD_DELETE = "CMD_DELETE";
	
	/* 本Servlet对应的Service */
	private Service9040130 service;
	
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;
	
    public Servlet9040130() {
        super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service9040130();
		arrResult = new ArrayList<Object>();
		
		String CMD = this.getString(inputdata, "CMD");
		
		if (CMD_SELECT.equals(CMD)) {
			getZWXXList(inputdata);
		} else if (CMD_CHK_EXIST.equals(CMD)) {
			chkZWXXExist(inputdata);
		} else if (CMD_INSERT.equals(CMD)) {
			insertZWXX(inputdata);
		} else if (CMD_UPDATE.equals(CMD)) {
			updateZWXX(inputdata);
		} else if (CMD_DELETE.equals(CMD)) {
			deleteZWXX(inputdata);
		}		
	}

	/**	
	 * @FunctionName: getZWXXList	
	 * @Description: 获取职务信息数据个数及列表	
	 * @param inputdata
	 * @throws Exception	
	 * @return void	
	 * @author ljg	
	 * @date 2015年2月5日 下午4:14:27	
	 */	
	private void getZWXXList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo_ZWXX beanIn = (Pojo_ZWXX) this.getObject(inputdata, "beanLoad",
				Pojo_ZWXX.class);

		int TotalCount = 0;
		List<Pojo_ZWXX> dataList = new ArrayList<Pojo_ZWXX>();
		
		try {
			TotalCount = service.getZWXXDataCount(beanIn);
			dataList = service.getZWXXDataList(beanIn, page, limit, sort);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(TotalCount, dataList);
		}
	}

	/**	
	 * @FunctionName: chkZWXXExist	
	 * @Description: 判断职务信息是否已存在	
	 * @param inputdata
	 * @throws Exception	
	 * @return void	
	 * @author ljg	
	 * @date 2015年2月5日 下午4:14:48	
	 */	
	private void chkZWXXExist(Map<String, String[]> inputdata) throws Exception {
		Pojo_ZWXX beanIn = (Pojo_ZWXX) this.getObject(inputdata, "BeanIn",Pojo_ZWXX.class);

		try {
			int TotalCount = service.getZWXXDataCount(beanIn);
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
	 * @FunctionName: insertZWXX	
	 * @Description: 新增职务信息	
	 * @param inputdata
	 * @throws Exception	
	 * @return void	
	 * @author ljg	
	 * @date 2015年2月5日 下午4:17:26	
	 */	
	private void insertZWXX(Map<String, String[]> inputdata) throws Exception {
		Pojo_ZWXX beanIn = (Pojo_ZWXX) this.getObject(inputdata, "BeanIn",Pojo_ZWXX.class);
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		beanIn.setZWXX_CJR(beanUser.getYHXX_YHID());
		beanIn.setZWXX_GXR(beanUser.getYHXX_YHID());
		boolean result = false;
		
		try {
			result = service.insertZWXX(beanIn);
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
	 * @FunctionName: updateZWXX	
	 * @Description: 修改职务信息	
	 * @param inputdata
	 * @throws Exception	
	 * @return void	
	 * @author ljg	
	 * @date 2015年2月5日 下午4:21:29	
	 */	
	private void updateZWXX(Map<String, String[]> inputdata) throws Exception {
		Pojo_ZWXX beanIn = (Pojo_ZWXX) this.getObject(inputdata, "BeanIn",Pojo_ZWXX.class);
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		beanIn.setZWXX_GXR(beanUser.getYHXX_YHID());
		boolean result = false;
		
		try {
			result = service.updateZWXX(beanIn);
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
	 * @FunctionName: deleteZWXX	
	 * @Description: 删除职务信息	
	 * @param inputdata
	 * @throws Exception	
	 * @return void	
	 * @author ljg	
	 * @date 2015年2月5日 下午4:21:59	
	 */	
	private void deleteZWXX(Map<String, String[]> inputdata) throws Exception {
		Pojo_ZWXX beanIn = (Pojo_ZWXX) this.getObject(inputdata, "BeanIn",Pojo_ZWXX.class);
		boolean result = false;
		
		try {
			result = service.deleteZWXX(beanIn);
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