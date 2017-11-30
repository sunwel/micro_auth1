package com.enlink.authcenter.business.exception;

/**
 * Created by someone on 2017/9/26.
 */
public class BussinessException extends RuntimeException {
    private String errCode;
    private String errMsg;

    public BussinessException(String msg){
        super(msg);
    }
    public BussinessException(String errCode,String errMsg){
        super();
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }
}
