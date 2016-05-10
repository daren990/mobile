package com.kaduihuan.bean;

import org.nutz.dao.entity.annotation.*;

/**
*
* @author Howe(howechiang@gmail.com)
* 
*/
@Table("tb_chanyue_common_realauth")
public class Realauth {

	/**
	 * 
	 */
	@Id
	@Column("uid")
	private Integer uid;
	
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	
	/**
	 * 真实名
	 */
	@Column("realName")
	private String realName;
	
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	/**
	 * 身份证号码
	 */
	@Column("idCard")
	private String idCard;
	
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	
	/**
	 * 验证状态
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
	 * 添加时间
	 */
	@Column("addTime")
	private java.util.Date addTime;
	
	public java.util.Date getAddTime() {
		return addTime;
	}
	public void setAddTime(java.util.Date addTime) {
		this.addTime = addTime;
	}
	
	/**
	 * 验证时间
	 */
	@Column("authTime")
	private java.util.Date authTime;
	
	public java.util.Date getAuthTime() {
		return authTime;
	}
	public void setAuthTime(java.util.Date authTime) {
		this.authTime = authTime;
	}
	
}