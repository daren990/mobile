package com.kaduihuan.bean;

import org.nutz.dao.entity.annotation.*;

/**
*
* @author Howe(howechiang@gmail.com)
* 
*/
@Table("tb_chanyue_common_socialbind")
public class Socialbind {

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
	 * OPENID
	 */
	@Column("openId")
	private String openId;
	
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	/**
	 * 绑定标识
	 */
	@Column("appId")
	private String appId;
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	
	/**
	 * 令牌
	 */
	@Column("token")
	private String token;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	/**
	 * 绑定时间
	 */
	@Column("bindDate")
	private java.util.Date bindDate;
	
	public java.util.Date getBindDate() {
		return bindDate;
	}
	public void setBindDate(java.util.Date bindDate) {
		this.bindDate = bindDate;
	}
	
}