package com.xsoa.pojo.common;

import java.io.Serializable;

public class Pojo_MESSAGE implements Serializable {

	private static final long serialVersionUID = 1L;

	/** The value of the simple 发消息方 property. */
	private String MESSAGE_FROM;

	/** The value of the simple 接消息方 property. */
	private String MESSAGE_TO;

	/** The value of the simple 消息时间 property. */
    private String MESSAGE_TIME;

    /** The value of the simple 消息内容 property. */
    private byte[] MESSAGE_CONTENT;

    /** The value of the simple 消息内容 property. */
    private String CONTENT;

    /** The value of the simple 消息类型 property. */
    private String MESSAGE_TYPE;

    /** The value of the simple 是否已读(0已读 1未读) property. */
    private String MESSAGE_READ;

    /** The value of the simple 未读消息数 property. */
    private String UNREAD_COUNT;

    /** The value of the simple 头像 property. */
    private String FACE;

    /** The value of the simple 名字 property. */
    private String NAME;

    public String getMESSAGE_FROM() {
        return MESSAGE_FROM;
    }

    public void setMESSAGE_FROM(String mESSAGE_FROM) {
        MESSAGE_FROM = mESSAGE_FROM;
    }

    public String getMESSAGE_TO() {
        return MESSAGE_TO;
    }

    public void setMESSAGE_TO(String mESSAGE_TO) {
        MESSAGE_TO = mESSAGE_TO;
    }

    public String getMESSAGE_TIME() {
        return MESSAGE_TIME;
    }

    public void setMESSAGE_TIME(String mESSAGE_TIME) {
        MESSAGE_TIME = mESSAGE_TIME;
    }

    public byte[] getMESSAGE_CONTENT() {
        return MESSAGE_CONTENT;
    }

    public void setMESSAGE_CONTENT(byte[] mESSAGE_CONTENT) {
        MESSAGE_CONTENT = mESSAGE_CONTENT;
    }

    public String getCONTENT() {
        return CONTENT;
    }

    public void setCONTENT(String cONTENT) {
        CONTENT = cONTENT;
    }

    public String getMESSAGE_TYPE() {
        return MESSAGE_TYPE;
    }

    public void setMESSAGE_TYPE(String mESSAGE_TYPE) {
        MESSAGE_TYPE = mESSAGE_TYPE;
    }

    public String getMESSAGE_READ() {
        return MESSAGE_READ;
    }

    public void setMESSAGE_READ(String mESSAGE_READ) {
        MESSAGE_READ = mESSAGE_READ;
    }

    public String getUNREAD_COUNT() {
        return UNREAD_COUNT;
    }

    public void setUNREAD_COUNT(String uNREAD_COUNT) {
        UNREAD_COUNT = uNREAD_COUNT;
    }

    public String getFACE() {
        return FACE;
    }

    public void setFACE(String fACE) {
        FACE = fACE;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String nAME) {
        NAME = nAME;
    }

}
