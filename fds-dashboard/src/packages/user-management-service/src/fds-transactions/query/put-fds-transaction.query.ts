import type { FdsTransactionsDto } from "@/packages/user-management-service/src/fds-transactions/dto/fds-transactions.dto";
import { userManagementServiceAxios } from "@/packages/user-management-service/src/shared/user-management.axios";
import { type UseMutationResult, useMutation } from "@tanstack/react-query";

export function putFdsTransactionApi({
  fdsTransactionId,
  data,
}: {
  fdsTransactionId: string;
  data: FdsTransactionsDto;
}): Promise<FdsTransactionsDto> {
  return userManagementServiceAxios.put(
    `/fds-transaction/${fdsTransactionId}`,
    data
  );
}

export function usePutFdsTransactionQuery(): UseMutationResult<
  FdsTransactionsDto,
  Error,
  {
    fdsTransactionId: string;
    data: FdsTransactionsDto;
  }
> {
  return useMutation<
    FdsTransactionsDto,
    Error,
    {
      fdsTransactionId: string;
      data: FdsTransactionsDto;
    }
  >({
    mutationFn: putFdsTransactionApi,
  });
}
