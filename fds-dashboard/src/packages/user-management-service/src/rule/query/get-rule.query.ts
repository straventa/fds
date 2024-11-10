import type { RuleDto } from "@/packages/user-management-service/src/rule/dto/rule.dto";
import { userManagementServiceAxios } from "@/packages/user-management-service/src/shared/user-management.axios";
import { useQuery, type UseQueryResult } from "@tanstack/react-query";

export function getRulesApi({ ruleId }: { ruleId: string }): Promise<RuleDto> {
  return userManagementServiceAxios.get(`/rule/${ruleId}`);
}

export function useGetRuleQuery({
  ruleId,
}: {
  ruleId: string;
}): UseQueryResult<RuleDto, Error> {
  return useQuery<RuleDto, Error>({
    queryKey: ["rule", ruleId],
    queryFn: () => getRulesApi({ ruleId }),
  });
}
