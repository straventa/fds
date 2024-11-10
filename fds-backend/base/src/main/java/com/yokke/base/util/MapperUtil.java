package com.yokke.base.util;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MapperUtil {

  private final ModelMapper modelMapper;

  public <T, S> S convertToEntity(T data, Class<S> type) {
    return modelMapper.map(data, type);
  }

  public <T, S> S convertToResponse(T data, Class<S> type) {
    return modelMapper.map(data, type);
  }

  public <T, S> List<S> convertToResponseList(List<T> lists, Class<S> type) {
    return lists.stream().map(list -> convertToResponse(list, type))
        .collect(Collectors.toList());
  }

  private <T> void updateFieldIfNotNull(T newValue, Consumer<T> setter) {
    if (newValue != null) {
      setter.accept(newValue);
    }
  }

  public <T, S> S mapNonNull(T data, Class<S> targetType) {
    modelMapper.getConfiguration()
        .setFieldMatchingEnabled(true)
        .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
        .setSkipNullEnabled(true);
    return modelMapper.map(data, targetType);
  }


}