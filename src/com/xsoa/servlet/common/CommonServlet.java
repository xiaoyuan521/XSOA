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
import com.xsoa.pojo.common.Pojo_SELECT_LIST;
import com.xsoa.service.common.CommonService;

/**
 * @ClassName: CommonServlet
 * @Package:com.hjlwl.servlet.common
 * @Description: 共通Servlet
 * @author ljg
 * @date 2014年7月21日 上午8:30:27
 * @update
 */
/**
 * Servlet implementation class CommonServlet
 */
@WebServlet("/CommonServlet")
public class CommonServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	/* 命令定义部分 */
	public static final String LOAD_SELECT = "LOAD_SELECT";
	public static final String GET_USER_SESSION = "GET_USER_SESSION";
	public static final String GET_USERID_SESSION = "GET_USERID_SESSION";
	public static final String GET_YGID_SESSION = "GET_YGID_SESSION";
	public static final String CLEAR_SESSION = "CLEAR_SESSION";

	public static final String TYPE_YHJS	 = "TYPE_YHJS"; //用户角色
	public static final String TYPE_ZDMC	 = "TYPE_ZDMC"; //站点名称

    public static final String YHXX_WJMC     = "YHXX_WJMC";//玩家名称
    public static final String YHXX_WJJS     = "YHXX_WJJS";//玩家角色
	public static final String TYPE_BMMC     = "TYPE_BMMC";//部门名称
	public static final String TYPE_BMJS     = "TYPE_BMJS";//部门-角色
	public static final String TYPE_BMYG     = "TYPE_BMYG";//部门-员工
	public static final String TYPE_JSZW     = "TYPE_JSZW";//角色-职位
	public static final String TYPE_GLGN     = "TYPE_GLGN";//关联功能
	public static final String TYPE_YGXM     = "TYPE_YGXM";//员工姓名
	public static final String TYPE_ZWMC     = "TYPE_ZWMC";//职务名称
	public static final String TYPE_XJLX     = "TYPE_XJLX";//休假类型
	public static final String TYPE_SPZT     = "TYPE_SPZT";//审批状态
	public static final String TYPE_KHMC     = "TYPE_KHMC";//客户名称
	public static final String TYPE_NY       = "TYPE_NY"; //年月(当前月的上下5个月)
	public static final String TYPE_RBZT     = "TYPE_RBZT";//日报状态
	public static final String TYPE_JBLX    = "TYPE_JBLX";//加班类型
	public static final String TYPE_SJZG    = "TYPE_SJZG";//上级主管
	public static final String TYPE_PJLX    = "TYPE_PJLX";//评价类型
	public static final String TYPE_SJLX    = "TYPE_SJLX";//事件类型
	public static final String TYPE_HTLX   = "TYPE_HTLX";//合同类型
	public static final String TYPE_YGPJ_BMMC  = "TYPE_YGPJ_BMMC";//部门名称（员工评价功能）
	public static final String TYPE_YGPJ_BMYG = "TYPE_YGPJ_BMYG";//部门-员工（员工评价功能）

	public static final String SPLC_KQGL     = "SPLC_KQGL";//考勤管理
	public static final String SPLC_KQXJ     = "SPLC_KQXJ";//考勤休假
	public static final String SPLC_KQJB     = "SPLC_KQJB";//考勤加班
	public static final String SPLC_KQCC     = "SPLC_KQCC";//考勤出差
	public static final String SPLC_XMGL     = "SPLC_XMGL";//项目管理
	public static final String SPLC_RBGL     = "SPLC_RBGL";//日报管理
	public static final String SPLC_BXGL     = "SPLC_BXGL";//报销管理
	public static final String SPLC_CGGL     = "SPLC_CGGL";//采购管理
	public static final String SPLC_KFZJ     = "SPLC_KFZJ";//可否终结


	/* 本Servlet对应的Service */
	private CommonService service;
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;

    public CommonServlet() {
        super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {

		service = new CommonService();
		arrResult = new ArrayList<Object>();

		String CMD = this.getString(inputdata, "CMD");

		if (LOAD_SELECT.equals(CMD)) {
			loadSelectList(inputdata);
		}else if (GET_USER_SESSION.equals(CMD)) {
			getUserSession(inputdata);
		}else if (GET_USERID_SESSION.equals(CMD)) {
			getUserIdSession(inputdata);
		}else if (GET_YGID_SESSION.equals(CMD)) {
			getYGIDSession(inputdata);
		}else if (CLEAR_SESSION.equals(CMD)) {
			this.clearSession();
		}else if (SPLC_KFZJ.equals(CMD)) {
			getSPR_KFZJ(inputdata);
		}
	}

	private void loadSelectList(Map<String, String[]> inputdata) throws Exception{
		List<Pojo_SELECT_LIST> result = null;
		try{
			String strType = this.getString(inputdata, "TYPE");// 下拉列表种类
			if (TYPE_YHJS.equals(strType)) {//角色select
				result = service.getYhjsList();
			} else if (TYPE_ZDMC.equals(strType)) {//全部站点select
				result = service.getZdmcList();
			} else if (TYPE_BMMC.equals(strType)) {
				result = service.getBmmcList();
			} else if (strType.contains(TYPE_BMJS)) {
				result = service.getBmjsList(strType.split("-")[1]);
			} else if (strType.contains(TYPE_BMYG)) {
				result = service.getBmygList(strType.split("-")[1]);
			} else if (strType.contains(TYPE_JSZW)) {
				result = service.getZwmcList(strType.split("-")[1], "part");
			} else if (strType.equals(TYPE_GLGN)) {
				result = service.getGLGNList();
			} else if (TYPE_YGXM.equals(strType)) {
				result = service.getYgxmList();
			} else if (TYPE_ZWMC.equals(strType)) {
				result = service.getZwmcList("", "all");
			} else if (TYPE_XJLX.equals(strType)) {
				result = service.getDICTList(strType.split("_")[1]);
			} else if (TYPE_SPZT.equals(strType)) {
				result = service.getDICTList(strType.split("_")[1]);
			} else if (TYPE_KHMC.equals(strType)) {
				Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
				result = service.getKhmcList(beanUser.getYHXX_YHID());
			} else if (TYPE_NY.equals(strType)) {
				result = service.getNyList();
			} else if (TYPE_RBZT.equals(strType)) {
				result = service.getDICTList(strType.split("_")[1]);
			} else if (strType.contains("SPLC_")) {
				result = getSPRList(strType);
			} else if (TYPE_JBLX.equals(strType)) {
				result = service.getDICTList(strType.split("_")[1]);
			} else if (strType.contains(TYPE_SJZG)) {
				result = service.getSjzgList(strType.split("-")[1], strType.split("-")[2], strType.split("-")[3]);
			} else if (TYPE_PJLX.equals(strType)) {
				result = service.getDICTList(strType.split("_")[1]);
			} else if (TYPE_SJLX.equals(strType)) {
				result = service.getDICTList(strType.split("_")[1]);
			} else if (TYPE_HTLX.equals(strType)) {
				result = service.getDICTList(strType.split("_")[1]);
			} else if (strType.contains(TYPE_YGPJ_BMMC)) {
				result = service.getYgpjBmmcList(strType.split("-")[1]);
			} else if (strType.contains(TYPE_YGPJ_BMYG)) {
				result = service.getYgpjBmygList(strType.split("-")[1], strType.split("-")[2]);
			} else if (strType.contains(YHXX_WJMC)) {
			    result = service.getWjmcList();
            } else if (strType.contains(YHXX_WJJS)) {
                result = service.getWjjsList();
            }

			arrResult.add("SUCCESS");
			arrResult.add(result);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			arrResult.add("ERROR");
		} finally {
			// 输出Ajax返回结果。
			print(arrResult);
		}
	}

	private void getUserSession(Map<String, String[]> inputdata) throws Exception{
		try{
			Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
			if(beanUser==null){
				arrResult.add("NOLOGIN");
			}else{
				arrResult.add(beanUser.getYHXX_YHMC());
			}
		}catch(Exception e){
			MyLogger.error(this.getClass().getName(), e);
			arrResult.add("ERROR");
		}finally {
			// 输出Ajax返回结果。
			print(arrResult);
		}
	}

	/**
	 * @FunctionName: getUserIdSession
	 * @Description: 取得Session的用户ID
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ljg
	 * @date 2015年3月11日 上午9:56:55
	 */
	private void getUserIdSession(Map<String, String[]> inputdata) throws Exception{
		try{
			Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
			if(beanUser==null){
				arrResult.add("NOLOGIN");
			}else{
				arrResult.add(beanUser.getYHXX_YHID());
			}
		}catch(Exception e){
			MyLogger.error(this.getClass().getName(), e);
			arrResult.add("ERROR");
		}finally {
			// 输出Ajax返回结果。
			print(arrResult);
		}
	}

	/**
	 * @FunctionName: getYGIDSession
	 * @Description: 取得Session中的员工ID
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ljg
	 * @date 2015年3月11日 上午9:57:15
	 */
	private void getYGIDSession(Map<String, String[]> inputdata) throws Exception{
		try{
			Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
			if(beanUser==null){
				arrResult.add("NOLOGIN");
			}else{
				arrResult.add(beanUser.getYHXX_BMID());
			}
		}catch(Exception e){
			MyLogger.error(this.getClass().getName(), e);
			arrResult.add("ERROR");
		}finally {
			// 输出Ajax返回结果。
			print(arrResult);
		}
	}

	/**
	 * @FunctionName: getSPRList
	 * @Description: 取得审批人列表
	 * @param flag
	 * @return
	 * @throws Exception
	 * @return List<Pojo_SELECT_LIST>
	 * @author ljg
	 * @date 2015年2月28日 上午11:23:08
	 */
	private List<Pojo_SELECT_LIST> getSPRList(String flag) throws Exception{
		List<Pojo_SELECT_LIST> result = null;
		String strSQID = "";
		String strCMD = "";
		try{
			Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
			String strCurrYGId = beanUser.getYHXX_BMID();

			strCMD = flag.split("-")[0];
			if(flag.split("-").length==2){//审批：传入申请ID
				strSQID=flag.split("-")[1];
			}else{//申请：申请ID为当前用户
				strSQID=strCurrYGId;
			}

			result = service.getSPRList(strCMD, strCurrYGId,strSQID);
		}catch(Exception e){
			MyLogger.error(this.getClass().getName(), e);
		}
		return result;
	}

	/**
	 * @FunctionName: getSPR_KFZJ
	 * @Description: 审批人可否终结
	 * @param inputdata
	 * @return
	 * @throws Exception
	 * @return String
	 * @author ljg
	 * @date 2015年3月7日 下午5:01:11
	 */
	private void getSPR_KFZJ(Map<String, String[]> inputdata) throws Exception{
		try{
			String strGLGN = this.getString(inputdata, "GLGN");// 关联功能
			String strSQDM = this.getString(inputdata, "SQDM");// 申请代码

			Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
			String strCurrYGId = beanUser.getYHXX_BMID();

			Integer res = service.getSPR_KFZJ(strGLGN, strCurrYGId,strSQDM);
			if(res>0){
				arrResult.add("1");
			}else{
				arrResult.add("0");
			}
		}catch(Exception e){
			MyLogger.error(this.getClass().getName(), e);
			arrResult.add("ERROR");
		}finally {
			// 输出Ajax返回结果。
			print(arrResult);
		}
	}

}
