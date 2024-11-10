import type { ErrorResponse } from "@/packages/base/src/interceptors/error.interceptor";
import type { ChangePasswordRequest } from "@/packages/user-management-service/src/auth/request/change-password.request";
import type { ChangePasswordResponse } from "@/packages/user-management-service/src/auth/response/change-password.response";
import { userManagementServiceAxios } from "@/packages/user-management-service/src/shared/user-management.axios";
import { useMutation, type UseMutationResult } from "@tanstack/react-query";

export function postChangePasswordApi({
  changePasswordRequest,
}: {
  changePasswordRequest: ChangePasswordRequest;
}): Promise<ChangePasswordResponse> {
  return userManagementServiceAxios.post(
    "/auth/change-password",
    changePasswordRequest
  );
}

export function usePostChangePasswordQuery(): UseMutationResult<
  ChangePasswordResponse,
  ErrorResponse,
  ChangePasswordRequest
> {
  return useMutation<
    ChangePasswordResponse,
    ErrorResponse,
    ChangePasswordRequest
  >({
    mutationFn: (changePasswordRequest) =>
      postChangePasswordApi({ changePasswordRequest }),
  });
}
