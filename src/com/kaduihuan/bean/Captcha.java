package com.kaduihuan.bean;

import org.nutz.dao.entity.annotation.*;

/**
*
* @author Howe(howechiang@gmail.com)
* 
*/
@Table("tb_chanyue_commcon_captcha")
public class Captcha {

	/**
	 * UID
	 */
	@Id(auto = false)
	@Column("uid")
	private Integer uid;
	
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	
	/**
	 * 验证码
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
	 * 验证类型
	 */
	@Column("type")
	private String type;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * 接收方
	 */
	@Column("receive")
	private String receive;
	
	public String getReceive() {
		return receive;
	}
	public void setReceive(String receive) {
		this.receive = receive;
	}
	
	/**
	 * 验证方式
	 */
	@Column("mode")
	private String mode;
	
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	/**
	 * 发送时间
	 */
	@Column("sendTime")
	private java.util.Date sendTime;
	
	public java.util.Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(java.util.Date sendTime) {
		this.sendTime = sendTime;
	}
	
}