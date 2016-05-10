package com.kaduihuan.bean;

import org.nutz.dao.entity.annotation.*;

/**
*
* @author Howe(howechiang@gmail.com)
* 
*/
@Table("tb_chanyue_chong_payway")
public class Payway {

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
	 * 支付方式
	 */
	@Column("name")
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 支付网关
	 */
	@Column("gate")
	private String gate;
	
	public String getGate() {
		return gate;
	}
	public void setGate(String gate) {
		this.gate = gate;
	}
	
	/**
	 * 支付编码
	 */
	@Column("code")
	private String code;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	/**
	 * 费率
	 */
	@Column("rate")
	private Long rate;
	
	public Long getRate() {
		return rate;
	}
	public void setRate(Long rate) {
		this.rate = rate;
	}
	
}