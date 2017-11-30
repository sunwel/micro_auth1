package com.enlink.authcommon.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.enlink.authcommon.constant.ResultStatusCodeEnum;
import com.enlink.authcommon.exception.ErrorCodeMsgException;

/**
 * 带错误描述的返回结果包装类
 * @author Timothy
 */
public class CodeMsgResult<T> {
	private String errCode; // 0代表true
	private String errMsg;
	private T result;

	public CodeMsgResult() {
	}

	public CodeMsgResult(String errCode, String errMsg, T result) {
		this.errCode = errCode;
		this.errMsg = errMsg;
		this.result = result;
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

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	/**
	 * 是否为成功结果
	 * @return boolean
	 */
	@JSONField(serialize = false)
	public boolean isSuccess() {
		return "0".equals(errCode);
	}

	/**
	 * 构造正常返回结果
	 * @param result
	 * @return
	 */
	public static <T> CodeMsgResult<T> sucessResult(T result) {
		return new CodeMsgResult<T>(ResultStatusCodeEnum.OK.getErrCode(), ResultStatusCodeEnum.OK.getErrMsg(), result);
	}

	/**
	 * 构造失败返回结果
	 * @param errCode 错误码
	 * @param errMsg 错误信息
	 * @param <T> 成功结果数据类型
	 * @return 返回结果包装类
	 */
	public static <T> CodeMsgResult<T> failureResult(String errCode, String errMsg) {
		return new CodeMsgResult(errCode, errMsg, null);
	}

	/**
	 * 构造默认编码的失败返回结果
	 * @param errMsg 失败信息
	 * @param <T> 成功结果数据类型
	 * @return 返回结果包装类
	 */
	public static <T> CodeMsgResult<T> defaultCodeFailureResult(String errMsg) {
		return new CodeMsgResult(ResultStatusCodeEnum.SYSTEM_ERR.getErrCode(), errMsg, null);
	}

	/**
	 * 构造失败返回结果
	 * @param resultStatusCode 错误码消息枚举
	 * @param <T> 成功结果数据类型
	 * @return 返回结果包装类
	 */
	public static <T> CodeMsgResult<T> failureResult(ResultStatusCodeEnum resultStatusCode) {
		return new CodeMsgResult<>(resultStatusCode.getErrCode(), resultStatusCode.getErrMsg(), null);
	}

	/**
	 * 构造失败返回结果
	 * @param exception 错误码消息异常
	 * @param <T> 成功结果数据类型
	 * @return 返回结果包装类
	 */
	public static <T> CodeMsgResult<T> failureResult(ErrorCodeMsgException exception) {
		return new CodeMsgResult<>(exception.getErrorCode(), exception.getErrorMsg(), null);
	}
}
