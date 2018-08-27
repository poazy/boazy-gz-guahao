package boazy.gz.guahao;

import boazy.gz.guahao.pojo.EMailParam;
import boazy.gz.guahao.pojo.LoginParam;
import boazy.gz.guahao.pojo.h100202.d2102602FEZX239897.RegDoc040Param;

/**
 * @author boazy
 * @company boazy
 * @date 2018/8/25
 */
public class GuahaoDemo {

    public static void main(String[] args) {
        // 创建邮件参数
        EMailParam emailParam = new EMailParam("applesmtp.126.com"
                , "boazy@126.com", "********");
        emailParam.setToAddresses("boazy@qq.com");
        // 创建登陆参数(用户名、密码；密码需要使用传送加密的，可以浏览器中登陆采用F12开发工具获取加密后的密码)
        LoginParam loginParam = new LoginParam("boazy", "********");
        // 创建挂号参数（这里直接是040医生号的对象参数，没有相关医生参数类可用RegParam类）
        // connNO为当前账号，可浏览器中访问挂号提交页面采用F12开发工具获取到
        RegDoc040Param regParam = new RegDoc040Param();
        regParam.regTime("2018-08-29", "2", "14:00", "14:30");
        regParam.regPerson("李平安", "M", "身份证号码"
                , "00000********", "201*-0*-1*");
        // 采用挂号模式1进行（刷）挂号
        GuahaoPattern.regPattern1(loginParam, regParam, emailParam, 1000L * 5);
    }

}
