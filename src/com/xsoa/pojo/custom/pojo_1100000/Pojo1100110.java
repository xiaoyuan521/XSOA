package com.xsoa.pojo.custom.pojo_1100000;

import com.xsoa.pojo.basetable.Pojo_LTBK;

public class Pojo1100110 extends Pojo_LTBK {
	private static final long serialVersionUID = 1L;

	/** The value of the simple 版主名称 property. */
	private String BZMC;

	/** The value of the simple 可见部门名称 property. */
    private String KJMC;

	/**
	 * Simple constructor of Abstract BeanPojo1100110 instances.
	 */
	public Pojo1100110() {
	}

    public String getBZMC() {
        return BZMC;
    }

    public void setBZMC(String bZMC) {
        BZMC = bZMC;
    }

    public String getKJMC() {
        return KJMC;
    }

    public void setKJMC(String kJMC) {
        KJMC = kJMC;
    }

}