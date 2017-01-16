package com.xsoa.pojo.basetable;

import java.io.Serializable;

public class Pojo_ZTHF implements Serializable {
	private static final long serialVersionUID = 1L;

	/** The value of the simple 回复ID property. */
    private String ZTHF_HFID;

	/** The value of the simple 主题ID property. */
	private String ZTHF_ZTID;

	/** The value of the simple 回复人员 property. */
	private String ZTHF_HFRY;

	/** The value of the simple 回复时间 property. */
	private String ZTHF_HFSJ;

	/** The value of the simple 回复内容 property. */
    private byte[] ZTHF_HFNR;

	/** The value of the simple 创建人 property. */
    private String ZTHF_CJR;

	/** The value of the simple 创建时间 property. */
	private String ZTHF_CJSJ;

	/** The value of the simple 更新人 property. */
	private String ZTHF_GXR;

	/** The value of the simple 更新时间 property. */
	private String ZTHF_GXSJ;

	/** The value of the simple 删除标志（0：未删除，1：已删除） property. */
	private String ZTHF_SCBZ;

	/**
	 * Simple constructor of Abstract BeanPojo_ZTHF instances.
	 */
	public Pojo_ZTHF() {
	}

    public String getZTHF_HFID() {
        return ZTHF_HFID;
    }

    public void setZTHF_HFID(String zTHF_HFID) {
        ZTHF_HFID = zTHF_HFID;
    }

    public String getZTHF_ZTID() {
        return ZTHF_ZTID;
    }

    public void setZTHF_ZTID(String zTHF_ZTID) {
        ZTHF_ZTID = zTHF_ZTID;
    }

    public String getZTHF_HFRY() {
        return ZTHF_HFRY;
    }

    public void setZTHF_HFRY(String zTHF_HFRY) {
        ZTHF_HFRY = zTHF_HFRY;
    }

    public String getZTHF_HFSJ() {
        return ZTHF_HFSJ;
    }

    public void setZTHF_HFSJ(String zTHF_HFSJ) {
        ZTHF_HFSJ = zTHF_HFSJ;
    }

    public byte[] getZTHF_HFNR() {
        return ZTHF_HFNR;
    }

    public void setZTHF_HFNR(byte[] zTHF_HFNR) {
        ZTHF_HFNR = zTHF_HFNR;
    }

    public String getZTHF_CJR() {
        return ZTHF_CJR;
    }

    public void setZTHF_CJR(String zTHF_CJR) {
        ZTHF_CJR = zTHF_CJR;
    }

    public String getZTHF_CJSJ() {
        return ZTHF_CJSJ;
    }

    public void setZTHF_CJSJ(String zTHF_CJSJ) {
        ZTHF_CJSJ = zTHF_CJSJ;
    }

    public String getZTHF_GXR() {
        return ZTHF_GXR;
    }

    public void setZTHF_GXR(String zTHF_GXR) {
        ZTHF_GXR = zTHF_GXR;
    }

    public String getZTHF_GXSJ() {
        return ZTHF_GXSJ;
    }

    public void setZTHF_GXSJ(String zTHF_GXSJ) {
        ZTHF_GXSJ = zTHF_GXSJ;
    }

    public String getZTHF_SCBZ() {
        return ZTHF_SCBZ;
    }

    public void setZTHF_SCBZ(String zTHF_SCBZ) {
        ZTHF_SCBZ = zTHF_SCBZ;
    }

}
