package com.kaduihuan.bean;

import org.nutz.dao.entity.annotation.*;

/**
*
* @author Howe(howechiang@gmail.com)
* 
*/
@Table("tb_chanyue_chong_server")
public class Server {

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
	
}