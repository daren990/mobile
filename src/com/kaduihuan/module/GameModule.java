package com.kaduihuan.module;

import com.kaduihuan.bean.*;
import com.kaduihuan.tool.SessionManger;
import com.kaduihuan.tool.ToolUtility;
import jodd.util.StringUtil;
import org.nutz.dao.Cnd;
import org.nutz.dao.sql.Criteria;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.service.EntityService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@IocBean(fields = {"dao"})
public class GameModule extends EntityService<Game> {

    @GET
    @At("/apple")
    @Ok("jetx:/game/apple.jetx")
    public Map<String, Object> appleIndex() throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = SessionManger.getCurrentUser(Mvcs.getReq());
        map.put("user", user);
        List<Game> games = dao().query(Game.class, Cnd.where("plid", "=", 1).and("isHot", "=", "1").and("shelf", "=", "1").desc("id"));
        map.put("games", games);
        map.put("sort", "HOT");
        return map;
    }

    @GET
    @At("/apple/sort")
    @Ok("jetx:/game/apple.jetx")
    public Map<String, Object> appleSort() throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        String sort = StringUtil.isBlank(ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("sort"))) ? "HOT" : ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("sort"));
        User user = SessionManger.getCurrentUser(Mvcs.getReq());
        map.put("user", user);
        Criteria cri = Cnd.cri();
        cri.where().and("plid", "=", "1").and("shelf", "=", "1");
        if (sort.equals("ABCDEF"))
            cri.where().and(Cnd.exps("py", "like", "A%").or("py", "like", "B%").or("py", "like", "C%").or("py", "like", "D%").or("py", "like", "E%").or("py", "like", "F%"));
        else if (sort.equals("GHIJK"))
            cri.where().and(Cnd.exps("py", "like", "G%").or("py", "like", "H%").or("py", "like", "I%").or("py", "like", "J%").or("py", "like", "K%"));
        else if (sort.equals("LMNOP"))
            cri.where().and(Cnd.exps("py", "like", "L%").or("py", "like", "L%").or("py", "like", "N%").or("py", "like", "O%").or("py", "like", "P%"));
        else if (sort.equals("QRSTU"))
            cri.where().and(Cnd.exps("py", "like", "Q%").or("py", "like", "R%").or("py", "like", "S%").or("py", "like", "T%").or("py", "like", "U%"));
        else if (sort.equals("VWXYZ"))
            cri.where().and(Cnd.exps("py", "like", "V%").or("py", "like", "W%").or("py", "like", "X%").or("py", "like", "Y%").or("py", "like", "Z%"));
        else
            cri.where().and("isHot", "=", "1");

        cri.getOrderBy().asc("py").desc("id");
        List<Game> games = dao().query(Game.class, cri);
        map.put("games", games);
        map.put("sort", sort);
        return map;
    }

    @GET
    @At("/sitemap")
    @Ok("jetx:/game/sitemap.jetx")
    public Map<String, Object> sitemap() throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = SessionManger.getCurrentUser(Mvcs.getReq());
        map.put("user", user);
        List<Game> games = dao().query(Game.class, Cnd.where("plid", "=", 1).and("shelf", "=", "1").desc("id"));
        map.put("games", games);
        return map;
    }

    @Ok("json")
    @At("/getGameList")
    public List<Game> getGameList() {
        Integer id = ToolUtility.isNumeric(Mvcs.getReq().getParameter("plid")) ? Integer.parseInt(ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("plid"))) : 0;
        return dao().query(Game.class, Cnd.where("plid", "=", id));
    }

    @Ok("json")
    @At("/getOperatorList")
    public List<Operator> getOperatorList() {
        Integer gid = ToolUtility.isNumeric(Mvcs.getReq().getParameter("gid")) ? Integer.parseInt(ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("gid"))) : 0;
        return dao().query(Operator.class, Cnd.where("gid", "=", gid));
    }

    @Ok("json")
    @At("/getParList")
    public List<Par> getParList() {
        Integer gid = ToolUtility.isNumeric(Mvcs.getReq().getParameter("gid")) ? Integer.parseInt(ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("gid"))) : 0;
        Integer oid = ToolUtility.isNumeric(Mvcs.getReq().getParameter("oid")) ? Integer.parseInt(ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("oid"))) : 0;
        Integer tid = ToolUtility.isNumeric(Mvcs.getReq().getParameter("tid")) ? Integer.parseInt(ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("tid"))) : 0;
        return dao().query(Par.class, Cnd.where("gid", "=", gid).and("oid", "=", oid).and("tid", "=", tid).asc("face"));
    }

    @Ok("json")
    @At("/getServerList")
    public List<Server> getServerList() {
        Integer gid = ToolUtility.isNumeric(Mvcs.getReq().getParameter("gid")) ? Integer.parseInt(ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("gid"))) : 0;
        Integer oid = ToolUtility.isNumeric(Mvcs.getReq().getParameter("oid")) ? Integer.parseInt(ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("oid"))) : 0;
        Integer aid = ToolUtility.isNumeric(Mvcs.getReq().getParameter("aid")) ? Integer.parseInt(ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("aid"))) : 0;
        return dao().query(Server.class, Cnd.where("gid", "=", gid).and("oid", "=", oid).and("aid", "=", aid).asc("num"));
    }

    @Ok("json")
    @At("/getAreaList")
    public List<Area> getAreaList() {
        Integer gid = ToolUtility.isNumeric(Mvcs.getReq().getParameter("gid")) ? Integer.parseInt(ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("gid"))) : 0;
        Integer oid = ToolUtility.isNumeric(Mvcs.getReq().getParameter("oid")) ? Integer.parseInt(ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("oid"))) : 0;
        return dao().query(Area.class, Cnd.where("gid", "=", gid).and("oid", "=", oid));
    }

//    @Ok("json")
//    @At("/getAreaList")
//    public Hao getAreaList() {
//        Integer gid = ToolUtility.isNumeric(Mvcs.getReq().getParameter("gid")) ? Integer.parseInt(ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("gid"))) : 0;
//        String oid = StringUtil.isBlank(Mvcs.getReq().getParameter("account")) ? null : ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("account"));
//        return dao().query(Area.class, Cnd.where("gid", "=", gid).and("oid", "=", oid));
//    }
}