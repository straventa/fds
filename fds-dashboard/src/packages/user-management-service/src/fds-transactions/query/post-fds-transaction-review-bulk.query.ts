import type { ErrorResponse } from "@/packages/base/src/interceptors/error.interceptor";
import type { FdsTransactionsDto } from "@/packages/user-management-service/src/fds-transactions/dto/fds-transactions.dto";
import { userManagementServiceAxios } from "@/packages/user-management-service/src/shared/user-management.axios";
import { type UseMutationResult, useMutation } from "@tanstack/react-query";

export function postFdsTransactionReviewBulkApi({
  data,
}: {
  data: FdsTransactionsDto[];
}): Promise<FdsTransactionsDto[]> {
  return userManagementServiceAxios.post(`/fds-transaction/review-bulk`, data);
}

export function usePostFdsTransactionReviewBulkQuery(): UseMutationResult<
  FdsTransactionsDto[],
  ErrorResponse,
  {
    data: FdsTransactionsDto[];
  }
> {
  return useMutation<
    FdsTransactionsDto[],
    ErrorResponse,
    {
      data: FdsTransactionsDto[];
    }
  >({
    mutationFn: postFdsTransactionReviewBulkApi,
  });
}