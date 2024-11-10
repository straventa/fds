import type { FdsTransactionsPageableDto } from "@/packages/user-management-service/src/fds-transactions/dto/fds-transactions.dto";
import type { GetFdsTransactionsParam } from "@/packages/user-management-service/src/fds-transactions/param/get-fds-transactions.param";
import { userManagementServiceAxios } from "@/packages/user-management-service/src/shared/user-management.axios";
import { useQuery, type UseQueryResult } from "@tanstack/react-query";

export function getFdsTransactionsApi({
  getFdsTransactionsParam,
}: {
  getFdsTransactionsParam: GetFdsTransactionsParam;
}): Promise<FdsTransactionsPageableDto> {
  return userManagementServiceAxios.get("/fds-transaction", {
    params: {
      ...getFdsTransactionsParam,
    },
  });
}

export function useGetFdsTransactionsQuery({
  getFdsTransactionsParam,
}: {
  getFdsTransactionsParam: GetFdsTransactionsParam;
}): UseQueryResult<FdsTransactionsPageableDto, Error> {
  return useQuery<FdsTransactionsPageableDto, Error>({
    queryKey: ["fds-transactions", getFdsTransactionsParam.actionType],
    queryFn: () => getFdsTransactionsApi({ getFdsTransactionsParam }),
  });
}
