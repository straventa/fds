import { userManagementServiceAxios } from "@/packages/user-management-service/src/shared/user-management.axios";
import { useQuery, type UseQueryResult } from "@tanstack/react-query";

export function getCancelGetFdsTransactionReviewApi(): Promise<string> {
  return userManagementServiceAxios.get(`/fds-transaction/cancel-review-all`);
}

export function useCancelGetFdsTransactionReviewQuery(): UseQueryResult<
  string,
  Error
> {
  return useQuery<string, Error>({
    queryKey: ["fds-transaction-cancel-all"],
    queryFn: () => getCancelGetFdsTransactionReviewApi(),
  });
}
