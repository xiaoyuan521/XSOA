package com.xsoa.service.service1100000;

import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.xsoa.pojo.basetable.Pojo_BMXX;
import com.xsoa.pojo.custom.pojo_1040000.Pojo1040150;
import com.xsoa.pojo.custom.pojo_1100000.Pojo1100110;

public class Service1100110 extends BaseService {
	private DBManager db = null;

	public Service1100110() {
		db = new DBManager();
	}

	/**
    *
    * @FunctionName: getDataList
    * @Description: 获取论坛版块信息
    * @param beanIn
    * @return
    * @throws Exception
    * @return List<Pojo1100110>
    * @author czl
    * @date 2016-9-21
    */
   public List<Pojo1100110> getDataList() throws Exception {
       List<Pojo1100110> dataList = null;

       try {
           db.openConnection();

           StringBuffer strbuf = new StringBuffer();
           strbuf.append(" SELECT ");
           strbuf.append("     L.LTBK_BKID, ");//版块ID
           strbuf.append("     L.LTBK_BKMC, ");//版块名称
           strbuf.append("     L.LTBK_BKSM, ");//版块说明
           strbuf.append("     L.LTBK_TPLJ, ");//图片路径
           strbuf.append("     L.LTBK_KJID, ");//可见部门ID
           strbuf.append("     Y.YHXX_YHMC AS BZMC ");//版主名称
           strbuf.append(" FROM ");
           strbuf.append("     LTBK L");
           strbuf.append(" LEFT JOIN ");
           strbuf.append("     YHXX Y");
           strbuf.append(" ON ");
           strbuf.append(" Y.YHXX_YHID = L.LTBK_BZID");
           strbuf.append(" WHERE 1=1 ");
           strbuf.append(this.searchSql());
           strbuf.append(" ORDER BY ");
           strbuf.append("     L.LTBK_CJSJ DESC ");
           ResultSetHandler<List<Pojo1100110>> rs = new BeanListHandler<Pojo1100110>(Pojo1100110.class);
           dataList = db.queryForBeanListHandler(strbuf, rs);
       } catch (Exception e) {
           MyLogger.error(this.getClass().getName(), e);
           throw e;
       } finally {
           db.closeConnection();
       }
       return dataList;
   }

