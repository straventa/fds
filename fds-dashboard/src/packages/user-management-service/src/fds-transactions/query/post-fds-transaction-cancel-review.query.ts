import type { ErrorResponse } from "@/packages/base/src/interceptors/error.interceptor";
import type { FdsTransactionsDto } from "@/packages/user-management-service/src/fds-transactions/dto/fds-transactions.dto";
import { userManagementServiceAxios } from "@/packages/user-management-service/src/shared/user-management.axios";
import { type UseMutationResult, useMutation } from "@tanstack/react-query";

export function postFdsTransactionCancelReviewApi({
  data,
}: {
  data: FdsTransactionsDto;
}): Promise<string> {
  return userManagementServiceAxios.post(
    `/fds-transaction/cancel-review`,
    data
  );
}

export function usePostFdsTransactionCancelReviewQuery(): UseMutationResult<
  string,
  ErrorResponse,
  {
    data: FdsTransactionsDto;
  }
> {
  return useMutation<
    string,
    ErrorResponse,
    {
      data: FdsTransactionsDto;
    }
  >({
    mutationFn: postFdsTransactionCancelReviewApi,
  });
}
