package com.kaduihuan.bean;

import org.nutz.dao.entity.annotation.*;

/**
*
* @author Howe(howechiang@gmail.com)
* 
*/
@Table("tb_chanyue_chong_game")
public class Game {

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
	 * 拼音
	 */
	@Column("pinyin")
	private String pinyin;
	
	public String getPinyin() {
		return pinyin;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
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
	 * 运行平台名称
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
	 * 拼音首字母
	 */
	@Column("py")
	private String py;
	
	public String getPy() {
		return py;
	}
	public void setPy(String py) {
		this.py = py;
	}
	
	/**
	 * 购买页模板代码
	 */
	@Column("template")
	private Integer template;
	
	public Integer getTemplate() {
		return template;
	}
	public void setTemplate(Integer template) {
		this.template = template;
	}
	
	/**
	 * 是否热门游戏
	 */
	@Column("isHot")
	private String isHot;
	
	public String getIsHot() {
		return isHot;
	}
	public void setIsHot(String isHot) {
		this.isHot = isHot;
	}
	
	/**
	 * 
	 */
	@Column("isTop")
	private String isTop;
	
	public String getIsTop() {
		return isTop;
	}
	public void setIsTop(String isTop) {
		this.isTop = isTop;
	}
	
	/**
	 * 游戏开发商
	 */
	@Column("developers")
	private String developers;
	
	public String getDevelopers() {
		return developers;
	}
	public void setDevelopers(String developers) {
		this.developers = developers;
	}
	
	/**
	 * 添加时间
	 */
	@Column("addDate")
	private java.util.Date addDate;
	
	public java.util.Date getAddDate() {
		return addDate;
	}
	public void setAddDate(java.util.Date addDate) {
		this.addDate = addDate;
	}
	
	/**
	 * 上架状态
	 */
	@Column("shelf")
	private String shelf;
	
	public String getShelf() {
		return shelf;
	}
	public void setShelf(String shelf) {
		this.shelf = shelf;
	}
	
	/**
	 * 首充
	 */
	@Column("t1")
	private String t1;
	
	public String getT1() {
		return t1;
	}
	public void setT1(String t1) {
		this.t1 = t1;
	}
	
	/**
	 * 首充代充
	 */
	@Column("t2")
	private String t2;
	
	public String getT2() {
		return t2;
	}
	public void setT2(String t2) {
		this.t2 = t2;
	}
	
	/**
	 * 代充
	 */
	@Column("t3")
	private String t3;
	
	public String getT3() {
		return t3;
	}
	public void setT3(String t3) {
		this.t3 = t3;
	}
	
	/**
	 * 金币
	 */
	@Column("t4")
	private String t4;
	
	public String getT4() {
		return t4;
	}
	public void setT4(String t4) {
		this.t4 = t4;
	}
	
	/**
	 * 月卡
	 */
	@Column("t5")
	private String t5;
	
	public String getT5() {
		return t5;
	}
	public void setT5(String t5) {
		this.t5 = t5;
	}
	
	/**
	 * 充值卡ID
	 */
	@Column("cid")
	private Integer cid;
	
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	
}