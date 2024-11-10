package com.yokke.merchantservice.merchant;

import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link com.yokke.merchantservice.merchant.Merchant}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MerchantDto implements Serializable {
    private String mid;

    private String aplcSeqNo;

    private String merNm;

    private String merDtlAddr1;

    private String merDtlAddr2;

    private String merDtlAddr3;

    private String merPost;

    private String merMailIno;

    private String merCityCd;

    private String maiTrtItmsNm;

    private String slsStrtDate;

    private String merTelRgnCd;

    private String merTelNo;

    private String merFaxTelRgnCd;

    private String merFaxTelNo;

    private String hpgeUrl;

    private String stdTiznCd;

    private String brndClcd;

    private String mccCd;

    private String brndClcd1;

    private String mccCd1;

    private String merSegmtCd;

    private String emailAddr;

    private String strtSlsTime;

    private String endSlsTime;

    private String bthMgrFnm1;

    private String bthMgrFnm2;

    private String bthMgrTelRgnCd;

    private String bthMgrTelNo;

    private String bthMgrHpTelNo11;

    private String bthMgrHpTelNo12;

    private String bthMgrHpTelNo21;

    private String bthMgrHpTelNo22;

    private String pmtAcctBkCd;

    private String acctMgmtBkCd;

    private String pmtAcctNo;

    private String acctTpCd;

    private String pmtAcctDpsoNm;

    private String pmtAcctBrCd;

    private String offclMerNm;

    private String offclMerDtlAddr1;

    private String offclMerDtlAddr2;

    private String offclMerDtlAddr3;

    private String offclMerPost;

    private String offclMerMailIno;

    private String offclMerCityCd;

    private String offclMerTelRgnCd;

    private String offclMerTelNo;

    private String offclMerFaxTelRgnCd;

    private String offclMerFaxTelNo;

    private String offclMerHpgeUrl;

    private String ktpno;

    private String npwp;

    //
    private String newRegtDate;

    private String newRegtTime;
//

    private String dtlBizTpCtnts;


}