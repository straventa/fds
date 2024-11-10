package com.yokke.merchantservice.cdd;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TBAZCMMNCDDTL_MST")
public class Cdd {

    @Id
    @Column(name = "CMMN_CD_ID", nullable = false, length = 8)
    private String cmmnCdId;

    @Column(name = "DTL_CD_ID", nullable = false, length = 10)
    private String dtlCdId;

    @Column(name = "DTL_CD_NM", nullable = false, length = 100)
    private String dtlCdNm;

    @Column(name = "CD_EXPL", nullable = false, length = 1000)
    private String cdExpl;

    @Column(name = "SORT_SEQ", nullable = false, precision = 5)
    private BigDecimal sortSeq;

    @Column(name = "CLSS_INFO_VAL1", length = 128)
    private String clssInfoVal1;

    @Column(name = "CLSS_INFO_VAL2", length = 128)
    private String clssInfoVal2;

    @Column(name = "CLSS_INFO_VAL4", length = 128)
    private String clssInfoVal4;

    @Column(name = "CLSS_INFO_VAL5", length = 128)
    private String clssInfoVal5;

    @Column(name = "CLSS_INFO_VAL6", length = 128)
    private String clssInfoVal6;

    @Column(name = "CLSS_INFO_VAL3", length = 128)
    private String clssInfoVal3;

    @Column(name = "DATA_STAT_CD", nullable = false, length = 1)
    private String dataStatCd;

    @Column(name = "INP_USR_ID", nullable = false, length = 15)
    private String inpUsrId;

    @Column(name = "INP_PGM_ID", nullable = false, length = 11)
    private String inpPgmId;

    @Column(name = "DATA_INP_DTTM", nullable = false)
    private LocalDateTime dataInpDttm;

    @Column(name = "CHNG_USR_ID", nullable = false, length = 15)
    private String chngUsrId;

    @Column(name = "CHNG_PGM_ID", nullable = false, length = 11)
    private String chngPgmId;

    @Column(name = "DATA_CHNG_DTTM", nullable = false)
    private LocalDateTime dataChngDttm;

    // Getters and setters
    // ...
}

