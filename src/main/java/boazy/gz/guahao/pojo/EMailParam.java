package boazy.gz.guahao.pojo;

/**
 * @author boazy
 * @company boazy
 * @date 2018/8/21
 */
public class EMailParam {
    private String mailSmtpHost;
    private String mailAmtpAuth;
    private String mailUsername;
    private String mailPassword;

    // 多个邮件接收地址以英文逗号分隔，如：boazy@qq.com,boazy126.com
    private String toAddresses;

    public EMailParam() {
    }

    public EMailParam(String mailSmtpHost, String mailUsername, String mailPassword) {
        this.mailSmtpHost = mailSmtpHost;
        this.mailAmtpAuth = "true";
        this.mailUsername = mailUsername;
        this.mailPassword = mailPassword;
    }

    public EMailParam(String mailSmtpHost, String mailAmtpAuth, String mailUsername, String mailPassword) {
        this.mailSmtpHost = mailSmtpHost;
        this.mailAmtpAuth = mailAmtpAuth;
        this.mailUsername = mailUsername;
        this.mailPassword = mailPassword;
    }

    public String getMailSmtpHost() {
        return mailSmtpHost;
    }

    public void setMailSmtpHost(String mailSmtpHost) {
        this.mailSmtpHost = mailSmtpHost;
    }

    public String getMailAmtpAuth() {
        return mailAmtpAuth;
    }

    public void setMailAmtpAuth(String mailAmtpAuth) {
        this.mailAmtpAuth = mailAmtpAuth;
    }

    public String getMailUsername() {
        return mailUsername;
    }

    public void setMailUsername(String mailUsername) {
        this.mailUsername = mailUsername;
    }

    public String getMailPassword() {
        return mailPassword;
    }

    public void setMailPassword(String mailPassword) {
        this.mailPassword = mailPassword;
    }

    public String getToAddresses() {
        return toAddresses;
    }

    public void setToAddresses(String toAddresses) {
        this.toAddresses = toAddresses;
    }

}
