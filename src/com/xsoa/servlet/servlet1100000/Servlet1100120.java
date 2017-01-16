package com.xsoa.servlet.servlet1100000;

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
import com.xsoa.pojo.basetable.Pojo_BKZT;
import com.xsoa.pojo.basetable.Pojo_LTDJ;
import com.xsoa.pojo.basetable.Pojo_LTJY;
import com.xsoa.pojo.basetable.Pojo_YHXX;
import com.xsoa.pojo.custom.pojo_1100000.Pojo1100120;
import com.xsoa.pojo.custom.pojo_1100000.Pojo1100121;
import com.xsoa.pojo.custom.pojo_1100000.Pojo1100122;
import com.xsoa.service.service1100000.Service1100120;
import com.xsoa.util.StringControl;

/**
 *
 * @ClassName: Servlet1100110
 * @Package:com.xsoa.servlet.servlet1100000
 * @Description: 论坛管理控制类
 * @author czl
 * @date 2016-9-19
 */
/**
 * Servlet implementation class Servlet1100110
 */
@WebServlet("/Servlet1100120")
public class Servlet1100120 extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	public static final String CMD_SELECT_ZT = "CMD_SELECT_ZT";
	public static final String CMD_SAVE_ZT = "CMD_SAVE_ZT";
	public static final String CMD_SELECT_ZT_INFO = "CMD_SELECT_ZT_INFO";
	public static final String CMD_SELECT_HF = "CMD_SELECT_HF";
	public static final String CMD_SAVE_HF = "CMD_SAVE_HF";
	public static final String CMD_UPDATE_ZD = "CMD_UPDATE_ZD";
	public static final String CMD_DELETE_ZT = "CMD_DELETE_ZT";
	public static final String CMD_DELETE_HF = "CMD_DELETE_HF";

	/* 本Servlet对应的Service */
	private Service1100120 service;

	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;

	/* 当前登录系统的用户对象 */
	Pojo_YHXX beanUser;

    public Servlet1100120() {
        super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service1100120();
		arrResult = new ArrayList<Object>();
		beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);

		String CMD = this.getString(inputdata, "CMD");

		if (CMD_SELECT.equals(CMD)) {
			getDataList();
		} else if (CMD_SELECT_ZT.equals(CMD)) {
		    getZtDataList(inputdata);
		} else if (CMD_SAVE_ZT.equals(CMD)) {
		    saveZtData(inputdata);
        } else if (CMD_SELECT_ZT_INFO.equals(CMD)) {
            getZtInfo(inputdata);
        } else if (CMD_SELECT_HF.equals(CMD)) {
            getHfDataList(inputdata);
        } else if (CMD_SAVE_HF.equals(CMD)) {
            saveHfData(inputdata);
        } else if (CMD_UPDATE_ZD.equals(CMD)) {
            updateZdData(inputdata);
        } else if (CMD_DELETE_ZT.equals(CMD)) {
            deleteZtData(inputdata);
        } else if (CMD_DELETE_HF.equals(CMD)) {
            deleteHfData(inputdata);
        }
	}
	/**
	 *
	 * @FunctionName: getDataList
	 * @Description: 获取论坛版块列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2016-9-28
	 */
	public void getDataList() throws Exception {
	    List<Pojo1100120> dataList = new ArrayList<Pojo1100120>();
	    List<Pojo1100120> bkList = new ArrayList<Pojo1100120>();
	    Pojo1100120 data;
        try {
            dataList = service.getDataList(beanUser.getYHXX_BMID());
            for (Pojo1100120 pojo : dataList) {
                data = new Pojo1100120();
                data.setLTBK_BKID(pojo.getLTBK_BKID());
                data.setLTBK_BKMC(pojo.getLTBK_BKMC());
                data.setLTBK_TPLJ(pojo.getLTBK_TPLJ());
                data.setLTBK_BZID(pojo.getLTBK_BZID());
                data.setBZMC(pojo.getBZMC());
                data.setZTZS(String.valueOf(service.getCountZt(pojo.getLTBK_BKID())));
                Pojo_BKZT bkzt = service.getZtInfo(pojo.getLTBK_BKID());
                if (bkzt == null || "".equals(bkzt.getBKZT_ZTMC())) {
                    data.setZTMC("无");
                    data.setFBSJ("无");
                    data.setZZMC("无");
                } else {
                    data.setZTMC(bkzt.getBKZT_ZTMC());
                    data.setFBSJ(bkzt.getBKZT_FBSJ());
                    data.setZZMC(bkzt.getBKZT_FBR());
                }

                bkList.add(data);
            }
            arrResult.add(bkList);
        } catch (Exception e) {
            MyLogger.error(this.getClass().getName(), e);
        } finally {
            // 输出Ajax返回结果。
            print(arrResult);
        }
	}

	/**
    *
    * @FunctionName: getZtDataList
    * @Description: 获取版块主题列表
    * @param inputdata
    * @throws Exception
    * @return void
    * @author czl
    * @date 2016-9-28
    */
    public void getZtDataList(Map<String, String[]> inputdata) throws Exception {
        String bkid = this.getString(inputdata, "BKID");
        String yhid = this.getString(inputdata, "YHID");
        List<Pojo1100121> dataList = new ArrayList<Pojo1100121>();
        List<Pojo1100121> bkList = new ArrayList<Pojo1100121>();
        Pojo1100121 data;
        try {
            dataList = service.getZtDataList(yhid, bkid);
            for (Pojo1100121 pojo : dataList) {
                data = new Pojo1100121();
                data.setBKZT_ZTID(pojo.getBKZT_ZTID());
                data.setBKZT_ZTMC(pojo.getBKZT_ZTMC());
                data.setBKZT_ZTLB(pojo.getBKZT_ZTLB());
                data.setZZMC(pojo.getZZMC());
                data.setGRZP(pojo.getGRZP());
                data.setHFGS(pojo.getHFGS());
                Pojo1100122 po = service.getHfInfo(pojo.getBKZT_ZTID());
                if (po != null) {
                    data.setZHHF(po.getZZMC());
                    data.setZHZP(po.getGRZP());
                }
                bkList.add(data);
            }
            arrResult.add(bkList);
        } catch (Exception e) {
            MyLogger.error(this.getClass().getName(), e);
        } finally {
           // 输出Ajax返回结果。
            print(arrResult);
        }
    }

    /**
    *
    * @FunctionName: saveZtData
    * @Description: 新增主题
    * @param inputdata
    * @throws Exception
    * @return void
    * @author czl
    * @date 2016-10-10
    */
    private void saveZtData(Map<String, String[]> inputdata) throws Exception {
        Pojo1100121 beanIn = (Pojo1100121) this.getObject(inputdata, "BeanIn",Pojo1100121.class);
        beanIn.setBKZT_CJR(beanUser.getYHXX_YHID());
        beanIn.setBKZT_GXR(beanUser.getYHXX_YHID());

        boolean result = false;

        try {
            result = service.insertData(beanIn);
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
    *
    * @FunctionName: getZtInfo
    * @Description: 查看主题
    * @param inputdata
    * @throws Exception
    * @return void
    * @author czl
    * @date 2016-10-11
    */
    private void getZtInfo(Map<String, String[]> inputdata) throws Exception{
        Pojo1100121 beanIn = (Pojo1100121) this.getObject(inputdata, "BeanIn",Pojo1100121.class);
        try {
            if (MyStringUtils.isNotBlank(beanIn.getBKZT_ZTID())){
                Pojo_LTDJ po = new Pojo_LTDJ();
                Pojo_LTJY jo = new Pojo_LTJY();
                Pojo_LTDJ vo = new Pojo_LTDJ();
                beanIn = service.getZtData(beanIn);
                jo = service.getBbsXp(beanIn.getBKZT_FBR());
                po = service.getBbsLevel(jo.getLTJY_JYZ());
                vo = service.getBbsUp(jo.getLTJY_JYZ());
                beanIn.setZTNR(StringControl.valueOf(beanIn.getBKZT_ZTNR()));
                beanIn.setJBID(po.getLTDJ_JBID());
                beanIn.setJBMC(po.getLTDJ_JBMC());
                beanIn.setJBTP(po.getLTDJ_JBTP());
                beanIn.setSJJY(jo.getLTJY_JYZ()+"/"+vo.getLTDJ_JBJY());
            }
            if (beanIn!=null) {
                arrResult.add("SUCCESS");
                arrResult.add(beanIn);
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
    * @FunctionName: getHfDataList
    * @Description: 获取主题回复列表
    * @param inputdata
    * @throws Exception
    * @return void
    * @author czl
    * @date 2016-10-11
    */
    public void getHfDataList(Map<String, String[]> inputdata) throws Exception {
        String ztid = this.getString(inputdata, "ZTID");
        List<Pojo1100122> dataList = new ArrayList<Pojo1100122>();
        List<Pojo1100122> hfList = new ArrayList<Pojo1100122>();
        Pojo1100122 data;
        try {
            dataList = service.getHfDataList(ztid);
            for (Pojo1100122 pojo : dataList) {
                Pojo_LTDJ po = new Pojo_LTDJ();
                Pojo_LTJY jo = new Pojo_LTJY();
                Pojo_LTDJ vo = new Pojo_LTDJ();
                jo = service.getBbsXp(pojo.getZTHF_HFRY());
                po = service.getBbsLevel(jo.getLTJY_JYZ());
                vo = service.getBbsUp(jo.getLTJY_JYZ());
                data = new Pojo1100122();
                data.setZTHF_HFID(pojo.getZTHF_HFID());
                data.setZTHF_HFSJ(pojo.getZTHF_HFSJ());
                data.setHFNR(StringControl.valueOf(pojo.getZTHF_HFNR()));
                data.setZZMC(pojo.getZZMC());
                data.setGRZP(pojo.getGRZP());
                data.setJBID(po.getLTDJ_JBID());
                data.setJBMC(po.getLTDJ_JBMC());
                data.setJBTP(po.getLTDJ_JBTP());
                data.setSJJY(jo.getLTJY_JYZ()+"/"+vo.getLTDJ_JBJY());
                hfList.add(data);
            }
            arrResult.add(hfList);
        } catch (Exception e) {
            MyLogger.error(this.getClass().getName(), e);
        } finally {
           // 输出Ajax返回结果。
            print(arrResult);
        }
    }

    /**
    *
    * @FunctionName: saveHfData
    * @Description: 新增回复
    * @param inputdata
    * @throws Exception
    * @return void
    * @author czl
    * @date 2016-10-11
    */
    private void saveHfData(Map<String, String[]> inputdata) throws Exception {
        Pojo1100122 beanIn = (Pojo1100122) this.getObject(inputdata, "BeanIn",Pojo1100122.class);
        beanIn.setZTHF_CJR(beanUser.getYHXX_YHID());
        beanIn.setZTHF_GXR(beanUser.getYHXX_YHID());

        boolean result = false;

        try {
            result = service.insertHfData(beanIn);
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
    *
    * @FunctionName: updateZdData
    * @Description: 置顶
    * @param inputdata
    * @throws Exception
    * @return void
    * @author czl
    * @date 2016-10-13
    */
    private void updateZdData(Map<String, String[]> inputdata) throws Exception {
        Pojo1100121 beanIn = (Pojo1100121) this.getObject(inputdata, "BeanIn",Pojo1100121.class);
        beanIn.setBKZT_GXR(beanUser.getYHXX_YHID());

        boolean result = false;

        try {
            result = service.updateZdData(beanIn);
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
    *
    * @FunctionName: deleteZtData
    * @Description: 删除主题
    * @param inputdata
    * @throws Exception
    * @return void
    * @author czl
    * @date 2016-10-17
    */
    private void deleteZtData(Map<String, String[]> inputdata) throws Exception {
        Pojo1100121 beanIn = (Pojo1100121) this.getObject(inputdata, "BeanIn",Pojo1100121.class);

        boolean result = false;

        try {
            result = service.deleteZtData(beanIn);
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
    *
    * @FunctionName: deleteHfData
    * @Description: 删除回复
    * @param inputdata
    * @throws Exception
    * @return void
    * @author czl
    * @date 2016-10-17
    */
    private void deleteHfData(Map<String, String[]> inputdata) throws Exception {
        Pojo1100122 beanIn = (Pojo1100122) this.getObject(inputdata, "BeanIn",Pojo1100122.class);

        boolean result = false;

        try {
            result = service.deleteHfData(beanIn);
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