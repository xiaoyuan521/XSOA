package com.xsoa.service.service1020000;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsoa.pojo.custom.pojo_1020000.Pojo1020110;
import com.xsoa.pojo.custom.pojo_1040000.Pojo1040160;

/**
 * @ClassName: Service1020110
 * @Package:com.xsoa.service.service1020000
 * @Description: 日程设定Service
 * @author czl
 * @date 2016-9-19
 * @update
 */
public class Service1020110 extends BaseService {
	private DBManager db = null;

	public Service1020110() {
		db = new DBManager();
	}

	/**
     *
     * @FunctionName: getRCSDList_TotalCount
     * @Description: 统计列表数据个数
     * @param beanIn
     * @return
     * @throws Exception
     * @return int
     * @author czl
     * @date 2014-12-19
     */
    public int getRCSDList_TotalCount(Pojo1040160 beanIn) throws Exception {
        int result = 0;
        try {
                db.openConnection();
                Pojo_BCXX bcxx = null;
                if(MyStringUtils.isNotBlank(beanIn.getGRRC_BCID())&&!("000").equals(beanIn.getGRRC_BCID())){
                    bcxx = this.getBCRQ(beanIn.getGRRC_BCID());
                    String btime = bcxx.getBCXX_KSSJ();
                    String etime = bcxx.getBCXX_JSSJ();
                    String betweens = getBetweenDate(btime,etime);
                    betweens = betweens.substring(0, betweens.length() - 1);
                    String[] between = betweens.split(",");
                    result = between.length;
                }
        } catch (Exception e) {
            MyLogger.error(this.getClass().getName(), e);
            throw e;
        } finally {
            db.closeConnection();
        }
        return result;
    }
    /**
     *
     * @FunctionName: getRCSDList_PageData
     * @Description: 查询明细
     * @param beanIn
     * @return
     * @throws Exception
     * @return int
     * @author czl
     * @param <T>
     * @date 2014-12-19
     */
    public <T> List<Pojo1040160> getRCSDList_PageData(Pojo1040160 beanIn, int page,
            int limit, String sort) throws Exception {
        List<Pojo1040160> result = null;

        try {
            db.openConnection();
            db.beginTran();
            Pojo_BCXX bcxx = null;
            if(MyStringUtils.isNotBlank(beanIn.getGRRC_BCID())&&!("000").equals(beanIn.getGRRC_BCID())){
                bcxx = this.getBCRQ(beanIn.getGRRC_BCID());
                String btime = bcxx.getBCXX_KSSJ();
                String etime = bcxx.getBCXX_JSSJ();
                String betweens = getBetweenDate(btime,etime);
                String newbetweens = "";
                betweens = betweens.substring(0, betweens.length() - 1);
                String[] between = betweens.split(",");

                List<Pojo1040160> PageData = new ArrayList<Pojo1040160>();
                PageData = getNYList(beanIn.getGRRC_BCID());
                String[] bet = new String[PageData.size()];
                for(int a=0;a<PageData.size();a++){
                    bet[a]=PageData.get(a).getGRRC_NY();
                 }
                for(String aItem:between){
                    boolean flag = true;
                    for(String bItem:bet){
                    if(aItem.equals(bItem)){
                    flag = false;
                    break;
                    }
                    }
                    if(flag){
                        newbetweens+=aItem+",";
                    }
                    }
                String[] newbetween = new String[0];
                if(!newbetweens.equals("")){
                    newbetween = newbetweens.split(",");
                }
                StringBuffer strbuf = new StringBuffer();
                if(PageData.size()==0){
                    strbuf.append(" SELECT A.*,TO_DATE(A.GRRC_NY, 'YYYY-MM') AS NY FROM(SELECT ");
                    strbuf.append("     B.GRRC_GRRCID, ");//班次日程ID
                    strbuf.append("     A.BCXX_BCMC AS BCMC, ");//班次名称
                    strbuf.append("     C.KCXX_KCMC AS KCMC, ");//课程名称
                    strbuf.append("     D.KCFY_RCID AS RCID, ");//时段ID
                    strbuf.append("     E.RCXQ_RCXQ AS RCXQ, ");//时段名称
                    strbuf.append("     A.BCXX_KSSJ ||'至'||A.BCXX_JSSJ AS BCSD, ");//班次时段
                    strbuf.append("     '"+between[0]+"' AS GRRC_NY, ");//上课年月
                    strbuf.append("     F01.RCXQ_SDJC AS GRRC_DAY01, ");//01日
                    strbuf.append("     F02.RCXQ_SDJC AS GRRC_DAY02, ");//02日
                    strbuf.append("     F03.RCXQ_SDJC AS GRRC_DAY03, ");//03日
                    strbuf.append("     F04.RCXQ_SDJC AS GRRC_DAY04, ");//04日
                    strbuf.append("     F05.RCXQ_SDJC AS GRRC_DAY05, ");//05日
                    strbuf.append("     F06.RCXQ_SDJC AS GRRC_DAY06, ");//06日
                    strbuf.append("     F07.RCXQ_SDJC AS GRRC_DAY07, ");//07日
                    strbuf.append("     F08.RCXQ_SDJC AS GRRC_DAY08, ");//08日
                    strbuf.append("     F09.RCXQ_SDJC AS GRRC_DAY09, ");//09日
                    strbuf.append("     F10.RCXQ_SDJC AS GRRC_DAY10, ");//10日
                    strbuf.append("     F11.RCXQ_SDJC AS GRRC_DAY11, ");//11日
                    strbuf.append("     F12.RCXQ_SDJC AS GRRC_DAY12, ");//12日
                    strbuf.append("     F13.RCXQ_SDJC AS GRRC_DAY13, ");//13日
                    strbuf.append("     F14.RCXQ_SDJC AS GRRC_DAY14, ");//14日
                    strbuf.append("     F15.RCXQ_SDJC AS GRRC_DAY15, ");//15日
                    strbuf.append("     F16.RCXQ_SDJC AS GRRC_DAY16, ");//16日
                    strbuf.append("     F17.RCXQ_SDJC AS GRRC_DAY17, ");//17日
                    strbuf.append("     F18.RCXQ_SDJC AS GRRC_DAY18, ");//18日
                    strbuf.append("     F19.RCXQ_SDJC AS GRRC_DAY19, ");//19日
                    strbuf.append("     F20.RCXQ_SDJC AS GRRC_DAY20, ");//20日
                    strbuf.append("     F21.RCXQ_SDJC AS GRRC_DAY21, ");//21日
                    strbuf.append("     F22.RCXQ_SDJC AS GRRC_DAY22, ");//22日
                    strbuf.append("     F23.RCXQ_SDJC AS GRRC_DAY23, ");//23日
                    strbuf.append("     F24.RCXQ_SDJC AS GRRC_DAY24, ");//24日
                    strbuf.append("     F25.RCXQ_SDJC AS GRRC_DAY25, ");//25日
                    strbuf.append("     F26.RCXQ_SDJC AS GRRC_DAY26, ");//26日
                    strbuf.append("     F27.RCXQ_SDJC AS GRRC_DAY27, ");//27日
                    strbuf.append("     F28.RCXQ_SDJC AS GRRC_DAY28, ");//28日
                    strbuf.append("     F29.RCXQ_SDJC AS GRRC_DAY29, ");//29日
                    strbuf.append("     F30.RCXQ_SDJC AS GRRC_DAY30, ");//30日
                    strbuf.append("     F31.RCXQ_SDJC AS GRRC_DAY31  ");//31日
                    strbuf.append("  FROM BCXX A,KCXX C,GRRC B,KCFY D,RCXQ E");
                    strbuf.append("      ,RCXQ F01,RCXQ F02,RCXQ F03,RCXQ F04,RCXQ F05,RCXQ F06,RCXQ F07 ");
                    strbuf.append("      ,RCXQ F08,RCXQ F09,RCXQ F10,RCXQ F11,RCXQ F12,RCXQ F13,RCXQ F14 ");
                    strbuf.append("      ,RCXQ F15,RCXQ F16,RCXQ F17,RCXQ F18,RCXQ F19,RCXQ F20,RCXQ F21 ");
                    strbuf.append("      ,RCXQ F22,RCXQ F23,RCXQ F24,RCXQ F25,RCXQ F26,RCXQ F27,RCXQ F28 ");
                    strbuf.append("      ,RCXQ F29,RCXQ F30,RCXQ F31");
                    strbuf.append(" WHERE A.BCXX_KCID = C.KCXX_KCID(+) ");
                    strbuf.append("   AND A.BCXX_BCID = B.GRRC_BCID(+)");
                    strbuf.append("   AND A.BCXX_KCFYID = D.KCFY_FYID(+)");
                    strbuf.append("   AND D.KCFY_RCID = E.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY01 = F01.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY02 = F02.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY03 = F03.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY04 = F04.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY05 = F05.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY06 = F06.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY07 = F07.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY08 = F08.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY09 = F09.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY10 = F10.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY11 = F11.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY12 = F12.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY13 = F13.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY14 = F14.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY15 = F15.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY16 = F16.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY17 = F17.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY18 = F18.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY19 = F19.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY20 = F20.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY21 = F21.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY22 = F22.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY23 = F23.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY24 = F24.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY25 = F25.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY26 = F26.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY27 = F27.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY28 = F28.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY29 = F29.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY30 = F30.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY31 = F31.RCXQ_RCID(+)");
                    if (MyStringUtils.isNotBlank(beanIn.getGRRC_BCID())) {
                        strbuf.append("  AND A.BCXX_BCID = '").append(beanIn.getGRRC_BCID())
                            .append("'");
                    }
                    for(int i = 0; i < between.length-1; i++){
                        strbuf.append(" UNION");
                        strbuf.append(" SELECT ");
                        strbuf.append("     B.GRRC_GRRCID, ");//班次日程ID
                        strbuf.append("     A.BCXX_BCMC AS BCMC, ");//班次名称
                        strbuf.append("     C.KCXX_KCMC AS KCMC, ");//课程名称
                        strbuf.append("     D.KCFY_RCID AS RCID, ");//时段ID
                        strbuf.append("     E.RCXQ_RCXQ AS RCXQ, ");//时段名称
                        strbuf.append("     A.BCXX_KSSJ ||'至'||A.BCXX_JSSJ AS BCSD, ");//班次时段
                        strbuf.append("     '"+between[i+1]+"' AS GRRC_NY, ");//上课年月
                        strbuf.append("     F01.RCXQ_SDJC AS GRRC_DAY01, ");//01日
                        strbuf.append("     F02.RCXQ_SDJC AS GRRC_DAY02, ");//02日
                        strbuf.append("     F03.RCXQ_SDJC AS GRRC_DAY03, ");//03日
                        strbuf.append("     F04.RCXQ_SDJC AS GRRC_DAY04, ");//04日
                        strbuf.append("     F05.RCXQ_SDJC AS GRRC_DAY05, ");//05日
                        strbuf.append("     F06.RCXQ_SDJC AS GRRC_DAY06, ");//06日
                        strbuf.append("     F07.RCXQ_SDJC AS GRRC_DAY07, ");//07日
                        strbuf.append("     F08.RCXQ_SDJC AS GRRC_DAY08, ");//08日
                        strbuf.append("     F09.RCXQ_SDJC AS GRRC_DAY09, ");//09日
                        strbuf.append("     F10.RCXQ_SDJC AS GRRC_DAY10, ");//10日
                        strbuf.append("     F11.RCXQ_SDJC AS GRRC_DAY11, ");//11日
                        strbuf.append("     F12.RCXQ_SDJC AS GRRC_DAY12, ");//12日
                        strbuf.append("     F13.RCXQ_SDJC AS GRRC_DAY13, ");//13日
                        strbuf.append("     F14.RCXQ_SDJC AS GRRC_DAY14, ");//14日
                        strbuf.append("     F15.RCXQ_SDJC AS GRRC_DAY15, ");//15日
                        strbuf.append("     F16.RCXQ_SDJC AS GRRC_DAY16, ");//16日
                        strbuf.append("     F17.RCXQ_SDJC AS GRRC_DAY17, ");//17日
                        strbuf.append("     F18.RCXQ_SDJC AS GRRC_DAY18, ");//18日
                        strbuf.append("     F19.RCXQ_SDJC AS GRRC_DAY19, ");//19日
                        strbuf.append("     F20.RCXQ_SDJC AS GRRC_DAY20, ");//20日
                        strbuf.append("     F21.RCXQ_SDJC AS GRRC_DAY21, ");//21日
                        strbuf.append("     F22.RCXQ_SDJC AS GRRC_DAY22, ");//22日
                        strbuf.append("     F23.RCXQ_SDJC AS GRRC_DAY23, ");//23日
                        strbuf.append("     F24.RCXQ_SDJC AS GRRC_DAY24, ");//24日
                        strbuf.append("     F25.RCXQ_SDJC AS GRRC_DAY25, ");//25日
                        strbuf.append("     F26.RCXQ_SDJC AS GRRC_DAY26, ");//26日
                        strbuf.append("     F27.RCXQ_SDJC AS GRRC_DAY27, ");//27日
                        strbuf.append("     F28.RCXQ_SDJC AS GRRC_DAY28, ");//28日
                        strbuf.append("     F29.RCXQ_SDJC AS GRRC_DAY29, ");//29日
                        strbuf.append("     F30.RCXQ_SDJC AS GRRC_DAY30, ");//30日
                        strbuf.append("     F31.RCXQ_SDJC AS GRRC_DAY31  ");//31日
                        strbuf.append("  FROM BCXX A,KCFY D,KCXX C,RCXQ E,(SELECT * FROM GRRC WHERE GRRC_NY='"+between[i+1]+"') B");
                        strbuf.append("      ,RCXQ F01,RCXQ F02,RCXQ F03,RCXQ F04,RCXQ F05,RCXQ F06,RCXQ F07 ");
                        strbuf.append("      ,RCXQ F08,RCXQ F09,RCXQ F10,RCXQ F11,RCXQ F12,RCXQ F13,RCXQ F14 ");
                        strbuf.append("      ,RCXQ F15,RCXQ F16,RCXQ F17,RCXQ F18,RCXQ F19,RCXQ F20,RCXQ F21 ");
                        strbuf.append("      ,RCXQ F22,RCXQ F23,RCXQ F24,RCXQ F25,RCXQ F26,RCXQ F27,RCXQ F28 ");
                        strbuf.append("      ,RCXQ F29,RCXQ F30,RCXQ F31");
                        strbuf.append(" WHERE A.BCXX_KCID = C.KCXX_KCID(+) ");
                        strbuf.append("   AND A.BCXX_BCID = B.GRRC_BCID(+)");
                        strbuf.append("   AND A.BCXX_KCFYID = D.KCFY_FYID(+)");
                        strbuf.append("   AND D.KCFY_RCID = E.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY01 = F01.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY02 = F02.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY03 = F03.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY04 = F04.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY05 = F05.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY06 = F06.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY07 = F07.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY08 = F08.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY09 = F09.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY10 = F10.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY11 = F11.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY12 = F12.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY13 = F13.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY14 = F14.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY15 = F15.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY16 = F16.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY17 = F17.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY18 = F18.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY19 = F19.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY20 = F20.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY21 = F21.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY22 = F22.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY23 = F23.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY24 = F24.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY25 = F25.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY26 = F26.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY27 = F27.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY28 = F28.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY29 = F29.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY30 = F30.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY31 = F31.RCXQ_RCID(+)");
                        if (MyStringUtils.isNotBlank(beanIn.getGRRC_BCID())) {
                            strbuf.append("  AND A.BCXX_BCID = '").append(beanIn.getGRRC_BCID())
                                .append("'");
                        }

                    }
                    strbuf.append("   ) A ORDER BY NY");
                }else{
                    strbuf.append(" SELECT A.*,TO_DATE(A.GRRC_NY, 'YYYY-MM') AS NY FROM(SELECT ");
                    strbuf.append("     B.GRRC_GRRCID, ");//班次日程ID
                    strbuf.append("     A.BCXX_BCMC AS BCMC, ");//班次名称
                    strbuf.append("     C.KCXX_KCMC AS KCMC, ");//课程名称
                    strbuf.append("     D.KCFY_RCID AS RCID, ");//时段ID
                    strbuf.append("     E.RCXQ_RCXQ AS RCXQ, ");//时段名称
                    strbuf.append("     A.BCXX_KSSJ ||'至'||A.BCXX_JSSJ AS BCSD, ");//班次时段
                    strbuf.append("     GRRC_NY, ");//上课年月
                    strbuf.append("     F01.RCXQ_SDJC AS GRRC_DAY01, ");//01日
                    strbuf.append("     F02.RCXQ_SDJC AS GRRC_DAY02, ");//02日
                    strbuf.append("     F03.RCXQ_SDJC AS GRRC_DAY03, ");//03日
                    strbuf.append("     F04.RCXQ_SDJC AS GRRC_DAY04, ");//04日
                    strbuf.append("     F05.RCXQ_SDJC AS GRRC_DAY05, ");//05日
                    strbuf.append("     F06.RCXQ_SDJC AS GRRC_DAY06, ");//06日
                    strbuf.append("     F07.RCXQ_SDJC AS GRRC_DAY07, ");//07日
                    strbuf.append("     F08.RCXQ_SDJC AS GRRC_DAY08, ");//08日
                    strbuf.append("     F09.RCXQ_SDJC AS GRRC_DAY09, ");//09日
                    strbuf.append("     F10.RCXQ_SDJC AS GRRC_DAY10, ");//10日
                    strbuf.append("     F11.RCXQ_SDJC AS GRRC_DAY11, ");//11日
                    strbuf.append("     F12.RCXQ_SDJC AS GRRC_DAY12, ");//12日
                    strbuf.append("     F13.RCXQ_SDJC AS GRRC_DAY13, ");//13日
                    strbuf.append("     F14.RCXQ_SDJC AS GRRC_DAY14, ");//14日
                    strbuf.append("     F15.RCXQ_SDJC AS GRRC_DAY15, ");//15日
                    strbuf.append("     F16.RCXQ_SDJC AS GRRC_DAY16, ");//16日
                    strbuf.append("     F17.RCXQ_SDJC AS GRRC_DAY17, ");//17日
                    strbuf.append("     F18.RCXQ_SDJC AS GRRC_DAY18, ");//18日
                    strbuf.append("     F19.RCXQ_SDJC AS GRRC_DAY19, ");//19日
                    strbuf.append("     F20.RCXQ_SDJC AS GRRC_DAY20, ");//20日
                    strbuf.append("     F21.RCXQ_SDJC AS GRRC_DAY21, ");//21日
                    strbuf.append("     F22.RCXQ_SDJC AS GRRC_DAY22, ");//22日
                    strbuf.append("     F23.RCXQ_SDJC AS GRRC_DAY23, ");//23日
                    strbuf.append("     F24.RCXQ_SDJC AS GRRC_DAY24, ");//24日
                    strbuf.append("     F25.RCXQ_SDJC AS GRRC_DAY25, ");//25日
                    strbuf.append("     F26.RCXQ_SDJC AS GRRC_DAY26, ");//26日
                    strbuf.append("     F27.RCXQ_SDJC AS GRRC_DAY27, ");//27日
                    strbuf.append("     F28.RCXQ_SDJC AS GRRC_DAY28, ");//28日
                    strbuf.append("     F29.RCXQ_SDJC AS GRRC_DAY29, ");//29日
                    strbuf.append("     F30.RCXQ_SDJC AS GRRC_DAY30, ");//30日
                    strbuf.append("     F31.RCXQ_SDJC AS GRRC_DAY31  ");//31日
                    strbuf.append("  FROM BCXX A,KCXX C,GRRC B,KCFY D,RCXQ E");
                    strbuf.append("      ,RCXQ F01,RCXQ F02,RCXQ F03,RCXQ F04,RCXQ F05,RCXQ F06,RCXQ F07 ");
                    strbuf.append("      ,RCXQ F08,RCXQ F09,RCXQ F10,RCXQ F11,RCXQ F12,RCXQ F13,RCXQ F14 ");
                    strbuf.append("      ,RCXQ F15,RCXQ F16,RCXQ F17,RCXQ F18,RCXQ F19,RCXQ F20,RCXQ F21 ");
                    strbuf.append("      ,RCXQ F22,RCXQ F23,RCXQ F24,RCXQ F25,RCXQ F26,RCXQ F27,RCXQ F28 ");
                    strbuf.append("      ,RCXQ F29,RCXQ F30,RCXQ F31");
                    strbuf.append(" WHERE A.BCXX_KCID = C.KCXX_KCID(+) ");
                    strbuf.append("   AND A.BCXX_BCID = B.GRRC_BCID(+)");
                    strbuf.append("   AND A.BCXX_KCFYID = D.KCFY_FYID(+)");
                    strbuf.append("   AND D.KCFY_RCID = E.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY01 = F01.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY02 = F02.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY03 = F03.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY04 = F04.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY05 = F05.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY06 = F06.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY07 = F07.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY08 = F08.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY09 = F09.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY10 = F10.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY11 = F11.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY12 = F12.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY13 = F13.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY14 = F14.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY15 = F15.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY16 = F16.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY17 = F17.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY18 = F18.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY19 = F19.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY20 = F20.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY21 = F21.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY22 = F22.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY23 = F23.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY24 = F24.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY25 = F25.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY26 = F26.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY27 = F27.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY28 = F28.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY29 = F29.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY30 = F30.RCXQ_RCID(+)");
                    strbuf.append("   AND B.GRRC_DAY31 = F31.RCXQ_RCID(+)");
                    if (MyStringUtils.isNotBlank(beanIn.getGRRC_BCID())) {
                        strbuf.append("  AND A.BCXX_BCID = '").append(beanIn.getGRRC_BCID())
                            .append("'");
                    }
                    for(int i = 0; i < newbetween.length; i++){
                        strbuf.append(" UNION");
                        strbuf.append(" SELECT ");
                        strbuf.append("     B.GRRC_GRRCID, ");//班次日程ID
                        strbuf.append("     A.BCXX_BCMC AS BCMC, ");//班次名称
                        strbuf.append("     C.KCXX_KCMC AS KCMC, ");//课程名称
                        strbuf.append("     D.KCFY_RCID AS RCID, ");//时段ID
                        strbuf.append("     E.RCXQ_RCXQ AS RCXQ, ");//时段名称
                        strbuf.append("     A.BCXX_KSSJ ||'至'||A.BCXX_JSSJ AS BCSD, ");//班次时段
                        strbuf.append("     '"+newbetween[i]+"' AS GRRC_NY, ");//上课年月
                        strbuf.append("     F01.RCXQ_SDJC AS GRRC_DAY01, ");//01日
                        strbuf.append("     F02.RCXQ_SDJC AS GRRC_DAY02, ");//02日
                        strbuf.append("     F03.RCXQ_SDJC AS GRRC_DAY03, ");//03日
                        strbuf.append("     F04.RCXQ_SDJC AS GRRC_DAY04, ");//04日
                        strbuf.append("     F05.RCXQ_SDJC AS GRRC_DAY05, ");//05日
                        strbuf.append("     F06.RCXQ_SDJC AS GRRC_DAY06, ");//06日
                        strbuf.append("     F07.RCXQ_SDJC AS GRRC_DAY07, ");//07日
                        strbuf.append("     F08.RCXQ_SDJC AS GRRC_DAY08, ");//08日
                        strbuf.append("     F09.RCXQ_SDJC AS GRRC_DAY09, ");//09日
                        strbuf.append("     F10.RCXQ_SDJC AS GRRC_DAY10, ");//10日
                        strbuf.append("     F11.RCXQ_SDJC AS GRRC_DAY11, ");//11日
                        strbuf.append("     F12.RCXQ_SDJC AS GRRC_DAY12, ");//12日
                        strbuf.append("     F13.RCXQ_SDJC AS GRRC_DAY13, ");//13日
                        strbuf.append("     F14.RCXQ_SDJC AS GRRC_DAY14, ");//14日
                        strbuf.append("     F15.RCXQ_SDJC AS GRRC_DAY15, ");//15日
                        strbuf.append("     F16.RCXQ_SDJC AS GRRC_DAY16, ");//16日
                        strbuf.append("     F17.RCXQ_SDJC AS GRRC_DAY17, ");//17日
                        strbuf.append("     F18.RCXQ_SDJC AS GRRC_DAY18, ");//18日
                        strbuf.append("     F19.RCXQ_SDJC AS GRRC_DAY19, ");//19日
                        strbuf.append("     F20.RCXQ_SDJC AS GRRC_DAY20, ");//20日
                        strbuf.append("     F21.RCXQ_SDJC AS GRRC_DAY21, ");//21日
                        strbuf.append("     F22.RCXQ_SDJC AS GRRC_DAY22, ");//22日
                        strbuf.append("     F23.RCXQ_SDJC AS GRRC_DAY23, ");//23日
                        strbuf.append("     F24.RCXQ_SDJC AS GRRC_DAY24, ");//24日
                        strbuf.append("     F25.RCXQ_SDJC AS GRRC_DAY25, ");//25日
                        strbuf.append("     F26.RCXQ_SDJC AS GRRC_DAY26, ");//26日
                        strbuf.append("     F27.RCXQ_SDJC AS GRRC_DAY27, ");//27日
                        strbuf.append("     F28.RCXQ_SDJC AS GRRC_DAY28, ");//28日
                        strbuf.append("     F29.RCXQ_SDJC AS GRRC_DAY29, ");//29日
                        strbuf.append("     F30.RCXQ_SDJC AS GRRC_DAY30, ");//30日
                        strbuf.append("     F31.RCXQ_SDJC AS GRRC_DAY31  ");//31日
                        strbuf.append("  FROM BCXX A,KCFY D,KCXX C,RCXQ E,(SELECT * FROM GRRC WHERE GRRC_NY='"+newbetween[i]+"') B");
                        strbuf.append("      ,RCXQ F01,RCXQ F02,RCXQ F03,RCXQ F04,RCXQ F05,RCXQ F06,RCXQ F07 ");
                        strbuf.append("      ,RCXQ F08,RCXQ F09,RCXQ F10,RCXQ F11,RCXQ F12,RCXQ F13,RCXQ F14 ");
                        strbuf.append("      ,RCXQ F15,RCXQ F16,RCXQ F17,RCXQ F18,RCXQ F19,RCXQ F20,RCXQ F21 ");
                        strbuf.append("      ,RCXQ F22,RCXQ F23,RCXQ F24,RCXQ F25,RCXQ F26,RCXQ F27,RCXQ F28 ");
                        strbuf.append("      ,RCXQ F29,RCXQ F30,RCXQ F31");
                        strbuf.append(" WHERE A.BCXX_KCID = C.KCXX_KCID(+) ");
                        strbuf.append("   AND A.BCXX_BCID = B.GRRC_BCID(+)");
                        strbuf.append("   AND A.BCXX_KCFYID = D.KCFY_FYID(+)");
                        strbuf.append("   AND D.KCFY_RCID = E.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY01 = F01.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY02 = F02.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY03 = F03.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY04 = F04.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY05 = F05.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY06 = F06.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY07 = F07.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY08 = F08.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY09 = F09.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY10 = F10.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY11 = F11.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY12 = F12.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY13 = F13.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY14 = F14.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY15 = F15.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY16 = F16.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY17 = F17.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY18 = F18.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY19 = F19.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY20 = F20.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY21 = F21.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY22 = F22.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY23 = F23.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY24 = F24.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY25 = F25.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY26 = F26.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY27 = F27.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY28 = F28.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY29 = F29.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY30 = F30.RCXQ_RCID(+)");
                        strbuf.append("   AND B.GRRC_DAY31 = F31.RCXQ_RCID(+)");
                        if (MyStringUtils.isNotBlank(beanIn.getGRRC_BCID())) {
                            strbuf.append("  AND A.BCXX_BCID = '").append(beanIn.getGRRC_BCID())
                                .append("'");
                        }

                    }
                    strbuf.append("   ) A ORDER BY NY");
                }
                StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
                        sort);
                ResultSetHandler<List<Pojo1040160>> rs = new BeanListHandler<Pojo1040160>(
                        Pojo1040160.class);

                result = db.queryForBeanListHandler(execSql, rs);
            }

        } catch (Exception e) {
            MyLogger.error(this.getClass().getName(), e);
            throw e;
        } finally {
            db.closeConnection();
        }
        return result;
    }
    //查询年月
    public List<Pojo1040160> getNYList(String bcid) throws Exception {
        List<Pojo1040160> result = null;
        try {
            StringBuffer strbuf = new StringBuffer();
            strbuf.append(" SELECT ");
            strbuf.append("         GRRC_NY");
            strbuf.append(" FROM ");
            strbuf.append("         GRRC ");
            strbuf.append(" WHERE ");
            strbuf.append("         GRRC_BCID = '"+bcid+"' ");
            ResultSetHandler<List<Pojo1040160>> rs = new BeanListHandler<Pojo1040160>(
                    Pojo1040160.class);
            result = db.queryForBeanListHandler(strbuf, rs);
        } catch (Exception e) {
            MyLogger.error(this.getClass().getName(), e);
            throw e;
        } finally {
        }
        return result;
    }
    //查询班次日期跨度
    private Pojo_BCXX getBCRQ(String bcid) throws Exception {
            StringBuffer strbuf = new StringBuffer();
            Pojo_BCXX bcxx = new Pojo_BCXX();
            try {
                    strbuf.append(" SELECT ");
                    strbuf.append("     BCXX_KSSJ,");//开始时间
                    strbuf.append("     BCXX_JSSJ");//结束时间
                    strbuf.append(" FROM ");
                    strbuf.append("     BCXX ");
                    strbuf.append("   WHERE BCXX_BCID = '").append(bcid).append("'");

                    ResultSetHandler<Pojo_BCXX> rsh = new BeanHandler<Pojo_BCXX>(
                            Pojo_BCXX.class);

                    bcxx = db.queryForBeanHandler(strbuf, rsh);
            } catch (Exception e) {
                    MyLogger.error(this.getClass().getName(), e);
                    throw e;
            }
                return bcxx;
    }
    //查询两个日期中间的月份
    public String getBetweenDate(String btime,String etime){
        String[] arg1 = btime.split("-");
        String[] arg2 = etime.split("-");
        int year1 = Integer.valueOf(arg1[0]);
        int year2 = Integer.valueOf(arg2[0]);
        int month1 = Integer.valueOf(arg1[1]);
        int month2 = Integer.valueOf(arg2[1]);
        String month = "";
        for (int i = year1; i <= year2; i++) {
            int monthCount = 12;
            int monthStart = 1;
            if (i == year1) {
                monthStart = month1;
                if(year1==year2){
                    monthCount = month2-monthStart+1;
                }else{
                    monthCount = 12-monthStart+1;
                }

            } else if (i == year2) {
                monthCount = month2;
            }
            for(int j = 0; j < monthCount; j++){
                int temp = monthStart+j;
                if(temp >=10){
                    month += i+"-"+(monthStart+j)+",";
                }else{
                    month += i+"-0"+(monthStart+j)+",";
                }
            }
        }
        return month;
    }
    /**
     * @FunctionName: insertRCSD
     * @Description: 插入日程设定
     * @param beanIn
     * @return String UUID
     * @throws Exception
     * @return int
     * @author czl
     * @date 2014-12-19
     */
    public String insertRCSD(Pojo1040160 beanIn) throws Exception {
        String result = "";
        try {
            db.openConnection();
            String strUUId = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数
            StringBuffer strbuf = new StringBuffer();
            strbuf.append(" INSERT INTO ");
            strbuf.append("     GRRC ");
            strbuf.append(" ( ");
            strbuf.append("     GRRC_GRRCID , ");//班次日程ID
            strbuf.append("     GRRC_BCID   , ");//班次ID
            strbuf.append("     GRRC_NY , ");//年月
            strbuf.append("     GRRC_CJR    , ");//创建人
            strbuf.append("     GRRC_CJSJ   , ");//创建时间
            strbuf.append("     GRRC_GXR    , ");//更新人
            strbuf.append("     GRRC_GXSJ   ");//更新时间
            strbuf.append(" ) ");
            strbuf.append(" VALUES ");
            strbuf.append(" ( ");
            strbuf.append("     '"+strUUId+"', ");
            strbuf.append("     '"+beanIn.getGRRC_BCID()+"', ");
            strbuf.append("     '"+beanIn.getGRRC_NY()+"', ");
            strbuf.append("     '"+beanIn.getGRRC_CJR()+"', ");//创建人
            strbuf.append("     TO_CHAR(SYSDATE,'YYYYMMDD HH24:MI:SS'), ");//创建时间
            strbuf.append("     '"+beanIn.getGRRC_GXR()+"', ");//更新人
            strbuf.append("     TO_CHAR(SYSDATE,'YYYYMMDD HH24:MI:SS') ");//更新时间
            strbuf.append(" ) ");

            int i = db.executeSQL(strbuf);
            if(i>0){
                result = strUUId;
            }

        } catch (Exception e) {
            MyLogger.error(this.getClass().getName(), e);
            throw e;
        } finally {
            db.closeConnection();
        }
        return result;
    }

    /**
     * @FunctionName: updateRCSD
     * @Description: 修改日程设定
     * @param beanIn
     * @return
     * @throws Exception
     * @return int
     * @author czl
     * @date 2014-12-19
     */
    public int updateRCSD(Pojo1040160 beanIn) throws Exception {
        int result = 0;

        try {
            db.openConnection();
            StringBuffer strbuf = new StringBuffer();
            strbuf.append(" UPDATE ");
            strbuf.append("        GRRC ");
            strbuf.append(" SET ");
            if(beanIn.getSFYK().equals("1")){
                strbuf.append("        GRRC_DAY").append(beanIn.getGZR()).append("='").append(beanIn.getSDJC()).append("', ");//时段简称
            }else if(beanIn.getSFYK().equals("2")){
                strbuf.append("        GRRC_DAY").append(beanIn.getGZR()).append("='', ");//时段简称
            }

            strbuf.append("        GRRC_GXR='").append(beanIn.getGRRC_GXR()).append("', ");//修改人
            strbuf.append("        GRRC_GXSJ=TO_CHAR(SYSDATE,'YYYY-MM-DD HH24:MI:SS') ");//修改时间
            strbuf.append(" WHERE ");
            strbuf.append("        GRRC_GRRCID = '").append(beanIn.getGRRC_GRRCID()).append("' ");//班次日程ID
            result = db.executeSQL(strbuf);
        } catch (Exception e) {
            MyLogger.error(this.getClass().getName(), e);
            throw e;
        } finally {
            db.closeConnection();
        }
        return result;
    }

    /**
     * @FunctionName: updateMuitRCSD
     * @Description: 修改整月日程设定
     * @param beanIn
     * @return
     * @throws Exception
     * @return int
     * @author czl
     * @date 2014-12-19
     */
    public int updateMuitRCSD(Pojo1040160 beanIn,Object[] dataDay) throws Exception {
        int result = 0;

        try {
            db.openConnection();

            StringBuffer strbuf = new StringBuffer();
            strbuf.append(" UPDATE ");
            strbuf.append("        GRRC ");
            strbuf.append(" SET ");
            for(int i=1;i<=31;i++){
                if(MyStringUtils.isNotBlank(dataDay[i].toString().replace("null", ""))){
                    String strDay = MyStringUtils.leftPad(i+"", 2, "0");//补0
                    strbuf.append(" GRRC_DAY"+strDay).append("='").append(dataDay[i].toString()).append("', ");//工作类型
                }else{
                    String strDay = MyStringUtils.leftPad(i+"", 2, "0");//补0
                    strbuf.append(" GRRC_DAY"+strDay).append("='', ");//工作类型
                }
            }
            strbuf.append(" GRRC_GXR='").append(beanIn.getGRRC_GXR()).append("', ");//修改人
            strbuf.append(" GRRC_GXSJ=TO_CHAR(SYSDATE,'YYYY-MM-DD HH24:MI:SS') ");//修改时间
            strbuf.append(" WHERE ");
            strbuf.append("        GRRC_GRRCID = '").append(beanIn.getGRRC_GRRCID()).append("' ");//班次日程ID
            result = db.executeSQL(strbuf);
        } catch (Exception e) {
            MyLogger.error(this.getClass().getName(), e);
            throw e;
        } finally {
            db.closeConnection();
        }
        return result;
    }

    public Pojo1020110 getRCSDListByID(Pojo1020110 beanIn) throws Exception {
        Pojo1020110 result = null;

        try {
            db.openConnection();
            StringBuffer strbuf = new StringBuffer();
            strbuf.append(" SELECT ");
            strbuf.append("     A.GRRC_GRRCID, ");//日程ID
            strbuf.append("     A.GRRC_DAY01, ");//01日
            strbuf.append("     A.GRRC_DAY02, ");//02日
            strbuf.append("     A.GRRC_DAY03, ");//03日
            strbuf.append("     A.GRRC_DAY04, ");//04日
            strbuf.append("     A.GRRC_DAY05, ");//05日
            strbuf.append("     A.GRRC_DAY06, ");//06日
            strbuf.append("     A.GRRC_DAY07, ");//07日
            strbuf.append("     A.GRRC_DAY08, ");//08日
            strbuf.append("     A.GRRC_DAY09, ");//09日
            strbuf.append("     A.GRRC_DAY10, ");//10日
            strbuf.append("     A.GRRC_DAY11, ");//11日
            strbuf.append("     A.GRRC_DAY12, ");//12日
            strbuf.append("     A.GRRC_DAY13, ");//13日
            strbuf.append("     A.GRRC_DAY14, ");//14日
            strbuf.append("     A.GRRC_DAY15, ");//15日
            strbuf.append("     A.GRRC_DAY16, ");//16日
            strbuf.append("     A.GRRC_DAY17, ");//17日
            strbuf.append("     A.GRRC_DAY18, ");//18日
            strbuf.append("     A.GRRC_DAY19, ");//19日
            strbuf.append("     A.GRRC_DAY20, ");//20日
            strbuf.append("     A.GRRC_DAY21, ");//21日
            strbuf.append("     A.GRRC_DAY22, ");//22日
            strbuf.append("     A.GRRC_DAY23, ");//23日
            strbuf.append("     A.GRRC_DAY24, ");//24日
            strbuf.append("     A.GRRC_DAY25, ");//25日
            strbuf.append("     A.GRRC_DAY26, ");//26日
            strbuf.append("     A.GRRC_DAY27, ");//27日
            strbuf.append("     A.GRRC_DAY28, ");//28日
            strbuf.append("     A.GRRC_DAY29, ");//29日
            strbuf.append("     A.GRRC_DAY30, ");//30日
            strbuf.append("     A.GRRC_DAY31,  ");//31日
            strbuf.append("     F01.RCXQ_RCXQ AS SDJC01, ");//01日
            strbuf.append("     F02.RCXQ_RCXQ AS SDJC02, ");//02日
            strbuf.append("     F03.RCXQ_RCXQ AS SDJC03, ");//03日
            strbuf.append("     F04.RCXQ_RCXQ AS SDJC04, ");//04日
            strbuf.append("     F05.RCXQ_RCXQ AS SDJC05, ");//05日
            strbuf.append("     F06.RCXQ_RCXQ AS SDJC06, ");//06日
            strbuf.append("     F07.RCXQ_RCXQ AS SDJC07, ");//07日
            strbuf.append("     F08.RCXQ_RCXQ AS SDJC08, ");//08日
            strbuf.append("     F09.RCXQ_RCXQ AS SDJC09, ");//09日
            strbuf.append("     F10.RCXQ_RCXQ AS SDJC10, ");//10日
            strbuf.append("     F11.RCXQ_RCXQ AS SDJC11, ");//11日
            strbuf.append("     F12.RCXQ_RCXQ AS SDJC12, ");//12日
            strbuf.append("     F13.RCXQ_RCXQ AS SDJC13, ");//13日
            strbuf.append("     F14.RCXQ_RCXQ AS SDJC14, ");//14日
            strbuf.append("     F15.RCXQ_RCXQ AS SDJC15, ");//15日
            strbuf.append("     F16.RCXQ_RCXQ AS SDJC16, ");//16日
            strbuf.append("     F17.RCXQ_RCXQ AS SDJC17, ");//17日
            strbuf.append("     F18.RCXQ_RCXQ AS SDJC18, ");//18日
            strbuf.append("     F19.RCXQ_RCXQ AS SDJC19, ");//19日
            strbuf.append("     F20.RCXQ_RCXQ AS SDJC20, ");//20日
            strbuf.append("     F21.RCXQ_RCXQ AS SDJC21, ");//21日
            strbuf.append("     F22.RCXQ_RCXQ AS SDJC22, ");//22日
            strbuf.append("     F23.RCXQ_RCXQ AS SDJC23, ");//23日
            strbuf.append("     F24.RCXQ_RCXQ AS SDJC24, ");//24日
            strbuf.append("     F25.RCXQ_RCXQ AS SDJC25, ");//25日
            strbuf.append("     F26.RCXQ_RCXQ AS SDJC26, ");//26日
            strbuf.append("     F27.RCXQ_RCXQ AS SDJC27, ");//27日
            strbuf.append("     F28.RCXQ_RCXQ AS SDJC28, ");//28日
            strbuf.append("     F29.RCXQ_RCXQ AS SDJC29, ");//29日
            strbuf.append("     F30.RCXQ_RCXQ AS SDJC30, ");//30日
            strbuf.append("     F31.RCXQ_RCXQ AS SDJC31  ");//31日

            strbuf.append("  FROM GRRC A ");
            strbuf.append("      ,RCXQ F01,RCXQ F02,RCXQ F03,RCXQ F04,RCXQ F05,RCXQ F06,RCXQ F07 ");
            strbuf.append("      ,RCXQ F08,RCXQ F09,RCXQ F10,RCXQ F11,RCXQ F12,RCXQ F13,RCXQ F14 ");
            strbuf.append("      ,RCXQ F15,RCXQ F16,RCXQ F17,RCXQ F18,RCXQ F19,RCXQ F20,RCXQ F21 ");
            strbuf.append("      ,RCXQ F22,RCXQ F23,RCXQ F24,RCXQ F25,RCXQ F26,RCXQ F27,RCXQ F28 ");
            strbuf.append("      ,RCXQ F29,RCXQ F30,RCXQ F31");
            strbuf.append(" WHERE 1=1 ");

            strbuf.append("   AND A.GRRC_DAY01  =F01.RCXQ_RCID(+)");
            strbuf.append("   AND A.GRRC_DAY02  =F02.RCXQ_RCID(+)");
            strbuf.append("   AND A.GRRC_DAY03  =F03.RCXQ_RCID(+)");
            strbuf.append("   AND A.GRRC_DAY04  =F04.RCXQ_RCID(+)");
            strbuf.append("   AND A.GRRC_DAY05  =F05.RCXQ_RCID(+)");
            strbuf.append("   AND A.GRRC_DAY06  =F06.RCXQ_RCID(+)");
            strbuf.append("   AND A.GRRC_DAY07  =F07.RCXQ_RCID(+)");
            strbuf.append("   AND A.GRRC_DAY08  =F08.RCXQ_RCID(+)");
            strbuf.append("   AND A.GRRC_DAY09  =F09.RCXQ_RCID(+)");
            strbuf.append("   AND A.GRRC_DAY10  =F10.RCXQ_RCID(+)");
            strbuf.append("   AND A.GRRC_DAY11  =F11.RCXQ_RCID(+)");
            strbuf.append("   AND A.GRRC_DAY12  =F12.RCXQ_RCID(+)");
            strbuf.append("   AND A.GRRC_DAY13  =F13.RCXQ_RCID(+)");
            strbuf.append("   AND A.GRRC_DAY14  =F14.RCXQ_RCID(+)");
            strbuf.append("   AND A.GRRC_DAY15  =F15.RCXQ_RCID(+)");
            strbuf.append("   AND A.GRRC_DAY16  =F16.RCXQ_RCID(+)");
            strbuf.append("   AND A.GRRC_DAY17  =F17.RCXQ_RCID(+)");
            strbuf.append("   AND A.GRRC_DAY18  =F18.RCXQ_RCID(+)");
            strbuf.append("   AND A.GRRC_DAY19  =F19.RCXQ_RCID(+)");
            strbuf.append("   AND A.GRRC_DAY20  =F20.RCXQ_RCID(+)");
            strbuf.append("   AND A.GRRC_DAY21  =F21.RCXQ_RCID(+)");
            strbuf.append("   AND A.GRRC_DAY22  =F22.RCXQ_RCID(+)");
            strbuf.append("   AND A.GRRC_DAY23  =F23.RCXQ_RCID(+)");
            strbuf.append("   AND A.GRRC_DAY24  =F24.RCXQ_RCID(+)");
            strbuf.append("   AND A.GRRC_DAY25  =F25.RCXQ_RCID(+)");
            strbuf.append("   AND A.GRRC_DAY26  =F26.RCXQ_RCID(+)");
            strbuf.append("   AND A.GRRC_DAY27  =F27.RCXQ_RCID(+)");
            strbuf.append("   AND A.GRRC_DAY28  =F28.RCXQ_RCID(+)");
            strbuf.append("   AND A.GRRC_DAY29  =F29.RCXQ_RCID(+)");
            strbuf.append("   AND A.GRRC_DAY30  =F30.RCXQ_RCID(+)");
            strbuf.append("   AND A.GRRC_DAY31  =F31.RCXQ_RCID(+)");

            strbuf.append("   AND A.GRRC_GRRCID  = '").append(beanIn.getGRRC_GRRCID()).append("'");
            ResultSetHandler<Pojo1020110> rs = new BeanHandler<Pojo1020110>(
                    Pojo1020110.class);
            result = db.queryForBeanHandler(strbuf, rs);
        } catch (Exception e) {
            MyLogger.error(this.getClass().getName(), e);
            throw e;
        } finally {
            db.closeConnection();
        }
        return result;
    }
}
