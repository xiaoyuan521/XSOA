package com.xsoa.pojo.basetable;

import java.io.Serializable;

public class Pojo_LTDJ implements Serializable {
	private static final long serialVersionUID = 1L;

	/** The value of the simple 级别ID property. */
    private String LTDJ_JBID;

	/** The value of the simple 级别ID property. */
	private String LTDJ_JBMC;

	/** The value of the simple 级别经验 property. */
	private Integer LTDJ_JBJY;

    /** The value of the simple 级别图片 property. */
    private String LTDJ_JBTP;

	/** The value of the simple 删除标志（0：未删除，1：已删除） property. */
	private String LTJD_SCBZ;

	/**
	 * Simple constructor of Abstract BeanPojo_LTDJ instances.
	 */
	public Pojo_LTDJ() {
	}

    public String getLTDJ_JBID() {
        return LTDJ_JBID;
    }

    public void setLTDJ_JBID(String lTDJ_JBID) {
        LTDJ_JBID = lTDJ_JBID;
    }

    public String getLTDJ_JBMC() {
        return LTDJ_JBMC;
    }

    public void setLTDJ_JBMC(String lTDJ_JBMC) {
        LTDJ_JBMC = lTDJ_JBMC;
    }

    public Integer getLTDJ_JBJY() {
        return LTDJ_JBJY;
    }

    public void setLTDJ_JBJY(Integer lTDJ_JBJY) {
        LTDJ_JBJY = lTDJ_JBJY;
    }

    public String getLTDJ_JBTP() {
        return LTDJ_JBTP;
    }

    public void setLTDJ_JBTP(String lTDJ_JBTP) {
        LTDJ_JBTP = lTDJ_JBTP;
    }

    public String getLTJD_SCBZ() {
        return LTJD_SCBZ;
    }

    public void setLTJD_SCBZ(String lTJD_SCBZ) {
        LTJD_SCBZ = lTJD_SCBZ;
    }

}
