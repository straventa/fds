import type { ErrorResponse } from "@/packages/base/src/interceptors/error.interceptor";
import type { ResetPasswordRequest } from "@/packages/user-management-service/src/auth/request/reset-password-request";
import type { ResetPasswordResponse } from "@/packages/user-management-service/src/auth/response/reset-password-response";
import { userManagementServiceAxios } from "@/packages/user-management-service/src/shared/user-management.axios";
import { useMutation, type UseMutationResult } from "@tanstack/react-query";

export function postResetPasswordApi({
  resetPasswordRequest,
}: {
  resetPasswordRequest: ResetPasswordRequest;
}): Promise<ResetPasswordResponse> {
  return userManagementServiceAxios.post(
    "/auth/reset-password",
    resetPasswordRequest
  );
}

export function usePostResetPasswordQuery(): UseMutationResult<
  ResetPasswordResponse,
  ErrorResponse,
  ResetPasswordRequest
> {
  return useMutation<
    ResetPasswordResponse,
    ErrorResponse,
    ResetPasswordRequest
  >({
    mutationFn: (resetPasswordRequest) =>
      postResetPasswordApi({ resetPasswordRequest }),
  });
}
