package com.enlink.auth.dao.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 微服务功能表
 * </p>
 *
 * @author idea
 * @since 2017-01-01
 */
@TableName("tl_microservice_func")
public class TlMicroserviceFunc extends Model<TlMicroserviceFunc> {

    private static final long serialVersionUID = 1L;

    /**
     * 功能ID
     */
	@TableId(value="func_id", type= IdType.AUTO)
	private Integer funcId;
    /**
     * 微服务ID
     */
	@TableField("service_id")
	private Integer serviceId;
    /**
     * 功能名
     */
	@TableField("func_name")
	private String funcName;
    /**
     * 功能中文名
     */
	@TableField("func_cname")
	private String funcCname;
    /**
     * 功能描述
     */
	@TableField("func_desc")
	private String funcDesc;
    /**
     * 状态；1:有效; 0:无效
     */
	@TableField("func_status")
	private Integer funcStatus;


	public Integer getFuncId() {
		return funcId;
	}

	public TlMicroserviceFunc setFuncId(Integer funcId) {
		this.funcId = funcId;
		return this;
	}

	public Integer getServiceId() {
		return serviceId;
	}

	public TlMicroserviceFunc setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
		return this;
	}

	public String getFuncName() {
		return funcName;
	}

	public TlMicroserviceFunc setFuncName(String funcName) {
		this.funcName = funcName;
		return this;
	}

	public String getFuncCname() {
		return funcCname;
	}

	public TlMicroserviceFunc setFuncCname(String funcCname) {
		this.funcCname = funcCname;
		return this;
	}

	public String getFuncDesc() {
		return funcDesc;
	}

	public TlMicroserviceFunc setFuncDesc(String funcDesc) {
		this.funcDesc = funcDesc;
		return this;
	}

	public Integer getFuncStatus() {
		return funcStatus;
	}

	public TlMicroserviceFunc setFuncStatus(Integer funcStatus) {
		this.funcStatus = funcStatus;
		return this;
	}

	public static final String FUNC_ID = "func_id";

	public static final String SERVICE_ID = "service_id";

	public static final String FUNC_NAME = "func_name";

	public static final String FUNC_CNAME = "func_cname";

	public static final String FUNC_DESC = "func_desc";

	public static final String FUNC_STATUS = "func_status";

	@Override
	protected Serializable pkVal() {
		return this.funcId;
	}

	@Override
	public String toString() {
		return "TlMicroserviceFunc{" +
			"funcId=" + funcId +
			", serviceId=" + serviceId +
			", funcName=" + funcName +
			", funcCname=" + funcCname +
			", funcDesc=" + funcDesc +
			", funcStatus=" + funcStatus +
			"}";
	}
}
