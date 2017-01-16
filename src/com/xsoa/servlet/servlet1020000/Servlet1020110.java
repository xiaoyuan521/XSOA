package com.xsoa.servlet.servlet1020000;

import java.io.IOException;
import java.util.ArrayList;
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
import com.xsoa.pojo.custom.pojo_1020000.Pojo1020110;
import com.xsoa.pojo.custom.pojo_1040000.Pojo1040160;
import com.xsoa.service.service1020000.Service1020110;


/**
 * @ClassName: Servlet1020110
 * @Package:com.xsoa.servlet.servlet1020000
 * @Description: 日程设定Servlet
 * @author czl
 * @date 2016-9-19
 * @update
 */
@WebServlet("/Servlet1020110")
public class Servlet1020110 extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/* 命令定义部分 */
	public static final String CMD_MUTI_SELECT = "CMD_MUTI_SELECT";
	public static final String CMD_MUTI_UPDATE = "CMD_MUTI_UPDATE";

	/* 本Servlet对应的Service */
	private Service1020110 service;

	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;

    public Servlet1020110() {
        super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service1020110();
		arrResult = new ArrayList<Object>();

		String CMD = this.getString(inputdata, "CMD");
		if(CMD_MUTI_SELECT.equals(CMD)){
            getRCSDMutiList(inputdata);
        }else if(CMD_MUTI_UPDATE.equals(CMD)){
            updateMutiRCSD(inputdata);
        }
	}

    private void getRCSDMutiList(Map<String, String[]> inputdata) throws Exception {

        Pojo1020110 beanIn = (Pojo1020110) this.getObject(inputdata, "beanLoad",
                Pojo1020110.class);
        Pojo1020110 resultList = null;
        try {
            resultList = service.getRCSDListByID(beanIn);
            arrResult.add("SUCCESS");
            arrResult.add(resultList);
        } catch (Exception e) {
            MyLogger.error(this.getClass().getName(), e);
        } finally {
            // 输出Ajax返回结果。
            print(arrResult);
        }
    }

    private void updateMutiRCSD(Map<String, String[]> inputdata) throws Exception {
        Pojo1040160 beanIn = (Pojo1040160) this.getObject(inputdata, "BeanIn",
                Pojo1040160.class);

        String dataDays = (String) this.getString(inputdata,"dataDays");
        JSONArray jsonArray = JSONArray.fromObject(dataDays);

        Pojo_YHXX beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
        beanIn.setBCRC_CJR(beanUser.getYHXX_YHID());
        beanIn.setBCRC_GXR(beanUser.getYHXX_YHID());

        try {
            int intResult = 0;

            //如果排班记录,则插入
            if(MyStringUtils.isBlank(beanIn.getBCRC_BCRCID())){
                 String strUUID = service.insertRCSD(beanIn);
                 if(!"".equals(strUUID)){
                     beanIn.setBCRC_BCRCID(strUUID);
                 }else{
                     arrResult.add("FAILURE");
                     return;
                 }
            }

            //已有排班记录,更新
            intResult = service.updateMuitRCSD(beanIn, jsonArray.toArray());

            if(intResult>0){
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
