package com.xsoa.pojo.basetable;

import java.io.Serializable;

public class Pojo_YGHT implements Serializable {
	private static final long serialVersionUID = 1L;

	/** The value of the simple 合同ID property. */
	private String YGHT_HTID;

	/** The value of the simple 合同编号 property. */
	private String YGHT_HTBH;

	/** The value of the simple 部门ID property. */
	private String YGHT_BMID;
	
	/** The value of the simple 员工ID property. */
	private String YGHT_YGID;

	/** The value of the simple 生效日期 property. */
	private String YGHT_SXRQ;

	/** The value of the simple 截止日期（失效日期） property. */
	private String YGHT_JZRQ;

	/** The value of the simple 合同年限 property. */
	private String YGHT_HTNX;

	/**
	 * The value of the simple
	 * 合同类型（0：临时合同，1：实习合同，2：正式合同，3：返聘合同，4：无固定期限劳动合同，5：固定期限劳动合同） property.
	 */
	private String YGHT_HTLX;

	/** The value of the simple 签订培训协议（0：否，1：是） property. */
	private String YGHT_QDPXXY;

	/** The value of the simple 是否上传附件（0：否，1：是） property. */
	private String YGHT_SFFJ;

	/** The value of the simple 备注信息 property. */
	private String YGHT_BZXX;
	
	/** The value of the simple 是否有效 property. */
	private String YGHT_SFYX;

	/** The value of the simple 创建人 property. */
	private String YGHT_CJR;

	/** The value of the simple 创建时间 property. */
	private String YGHT_CJSJ;

	/** The value of the simple 更新人 property. */
	private String YGHT_GXR;

	/** The value of the simple 更新时间 property. */
	private String YGHT_GXSJ;

	/** The value of the simple 删除标志（0：未删除，1：已删除） property. */
	private String YGHT_SCBZ;

	/**
	 * Simple constructor of Abstract BeanPojo_YGHT instances.
	 */
	public Pojo_YGHT() {
	}

	/**
	 * Return the value of the 合同ID column.
	 * 
	 * @return java.lang.String
	 */
	public String getYGHT_HTID() {
		return this.YGHT_HTID;
	}

	/**
	 * Return the value of the 合同编号 column.
	 * 
	 * @return java.lang.String
	 */
	public String getYGHT_HTBH() {
		return this.YGHT_HTBH;
	}

	/**
	 * Return the value of the 部门ID column.
	 * 
	 * @return java.lang.String
	 */
	public String getYGHT_BMID() {
		return this.YGHT_BMID;
	}
	
	/**
	 * Return the value of the 员工ID column.
	 * 
	 * @return java.lang.String
	 */
	public String getYGHT_YGID() {
		return this.YGHT_YGID;
	}

	/**
	 * Return the value of the 生效日期 column.
	 * 
	 * @return java.lang.String
	 */
	public String getYGHT_SXRQ() {
		return this.YGHT_SXRQ;
	}

	/**
	 * Return the value of the 截止日期（失效日期） column.
	 * 
	 * @return java.lang.String
	 */
	public String getYGHT_JZRQ() {
		return this.YGHT_JZRQ;
	}

	/**
	 * Return the value of the 合同年限 column.
	 * 
	 * @return java.lang.String
	 */
	public String getYGHT_HTNX() {
		return this.YGHT_HTNX;
	}

	/**
	 * Return the value of the
	 * 合同类型（0：临时合同，1：实习合同，2：正式合同，3：返聘合同，4：无固定期限劳动合同，5：固定期限劳动合同） column.
	 * 
	 * @return java.lang.String
	 */
	public String getYGHT_HTLX() {
		return this.YGHT_HTLX;
	}

	/**
	 * Return the value of the 签订培训协议（0：否，1：是） column.
	 * 
	 * @return java.lang.String
	 */
	public String getYGHT_QDPXXY() {
		return this.YGHT_QDPXXY;
	}

	/**
	 * Return the value of the 是否上传附件（0：否，1：是） column.
	 * 
	 * @return java.lang.String
	 */
	public String getYGHT_SFFJ() {
		return this.YGHT_SFFJ;
	}

	/**
	 * Return the value of the 备注信息 column.
	 * 
	 * @return java.lang.String
	 */
	public String getYGHT_BZXX() {
		return this.YGHT_BZXX;
	}

	/**
	 * Return the value of the 是否有效 column.
	 * 
	 * @return java.lang.String
	 */
	public String getYGHT_SFYX() {
		return this.YGHT_SFYX;
	}
	
	/**
	 * Return the value of the 创建人 column.
	 * 
	 * @return java.lang.String
	 */
	public String getYGHT_CJR() {
		return this.YGHT_CJR;
	}

	/**
	 * Return the value of the 创建时间 column.
	 * 
	 * @return java.lang.String
	 */
	public String getYGHT_CJSJ() {
		return this.YGHT_CJSJ;
	}

	/**
	 * Return the value of the 更新人 column.
	 * 
	 * @return java.lang.String
	 */
	public String getYGHT_GXR() {
		return this.YGHT_GXR;
	}

