package com.yokke.merchantservice.owner;


import com.yokke.base.mapper.BaseMapper;
import com.yokke.merchantservice.merchant.MerchantDto;
import org.springframework.stereotype.Service;

@Service
public class OwnerMapper extends BaseMapper {
    public OwnerDto mapToDto(Owner merchantReservation) {
        return OwnerDto.builder()
                .mid(merchantReservation.getMid())
                .ktpno(merchantReservation.getKtpno())
                .rpsvNm(merchantReservation.getRpsvNm())
                .homDtlAddr1(merchantReservation.getHomDtlAddr1())
                .homDtlAddr2(merchantReservation.getHomDtlAddr2())
                .homDtlAddr3(merchantReservation.getHomDtlAddr3())
                .homPost(merchantReservation.getHomPost())
                .homMailIno(merchantReservation.getHomMailIno())
                .rpsvCityCd(merchantReservation.getRpsvCityCd())
                .homTelRgnCd(merchantReservation.getHomTelRgnCd())
                .homTelNo(merchantReservation.getHomTelNo())
                .rpsvHpTelNo1(merchantReservation.getRpsvHpTelNo1())
                .rpsvHpTelNo2(merchantReservation.getRpsvHpTelNo2())
                .rpsvFaxTelRgnCd(merchantReservation.getRpsvFaxTelRgnCd())
                .rpsvFaxTelNo(merchantReservation.getRpsvFaxTelNo())
                .rpsvEmailAddr(merchantReservation.getRpsvEmailAddr())
                .inpUsrId(merchantReservation.getInpUsrId())
                .inpPgmId(merchantReservation.getInpPgmId())
                .dataInpDttm(merchantReservation.getDataInpDttm())
                .chngUsrId(merchantReservation.getChngUsrId())
                .chngPgmId(merchantReservation.getChngPgmId())
                .dataChngDttm(merchantReservation.getDataChngDttm())
                .build();
    }}
