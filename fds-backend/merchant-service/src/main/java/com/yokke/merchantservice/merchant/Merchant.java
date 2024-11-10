package com.yokke.merchantservice.merchant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TBMCMERMAIN_MST")
public class Merchant {

    @Id
    @Column(name = "MID", length = 11, nullable = false)
    private String mid;

    @Column(name = "APLC_SEQ_NO", length = 11, nullable = false)
    private String aplcSeqNo;

    @Column(name = "MER_NM", length = 100)
    private String merNm;

    @Column(name = "MER_DTL_ADDR1", length = 100)
    private String merDtlAddr1;

    @Column(name = "MER_DTL_ADDR2", length = 100)
    private String merDtlAddr2;

    @Column(name = "MER_DTL_ADDR3", length = 100)
    private String merDtlAddr3;

    @Column(name = "MER_POST", length = 6)
    private String merPost;

    @Column(name = "MER_MAIL_INO", length = 10)
    private String merMailIno;

    @Column(name = "MER_CITY_CD", length = 3)
    private String merCityCd;

    @Column(name = "MAI_TRT_ITMS_NM", length = 100)
    private String maiTrtItmsNm;

    @Column(name = "SLS_STRT_DATE", length = 8)
    private String slsStrtDate;

    @Column(name = "MER_TEL_RGN_CD", length = 4)
    private String merTelRgnCd;

    @Column(name = "MER_TEL_NO", length = 20)
    private String merTelNo;

    @Column(name = "MER_FAX_TEL_RGN_CD", length = 4)
    private String merFaxTelRgnCd;

    @Column(name = "MER_FAX_TEL_NO", length = 20)
    private String merFaxTelNo;

    @Column(name = "HPGE_URL", length = 200)
    private String hpgeUrl;

    @Column(name = "STD_TIZN_CD", length = 1)
    private String stdTiznCd;

    @Column(name = "BRND_CLCD", length = 1, nullable = false)
    private String brndClcd;

    @Column(name = "MCC_CD", length = 4, nullable = false)
    private String mccCd;

    @Column(name = "BRND_CLCD1", length = 1)
    private String brndClcd1;

    @Column(name = "MCC_CD1", length = 4)
    private String mccCd1;

    @Column(name = "MER_SEGMT_CD", length = 1)
    private String merSegmtCd;

    @Column(name = "EMAIL_ADDR", length = 50)
    private String emailAddr;

    @Column(name = "STRT_SLS_TIME", length = 2)
    private String strtSlsTime;

    @Column(name = "END_SLS_TIME", length = 2)
    private String endSlsTime;

    @Column(name = "BTH_MGR_FNM1", length = 50)
    private String bthMgrFnm1;

    @Column(name = "BTH_MGR_FNM2", length = 50)
    private String bthMgrFnm2;

    @Column(name = "BTH_MGR_TEL_RGN_CD", length = 4)
    private String bthMgrTelRgnCd;

    @Column(name = "BTH_MGR_TEL_NO", length = 20)
    private String bthMgrTelNo;

    @Column(name = "BTH_MGR_HP_TEL_NO11", length = 20)
    private String bthMgrHpTelNo11;

    @Column(name = "BTH_MGR_HP_TEL_NO12", length = 20)
    private String bthMgrHpTelNo12;

    @Column(name = "BTH_MGR_HP_TEL_NO21", length = 20)
    private String bthMgrHpTelNo21;

    @Column(name = "BTH_MGR_HP_TEL_NO22", length = 20)
    private String bthMgrHpTelNo22;

    @Column(name = "PMT_ACCT_BK_CD", length = 3)
    private String pmtAcctBkCd;

    @Column(name = "ACCT_MGMT_BK_CD", length = 7)
    private String acctMgmtBkCd;

    @Column(name = "PMT_ACCT_NO", length = 20)
    private String pmtAcctNo;

    @Column(name = "ACCT_TP_CD", length = 1)
    private String acctTpCd;

    @Column(name = "PMT_ACCT_DPSO_NM", length = 100)
    private String pmtAcctDpsoNm;

