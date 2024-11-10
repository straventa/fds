package com.yokke.merchantservice.cdd;


import com.yokke.base.mapper.BaseMapper;
import org.springframework.stereotype.Service;

@Service
public class CddMapper extends BaseMapper {

    public static CddDto mapToDto(Cdd cdd) {
        return CddDto.builder()
                .cmmnCdId(cdd.getCmmnCdId())
                .dtlCdId(cdd.getDtlCdId())
                .dtlCdNm(cdd.getDtlCdNm())
                .cdExpl(cdd.getCdExpl())
                .sortSeq(cdd.getSortSeq())
                .clssInfoVal1(cdd.getClssInfoVal1())
                .clssInfoVal2(cdd.getClssInfoVal2())
                .clssInfoVal4(cdd.getClssInfoVal4())
                .clssInfoVal5(cdd.getClssInfoVal5())
                .clssInfoVal6(cdd.getClssInfoVal6())
                .clssInfoVal3(cdd.getClssInfoVal3())
                .dataStatCd(cdd.getDataStatCd())
                .inpUsrId(cdd.getInpUsrId())
                .build();
    }

    public static Cdd mapToEntity(CddDto cddDto) {
        return Cdd.builder()
                .cmmnCdId(cddDto.getCmmnCdId())
                .dtlCdId(cddDto.getDtlCdId())
                .dtlCdNm(cddDto.getDtlCdNm())
                .cdExpl(cddDto.getCdExpl())
                .sortSeq(cddDto.getSortSeq())
                .clssInfoVal1(cddDto.getClssInfoVal1())
                .clssInfoVal2(cddDto.getClssInfoVal2())
                .clssInfoVal4(cddDto.getClssInfoVal4())
                .clssInfoVal5(cddDto.getClssInfoVal5())
                .clssInfoVal6(cddDto.getClssInfoVal6())
                .clssInfoVal3(cddDto.getClssInfoVal3())
                .dataStatCd(cddDto.getDataStatCd())
                .inpUsrId(cddDto.getInpUsrId())
                .build();
    }
}
