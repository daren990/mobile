package com.kaduihuan.module;

import com.kaduihuan.bean.LoginLogs;
import com.kaduihuan.bean.User;
import com.kaduihuan.tool.CodeCryption;
import com.kaduihuan.tool.HttpClientTool;
import com.kaduihuan.tool.ToolUtility;
import jodd.util.StringUtil;
import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.service.EntityService;

import com.kaduihuan.bean.Socialbind;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@IocBean(fields = {"dao"})
public class SocialbindModule extends EntityService<Socialbind> {

    @At("/login/qq")
    @Ok("redirect:${obj}")
    public String qqLogin() {
        return "https://graph.qq.com/oauth2.0/authorize?response_type=code&client_id=100455201&scope=get_user_info&redirect_uri=https%3A%2F%2Fm.fakafa.com%2Fsocial%2FqqBacktrack&display=mobile&state=fakafa";
    }

    @At("/login/weibo")
    @Ok("redirect:${obj}")
    public String weiboLogin() {
        return "https://open.weibo.cn/oauth2/authorize?client_id=1006424902&display=mobile&response_type=code&redirect_uri=https%3A%2F%2Fm.fakafa.com%2Fsocial%2FweiboBacktrack&state=fakafa";
    }

    @At("/login/alipay")
    @Ok("redirect:${obj}")
    public String alipayLogin() {
        return "https://mapi.alipay.com/gateway.do?target_service=user.auth.quick.login&sign_type=MD5&return_url=https://m.fakafa.com/social/alipayBacktrack&sign=86f0b1f55cc3171e683019ebb53557a1&service=alipay.auth.authorize&partner=2088121042786742&_input_charset=utf-8";
    }

    @At("/login/360")
    @Ok("redirect:${obj}")
    public String qihuLogin() {
        return "https://openapi.360.cn/oauth2/authorize?client_id=bfecafd4d04b225e1edf359ecc2883d9&state=fakafa&response_type=code&redirect_uri=https://m.fakafa.com/social/qihuBacktrack&scope=basic&display=mobile.default";
    }

