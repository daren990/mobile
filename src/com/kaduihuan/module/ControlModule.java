package com.kaduihuan.module;

import com.kaduihuan.bean.*;
import com.kaduihuan.tool.CodeCryption;
import com.kaduihuan.tool.PinyinUtil;
import com.kaduihuan.tool.SessionManger;
import com.kaduihuan.tool.ToolUtility;
import jodd.mail.Email;
import jodd.mail.SendMailSession;
import jodd.mail.SmtpServer;
import jodd.props.Props;
import jodd.util.StringUtil;
import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@IocBean(fields = {"dao"})
public class ControlModule {

    private Dao dao;

    /**
     * 注册页
     *
     * @return
     * @throws IOException
     */
    @GET
    @At("/register")
    @Ok("jetx:/register.jetx")
    public Map<String, Object> register() throws IOException {

        if (SessionManger.checkLogin(Mvcs.getReq()))
            Mvcs.getResp().sendRedirect("/index");
        Map<String, Object> map = new HashMap<String, Object>();
        String returnUrl = Mvcs.getReq().getParameter("returnUrl");
        if (!StringUtil.isBlank(returnUrl))
            map.put("returnUrl", returnUrl);
        return map;
    }

    @POST
    @At("/register")
    @Ok("redirect:${obj}")
    public String register(@Param("email") String email,
                        @Param("password") String password) {

        String salt = ToolUtility.getRandomString(6);
        if (!StringUtil.isBlank(email) && !ToolUtility.sqlValidate(email) && ToolUtility.verifyEmail(email)  && !StringUtil.isBlank(password)) {
            try {

                String ip = ToolUtility.getIpAddr(Mvcs.getReq());
                User user = new User();
                user.setEmail(email.toLowerCase());
                user.setSalt(salt);
                user.setRegIp(ip);
                user.setAnswer(null);
                user.setQuestion(null);
                user.setMailVerify("0");
                user.setPassword(CodeCryption.encode("MD5",
                        CodeCryption.encode("MD5", password) + salt));
                user.setRegDate(new Date());
                dao.insert(user);

                String code = ToolUtility.getRandomNumber(6);
                Captcha captcha = new Captcha();
                captcha.setUid(user.getId());
                captcha.setCode(code);
                captcha.setReceive(email);
                captcha.setMode("email");
                captcha.setType("register");
                dao.insert(captcha);

                String valid = CodeCryption
                        .encode("BASE64",
                                "uid="
                                        + user.getId()
                                        + "&code="
                                        + CodeCryption.encode(
                                        "MD5",
                                        CodeCryption
                                                .encode("MD5", code)
                                                + code)
                                        + "&type=register&email=" + email);

                Props props = new Props();
                props.load(new File("WEB-INF/classes/mail.properties"), "utf-8");
                Email mail = Email.create();
                mail.addText("发卡发邮件验证", props.getValue("mail.charset"));
                mail.addHtml(props.getValue("mail.templates.vaildMail").replace("@EMAIL", email).replaceAll("@VAILURL", valid), props.getValue("mail.charset"));
                mail.from(props.getValue("mail.from")).to(email);
                mail.subject("发卡发邮件验证", props.getValue("mail.charset"));
                SmtpServer smtpServer = SmtpServer.create(props.getValue("mail.hostName")).authenticateWith(props.getValue("mail.userName"), props.getValue("mail.password"));
                SendMailSession session = smtpServer.createSession();
                session.open();
                session.sendMail(mail);
                session.close();

                Mvcs.getReq().getSession().setAttribute(ToolUtility.USER_KEY, dao.fetch(User.class, user.getId()));
                return "redirect:/user/index.html";
            } catch (Exception e) {
                return "redirect:/register.html";
            }
        }
        return "redirect:/register.html";
    }
    /**
     * 登录页
     *
     * @return
     * @throws IOException
     */
    @GET
    @At("/login")
    @Ok("jetx:/login.jetx")
    public Map<String, Object> login() throws IOException {

        if (SessionManger.checkLogin(Mvcs.getReq()))
            Mvcs.getResp().sendRedirect("/index");
        Map<String, Object> map = new HashMap<String, Object>();
        String returnUrl = Mvcs.getReq().getParameter("returnUrl");
        if (!StringUtil.isBlank(returnUrl))
            map.put("returnUrl", returnUrl);
        return map;
    }

