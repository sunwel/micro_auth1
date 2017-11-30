package com.enlink.auth.dao.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 微服务信息表
 * </p>
 *
 * @author idea
 * @since 2017-01-01
 */
@TableName("tl_microservice")
public class TlMicroservice extends Model<TlMicroservice> {

    private static final long serialVersionUID = 1L;

    /**
     * 微服务ID
     */
	@TableId(value="service_id", type= IdType.AUTO)
	private Integer serviceId;
    /**
     * 微服务名
     */
	@TableField("service_name")
	private String serviceName;
    /**
     * 微服务中文名
     */
	@TableField("service_cname")
	private String serviceCname;
    /**
     * 微服务说明
     */
	@TableField("service_desc")
	private String serviceDesc;
    /**
     * 状态；1:有效; 0:无效
     */
	@TableField("service_status")
	private Integer serviceStatus;


	public Integer getServiceId() {
		return serviceId;
	}

	public TlMicroservice setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
		return this;
	}

	public String getServiceName() {
		return serviceName;
	}

	public TlMicroservice setServiceName(String serviceName) {
		this.serviceName = serviceName;
		return this;
	}

	public String getServiceCname() {
		return serviceCname;
	}

	public TlMicroservice setServiceCname(String serviceCname) {
		this.serviceCname = serviceCname;
		return this;
	}

	public String getServiceDesc() {
		return serviceDesc;
	}

	public TlMicroservice setServiceDesc(String serviceDesc) {
		this.serviceDesc = serviceDesc;
		return this;
	}

	public Integer getServiceStatus() {
		return serviceStatus;
	}

	public TlMicroservice setServiceStatus(Integer serviceStatus) {
		this.serviceStatus = serviceStatus;
		return this;
	}

	public static final String SERVICE_ID = "service_id";

	public static final String SERVICE_NAME = "service_name";

	public static final String SERVICE_CNAME = "service_cname";

	public static final String SERVICE_DESC = "service_desc";

	public static final String SERVICE_STATUS = "service_status";

	@Override
	protected Serializable pkVal() {
		return this.serviceId;
	}

	@Override
	public String toString() {
		return "TlMicroservice{" +
			"serviceId=" + serviceId +
			", serviceName=" + serviceName +
			", serviceCname=" + serviceCname +
			", serviceDesc=" + serviceDesc +
			", serviceStatus=" + serviceStatus +
			"}";
	}
}
