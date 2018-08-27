package boazy.gz.guahao.pojo.h100202.d2002418FEZX239897;

import boazy.gz.guahao.pojo.RegParam;

/**
 * 2002418FEZX239897|012
 * 儿外科特诊|罗真东
 * <p>
 * 副主任医师，周一上午号
 * <p>
 * http://www.guahao.gov.cn/ajx_regtime.xhtml?HIS_CD=100202&DEP_ID=2002418FEZX239897&DOC_ID=012&REG_DAT=2018-09-03&TM_FLG=1
 *
 * @author boazy
 * @company boazy
 * @date 2018/8/27
 */
public class RegDoc012Param extends RegParam {

    public RegDoc012Param() {
        super();

        this.setYbChkFlg("1");
        this.setRegFlg("0");
        this.setRegPayTyp("");
        this.setMedCode("");
        this.setHisCd("100202");
        this.setDepId("2002418FEZX239897");
        this.setDepNm("儿外科特诊");
        this.setDocId("012");
        this.setDocNm("罗真东");

        /*this.setRegDat("2018-09-03");
        this.setTimFlg("1");
        this.setStaTim("08:00");
        this.setEndTim("08:30");*/

        this.setRegFee("0");
        this.setTreFee("3000");
        this.setBbtreFee("3000");
        this.setBbregFee("0");
        this.setVerId(null);

        /*this.setOpeNm("姓名");
        this.setOpeSex("性别");
        this.setOpeIdc("430481yyyyMMddXXXX");
        this.setConnNo("00000761XXXX");
        this.setConnTyp("1");
        this.setConnBirthDt("yyyy-MM-dd");*/

        this.setGravidaFlag("");
        this.setSuckleFlag("");
    }

}
