package com.kaduihuan.module;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.EntityService;

import com.kaduihuan.bean.Seo;

@IocBean(fields={"dao"})
public class SeoModule extends EntityService<Seo>{

}