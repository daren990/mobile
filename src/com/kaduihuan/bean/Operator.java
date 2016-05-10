package com.kaduihuan.bean;

import org.nutz.dao.entity.annotation.*;

/**
*
* @author Howe(howechiang@gmail.com)
* 
*/
@Table("tb_chanyue_chong_operator")
public class Operator {

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
	 * 是否支持首充
	 */
	@Column("isF")
	private String isF;
	
	public String getIsF() {
		return isF;
	}
	public void setIsF(String isF) {
		this.isF = isF;
	}
	
	/**
	 * 安卓游戏下载链接
	 */
	@Column("downUrl")
	private String downUrl;
	
	public String getDownUrl() {
		return downUrl;
	}
	public void setDownUrl(String downUrl) {
		this.downUrl = downUrl;
	}
	
}