	/**
	 *
	 * @FunctionName: searchSql
	 * @Description: 查询条件部分
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return String
	 * @author czl
	 * @date 2015-9-21
	 */
	private String searchSql() throws Exception {
		StringBuffer strbuf = new StringBuffer();

		try {
			/* 公共方法 */
			strbuf.append(" AND L.LTBK_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		}
		return strbuf.toString();
	}

	/**
    *
    * @FunctionName: getBmmcByBmids
    * @Description: 获取部门名称
    * @param beanIn
    * @throws Exception
    * @return String
    * @author czl
    * @date 2016-9-27
    */
   public String getBmmcByBmids(String bmids) throws Exception {
       String result;
       try {
           db.openConnection();

           StringBuffer strbuf = new StringBuffer();
           strbuf.append(" SELECT ");
           strbuf.append("     group_concat(BMXX_BMMC) ");//部门名称
           strbuf.append(" FROM ");
           strbuf.append("     BMXX");
           strbuf.append(" WHERE ");
           strbuf.append("     BMXX_BMID IN (");
           strbuf.append("     "+bmids+")");//
           result = db.queryForString(strbuf);
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
    * @FunctionName: getBmxxList
    * @Description: 获取部门信息
    * @throws Exception
    * @return List<Pojo_BMXX>
    * @author czl
    * @date 2016-9-26
    */
   public List<Pojo_BMXX> getBmxxList() throws Exception {
       List<Pojo_BMXX> dataList = null;

       try {
           db.openConnection();

           StringBuffer strbuf = new StringBuffer();
           strbuf.append(" SELECT ");
           strbuf.append("     B.BMXX_BMID, ");//部门ID
           strbuf.append("     B.BMXX_BMMC ");//部门名称
           strbuf.append(" FROM ");
           strbuf.append("     BMXX B");
           strbuf.append(" WHERE B.BMXX_SCBZ = 0 ");

           ResultSetHandler<List<Pojo_BMXX>> rs = new BeanListHandler<Pojo_BMXX>(Pojo_BMXX.class);
           dataList = db.queryForBeanListHandler(strbuf, rs);
       } catch (Exception e) {
           MyLogger.error(this.getClass().getName(), e);
           throw e;
       } finally {
           db.closeConnection();
       }
       return dataList;
   }

	/**
	 *
	 * @FunctionName: getDetailData
	 * @Description: 获取游戏信息
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return Pojo1040150
	 * @author czl
	 * @date 2016-9-19
	 */
	public Pojo1040150 getDetailData(Pojo1040150 beanIn) throws Exception {
		Pojo1040150 result = null;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     Y.YXXX_YXID, ");//游戏ID
			strbuf.append("     Y.YXXX_YHID, ");//用户ID
			strbuf.append("     Y.YXXX_LRID, ");//狼人ID
			strbuf.append("     Y.YXXX_YXRQ, ");//游戏日期
			strbuf.append("     Y.YXXX_SYZT, ");//输赢状态
			strbuf.append("     Y.YXXX_BZXX, ");//备注
			strbuf.append("     A.YHXX_YHMC AS YXXX_WJMC, ");//玩家名称
			strbuf.append("     B.BMXX_BMMC AS BMMC ");//部门名称
			strbuf.append(" FROM ");
			strbuf.append("     YXXX Y LEFT JOIN YHXX A ON Y.YXXX_YHID = A.YHXX_YHID ");
			strbuf.append("     LEFT JOIN BMXX B ON A.YHXX_BMID = B.BMXX_BMID ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND Y.YXXX_YXID = '").append(beanIn.getYXXX_YXID()).append("'");

			ResultSetHandler<Pojo1040150> rs = new BeanHandler<Pojo1040150>(
					Pojo1040150.class);

			result = db.queryForBeanHandler(strbuf, rs);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return result;
	}
	/**
     * @FunctionName: deleteYXXX
     * @Description: 删除游戏信息
     * @param beanIn
     * @return
     * @throws Exception
     * @return boolean
     * @author czl
     * @date 2015-9-12
     */
    public boolean deleteYXXX(Pojo1040150 beanIn) throws Exception {
        boolean result =false;
        int count_YXXX = 0;
        int count_JSXX = 0;
        int count_SLXX = 0;
        try {
            db.openConnection();
            db.beginTran();
            StringBuffer strbuf_YXXX = new StringBuffer();
            strbuf_YXXX.append(" DELETE FROM ");
            strbuf_YXXX.append("     YXXX ");
            strbuf_YXXX.append(" WHERE ");
            strbuf_YXXX.append("     YXXX_YXID='").append(beanIn.getYXXX_YXID()).append("' ");
            count_YXXX = db.executeSQL(strbuf_YXXX);

            StringBuffer strbuf_JSXX = new StringBuffer();
            strbuf_JSXX.append(" UPDATE ");
            strbuf_JSXX.append("     JSXX ");
            strbuf_JSXX.append(" SET ");
            switch (beanIn.getYXXX_LRID()) {
                case "01" :
                    strbuf_JSXX.append("     JSXX_LRJS=JSXX_LRJS-1,");//
                    if ("0".equals(beanIn.getYXXX_SYZT())) {
                        strbuf_JSXX.append("     JSXX_LRSL=JSXX_LRSL-1,");//
                    }
                    break;
                case "02" :
                    strbuf_JSXX.append("     JSXX_LQJS=JSXX_LQJS-1,");//
                    if ("0".equals(beanIn.getYXXX_SYZT())) {
                        strbuf_JSXX.append("     JSXX_LQSL=JSXX_LQSL-1,");//
                    }
                    break;
                case "03" :
                    strbuf_JSXX.append("     JSXX_NWJS=JSXX_NWJS-1,");//
                    if ("0".equals(beanIn.getYXXX_SYZT())) {
                        strbuf_JSXX.append("     JSXX_NWSL=JSXX_NWSL-1,");//
                    }
                    break;
                case "04" :
                    strbuf_JSXX.append("     JSXX_XZJS=JSXX_XZJS-1,");//
                    if ("0".equals(beanIn.getYXXX_SYZT())) {
                        strbuf_JSXX.append("     JSXX_XZSL=JSXX_XZSL-1,");//
                    }
                    break;
                case "05" :
                    strbuf_JSXX.append("     JSXX_PMJS=JSXX_PMJS-1,");//
                    if ("0".equals(beanIn.getYXXX_SYZT())) {
                        strbuf_JSXX.append("     JSXX_PMSL=JSXX_PMSL-1,");//
                    }
                    break;
            }
            strbuf_JSXX.append("     JSXX_GXR='").append(beanIn.getYXXX_GXR()).append("',");//更新人
            strbuf_JSXX.append("     JSXX_GXSJ=DATE_FORMAT(now(),'%Y%m%d %H:%i:%s')");//更新时间
            strbuf_JSXX.append(" WHERE 1=1");
            strbuf_JSXX.append(" AND JSXX_YHID='").append(beanIn.getYXXX_YHID()).append("'");//玩家ID
            count_JSXX = db.executeSQL(strbuf_JSXX);

            StringBuffer strbuf_SLXX = new StringBuffer();
            String ztsl = getZTSL(beanIn.getYXXX_YHID());
            strbuf_SLXX.append(" UPDATE ");
            strbuf_SLXX.append("     SLXX ");
            strbuf_SLXX.append(" SET ");
            strbuf_SLXX.append("     SLXX_SCSL=SLXX_ZTSL,");
            strbuf_SLXX.append("     SLXX_ZTSL='").append(ztsl).append("',");
            strbuf_SLXX.append("     SLXX_GXR='").append(beanIn.getYXXX_GXR()).append("',");//更新人
            strbuf_SLXX.append("     SLXX_GXSJ=DATE_FORMAT(now(),'%Y%m%d %H:%i:%s')");//更新时间
            strbuf_SLXX.append(" WHERE 1=1");
            strbuf_SLXX.append(" AND SLXX_YHID='").append(beanIn.getYXXX_YHID()).append("'");
            count_SLXX = db.executeSQL(strbuf_SLXX);
            if (count_YXXX > 0 && count_JSXX > 0 && count_SLXX > 0) {
                db.commit();
                result = true;
            } else {
                db.rollback();
            }
        } catch (Exception e) {
            db.rollback();
            result = false;
            MyLogger.error(this.getClass().getName(), e);
            throw e;
        } finally {
            db.closeConnection();
        }
        return result;
    }
	/**
	 *
	 * @FunctionName: insertData
	 * @Description: 新增论坛版块
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return String
	 * @author czl
	 * @date 2016-9-26
	 */
    public boolean insertData(Pojo1100110 beanIn) throws Exception {
        boolean result = false;
        int count = 0;
        String bkId = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数，用于插入版块ID
        try {
            db.openConnection();
            db.beginTran();
            StringBuffer strbuf_LTBK = new StringBuffer();
            strbuf_LTBK.append(" INSERT INTO ");
            strbuf_LTBK.append("     LTBK ");
            strbuf_LTBK.append(" ( ");
            strbuf_LTBK.append("     LTBK_BKID, ");//版块ID
            strbuf_LTBK.append("     LTBK_BKMC, ");//版块名称
            strbuf_LTBK.append("     LTBK_BKSM, ");//版块说明
            strbuf_LTBK.append("     LTBK_TPLJ, ");//图片路径
            strbuf_LTBK.append("     LTBK_KJID, ");//可见部门ID
            strbuf_LTBK.append("     LTBK_BZID, ");//版主
            strbuf_LTBK.append("     LTBK_CJR, ");//创建人
            strbuf_LTBK.append("     LTBK_CJSJ, ");//创建时间
            strbuf_LTBK.append("     LTBK_GXR, ");//更新人
            strbuf_LTBK.append("     LTBK_GXSJ, ");//更新时间
            strbuf_LTBK.append("     LTBK_SCBZ  ");//删除标志
            strbuf_LTBK.append(" ) ");
            strbuf_LTBK.append(" VALUES ");
            strbuf_LTBK.append(" ( ");
            strbuf_LTBK.append("     '"+bkId+"', ");
            strbuf_LTBK.append("     '"+beanIn.getLTBK_BKMC()+"', ");
            strbuf_LTBK.append("     '"+beanIn.getLTBK_BKSM()+"', ");
            strbuf_LTBK.append("     '"+beanIn.getLTBK_TPLJ()+"', ");
            strbuf_LTBK.append("     '"+beanIn.getLTBK_KJID().substring(0,beanIn.getLTBK_KJID().length()-1)+"', ");
            strbuf_LTBK.append("     '"+beanIn.getLTBK_BZID()+"', ");
            strbuf_LTBK.append("     '"+beanIn.getLTBK_CJR()+"', ");
            strbuf_LTBK.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");
            strbuf_LTBK.append("     '"+beanIn.getLTBK_GXR()+"', ");
            strbuf_LTBK.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'),  ");
            strbuf_LTBK.append("     '0'  ");
            strbuf_LTBK.append(" ) ");
            count = db.executeSQL(strbuf_LTBK);
            if (count > 0) {
                db.commit();
                result = true;
            } else {
                db.rollback();
            }
        } catch (Exception e) {
            db.rollback();
            result = false;
            MyLogger.error(this.getClass().getName(), e);
            throw e;
        } finally {
            db.closeConnection();
        }
        return result;
    }
    private String getZTSL(String yhid) throws Exception {
        String result;
        try {
            StringBuffer strbuf = new StringBuffer();
            strbuf.append(" SELECT ");
            strbuf.append("     cast(cast(avg(");
            strbuf.append("     (J.JSXX_LRSL+J.JSXX_NWSL+J.JSXX_XZSL+J.JSXX_LQSL+J.JSXX_PMSL)");
            strbuf.append("     /");
            strbuf.append("     (J.JSXX_LRJS+J.JSXX_NWJS+J.JSXX_XZJS+J.JSXX_LQJS+J.JSXX_PMJS)");
            strbuf.append("     *100)as decimal(10,1)) AS CHAR) ");
            strbuf.append("     AS ZTSL ");
            strbuf.append(" FROM ");
            strbuf.append("     JSXX J ");
            strbuf.append(" WHERE 1=1");
            strbuf.append(" AND J.JSXX_SCBZ = '0'");
            strbuf.append(" AND J.JSXX_YHID = '").append(yhid).append("'");

            result = db.queryForString(strbuf);
        } catch (Exception e) {
            MyLogger.error(this.getClass().getName(), e);
            throw e;
        } finally {

        }
        return result;
    }
    /**
    *
    * @FunctionName: getJsxxCount
    * @Description: 获取局数信息记录个数
    * @param beanIn
    * @return
    * @throws Exception
    * @return int
    * @author czl
    * @date 2016-9-13
    */
   public int getJsxxCount(String yhid) throws Exception {
       int result = 0;

       try {
           StringBuffer strbuf = new StringBuffer();
           strbuf.append(" SELECT ");
           strbuf.append("     COUNT(J.JSXX_JSID) ");
           strbuf.append(" FROM ");
           strbuf.append("     JSXX J ");
           strbuf.append(" WHERE 1=1");
           strbuf.append(" AND J.JSXX_SCBZ = '0'");
           strbuf.append(" AND J.JSXX_YHID = '").append(yhid).append("'");

           result = db.queryForInteger(strbuf);
       } catch (Exception e) {
           MyLogger.error(this.getClass().getName(), e);
           throw e;
       } finally {

       }
       return result;
   }
    /**
     *
     * @FunctionName: getYhxxCount
     * @Description: 获取胜率信息记录个数
     * @param beanIn
     * @return
     * @throws Exception
     * @return int
     * @author czl
     * @date 2016-9-12
     */
    public int getSlxxCount(String yhid) throws Exception {
        int result = 0;

        try {
            StringBuffer strbuf = new StringBuffer();
            strbuf.append(" SELECT ");
            strbuf.append("     COUNT(S.SLXX_SLID) ");
            strbuf.append(" FROM ");
            strbuf.append("     SLXX S ");
            strbuf.append(" WHERE 1=1");
            strbuf.append(" AND S.SLXX_SCBZ = '0'");
            strbuf.append(" AND S.SLXX_YHID = '").append(yhid).append("'");

            result = db.queryForInteger(strbuf);
        } catch (Exception e) {
            MyLogger.error(this.getClass().getName(), e);
            throw e;
        } finally {

        }
        return result;
    }

}