package com.xsoa.pojo.custom.pojo_9010000;

import com.xsoa.pojo.basetable.Pojo_YHXX;

public class Pojo9010110 extends Pojo_YHXX {
	private static final long serialVersionUID = 1L;

	/** The value of the simple 角色名称 property. */
	private String YHJS_JSMC;

	/** The value of the simple 锁定状态 property. */
	private String SDZT;

	/** The value of the simple 删除标志（0：未删除，1：已删除） property. */
	private String SCBZ;

	/**
	 * Simple constructor of Abstract BeanPojo9010110 instances.
	 */
	public Pojo9010110() {
	}

	/**
	 * Return the value of the 角色名称 column.
	 * 
	 * @return java.lang.String
	 */
	public String getYHJS_JSMC() {
		return this.YHJS_JSMC;
	}

	/**
	 * Return the value of the 锁定状态 column.
	 * 
	 * @return java.lang.String
	 */
	public String getSDZT() {
		return this.SDZT;
	}

	/**
	 * Return the value of the 删除标志（0：未删除，1：已删除） column.
	 * 
	 * @return java.lang.String
	 */
	public String getSCBZ() {
		return this.SCBZ;
	}

	/**
	 * Set the value of the 角色名称 column.
	 * 
	 * @param String
	 *            YHJS_JSMC
	 */
	public void setYHJS_JSMC(String YHJS_JSMC) {
		this.YHJS_JSMC = YHJS_JSMC;
	}

	/**
	 * Set the value of the 锁定状态 column.
	 * 
	 * @param String
	 *            SDZT
	 */
	public void setSDZT(String SDZT) {
		this.SDZT = SDZT;
	}

	/**
	 * Set the value of the 删除标志（0：未删除，1：已删除） column.
	 * 
	 * @param String
	 *            SCBZ
	 */
	public void setSCBZ(String SCBZ) {
		this.SCBZ = SCBZ;
	}
}