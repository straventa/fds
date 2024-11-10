import type { ErrorResponse } from "@/packages/base/src/interceptors/error.interceptor";
import { userManagementServiceAxios } from "@/packages/user-management-service/src/shared/user-management.axios";
import type { UserAccountResponse } from "@/packages/user-management-service/src/user-account/response/user-account.response";
import { useMutation, type UseMutationResult } from "@tanstack/react-query";

export function postUserAccountApi({
  userAccountRequest,
}: {
  userAccountRequest: UserAccountResponse;
}): Promise<UserAccountResponse> {
  return userManagementServiceAxios.post("/user-account", userAccountRequest);
}

export function usePostUserAccountQuery(): UseMutationResult<
  UserAccountResponse,
  ErrorResponse,
  UserAccountResponse
> {
  return useMutation<UserAccountResponse, ErrorResponse, UserAccountResponse>({
    mutationFn: (userAccountRequest) =>
      postUserAccountApi({ userAccountRequest }),
  });
}
