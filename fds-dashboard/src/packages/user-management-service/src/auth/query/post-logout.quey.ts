import type { ErrorResponse } from "@/packages/base/src/interceptors/error.interceptor";
import { userManagementServiceAxios } from "@/packages/user-management-service/src/shared/user-management.axios";
import { type UseMutationResult, useMutation } from "@tanstack/react-query";

export function postLogoutApi(): Promise<void> {
  return userManagementServiceAxios.post("/auth/logout");
}

export function usePostLogoutQuery(): UseMutationResult<
  void,
  ErrorResponse,
  void
> {
  return useMutation<void, ErrorResponse, void>({
    mutationFn: () => postLogoutApi(),
  });
}
