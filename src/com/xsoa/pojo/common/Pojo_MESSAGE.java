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
    private String MESSAGE_CONTENT;

    /** The value of the simple 消息类型 property. */
    private String MESSAGE_TYPE;

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

    public String getMESSAGE_CONTENT() {
        return MESSAGE_CONTENT;
    }

    public void setMESSAGE_CONTENT(String mESSAGE_CONTENT) {
        MESSAGE_CONTENT = mESSAGE_CONTENT;
    }

    public String getMESSAGE_TYPE() {
        return MESSAGE_TYPE;
    }

    public void setMESSAGE_TYPE(String mESSAGE_TYPE) {
        MESSAGE_TYPE = mESSAGE_TYPE;
    }

}
