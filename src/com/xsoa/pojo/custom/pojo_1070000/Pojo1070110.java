package com.xsoa.pojo.custom.pojo_1070000;

import com.xsoa.pojo.basetable.Pojo_GZZF;

public class Pojo1070110 extends Pojo_GZZF {
	private static final long serialVersionUID = 1L;

	/** The value of the simple 部门名称 property. */
	private String BMMC;
	
	/** The value of the simple 员工姓名 property. */
	private String YGXM;

	/**
	 * Simple constructor of Abstract BeanPojo1070110 instances.
	 */
	public Pojo1070110() {
	}

	/**
	 * Return the value of the 部门名称 column.
	 * 
	 * @return java.lang.String
	 */
	public String getBMMC() {
		return this.BMMC;
	}

	/**
	 * Return the value of the 员工姓名 column.
	 * 
	 * @return java.lang.String
	 */
	public String getYGXM() {
		return this.YGXM;
	}
	
	/**
	 * Set the value of the 部门名称 column.
	 * 
	 * @param String
	 *            BMMC
	 */
	public void setBMMC(String BMMC) {
		this.BMMC = BMMC;
	}
	
	/**
	 * Set the value of the 员工姓名 column.
	 * 
	 * @param String
	 *            YGXM
	 */
	public void setYGXM(String YGXM) {
		this.YGXM = YGXM;
	}
}