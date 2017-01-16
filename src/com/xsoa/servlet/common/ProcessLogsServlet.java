package com.xsoa.servlet.common;

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
import com.xsoa.pojo.common.Pojo_PROCESS_LOGS;
import com.xsoa.service.common.ProcessLogsService;

/**
 * 
 * @ClassName: ProcessLogsServlet
 * @Package:com.xsoa.servlet.common
 * @Description: 审批流程控制类
 * @author czl
 * @date 2015-03-18
 */
/**
 * Servlet implementation class ProcessLogsServlet
 */
@WebServlet("/ProcessLogsServlet")
public class ProcessLogsServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/* 命令定义部分 */
	public static final String CMD_INFO = "CMD_INFO";
	
	/* 本Servlet对应的Service */
	private ProcessLogsService service;
	
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;
	
	/* 当前登录系统的用户对象 */
	Pojo_YHXX beanUser;
	
    public ProcessLogsServlet() {
        super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new ProcessLogsService();
		arrResult = new ArrayList<Object>();
		beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		
		String CMD = this.getString(inputdata, "CMD");
		
		if (CMD_INFO.equals(CMD)) {
			getProcessList(inputdata);
		}	
	}
	/**
	 * 
	 * @FunctionName: getProcessList
	 * @Description: 获得审批流程信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2015-03-18
	 */
	private void getProcessList(Map<String, String[]> inputdata) throws Exception {
		Pojo_PROCESS_LOGS beanIn = (Pojo_PROCESS_LOGS) this.getObject(inputdata, "BeanIn",Pojo_PROCESS_LOGS.class);
		List<Pojo_PROCESS_LOGS> PageData = new ArrayList<Pojo_PROCESS_LOGS>();
		try {
			PageData = service.getProcessList(beanIn.getSQID(),beanIn.getLOGTYPE());
			if(PageData.size()>0){
				arrResult.add("SUCCESS");
				arrResult.add(PageData);	
			}else{
				arrResult.add("DATA_NULL");
				arrResult.add("");
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			print(arrResult);
		}
	}
}