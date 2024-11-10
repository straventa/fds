import { userManagementServiceAxios } from "@/packages/user-management-service/src/shared/user-management.axios";
import type { UserAccountResponse } from "@/packages/user-management-service/src/user-account/response/user-account.response";
import { useQuery, type UseQueryResult } from "@tanstack/react-query";

export function getMeApi(): Promise<UserAccountResponse> {
  return userManagementServiceAxios.get(`/auth/status`);
}

export function useGetMeQuery(): UseQueryResult<UserAccountResponse, Error> {
  return useQuery<UserAccountResponse, Error>({
    queryKey: ["user-account", "me"],
    queryFn: getMeApi,
    refetchInterval: 5000,
  });
}