    @Column(name = "PMT_ACCT_BR_CD", length = 5)
    private String pmtAcctBrCd;

    @Column(name = "OFFCL_MER_NM", length = 100)
    private String offclMerNm;

    @Column(name = "OFFCL_MER_DTL_ADDR1", length = 100)
    private String offclMerDtlAddr1;

    @Column(name = "OFFCL_MER_DTL_ADDR2", length = 100)
    private String offclMerDtlAddr2;

    @Column(name = "OFFCL_MER_DTL_ADDR3", length = 100)
    private String offclMerDtlAddr3;

    @Column(name = "OFFCL_MER_POST", length = 6)
    private String offclMerPost;

    @Column(name = "OFFCL_MER_MAIL_INO", length = 10)
    private String offclMerMailIno;

    @Column(name = "OFFCL_MER_CITY_CD", length = 3)
    private String offclMerCityCd;

    @Column(name = "OFFCL_MER_TEL_RGN_CD", length = 4)
    private String offclMerTelRgnCd;

    @Column(name = "OFFCL_MER_TEL_NO", length = 20)
    private String offclMerTelNo;

    @Column(name = "OFFCL_MER_FAX_TEL_RGN_CD", length = 4)
    private String offclMerFaxTelRgnCd;

    @Column(name = "OFFCL_MER_FAX_TEL_NO", length = 20)
    private String offclMerFaxTelNo;

    @Column(name = "OFFCL_MER_HPGE_URL", length = 200)
    private String offclMerHpgeUrl;

    @Column(name = "KTPNO", length = 20, nullable = false)
    private String ktpno;

    @Column(name = "NPWP", length = 20, nullable = false)
    private String npwp;

    //    @Column(name = "CO_TP_CD", length = 1)
//    private String coTpCd;
//
//    @Column(name = "BIZ_TP_CD", length = 4)
//    private String bizTpCd;
//
    @Column(name = "DTL_BIZ_TP_CTNTS", length = 1000)
    private String dtlBizTpCtnts;
    //
//    @Column(name = "YSALES_AMT", precision = 38, scale = 2, nullable = false)
//    private BigDecimal ysalesAmt = BigDecimal.ZERO;
//
//    @Column(name = "NON_CSH_SALE_RT", precision = 7, scale = 4)
//    private BigDecimal nonCshSaleRt;
//
//    @Column(name = "WORKR_CNT", nullable = false)
//    private Integer workrCnt = 0;
//
//    @Column(name = "BTH_SIZE")
//    private Integer bthSize;
//
//    @Column(name = "MER_BTH_CLCD", length = 1)
//    private String merBthClcd;
//
//    @Column(name = "BIZ_ENV_CD", length = 2)
//    private String bizEnvCd;
//
//    @Column(name = "DTL_BIZ_ENV_CD", length = 4)
//    private String dtlBizEnvCd;
//
//    @Column(name = "PGCO_CD", length = 2)
//    private String pgcoCd;
//
//    @Column(name = "POSTNG_CLCD", length = 2)
//    private String postngClcd;
//
//    @Column(name = "STTL_TRUST_CLCD", length = 1)
//    private String sttlTrustClcd;
//
//    @Column(name = "MER_PMT_MTHD_CD", length = 1)
//    private String merPmtMthdCd;
//
//    @Column(name = "MER_PMT_CYCL_CD", length = 1)
//    private String merPmtCyclCd = "0";
//
//    @Column(name = "MER_PMT_STTL_DAY_CD", length = 1)
//    private String merPmtSttlDayCd;
//
//    @Column(name = "MSTMT_SND_TP_CD", length = 1)
//    private String mstmtSndTpCd;
//
//    @Column(name = "MSTMT_SND_CYCL_CD", length = 1)
//    private String mstmtSndCyclCd;
//
//    @Column(name = "MSTMT_SND_UNT_CD", length = 1)
//    private String mstmtSndUntCd;
//
//    @Column(name = "MSTMT_SND_MER_GRUP_ID", length = 8)
//    private String mstmtSndMerGrupId;
//
//    @Column(name = "MSTMT_SND_EMAIL_ADDR", length = 50)
//    private String mstmtSndEmailAddr;
//
//    @Column(name = "MEMBS_FEE_CMPS_YN", length = 1)
//    private String membsFeeCmpsYn;
//
//    @Column(name = "MEMBS_FEE_EXCP_MCNT", nullable = false)
//    private Integer membsFeeExcpMcnt = 0;
//
//    @Column(name = "MEMBS_FEE_EXCP_MO_VAL", length = 12)
//    private String membsFeeExcpMoVal;
//
//    @Column(name = "MEMBS_FEE_BILL_MTHD_CD", length = 1)
//    private String membsFeeBillMthdCd;
//
//    @Column(name = "MER_LIM_APPL_YN", length = 1)
//    private String merLimApplYn;
//
//    @Column(name = "MER_MO_LIM_AMT", precision = 38, scale = 2, nullable = false)
//    private BigDecimal merMoLimAmt = BigDecimal.ZERO;
//
    @Column(name = "NEW_REGT_DATE", length = 8)
    private String newRegtDate;

