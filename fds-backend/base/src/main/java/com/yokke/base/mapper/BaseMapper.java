package com.yokke.base.mapper;

import com.yokke.base.dto.BaseDto;
import com.yokke.base.model.BaseModel;
import com.yokke.base.response.BaseResponse;

public class BaseMapper {
    public <T extends BaseModel, R extends BaseResponse> R mapAudit(T baseModel, R baseResponse) {
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