    /**
     * 登录
     *
     * @param email
     * @param password
     * @return
     */
    @POST
    @At("/login")
    @Ok("redirect:${obj}")
    public String login(@Param("email") String email,
                        @Param("password") String password) {

        String returnUrl = ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("returnUrl"));

        if (!StringUtil.isBlank(email) && !ToolUtility.sqlValidate(email) && !StringUtil.isBlank(password)) {
            User user = dao.fetch(
                    User.class,
                    Cnd.wrap("email = '" + email.toLowerCase()
                            + "' and password = md5(concat('"+CodeCryption.encode("MD5", password)+"', salt))"));
            if (user != null) {
                String salt = ToolUtility.getRandomString(6);

                user.setSalt(salt);
                user.setPassword(CodeCryption.encode("MD5",
                        CodeCryption.encode("MD5", password) + salt));
                dao.update(user);
                LoginLogs log = new LoginLogs();
                log.setUid(user.getId());
                log.setIp(ToolUtility.getIpAddr(Mvcs.getReq()));
                log.setLoginDate(new Date());
                dao.insert(log);
                Mvcs.getReq().getSession().setAttribute(ToolUtility.USER_KEY, user);
                if (!StringUtil.isBlank(returnUrl))
                    return returnUrl;
                else
                    return "/index";
            } else
                return "/login";
        } else
            return "/login";
    }

    @GET
    @At("/index")
    @Ok("jetx:/index.jetx")
    public Map<String, Object> index() throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = SessionManger.getCurrentUser(Mvcs.getReq());
        map.put("user", user);
        return map;
    }

    @GET
    @At("/search")
    @Ok("jetx:/search.jetx")
    public Map<String, Object> search() throws IOException {

        String gName = ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("gname").trim());
        if (StringUtil.isBlank(gName))
            Mvcs.getResp().sendRedirect("/error/404.html");
        String pinyin = PinyinUtil.getPinYin(gName);
        List<Game> games = dao.query(Game.class, Cnd.wrap("(lower(pinyin) like '%" + pinyin + "%' or lower(py) like '%" + pinyin + "%') and shelf='1' and plid = '1'"));
        if (games.isEmpty())
            Mvcs.getResp().sendRedirect("/error/404.html");
        Map<String, Object> map = new HashMap<String, Object>();
        User user = SessionManger.getCurrentUser(Mvcs.getReq());
        map.put("user", user);
        map.put("games", games);
        map.put("gname", gName);
        return map;
    }

    @At("/logout")
    @Ok("redirect:/login")
    public void logout(HttpServletRequest req) throws IOException {
        req.getSession().invalidate();
    }

    @Ok("raw")
    @At("/checkpasswd")
    public String checkPasswd() {

        String email = ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("email").toLowerCase());
        String password = ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("password").toLowerCase());

        if (StringUtil.isBlank(password))
            return "{\"data\":{\"error\":\"密码不能为空\"}}";
        else {
            User user = dao.fetch(
                    User.class,
                    Cnd.wrap("email = '" + email.toLowerCase()
                            + "' and password = md5(concat('"+CodeCryption.encode("MD5", password)+"', salt))"));
            if (user != null)
                return "{\"data\":{\"ok\":\"\"}}";
            else
                return "{\"data\":{\"error\":\"密码错误\"}}";
        }
    }

    @Ok("raw")
    @At("/checkpswd")
    public String checkPswd() {

        User user = SessionManger.getCurrentUser(Mvcs.getReq());
        String password = ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("password").toLowerCase());

        if (StringUtil.isBlank(password))
            return "{\"data\":{\"error\":\"密码不能为空\"}}";
        else if (user == null)
            return "{\"data\":{\"error\":\"登录信息过期\"}}";
        else {
            user = dao.fetch(
                    User.class,
                    Cnd.wrap("email = '" + user.getEmail()
                            + "' and password = md5(concat('"+CodeCryption.encode("MD5", password)+"', salt))"));
            if (user != null)
                return "{\"data\":{\"ok\":\"成功修改密码后将自动退出系统\"}}";
            else
                return "{\"data\":{\"error\":\"密码错误\"}}";
        }
    }

    @Ok("raw")
    @At("/checkemail")
    public String checkEmail() {

        String email = ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("email").toLowerCase());

        if (StringUtil.isBlank(email) || ToolUtility.sqlValidate(email))
            return "{\"data\":{\"error\":\"邮箱不能为空\"}}";
        else {
            User user = dao.fetch(User.class,
                    Cnd.where("EMAIL", "=", email.toLowerCase()));
            if (user != null)
                return "{\"data\":{\"ok\":\"\"}}";
            else
                return "{\"data\":{\"error\":\"邮箱不存在\"}}";
        }
    }

    @Ok("raw")
    @At("/verifyemail")
    public String verifyEmail() {

        String email = ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("email").toLowerCase());

        if (StringUtil.isBlank(email) || ToolUtility.sqlValidate(email))
            return "{\"data\":{\"error\":\"邮箱不能为空\"}}";
        else {
            User user = dao.fetch(User.class,
                    Cnd.where("EMAIL", "=", email.toLowerCase()));
            if (user == null)
                return "{\"data\":{\"ok\":\"\"}}";
            else
                return "{\"data\":{\"error\":\"邮箱已存在\"}}";
        }
    }

