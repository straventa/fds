import type { FdsTransactionsMerchantReportDto } from "@/packages/user-management-service/src/fds-transactions/dto/fds-transactions-report-merchant.dto";
import type { GetFdsTransactionsReportAnalystParam } from "@/packages/user-management-service/src/fds-transactions/param/get-fds-transactions-report-analyst.param";
import { userManagementServiceAxios } from "@/packages/user-management-service/src/shared/user-management.axios";
import { useQuery, type UseQueryResult } from "@tanstack/react-query";

export function getFdsTransactionsReportMerchantApi({
  getFdsTransactionsReportAnalystParam,
}: {
  getFdsTransactionsReportAnalystParam: GetFdsTransactionsReportAnalystParam;
}): Promise<FdsTransactionsMerchantReportDto[]> {
  return userManagementServiceAxios.get("/fds-transaction/report/merchant", {
    params: {
      ...getFdsTransactionsReportAnalystParam,
    },
  });
}

export function useGetFdsTransactionsReportMerchantQuery({
  getFdsTransactionsReportAnalystParam,
}: {
  getFdsTransactionsReportAnalystParam: GetFdsTransactionsReportAnalystParam;
}): UseQueryResult<FdsTransactionsMerchantReportDto[], Error> {
  return useQuery<FdsTransactionsMerchantReportDto[], Error>({
    queryKey: [
      "fds-transaction-report-merchant",
      getFdsTransactionsReportAnalystParam.startDate,
    ],
    queryFn: () =>
      getFdsTransactionsReportMerchantApi({
        getFdsTransactionsReportAnalystParam,
      }),
  });
}
