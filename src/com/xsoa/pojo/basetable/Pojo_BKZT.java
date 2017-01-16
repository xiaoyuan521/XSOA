package com.xsoa.pojo.basetable;

import java.io.Serializable;

public class Pojo_BKZT implements Serializable {
	private static final long serialVersionUID = 1L;

	/** The value of the simple 主题ID property. */
    private String BKZT_ZTID;

	/** The value of the simple 版块ID property. */
	private String BKZT_BKID;

	/** The value of the simple 主题名称 property. */
	private String BKZT_ZTMC;

	/** The value of the simple 主题内容 property. */
    private byte[] BKZT_ZTNR;

	/** The value of the simple 发表人 property. */
	private String BKZT_FBR;

	/** The value of the simple 发表时间 property. */
    private String BKZT_FBSJ;

    /** The value of the simple 主题类别 property. */
    private String BKZT_ZTLB;

	/** The value of the simple 创建人 property. */
    private String BKZT_CJR;

	/** The value of the simple 创建时间 property. */
	private String BKZT_CJSJ;

	/** The value of the simple 更新人 property. */
	private String BKZT_GXR;

	/** The value of the simple 更新时间 property. */
	private String BKZT_GXSJ;

	/** The value of the simple 删除标志（0：未删除，1：已删除） property. */
	private String BKZT_SCBZ;

	/**
	 * Simple constructor of Abstract BeanPojo_BKZT instances.
	 */
	public Pojo_BKZT() {
	}

    public String getBKZT_ZTID() {
        return BKZT_ZTID;
    }

    public void setBKZT_ZTID(String bKZT_ZTID) {
        BKZT_ZTID = bKZT_ZTID;
    }

    public String getBKZT_BKID() {
        return BKZT_BKID;
    }

    public void setBKZT_BKID(String bKZT_BKID) {
        BKZT_BKID = bKZT_BKID;
    }

    public String getBKZT_ZTMC() {
        return BKZT_ZTMC;
    }

    public void setBKZT_ZTMC(String bKZT_ZTMC) {
        BKZT_ZTMC = bKZT_ZTMC;
    }


    public byte[] getBKZT_ZTNR() {
        return BKZT_ZTNR;
    }

    public void setBKZT_ZTNR(byte[] bKZT_ZTNR) {
        BKZT_ZTNR = bKZT_ZTNR;
    }

    public String getBKZT_FBR() {
        return BKZT_FBR;
    }

    public void setBKZT_FBR(String bKZT_FBR) {
        BKZT_FBR = bKZT_FBR;
    }

    public String getBKZT_FBSJ() {
        return BKZT_FBSJ;
    }

    public void setBKZT_FBSJ(String bKZT_FBSJ) {
        BKZT_FBSJ = bKZT_FBSJ;
    }

    public String getBKZT_ZTLB() {
        return BKZT_ZTLB;
    }

    public void setBKZT_ZTLB(String bKZT_ZTLB) {
        BKZT_ZTLB = bKZT_ZTLB;
    }

    public String getBKZT_CJR() {
        return BKZT_CJR;
    }

    public void setBKZT_CJR(String bKZT_CJR) {
        BKZT_CJR = bKZT_CJR;
    }

    public String getBKZT_CJSJ() {
        return BKZT_CJSJ;
    }

    public void setBKZT_CJSJ(String bKZT_CJSJ) {
        BKZT_CJSJ = bKZT_CJSJ;
    }

    public String getBKZT_GXR() {
        return BKZT_GXR;
    }

    public void setBKZT_GXR(String bKZT_GXR) {
        BKZT_GXR = bKZT_GXR;
    }

    public String getBKZT_GXSJ() {
        return BKZT_GXSJ;
    }

    public void setBKZT_GXSJ(String bKZT_GXSJ) {
        BKZT_GXSJ = bKZT_GXSJ;
    }

    public String getBKZT_SCBZ() {
        return BKZT_SCBZ;
    }

    public void setBKZT_SCBZ(String bKZT_SCBZ) {
        BKZT_SCBZ = bKZT_SCBZ;
    }

}
