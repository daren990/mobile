package com.kaduihuan.module;

import com.kaduihuan.bean.*;
import com.kaduihuan.tool.Blowfish;
import com.kaduihuan.tool.SessionManger;
import com.kaduihuan.tool.ToolUtility;
import jodd.util.StringUtil;
import org.nutz.dao.Cnd;
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
public class OrderModule extends EntityService<Order> {


    @GET
    @At("/ibuy")
    @Ok("jetx:/buy/ibuy.jetx")
    public Map<String, Object> ibuy() throws IOException {

        Integer id = ToolUtility.isNumeric(Mvcs.getReq().getParameter("id")) ? Integer.parseInt(ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("id"))) : 0;
        Game game = dao().fetch(Game.class, id);
        if (game == null)
            Mvcs.getResp().sendRedirect("/error/404.html");
        if (game.getPlid() != 1)
            Mvcs.getResp().sendRedirect("/apple.html");
        String oid = Mvcs.getReq().getParameter("oid");
        String sid = Mvcs.getReq().getParameter("sid");
        String aid = Mvcs.getReq().getParameter("aid");
        String pid = Mvcs.getReq().getParameter("pid");
        Order order = new Order();
        order.setOid(StringUtil.isBlank(oid) ? null : Integer.parseInt(oid));
        order.setSid(StringUtil.isBlank(sid) ? null : Integer.parseInt(sid));
        order.setAid(StringUtil.isBlank(aid) ? null : Integer.parseInt(aid));
        order.setPid(StringUtil.isBlank(pid) ? null : Integer.parseInt(pid));
        List<Operator> operators = dao().query(Operator.class, Cnd.where("gid", "=", game.getId()));
        Map<String, Object> map = new HashMap<String, Object>();
        User user = SessionManger.getCurrentUser(Mvcs.getReq());
        map.put("user", user);
        map.put("order", order);
        map.put("game", game);
        map.put("operators", operators);
        return map;
    }


    @POST
    @At("/ibuy")
    @Ok("redirect:${obj}")
    public String ibuy(@Param("pswd") String pswd) {

        Integer gid = ToolUtility.isNumeric(Mvcs.getReq().getParameter("gid")) ? Integer.parseInt(ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("gid"))) : 0;
        Game game = dao().fetch(Game.class, gid);
        Integer sid = ToolUtility.isNumeric(Mvcs.getReq().getParameter("sid")) ? Integer.parseInt(ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("sid"))) : 0;
        Server sever = dao().fetch(Server.class, sid);
        Integer oid = ToolUtility.isNumeric(Mvcs.getReq().getParameter("oid")) ? Integer.parseInt(ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("oid"))) : 0;
        Operator operator = dao().fetch(Operator.class, oid);
        Integer pid = ToolUtility.isNumeric(Mvcs.getReq().getParameter("pid")) ? Integer.parseInt(ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("pid"))) : 0;
        Par par = dao().fetch(Par.class, pid);
        Integer aid = ToolUtility.isNumeric(Mvcs.getReq().getParameter("aid")) ? Integer.parseInt(ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("aid"))) : 0;
        Area area = dao().fetch(Area.class, aid);
        Integer quantity = ToolUtility.isNumeric(Mvcs.getReq().getParameter("quantity")) ? 0 : Integer.parseInt(ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("quantity")));
        String account = ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("account")).trim();
        String juese = ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("juese")).trim();
        String qq = ToolUtility.verifyQQ(Mvcs.getReq().getParameter("qq")) ? ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("qq")).trim() : "";
        String mobile = ToolUtility.verifyMobile(Mvcs.getReq().getParameter("mobile")) ? ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("mobile")).trim() : "";
        pswd = ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("pswd")).trim();

        if (game == null || sever == null || operator == null || par == null
                || area == null || quantity > 11 || quantity < 0
                || StringUtil.isBlank(account) || StringUtil.isBlank(pswd))

            return "/error-505.html";

        if (game.getPlid() != 1)
            return "/apple.html";

        StringBuffer buffer = new StringBuffer();
        buffer.append("/ibuy.jspx?id=").append(gid);
        if (sid != null)
            buffer.append("&sid=").append(sid);
        if (aid != null)
            buffer.append("&aid=").append(aid);
        if (oid != null)
            buffer.append("&oid=").append(oid);
        if (pid != null)
            buffer.append("&pid=").append(pid);

        if (!SessionManger.checkLogin(Mvcs.getReq())) {
            ToolUtility.setCookie(Mvcs.getReq(), Mvcs.getResp(), "url", buffer.toString(), -1, true);
            return "/login.html?returnUrl=" + buffer.toString();
        }

        User user = SessionManger.getCurrentUser(Mvcs.getReq());
        String source = "WAP";
        Order order = new Order();
        order.setAccount(Blowfish.encode(account, game.getId().toString()));
        order.setPswd(Blowfish.encode(pswd, game.getId().toString()));
        order.setGid(game.getId());
        order.setGname(game.getGname());
        order.setUid(user.getId());
        order.setSid(sever.getId());
        order.setSname(sever.getSname());
        order.setMobile(mobile);
        order.setSource(source);
        order.setStatus("0");
        order.setSource("fakafa");
        order.setQq(qq);
        order.setJuese(juese);
        order.setAid(area.getId());
        order.setAname(area.getAname());
        order.setFace(par.getFace());
        order.setQuantity(quantity);
        order.setOname(operator.getOname());
        order.setOid(operator.getId());
        order.setStandard(par.getStandard());
        order.setPid(par.getId());
        order.setIp(ToolUtility.getIpAddr(Mvcs.getReq()));
        order.setNum(sever.getNum());
        order.setPname(game.getPname());
        order.setPlid(1);
        order.setType("官方代充");
        order.setPrice(par.getPrice());
        order.setOrderTime(new Date());
        dao().insert(order);
        return "/payment.html?id=" + order.getId();
    }


}