import type { FdsTransactionsDto } from "@/packages/user-management-service/src/fds-transactions/dto/fds-transactions.dto";
import { userManagementServiceAxios } from "@/packages/user-management-service/src/shared/user-management.axios";
import { useQuery, type UseQueryResult } from "@tanstack/react-query";

export function getFdsTransactionReviewApi({
  fdsTransactionId,
}: {
  fdsTransactionId: string;
}): Promise<FdsTransactionsDto> {
  return userManagementServiceAxios.get(
    `/fds-transaction/review/${fdsTransactionId}`
  );
}

export function useGetFdsTransactionReviewQuery({
  fdsTransactionId,
}: {
  fdsTransactionId: string;
}): UseQueryResult<FdsTransactionsDto, Error> {
  return useQuery<FdsTransactionsDto, Error>({
    queryKey: ["fds-transaction-review", fdsTransactionId],
    queryFn: () => getFdsTransactionReviewApi({ fdsTransactionId }),
  });
}
