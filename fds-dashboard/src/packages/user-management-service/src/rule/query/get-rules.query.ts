import type { RulePageableDto } from "@/packages/user-management-service/src/rule/dto/rule.dto";
import type { GetRuleParam } from "@/packages/user-management-service/src/rule/param/get-rule.param";
import { userManagementServiceAxios } from "@/packages/user-management-service/src/shared/user-management.axios";
import { useQuery, type UseQueryResult } from "@tanstack/react-query";

export function getRulesApi({
  getRulesParam,
}: {
  getRulesParam: GetRuleParam;
}): Promise<RulePageableDto> {
  return userManagementServiceAxios.get("/rule", {
    params: {
      ...getRulesParam,
    },
  });
}

export function useGetRulesQuery({
  getRulesParam,
}: {
  getRulesParam: GetRuleParam;
}): UseQueryResult<RulePageableDto, Error> {
  return useQuery<RulePageableDto, Error>({
    queryKey: ["rule", getRulesParam],
    queryFn: () => getRulesApi({ getRulesParam }),
  });
}
