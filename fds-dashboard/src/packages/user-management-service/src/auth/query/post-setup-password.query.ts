import type { ErrorResponse } from "@/packages/base/src/interceptors/error.interceptor";
import type { SetupPasswordRequest } from "@/packages/user-management-service/src/auth/request/setup-password.request";
import type { SetupPasswordResponse } from "@/packages/user-management-service/src/auth/response/setup-password.response";
import { userManagementServiceAxios } from "@/packages/user-management-service/src/shared/user-management.axios";
import { useMutation, type UseMutationResult } from "@tanstack/react-query";

export function postSetupPasswordApi({
  setupPasswordRequest,
}: {
  setupPasswordRequest: SetupPasswordRequest;
}): Promise<SetupPasswordResponse> {
  return userManagementServiceAxios.post(
    "/auth/setup-password",
    setupPasswordRequest
  );
}

export function usePostSetupPasswordQuery(): UseMutationResult<
  SetupPasswordResponse,
  ErrorResponse,
  SetupPasswordRequest
> {
  return useMutation<
    SetupPasswordResponse,
    ErrorResponse,
    SetupPasswordRequest
  >({
    mutationFn: (setupPasswordRequest) =>
      postSetupPasswordApi({ setupPasswordRequest }),
  });
}
