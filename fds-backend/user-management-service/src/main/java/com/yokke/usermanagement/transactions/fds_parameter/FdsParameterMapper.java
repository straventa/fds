package com.yokke.usermanagement.transactions.fds_parameter;

import com.yokke.base.mapper.BaseMapper;
import org.springframework.stereotype.Service;

@Service
public class FdsParameterMapper extends BaseMapper {

    public FdsParameterDto mapToDto(
            FdsParameter fdsParameter,
            FdsParameterDto fdsParameterDto
    ) {
        fdsParameterDto.setFdsParameterId(fdsParameter.getFdsParameterId());
        fdsParameterDto.setFdsParameterKey(fdsParameter.getFdsParameterKey());
        fdsParameterDto.setFdsParameterValue(fdsParameter.getFdsParameterValue());
        fdsParameterDto.setFdsParameterCategory(fdsParameter.getFdsParameterCategory());
        return fdsParameterDto;
    }


    public FdsParameterDto mapToEntity(
            FdsParameterDto fdsParameterDto,
            FdsParameter fdsParameter
    ) {
        fdsParameter.setFdsParameterKey(fdsParameterDto.getFdsParameterKey());
        fdsParameter.setFdsParameterValue(fdsParameterDto.getFdsParameterValue());
        fdsParameter.setFdsParameterCategory(fdsParameterDto.getFdsParameterCategory());
        return fdsParameterDto;
    }
}
