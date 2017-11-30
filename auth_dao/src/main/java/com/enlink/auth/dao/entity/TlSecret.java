package com.enlink.auth.dao.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 私钥表
 * </p>
 *
 * @author idea
 * @since 2017-01-01
 */
@TableName("tl_secret")
public class TlSecret extends Model<TlSecret> {

    private static final long serialVersionUID = 1L;

    /**
     * 私钥ID
     */
	@TableId(value="secret_id", type= IdType.AUTO)
	private Integer secretId;
    /**
     * 私钥
     */
	private String secret;
    /**
     * 状态；1:有效; 0:无效
     */
	private Integer status;


	public Integer getSecretId() {
		return secretId;
	}

	public TlSecret setSecretId(Integer secretId) {
		this.secretId = secretId;
		return this;
	}

	public String getSecret() {
		return secret;
	}

	public TlSecret setSecret(String secret) {
		this.secret = secret;
		return this;
	}

	public Integer getStatus() {
		return status;
	}

	public TlSecret setStatus(Integer status) {
		this.status = status;
		return this;
	}

	public static final String SECRET_ID = "secret_id";

	public static final String SECRET = "secret";

	public static final String STATUS = "status";

	@Override
	protected Serializable pkVal() {
		return this.secretId;
	}

	@Override
	public String toString() {
		return "TlSecret{" +
			"secretId=" + secretId +
			", secret=" + secret +
			", status=" + status +
			"}";
	}
}
