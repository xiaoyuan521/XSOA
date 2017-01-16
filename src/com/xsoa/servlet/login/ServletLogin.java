package com.xsoa.servlet.login;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.framework.core.BaseServlet;
import com.framework.log.MyLogger;
import com.framework.session.SessionAttribute;
import com.xsoa.pojo.basetable.Pojo_MENU;
import com.xsoa.pojo.basetable.Pojo_YHXX;
import com.xsoa.service.login.ServiceLogin;

/**
 * Servlet implementation class ServletLogin
 */
@WebServlet("/ServletLogin")
public class ServletLogin extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/* 命令定义部分 */
	public static final String CMD_USER_CHECK = "CMD_USER_CHECK";
	public static final String CMD_SELECT = "CMD_SELECT";
	public static final String CMD_SET_FRIEND = "CMD_SET_FRIEND";
	public static final String CMD_SET_GROUP = "CMD_SET_GROUP";

	// 本Servlet对应的Service
	private ServiceLogin service;
	// Ajax返回前台的结果集
	private ArrayList<Object> arrResult;

	//private String projPath;

	public ServletLogin() {
		super();
	}

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new ServiceLogin();
		arrResult = new ArrayList<Object>();
		//projPath = request.getSession().getServletContext().getRealPath("/bin/upload/pics/");

		if (CMD_USER_CHECK.equals(CMD)) {
			checkUser(inputdata);
		} else if (CMD_SET_FRIEND.equals(CMD)) {
		    setJsonFile(inputdata, request);
        } else if (CMD_SET_GROUP.equals(CMD)) {
            setJsonFile(inputdata, request);
        } else if (CMD_SELECT.equals(CMD)) {
		    selectList(inputdata);
		}

		// 输出Ajax返回结果集
	    //print(arrResult);

	}

	/**
	 * @FunctionName: checkUser
	 * @Description: 验证用户，成功后取得菜单信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2016-9-6
	 */
	private void checkUser(Map<String, String[]> inputdata) throws Exception {
		String userId = this.getString(inputdata, "USERID");// 用户ID
		String userPS = this.getString(inputdata, "PASSWORD");// 用户密码
		List<Pojo_MENU> menuList = null;// 菜单信息
		Pojo_YHXX userBean       = null;// 用户信息
		try {
			/* 验证用户 */
			userBean = service.checkUserID(userId);
			if(userBean == null){
				arrResult.add("CMD_NOEXIST");
				return;
			}else{
				String dbPass = userBean.getYHXX_YHMM();
				if(!dbPass.equals(userPS)){//验证密码
					arrResult.add("CMD_PASS_ERR");
					return;
				}
			}

			/*用户验证通过，取得用户对应的菜单信息*/
			menuList=service.getMenuInfoByUser(userId);

			if(menuList==null){
				arrResult.add("CMD_MENU_NULL");
				return;
			}
			/*记录登录用户信息*/
			int ret = service.setLoginInfo(userId,this.getIpAddr());
			if(ret <= 0){
				System.out.println("插入登录日志失败！!");
			}
			/*double randomNum = Math.random();
			File dstFile = new File(projPath+"/"+userBean.getYHXX_YHID()+".JPG");
			if (dstFile.exists()) {
				userInfo.setHEAD_PICS("upload/pics/"+userBean.getYHXX_YHID()+".JPG?id="+randomNum);
			}else{
				userInfo.setHEAD_PICS("img/resources/nopic2.png?id="+randomNum);
			}*/

			setSessionObject(SessionAttribute.LOGIN_USER, userBean);//登录用户信息存入Session
			setSessionObject(SessionAttribute.LOGIN_MENU, menuList);//用户菜单信息存入Session
			setSessionObject(SessionAttribute.LOGIN_MENU_JSON, JSONArray.fromObject(menuList).toString());
			arrResult.add("CMD_OK");

		} catch (Exception e) {
			arrResult.add("CMD_EXCEPTION");
		} finally {
		    print(arrResult);
		}
	}

	/**
     * @FunctionName: selectList
     * @Description: 获取狼友和管理员名单
     * @param inputdata
     * @throws Exception
     * @return void
     * @author czl
	 * @throws IOException
	 * @throws ServletException
     * @date 2017-01-13
     */
	private void selectList(Map<String, String[]> inputdata) throws Exception {
	    List<Pojo_YHXX> dataList = new ArrayList<Pojo_YHXX>();
	    Pojo_YHXX data = new Pojo_YHXX();
        try {
            dataList = service.getDataList();
            data = service.getGLY();
            arrResult.add(dataList);
            arrResult.add(data);
        } catch (Exception e) {
            MyLogger.error(this.getClass().getName(), e);
        } finally {
            // 输出Ajax返回结果。
            print(arrResult);
        }
	}

	/**
     * @FunctionName: setJsonFile
     * @Description: 生成狼友的json文件
     * @param inputdata
     * @throws Exception
     * @return void
     * @author czl
     * @date 2017-01-13
     */
    private void setJsonFile(Map<String, String[]> inputdata, HttpServletRequest request) throws Exception {
        String jsonList = this.getString(inputdata, "LIST");// 用户ID
        String flag = this.getString(inputdata, "FLAG");
        String filePath = "";
        if ("friend".equals(flag)) {
            filePath = request.getSession().getServletContext().getRealPath("/bin/friend.json");
        } else if ("group".equals(flag)) {
            filePath = request.getSession().getServletContext().getRealPath("/bin/groups.json");
        }

        try {
            File file = new File(filePath);
            char [] stack = new char[1024];
            int top=-1;
            StringBuffer sb = new StringBuffer();
            char [] charArray = jsonList.toCharArray();
            for(int i=0;i<charArray.length;i++){
                char c= charArray[i];
                if ('{' == c || '[' == c) {
                    stack[++top] = c;
                    sb.append("\n"+charArray[i] + "\n");
                    for (int j = 0; j <= top; j++) {
                        sb.append("\t");
                    }
                    continue;
                }
                if ((i + 1) <= (charArray.length - 1)) {
                    char d = charArray[i+1];
                    if ('}' == d || ']' == d) {
                        top--;
                        sb.append(charArray[i] + "\n");
                        for (int j = 0; j <= top; j++) {
                            sb.append("\t");
                        }
                        continue;
                    }
                }
                if (',' == c) {
                    sb.append(charArray[i] + "");
                    for (int j = 0; j <= top; j++) {
                        sb.append("");
                    }
                    continue;
                }
                sb.append(c);
            }
            Writer write = new FileWriter(file);
            write.write(sb.toString());
            write.flush();
            write.close();

        } catch (Exception e) {
            arrResult.add("CMD_EXCEPTION");
        }
    }

}
