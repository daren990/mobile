package com.kaduihuan.module;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.kaduihuan.bean.User;
import com.kaduihuan.tool.SessionManger;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.service.EntityService;

import com.kaduihuan.bean.Notice;

@IocBean(fields = {"dao"})
public class NoticeModule extends EntityService<Notice> {

    @GET
    @At("/notice")
    @Ok("jetx:/notice.jetx")
    public Map<String, Object> noticeList() throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = SessionManger.getCurrentUser(Mvcs.getReq());
        map.put("user", user);
        return map;
    }
}