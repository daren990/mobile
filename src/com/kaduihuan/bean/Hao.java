package com.kaduihuan.bean;

import org.nutz.dao.entity.annotation.*;

/**
*
* @author Howe(howechiang@gmail.com)
* 
*/
@Table("tb_chanyue_chong_hao")
public class Hao {

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
	 * 游戏ID
	 */
	@Column("gid")
	private Integer gid;
	
	public Integer getGid() {
		return gid;
	}
	public void setGid(Integer gid) {
		this.gid = gid;
	}
	
	/**
	 * 游戏名称
	 */
	@Column("gName")
	private String gname;
	
	public String getGname() {
		return gname;
	}
	public void setGname(String gname) {
		this.gname = gname;
	}
	
	/**
	 * 运营商ID
	 */
	@Column("oid")
	private Integer oid;
	
	public Integer getOid() {
		return oid;
	}
	public void setOid(Integer oid) {
		this.oid = oid;
	}
	
	/**
	 * 
	 */
	@Column("oName")
	private String oname;
	
	public String getOname() {
		return oname;
	}
	public void setOname(String oname) {
		this.oname = oname;
	}
	
	/**
	 * 区ID
	 */
	@Column("aid")
	private Integer aid;
	
	public Integer getAid() {
		return aid;
	}
	public void setAid(Integer aid) {
		this.aid = aid;
	}
	
	/**
	 * 
	 */
	@Column("aName")
	private String aname;
	
	public String getAname() {
		return aname;
	}
	public void setAname(String aname) {
		this.aname = aname;
	}
	
	/**
	 * 服ID
	 */
	@Column("sid")
	private Integer sid;
	
	public Integer getSid() {
		return sid;
	}
	public void setSid(Integer sid) {
		this.sid = sid;
	}
	
	/**
	 * 区服编码
	 */
	@Column("num")
	private String num;
	
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	
	/**
	 * 
	 */
	@Column("sName")
	private String sname;
	
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	
	/**
	 * 首充帐号
	 */
	@Column("account")
	private String account;
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	
	/**
	 * 帐号密码
	 */
	@Column("pswd")
	private String pswd;
	
	public String getPswd() {
		return pswd;
	}
	public void setPswd(String pswd) {
		this.pswd = pswd;
	}
	
	/**
	 * UID
	 */
	@Column("creatTime")
	private java.util.Date creatTime;
	
	public java.util.Date getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(java.util.Date creatTime) {
		this.creatTime = creatTime;
	}
	
	/**
	 * 
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
	 * 
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
	 * 帐号备注
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