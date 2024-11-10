package com.yokke.usermanagement.user_account_rule;

import com.yokke.base.mapper.BaseMapper;
import com.yokke.usermanagement.rule.Rule;
import com.yokke.usermanagement.rule.RuleDto;
import com.yokke.usermanagement.user_account.UserAccountDTO;
import org.springframework.stereotype.Service;

@Service
public class UserAccountRuleMapper extends BaseMapper {


    public UserAccountRuleDto mapToDto(UserAccountRule entity, UserAccountRuleDto dto) {
        dto = mapAudit(dto,entity);
        dto.setUserRuleId(entity.getUserRuleId());
        dto.setIsActive(entity.getIsActive());
        dto.setPriority(entity.getPriority());
        return dto;
    }

    public UserAccountRule mapToEntity(UserAccountRuleDto dto, UserAccountRule entity) {
        entity.setIsActive(dto.getIsActive());
        entity.setPriority(dto.getPriority());
        return entity;
    }
    public UserAccountRule mapToEntity(UserAccountRuleDto dto, UserAccountRule entity, Rule rule) {
        entity.setIsActive(dto.getIsActive());
        entity.setPriority(dto.getPriority());
        entity.setRule(rule);
        return entity;
    }

    public UserAccountRuleDto mapToDto(UserAccountRule userAccountRule, UserAccountRuleDto userAccountRuleDto, RuleDto ruleDto){
        userAccountRuleDto = mapToDto(userAccountRule,userAccountRuleDto);
        userAccountRuleDto.setRule(ruleDto);
        return userAccountRuleDto;
    }

    public UserAccountRuleDto mapToDto(UserAccountRule userAccountRule, UserAccountRuleDto userAccountRuleDto, UserAccountDTO userAccountDTO){
        userAccountRuleDto = mapToDto(userAccountRule,userAccountRuleDto);
        userAccountRuleDto.setUserAccount(userAccountDTO);
        return userAccountRuleDto;
    }

}