//
//    @Ok("raw")
//    @At("/sendMail")
//    public String sendMail() throws IOException {
//
//        String mail = Mvcs.getReq().getParameter("email").toLowerCase();
//
//        Props props = new Props();
//        props.load(new File("WEB-INF/classes/mail.properties"), "utf-8");
//        Email email = Email.create();
//        email.addText("这里写的是Test", props.getValue("mail.charset"));
//        email.addHtml(props.getValue("mail.templates.order").replace("@CAPTCHA", "123456"), props.getValue("mail.charset"));
//        email.from(props.getValue("mail.from")).to(mail);
//        email.subject("这里写的是Test", props.getValue("mail.charset"));
//
//        SmtpServer smtpServer = SmtpServer.create(props.getValue("mail.hostName")).authenticateWith(props.getValue("mail.userName"), props.getValue("mail.password"));
//        SendMailSession session = smtpServer.createSession();
//        session.open();
//        session.sendMail(email);
//        session.close();
//        System.out.println("发送成功!...");
//
//        return "{\"data\":{\"error\":\"邮箱不存在\"}}";
//
//    }

    @GET
    @At("/validEmail")
    @Ok("jetx:/validemail.jetx")
    public Map<String, Object> validEmail() throws IOException {

        String key = Mvcs.getReq().getParameter("key").replaceAll("#", "");
        Map<String, Object> map = new HashMap<String, Object>();

        if (key.indexOf("uid") >= 0 && key.indexOf("code") >= 0 && key.indexOf("email") >= 0) {
            String uid = key.substring(key.indexOf("uid") + 4, key.indexOf("code") - 1);
            String code = key.substring(key.indexOf("code") + 5, key.indexOf("type") - 1);
            String type = key.substring(key.indexOf("type") + 5, key.indexOf("email") - 1);
            String email = key.substring(key.indexOf("email") + 6);
            Captcha captcha = dao.fetch(Captcha.class, Cnd.where("code", "=", code).and("type", "=", type).and("mode", "=", "email").and("receive", "=", email).and("uid", "=", uid));

            if (captcha != null) {
                dao.update(User.class, Chain.make("mailVerify","1"), Cnd.where("id","=",uid));
                dao.delete(captcha);

                OperLogs logs = new OperLogs();
                logs.setUid(Integer.parseInt(uid));
                logs.setIp(ToolUtility.getIpAddr(Mvcs.getReq()));
                logs.setOperDate(new Date());
                logs.setAction("完成验证邮箱（" + email + "）");
                dao.insert(logs);
                map.put("msg", "1");
            } else
                map.put("msg", "0");
        } else
            map.put("msg", "0");
        User user = SessionManger.getCurrentUser(Mvcs.getReq());
        map.put("user", user);
        return map;
    }

}