    @Column(name = "NEW_REGT_TIME", length = 6)
    private String newRegtTime;
//
//    @Column(name = "MER_CNCEL_DATE", length = 8)
//    private String merCncelDate;
//
//    @Column(name = "CNCEL_MER_ACCI_CD", length = 4)
//    private String cncelMerAcciCd;
//
//    @Column(name = "EVNT_STRT_DATE", length = 8)
//    private String evntStrtDate;
//
//    @Column(name = "EVNT_END_DATE", length = 8)
//    private String evntEndDate;
//
//    @Column(name = "RCRU_CHNL_CD", length = 2)
//    private String rcruChnlCd;
//
//    @Column(name = "OPN_CHG_EMP_NO", length = 13)
//    private String opnChgEmpNo;
//
//    @Column(name = "OPN_CHG_DEPT_CD", length = 10)
//    private String opnChgDeptCd;
//
//    @Column(name = "MGMT_CHG_EMP_NO", length = 13)
//    private String mgmtChgEmpNo;
//
//    @Column(name = "MGMT_CHG_DEPT_CD", length = 10)
//    private String mgmtChgDeptCd;
//
//    @Column(name = "MGMT_ICHRG_RGN_CD", length = 3)
//    private String mgmtIchrgRgnCd;
//
//    @Column(name = "MGMT_ICHRG_AREA_CD", length = 6)
//    private String mgmtIchrgAreaCd;
//
//    @Column(name = "MGMT_ICHRG_BR_CD", length = 5)
//    private String mgmtIchrgBrCd;
//
//    @Column(name = "TMNL_MGMT_VEND_NO", length = 5)
//    private String tmnlMgmtVendNo;
//
//    @Column(name = "INP_USR_ID", length = 15, nullable = false)
//    private String inpUsrId;
//
//    @Column(name = "INP_PGM_ID", length = 11, nullable = false)
//    private String inpPgmId;
//
//    @Column(name = "DATA_INP_DTTM", nullable = false)
//    private Timestamp dataInpDttm;
//
//    @Column(name = "CHNG_USR_ID", length = 15, nullable = false)
//    private String chngUsrId;
//
//    @Column(name = "CHNG_PGM_ID", length = 11, nullable = false)
//    private String chngPgmId;
//
//    @Column(name = "DATA_CHNG_DTTM", nullable = false)
//    private Timestamp dataChngDttm;
//
//    @Column(name = "NPG_INFO_VAL", length = 4)
//    private String npgInfoVal;
//
//    @Column(name = "MIG_YN", length = 1)
//    private String migYn;
//
//    @Column(name = "JAL_YN", length = 1)
//    private String jalYn;
//
//    @Column(name = "PMT_UNT", length = 1, nullable = false)
//    private String pmtUnt;
//
//    @Column(name = "MGMT_BR_CD_LOC", length = 5)
//    private String mgmtBrCdLoc;


}
