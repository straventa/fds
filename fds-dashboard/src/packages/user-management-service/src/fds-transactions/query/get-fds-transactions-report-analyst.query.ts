import type { FdsTransactionReportAnalystDto } from "@/packages/user-management-service/src/fds-transactions/dto/fds-transactions-report-analyst.dto";
import type { GetFdsTransactionsReportAnalystParam } from "@/packages/user-management-service/src/fds-transactions/param/get-fds-transactions-report-analyst.param";
import { userManagementServiceAxios } from "@/packages/user-management-service/src/shared/user-management.axios";
import { useQuery, type UseQueryResult } from "@tanstack/react-query";

export function getFdsTransactionsReportAnalystApi({
  getFdsTransactionsReportAnalystParam,
}: {
  getFdsTransactionsReportAnalystParam: GetFdsTransactionsReportAnalystParam;
}): Promise<FdsTransactionReportAnalystDto[]> {
  return userManagementServiceAxios.get("/fds-transaction/report/analyst", {
    params: {
      ...getFdsTransactionsReportAnalystParam,
    },
  });
}

export function useGetFdsTransactionsReportAnalystQuery({
  getFdsTransactionsReportAnalystParam,
}: {
  getFdsTransactionsReportAnalystParam: GetFdsTransactionsReportAnalystParam;
}): UseQueryResult<FdsTransactionReportAnalystDto[], Error> {
  return useQuery<FdsTransactionReportAnalystDto[], Error>({
    queryKey: [
      "fds-transaction-report-analyst",
      getFdsTransactionsReportAnalystParam.startDate,
    ],
    queryFn: () =>
      getFdsTransactionsReportAnalystApi({
        getFdsTransactionsReportAnalystParam,
      }),
  });
}
