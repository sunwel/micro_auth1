package com.enlink.authcommon.exception;

import com.enlink.authcommon.constant.ResultStatusCodeEnum;

/**
 * 错误码消息异常
 * @author Timothy
 */
public class ErrorCodeMsgException extends RuntimeException {

	private String errorCode;
	private String errorMsg;

	public ErrorCodeMsgException(String errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public ErrorCodeMsgException(ResultStatusCodeEnum resultStatusCodeEnum) {
		super(resultStatusCodeEnum.getErrMsg());
		this.errorCode = resultStatusCodeEnum.getErrCode();
		this.errorMsg = resultStatusCodeEnum.getErrMsg();
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}
}
