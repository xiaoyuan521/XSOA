package com.xsoa.service.service1100000;

import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.xsoa.pojo.basetable.Pojo_BKZT;
import com.xsoa.pojo.basetable.Pojo_LTDJ;
import com.xsoa.pojo.basetable.Pojo_LTJY;
import com.xsoa.pojo.custom.pojo_1100000.Pojo1100120;
import com.xsoa.pojo.custom.pojo_1100000.Pojo1100121;
import com.xsoa.pojo.custom.pojo_1100000.Pojo1100122;

public class Service1100120 extends BaseService {
	private DBManager db = null;

	public Service1100120() {
		db = new DBManager();
	}

	/**
    *
    * @FunctionName: getDataList
    * @Description: 获取论坛版块信息
    * @param beanIn
    * @return
    * @throws Exception
    * @return List<Pojo1100120>
    * @author czl
    * @date 2016-9-28
    */
   public List<Pojo1100120> getDataList(String bmid) throws Exception {
       List<Pojo1100120> dataList = null;

       try {
           db.openConnection();

           StringBuffer strbuf = new StringBuffer();
           strbuf.append(" SELECT ");
           strbuf.append("     L.LTBK_BKID, ");//版块ID
           strbuf.append("     L.LTBK_BKMC, ");//版块名称
           strbuf.append("     L.LTBK_TPLJ, ");//图片路径
           strbuf.append("     L.LTBK_BZID, ");//版主ID
           strbuf.append("     Y.YHXX_YHMC AS BZMC ");//版主名称
           strbuf.append(" FROM ");
           strbuf.append("     LTBK L");
           strbuf.append(" LEFT JOIN ");
           strbuf.append("     YHXX Y");
           strbuf.append(" ON ");
           strbuf.append(" Y.YHXX_YHID = L.LTBK_BZID");
           strbuf.append(" WHERE 1=1 ");
           strbuf.append(this.searchSql(bmid));
           strbuf.append(" ORDER BY ");
           strbuf.append("     L.LTBK_CJSJ DESC ");
           ResultSetHandler<List<Pojo1100120>> rs = new BeanListHandler<Pojo1100120>(Pojo1100120.class);
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
	private String searchSql(String bmid) throws Exception {
		StringBuffer strbuf = new StringBuffer();

		try {
		    /* 公共方法 */
		    strbuf.append(" AND find_in_set('"+bmid+"', L.LTBK_KJID)");

			strbuf.append(" AND L.LTBK_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		}
		return strbuf.toString();
	}

	/**
     *
     * @FunctionName: getCountZt
     * @Description: 获取主题数
     * @param beanIn
     * @throws Exception
     * @return int
     * @author czl
     * @date 2016-9-28
     */
    public int getCountZt(String bkid) throws Exception {
        int result = 0;

        try {
            db.openConnection();

            StringBuffer strbuf = new StringBuffer();
            strbuf.append(" SELECT ");
            strbuf.append("     COUNT(*) ");//用户信息个数
            strbuf.append(" FROM ");
            strbuf.append("     BKZT ");
            strbuf.append(" WHERE 1=1");
            strbuf.append(" AND BKZT_SCBZ = '0'");
            strbuf.append(" AND BKZT_BKID = '").append(bkid).append("'");

            result = db.queryForInteger(strbuf);
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
     * @FunctionName: getZtInfo
     * @Description: 获取主题详情
     * @param bkid
     * @throws Exception
     * @return Pojo_BKZT
     * @author czl
     * @date 2016-9-28
     */
    public Pojo_BKZT getZtInfo(String bkid) throws Exception {
        Pojo_BKZT baseBean = new Pojo_BKZT();

        try {
            db.openConnection();

            StringBuffer strbuf = new StringBuffer();
            strbuf.append(" SELECT ");
            strbuf.append("     B.BKZT_ZTID, ");//主题ID
            strbuf.append("     B.BKZT_ZTMC, ");//主题名称
            strbuf.append("     DATE_FORMAT(STR_TO_DATE(B.BKZT_FBSJ,'%Y%m%d %H:%i'), '%Y-%m-%d %H:%i') AS BKZT_FBSJ, ");//发表时间
            strbuf.append("     Y.YHXX_YHMC AS BKZT_FBR ");//发表人
            strbuf.append(" FROM ");
            strbuf.append("     BKZT B");
            strbuf.append(" LEFT JOIN ");
            strbuf.append("     YHXX Y");
            strbuf.append(" ON ");
            strbuf.append("     B.BKZT_FBR = Y.YHXX_YHID ");
            strbuf.append(" WHERE 1=1 ");
            strbuf.append(" AND B.BKZT_SCBZ = '0'");
            strbuf.append(" AND B.BKZT_BKID = '").append(bkid).append("'");
            strbuf.append(" ORDER BY ");
            strbuf.append("     B.BKZT_FBSJ DESC LIMIT 1 ");
            strbuf.append("");

            ResultSetHandler<Pojo_BKZT> rs = new BeanHandler<Pojo_BKZT>(
                    Pojo_BKZT.class);

            baseBean = db.queryForBeanHandler(strbuf, rs);
        } catch (Exception e) {
            MyLogger.error(this.getClass().getName(), e);
            throw e;
        } finally {
            db.closeConnection();
        }
        return baseBean;
    }

    /**
    *
    * @FunctionName: getZtDataList
    * @Description: 获取版块主题信息
    * @param beanIn
    * @return
    * @throws Exception
    * @return List<Pojo1100121>
    * @author czl
    * @date 2016-9-29
    */
   public List<Pojo1100121> getZtDataList(String yhid, String bkid) throws Exception {
       List<Pojo1100121> dataList = null;

       try {
           db.openConnection();

           StringBuffer strbuf = new StringBuffer();
           strbuf.append(" SELECT ");
           strbuf.append("     B.BKZT_ZTID, ");//主题ID
           strbuf.append("     B.BKZT_ZTMC, ");//主题名称
           strbuf.append("     B.BKZT_ZTLB, ");//主题类别
           strbuf.append("     Y.YHXX_YHMC AS ZZMC, ");//发表人
           strbuf.append("     Y.YHXX_GRZP AS GRZP, ");//个人头像
           strbuf.append("     COUNT(Z.ZTHF_HFID) AS HFGS ");//回复个数
           strbuf.append(" FROM ");
           strbuf.append("     BKZT B");
           strbuf.append(" LEFT JOIN ");
           strbuf.append("     YHXX Y");
           strbuf.append(" ON ");
           strbuf.append(" Y.YHXX_YHID = B.BKZT_FBR");
           strbuf.append(" LEFT JOIN ");
           strbuf.append("     ZTHF Z");
           strbuf.append(" ON ");
           strbuf.append(" B.BKZT_ZTID = Z.ZTHF_ZTID");
           strbuf.append(" WHERE 1=1 ");
           if (!"nouser".equals(yhid)) {
               strbuf.append(" AND B.BKZT_FBR = '").append(yhid).append("'");
           } else {
               strbuf.append(" AND B.BKZT_BKID = '").append(bkid).append("'");
           }
           strbuf.append(" GROUP BY ");
           strbuf.append("     B.BKZT_ZTID ");
           strbuf.append(" ORDER BY ");
           strbuf.append("     B.BKZT_ZTLB DESC, B.BKZT_FBSJ DESC");
           ResultSetHandler<List<Pojo1100121>> rs = new BeanListHandler<Pojo1100121>(Pojo1100121.class);
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
   * @FunctionName: insertData
   * @Description: 新增主题
   * @param beanIn
   * @return
   * @throws Exception
   * @return String
   * @author czl
   * @date 2016-10-10
   */
  public boolean insertData(Pojo1100121 beanIn) throws Exception {
      boolean result = false;
      int countZT = 0;
      int countJY = 0;
      String ztId = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数，用于插入主题ID

      try {
          db.openConnection();
          db.beginTran();
          StringBuffer strbufZ = new StringBuffer();
          strbufZ.append(" INSERT INTO ");
          strbufZ.append("     BKZT ");
          strbufZ.append(" ( ");
          strbufZ.append("     BKZT_ZTID, ");//主题ID
          strbufZ.append("     BKZT_BKID, ");//版块ID
          strbufZ.append("     BKZT_ZTMC, ");//主题名称
          strbufZ.append("     BKZT_ZTNR, ");//主题内容
          strbufZ.append("     BKZT_FBR, ");//发布人
          strbufZ.append("     BKZT_FBSJ, ");//发布时间
          strbufZ.append("     BKZT_ZTLB, ");//主题类别（0：普通 1：精华）
          strbufZ.append("     BKZT_CJR, ");//创建人
          strbufZ.append("     BKZT_CJSJ, ");//创建时间
          strbufZ.append("     BKZT_GXR, ");//更新人
          strbufZ.append("     BKZT_GXSJ, ");//更新时间
          strbufZ.append("     BKZT_SCBZ  ");//删除标志
          strbufZ.append(" ) ");
          strbufZ.append(" VALUES ");
          strbufZ.append(" ( ");
          strbufZ.append("     '"+ztId+"', ");
          strbufZ.append("     '"+beanIn.getBKZT_BKID()+"', ");
          strbufZ.append("     '"+beanIn.getBKZT_ZTMC()+"', ");
          strbufZ.append("     '"+beanIn.getZTNR()+"', ");
          strbufZ.append("     '"+beanIn.getBKZT_CJR()+"', ");
          strbufZ.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");
          strbufZ.append("     '0', ");
          strbufZ.append("     '"+beanIn.getBKZT_CJR()+"', ");
          strbufZ.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");
          strbufZ.append("     '"+beanIn.getBKZT_GXR()+"', ");
          strbufZ.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'),  ");
          strbufZ.append("     '0'  ");
          strbufZ.append(" ) ");
          countZT = db.executeSQL(strbufZ);

          int count_jy = getLtjyCount(beanIn.getBKZT_CJR());
          if (count_jy>0) {
              StringBuffer strbufJ = new StringBuffer();
              strbufJ.append(" UPDATE ");
              strbufJ.append("     LTJY ");
              strbufJ.append(" SET ");
              strbufJ.append("     LTJY_JYZ=LTJY_JYZ+5,");//
              strbufJ.append("     LTJY_GXR='").append(beanIn.getBKZT_CJR()).append("',");//更新人
              strbufJ.append("     LTJY_GXSJ=DATE_FORMAT(now(),'%Y%m%d %H:%i:%s')");//更新时间
              strbufJ.append(" WHERE 1=1");
              strbufJ.append(" AND LTJY_YHID='").append(beanIn.getBKZT_CJR()).append("'");//玩家ID
              countJY = db.executeSQL(strbufJ);
          } else {
              StringBuffer strbufJ = new StringBuffer();
              strbufJ.append(" INSERT INTO ");
              strbufJ.append("     LTJY ");
              strbufJ.append(" ( ");
              strbufJ.append("     LTJY_YHID, ");//用户ID
              strbufJ.append("     LTJY_JYZ, ");//经验值
              strbufJ.append("     LTJY_CJR, ");//创建人
              strbufJ.append("     LTJY_CJSJ, ");//创建时间
              strbufJ.append("     LTJY_GXR, ");//更新人
              strbufJ.append("     LTJY_GXSJ, ");//更新时间
              strbufJ.append("     LTJY_SCBZ  ");//删除标志
              strbufJ.append(" ) ");
              strbufJ.append(" VALUES ");
              strbufJ.append(" ( ");
              strbufJ.append("     '"+beanIn.getBKZT_CJR()+"', ");
              strbufJ.append("     '5', ");
              strbufJ.append("     '"+beanIn.getBKZT_CJR()+"', ");
              strbufJ.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");
              strbufJ.append("     '"+beanIn.getBKZT_GXR()+"', ");
              strbufJ.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'),  ");
              strbufJ.append("     '0'  ");
              strbufJ.append(" ) ");
              countJY = db.executeSQL(strbufJ);
          }

          if (countZT > 0 && countJY > 0) {
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
    * @FunctionName: getZtData
    * @Description: 获取主题信息
    * @param beanIn
    * @throws Exception
    * @return Pojo1100121
    * @author czl
    * @date 2016-10-11
    */
    public Pojo1100121 getZtData(Pojo1100121 beanIn) throws Exception {
        Pojo1100121 result = null;

        try {
            db.openConnection();

            StringBuffer strbuf = new StringBuffer();
            strbuf.append(" SELECT ");
            strbuf.append("     B.BKZT_ZTID, ");//主题ID
            strbuf.append("     B.BKZT_ZTMC, ");//主题名称
            strbuf.append("     B.BKZT_ZTNR, ");//主题内容
            strbuf.append("     B.BKZT_FBR, ");//发表人
            strbuf.append("     Y.YHXX_YHMC AS ZZMC, ");//作者名称
            strbuf.append("     Y.YHXX_GRZP AS GRZP, ");//个人照片
            strbuf.append("     DATE_FORMAT(STR_TO_DATE(B.BKZT_FBSJ, '%Y%m%d %H:%i'), '%Y-%m-%d %H:%i') AS BKZT_FBSJ, ");//发布时间
            strbuf.append("     B.BKZT_ZTLB ");//主题类别
            strbuf.append(" FROM ");
            strbuf.append("     BKZT B LEFT JOIN YHXX Y ON B.BKZT_FBR = Y.YHXX_YHID ");
            strbuf.append(" WHERE 1=1 ");
            strbuf.append(" AND B.BKZT_ZTID = '").append(beanIn.getBKZT_ZTID()).append("'");

            ResultSetHandler<Pojo1100121> rs = new BeanHandler<Pojo1100121>(
                    Pojo1100121.class);

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
    *
    * @FunctionName: getHfDataList
    * @Description: 获取主题回复信息
    * @param beanIn
    * @return
    * @throws Exception
    * @return List<Pojo1100122>
    * @author czl
    * @date 2016-10-11
    */
   public List<Pojo1100122> getHfDataList(String ztid) throws Exception {
       List<Pojo1100122> dataList = null;

       try {
           db.openConnection();

           StringBuffer strbuf = new StringBuffer();
           strbuf.append(" SELECT ");
           strbuf.append("     Z.ZTHF_HFID, ");//回复ID
           strbuf.append("     DATE_FORMAT(STR_TO_DATE(Z.ZTHF_HFSJ, '%Y%m%d %H:%i'), '%Y-%m-%d %H:%i') AS ZTHF_HFSJ, ");//回复时间
           strbuf.append("     Z.ZTHF_HFNR, ");//回复内容
           strbuf.append("     Z.ZTHF_HFRY, ");//回复人
           strbuf.append("     Y.YHXX_YHMC AS ZZMC, ");//回复人
           strbuf.append("     Y.YHXX_GRZP AS GRZP ");//个人头像
           strbuf.append(" FROM ");
           strbuf.append("     ZTHF Z");
           strbuf.append(" LEFT JOIN ");
           strbuf.append("     YHXX Y");
           strbuf.append(" ON ");
           strbuf.append(" Y.YHXX_YHID = Z.ZTHF_HFRY");
           strbuf.append(" WHERE 1=1 ");
           strbuf.append(" AND Z.ZTHF_ZTID = '").append(ztid).append("'");
           strbuf.append(" ORDER BY ");
           strbuf.append("     Z.ZTHF_HFSJ ");
           ResultSetHandler<List<Pojo1100122>> rs = new BeanListHandler<Pojo1100122>(Pojo1100122.class);
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
   * @FunctionName: insertHfData
   * @Description: 新增回复
   * @param beanIn
   * @throws Exception
   * @return boolean
   * @author czl
   * @date 2016-10-11
   */
  public boolean insertHfData(Pojo1100122 beanIn) throws Exception {
      boolean result = false;
      int countHf = 0;
      int countJy = 0;
      int countZt = 0;
      String hfId = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数，用于插入回复ID

      try {
          db.openConnection();
          db.beginTran();
          StringBuffer strbufH = new StringBuffer();
          strbufH.append(" INSERT INTO ");
          strbufH.append("     ZTHF ");
          strbufH.append(" ( ");
          strbufH.append("     ZTHF_HFID, ");//回复ID
          strbufH.append("     ZTHF_ZTID, ");//主题ID
          strbufH.append("     ZTHF_HFRY, ");//回复人员
          strbufH.append("     ZTHF_HFSJ, ");//回复时间
          strbufH.append("     ZTHF_HFNR, ");//回复内容
          strbufH.append("     ZTHF_CJR, ");//创建人
          strbufH.append("     ZTHF_CJSJ, ");//创建时间
          strbufH.append("     ZTHF_GXR, ");//更新人
          strbufH.append("     ZTHF_GXSJ, ");//更新时间
          strbufH.append("     ZTHF_SCBZ  ");//删除标志
          strbufH.append(" ) ");
          strbufH.append(" VALUES ");
          strbufH.append(" ( ");
          strbufH.append("     '"+hfId+"', ");
          strbufH.append("     '"+beanIn.getZTHF_ZTID()+"', ");
          strbufH.append("     '"+beanIn.getZTHF_CJR()+"', ");
          strbufH.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");
          strbufH.append("     '"+beanIn.getHFNR()+"', ");
          strbufH.append("     '"+beanIn.getZTHF_CJR()+"', ");
          strbufH.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");
          strbufH.append("     '"+beanIn.getZTHF_GXR()+"', ");
          strbufH.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'),  ");
          strbufH.append("     '0'  ");
          strbufH.append(" ) ");
          countHf = db.executeSQL(strbufH);

          int count_jy = getLtjyCount(beanIn.getZTHF_CJR());
          if (count_jy>0) {
              StringBuffer strbufJ = new StringBuffer();
              strbufJ.append(" UPDATE ");
              strbufJ.append("     LTJY ");
              strbufJ.append(" SET ");
              strbufJ.append("     LTJY_JYZ=LTJY_JYZ+2,");//
              strbufJ.append("     LTJY_GXR='").append(beanIn.getZTHF_CJR()).append("',");//更新人
              strbufJ.append("     LTJY_GXSJ=DATE_FORMAT(now(),'%Y%m%d %H:%i:%s')");//更新时间
              strbufJ.append(" WHERE 1=1");
              strbufJ.append(" AND LTJY_YHID='").append(beanIn.getZTHF_CJR()).append("'");//玩家ID
              countJy = db.executeSQL(strbufJ);
          } else {
              StringBuffer strbufJ = new StringBuffer();
              strbufJ.append(" INSERT INTO ");
              strbufJ.append("     LTJY ");
              strbufJ.append(" ( ");
              strbufJ.append("     LTJY_YHID, ");//用户ID
              strbufJ.append("     LTJY_JYZ, ");//经验值
              strbufJ.append("     LTJY_CJR, ");//创建人
              strbufJ.append("     LTJY_CJSJ, ");//创建时间
              strbufJ.append("     LTJY_GXR, ");//更新人
              strbufJ.append("     LTJY_GXSJ, ");//更新时间
              strbufJ.append("     LTJY_SCBZ  ");//删除标志
              strbufJ.append(" ) ");
              strbufJ.append(" VALUES ");
              strbufJ.append(" ( ");
              strbufJ.append("     '"+beanIn.getZTHF_CJR()+"', ");
              strbufJ.append("     '2', ");
              strbufJ.append("     '"+beanIn.getZTHF_CJR()+"', ");
              strbufJ.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");
              strbufJ.append("     '"+beanIn.getZTHF_GXR()+"', ");
              strbufJ.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'),  ");
              strbufJ.append("     '0'  ");
              strbufJ.append(" ) ");
              countJy = db.executeSQL(strbufJ);
          }

          StringBuffer strbufZ = new StringBuffer();
          strbufZ.append(" UPDATE ");
          strbufZ.append("     LTJY ");
          strbufZ.append(" SET ");
          strbufZ.append("     LTJY_JYZ=LTJY_JYZ+1,");//
          strbufZ.append("     LTJY_GXR='").append(beanIn.getZTHF_CJR()).append("',");//更新人
          strbufZ.append("     LTJY_GXSJ=DATE_FORMAT(now(),'%Y%m%d %H:%i:%s')");//更新时间
          strbufZ.append(" WHERE 1=1");
          strbufZ.append(" AND LTJY_YHID='").append(beanIn.getFBR()).append("'");//玩家ID
          countZt = db.executeSQL(strbufZ);


          if (countHf > 0 && countJy > 0 && countZt > 0) {
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
  * @FunctionName: getJsxxCount
  * @Description: 获取局数信息记录个数
  * @param beanIn
  * @return
  * @throws Exception
  * @return int
  * @author czl
  * @date 2016-9-13
  */
  public int getLtjyCount(String yhid) throws Exception {
     int result = 0;

     try {
         StringBuffer strbuf = new StringBuffer();
         strbuf.append(" SELECT ");
         strbuf.append("     COUNT(L.LTJY_YHID) ");
         strbuf.append(" FROM ");
         strbuf.append("     LTJY L ");
         strbuf.append(" WHERE 1=1");
         strbuf.append(" AND L.LTJY_SCBZ = '0'");
         strbuf.append(" AND L.LTJY_YHID = '").append(yhid).append("'");

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
  * @FunctionName: updateZdData
  * @Description: 置顶
  * @param beanIn
  * @throws Exception
  * @return boolean
  * @author czl
  * @date 2016-10-13
  */
 public boolean updateZdData(Pojo1100121 beanIn) throws Exception {
     boolean result = false;
     int count = 0;

     try {
         db.openConnection();
         db.beginTran();
         StringBuffer strbuf = new StringBuffer();
         strbuf.append(" UPDATE ");
         strbuf.append("     BKZT ");
         strbuf.append(" SET ");
         strbuf.append("     BKZT_ZTLB='").append(beanIn.getBKZT_ZTLB()).append("',");
         strbuf.append("     BKZT_GXR='").append(beanIn.getBKZT_GXR()).append("',");//更新人
         strbuf.append("     BKZT_GXSJ=DATE_FORMAT(now(),'%Y%m%d %H:%i:%s')");//更新时间
         strbuf.append(" WHERE 1=1");
         strbuf.append(" AND BKZT_ZTID='").append(beanIn.getBKZT_ZTID()).append("'");
         count = db.executeSQL(strbuf);

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

    /**
    *
    * @FunctionName: getHfInfo
    * @Description: 获取最后回复详情
    * @param bkid
    * @throws Exception
    * @return Pojo_ZTHF
    * @author czl
    * @date 2016-10-14
    */
    public Pojo1100122 getHfInfo(String ztid) throws Exception {
        Pojo1100122 baseBean = new Pojo1100122();

        try {
            db.openConnection();

            StringBuffer strbuf = new StringBuffer();
            strbuf.append(" SELECT ");
            strbuf.append("     Y.YHXX_YHMC AS ZZMC, ");//回复人
            strbuf.append("     Y.YHXX_GRZP AS GRZP ");//头像
            strbuf.append(" FROM ");
            strbuf.append("     ZTHF Z");
            strbuf.append(" LEFT JOIN ");
            strbuf.append("     YHXX Y");
            strbuf.append(" ON ");
            strbuf.append("     Z.ZTHF_HFRY = Y.YHXX_YHID ");
            strbuf.append(" WHERE 1=1 ");
            strbuf.append(" AND Z.ZTHF_SCBZ = '0'");
            strbuf.append(" AND Z.ZTHF_ZTID = '").append(ztid).append("'");
            strbuf.append(" ORDER BY");
            strbuf.append("     Z.ZTHF_HFSJ DESC LIMIT 1 ");
            strbuf.append("");

            ResultSetHandler<Pojo1100122> rs = new BeanHandler<Pojo1100122>(
                    Pojo1100122.class);

            baseBean = db.queryForBeanHandler(strbuf, rs);
        } catch (Exception e) {
            MyLogger.error(this.getClass().getName(), e);
            throw e;
        } finally {
            db.closeConnection();
        }
        return baseBean;
    }

    /**
    *
    * @FunctionName: deleteZtData
    * @Description: 删除主题
    * @param beanIn
    * @throws Exception
    * @return boolean
    * @author czl
    * @date 2016-10-17
    */
   public boolean deleteZtData(Pojo1100121 beanIn) throws Exception {
       boolean result = false;
       int count = 0;

       try {
           db.openConnection();
           db.beginTran();
           StringBuffer strbuf_ZT = new StringBuffer();
           strbuf_ZT.append(" DELETE FROM");
           strbuf_ZT.append("     BKZT ");
           strbuf_ZT.append(" WHERE ");
           strbuf_ZT.append("     BKZT_ZTID='").append(beanIn.getBKZT_ZTID()).append("'");
           count = db.executeSQL(strbuf_ZT);

           StringBuffer strbuf_HF = new StringBuffer();
           strbuf_HF.append(" DELETE FROM");
           strbuf_HF.append("     ZTHF ");
           strbuf_HF.append(" WHERE ");
           strbuf_HF.append("     ZTHF_ZTID='").append(beanIn.getBKZT_ZTID()).append("'");
           db.executeSQL(strbuf_HF);
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

   /**
   *
   * @FunctionName: deleteHfData
   * @Description: 删除回复
   * @param beanIn
   * @throws Exception
   * @return boolean
   * @author czl
   * @date 2016-10-17
   */
  public boolean deleteHfData(Pojo1100122 beanIn) throws Exception {
      boolean result = false;
      int count = 0;

      try {
          db.openConnection();
          db.beginTran();

          StringBuffer strbuf = new StringBuffer();
          strbuf.append(" DELETE FROM");
          strbuf.append("     ZTHF ");
          strbuf.append(" WHERE ");
          strbuf.append("     ZTHF_HFID='").append(beanIn.getZTHF_HFID()).append("'");
          count = db.executeSQL(strbuf);

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

  /**
  *
  * @FunctionName: getBbsXp
  * @Description: 获取个人经验值
  * @param beanIn
  * @throws Exception
  * @return Pojo_LTJY
  * @author czl
  * @date 2016-10-25
  */
  public Pojo_LTJY getBbsXp(String yhid) throws Exception {
      Pojo_LTJY result = null;

      try {
          db.openConnection();

          StringBuffer strbuf = new StringBuffer();
          strbuf.append(" SELECT ");
          strbuf.append("     L.LTJY_JYZ ");//经验值
          strbuf.append(" FROM ");
          strbuf.append("     LTJY L ");
          strbuf.append(" WHERE 1=1 ");
          strbuf.append(" AND L.LTJY_YHID = '").append(yhid).append("'");

          ResultSetHandler<Pojo_LTJY> rs = new BeanHandler<Pojo_LTJY>(
                  Pojo_LTJY.class);

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
  *
  * @FunctionName: getBbsLevel
  * @Description: 获取论坛等级
  * @param beanIn
  * @throws Exception
  * @return Pojo_LTDJ
  * @author czl
  * @date 2016-10-25
  */
  public Pojo_LTDJ getBbsLevel(Integer jyz) throws Exception {
      Pojo_LTDJ result = null;

      try {
          db.openConnection();

          StringBuffer strbuf = new StringBuffer();
          strbuf.append(" SELECT ");
          strbuf.append("     L.LTDJ_JBID, ");//级别ID
          strbuf.append("     L.LTDJ_JBMC, ");//级别名称
          strbuf.append("     L.LTDJ_JBTP ");//级别图片
          strbuf.append(" FROM ");
          strbuf.append("     LTDJ L ");
          strbuf.append(" WHERE 1=1 ");
          strbuf.append(" AND L.LTDJ_JBJY <= ").append(jyz).append("");
          strbuf.append(" ORDER BY L.LTDJ_JBJY DESC LIMIT 1 ");

          ResultSetHandler<Pojo_LTDJ> rs = new BeanHandler<Pojo_LTDJ>(
                  Pojo_LTDJ.class);

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
  *
  * @FunctionName: getBbsUp
  * @Description: 获取升级所需经验
  * @param beanIn
  * @throws Exception
  * @return Pojo_LTDJ
  * @author czl
  * @date 2016-10-25
  */
  public Pojo_LTDJ getBbsUp(Integer jyz) throws Exception {
      Pojo_LTDJ result = null;

      try {
          db.openConnection();

          StringBuffer strbuf = new StringBuffer();
          strbuf.append(" SELECT ");
          strbuf.append("     L.LTDJ_JBJY ");//经验值
          strbuf.append(" FROM ");
          strbuf.append("     LTDJ L ");
          strbuf.append(" WHERE 1=1 ");
          strbuf.append(" AND L.LTDJ_JBJY > ").append(jyz).append("");
          strbuf.append(" ORDER BY L.LTDJ_JBJY LIMIT 1 ");

          ResultSetHandler<Pojo_LTDJ> rs = new BeanHandler<Pojo_LTDJ>(
                  Pojo_LTDJ.class);

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