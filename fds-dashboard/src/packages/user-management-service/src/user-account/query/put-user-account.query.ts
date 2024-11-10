import { userManagementServiceAxios } from "@/packages/user-management-service/src/shared/user-management.axios";
import type { UserAccountResponse } from "@/packages/user-management-service/src/user-account/response/user-account.response";
import { useMutation, type UseMutationResult } from "@tanstack/react-query";

export function putUserAccountApi({
  userAccountId,
  userAccountRequest,
}: {
  userAccountId: string;
  userAccountRequest: UserAccountResponse;
}): Promise<UserAccountResponse> {
  return userManagementServiceAxios.put(
    `/user-account/${userAccountId}`,
    userAccountRequest
  );
}

export function usePutUserAccountQuery(): UseMutationResult<
  UserAccountResponse,
  Error,
  {
    userAccountId: string;
    userAccountRequest: UserAccountResponse;
  }
> {
  return useMutation<
    UserAccountResponse,
    Error,
    {
      userAccountId: string;
      userAccountRequest: UserAccountResponse;
    }
  >({
    mutationFn: putUserAccountApi,
  });
}
