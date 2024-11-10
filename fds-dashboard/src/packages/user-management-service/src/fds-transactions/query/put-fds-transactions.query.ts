import type { FdsTransactionsDto } from "@/packages/user-management-service/src/fds-transactions/dto/fds-transactions.dto";
import { userManagementServiceAxios } from "@/packages/user-management-service/src/shared/user-management.axios";
import { useMutation, type UseMutationResult } from "@tanstack/react-query";

export function putFdsTransactionsApi({
  data,
}: {
  data: FdsTransactionsDto[];
}): Promise<FdsTransactionsDto[]> {
  return userManagementServiceAxios.put(`/fds-transaction`, data);
}

export function usePutFdsTransactionsQuery(): UseMutationResult<
  FdsTransactionsDto[],
  Error,
  {
    data: FdsTransactionsDto[];
  }
> {
  return useMutation<
    FdsTransactionsDto[],
    Error,
    {
      data: FdsTransactionsDto[];
    }
  >({
    mutationFn: putFdsTransactionsApi,
  });
}
