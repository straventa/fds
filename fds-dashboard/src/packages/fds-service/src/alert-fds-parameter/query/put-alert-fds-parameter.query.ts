import type { AlertFdsParameterDto } from "@/packages/fds-service/src/alert-fds-parameter/dto/alert-fds-parameter.dto";
import { fdsServiceAxios } from "@/packages/fds-service/src/shared/fds-service.axios";
import { useMutation, type UseMutationResult } from "@tanstack/react-query";

export function putAlertFdsParameterApi({
  uniqueId,
  data,
}: {
  uniqueId: string;
  data: AlertFdsParameterDto;
}): Promise<AlertFdsParameterDto> {
  return fdsServiceAxios.put(`/alert-fds-parameter/${uniqueId}`, data);
}

// export function usePutAlertFdsParameter(): UseMutationResult<
//   AlertFdsParameterDto,
//   Error,
//   AlertFdsParameterDto,
//   unknown
// > {
//   return useMutation<AlertFdsParameterDto, Error, AlertFdsParameterDto>({
//     mutationFn: (uniqueId, data) => putAlertFdsParameterApi({ uniqueId, data }),
//   });
// }
export function usePutAlertFdsParameter(): UseMutationResult<
  AlertFdsParameterDto,
  Error,
  {
    uniqueId: string;
    data: AlertFdsParameterDto;
  }
> {
  return useMutation<
    AlertFdsParameterDto,
    Error,
    {
      uniqueId: string;
      data: AlertFdsParameterDto;
    }
  >({
    mutationFn: putAlertFdsParameterApi,
  });
}
