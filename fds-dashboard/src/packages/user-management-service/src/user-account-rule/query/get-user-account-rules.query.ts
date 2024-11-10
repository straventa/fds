import { userManagementServiceAxios } from "@/packages/user-management-service/src/shared/user-management.axios";
import type { UserAccountRuleDto } from "@/packages/user-management-service/src/user-account-rule/dto/user-account-rule.dto";
import { useQuery, type UseQueryResult } from "@tanstack/react-query";

export function getUserAccountRulesApi(): Promise<UserAccountRuleDto[]> {
  return userManagementServiceAxios.get(`/user-account-rule`);
}

export function useGetUserAccountRulesQuery(): UseQueryResult<
  UserAccountRuleDto[],
  Error
> {
  return useQuery<UserAccountRuleDto[], Error>({
    queryKey: ["user-account-rule"],
    queryFn: getUserAccountRulesApi,
  });
}
