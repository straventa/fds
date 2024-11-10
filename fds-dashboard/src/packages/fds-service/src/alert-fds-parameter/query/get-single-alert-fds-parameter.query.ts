import type { AlertFdsParameterDto } from "@/packages/fds-service/src/alert-fds-parameter/dto/alert-fds-parameter.dto";
import { fdsServiceAxios } from "@/packages/fds-service/src/shared/fds-service.axios";
import { useQuery, type UseQueryResult } from "@tanstack/react-query";

export function getSingleAlertFdsParameterApi({
  uniqueId,
}: {
  uniqueId: string;
}): Promise<AlertFdsParameterDto> {
  return fdsServiceAxios.get(`/alert-fds-parameter/${uniqueId}`);
}

export function useGetSingleAlertFdsParameter({
  uniqueId,
}: {
  uniqueId: string;
}): UseQueryResult<AlertFdsParameterDto, Error> {
  return useQuery<AlertFdsParameterDto, Error>({
    queryKey: ["alert-fds-parameter", uniqueId],
    queryFn: () => getSingleAlertFdsParameterApi({ uniqueId }),
  });
}
