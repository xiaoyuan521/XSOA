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
import com.framework.util.MyStringUtils;
import com.xsoa.pojo.basetable.Pojo_YHXX;
import com.xsoa.pojo.custom.pojo_1020000.Pojo1020130;
import com.xsoa.service.service1020000.Service1020130;

@WebServlet("/Servlet1020130")
public class Servlet1020130 extends BaseServlet {
	private static final long serialVersionUID = 1L;

    /* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	public static final String CMD_SAVE = "CMD_SAVE";
	public static final String CMD_DELETE = "CMD_DELETE";
	public static final String CMD_GETXJXX = "CMD_GETXJXX";

	/* 本Servlet对应的Service */
	private Service1020130 service;

	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;

    public Servlet1020130() {
        super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service1020130();
		arrResult = new ArrayList<Object>();

		String CMD = this.getString(inputdata, "CMD");

		if (CMD_SELECT.equals(CMD)) {
			getXJSQList(inputdata);
		} else if (CMD_SAVE.equals(CMD)) {
			saveXJSQ(inputdata);
		} else if (CMD_DELETE.equals(CMD)) {
			deleteXJSQ(inputdata);
		} else if (CMD_GETXJXX.equals(CMD)) {
			getXJXXRow(inputdata);
		}
	}

	/**
	 * @FunctionName: getXJSQList
	 * @Description: 获取休假申请数据个数及列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ljg
	 * @date 2015年2月25日 下午3:20:08
	 */
	private void getXJSQList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo1020130 beanIn = (Pojo1020130) this.getObject(inputdata, "beanLoad",Pojo1020130.class);
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		beanIn.setXJSQ_SQR(beanUser.getYHXX_YHID());

		int TotalCount = 0;
		List<Pojo1020130> dataList = new ArrayList<Pojo1020130>();

		try {
			TotalCount = service.getXJSQDataCount(beanIn);
			dataList = service.getXJSQDataList(beanIn, page, limit, sort);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(TotalCount, dataList);
		}
	}

	/**
	 * @FunctionName: saveXJSQ
	 * @Description: 保存休假信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ljg
	 * @date 2015年3月2日 下午3:41:01
	 */
	private void saveXJSQ(Map<String, String[]> inputdata) throws Exception {
		Pojo1020130 beanIn = (Pojo1020130) this.getObject(inputdata, "BeanIn",Pojo1020130.class);
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		beanIn.setXJSQ_SQR(beanUser.getYHXX_YHID());
		beanIn.setXJSQ_CJR(beanUser.getYHXX_YHID());
		beanIn.setXJSQ_GXR(beanUser.getYHXX_YHID());
		boolean result = false;

		try {
			//检查是否已存在
			int TotalCount = service.getCheckXJSQCount(beanIn);
			if (TotalCount > 0) {
				arrResult.add("DATA_EXIST");
			}else{
				if(beanIn.getXJSQ_SQID().equals("")){//新增
					result = service.insertXJSQ(beanIn);
				}else{//修改
					result = service.updateXJSQ(beanIn);
				}
				if (result) {
					arrResult.add("SUCCESS");
				} else {
					arrResult.add("FAILURE");
				}
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
	 * @FunctionName: deleteXJSQ
	 * @Description: 删除休假申请
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ljg
	 * @date 2015年2月25日 下午3:21:40
	 */
	private void deleteXJSQ(Map<String, String[]> inputdata) throws Exception {
		Pojo1020130 beanIn = (Pojo1020130) this.getObject(inputdata, "BeanIn",Pojo1020130.class);
		boolean result = false;

		try {
			result = service.deleteXJSQ(beanIn);
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

	private void getXJXXRow(Map<String, String[]> inputdata) throws Exception{
		Pojo1020130 beanIn = (Pojo1020130) this.getObject(inputdata, "BeanIn",Pojo1020130.class);
		Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		beanIn.setXJSQ_SQR(beanUser.getYHXX_YHID());
		try {
			if (MyStringUtils.isNotBlank(beanIn.getXJSQ_SQID())){
				beanIn = service.getDataBySQID(beanIn);
			}else{
				beanIn = service.getDataByUSER(beanIn);
			}
			if (beanIn!=null) {
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
