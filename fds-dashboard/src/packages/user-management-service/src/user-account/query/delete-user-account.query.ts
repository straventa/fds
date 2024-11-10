import type { ErrorResponse } from "@/packages/base/src/interceptors/error.interceptor";
import { userManagementServiceAxios } from "@/packages/user-management-service/src/shared/user-management.axios";
import { type UseMutationResult, useMutation } from "@tanstack/react-query";

export function deleteUserAccountApi({
  userAccountId,
}: {
  userAccountId: string;
}): Promise<void> {
  return userManagementServiceAxios.delete(`/user-account/${userAccountId}`);
}

export function useDeleteUserAccountQuery(): UseMutationResult<
  void,
  ErrorResponse,
  string
> {
  return useMutation<void, ErrorResponse, string>({
    mutationFn: (userAccountId) => deleteUserAccountApi({ userAccountId }),
  });
}
