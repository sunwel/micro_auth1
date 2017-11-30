package com.enlink.auth.dao.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 客户端信息表
 * </p>
 *
 * @author idea
 * @since 2017-01-01
 */
@TableName("tl_client")
public class TlClient extends Model<TlClient> {

    private static final long serialVersionUID = 1L;

    /**
     * 客户端ID
     */
    @TableId("client_id")
	private String clientId;
    /**
     * 客户端登录key1
     */
	@TableField("client_key1")
	private String clientKey1;
    /**
     * 客户端登录key2；多个的目的是为了方便平滑的更新登录key
     */
	@TableField("client_key2")
	private String clientKey2;
    /**
     * 客户端类型；99:超级管理员; 1:内部应用; 2:内部服务
     */
	@TableField("client_type")
	private Integer clientType;
    /**
     * 描述信息
     */
	@TableField("client_desc")
	private String clientDesc;
    /**
     * 状态；1:有效; 0:无效
     */
	@TableField("client_status")
	private Integer clientStatus;


	public String getClientId() {
		return clientId;
	}

	public TlClient setClientId(String clientId) {
		this.clientId = clientId;
		return this;
	}

	public String getClientKey1() {
		return clientKey1;
	}

	public TlClient setClientKey1(String clientKey1) {
		this.clientKey1 = clientKey1;
		return this;
	}

	public String getClientKey2() {
		return clientKey2;
	}

	public TlClient setClientKey2(String clientKey2) {
		this.clientKey2 = clientKey2;
		return this;
	}

	public Integer getClientType() {
		return clientType;
	}

	public TlClient setClientType(Integer clientType) {
		this.clientType = clientType;
		return this;
	}

	public String getClientDesc() {
		return clientDesc;
	}

	public TlClient setClientDesc(String clientDesc) {
		this.clientDesc = clientDesc;
		return this;
	}

	public Integer getClientStatus() {
		return clientStatus;
	}

	public TlClient setClientStatus(Integer clientStatus) {
		this.clientStatus = clientStatus;
		return this;
	}

	public static final String CLIENT_ID = "client_id";

	public static final String CLIENT_KEY1 = "client_key1";

	public static final String CLIENT_KEY2 = "client_key2";

	public static final String CLIENT_TYPE = "client_type";

	public static final String CLIENT_DESC = "client_desc";

	public static final String CLIENT_STATUS = "client_status";

	@Override
	protected Serializable pkVal() {
		return this.clientId;
	}

	@Override
	public String toString() {
		return "TlClient{" +
			"clientId=" + clientId +
			", clientKey1=" + clientKey1 +
			", clientKey2=" + clientKey2 +
			", clientType=" + clientType +
			", clientDesc=" + clientDesc +
			", clientStatus=" + clientStatus +
			"}";
	}
}
