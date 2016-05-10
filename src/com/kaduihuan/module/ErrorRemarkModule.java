package com.kaduihuan.module;


import com.kaduihuan.bean.User;
import com.kaduihuan.tool.SessionManger;
import com.kaduihuan.tool.ToolUtility;
import jodd.util.StringUtil;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.service.EntityService;

import com.kaduihuan.bean.ErrorRemark;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@IocBean(fields = {"dao"})
public class ErrorRemarkModule extends EntityService<ErrorRemark> {
    
    @At("/error")
    @Ok("jetx:/error.jetx")
    public Map<String, Object> error() throws IOException {

        String code = StringUtil.isBlank(ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("code").trim())) ? "404" : ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("code").trim());
        ErrorRemark error = dao().fetch(ErrorRemark.class, code);
        if (error == null)
            Mvcs.getResp().sendRedirect("/error/404.html");
        Map<String, Object> map = new HashMap<String, Object>();
        User user = SessionManger.getCurrentUser(Mvcs.getReq());
        map.put("user", user);
        map.put("error", error);
        return map;
    }
}