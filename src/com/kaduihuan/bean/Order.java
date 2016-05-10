package com.kaduihuan.bean;

import org.nutz.dao.entity.annotation.*;

/**
*
* @author Howe(howechiang@gmail.com)
* 
*/
@Table("tb_chanyue_chong_order")
public class Order {

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
	 * 充值类型
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
	 * 运行平台编码
	 */
	@Column("plid")
	private Integer plid;
	
	public Integer getPlid() {
		return plid;
	}
	public void setPlid(Integer plid) {
		this.plid = plid;
	}
	
	/**
	 * 运行平台
	 */
	@Column("pName")
	private String pname;
	
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
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
	 * 运营商名称
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
	 * 区名称
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
	 * 区服ID
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
	 * 服务器名称
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
	 * 价格ID
	 */
	@Column("pid")
	private Integer pid;
	
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	
	/**
	 * 商品规格
	 */
	@Column("standard")
	private String standard;
	
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	
	/**
	 * 面值
	 */
	@Column("face")
	private Integer face;
	
	public Integer getFace() {
		return face;
	}
	public void setFace(Integer face) {
		this.face = face;
	}
	
	/**
	 * 单价
	 */
	@Column("price")
	private Double price;
	
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
	/**
	 * 购买数量
	 */
	@Column("quantity")
	private Integer quantity;
	
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	/**
	 * 充值帐号
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
	 * 联系手机
	 */
	@Column("mobile")
	private String mobile;
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	/**
	 * 联系QQ
	 */
	@Column("qq")
	private String qq;
	
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	
	/**
	 * 下单时间
	 */
	@Column("orderTime")
	private java.util.Date orderTime;
	
	public java.util.Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(java.util.Date orderTime) {
		this.orderTime = orderTime;
	}
	
	/**
	 * 支付成功时间
	 */
	@Column("payTime")
	private java.util.Date payTime;
	
	public java.util.Date getPayTime() {
		return payTime;
	}
	public void setPayTime(java.util.Date payTime) {
		this.payTime = payTime;
	}
	
	/**
	 * 完成时间
	 */
	@Column("finishTime")
	private java.util.Date finishTime;
	
	public java.util.Date getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(java.util.Date finishTime) {
		this.finishTime = finishTime;
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
	 * 订单状态
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
	
	/**
	 * 角色名
	 */
	@Column("juese")
	private String juese;
	
	public String getJuese() {
		return juese;
	}
	public void setJuese(String juese) {
		this.juese = juese;
	}
	
	/**
	 * 订单来源
	 */
	@Column("source")
	private String source;
	
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
}