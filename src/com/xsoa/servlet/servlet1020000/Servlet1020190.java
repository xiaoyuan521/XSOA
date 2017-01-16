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
import com.xsoa.pojo.custom.pojo_1020000.Pojo1020190;
import com.xsoa.service.service1020000.Service1020190;

/**
 * 
 * @ClassName: Servlet1020190
 * @Package:com.xsoa.servlet.servlet1020000
 * @Description: 出差审批控制类
 * @author ztz
 * @date 2015年3月9日 上午10:19:21
 */
/**
 * Servlet implementation class Servlet1020190
 */
@WebServlet("/Servlet1020190")
public class Servlet1020190 extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	public static final String CMD_INIT_INFO = "CMD_INIT_INFO";
	public static final String CMD_SAVE = "CMD_SAVE";
	
	/* 本Servlet对应的Service */
	private Service1020190 service;
	
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;
	
	/* 当前登录系统的用户对象 */
	Pojo_YHXX beanUser;
	
    public Servlet1020190() {
        super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service1020190();
		arrResult = new ArrayList<Object>();
		beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);

		String CMD = this.getString(inputdata, "CMD");
		
		if (CMD_SELECT.equals(CMD)) {
			getDataList(inputdata);
		} else if (CMD_INIT_INFO.equals(CMD)) {
			initData(inputdata);
		} else if (CMD_SAVE.equals(CMD)) {
			saveData(inputdata);
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
	 * @date 2015年3月9日 上午10:22:35
	 */
	private void getDataList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo1020190 beanIn = (Pojo1020190) this.getObject(inputdata, "beanLoad",
				Pojo1020190.class);
		beanIn.setCCSQ_XYSP(beanUser.getYHXX_YGID());

		int TotalCount = 0;
		List<Pojo1020190> dataList = new ArrayList<Pojo1020190>();
		
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
	 * @Description: 获取出差申请详情数据
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年3月9日 上午10:22:46
	 */
	private void initData(Map<String, String[]> inputdata) throws Exception {
		String dataId = this.getString(inputdata, "DATAID");
		Pojo1020190 beanIn = null;
		
		try {
			beanIn = service.initData(dataId);
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
	 * @FunctionName: saveData
	 * @Description: 保存审批
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年3月9日 下午2:47:03
	 */
	private void saveData(Map<String, String[]> inputdata) throws Exception {
		Pojo1020190 beanIn = (Pojo1020190) this.getObject(inputdata, "BeanIn",Pojo1020190.class);
		beanIn.setCCSQ_GXR(beanUser.getYHXX_YHID());
		beanIn.setCCSQ_SQR(beanUser.getYHXX_YGID());
		boolean result = false;
		
		try {
			result = service.saveData(beanIn);
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