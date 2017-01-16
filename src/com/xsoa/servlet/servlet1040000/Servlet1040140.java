package com.xsoa.servlet.servlet1040000;

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
import com.xsoa.pojo.custom.pojo_1040000.Pojo1040140;
import com.xsoa.service.service1040000.Service1040140;

/**
 *
 * @ClassName: Servlet1040130
 * @Package:com.xsoa.servlet.servlet1040000
 * @Description: 玩家信息控制类
 * @author czl
 * @date 2016-9-7
 */
/**
 * Servlet implementation class Servlet1040140
 */
@WebServlet("/Servlet1040140")
public class Servlet1040140 extends BaseServlet {
    private static final long serialVersionUID = 1L;

    /* 命令定义部分 */
    public static final String CMD_SELECT = "CMD_SELECT";

    /* 本Servlet对应的Service */
    private Service1040140 service;

    /* Ajax返回前台的结果集 */
    private ArrayList<Object> arrResult;

    /* 当前登录系统的用户对象 */
    Pojo_YHXX beanUser;

    @Override
    public void process(HttpServletRequest request,
            HttpServletResponse response, Map<String, String[]> inputdata)
            throws IOException, ServletException, Exception {
        service = new Service1040140();
        arrResult = new ArrayList<Object>();
        beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);

        String CMD = this.getString(inputdata, "CMD");

        if (CMD_SELECT.equals(CMD)) {
            getDataList(inputdata);
        }
    }
    /**
     * Constructor of the object.
     */
    public Servlet1040140() {
        super();
    }
    /**
     *
     * @FunctionName: getDataList
     * @Description: 获取玩家信息
     * @param inputdata
     * @throws Exception
     * @return void
     * @author czl
     * @date 2016-9-6
     */
    private void getDataList(Map<String, String[]> inputdata) throws Exception {
        List<Pojo1040140> dataList = new ArrayList<Pojo1040140>();

        try {
            dataList = service.getDataList();
            arrResult.add(dataList);
        } catch (Exception e) {
            MyLogger.error(this.getClass().getName(), e);
        } finally {
            // 输出Ajax返回结果。
            print(arrResult);
        }
    }
}