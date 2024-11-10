package com.yokke.base.service;

import com.yokke.base.dto.BaseDto;
import com.yokke.base.model.BaseModel;
import com.yokke.base.response.BaseResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BaseService {

    public <T extends BaseModel, R extends BaseResponse> R mapAudit(T baseModel, R baseResponse) {
        baseResponse.setCreatedBy(baseModel.getCreatedBy());
        baseResponse.setCreatedDate(baseModel.getCreatedDate());
        baseResponse.setLastModifiedBy(baseModel.getLastModifiedBy());
        baseResponse.setLastModifiedDate(baseModel.getLastModifiedDate());
        return baseResponse;
    }

    public <T extends BaseModel, R extends BaseDto> R mapAudit(T baseModel, R baseResponse) {
        baseResponse.setCreatedBy(baseModel.getCreatedBy());
        baseResponse.setCreatedDate(baseModel.getCreatedDate());
        baseResponse.setLastModifiedBy(baseModel.getLastModifiedBy());
        baseResponse.setLastModifiedDate(baseModel.getLastModifiedDate());
        return baseResponse;
    }


    public <R extends BaseResponse, T extends BaseModel> R mapAudit(R baseResponse, T baseModel) {
        baseResponse.setCreatedBy(baseModel.getCreatedBy());
        baseResponse.setCreatedDate(baseModel.getCreatedDate());
        baseResponse.setLastModifiedBy(baseModel.getLastModifiedBy());
        baseResponse.setLastModifiedDate(baseModel.getLastModifiedDate());
        return baseResponse;
    }
    public <R extends BaseDto, T extends BaseModel> R mapAudit(R baseResponse, T baseModel) {
        baseResponse.setCreatedBy(baseModel.getCreatedBy());
        baseResponse.setCreatedDate(baseModel.getCreatedDate());
        baseResponse.setLastModifiedBy(baseModel.getLastModifiedBy());
        baseResponse.setLastModifiedDate(baseModel.getLastModifiedDate());
        return baseResponse;
    }
}
