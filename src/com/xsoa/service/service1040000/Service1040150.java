package com.xsoa.service.service1040000;

import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsoa.pojo.basetable.Pojo_DWDJ;
import com.xsoa.pojo.basetable.Pojo_DWXX;
import com.xsoa.pojo.basetable.Pojo_LRXX;
import com.xsoa.pojo.custom.pojo_1040000.Pojo1040150;

public class Service1040150 extends BaseService {
	private DBManager db = null;

	public Service1040150() {
		db = new DBManager();
	}
	/**
	 *
	 * @FunctionName: getDataCount
	 * @Description: 获取游戏信息数据个数
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2016-9-12
	 */
	public int getDataCount(Pojo1040150 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(Y.YXXX_YXID) ");//游戏信息个数
			strbuf.append(" FROM ");
			strbuf.append("     YXXX Y LEFT JOIN YHXX B ON Y.YXXX_YHID = B.YHXX_YHID LEFT JOIN LRXX L ON Y.YXXX_LRID = L.LRXX_LRID ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(this.searchSql(beanIn));

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
	 * @FunctionName: getDataList
	 * @Description: 获取游戏信息列表数据明细
	 * @param beanIn
	 * @param page
	 * @param limit
	 * @param sort
	 * @return
	 * @throws Exception
	 * @return List<Pojo1040150>
	 * @author czl
	 * @date 2016-9-12
	 */
	public List<Pojo1040150> getDataList(Pojo1040150 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo1040150> result = null;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     Y.YXXX_YXID, ");//游戏ID
			strbuf.append("     Y.YXXX_YHID, ");//玩家ID
			strbuf.append("     Y.YXXX_LRID, ");//狼人ID
			strbuf.append("     Y.YXXX_SYZT, ");//输赢ID
			strbuf.append("     B.YHXX_YHMC AS YXXX_WJMC, ");//玩家名称
			strbuf.append("     L.LRXX_LRMC AS YXXX_WJJS, ");//玩家角色
			strbuf.append("     CASE WHEN Y.YXXX_SYZT = '0' THEN '赢' ELSE '输' END AS SYZT, ");//输赢状态
			strbuf.append("     Y.YXXX_YXRQ, ");//游戏日期
			strbuf.append("     Y.YXXX_BZXX, ");//备注
			strbuf.append("     C.YHXX_YHMC AS YXXX_LRR, ");//录入人
			strbuf.append("     Y.YXXX_CJSJ, ");//创建时间
			strbuf.append("     Y.YXXX_GXR, ");//更新人
			strbuf.append("     Y.YXXX_GXSJ, ");//更新时间
			strbuf.append("     Y.YXXX_SCBZ  ");//删除标志（0：未删除，1：已删除）
			strbuf.append(" FROM ");
			strbuf.append("     YXXX Y ");
			strbuf.append(" LEFT JOIN ");
			strbuf.append("     YHXX B ");
			strbuf.append(" ON ");
			strbuf.append("     Y.YXXX_YHID = B.YHXX_YHID ");
			strbuf.append(" LEFT JOIN ");
            strbuf.append("     LRXX L ");
            strbuf.append(" ON ");
            strbuf.append("     Y.YXXX_LRID = L.LRXX_LRID ");
            strbuf.append(" LEFT JOIN ");
            strbuf.append("     YHXX C ");
            strbuf.append(" ON ");
            strbuf.append("     Y.YXXX_CJR = C.YHXX_YHID ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(this.searchSql(beanIn));
			strbuf.append(" ORDER BY ");
			strbuf.append("     Y.YXXX_CJSJ DESC, Y.YXXX_YHID DESC ");

			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);

			ResultSetHandler<List<Pojo1040150>> rs = new BeanListHandler<Pojo1040150>(
					Pojo1040150.class);

			result = db.queryForBeanListHandler(execSql, rs);
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
	 * @FunctionName: searchSql
	 * @Description: 查询条件部分
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return String
	 * @author czl
	 * @date 2015-9-12
	 */
	private String searchSql(Pojo1040150 beanIn) throws Exception {
		StringBuffer strbuf = new StringBuffer();

		try {
			/* 公共方法 */
			strbuf.append(" AND Y.YXXX_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND B.YHXX_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND L.LRXX_SCBZ = '0'");//删除标志（0：未删除，1：已删除）

			/* 玩家ID */
			if (MyStringUtils.isNotBlank(beanIn.getYXXX_YHID()) && !"000".equals(beanIn.getYXXX_YHID())) {
				strbuf.append(" AND Y.YXXX_YHID = '").append(beanIn.getYXXX_YHID()).append("'");
			}
			/* 角色ID */
            if (MyStringUtils.isNotBlank(beanIn.getYXXX_LRID()) && !"000".equals(beanIn.getYXXX_LRID())) {
                strbuf.append(" AND Y.YXXX_LRID = '").append(beanIn.getYXXX_LRID()).append("'");
            }
			/* 输赢状态 */
			if (MyStringUtils.isNotBlank(beanIn.getYXXX_SYZT()) && !"000".equals(beanIn.getYXXX_SYZT())) {
				strbuf.append(" AND Y.YXXX_SYZT = '").append(beanIn.getYXXX_SYZT()).append("'");
			}
			/* 开始时间 */
            if (MyStringUtils.isNotBlank(beanIn.getYXXX_KSSJ())) {
                strbuf.append(" AND Y.YXXX_YXRQ >= '").append(beanIn.getYXXX_KSSJ()).append("'");
            }
            /* 结束时间 */
            if (MyStringUtils.isNotBlank(beanIn.getYXXX_JSSJ())) {
                strbuf.append(" AND Y.YXXX_YXRQ <= '").append(beanIn.getYXXX_JSSJ()).append("'");
            }
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		}
		return strbuf.toString();
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
        int count_DWXX = 0;
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

            //删除胜率信息
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

            //删除段位信息
            StringBuffer strbuf_DWXX = new StringBuffer();
            strbuf_DWXX.append(" UPDATE ");
            strbuf_DWXX.append("     DWXX ");
            strbuf_DWXX.append(" SET ");
            strbuf_DWXX.append("     DWXX_GRJF=DWXX_SCJF,");
            strbuf_DWXX.append("     DWXX_GXR='").append(beanIn.getYXXX_GXR()).append("',");//更新人
            strbuf_DWXX.append("     DWXX_GXSJ=DATE_FORMAT(now(),'%Y%m%d %H:%i:%s')");//更新时间
            strbuf_DWXX.append(" WHERE 1=1");
            strbuf_DWXX.append(" AND DWXX_YHID='").append(beanIn.getYXXX_YHID()).append("'");
            count_DWXX = db.executeSQL(strbuf_DWXX);
            if (count_YXXX > 0 && count_JSXX > 0 && count_SLXX > 0 && count_DWXX > 0) {
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
	 * @Description: 新增游戏信息
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return String
	 * @author czl
	 * @date 2016-9-12
	 */
    public boolean insertData(Pojo1040150 beanIn, List <Pojo_DWDJ> dwList) throws Exception {
        boolean result = false;
        int count_YXXX = 0;
        int count_JSXX = 0;
        int count_SLXX = 0;
        int count_DWXX = 0;
        String yxId = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数，用于插入游戏ID
        String jsId = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数，用于插入局数ID
        String slId = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数，用于插入胜率ID

        try {
            db.openConnection();
            db.beginTran();
            StringBuffer strbuf_YXXX = new StringBuffer();
            strbuf_YXXX.append(" INSERT INTO ");
            strbuf_YXXX.append("     YXXX ");
            strbuf_YXXX.append(" ( ");
            strbuf_YXXX.append("     YXXX_YXID, ");//游戏ID
            strbuf_YXXX.append("     YXXX_YHID, ");//玩家ID
            strbuf_YXXX.append("     YXXX_LRID, ");//狼人ID
            strbuf_YXXX.append("     YXXX_YXRQ, ");//游戏日期
            strbuf_YXXX.append("     YXXX_SYZT, ");//输赢状态
            strbuf_YXXX.append("     YXXX_BZXX, ");//备注
            strbuf_YXXX.append("     YXXX_CJR, ");//创建人
            strbuf_YXXX.append("     YXXX_CJSJ, ");//创建时间
            strbuf_YXXX.append("     YXXX_GXR, ");//更新人
            strbuf_YXXX.append("     YXXX_GXSJ, ");//更新时间
            strbuf_YXXX.append("     YXXX_SCBZ  ");//删除标志
            strbuf_YXXX.append(" ) ");
            strbuf_YXXX.append(" VALUES ");
            strbuf_YXXX.append(" ( ");
            strbuf_YXXX.append("     '"+yxId+"', ");
            strbuf_YXXX.append("     '"+beanIn.getYXXX_YHID()+"', ");
            strbuf_YXXX.append("     '"+beanIn.getYXXX_LRID()+"', ");
            strbuf_YXXX.append("     '"+beanIn.getYXXX_YXRQ()+"', ");
            strbuf_YXXX.append("     '"+beanIn.getYXXX_SYZT()+"', ");
            strbuf_YXXX.append("     '"+beanIn.getYXXX_BZXX()+"', ");
            strbuf_YXXX.append("     '"+beanIn.getYXXX_CJR()+"', ");
            strbuf_YXXX.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");
            strbuf_YXXX.append("     '"+beanIn.getYXXX_GXR()+"', ");
            strbuf_YXXX.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'),  ");
            strbuf_YXXX.append("     '0'  ");
            strbuf_YXXX.append(" ) ");
            count_YXXX = db.executeSQL(strbuf_YXXX);

            int count_js = getJsxxCount(beanIn.getYXXX_YHID());
            if (count_js>0) {
                StringBuffer strbuf_JSXX = new StringBuffer();
                strbuf_JSXX.append(" UPDATE ");
                strbuf_JSXX.append("     JSXX ");
                strbuf_JSXX.append(" SET ");
                switch (beanIn.getYXXX_LRID()) {
                    case "01" :
                        strbuf_JSXX.append("     JSXX_LRJS=JSXX_LRJS+1,");//
                        if ("0".equals(beanIn.getYXXX_SYZT())) {
                            strbuf_JSXX.append("     JSXX_LRSL=JSXX_LRSL+1,");//
                        }
                        break;
                    case "02" :
                        strbuf_JSXX.append("     JSXX_LQJS=JSXX_LQJS+1,");//
                        if ("0".equals(beanIn.getYXXX_SYZT())) {
                            strbuf_JSXX.append("     JSXX_LQSL=JSXX_LQSL+1,");//
                        }
                        break;
                    case "03" :
                        strbuf_JSXX.append("     JSXX_NWJS=JSXX_NWJS+1,");//
                        if ("0".equals(beanIn.getYXXX_SYZT())) {
                            strbuf_JSXX.append("     JSXX_NWSL=JSXX_NWSL+1,");//
                        }
                        break;
                    case "04" :
                        strbuf_JSXX.append("     JSXX_XZJS=JSXX_XZJS+1,");//
                        if ("0".equals(beanIn.getYXXX_SYZT())) {
                            strbuf_JSXX.append("     JSXX_XZSL=JSXX_XZSL+1,");//
                        }
                        break;
                    case "05" :
                        strbuf_JSXX.append("     JSXX_PMJS=JSXX_PMJS+1,");//
                        if ("0".equals(beanIn.getYXXX_SYZT())) {
                            strbuf_JSXX.append("     JSXX_PMSL=JSXX_PMSL+1,");//
                        }
                        break;
                }
                strbuf_JSXX.append("     JSXX_GXR='").append(beanIn.getYXXX_GXR()).append("',");//更新人
                strbuf_JSXX.append("     JSXX_GXSJ=DATE_FORMAT(now(),'%Y%m%d %H:%i:%s')");//更新时间
                strbuf_JSXX.append(" WHERE 1=1");
                strbuf_JSXX.append(" AND JSXX_YHID='").append(beanIn.getYXXX_YHID()).append("'");//玩家ID
                count_JSXX = db.executeSQL(strbuf_JSXX);
            } else {
                int lrjs = 0;
                int lrsl = 0;
                int nwjs = 0;
                int nwsl = 0;
                int xzjs = 0;
                int xzsl = 0;
                int lqjs = 0;
                int lqsl = 0;
                int pmjs = 0;
                int pmsl = 0;
                switch (beanIn.getYXXX_LRID()){
                    case "01" :
                        lrjs = 1;
                        if ("0".equals(beanIn.getYXXX_SYZT())) {
                            lrsl = 1;
                        } else {
                            lrsl = 0;
                        }
                        break;
                    case "02" :
                        lqjs = 1;
                        if ("0".equals(beanIn.getYXXX_SYZT())) {
                            lqsl = 1;
                        } else {
                            lqsl = 0;
                        }
                        break;
                    case "03" :
                        nwjs = 1;
                        if ("0".equals(beanIn.getYXXX_SYZT())) {
                            nwsl = 1;
                        } else {
                            nwsl = 0;
                        }
                        break;
                    case "04" :
                        xzjs = 1;
                        if ("0".equals(beanIn.getYXXX_SYZT())) {
                            xzsl = 1;
                        } else {
                            xzsl = 0;
                        }
                        break;
                    case "05" :
                        pmjs = 1;
                        if ("0".equals(beanIn.getYXXX_SYZT())) {
                            pmsl = 1;
                        } else {
                            pmsl = 0;
                        }
                        break;
                }
                StringBuffer strbuf_JSXX = new StringBuffer();

                strbuf_JSXX.append(" INSERT INTO ");
                strbuf_JSXX.append("     JSXX ");
                strbuf_JSXX.append(" ( ");
                strbuf_JSXX.append("     JSXX_JSID, ");//局数ID
                strbuf_JSXX.append("     JSXX_YHID, ");//用户ID
                strbuf_JSXX.append("     JSXX_LRJS, ");//狼人局数
                strbuf_JSXX.append("     JSXX_LRSL, ");//狼人胜利
                strbuf_JSXX.append("     JSXX_NWJS, ");//女巫局数
                strbuf_JSXX.append("     JSXX_NWSL, ");//女巫胜利
                strbuf_JSXX.append("     JSXX_XZJS, ");//预言家局数
                strbuf_JSXX.append("     JSXX_XZSL, ");//预言家胜利
                strbuf_JSXX.append("     JSXX_LQJS, ");//猎人局数
                strbuf_JSXX.append("     JSXX_LQSL, ");//猎人胜利
                strbuf_JSXX.append("     JSXX_PMJS, ");//平民局数
                strbuf_JSXX.append("     JSXX_PMSL, ");//平民胜利
                strbuf_JSXX.append("     JSXX_CJR, ");//创建人
                strbuf_JSXX.append("     JSXX_CJSJ, ");//创建时间
                strbuf_JSXX.append("     JSXX_GXR, ");//更新人
                strbuf_JSXX.append("     JSXX_GXSJ, ");//更新时间
                strbuf_JSXX.append("     JSXX_SCBZ  ");//删除标志（0：未删除，1：已删除）
                strbuf_JSXX.append(" ) ");
                strbuf_JSXX.append(" VALUES ");
                strbuf_JSXX.append(" ( ");
                strbuf_JSXX.append("     '"+jsId+"', ");
                strbuf_JSXX.append("     '"+beanIn.getYXXX_YHID()+"', ");
                strbuf_JSXX.append("     '"+lrjs+"', ");
                strbuf_JSXX.append("     '"+lrsl+"', ");
                strbuf_JSXX.append("     '"+nwjs+"', ");
                strbuf_JSXX.append("     '"+nwsl+"', ");
                strbuf_JSXX.append("     '"+xzjs+"', ");
                strbuf_JSXX.append("     '"+xzsl+"', ");
                strbuf_JSXX.append("     '"+lqjs+"', ");
                strbuf_JSXX.append("     '"+lqsl+"', ");
                strbuf_JSXX.append("     '"+pmjs+"', ");
                strbuf_JSXX.append("     '"+pmsl+"', ");
                strbuf_JSXX.append("     '"+beanIn.getYXXX_CJR()+"', ");
                strbuf_JSXX.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");
                strbuf_JSXX.append("     '"+beanIn.getYXXX_GXR()+"', ");
                strbuf_JSXX.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");
                strbuf_JSXX.append("     '0'  ");
                strbuf_JSXX.append(" ) ");
                count_JSXX = db.executeSQL(strbuf_JSXX);
            }

            int count = getSlxxCount(beanIn.getYXXX_YHID());
            if (count > 0) {
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
            } else {
                StringBuffer strbuf_SLXX = new StringBuffer();
                int ztsl = 0;
                if ("0".equals(beanIn.getYXXX_SYZT())) {
                    ztsl = 100;
                }
                strbuf_SLXX.append(" INSERT INTO ");
                strbuf_SLXX.append("     SLXX ");
                strbuf_SLXX.append(" ( ");
                strbuf_SLXX.append("     SLXX_SLID, ");//胜率ID
                strbuf_SLXX.append("     SLXX_YHID, ");//用户ID
                strbuf_SLXX.append("     SLXX_SCSL, ");//上次胜率
                strbuf_SLXX.append("     SLXX_ZTSL, ");//总胜率
                strbuf_SLXX.append("     SLXX_CJR, ");//创建人
                strbuf_SLXX.append("     SLXX_CJSJ, ");//创建时间
                strbuf_SLXX.append("     SLXX_GXR, ");//更新人
                strbuf_SLXX.append("     SLXX_GXSJ, ");//更新时间
                strbuf_SLXX.append("     SLXX_SCBZ  ");//删除标志（0：未删除，1：已删除）
                strbuf_SLXX.append(" ) ");
                strbuf_SLXX.append(" VALUES ");
                strbuf_SLXX.append(" ( ");
                strbuf_SLXX.append("     '"+slId+"', ");
                strbuf_SLXX.append("     '"+beanIn.getYXXX_YHID()+"', ");
                strbuf_SLXX.append("     '0', ");
                strbuf_SLXX.append("     '"+ztsl+"', ");
                strbuf_SLXX.append("     '"+beanIn.getYXXX_CJR()+"', ");
                strbuf_SLXX.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");
                strbuf_SLXX.append("     '"+beanIn.getYXXX_GXR()+"', ");
                strbuf_SLXX.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");
                strbuf_SLXX.append("     '0'  ");
                strbuf_SLXX.append(" ) ");
                count_SLXX = db.executeSQL(strbuf_SLXX);
            }

            //段位信息
            int jf = dwList.get(0).getDWDJ_UP();
            int count_dw = getDwxxCount(beanIn.getYXXX_YHID());
            if (count_dw > 0) {
                int grjf = getGrjf(beanIn.getYXXX_YHID()).getDWXX_GRJF();
                StringBuffer strbuf_DWXX = new StringBuffer();
                strbuf_DWXX.append(" UPDATE ");
                strbuf_DWXX.append("     DWXX ");
                strbuf_DWXX.append(" SET ");
                strbuf_DWXX.append("     DWXX_SCJF=DWXX_GRJF,");
                strbuf_DWXX.append("     DWXX_GRJF= " + getBJFS(grjf, beanIn.getYXXX_SYZT(), dwList) +",");
                strbuf_DWXX.append("     DWXX_GXR='").append(beanIn.getYXXX_GXR()).append("',");//更新人
                strbuf_DWXX.append("     DWXX_GXSJ=DATE_FORMAT(now(),'%Y%m%d %H:%i:%s')");//更新时间
                strbuf_DWXX.append(" WHERE 1=1");
                strbuf_DWXX.append(" AND DWXX_YHID='").append(beanIn.getYXXX_YHID()).append("'");
                count_DWXX = db.executeSQL(strbuf_DWXX);
            } else {
                StringBuffer strbuf_DWXX = new StringBuffer();

                strbuf_DWXX.append(" INSERT INTO ");
                strbuf_DWXX.append("     DWXX ");
                strbuf_DWXX.append(" ( ");
                strbuf_DWXX.append("     DWXX_YHID, ");//用户ID
                strbuf_DWXX.append("     DWXX_GRJF, ");//个人积分
                strbuf_DWXX.append("     DWXX_SCJF, ");//上次积分
                strbuf_DWXX.append("     DWXX_CJR, ");//创建人
                strbuf_DWXX.append("     DWXX_CJSJ, ");//创建时间
                strbuf_DWXX.append("     DWXX_GXR, ");//更新人
                strbuf_DWXX.append("     DWXX_GXSJ, ");//更新时间
                strbuf_DWXX.append("     DWXX_SCBZ  ");//删除标志（0：未删除，1：已删除）
                strbuf_DWXX.append(" ) ");
                strbuf_DWXX.append(" VALUES ");
                strbuf_DWXX.append(" ( ");
                strbuf_DWXX.append("     '"+beanIn.getYXXX_YHID()+"', ");
                if ("0".equals(beanIn.getYXXX_SYZT())) {
                    strbuf_DWXX.append("     "+jf+", ");
                } else {
                    strbuf_DWXX.append("     0, ");
                }
                strbuf_DWXX.append("     0, ");
                strbuf_DWXX.append("     '"+beanIn.getYXXX_CJR()+"', ");
                strbuf_DWXX.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");
                strbuf_DWXX.append("     '"+beanIn.getYXXX_GXR()+"', ");
                strbuf_DWXX.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");
                strbuf_DWXX.append("     '0'  ");
                strbuf_DWXX.append(" ) ");
                count_DWXX = db.executeSQL(strbuf_DWXX);
            }


            if (count_YXXX > 0 && count_JSXX > 0 && count_SLXX > 0 && count_DWXX > 0) {
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
    * 获取本局段位分数
    */
    private int getBJFS(int grjf, String syzt, List <Pojo_DWDJ> dwList) {
        int bjfs = 0;
        for (Pojo_DWDJ dj : dwList) {
            if ((grjf >= dj.getDWDJ_MIN()) && (grjf <= dj.getDWDJ_MAX())) {
                if ("0".equals(syzt)) {
                    bjfs = grjf + dj.getDWDJ_UP();
                } else {
                    bjfs = grjf - dj.getDWDJ_DOWN() >0 ? grjf - dj.getDWDJ_DOWN() : 0;
                }
            }
        }

        return bjfs;
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

    /**
    *
    * @FunctionName: getDwxxCount
    * @Description: 获取段位信息记录个数
    * @param beanIn
    * @throws Exception
    * @return int
    * @author czl
    * @date 2017-01-05
    */
   public int getDwxxCount(String yhid) throws Exception {
       int result = 0;

       try {
           StringBuffer strbuf = new StringBuffer();
           strbuf.append(" SELECT ");
           strbuf.append("     COUNT(D.DWXX_YHID) ");
           strbuf.append(" FROM ");
           strbuf.append("     DWXX D ");
           strbuf.append(" WHERE 1=1");
           strbuf.append(" AND D.DWXX_SCBZ = '0'");
           strbuf.append(" AND D.DWXX_YHID = '").append(yhid).append("'");

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
   * @FunctionName: getGrjf
   * @Description: 获取个人积分
   * @param beanIn
   * @throws Exception
   * @return int
   * @author czl
   * @date 2017-01-06
   */
  public Pojo_DWXX getGrjf(String yhid) throws Exception {
      Pojo_DWXX result;

      try {
          StringBuffer strbuf = new StringBuffer();
          strbuf.append(" SELECT ");
          strbuf.append("     D.DWXX_GRJF ");
          strbuf.append(" FROM ");
          strbuf.append("     DWXX D ");
          strbuf.append(" WHERE 1=1");
          strbuf.append(" AND D.DWXX_SCBZ = '0'");
          strbuf.append(" AND D.DWXX_YHID = '").append(yhid).append("'");

          ResultSetHandler<Pojo_DWXX> rs = new BeanHandler<Pojo_DWXX>(
                  Pojo_DWXX.class);

          result = db.queryForBeanHandler(strbuf, rs);
      } catch (Exception e) {
          MyLogger.error(this.getClass().getName(), e);
          throw e;
      } finally {

      }
      return result;
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
    * @date 2016-9-12
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
           strbuf.append(" WHERE L.LRXX_SCBZ = 0 ");

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

}