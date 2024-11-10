import type { AlertFdsParameterAnalystDto } from "@/packages/fds-service/src/alert-fds-parameter/dto/alert-fds-parameter-analyst.dto";
import { fdsServiceAxios } from "@/packages/fds-service/src/shared/fds-service.axios";
import { useQuery, type UseQueryResult } from "@tanstack/react-query";

export function getAlertFdsParameterAnalystApi({
  startDate,
  endDate,
}: {
  startDate: Date;
  endDate: Date;
}): Promise<AlertFdsParameterAnalystDto[]> {
  return fdsServiceAxios.get(`/alert-fds-parameter/statistics/analyst`, {
    params: {
      startDate: startDate,
      endDate: endDate,
    },
  });
}

export function useGetAlertFdsParameterAnalystQuery({
  startDate,
  endDate,
}: {
  startDate: Date;
  endDate: Date;
}): UseQueryResult<AlertFdsParameterAnalystDto[], Error> {
  return useQuery<AlertFdsParameterAnalystDto[], Error>({
    queryKey: ["alert-fds-parameter", "analyst", startDate, endDate],
    queryFn: () => getAlertFdsParameterAnalystApi({ startDate, endDate }),
  });
}
