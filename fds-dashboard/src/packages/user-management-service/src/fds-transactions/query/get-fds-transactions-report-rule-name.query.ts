import type { FdsTransactionReportRuleNameDto } from "@/packages/user-management-service/src/fds-transactions/dto/fds-transactions-report-rule-name.dto";
import type { GetFdsTransactionsReportAnalystParam } from "@/packages/user-management-service/src/fds-transactions/param/get-fds-transactions-report-analyst.param";
import { userManagementServiceAxios } from "@/packages/user-management-service/src/shared/user-management.axios";
import { useQuery, type UseQueryResult } from "@tanstack/react-query";

export function getFdsTransactionsReportRuleNameApi({
  getFdsTransactionsReportAnalystParam,
}: {
  getFdsTransactionsReportAnalystParam: GetFdsTransactionsReportAnalystParam;
}): Promise<FdsTransactionReportRuleNameDto[]> {
  return userManagementServiceAxios.get("/fds-transaction/report/rule-name", {
    params: {
      ...getFdsTransactionsReportAnalystParam,
    },
  });
}

export function useGetFdsTransactionsReportRuleNameQuery({
  getFdsTransactionsReportAnalystParam,
}: {
  getFdsTransactionsReportAnalystParam: GetFdsTransactionsReportAnalystParam;
}): UseQueryResult<FdsTransactionReportRuleNameDto[], Error> {
  return useQuery<FdsTransactionReportRuleNameDto[], Error>({
    queryKey: [
      "fds-transaction-report-rule-name",
      getFdsTransactionsReportAnalystParam.startDate,
    ],
    queryFn: () =>
      getFdsTransactionsReportRuleNameApi({
        getFdsTransactionsReportAnalystParam,
      }),
  });
}
