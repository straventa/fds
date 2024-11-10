import type { RuleDto } from "@/packages/user-management-service/src/rule/dto/rule.dto";
import { userManagementServiceAxios } from "@/packages/user-management-service/src/shared/user-management.axios";
import { useMutation, type UseMutationResult } from "@tanstack/react-query";

export function putRuleApi({
  ruleId,
  ruleDto,
}: {
  ruleId: string;
  ruleDto: RuleDto;
}): Promise<RuleDto> {
  return userManagementServiceAxios.put(`/rule/${ruleId}`, ruleDto);
}

export function usePutRuleQuery(): UseMutationResult<
  RuleDto,
  Error,
  {
    ruleId: string;
    ruleDto: RuleDto;
  }
> {
  return useMutation<
    RuleDto,
    Error,
    {
      ruleId: string;
      ruleDto: RuleDto;
    }
  >({
    mutationFn: putRuleApi,
  });
}
