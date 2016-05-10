package com.kaduihuan.bean;

import org.nutz.dao.entity.annotation.*;

/**
 * @author Howe(howechiang@gmail.com)
 */
@Table("tb_chanyue_common_user")
public class User {

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
     * 邮箱
     */
    @Column("email")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 加密后密码
     */
    @Column("password")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 手机号
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
     * QQ
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
     * 邮箱验证
     */
    @Column("mailVerify")
    private String mailVerify;

    public String getMailVerify() {
        return mailVerify;
    }

    public void setMailVerify(String mailVerify) {
        this.mailVerify = mailVerify;
    }

    /**
     * 注册IP
     */
    @Column("regIP")
    private String regIp;

    public String getRegIp() {
        return regIp;
    }

    public void setRegIp(String regIp) {
        this.regIp = regIp;
    }

    /**
     * 注册时间
     */
    @Column("regDate")
    private java.util.Date regDate;

    public java.util.Date getRegDate() {
        return regDate;
    }

    public void setRegDate(java.util.Date regDate) {
        this.regDate = regDate;
    }

    /**
     * 加密钥
     */
    @Column("salt")
    private String salt;

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * 密保问题
     */
    @Column("question")
    private String question;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * 密保答案
     */
    @Column("answer")
    private String answer;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * 用户昵称
     */
    private String nickName;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * 用户头像
     */
    private String avatar;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}