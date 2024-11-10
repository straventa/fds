import type { CddDto } from "@/packages/fds-service/src/cdd/dto/cdd.dto";
import { fdsServiceAxios } from "@/packages/fds-service/src/shared/fds-service.axios";
import { useQuery, type UseQueryResult } from "@tanstack/react-query";

export function getCddApi({ cddId }: { cddId: string }): Promise<CddDto> {
  return fdsServiceAxios.get(`/cdd/${cddId}`);
}

export function useGetCddQuery({
  cddId,
}: {
  cddId: string;
}): UseQueryResult<CddDto, Error> {
  return useQuery<CddDto, Error>({
    queryKey: ["cdd", cddId],
    queryFn: () => getCddApi({ cddId }),
  });
}
