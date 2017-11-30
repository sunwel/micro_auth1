package com.enlink.authserver;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.enlink.authcommon.constant.ResultStatusCodeEnum;
import com.enlink.authcommon.exception.ErrorCodeMsgException;
import com.enlink.authcommon.model.CodeMsgResult;

/**
 * restful接口的统一异常处理
 * @author Timothy
 */
@RestControllerAdvice
public class GlobalControllerExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public CodeMsgResult<Object> jsonErrorHandler(HttpServletRequest req, Exception e) throws Exception {
		if (e instanceof ErrorCodeMsgException) {
			ErrorCodeMsgException codeMsgException = (ErrorCodeMsgException) e;
			logger.warn("捕捉到错误码消息异常！code={}, msg={}", codeMsgException.getErrorCode(), codeMsgException.getErrorMsg());
			return CodeMsgResult.failureResult(codeMsgException);
		} else if (e instanceof AuthorizationException) {
			logger.warn("捕捉到访问权限异常！" + e.getMessage(), e);
			return CodeMsgResult.failureResult(ResultStatusCodeEnum.PERMISSION_DENIED);
		} else {
			logger.error(e.getMessage(), e);
			return CodeMsgResult.defaultCodeFailureResult(e.getMessage());
		}
	}
}
