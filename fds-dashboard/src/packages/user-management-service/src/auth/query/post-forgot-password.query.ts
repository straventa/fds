import type { ErrorResponse } from "@/packages/base/src/interceptors/error.interceptor";
import type { ForgotPasswordRequest } from "@/packages/user-management-service/src/auth/request/forgot-password-request";
import type { ForgotPasswordResponse } from "@/packages/user-management-service/src/auth/response/forgot-password-response";
import { userManagementServiceAxios } from "@/packages/user-management-service/src/shared/user-management.axios";
import { useMutation, type UseMutationResult } from "@tanstack/react-query";

export function postForgotPasswordApi({
  setupPasswordRequest,
}: {
  setupPasswordRequest: ForgotPasswordRequest;
}): Promise<ForgotPasswordResponse> {
  return userManagementServiceAxios.post(
    "/auth/forgot-password",
    setupPasswordRequest
  );
}

export function usePostForgotPasswordQuery(): UseMutationResult<
  ForgotPasswordResponse,
  ErrorResponse,
  ForgotPasswordRequest
> {
  return useMutation<
    ForgotPasswordResponse,
    ErrorResponse,
    ForgotPasswordRequest
  >({
    mutationFn: (setupPasswordRequest) =>
      postForgotPasswordApi({ setupPasswordRequest }),
  });
}
