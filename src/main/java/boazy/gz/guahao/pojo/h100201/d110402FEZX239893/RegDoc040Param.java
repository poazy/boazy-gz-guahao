package boazy.gz.guahao.pojo.h100201.d110402FEZX239893;

import boazy.gz.guahao.pojo.RegParam;

/**
 * 110402FEZX239893|040
 * 儿童骨科（儿）|徐宏文
 * <p>
 * 主任医师，周一上午号
 * <p>
 * http://www.guahao.gov.cn/ajx_regtime.xhtml?HIS_CD=100201&DEP_ID=110402FEZX239893&DOC_ID=040&REG_DAT=2018-09-10&TM_FLG=1
 *
 * @author boazy
 * @company boazy
 * @date 2018/8/31
 */
public class RegDoc040Param extends RegParam {

    public RegDoc040Param() {
        super();

        this.setYbChkFlg("1");
        this.setRegFlg("0");
        this.setRegPayTyp("");
        this.setMedCode("");
        this.setHisCd("100201");
        this.setDepId("110402FEZX239893");
        this.setDepNm("儿童骨科（儿）");
        this.setDocId("040");
        this.setDocNm("徐宏文");

        /*this.setRegDat("2018-09-10");
        this.setTimFlg("1");
        this.setStaTim("09:00");
        this.setEndTim("09:30");*/

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
