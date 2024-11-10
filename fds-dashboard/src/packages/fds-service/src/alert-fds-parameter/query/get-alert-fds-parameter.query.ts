import type { AlertFdsParameterPageableDto } from "@/packages/fds-service/src/alert-fds-parameter/dto/alert-fds-parameter.dto";
import type { GetAlertFdsParameterParam } from "@/packages/fds-service/src/alert-fds-parameter/param/get-alert-fds-parameter.param";
import { fdsServiceAxios } from "@/packages/fds-service/src/shared/fds-service.axios";
import { useQuery, type UseQueryResult } from "@tanstack/react-query";

export function getAlertFdsParameterApi({
  getAlertFdsParameterParam,
}: {
  getAlertFdsParameterParam: GetAlertFdsParameterParam;
}): Promise<AlertFdsParameterPageableDto> {
  return fdsServiceAxios.get("/alert-fds-parameter", {
    params: {
      ...getAlertFdsParameterParam,
    },
  });
}

export function useGetAlertFdsParameter({
  getAlertFdsParameterParam,
}: {
  getAlertFdsParameterParam: GetAlertFdsParameterParam;
}): UseQueryResult<AlertFdsParameterPageableDto, Error> {
  return useQuery<AlertFdsParameterPageableDto, Error>({
    queryKey: ["alert-fds-parameter", getAlertFdsParameterParam.actionType],
    queryFn: () => getAlertFdsParameterApi({ getAlertFdsParameterParam }),
  });
}
