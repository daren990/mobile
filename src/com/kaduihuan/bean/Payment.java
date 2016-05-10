package com.kaduihuan.bean;

import org.nutz.dao.entity.annotation.*;

/**
*
* @author Howe(howechiang@gmail.com)
* 
*/
@Table("tb_chanyue_chong_payment")
public class Payment {

	/**
	 * ID
	 */
	@Id
	@Column("id")
	private Integer id;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	/**
	 * UID
	 */
	@Column("uid")
	private Integer uid;
	
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	
	/**
	 * 订单编号
	 */
	@Column("orderId")
	private Integer orderId;
	
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	
	/**
	 * 订单金额
	 */
	@Column("total")
	private Double total;
	
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	
	/**
	 * 三方支付单号
	 */
	@Column("tsn")
	private String tsn;
	
	public String getTsn() {
		return tsn;
	}
	public void setTsn(String tsn) {
		this.tsn = tsn;
	}
	
	/**
	 * 支付返回编码
	 */
	@Column("backCode")
	private String backCode;
	
	public String getBackCode() {
		return backCode;
	}
	public void setBackCode(String backCode) {
		this.backCode = backCode;
	}
	
	/**
	 * 实际支付金额
	 */
	@Column("actual")
	private Double actual;
	
	public Double getActual() {
		return actual;
	}
	public void setActual(Double actual) {
		this.actual = actual;
	}
	
	/**
	 * 支付网关
	 */
	@Column("payGate")
	private String payGate;
	
	public String getPayGate() {
		return payGate;
	}
	public void setPayGate(String payGate) {
		this.payGate = payGate;
	}
	
	/**
	 * 支付方式
	 */
	@Column("payWay")
	private String payWay;
	
	public String getPayWay() {
		return payWay;
	}
	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}
	
	/**
	 * 支付方式名称
	 */
	@Column("payName")
	private String payName;
	
	public String getPayName() {
		return payName;
	}
	public void setPayName(String payName) {
		this.payName = payName;
	}
	
	/**
	 * 银行类型
	 */
	@Column("bankType")
	private String bankType;
	
	public String getBankType() {
		return bankType;
	}
	public void setBankType(String bankType) {
		this.bankType = bankType;
	}
	
	/**
	 * 银行订单号
	 */
	@Column("bankBillno")
	private String bankBillno;
	
	public String getBankBillno() {
		return bankBillno;
	}
	public void setBankBillno(String bankBillno) {
		this.bankBillno = bankBillno;
	}
	
	/**
	 * 手续费
	 */
	@Column("factorage")
	private Double factorage;
	
	public Double getFactorage() {
		return factorage;
	}
	public void setFactorage(Double factorage) {
		this.factorage = factorage;
	}
	
	/**
	 * 创建时间
	 */
	@Column("createTime")
	private java.util.Date createTime;
	
	public java.util.Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	
	/**
	 * 支付返回时间
	 */
	@Column("backTime")
	private java.util.Date backTime;
	
	public java.util.Date getBackTime() {
		return backTime;
	}
	public void setBackTime(java.util.Date backTime) {
		this.backTime = backTime;
	}
	
	/**
	 * 支付状态
	 */
	@Column("status")
	private String status;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * 提交IP
	 */
	@Column("ip")
	private String ip;
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	/**
	 * 备注
	 */
	@Column("remark")
	private String remark;
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}