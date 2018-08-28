package boazy.gz.guahao;

import boazy.gz.guahao.pojo.EMailParam;
import boazy.gz.guahao.pojo.LoginParam;
import boazy.gz.guahao.pojo.RegParam;
import boazy.gz.guahao.service.GuahaoService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class GuahaoPattern {
    private static final Logger LOGGER = LoggerFactory.getLogger(GuahaoPattern.class);
    private static final int RETRY_TIMES = 5;

    /**
     * 挂号模式1
     * 首先爬取有余号时间段数据
     * 访问首页页面
     * 登陆
     * 挂号（挂最早号有号的时段号）
     *
     * @param loginParam 登陆参数
     * @param regParam 挂号参数
     * @param emailParam 发送邮件参数
     * @param waitTime 刷号频率
     */
    public static void regPattern1(LoginParam loginParam, RegParam regParam, EMailParam emailParam, Long waitTime) {
        GuahaoService guahaoService = new GuahaoService();
        guahaoService.setEmailParam(emailParam);

        String regSETimes = null;
        if(null != regParam.getStaTim() && null != regParam.getEndTim()) {
            regSETimes = regParam.getStaTim() + "-" + regParam.getEndTim();
        }

        List<String> periods;
        while (true) {
            // 首先爬取有余号时间段数据
            periods = guahaoService.getRemainderPeriod(
                    regParam.getHisCd(), regParam.getDepId()
                    , regParam.getDocId(), regParam.getRegDat(), regParam.getTimFlg());
            if (null == periods || periods.isEmpty()) {
                LOGGER.warn("（{}|{}|{}|{}|{}）没有爬取到有余号时段！继续努力爬取中..."
                        , regParam.getHisCd(), regParam.getDepNm(), regParam.getDocNm()
                        , regParam.getRegDat(), regParam.getTimFlg()
                );
            } else if(null == regSETimes) {
                break;
            } else if(null != regSETimes && periods.contains(regSETimes)) {
                break;
            }

            if(null != regSETimes) {
                LOGGER.warn("（{}|{}|{}|{}|{}|{}）没有爬取到有余号时段！继续努力爬取中..."
                        , regParam.getHisCd(), regParam.getDepNm(), regParam.getDocNm()
                        , regParam.getRegDat(), regParam.getTimFlg(), regSETimes
                );
            }

            if(null != waitTime) {
                try {
                    Thread.sleep(waitTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        }

        // 1、访问首页页面
        guahaoService.homepage();
        // 2、登陆
        int count = 1;
        while (count <= RETRY_TIMES) {

            String loginResp = guahaoService.login(loginParam);
            JSONObject jsonObject = JSONObject.parseObject(loginResp);
            if ("SUC" .equals(jsonObject.getString("RSP_MAP"))) {
                LOGGER.info("登陆成功");
                break;
            }

            JSONObject gwaObject = jsonObject.getJSONObject("GWA");
            LOGGER.error("登陆失败：" + gwaObject.getString("MSG_INF"));

            count++;
        }
        // 3、挂号
        LOGGER.info("有余号的时段：{}", periods);
        LOGGER.info("提交挂号：{}|{}|{}|{}|{}|{}"
                , regParam.getHisCd(), regParam.getDepNm(), regParam.getDocNm()
                , regParam.getRegDat(), regParam.getTimFlg(), null != regSETimes ? regSETimes : periods.get(0)
        );
        String[] seTimes = periods.get(0).split("-");
        if(null != regSETimes) {
            seTimes = regSETimes.split("-");
        }
        regParam.setStaTim(seTimes[0]);
        regParam.setEndTim(seTimes[1]);
        count = 1;
        while (count <= RETRY_TIMES) {

            String regResp = guahaoService.reg(regParam);
            JSONObject jsonObject = JSONObject.parseObject(regResp);
            count++;
            if ("SUC" .equals(jsonObject.getString("RSP_MAP"))) {
                LOGGER.info("挂号成功，请在30分钟内支付！");

                String ordNo = jsonObject.getString("ORD_NO");
                if (null != ordNo && !ordNo.trim().isEmpty()) {
                    String orderDetailUrl = guahaoService.getOrderDetailUrl(ordNo);
                    LOGGER.info("订单详细信息URL：{}", orderDetailUrl);

                    // 邮件通知
                    try {
                        guahaoService.sendEMail(orderDetailUrl + "<br />" + regResp);
                    } catch (Exception e) {
                        LOGGER.error("发送挂号成功邮件时发生异常！");
                    }

                    break; // 挂号成功退出
                } else {
                    LOGGER.error("ORD_NO is null.");
                }

                continue;
            }

            JSONObject gwaObject = jsonObject.getJSONObject("GWA");
            if(null != gwaObject) {
                LOGGER.error("挂号失败：" + gwaObject.getString("MSG_INF"));
            } else {
                LOGGER.error("挂号失败：未知问题，检查代码程序！" );

            }
        }

        regPattern1(loginParam, regParam, emailParam, waitTime);
    }

}
