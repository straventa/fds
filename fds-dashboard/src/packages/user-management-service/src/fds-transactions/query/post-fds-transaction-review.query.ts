import type { FdsTransactionsDto } from "@/packages/user-management-service/src/fds-transactions/dto/fds-transactions.dto";
import { userManagementServiceAxios } from "@/packages/user-management-service/src/shared/user-management.axios";
import { type UseMutationResult, useMutation } from "@tanstack/react-query";

export function postFdsTransactionReviewApi({
  data,
}: {
  data: FdsTransactionsDto;
}): Promise<string> {
  return userManagementServiceAxios.post(`/fds-transaction/review`, data);
}

export function usePostFdsTransactionReviewQuery(): UseMutationResult<
  string,
  Error,
  {
    data: FdsTransactionsDto;
  }
> {
  return useMutation<
    string,
    Error,
    {
      data: FdsTransactionsDto;
    }
  >({
    mutationFn: postFdsTransactionReviewApi,
  });
}
