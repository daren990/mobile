package com.kaduihuan.pay;

import com.kaduihuan.bean.Order;
import com.kaduihuan.bean.Payment;
import com.kaduihuan.bean.Payway;
import com.kaduihuan.tool.CodeCryption;
import com.kaduihuan.tool.HttpClientTool;
import com.kaduihuan.tool.ToolUtility;

import java.util.HashMap;
import java.util.Map;

/**
 * 支付宝支付
 *
 * @author Howe
 */
public class Alipay {

    /**
     * 合作者身份ID
     */
    public final static String PARTNER = "222222222222";
    /**
     * 密钥
     */
    public final static String MD5_KEY = "11111111111111";

    public final static String INPUT_CHARSET = "utf-8";

    public final static String SIGN_TYPE = "MD5";

    public final static String SELLER_ACCOUNT_NAME = "x@x.com";

    /**
     * 页面跳转同步通知页面路径
     */
    public final static String RETURN_URL = "https://m.fakafa.com/alipay/return.html";
    public final static String NOTIFY_URL = "https://m.fakafa.com/alipay/notify.html";
    public final static String ALIPAY_GATEWAY = "https://mapi.alipay.com/gateway.do";
    /**
     * 操作中断返回地址
     */
    public final static String MERCHANT_URL = "https://m.fakafa.com/";

    /**
     * @param order
     * @param payway
     * @param payment
     * @return
     */
    public String create(Order order, Payway payway, Payment payment) {

        // 商品描述
        String body = order.getType() + order.getFace() + "元("
                + order.getStandard() + ")×" + order.getQuantity() + "-"
                + order.getGname() + order.getPname() + "-" + order.getOname()
                + "-" + order.getAname() + order.getNum() + "服("
                + order.getSname() + ")";
        // 超时时间
        String it_b_pay = "30m";
        // 卖家支付宝用户号
        String seller_id = PARTNER;
        // 商品展示网址
        String show_url = "https://m.fakafa.com/game-" + order.getGid() + ".html";
        // 接口名称
        String service = "alipay.wap.create.direct.pay.by.user";
        // 商品名称
        String subject = "发卡发订单：" + order.getId();
        // 合作者身份ID
        String partner = PARTNER;
        // 参数编码字符集
        String _input_charset = INPUT_CHARSET;
        // 签名方式
        String sign_type = SIGN_TYPE;
        // 支付类型
        String payment_type = "1";
        // 服务器异步通知页面路径
        String notify_url = NOTIFY_URL;
        // 页面跳转同步通知页面路径
        String return_url = RETURN_URL;
        // 商户网站唯一订单号
        String out_trade_no = payment.getId().toString();
        // 交易金额
        Double total_fee = payment.getTotal();

        StringBuffer sbuffer = new StringBuffer();
        sbuffer.append("_input_charset=").append(_input_charset);
        sbuffer.append("&body=").append(body);
        sbuffer.append("&it_b_pay=").append(it_b_pay);
        sbuffer.append("&notify_url=").append(notify_url);
        sbuffer.append("&out_trade_no=").append(out_trade_no);
        sbuffer.append("&partner=").append(partner);
        sbuffer.append("&payment_type=").append(payment_type);
        sbuffer.append("&return_url=").append(return_url);
        sbuffer.append("&seller_id=").append(seller_id);
        sbuffer.append("&service=").append(service);
        sbuffer.append("&show_url=").append(show_url);
        sbuffer.append("&subject=").append(subject);
        sbuffer.append("&total_fee=").append(total_fee);

        return ALIPAY_GATEWAY + "?" + sbuffer.toString() + "&sign=" + CodeCryption.encode("MD5",sbuffer.append(MD5_KEY).toString());
    }

    public boolean verify(String id) {

        // 接口版本
        String service = "notify_verify";
        // 商户号
        String partner = PARTNER;
        // 通知ID
        String notify_id = id;
        Map<String, Object> parms = new HashMap<String, Object>();
        parms.put("service", service);
        parms.put("partner", partner);
        parms.put("notify_id", notify_id);
        return Boolean.parseBoolean(HttpClientTool.post(ALIPAY_GATEWAY, parms));
    }
}