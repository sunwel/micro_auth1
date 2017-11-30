package com.enlink.authclient;

import com.enlink.authcommon.model.CodeMsgResult;

/**
 * 客户端授权中心登录服务接口类
 * @author Timothy
 */
public interface IClientLoginService {

	/** 客户端向授权中心发起登录请求 */
	CodeMsgResult<String> login();
}
