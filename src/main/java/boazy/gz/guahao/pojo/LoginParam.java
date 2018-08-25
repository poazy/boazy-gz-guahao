package boazy.gz.guahao.pojo;

/**
 * 登陆参数
 *
 * @author boazy
 * @company boazy
 * @date 2018/8/21
 */
public class LoginParam {
    private String cardNo;
    private String verCd;
    private String cardTyp = "1";
    private String pwd;
    private String loginScon ="1";
    private String ip;

    public LoginParam() {
    }

    public LoginParam(String cardNo, String pwd) {
        this.cardNo = cardNo;
        this.pwd = pwd;
    }

    public LoginParam(String cardNo, String verCd, String pwd) {
        this.cardNo = cardNo;
        this.verCd = verCd;
        this.pwd = pwd;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getVerCd() {
        return verCd;
    }

    public void setVerCd(String verCd) {
        this.verCd = verCd;
    }

    public String getCardTyp() {
        return cardTyp;
    }

    public void setCardTyp(String cardTyp) {
        this.cardTyp = cardTyp;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getLoginScon() {
        return loginScon;
    }

    public void setLoginScon(String loginScon) {
        this.loginScon = loginScon;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

}
