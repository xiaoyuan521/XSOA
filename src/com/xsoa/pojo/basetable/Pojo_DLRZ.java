package com.xsoa.pojo.basetable;

import java.io.Serializable;

public class Pojo_DLRZ implements Serializable {
	private static final long serialVersionUID = 1L;

	/** The value of the simple 登录ID property. */
	private String DLRZ_DLID;

	/** The value of the simple 用户名称 property. */
	private String DLRZ_YHMC;

	/** The value of the simple 用户ID property. */
	private String DLRZ_YHID;

	/** The value of the simple 登录时间 property. */
	private String DLRZ_DLSJ;

	/** The value of the simple 登录IP property. */
	private String DLRZ_DLIP;

	/**
	 * Simple constructor of Abstract BeanPojo_DLRZ instances.
	 */
	public Pojo_DLRZ() {
	}

	/**
	 * Return the value of the 登录ID column.
	 * 
	 * @return java.lang.String
	 */
	public String getDLRZ_DLID() {
		return this.DLRZ_DLID;
	}

	/**
	 * Return the value of the 用户名称 column.
	 * 
	 * @return java.lang.String
	 */
	public String getDLRZ_YHMC() {
		return this.DLRZ_YHMC;
	}

	/**
	 * Return the value of the 用户ID column.
	 * 
	 * @return java.lang.String
	 */
	public String getDLRZ_YHID() {
		return this.DLRZ_YHID;
	}

	/**
	 * Return the value of the 登录时间 column.
	 * 
	 * @return java.lang.String
	 */
	public String getDLRZ_DLSJ() {
		return this.DLRZ_DLSJ;
	}

	/**
	 * Return the value of the 登录IP column.
	 * 
	 * @return java.lang.String
	 */
	public String getDLRZ_DLIP() {
		return this.DLRZ_DLIP;
	}

	/**
	 * Set the value of the 登录ID column.
	 * 
	 * @param String
	 *            DLRZ_DLID
	 */
	public void setDLRZ_DLID(String DLRZ_DLID) {
		this.DLRZ_DLID = DLRZ_DLID;
	}

	/**
	 * Set the value of the 用户名称 column.
	 * 
	 * @param String
	 *            DLRZ_YHMC
	 */
	public void setDLRZ_YHMC(String DLRZ_YHMC) {
		this.DLRZ_YHMC = DLRZ_YHMC;
	}

	/**
	 * Set the value of the 用户ID column.
	 * 
	 * @param String
	 *            DLRZ_YHID
	 */
	public void setDLRZ_YHID(String DLRZ_YHID) {
		this.DLRZ_YHID = DLRZ_YHID;
	}

	/**
	 * Set the value of the 登录时间 column.
	 * 
	 * @param String
	 *            DLRZ_DLSJ
	 */
	public void setDLRZ_DLSJ(String DLRZ_DLSJ) {
		this.DLRZ_DLSJ = DLRZ_DLSJ;
	}

	/**
	 * Set the value of the 登录IP column.
	 * 
	 * @param String
	 *            DLRZ_DLIP
	 */
	public void setDLRZ_DLIP(String DLRZ_DLIP) {
		this.DLRZ_DLIP = DLRZ_DLIP;
	}

}