	/**
	 * Return the value of the 更新时间 column.
	 * 
	 * @return java.lang.String
	 */
	public String getYGHT_GXSJ() {
		return this.YGHT_GXSJ;
	}

	/**
	 * Return the value of the 删除标志（0：未删除，1：已删除） column.
	 * 
	 * @return java.lang.String
	 */
	public String getYGHT_SCBZ() {
		return this.YGHT_SCBZ;
	}

	/**
	 * Set the value of the 合同ID column.
	 * 
	 * @param String
	 *            YGHT_HTID
	 */
	public void setYGHT_HTID(String YGHT_HTID) {
		this.YGHT_HTID = YGHT_HTID;
	}

	/**
	 * Set the value of the 合同编号 column.
	 * 
	 * @param String
	 *            YGHT_HTBH
	 */
	public void setYGHT_HTBH(String YGHT_HTBH) {
		this.YGHT_HTBH = YGHT_HTBH;
	}

	/**
	 * Set the value of the 部门ID column.
	 * 
	 * @param String
	 *            YGHT_BMID
	 */
	public void setYGHT_BMID(String YGHT_BMID) {
		this.YGHT_BMID = YGHT_BMID;
	}

	
	/**
	 * Set the value of the 员工ID column.
	 * 
	 * @param String
	 *            YGHT_YGID
	 */
	public void setYGHT_YGID(String YGHT_YGID) {
		this.YGHT_YGID = YGHT_YGID;
	}

	/**
	 * Set the value of the 生效日期 column.
	 * 
	 * @param String
	 *            YGHT_SXRQ
	 */
	public void setYGHT_SXRQ(String YGHT_SXRQ) {
		this.YGHT_SXRQ = YGHT_SXRQ;
	}

	/**
	 * Set the value of the 截止日期（失效日期） column.
	 * 
	 * @param String
	 *            YGHT_JZRQ
	 */
	public void setYGHT_JZRQ(String YGHT_JZRQ) {
		this.YGHT_JZRQ = YGHT_JZRQ;
	}

	/**
	 * Set the value of the 合同年限 column.
	 * 
	 * @param String
	 *            YGHT_HTNX
	 */
	public void setYGHT_HTNX(String YGHT_HTNX) {
		this.YGHT_HTNX = YGHT_HTNX;
	}

	/**
	 * Set the value of the
	 * 合同类型（0：临时合同，1：实习合同，2：正式合同，3：返聘合同，4：无固定期限劳动合同，5：固定期限劳动合同） column.
	 * 
	 * @param String
	 *            YGHT_HTLX
	 */
	public void setYGHT_HTLX(String YGHT_HTLX) {
		this.YGHT_HTLX = YGHT_HTLX;
	}

	/**
	 * Set the value of the 签订培训协议（0：否，1：是） column.
	 * 
	 * @param String
	 *            YGHT_QDPXXY
	 */
	public void setYGHT_QDPXXY(String YGHT_QDPXXY) {
		this.YGHT_QDPXXY = YGHT_QDPXXY;
	}

	/**
	 * Set the value of the 是否上传附件（0：否，1：是） column.
	 * 
	 * @param String
	 *            YGHT_SFFJ
	 */
	public void setYGHT_SFFJ(String YGHT_SFFJ) {
		this.YGHT_SFFJ = YGHT_SFFJ;
	}

	/**
	 * Set the value of the 备注信息 column.
	 * 
	 * @param String
	 *            YGHT_BZXX
	 */
	public void setYGHT_BZXX(String YGHT_BZXX) {
		this.YGHT_BZXX = YGHT_BZXX;
	}

	/**
	 * Set the value of the 是否有效 column.
	 * 
	 * @param String
	 *            YGHT_SFYX
	 */
	public void setYGHT_SFYX(String YGHT_SFYX) {
		this.YGHT_SFYX = YGHT_SFYX;
	}
	
	/**
	 * Set the value of the 创建人 column.
	 * 
	 * @param String
	 *            YGHT_CJR
	 */
	public void setYGHT_CJR(String YGHT_CJR) {
		this.YGHT_CJR = YGHT_CJR;
	}

	/**
	 * Set the value of the 创建时间 column.
	 * 
	 * @param String
	 *            YGHT_CJSJ
	 */
	public void setYGHT_CJSJ(String YGHT_CJSJ) {
		this.YGHT_CJSJ = YGHT_CJSJ;
	}

	/**
	 * Set the value of the 更新人 column.
	 * 
	 * @param String
	 *            YGHT_GXR
	 */
	public void setYGHT_GXR(String YGHT_GXR) {
		this.YGHT_GXR = YGHT_GXR;
	}

	/**
	 * Set the value of the 更新时间 column.
	 * 
	 * @param String
	 *            YGHT_GXSJ
	 */
	public void setYGHT_GXSJ(String YGHT_GXSJ) {
		this.YGHT_GXSJ = YGHT_GXSJ;
	}

	/**
	 * Set the value of the 删除标志（0：未删除，1：已删除） column.
	 * 
	 * @param String
	 *            YGHT_SCBZ
	 */
	public void setYGHT_SCBZ(String YGHT_SCBZ) {
		this.YGHT_SCBZ = YGHT_SCBZ;
	}
}
