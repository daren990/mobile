package com.kaduihuan.module;

import com.kaduihuan.bean.*;
import com.kaduihuan.tool.Blowfish;
import com.kaduihuan.tool.CodeCryption;
import com.kaduihuan.tool.SessionManger;
import com.kaduihuan.tool.ToolUtility;
import jodd.util.StringUtil;
import org.nutz.dao.Cnd;
import org.nutz.dao.sql.Criteria;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.annotation.*;
import org.nutz.service.EntityService;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@IocBean(fields = {"dao"})
public class UserModule extends EntityService<User> {

    @GET
    @At("/user/index")
    @Ok("jetx:/user/index.jetx")
    public Map<String, Object> userIndex() throws IOException {

        if (!SessionManger.checkLogin(Mvcs.getReq()))
            Mvcs.getResp().sendRedirect("/login.html?returnUrl=/user/index.html");
        Map<String, Object> map = new HashMap<String, Object>();
        User user = SessionManger.getCurrentUser(Mvcs.getReq());
        map.put("user", user);
        LoginLogs logs = dao().fetch(LoginLogs.class, Cnd.where("uid", "=", user.getId()).limit(1, 1).desc("loginDate"));
        map.put("logs", logs);
        return map;
    }

    @GET
    @At("/user/modifypswd")
    @Ok("jetx:/user/modifypswd.jetx")
    public Map<String, Object> modifypswd() throws IOException {

        if (!SessionManger.checkLogin(Mvcs.getReq()))
            Mvcs.getResp().sendRedirect("/login.html?returnUrl=/user/modifypswd.html");
        Map<String, Object> map = new HashMap<String, Object>();
        User user = SessionManger.getCurrentUser(Mvcs.getReq());
        map.put("user", user);
        return map;
    }

    @POST
    @At("/user/realauth")
    @Ok("redirect:${obj}")
    public String realauth(@Param("realname") String realName, @Param("idcard") String idCard) {

        if (!SessionManger.checkLogin(Mvcs.getReq()))
            return "/login.html?returnUrl=/user/realauth.html";
        Map<String, Object> map = new HashMap<String, Object>();
        User user = SessionManger.getCurrentUser(Mvcs.getReq());
        if (ToolUtility.verifyIdCard(idCard) && ToolUtility.verifyChinese(realName)) {
            Realauth realauth = new Realauth();
            realauth.setUid(user.getId());
            realauth.setRealName(realName);
            realauth.setIdCard(idCard);
            realauth.setAddTime(new Date());
            realauth.setAuthTime(new Date());
            realauth.setStatus("1");
            dao().insert(realauth);
            return "/user/realauth.html";
        } else
            return "/user/realauth.html";
    }

    @GET
    @At("/user/realauth")
    @Ok("jetx:/user/realauth.jetx")
    public Map<String, Object> realauth() throws IOException {

        if (!SessionManger.checkLogin(Mvcs.getReq()))
            Mvcs.getResp().sendRedirect("/login.html?returnUrl=/user/realauth.html");
        Map<String, Object> map = new HashMap<String, Object>();
        User user = SessionManger.getCurrentUser(Mvcs.getReq());
        map.put("user", user);
        Realauth realauth = dao().fetch(Realauth.class, user.getId());
        map.put("realauth", realauth);
        return map;
    }

    @GET
    @At("/user/trade")
    @Ok("jetx:/user/trade.jetx")
    public Map<String, Object> trade() throws IOException {

        if (!SessionManger.checkLogin(Mvcs.getReq()))
            Mvcs.getResp().sendRedirect("/login.html?returnUrl=/user/trade.html");
        Map<String, Object> map = new HashMap<String, Object>();
        User user = SessionManger.getCurrentUser(Mvcs.getReq());
        map.put("user", user);

        String status = StringUtil.isBlank(Mvcs.getReq().getParameter("status")) ? "a" : ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("status"));
        Criteria cri = Cnd.cri();
        cri.where().and("uid", "=", user.getId());
        if (status.equals("a"))
            cri.where().and("1", "=", "1");
        else
            cri.where().and("status", "=", status);
        cri.getOrderBy().desc("orderTime");
        List<Order> orders = dao().query(Order.class, cri);
        map.put("orders", orders);
        return map;
    }

    @GET
    @At("/user/detail")
    @Ok("jetx:/user/detail.jetx")
    public Map<String, Object> detail() throws IOException {

        String id = ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("id"));
        if (!SessionManger.checkLogin(Mvcs.getReq()))
            Mvcs.getResp().sendRedirect("/login.html?returnUrl=/user/detail.html?id=" + id);
        Map<String, Object> map = new HashMap<String, Object>();
        User user = SessionManger.getCurrentUser(Mvcs.getReq());
        map.put("user", user);
        Order order = dao().fetch(Order.class, Cnd.where("id", "=", id).and("uid", "=", user.getId()));
        order.setAccount(Blowfish.decode(order.getAccount(), order.getGid().toString()));
        order.setPswd(Blowfish.decode(order.getPswd(), order.getGid().toString()));
        map.put("order", order);
        Payment payment = dao().fetch(Payment.class, Cnd.wrap("orderId = " + id + " status != '0' and tsn is not null", null));
        map.put("payment", payment);
        if (order.getType().equals("安卓首充号"))
            map.put("hao", dao().fetch(Hao.class, Cnd.where("orderId", "=", id)).getRemark());
        return map;
    }

    @POST
    @At("/user/modifypswd")
    @Ok("redirect:${obj}")
    public String modifypswd(@Param("pswd") String pswd, @Param("password") String password) {

        if (!SessionManger.checkLogin(Mvcs.getReq()))
            return "/login.html?returnUrl=/user/modifypswd.html";
        Map<String, Object> map = new HashMap<String, Object>();
        User user = SessionManger.getCurrentUser(Mvcs.getReq());
        if (CodeCryption.encode("MD5", CodeCryption.encode("MD5", password) + user.getSalt()).equals(user.getPassword())) {
            String salt = ToolUtility.getRandomString(6);
            user.setSalt(salt);
            user.setPassword(CodeCryption.encode("MD5", CodeCryption.encode("MD5", pswd) + salt));
            dao().update(user);
            return "/logout.html";
        } else
            return "/user/modifypswd.html";
    }


}