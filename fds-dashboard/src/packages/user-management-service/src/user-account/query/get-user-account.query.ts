import { userManagementServiceAxios } from "@/packages/user-management-service/src/shared/user-management.axios";
import type { UserAccountResponse } from "@/packages/user-management-service/src/user-account/response/user-account.response";
import { useQuery, type UseQueryResult } from "@tanstack/react-query";

export default function getUserAccountApi({
  userAccountId,
}: {
  userAccountId: string;
}): Promise<UserAccountResponse> {
  return userManagementServiceAxios.get(`/user-account/${userAccountId}`);
}

export function useGetUserAccountQuery({
  userAccountId,
}: {
  userAccountId: string;
}): UseQueryResult<UserAccountResponse, Error> {
  return useQuery<UserAccountResponse, Error>({
    queryKey: ["user-account", userAccountId],
    queryFn: () => getUserAccountApi({ userAccountId }),
  });
}
