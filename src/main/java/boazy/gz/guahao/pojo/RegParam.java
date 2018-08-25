package boazy.gz.guahao.pojo;

/**
 * 挂号参数
 *
 * @author boazy
 * @company boazy
 * @date 2018/8/21
 */
public class RegParam {

    private String token = String.valueOf(System.currentTimeMillis());
    private String ybChkFlg = "1";
    private String regFlg = "0";
    private String regPayTyp = "";
    private String medCode = "";
    private String hisCd;
    private String depId;
    private String depNm;
    private String docId;
    private String docNm;
    private String regDat;
    private String timFlg;
    private String staTim;
    private String endTim;
    private String regFee;
    private String treFee;
    private String bbtreFee;
    private String bbregFee;
    private String verId;
    private String opeNm;
    private String opeSex;
    private String opeIdc;
    private String connNo;
    private String connTyp;
    private String connBirthDt;
    private String gravidaFlag = "";
    private String suckleFlag = "";

    public RegParam() {
    }

    /**
     * 设置注册时间
     *
     * @param regDat 挂哪天的号（格式：yyyy-MM-dd）
     * @param timFlg 挂哪个时段的号（1表示上午、2表示下午）
     * @param staTim 挂哪个时候的号开始时间（格式：HH:mm）
     * @param endTim 挂哪个时候的号结束时间（格式：HH:mm）
     * @return 响应040医生的挂号参数
     */
    public RegParam regTime(String regDat, String timFlg, String staTim, String endTim) {

        this.setRegDat(regDat);
        this.setTimFlg(timFlg);
        this.setStaTim(staTim);
        this.setEndTim(endTim);

        return this;
    }

    /**
     * 设置挂号人信息
     *
     * @param opeNm 就诊人姓名
     * @param opeSex 就诊人性别（M表示男、F表示女）
     * @param opeIdc 就诊人证件号码
     * @param connNo 系统就诊人号码（从就人信息中获得）
     * @param connBirthDt 就诊人出生年月日（格式：yyyy-MM-dd）
     * @return
     */
    public RegParam regPerson(String opeNm, String opeSex, String opeIdc, String connNo, String connBirthDt) {

        this.setOpeNm(opeNm);
        this.setOpeSex(opeSex);
        this.setOpeIdc(opeIdc);
        this.setConnNo(connNo);
        this.setConnTyp("1");
        this.setConnBirthDt(connBirthDt);

        return this;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getYbChkFlg() {
        return ybChkFlg;
    }

    public void setYbChkFlg(String ybChkFlg) {
        this.ybChkFlg = ybChkFlg;
    }

    public String getRegFlg() {
        return regFlg;
    }

    public void setRegFlg(String regFlg) {
        this.regFlg = regFlg;
    }

    public String getRegPayTyp() {
        return regPayTyp;
    }

    public void setRegPayTyp(String regPayTyp) {
        this.regPayTyp = regPayTyp;
    }

    public String getMedCode() {
        return medCode;
    }

    public void setMedCode(String medCode) {
        this.medCode = medCode;
    }

    public String getHisCd() {
        return hisCd;
    }

    public void setHisCd(String hisCd) {
        this.hisCd = hisCd;
    }

    public String getDepId() {
        return depId;
    }

    public void setDepId(String depId) {
        this.depId = depId;
    }

    public String getDepNm() {
        return depNm;
    }

    public void setDepNm(String depNm) {
        this.depNm = depNm;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getDocNm() {
        return docNm;
    }

    public void setDocNm(String docNm) {
        this.docNm = docNm;
    }

    public String getRegDat() {
        return regDat;
    }

    public void setRegDat(String regDat) {
        this.regDat = regDat;
    }

    public String getTimFlg() {
        return timFlg;
    }

    public void setTimFlg(String timFlg) {
        this.timFlg = timFlg;
    }

    public String getStaTim() {
        return staTim;
    }

    public void setStaTim(String staTim) {
        this.staTim = staTim;
    }

    public String getEndTim() {
        return endTim;
    }

    public void setEndTim(String endTim) {
        this.endTim = endTim;
    }

    public String getRegFee() {
        return regFee;
    }

    public void setRegFee(String regFee) {
        this.regFee = regFee;
    }

    public String getTreFee() {
        return treFee;
    }

    public void setTreFee(String treFee) {
        this.treFee = treFee;
    }

    public String getBbtreFee() {
        return bbtreFee;
    }

    public void setBbtreFee(String bbtreFee) {
        this.bbtreFee = bbtreFee;
    }

    public String getBbregFee() {
        return bbregFee;
    }

    public void setBbregFee(String bbregFee) {
        this.bbregFee = bbregFee;
    }

    public String getVerId() {
        return verId;
    }

    public void setVerId(String verId) {
        this.verId = verId;
    }

    public String getOpeNm() {
        return opeNm;
    }

    public void setOpeNm(String opeNm) {
        this.opeNm = opeNm;
    }

    public String getOpeSex() {
        return opeSex;
    }

    public void setOpeSex(String opeSex) {
        this.opeSex = opeSex;
    }

    public String getOpeIdc() {
        return opeIdc;
    }

    public void setOpeIdc(String opeIdc) {
        this.opeIdc = opeIdc;
    }

    public String getConnNo() {
        return connNo;
    }

    public void setConnNo(String connNo) {
        this.connNo = connNo;
    }

    public String getConnTyp() {
        return connTyp;
    }

    public void setConnTyp(String connTyp) {
        this.connTyp = connTyp;
    }

    public String getConnBirthDt() {
        return connBirthDt;
    }

    public void setConnBirthDt(String connBirthDt) {
        this.connBirthDt = connBirthDt;
    }

    public String getGravidaFlag() {
        return gravidaFlag;
    }

    public void setGravidaFlag(String gravidaFlag) {
        this.gravidaFlag = gravidaFlag;
    }

    public String getSuckleFlag() {
        return suckleFlag;
    }

    public void setSuckleFlag(String suckleFlag) {
        this.suckleFlag = suckleFlag;
    }

}
