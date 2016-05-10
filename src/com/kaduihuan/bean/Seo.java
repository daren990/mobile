package com.kaduihuan.bean;

import org.nutz.dao.entity.annotation.*;

/**
*
* @author Howe(howechiang@gmail.com)
* 
*/
@Table("tb_chanyue_chong_seo")
public class Seo {

	/**
	 * GID
	 */
	@Id(auto = false)
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
	 * PLID
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
	 * 页面标题
	 */
	@Column("title")
	private String title;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * 页面关键字
	 */
	@Column("keywords")
	private String keywords;
	
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	
	/**
	 * 页面说明
	 */
	@Column("description")
	private String description;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * 更新时间
	 */
	@Column("modified")
	private java.util.Date modified;
	
	public java.util.Date getModified() {
		return modified;
	}
	public void setModified(java.util.Date modified) {
		this.modified = modified;
	}
	
}