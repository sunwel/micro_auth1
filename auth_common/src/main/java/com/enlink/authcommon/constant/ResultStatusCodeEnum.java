package com.enlink.authcommon.constant;

/**
 * 结果状态吗枚举类
 * @author idea
 */
public enum ResultStatusCodeEnum {

	OK("0", "OK"),
	SYSTEM_ERR("-1", "系统异常"),
	CLIENTID_INVALID("-2", "客户端ID错误"),
	CLIENTSIGN_INVALID("-3", "客户端签名错误"),
	ALGID_INVALID("-4", "算法ID错误"),
	REQTIME_INVALID("-5", "请求时间戳错误"),
	SALT_INVALID("-6", "盐值错误"),
	REQPARAM_INVALID("-7", "请求参数错误"),

	MICROTOKEN_INVALID("-8", "微服务Token无效"),
	MICROTOKEN_EXPIRED("-9", "微服务Token已过期"),

	PERMISSION_DENIED("-10", "没有访问权限");

	// 成员变量
	private String errCode;
	private String errMsg;

	// 构造方法
	ResultStatusCodeEnum(String errCode, String errMsg) {
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
}
