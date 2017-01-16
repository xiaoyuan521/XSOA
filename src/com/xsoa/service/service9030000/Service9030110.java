package com.xsoa.service.service9030000;


import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsoa.pojo.custom.pojo_9030000.Pojo9030110;

public class Service9030110 extends BaseService {
	private DBManager db = null;

	public Service9030110() {
		db = new DBManager();
	}
	/**
	 *
	 * @FunctionName: getYhInfo
	 * @Description: 获取用户信息
	 * @param ygid
	 * @throws Exception
	 * @return Pojo9030110
	 * @author czl
	 * @date 2016-9-18
	 */
	public Pojo9030110 getYhInfo(String ygid) throws Exception {
		Pojo9030110 result = null;
		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT Y.YHXX_YHID,");// 用户ID
			sql.append("     Y.YHXX_YHMC, ");//用户名称
			sql.append("     B.BMXX_BMMC AS BMMC, ");//部门名称
			sql.append("     J.YHJS_JSMC AS JSMC, ");//出生日期
			sql.append("     Y.YHXX_TEL, ");//性别（0：男 1：女）
			sql.append("     Y.YHXX_EMAIL, ");//婚姻状况（未婚/已婚）
			sql.append("     Y.YHXX_GRZP ");//个人照片
			sql.append("   FROM YHXX Y LEFT JOIN BMXX B ON Y.YHXX_BMID = B.BMXX_BMID LEFT JOIN YHJS J ON Y.YHXX_JSID = J.YHJS_JSID");
			sql.append(" WHERE 1=1 ");
			sql.append(" AND Y.YHXX_SCBZ = '0'");
			if (MyStringUtils.isNotBlank(ygid)) {
				sql.append(" AND Y.YHXX_YHID ='").append(ygid).append("'");
			}
			ResultSetHandler<Pojo9030110> rsh = new BeanHandler<Pojo9030110>(
					Pojo9030110.class);
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
	 * @FunctionName: updateYhInfo
	 * @Description: 修改用户信息
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author czl
	 * @date 2016-9-18
	 */
	public boolean updateYhInfo(Pojo9030110 beanIn) throws Exception {
		boolean result = false;
		int count_YHXX = 0;
		try {
			db.openConnection();
			db.beginTran();
			/* 更新YGXX表数据Start */
			StringBuffer strbuf_YHXX = new StringBuffer();
			strbuf_YHXX.append(" UPDATE ");
			strbuf_YHXX.append("     YHXX ");
			strbuf_YHXX.append(" SET ");
			strbuf_YHXX.append("     YHXX_YHMC='").append(beanIn.getYHXX_YHMC()).append("',");//用户名称
			strbuf_YHXX.append("     YHXX_TEL='").append(beanIn.getYHXX_TEL()).append("',");//tel
			strbuf_YHXX.append("     YHXX_EMAIL='").append(beanIn.getYHXX_EMAIL()).append("',");//email
			strbuf_YHXX.append("     YHXX_GRZP='").append(beanIn.getYHXX_GRZP()).append("',");//个人照片
			strbuf_YHXX.append("     YHXX_GXR='").append(beanIn.getYHXX_GXR()).append("',");//更新人
			strbuf_YHXX.append("     YHXX_GXSJ=DATE_FORMAT(now(),'%Y%m%d %H:%i:%s')");//更新时间
			strbuf_YHXX.append(" WHERE 1=1");
			strbuf_YHXX.append(" AND YHXX_YHID='").append(beanIn.getYHXX_YHID()).append("'");//用户ID
			count_YHXX = db.executeSQL(strbuf_YHXX);
			/* 更新YGXX表数据End */
			if (count_YHXX  > 0) {
				db.commit();
				result = true;
			} else {
				db.rollback();
			}
		} catch (Exception e) {
			db.rollback();
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return result;
	}
}