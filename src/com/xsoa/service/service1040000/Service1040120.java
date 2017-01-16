package com.xsoa.service.service1040000;

import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.xsoa.pojo.basetable.Pojo_LRXX;
import com.xsoa.pojo.custom.pojo_1040000.Pojo1040120;

public class Service1040120 extends BaseService {
	private DBManager db = null;

	public Service1040120() {
		db = new DBManager();
	}

	/**
    *
    * @FunctionName: getLrxxList
    * @Description: 获取狼人信息
    * @throws Exception
    * @return List<Pojo_LRXX>
    * @author czl
    * @date 2016-10-18
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
    * @FunctionName: getData
    * @Description: 获取玩家拿牌率
    * @throws Exception
    * @return String
    * @author czl
    * @date 2016-10-18
    */
   public Pojo1040120 getData(String lrid, String yhid) throws Exception {
       Pojo1040120 baseBean = new Pojo1040120();
       try {
           db.openConnection();

           StringBuffer strbuf = new StringBuffer();
           strbuf.append(" SELECT ");
           switch (lrid) {
               case "01" :
                   strbuf.append("cast(avg((JSXX_LRJS)/(JSXX_LRJS+JSXX_NWJS+JSXX_LQJS+JSXX_XZJS+JSXX_PMJS)*100) as decimal(10,1)) AS NPL");//
                   break;
               case "02" :
                   strbuf.append("cast(avg((JSXX_LQJS)/(JSXX_LRJS+JSXX_NWJS+JSXX_LQJS+JSXX_XZJS+JSXX_PMJS)*100) as decimal(10,1)) AS NPL");//
                   break;
               case "03" :
                   strbuf.append("cast(avg((JSXX_NWJS)/(JSXX_LRJS+JSXX_NWJS+JSXX_LQJS+JSXX_XZJS+JSXX_PMJS)*100) as decimal(10,1)) AS NPL");//
                   break;
               case "04" :
                   strbuf.append("cast(avg((JSXX_XZJS)/(JSXX_LRJS+JSXX_NWJS+JSXX_LQJS+JSXX_XZJS+JSXX_PMJS)*100) as decimal(10,1)) AS NPL");//
                   break;
               case "05" :
                   strbuf.append("cast(avg((JSXX_PMJS)/(JSXX_LRJS+JSXX_NWJS+JSXX_LQJS+JSXX_XZJS+JSXX_PMJS)*100) as decimal(10,1)) AS NPL");//
                   break;
           }
           strbuf.append(" FROM ");
           strbuf.append("     JSXX");
           strbuf.append(" WHERE ");
           strbuf.append("     JSXX_YHID = '").append(yhid).append("'");

           ResultSetHandler<Pojo1040120> rs = new BeanHandler<Pojo1040120>(
                   Pojo1040120.class);

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
   * @FunctionName: getSl
   * @Description: 获取玩家胜率
   * @throws Exception
   * @return String
   * @author czl
   * @date 2016-10-18
   */
  public Pojo1040120 getSl(String lrid, String yhid) throws Exception {
      Pojo1040120 baseBean = new Pojo1040120();
      try {
          db.openConnection();

          StringBuffer strbuf = new StringBuffer();
          strbuf.append(" SELECT ");
          switch (lrid) {
              case "01" :
                  strbuf.append("cast(avg((JSXX_LRSL)/(JSXX_LRJS)*100) as decimal(10,1)) AS GRSL");//
                  break;
              case "02" :
                  strbuf.append("cast(avg((JSXX_LQSL)/(JSXX_LQJS)*100) as decimal(10,1)) AS GRSL");//
                  break;
              case "03" :
                  strbuf.append("cast(avg((JSXX_NWSL)/(JSXX_NWJS)*100) as decimal(10,1)) AS GRSL");//
                  break;
              case "04" :
                  strbuf.append("cast(avg((JSXX_XZSL)/(JSXX_XZJS)*100) as decimal(10,1)) AS GRSL");//
                  break;
              case "05" :
                  strbuf.append("cast(avg((JSXX_PMSL)/(JSXX_PMJS)*100) as decimal(10,1)) AS GRSL");//
                  break;
          }
          strbuf.append(" FROM ");
          strbuf.append("     JSXX");
          strbuf.append(" WHERE ");
          strbuf.append("     JSXX_YHID = '").append(yhid).append("'");

          ResultSetHandler<Pojo1040120> rs = new BeanHandler<Pojo1040120>(
                  Pojo1040120.class);

          baseBean = db.queryForBeanHandler(strbuf, rs);
      } catch (Exception e) {
          MyLogger.error(this.getClass().getName(), e);
          throw e;
      } finally {
          db.closeConnection();
      }
      return baseBean;
  }
}