package com.xsoa.pojo.common;

import java.io.Serializable;

/**
 * @ClassName: PROCESS_LOGS
 * @Package:com.xsoa.pojo.common
 * @Description: 审批流程共通类
 * @author czl
 * @date 2015-03-18
 * @update
 */
public class Pojo_PROCESS_LOGS implements Serializable {

	private static final long serialVersionUID = 1L;

	/** The value of the simple 申请ID property. */
	private String SQID;

	/** The value of the simple 日志类型 property. */
	private String LOGTYPE;
	
	/** The value of the simple 操作时间 property. */
	private String CZSJ;
	
	/** The value of the simple 操作类型 property. */
	private String CZLX;
	
	/** The value of the simple 操作内容 property. */
	private String CZNR;
	
	/** The value of the simple 操作人 property. */
	private String CZR;

	/**
	 * Return the value of the 申请ID column.
	 * 
	 * @return java.lang.String
	 */
	public String getSQID() {
		return this.SQID;
	}

	/**
	 * Return the value of the 日志类型 column.
	 * 
	 * @return java.lang.String
	 */
	public String getLOGTYPE() {
		return this.LOGTYPE;
	}
	
	/**
	 * Return the value of the 操作时间 column.
	 * 
	 * @return java.lang.String
	 */
	public String getCZSJ() {
		return this.CZSJ;
	}
	
	/**
	 * Return the value of the 操作类型 column.
	 * 
	 * @return java.lang.String
	 */
	public String getCZLX() {
		return this.CZLX;
	}
	
	/**
	 * Return the value of the 操作内容 column.
	 * 
	 * @return java.lang.String
	 */
	public String getCZNR() {
		return this.CZNR;
	}
	
	/**
	 * Return the value of the 操作人 column.
	 * 
	 * @return java.lang.String
	 */
	public String getCZR() {
		return this.CZR;
	}

	/**
	 * Set the value of the 申请ID column.
	 * 
	 * @param String
	 *            SQID
	 */
	public void setSQID(String SQID) {
		this.SQID = SQID;
	}

	/**
	 * Set the value of the 日志类型 column.
	 * 
	 * @param String
	 *            LOGTYPE
	 */
	public void setLOGTYPE(String LOGTYPE) {
		this.LOGTYPE = LOGTYPE;
	}
	
	/**
	 * Set the value of the 操作时间 column.
	 * 
	 * @param String
	 *            CZSJ
	 */
	public void setCZSJ(String CZSJ) {
		this.CZSJ = CZSJ;
	}
	
	/**
	 * Set the value of the 操作类型 column.
	 * 
	 * @param String
	 *            CZLX
	 */
	public void setCZLX(String CZLX) {
		this.CZLX = CZLX;
	}
	
	/**
	 * Set the value of the 操作内容 column.
	 * 
	 * @param String
	 *            CZNR
	 */
	public void setCZNR(String CZNR) {
		this.CZNR = CZNR;
	}
	
	/**
	 * Set the value of the 操作人 column.
	 * 
	 * @param String
	 *            CZR
	 */
	public void setCZR(String CZR) {
		this.CZR = CZR;
	}

}