    @At("/social/qqBacktrack")
    @Ok("redirect:${obj}")
    public String qqBacktrack() {

        String code = ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("code").trim());
        if (StringUtil.isBlank(code) || !Mvcs.getReq().getParameter("state").equals("fakafa"))
            return "/error-777.html";
        else {
            String tmp = HttpClientTool.get("https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id=100455201&client_secret=c55144d04a7d81d02b163b3d232c8527&code="
                    + code
                    + "&redirect_uri=https://m.fakafa.com/social/qqBacktrack");
            if (tmp.indexOf("access_token") >= 0 && tmp != null) {
                String token = tmp.substring(tmp.indexOf("access_token") + 13,
                        tmp.indexOf("&"));
                tmp = HttpClientTool.get("https://graph.qq.com/oauth2.0/me?access_token="
                        + token);
                if (tmp.indexOf("openid") >= 0 && tmp != null) {
                    String openId = ToolUtility.getValueFromJson(tmp, "openid", "}").toUpperCase();
                    Socialbind socialbind = dao().fetch(Socialbind.class, Cnd.where("openid", "=", openId).and("appId", "=", "qq"));

                    tmp = HttpClientTool
                            .get("https://graph.qq.com/user/get_user_info?access_token="
                                    + token
                                    + "&oauth_consumer_key=100455201&openid="
                                    + openId);
                    String nickName = ToolUtility.getValueFromJson(tmp, "nickname", "gender");
                    String avatar = ToolUtility.getValueFromJson(tmp, "figureurl_qq_1", "figureurl_qq_2");
                    String ip = ToolUtility.getIpAddr(Mvcs.getReq());
                    if (socialbind != null) {
                        User user = dao().fetch(User.class, socialbind.getUid());
                        if (user != null) {

                            LoginLogs logs = new LoginLogs();
                            logs.setIp(ip);
                            logs.setUid(user.getId());
                            logs.setLoginDate(new Date());
                            dao().insert(logs);
                            user.setNickName(nickName);
                            user.setAvatar(avatar);
                            Mvcs.getReq().getSession().setAttribute(ToolUtility.USER_KEY, user);
                            String url = ToolUtility.getCookieValue(Mvcs.getReq(), "url");
                            ToolUtility.deleteCookie(Mvcs.getReq(), Mvcs.getResp(), "url", true);
                            if (StringUtil.isBlank(url))
                                return ToolUtility.WEBSITE_URL;
                            else
                                return url;
                        } else
                            return "/error-505.html";
                    } else {

                        User user = new User();
                        user.setPassword("QuickLogin");
                        user.setMailVerify("1");
                        user.setMobile(null);
                        user.setEmail(openId + "@quicklogin.qq");
                        user.setQq(null);
                        user.setRegIp(ip);
                        user.setSalt("fakafa");
                        user.setRegDate(new Date());
                        user.setAnswer(null);
                        user.setQuestion(null);
                        dao().insert(user);

                        socialbind = new Socialbind();
                        socialbind.setOpenId(openId);
                        socialbind.setAppId("qq");
                        socialbind.setToken(token);
                        socialbind.setUid(user.getId());
                        socialbind.setBindDate(new Date());
                        dao().insert(socialbind);

                        LoginLogs logs = new LoginLogs();
                        logs.setIp(ip);
                        logs.setUid(user.getId());
                        logs.setLoginDate(new Date());
                        dao().insert(logs);
                        user.setNickName(nickName);
                        Mvcs.getReq().getSession().setAttribute(ToolUtility.USER_KEY, user);

                        String url = ToolUtility.getCookieValue(Mvcs.getReq(), "url");
                        ToolUtility.deleteCookie(Mvcs.getReq(), Mvcs.getResp(), "url", true);
                        if (StringUtil.isBlank(url))
                            return ToolUtility.WEBSITE_URL;
                        else
                            return url;
                    }
                } else
                    return "/error-777.html";
            } else
                return "/error-777.html";
        }
    }

    @At("/social/weiboBacktrack")
    @Ok("redirect:${obj}")
    public String weiboBacktrack() throws IOException {

        String code = ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("code").trim());
        if (StringUtil.isBlank(code) || !Mvcs.getReq().getParameter("state").equals("fakafa"))
            return "/error-777.html";
        else {

            Map<String, Object> parms = new HashMap<String, Object>();
            parms.put("client_id", "1006424902");
            parms.put("client_secret", "fa1af17387b862e3fbf2a5f1dc4cf12c");
            parms.put("grant_type", "authorization_code");
            parms.put("redirect_uri", ToolUtility.WEBSITE_URL);
            parms.put("code", code);
            String tmp = HttpClientTool.post(
                    "https://api.weibo.com/oauth2/access_token", parms);
            if (tmp.indexOf("uid") >= 0 && tmp != null) {

                String access_token = ToolUtility.getValueFromJson(tmp,
                        "access_token", "remind_in");
                String openId = ToolUtility.getValueFromJson(tmp, "uid", "}");
                Socialbind socialbind = dao().fetch(Socialbind.class, Cnd.where("openid", "=", openId).and("appId", "=", "weibo"));
                tmp = HttpClientTool.get("https://api.weibo.com/2/users/show.json?source=4123109967&access_token=" + access_token + "&uid=" + openId);
                String avatar = ToolUtility.getValueFromJson(tmp, "profile_image_url", "cover_image").replaceAll("http:", "");
                String nickName = ToolUtility.getValueFromJson(tmp.replaceAll("\"", ""), "screen_name", ",name");

                String ip = ToolUtility.getIpAddr(Mvcs.getReq());
                if (socialbind != null) {
                    User user = dao().fetch(User.class, socialbind.getUid());
                    if (user != null) {
                        LoginLogs logs = new LoginLogs();
                        logs.setIp(ip);
                        logs.setUid(user.getId());
                        logs.setLoginDate(new Date());
                        user.setNickName(nickName);
                        user.setAvatar(avatar);
                        Mvcs.getReq().getSession().setAttribute(ToolUtility.USER_KEY, user);

                        String url = ToolUtility.getCookieValue(Mvcs.getReq(), "url");
                        ToolUtility.deleteCookie(Mvcs.getReq(), Mvcs.getResp(), "url", true);
                        if (StringUtil.isBlank(url))
                            return ToolUtility.WEBSITE_URL;
                        else
                            return url;
                    } else
                        return "/error-505.html";
                } else {

                    User user = new User();
                    user.setPassword("QuickLogin");
                    user.setMailVerify("1");
                    user.setMobile(null);
                    user.setEmail(openId + "@quicklogin.weibo");
                    user.setQq(null);
                    user.setRegIp(ip);
                    user.setRegDate(new Date());
                    user.setSalt("fakafa");
                    user.setAnswer(null);
                    user.setQuestion(null);
                    dao().insert(user);

                    socialbind = new Socialbind();
                    socialbind.setOpenId(openId);
                    socialbind.setAppId("weibo");
                    socialbind.setBindDate(new Date());
                    socialbind.setToken(access_token);
                    socialbind.setUid(user.getId());
                    dao().insert(socialbind);

                    LoginLogs logs = new LoginLogs();
                    logs.setIp(ip);
                    logs.setUid(user.getId());
                    logs.setLoginDate(new Date());
                    dao().insert(logs);
                    user.setNickName(nickName);
                    user.setAvatar(avatar);
                    Mvcs.getReq().getSession().setAttribute(ToolUtility.USER_KEY, user);

                    String url = ToolUtility.getCookieValue(Mvcs.getReq(), "url");
                    ToolUtility.deleteCookie(Mvcs.getReq(), Mvcs.getResp(), "url", true);
                    if (StringUtil.isBlank(url))
                        return ToolUtility.WEBSITE_URL;
                    else
                        return url;
                }
            } else
                return "/error-777.html";
        }
    }

    @At("/social/alipayBacktrack")
    @Ok("redirect:${obj}")
    public String alipayBacktrack() throws IOException {

        String notify_id = ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("notify_id").trim());
        String user_id = ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("user_id").trim());
        String token = ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("token").trim());
        String real_name = ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("real_name").trim());
        if (StringUtil.isBlank(notify_id) || StringUtil.isBlank(user_id))
            return "/error-777.html";
        else {
            String tmp = HttpClientTool
                    .get("https://mapi.alipay.com/gateway.do?service=notify_verify&partner=2088121042786742&notify_id="
                            + CodeCryption.encode("URL", notify_id));
            if (tmp.toLowerCase().equals("true") && !StringUtil.isBlank(tmp)) {
                String openId = user_id;
                Socialbind socialbind = dao().fetch(Socialbind.class, Cnd.where("openid", "=", openId).and("appId", "=", "qq"));
                String ip = ToolUtility.getIpAddr(Mvcs.getReq());
                if (socialbind != null) {
                    User user = dao().fetch(User.class, socialbind.getUid());
                    if (user != null) {

                        LoginLogs logs = new LoginLogs();
                        logs.setIp(ip);
                        logs.setUid(user.getId());
                        logs.setLoginDate(new Date());
                        user.setNickName(real_name);
                        Mvcs.getReq().getSession().setAttribute(ToolUtility.USER_KEY, user);
                        String url = ToolUtility.getCookieValue(Mvcs.getReq(), "url");
                        ToolUtility.deleteCookie(Mvcs.getReq(), Mvcs.getResp(), "url", true);
                        if (StringUtil.isBlank(url))
                            return ToolUtility.WEBSITE_URL;
                        else
                            return url;
                    } else
                        return "/error-505.html";
                } else {

                    User user = new User();
                    user.setPassword("QuickLogin");
                    user.setMailVerify("1");
                    user.setMobile(null);
                    user.setEmail(openId + "@quicklogin.alipay");
                    user.setQq(null);
                    user.setRegIp(ip);
                    user.setSalt("fakafa");
                    user.setRegDate(new Date());
                    user.setAnswer(null);
                    user.setQuestion(null);
                    dao().insert(user);
                    socialbind = new Socialbind();
                    socialbind.setOpenId(openId);
                    socialbind.setAppId("alipay");
                    socialbind.setBindDate(new Date());
                    socialbind.setToken(token);
                    socialbind.setUid(user.getId());
                    dao().insert(socialbind);

                    LoginLogs logs = new LoginLogs();
                    logs.setIp(ip);
                    logs.setLoginDate(new Date());
                    logs.setUid(user.getId());
                    dao().insert(logs);
                    user.setNickName(real_name);
                    Mvcs.getReq().getSession().setAttribute(ToolUtility.USER_KEY, user);

                    String url = ToolUtility.getCookieValue(Mvcs.getReq(), "url");
                    ToolUtility.deleteCookie(Mvcs.getReq(), Mvcs.getResp(), "url", true);
                    if (StringUtil.isBlank(url))
                        return ToolUtility.WEBSITE_URL;
                    else
                        return url;
                }
            } else
                return "/error-505.html";
        }
    }

    @At("/social/qihuBacktrack")
    @Ok("redirect:${obj}")
    public String qihuBacktrack() throws IOException {

        String code = ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("code").trim());
        if (StringUtil.isBlank(code) || !Mvcs.getReq().getParameter("state").equals("fakafa"))
            return "/error-777.html";
        else {
            String tmp = HttpClientTool.get("https://openapi.360.cn/oauth2/access_token?client_id=bfecafd4d04b225e1edf359ecc2883d9&client_secret=14a5ee761e31d8e03ade5f5ba5072c8a&code="
                    + code
                    + "&grant_type=authorization_code&redirect_uri=http%3A%2F%2Fm.fakafa.com%2Fsocial%2F360Backtrack");

            if (tmp.indexOf("access_token") >= 0 && tmp != null) {

                String access_token = ToolUtility.getValueFromJson(tmp, "access_token", "expires_in");
                Map<String, Object> parms = new HashMap<String, Object>();
                parms.put("access_token", access_token);
                tmp = HttpClientTool.post(
                        "https://openapi.360.cn/user/me.json", parms);

                if (tmp.indexOf("id") >= 0 || tmp == null) {

                    String openId = ToolUtility.getValueFromJson(tmp, "id", "name");
                    Socialbind socialbind = dao().fetch(Socialbind.class, Cnd.where("openid", "=", openId).and("appId", "=", "360"));
                    String ip = ToolUtility.getIpAddr(Mvcs.getReq());
                    String nickName = ToolUtility.getValueFromJson(tmp,
                            "name", "avatar");
                    String avatar = ToolUtility.getValueFromJson(tmp, "avatar", "}");
                    if (socialbind != null) {
                        User user = dao().fetch(User.class, socialbind.getUid());
                        if (user != null) {

                            LoginLogs logs = new LoginLogs();
                            logs.setIp(ip);
                            logs.setUid(user.getId());
                            logs.setLoginDate(new Date());
                            dao().insert(logs);
                            user.setNickName(nickName);
                            user.setAvatar(avatar);
                            Mvcs.getReq().getSession().setAttribute(ToolUtility.USER_KEY, user);

                            String url = ToolUtility.getCookieValue(Mvcs.getReq(), "url");
                            ToolUtility.deleteCookie(Mvcs.getReq(), Mvcs.getResp(), "url", true);
                            if (StringUtil.isBlank(url))
                                return ToolUtility.WEBSITE_URL;
                            else
                                return url;
                        } else
                            return "/error-505.html";
                    } else {

                        User user = new User();
                        user.setPassword("QuickLogin");
                        user.setMailVerify("1");
                        user.setMobile(null);
                        user.setEmail(openId + "@quicklogin.360");
                        user.setQq(null);
                        user.setRegIp(ip);
                        user.setRegDate(new Date());
                        user.setSalt("fakafa");
                        user.setAnswer(null);
                        user.setQuestion(null);
                        dao().insert(user);

                        socialbind = new Socialbind();
                        socialbind.setOpenId(openId);
                        socialbind.setAppId("360");
                        socialbind.setToken(access_token);
                        socialbind.setBindDate(new Date());
                        socialbind.setUid(user.getId());
                        dao().insert(socialbind);

                        LoginLogs logs = new LoginLogs();
                        logs.setIp(ip);
                        logs.setUid(user.getId());
                        logs.setLoginDate(new Date());
                        dao().insert(logs);
                        user.setNickName(nickName);
                        Mvcs.getReq().getSession().setAttribute(ToolUtility.USER_KEY, user);

                        String url = ToolUtility.getCookieValue(Mvcs.getReq(), "url");
                        ToolUtility.deleteCookie(Mvcs.getReq(), Mvcs.getResp(), "url", true);
                        if (StringUtil.isBlank(url))
                            return ToolUtility.WEBSITE_URL;
                        else
                            return url;
                    }
                } else
                    return "/error-777.html";
            } else
                return "/error-777.html";
        }
    }
}
