import type { ErrorResponse } from "@/packages/base/src/interceptors/error.interceptor";
import type { FdsParameterPageableDto } from "@/packages/user-management-service/src/fds-parameter/dto/fds-parameter.dto";
import type { GetFdsParameterParam } from "@/packages/user-management-service/src/fds-parameter/param/get-fds-parameter.param";
import { userManagementServiceAxios } from "@/packages/user-management-service/src/shared/user-management.axios";
import { type UseQueryResult, useQuery } from "@tanstack/react-query";

export function getFdsParametersApi({
  getFdsParameterParam,
}: {
  getFdsParameterParam: GetFdsParameterParam;
}): Promise<FdsParameterPageableDto> {
  return userManagementServiceAxios.get(`/fds-parameter`, {
    params: {
      ...getFdsParameterParam,
    },
  });
}

export function useGetFdsParametersQuery({
  getFdsParameterParam,
}: {
  getFdsParameterParam: GetFdsParameterParam;
}): UseQueryResult<FdsParameterPageableDto, ErrorResponse> {
  return useQuery<FdsParameterPageableDto, ErrorResponse>({
    queryKey: ["fds-parameter", getFdsParameterParam.page],
    queryFn: () => getFdsParametersApi({ getFdsParameterParam }),
  });
}
