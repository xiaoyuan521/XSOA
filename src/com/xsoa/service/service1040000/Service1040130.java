package com.xsoa.service.service1040000;

import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.xsoa.pojo.basetable.Pojo_JRFG;
import com.xsoa.pojo.basetable.Pojo_JSXX;
import com.xsoa.pojo.basetable.Pojo_LRXX;
import com.xsoa.pojo.custom.pojo_1040000.Pojo1040130;

public class Service1040130 extends BaseService {
	private DBManager db = null;

	public Service1040130() {
		db = new DBManager();
	}

	/**
     * @FunctionName: getJrfg
     * @Description: 获取今日法官
     * @param yhid
     * @throws Exception
     * @return Pojo1050110
     * @author czl
     * @date 2015-02-27
     */
    public Pojo_JRFG getJrfg() throws Exception {
        Pojo_JRFG result = null;
        try {
            db.openConnection();

            StringBuffer sql = new StringBuffer();
            sql.append(" SELECT ");
            sql.append("     Y.YHXX_YHMC AS JRFG_FGID, ");//今日法官
            sql.append("     X.YHXX_YHMC AS JRFG_BYID ");//备用法官
            sql.append(" FROM ");
            sql.append("     JRFG J");
            sql.append(" LEFT JOIN ");
            sql.append("     YHXX Y ");
            sql.append(" ON ");
            sql.append("     J.JRFG_FGID = Y.YHXX_YHID");
            sql.append(" LEFT JOIN ");
            sql.append("     YHXX X ");
            sql.append(" ON ");
            sql.append("     J.JRFG_BYID = X.YHXX_YHID");

            ResultSetHandler<Pojo_JRFG> rsh = new BeanHandler<Pojo_JRFG>(
                    Pojo_JRFG.class);
            result = db.queryForBeanHandler(sql, rsh);

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
	 * @FunctionName: getDataList
	 * @Description: 获取玩家胜率信息
	 * @param beanIn
	 * @param page
	 * @param limit
	 * @param sort
	 * @return
	 * @throws Exception
	 * @return List<Pojo1040130>
	 * @author czl
	 * @date 2016-9-6
	 */
	public List<Pojo1040130> getDataList() throws Exception {
		List<Pojo1040130> dataList = null;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     Y.YHXX_YHID, ");//用户ID
			strbuf.append("     Y.YHXX_YHMC, ");//用户名称
			strbuf.append("     S.SLXX_SCSL, ");//上次胜率
			strbuf.append("     S.SLXX_ZTSL ");//总体胜率
			strbuf.append(" FROM ");
			strbuf.append("     YHXX Y");
			strbuf.append(" LEFT JOIN ");
			strbuf.append("     SLXX S");
			strbuf.append(" ON ");
			strbuf.append(" Y.YHXX_YHID = S.SLXX_YHID");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(this.searchSql());
			strbuf.append(" ORDER BY ");
			strbuf.append("     Y.YHXX_YHID ");
			ResultSetHandler<List<Pojo1040130>> rs = new BeanListHandler<Pojo1040130>(Pojo1040130.class);
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
    * @FunctionName: getLrxxList
    * @Description: 获取狼人信息
    * @param beanIn
    * @param page
    * @param limit
    * @param sort
    * @return
    * @throws Exception
    * @return List<Pojo_LRXX>
    * @author czl
    * @date 2016-9-14
    */
   public List<Pojo_LRXX> getLrxxList() throws Exception {
       List<Pojo_LRXX> dataList = null;

       try {
           db.openConnection();

           StringBuffer strbuf = new StringBuffer();
           strbuf.append(" SELECT ");
           strbuf.append("     L.LRXX_LRID, ");//狼人ID
           strbuf.append("     L.LRXX_LRMC ");//狼人名称
           strbuf.append(" FROM ");
           strbuf.append("     LRXX L");
           strbuf.append(" WHERE 1=1 ");
           strbuf.append(" AND L.LRXX_SCBZ = '0' ");
           strbuf.append(" ORDER BY ");
           strbuf.append("     L.LRXX_LRID ");
           ResultSetHandler<List<Pojo_LRXX>> rs = new BeanListHandler<Pojo_LRXX>(Pojo_LRXX.class);
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
   * @FunctionName: getLrslList
   * @Description: 获取狼人胜率
   * @param beanIn
   * @param page
   * @param limit
   * @param sort
   * @return
   * @throws Exception
   * @return List<Pojo_JSXX>
   * @author czl
   * @date 2016-9-14
   */
  public List<Pojo_JSXX> getLrslList() throws Exception {
      List<Pojo_JSXX> dataList = null;

      try {
          db.openConnection();

          StringBuffer strbuf = new StringBuffer();
          strbuf.append(" SELECT ");
          strbuf.append("     Y.YHXX_YHID, ");//用户ID
          strbuf.append("     Y.YHXX_YHMC AS YHMC, ");//用户名称
          strbuf.append("     cast(avg((J.JSXX_LRSL)/(J.JSXX_LRJS)*100)as decimal(10,1)) AS LRSL,");//狼人胜率
          strbuf.append("     cast(avg((J.JSXX_NWSL)/(J.JSXX_NWJS)*100)as decimal(10,1)) AS NWSL,");//女巫胜率
          strbuf.append("     cast(avg((J.JSXX_XZSL)/(J.JSXX_XZJS)*100)as decimal(10,1)) AS XZSL,");//预言家胜率
          strbuf.append("     cast(avg((J.JSXX_LQSL)/(J.JSXX_LQJS)*100)as decimal(10,1)) AS LQSL,");//猎人胜率
          strbuf.append("     cast(avg((J.JSXX_PMSL)/(J.JSXX_PMJS)*100)as decimal(10,1)) AS PMSL");//平民胜率
          strbuf.append(" FROM ");
          strbuf.append("     YHXX Y");
          strbuf.append(" LEFT JOIN ");
          strbuf.append("     JSXX J");
          strbuf.append(" ON ");
          strbuf.append(" Y.YHXX_YHID = J.JSXX_YHID");
          strbuf.append(" WHERE 1=1 ");
          strbuf.append(this.searchSql());
          strbuf.append(" GROUP BY ");
          strbuf.append("     Y.YHXX_YHID ");
          strbuf.append(" ORDER BY ");
          strbuf.append("     Y.YHXX_YHID ");
          ResultSetHandler<List<Pojo_JSXX>> rs = new BeanListHandler<Pojo_JSXX>(Pojo_JSXX.class);
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
	 * @date 2016-9-6
	 */
	private String searchSql() throws Exception {
		StringBuffer strbuf = new StringBuffer();

		try {
			/* 公共方法 */
			strbuf.append(" AND Y.YHXX_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			/* 公共方法 */
            strbuf.append(" AND Y.YHXX_JSID != 'J101'");//删除标志（0：未删除，1：已删除）
			/* 判断当前登陆用户是否是管理人员 */
//			if ("0".equals(beanIn.getDQYH_ZWDJ())) {//职员
//				strbuf.append(" AND A.KHXX_CJR = '").append(beanIn.getDQYH_YHID()).append("'");
//			} else {//管理人员
//				strbuf.append(" AND D.BMXX_BMID = '").append(beanIn.getDQYH_BMID()).append("'");
//			}

		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		}
		return strbuf.toString();
	}
}