package com.yokke.merchantservice.cdd;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for {@link Cdd}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CddDto implements Serializable {
    String cmmnCdId;
    String dtlCdId;
    String dtlCdNm;
    String cdExpl;
    BigDecimal sortSeq;
    String clssInfoVal1;
    String clssInfoVal2;
    String clssInfoVal4;
    String clssInfoVal5;
    String clssInfoVal6;
    String clssInfoVal3;
    String dataStatCd;
    String inpUsrId;
    String inpPgmId;
    LocalDateTime dataInpDttm;
    String chngUsrId;
    String chngPgmId;
    LocalDateTime dataChngDttm;
}