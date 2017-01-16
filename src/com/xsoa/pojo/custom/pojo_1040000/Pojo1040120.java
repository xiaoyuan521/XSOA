package com.xsoa.pojo.custom.pojo_1040000;

import com.xsoa.pojo.basetable.Pojo_JSXX;

public class Pojo1040120 extends Pojo_JSXX {
	private static final long serialVersionUID = 1L;

	/** The value of the simple 狼人角色 property. */
    private String LRJS;

	/** The value of the simple 拿牌率 property. */
	private Double NPL;

    /** The value of the simple 个人胜率 property. */
    private Double GRSL;

	/**
	 * Simple constructor of Abstract BeanPojo1040110 instances.
	 */
	public Pojo1040120() {
	}

    public String getLRJS() {
        return LRJS;
    }

    public void setLRJS(String lRJS) {
        LRJS = lRJS;
    }

    public Double getNPL() {
        return NPL;
    }

    public void setNPL(Double nPL) {
        NPL = nPL;
    }

    public Double getGRSL() {
        return GRSL;
    }

    public void setGRSL(Double gRSL) {
        GRSL = gRSL;
    }

}