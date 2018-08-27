package boazy.gz.guahao;

import boazy.gz.guahao.pojo.EMailParam;
import boazy.gz.guahao.pojo.LoginParam;
import boazy.gz.guahao.pojo.RegParam;
import org.junit.Before;
import org.junit.Test;

public class RegTests {
    private EMailParam emailParam;
    private LoginParam loginParam;

    @Before
    public void regInit() {
        // 创建邮件参数
        emailParam = new EMailParam("applesmtp.126.com"
                , "boazy@126.com", "********");
        emailParam.setToAddresses("boazy@qq.com");
        // 创建登陆参数
        loginParam = new LoginParam("username", "********");
    }

    @Test
    public void reg1803() {
        // 创建挂号参数
        RegParam regParam = new RegParam();
        regParam.setYbChkFlg("1");
        regParam.setRegFlg("0");
        regParam.setRegPayTyp("");
        regParam.setMedCode("");
        regParam.setHisCd("100202");
        regParam.setDepId("20007301FEZX239894");
        regParam.setDepNm("口腔正畸科（珠）主治");
        regParam.setDocId("1803");
        regParam.setDocNm("李新桂");

        regParam.setRegDat("2018-09-03");
        regParam.setTimFlg("1");
        regParam.setStaTim("08:00");
        regParam.setEndTim("08:30");

        regParam.setRegFee("0");
        regParam.setTreFee("1000");
        regParam.setBbtreFee("1300");
        regParam.setBbregFee("0");
        regParam.setVerId(null);

        regParam.setOpeNm("段世骏");
        regParam.setOpeSex("M");
        regParam.setOpeIdc("430481************");
        regParam.setConnNo("00000*******");
        regParam.setConnTyp("1");
        regParam.setConnBirthDt("201*-0*-1*");

        regParam.setGravidaFlag("");
        regParam.setSuckleFlag("");

        GuahaoPattern.regPattern1(loginParam, regParam, emailParam, 1000L * 5);
    }

}
