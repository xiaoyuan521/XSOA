package com.xsoa.pojo.basetable;

import java.io.Serializable;

public class Pojo_LRXX implements Serializable {
	private static final long serialVersionUID = 1L;

	/** The value of the simple 狼人ID property. */
	private String LRXX_LRID;

	/** The value of the simple 狼人名称 property. */
	private String LRXX_LRMC;


	/**
	 * Simple constructor of Abstract BeanPojo_BFRC instances.
	 */
	public Pojo_LRXX() {
	}


    public String getLRXX_LRID() {
        return LRXX_LRID;
    }


    public void setLRXX_LRID(String lRXX_LRID) {
        LRXX_LRID = lRXX_LRID;
    }


    public String getLRXX_LRMC() {
        return LRXX_LRMC;
    }


    public void setLRXX_LRMC(String lRXX_LRMC) {
        LRXX_LRMC = lRXX_LRMC;
    }

}
