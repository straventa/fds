import type { RuleStatisticsDto } from "@/packages/user-management-service/src/rule/dto/rule-statistics.dto";
import { userManagementServiceAxios } from "@/packages/user-management-service/src/shared/user-management.axios";
import { useQuery, type UseQueryResult } from "@tanstack/react-query";

export function getRuleStatisticsApi({
  ruleId,
  startDate,
  endDate,
}: {
  ruleId: string;
  startDate: Date;
  endDate: Date;
}): Promise<RuleStatisticsDto> {
  return userManagementServiceAxios.get(
    `/rule/${ruleId}/statistics?start-date=${startDate.toISOString()}&end-date=${endDate.toISOString()}`
  );
}

export function useGetRuleStatisticsQuery({
  ruleId,
  startDate,
  endDate,
}: {
  ruleId: string;
  startDate: Date;
  endDate: Date;
}): UseQueryResult<RuleStatisticsDto, Error> {
  return useQuery<RuleStatisticsDto, Error>({
    queryKey: ["rule", ruleId, startDate, endDate],
    queryFn: () => getRuleStatisticsApi({ ruleId, startDate, endDate }),
  });
}
