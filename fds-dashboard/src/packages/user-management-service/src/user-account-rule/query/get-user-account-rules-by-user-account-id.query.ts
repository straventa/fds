import { userManagementServiceAxios } from "@/packages/user-management-service/src/shared/user-management.axios";
import type { UserAccountRuleDto } from "@/packages/user-management-service/src/user-account-rule/dto/user-account-rule.dto";
import { useQuery, type UseQueryResult } from "@tanstack/react-query";

export default function getUserAccountRulesByUserAccountIdApi({
  userAccountId,
}: {
  userAccountId: string;
}): Promise<UserAccountRuleDto[]> {
  return userManagementServiceAxios.get(
    `/user-account/${userAccountId}/user-account-rule`
  );
}

export function useGetUserAccountRulesByUserAccountIdQuery({
  userAccountId,
}: {
  userAccountId: string;
}): UseQueryResult<UserAccountRuleDto[], Error> {
  return useQuery<UserAccountRuleDto[], Error>({
    queryKey: ["user-account-rule-by-user-account-id", userAccountId],
    queryFn: () => getUserAccountRulesByUserAccountIdApi({ userAccountId }),
  });
}
