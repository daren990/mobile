package com.kaduihuan.bean;

import org.nutz.dao.entity.annotation.*;

/**
*
* @author Howe(howechiang@gmail.com)
* 
*/
@Table("tb_chanyue_chong_platform")
public class Platform {

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
	 * 父级
	 */
	@Column("pid")
	private Integer pid;
	
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	
}