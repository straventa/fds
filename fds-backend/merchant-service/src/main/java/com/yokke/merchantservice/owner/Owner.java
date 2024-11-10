package com.yokke.merchantservice.owner;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jakarta.persistence.*;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TBMCMERRPSV_MST")
public class Owner {

    @Id
    @Column(name = "MID", length = 11, nullable = false)
    private String mid;

    @Column(name = "KTPNO", length = 20)
    private String ktpno;

    @Column(name = "RPSV_NM", length = 60)
    private String rpsvNm;

    @Column(name = "HOM_DTL_ADDR1", length = 100)
    private String homDtlAddr1;

    @Column(name = "HOM_DTL_ADDR2", length = 100)
    private String homDtlAddr2;

    @Column(name = "HOM_DTL_ADDR3", length = 100)
    private String homDtlAddr3;

    @Column(name = "HOM_POST", length = 6)
    private String homPost;

    @Column(name = "HOM_MAIL_INO", length = 10)
    private String homMailIno;

    @Column(name = "RPSV_CITY_CD", length = 3)
    private String rpsvCityCd;

    @Column(name = "HOM_TEL_RGN_CD", length = 4)
    private String homTelRgnCd;

    @Column(name = "HOM_TEL_NO", length = 20)
    private String homTelNo;

    @Column(name = "RPSV_HP_TEL_NO1", length = 20)
    private String rpsvHpTelNo1;

    @Column(name = "RPSV_HP_TEL_NO2", length = 20)
    private String rpsvHpTelNo2;

    @Column(name = "RPSV_FAX_TEL_RGN_CD", length = 4)
    private String rpsvFaxTelRgnCd;

    @Column(name = "RPSV_FAX_TEL_NO", length = 20)
    private String rpsvFaxTelNo;

    @Column(name = "RPSV_EMAIL_ADDR", length = 50)
    private String rpsvEmailAddr;

    @Column(name = "INP_USR_ID", length = 15, nullable = false)
    private String inpUsrId;

    @Column(name = "INP_PGM_ID", length = 11, nullable = false)
    private String inpPgmId;

    @Column(name = "DATA_INP_DTTM", nullable = false)
    private Timestamp dataInpDttm;

    @Column(name = "CHNG_USR_ID", length = 15, nullable = false)
    private String chngUsrId;

    @Column(name = "CHNG_PGM_ID", length = 11, nullable = false)
    private String chngPgmId;

    @Column(name = "DATA_CHNG_DTTM", nullable = false)
    private Timestamp dataChngDttm;

    // Getters and Setters
    // ...
}