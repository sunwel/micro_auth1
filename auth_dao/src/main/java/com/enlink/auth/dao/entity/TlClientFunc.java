package com.enlink.auth.dao.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 客户端功能权限表
 * </p>
 *
 * @author idea
 * @since 2017-01-01
 */
@TableName("tl_client_func")
public class TlClientFunc extends Model<TlClientFunc> {

    private static final long serialVersionUID = 1L;

    /**
     * 权限ID
     */
	@TableId(value="cf_id", type= IdType.AUTO)
	private Integer cfId;
    /**
     * 客户端ID
     */
	@TableField("client_id")
	private String clientId;
    /**
     * 功能ID
     */
	@TableField("func_id")
	private Integer funcId;


	public Integer getCfId() {
		return cfId;
	}

	public TlClientFunc setCfId(Integer cfId) {
		this.cfId = cfId;
		return this;
	}

	public String getClientId() {
		return clientId;
	}

	public TlClientFunc setClientId(String clientId) {
		this.clientId = clientId;
		return this;
	}

	public Integer getFuncId() {
		return funcId;
	}

	public TlClientFunc setFuncId(Integer funcId) {
		this.funcId = funcId;
		return this;
	}

	public static final String CF_ID = "cf_id";

	public static final String CLIENT_ID = "client_id";

	public static final String FUNC_ID = "func_id";

	@Override
	protected Serializable pkVal() {
		return this.cfId;
	}

	@Override
	public String toString() {
		return "TlClientFunc{" +
			"cfId=" + cfId +
			", clientId=" + clientId +
			", funcId=" + funcId +
			"}";
	}
}
