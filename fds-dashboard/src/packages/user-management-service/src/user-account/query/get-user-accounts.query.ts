import { userManagementServiceAxios } from "@/packages/user-management-service/src/shared/user-management.axios";
import type { GetUserAccountParam } from "@/packages/user-management-service/src/user-account/param/get-user-account.param";
import type { UserAccountPageableResponse } from "@/packages/user-management-service/src/user-account/response/user-account.response";
import { useQuery, type UseQueryResult } from "@tanstack/react-query";

export default function getUserAccountsApi({
  getUserAccountParam,
}: {
  getUserAccountParam: GetUserAccountParam;
}): Promise<UserAccountPageableResponse> {
  return userManagementServiceAxios.get("/user-account", {
    params: {
      ...getUserAccountParam,
    },
  });
}

export function useGetUserAccountsQuery({
  getUserAccountParam,
}: {
  getUserAccountParam: GetUserAccountParam;
}): UseQueryResult<UserAccountPageableResponse, Error> {
  return useQuery<UserAccountPageableResponse, Error>({
    queryKey: ["user-account", getUserAccountParam],
    queryFn: () => getUserAccountsApi({ getUserAccountParam }),
  });
}
