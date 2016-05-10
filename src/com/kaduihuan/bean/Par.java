package com.kaduihuan.bean;

import org.nutz.dao.entity.annotation.*;

/**
*
* @author Howe(howechiang@gmail.com)
* 
*/
@Table("tb_chanyue_chong_par")
public class Par {

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
	 * 运营版本ID
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
	 * 充值类型
	 */
	@Column("tid")
	private Integer tid;
	
	public Integer getTid() {
		return tid;
	}
	public void setTid(Integer tid) {
		this.tid = tid;
	}
	
}