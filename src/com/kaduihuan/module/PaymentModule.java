package com.kaduihuan.module;

import java.io.IOException;
import java.util.*;

import com.kaduihuan.bean.*;
import com.kaduihuan.pay.*;
import com.kaduihuan.tool.Blowfish;
import com.kaduihuan.tool.CodeCryption;
import com.kaduihuan.tool.SessionManger;
import com.kaduihuan.tool.ToolUtility;

import jodd.util.StringUtil;
import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.annotation.*;
import org.nutz.service.EntityService;

@IocBean(fields = {"dao"})
public class PaymentModule extends EntityService<Payment> {

    @GET
    @At("/payment")
    @Ok("jetx:/payment/payment.jetx")
    public Map<String, Object> payment() throws IOException {

        Integer orderId = ToolUtility.isNumeric(Mvcs.getReq().getParameter("id")) ? Integer.parseInt(ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("id"))) : 0;
        if (!SessionManger.checkLogin(Mvcs.getReq()))
            Mvcs.getResp().sendRedirect("/login.html?returnUrl=/payment.html?id=" + orderId);
        Order order = dao().fetch(Order.class, orderId);
        if (order == null)
            Mvcs.getResp().sendRedirect("/error/404.html");
        if (order.getStatus().equals("1"))
            Mvcs.getResp().sendRedirect("/user/trade.html");
        User user = SessionManger.getCurrentUser(Mvcs.getReq());
        if (!order.getUid().equals(user.getId()))
            Mvcs.getResp().sendRedirect("/error-555.html");
        order.setAccount(Blowfish.decode(order.getAccount(), order.getGid().toString()));
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("order", order);
        if (order.getType().equals("安卓首充号"))
            map.put("hao", dao().fetch(Hao.class, Cnd.where("orderId", "=", order.getId())).getRemark());
        return map;
    }

    @POST
    @At("/payment")
    @Ok("redirect:${obj}")
    public String payment(@Param("payid") String payid) {
        Integer orderId = ToolUtility.isNumeric(Mvcs.getReq().getParameter("orderid")) ? Integer.parseInt(ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("orderid"))) : 0;

        Order order = dao().fetch(Order.class, orderId);
        Payway payway = dao().fetch(Payway.class, Integer.parseInt(payid));
        String ip = ToolUtility.getIpAddr(Mvcs.getReq());
        if (order == null || payway == null)
            return "/error-998.html";
        if (!SessionManger.checkLogin(Mvcs.getReq()))
            return "/login.html?returnUrl=/payment.html?id=" + orderId;

        String url = ToolUtility.WEBSITE_URL;

        Payment payment = new Payment();
        payment.setOrderId(order.getId());
        payment.setUid(order.getUid());
        payment.setIp(ip);
        payment.setStatus("0");
        payment.setPayName(payway.getName());
        payment.setPayWay(payway.getCode());
        payment.setPayGate(payway.getGate());
        payment.setTotal(Double.valueOf(new java.text.DecimalFormat("#.00").format(order.getPrice() * order.getQuantity().doubleValue())));
        payment.setCreateTime(new Date());

        dao().insert(payment);
        if (payway.getGate().equals("TENPAY"))
            url = new Tenpay().create(order, payway, payment);
        else if (payway.getGate().equals("ALIPAY"))
            url = new Alipay().create(order, payway, payment);
        else if (payway.getGate().equals("99BILL"))
            url = new Kuaiqian().create(order, payway, payment);
        else if (payway.getGate().equals("CARDPAY"))
            url = new Cardpay().create(order, payway, payment);
        else
            url = ToolUtility.WEBSITE_URL;
        return url;
    }


    /** ******************************************支付回调******************************************* **/

    /**
     * 支付宝同步回调
     *
     * @return
     * @throws IOException
     */
    @GET
    @At("/alipay/return")
    @Ok("jetx:/payment/alipayreturn.jetx")
    public Map<String, Object> alipayReturn() throws IOException {

        Map<String, Object> map = new HashMap<String, Object>();
        // 商品描述
        String body = CodeCryption.decode("URL", ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("body")));

        String is_success = ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("is_success"));
        // 通知校验ID
        String notify_id = CodeCryption.decode("URL", ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("notify_id")));
        // 通知时间
        String notify_time = CodeCryption.decode("URL", ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("notify_time")));
        // 通知类型
        String notify_type = ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("notify_type"));
        // 商户网站唯一订单号
        String out_trade_no = ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("out_trade_no"));
        // 支付类型
        String payment_type = ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("payment_type"));
        // 卖家支付宝账号
        String seller_id = ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("seller_id"));
        // 商品名称
        String subject = CodeCryption.decode("URL", ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("subject")));
        // 交易金额
        String total_fee = ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("total_fee"));
        // 支付宝交易号
        String trade_no = ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("trade_no"));
        // 交易状态
        String trade_status = ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("trade_status"));
        // 签名方式
        String sign_type = ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("sign_type"));
        // 签名
        String sign = ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("sign"));
        String service = ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("service"));

        if (!seller_id.equals(Alipay.PARTNER))
            Mvcs.getResp().sendRedirect("/error-222.html");

        if (!is_success.equals("T"))
            Mvcs.getResp().sendRedirect("/error-998.html");

        Order order = dao().fetch(Order.class, dao().fetch(Payment.class, Integer.parseInt(out_trade_no)).getId());
        order.setAccount(Blowfish.decode(order.getAccount(), order.getGid().toString()));
        map.put("order", order);
        map.put("trade_no", trade_no);
        map.put("total", total_fee);
        map.put("trade_status", trade_status.equals("TRADE_SUCCESS") ? "成功" : "失败");
        return map;
    }


    /**
     * 支付宝异步回调
     *
     * @return
     */
    @At("/alipay/notify")
    @Ok("raw")
    public String alipayNotify() {

        try {

            // 通知时间
            String notify_time = CodeCryption.decode("URL", ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("notify_time")));
            // 通知类型
            String notify_type = ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("notify_type"));
            // 通知校验ID
            String notify_id = ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("notify_id"));
            // 签名方式
            String sign_type = ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("sign_type"));
            // 签名
            String sign = ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("sign"));
            // 商户网站唯一订单号
            String out_trade_no = ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("out_trade_no"));
            // 商品名称
            String subject = CodeCryption.decode("URL", ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("subject")));
            String orderId = subject.replaceAll("发卡发订单：", "");
            // 支付类型
            String payment_type = ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("payment_type"));
            // 支付宝交易号
            String trade_no = ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("trade_no"));
            // 交易状态
            String trade_status = ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("trade_status"));
            // 交易创建时间
            String gmt_create = CodeCryption.decode("URL", ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("gmt_create")));
            // 交易付款时间
            String gmt_payment = CodeCryption.decode("URL", ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("gmt_payment")));
            // 交易关闭时间
            String gmt_close = CodeCryption.decode("URL", ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("gmt_close")));
            // 卖家支付宝账号
            String seller_email = CodeCryption.decode("URL", ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("seller_email")));
            // 买家支付宝账号
            String buyer_email = CodeCryption.decode("URL", ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("buyer_email")));
            // 卖家支付宝账户号
            String seller_id = ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("seller_id"));
            // 买家支付宝账户号
            String buyer_id = ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("buyer_id"));
            // 商品单价
            String price = ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("price"));
            // 交易金额
            String total_fee = ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("total_fee"));
            // 购买数量
            String quantity = ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("quantity"));
            // 商品描述
            String body = CodeCryption.decode("URL", ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("body")));
            // 折扣
            String discount = ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("discount"));
            // 是否调整总价
            String is_total_fee_adjust = ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("is_total_fee_adjust"));
            // 是否使用红包买家
            String use_coupon = ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("use_coupon"));
            // 退款状态
            String refund_status = ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("refund_status"));
            // 退款时间
            String gmt_refund = CodeCryption.decode("URL", ToolUtility.transactSQLInjection(Mvcs.getReq().getParameter("gmt_refund")));

            StringBuffer sb = new StringBuffer();
            sb.append("body=").append(body);
            if (!StringUtil.isBlank(buyer_email))
                sb.append("&buyer_email=").append(buyer_email);
            if (!StringUtil.isBlank(buyer_id))
                sb.append("&buyer_id=").append(buyer_id);
            if (!StringUtil.isBlank(discount))
                sb.append("&discount=").append(discount);
            if (!StringUtil.isBlank(gmt_close))
                sb.append("&gmt_close=").append(gmt_close);
            if (!StringUtil.isBlank(gmt_create))
                sb.append("&gmt_create=").append(gmt_create);
            if (!StringUtil.isBlank(gmt_payment))
                sb.append("&gmt_payment=").append(gmt_payment);
            if (!StringUtil.isBlank(gmt_refund))
                sb.append("&gmt_refund=").append(gmt_refund);
            if (!StringUtil.isBlank(is_total_fee_adjust))
                sb.append("&is_total_fee_adjust=").append(is_total_fee_adjust);
            sb.append("&notify_id=").append(notify_id);
            sb.append("&notify_time=").append(notify_time);
            sb.append("&notify_type=").append(notify_type);
            if (!StringUtil.isBlank(out_trade_no))
                sb.append("&out_trade_no=").append(out_trade_no);
            if (!StringUtil.isBlank(payment_type))
                sb.append("&payment_type=").append(payment_type);
            if (!StringUtil.isBlank(price))
                sb.append("&price=").append(price);
            if (!StringUtil.isBlank(quantity))
                sb.append("&quantity=").append(quantity);
            if (!StringUtil.isBlank(refund_status))
                sb.append("&refund_status=").append(refund_status);
            if (!StringUtil.isBlank(seller_email))
                sb.append("&seller_email=").append(seller_email);
            if (!StringUtil.isBlank(seller_id))
                sb.append("&seller_id=").append(seller_id);
            if (!StringUtil.isBlank(subject))
                sb.append("&subject=").append(subject);
            if (!StringUtil.isBlank(total_fee))
                sb.append("&total_fee=").append(total_fee);
            if (!StringUtil.isBlank(trade_no))
                sb.append("&trade_no=").append(trade_no);
            if (!StringUtil.isBlank(trade_status))
                sb.append("&trade_status=").append(trade_status);
            if (!StringUtil.isBlank(use_coupon))
                sb.append("&use_coupon=").append(use_coupon);
            sb.append(Alipay.MD5_KEY);
            String v = CodeCryption.encode("MD5", sb.toString());
            String msg = "fail";
            if (!sign.equals(v) || !seller_id.equals(Alipay.PARTNER))
                msg = "fail";
            if (new Alipay().verify(notify_id)) {

                if (dao().fetch(Payment.class, Cnd.where("tsn", "=", trade_no).and("payGate", "=", "ALIPAY")) == null && trade_status.equals("TRADE_SUCCESS")) {
                    Sql sql = Sqls.create("call pro_payment_order(@TSN, @BACKCODE, @BANKTYPE, @BANKBILLNO, @REMARK, @ORDERID, @ID, @ACTUAL");
                    sql.params().set("TSN", trade_no)
                            .set("BACKCODE", trade_status)
                            .set("BANKTYPE", "支付宝账号")
                            .set("BANKBILLNO", buyer_email)
                            .set("REMARK", null)
                            .set("ORDERID", orderId)
                            .set("ID", out_trade_no)
                            .set("ACTUAL", total_fee);
                    dao().execute(sql);
                }
                msg = "success";
            } else
                msg = "fail";
            return msg;
        } catch (Exception e) {
            return "fail";
        }
    }
}