package com.yokke.merchantservice.owner;

import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * DTO for {@link Owner}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OwnerDto implements Serializable {
    String mid;
    String ktpno;
    String rpsvNm;
    String homDtlAddr1;
    String homDtlAddr2;
    String homDtlAddr3;
    String homPost;
    String homMailIno;
    String rpsvCityCd;
    String homTelRgnCd;
    String homTelNo;
    String rpsvHpTelNo1;
    String rpsvHpTelNo2;
    String rpsvFaxTelRgnCd;
    String rpsvFaxTelNo;
    String rpsvEmailAddr;
    String inpUsrId;
    String inpPgmId;
    Timestamp dataInpDttm;
    String chngUsrId;
    String chngPgmId;
    Timestamp dataChngDttm;
}