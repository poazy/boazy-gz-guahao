package boazy.gz.guahao.service;

import boazy.gz.guahao.pojo.EMailParam;
import boazy.gz.guahao.pojo.LoginParam;
import boazy.gz.guahao.pojo.RegParam;
import com.alibaba.fastjson.JSONObject;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GuahaoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GuahaoService.class);

    private static final CloseableHttpClient HTTP_CLIENT = HttpClients.custom()
            .setDefaultCookieStore(new BasicCookieStore())
            .setRedirectStrategy(new LaxRedirectStrategy())
            .build();
    private static final int RETRY_TIMES = 3;

    private String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36";

    private String homePageUrl = "http://www.guahao.gov.cn";

    private EMailParam emailParam;

    public void homepage() {
        HttpUriRequest homepageRequest = RequestBuilder
                .get()
                .setUri(homePageUrl)
                .build();
        homepageRequest.setHeader("User-Agent", userAgent);

        int count = 1;
        CloseableHttpResponse homepageResponse = null;
        while (true) {
            try {
                homepageResponse = HTTP_CLIENT.execute(homepageRequest);
            } catch (Throwable throwable) {
                if (count > RETRY_TIMES) {
                    throw new RuntimeException(throwable.getMessage(), throwable);
                }
                LOGGER.error("访问首页异常");

                count++;
            } finally {
                close(homepageResponse);
            }

            break;
        }
    }

    public String login(LoginParam loginParam) {
        String loginCaptcha = loginParam.getVerCd();
        if (null == loginCaptcha || loginCaptcha.trim().isEmpty()) {
            loginCaptcha = getCaptcha();
        }
        LOGGER.info("登陆验证码：{}", loginCaptcha);

        HttpUriRequest loginRequest = RequestBuilder
                .post()
                .setUri(homePageUrl + "/logined.xhtml?" + Math.random())
                .addParameter("CARD_NO", loginParam.getCardNo())
                .addParameter("VER_CD", loginCaptcha)
                .addParameter("CARD_TYP", loginParam.getCardTyp())
                .addParameter("PWD", loginParam.getPwd())
                .addParameter("LOGIN_SCON", loginParam.getLoginScon())
                .addParameter("IP", loginParam.getIp())
                .build();
        loginRequest.addHeader("User-Agent", userAgent);

        int loginCount = 1;
        CloseableHttpResponse loginResponse = null;
        String loginResp = null;
        while (true) {
            try {
                loginResponse = HTTP_CLIENT.execute(loginRequest);
                loginResp = EntityUtils.toString(loginResponse.getEntity());
                if (loginResp.indexOf("{") > 0) {
                    loginResp = loginResp.substring(loginResp.indexOf("{")); // 去掉响应前面的空白符
                }
            } catch (Throwable throwable) {
                if (loginCount > RETRY_TIMES) {
                    throw new RuntimeException(throwable.getMessage(), throwable);
                }
                LOGGER.error("登陆异常！");
            } finally {
                close(loginResponse);
            }

            if (null != loginResp && !loginResp.trim().isEmpty()) {
                break;
            }

            loginCount++;
        }
        LOGGER.info("loginResp：{}", loginResp);

        return loginResp;
    }

    public String reg(RegParam regParam) {
        String regCaptcha = regParam.getVerId();
        if (null == regCaptcha || regCaptcha.trim().isEmpty()) {
            regCaptcha = getCaptcha();
        }
        LOGGER.info("挂号验证码：{}", regCaptcha);

        RequestBuilder requestBuilder = RequestBuilder
                .post()
                .setUri(homePageUrl + "/guahao.xhtml")
                .addParameter("token", regParam.getToken())
                .addParameter("YB_CHK_FLG", regParam.getYbChkFlg())
                .addParameter("REG_FLG", regParam.getRegFlg())
                .addParameter("REG_PAY_TYP", regParam.getRegPayTyp())
                .addParameter("MED_CODE", regParam.getMedCode())
                .addParameter("HIS_CD", regParam.getHisCd())
                .addParameter("DEP_ID", regParam.getDepId())
                .addParameter("DEP_NM", regParam.getDepNm())
                .addParameter("DOC_ID", regParam.getDocId())
                .addParameter("DOC_NM", regParam.getDocNm())
                .addParameter("REG_DAT", regParam.getRegDat())
                .addParameter("TIM_FLG", regParam.getTimFlg())
                .addParameter("STA_TIM", regParam.getStaTim())
                .addParameter("END_TIM", regParam.getEndTim())
                .addParameter("REG_FEE", regParam.getRegFee())
                .addParameter("TRE_FEE", regParam.getTreFee())
                .addParameter("BBTRE_FEE", regParam.getBbtreFee())
                .addParameter("BBREG_FEE", regParam.getBbregFee())
                .addParameter("VER_ID", regCaptcha)
                .addParameter("OPE_NM", regParam.getOpeNm())
                .addParameter("OPE_SEX", regParam.getOpeSex())
                .addParameter("OPE_IDC", regParam.getOpeIdc())
                .addParameter("CONN_NO", regParam.getConnNo())
                .addParameter("CONN_TYP", regParam.getConnTyp())
                .addParameter("CONN_BIRTH_DT", regParam.getConnBirthDt())
                .addParameter("GRAVIDAFLAG", regParam.getGravidaFlag())
                .addParameter("SUCKLEFLAG", regParam.getSuckleFlag())

                .addHeader("Host", homePageUrl.substring(homePageUrl.indexOf("//") + 2))
                .addHeader("User-Agent", userAgent)
                .addHeader("Accept", "application/json, text/javascript, */*")
                .addHeader("Origin", homePageUrl)
                .addHeader("Accept-Language", "zh-CN,zh;q=0.8")
                .addHeader("X-Requested-With", "XMLHttpRequest")
                .addHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

        HttpUriRequest regRequest = requestBuilder.build();
        LOGGER.info("regReq：{}", JSONObject.toJSONString(requestBuilder.getParameters()));

        int regCount = 1;
        CloseableHttpResponse regResponse = null;
        String regResp = null;
        while (true) {
            try {
                regResponse = HTTP_CLIENT.execute(regRequest);
                regResp = EntityUtils.toString(regResponse.getEntity());
                if(regResp.indexOf("{") > 0) {
                    regResp = regResp.substring(regResp.indexOf("{")); // 去掉响应前面的空白符
                }
            } catch (Throwable throwable) {
                if (regCount > RETRY_TIMES) {
                    throw new RuntimeException(throwable.getMessage(), throwable);
                }
                LOGGER.error("挂号异常");
            } finally {
                close(regResponse);
            }

            if (null != regResp && !regResp.trim().isEmpty()) {
                break;
            }

            regCount++;
        }
        LOGGER.info("regResp：{}", regResp);

        return regResp;
    }

    public String getCaptcha() {
        HttpUriRequest captchaRequest = RequestBuilder
                .get()
                .setUri(homePageUrl + "/pmodule/include/codeImage.jsp?" + Math.random())
                .build();

        int count = 1;
        CloseableHttpResponse response = null;
        while (true) {
            try {
                response = HTTP_CLIENT.execute(captchaRequest);
                InputStream inputStream = response.getEntity().getContent();

                ITesseract instance = new Tesseract();
                BufferedImage image = ImageIO.read(inputStream);
                String captcha = instance.doOCR(image);
                if(captcha.length() > 4) {
                    captcha = captcha.substring(0, 4);
                }
                String newCaptcha = captcha.replaceAll("S|s|\\$", "5");
                newCaptcha = newCaptcha.replaceAll("O|o", "0");
                newCaptcha = newCaptcha.replaceAll("Z|z", "2");
                newCaptcha = newCaptcha.replaceAll("B", "8");
                newCaptcha = newCaptcha.replaceAll("b", "6");
                newCaptcha = newCaptcha.replaceAll("I|i|l", "1");
                newCaptcha = newCaptcha.replaceAll("q|g", "9");
                newCaptcha = newCaptcha.replaceAll("a|A", "4");
                newCaptcha = newCaptcha.replaceAll("T", "7");
                if(!newCaptcha.equals(captcha)) {
                    LOGGER.warn("captcha：{}", captcha);
                    captcha = newCaptcha;
                }

                return captcha;
            } catch (Exception e) {
                if (count > RETRY_TIMES) {
                    throw new RuntimeException(e.getMessage(), e);
                }
                LOGGER.error("获取验证码异常！");
            } finally {
                close(response);
            }

            count++;
        }
    }

    private void close(CloseableHttpResponse response) {
        if (null != response) {
            try {
                EntityUtils.consume(response.getEntity());
            } catch (Exception e) {
                // 安全关闭
            }
            try {
                response.close();
            } catch (Exception e) {
                // 安全关闭
            }
        }
    }

    public String getOrderDetailUrl(String ordNo) {
        String orderDetailUrl = homePageUrl + "/orderdetail.xhtml?ORD_NO=" + ordNo;
        return orderDetailUrl;
    }

    public List<String> getRemainderPeriod(String hisCd, String depId, String docId, String regDat, String tmFlg) {
        // http://www.guahao.gov.cn/ajx_regtime.xhtml?HIS_CD=100202&DEP_ID=2102602FEZX239897&DOC_ID=040&REG_DAT=2018-08-22&TM_FLG=2

        HttpUriRequest captchaRequest = RequestBuilder
                .get()
                .setUri(homePageUrl + "/ajx_regtime.xhtml?r=" + Math.random())
                .addParameter("HIS_CD", hisCd)
                .addParameter("DEP_ID", depId)
                .addParameter("DOC_ID", docId)
                .addParameter("REG_DAT", regDat)
                .addParameter("TM_FLG", tmFlg)
                .build();

        int count = 1;
        CloseableHttpResponse response = null;
        String resp = null;
        while (true) {
            try {
                response = HTTP_CLIENT.execute(captchaRequest);
                resp = EntityUtils.toString(response.getEntity());
            } catch (Throwable throwable) {
                if (count > RETRY_TIMES) {
                    throw new RuntimeException(throwable.getMessage(), throwable);
                }
                LOGGER.error("爬取时段余号异常");
            } finally {
                close(response);
            }

            if (null != resp && !resp.trim().isEmpty()) {
                break;
            }

            count++;
        }
        //LOGGER.info("remainderPeriodResp：{}", resp);

        // submitRegTime('15:00','15:30')
        List<String> remainderPeriods = new ArrayList<>();
        Pattern pattern = Pattern.compile("(?<=submitRegTime\\()('[0-9]{2}:[0-9]{2}','[0-9]{2}:[0-9]{2}')(?=\\))");
        Matcher matcher = pattern.matcher(resp);
        while(matcher.find()) {
            remainderPeriods.add(matcher.group()
                    .replace("','", "-")
                    .replaceAll("'", ""));
        }

        return remainderPeriods;
    }

    public void sendEMail(String regResp) throws MessagingException {
        if(null == emailParam) {
            LOGGER.error("自动（刷）挂号未能发送邮件通知，邮相关参数为空！");
        }

        Properties mailProps = new Properties();
        /*mailProps.put("mail.smtp.host", "applesmtp.126.com");
        mailProps.put("mail.smtp.auth", "true");
        mailProps.put("mail.username", "boazy@126.com");
        mailProps.put("mail.password", "Dwb70822Gry70208");*/
        mailProps.put("mail.smtp.host", emailParam.getMailSmtpHost());
        mailProps.put("mail.smtp.auth", emailParam.getMailAmtpAuth());
        mailProps.put("mail.username", emailParam.getMailUsername());
        mailProps.put("mail.password", emailParam.getMailPassword());

        Authenticator authenticator = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                String userName = mailProps.getProperty("mail.username");
                String password = mailProps.getProperty("mail.password");
                return new PasswordAuthentication(userName, password);
            }
        };

        // 使用环境属性和授权信息，创建邮件会话
        Session mailSession = Session.getInstance(mailProps, authenticator);

        // 创建邮件消息
        MimeMessage message = new MimeMessage(mailSession);
        // 设置发件人
        InternetAddress from = new InternetAddress(mailProps.getProperty("mail.username"));
        message.setFrom(from);
        // 设置收件人
        message.setRecipients(Message.RecipientType.TO, emailParam.getToAddresses());
        // 设置邮件标题
        message.setSubject("自动(刷)挂号成功(广州市妇女儿童医疗中心)，请在30分钟内支付！");
        // 设置邮件的内容体
        message.setContent(regResp, "text/html;charset=UTF-8");
        // 发送邮件
        Transport.send(message);
    }

    public EMailParam getEmailParam() {
        return emailParam;
    }

    public void setEmailParam(EMailParam emailParam) {
        this.emailParam = emailParam;
    }

}
