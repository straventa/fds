import type { OwnerDto } from "@/packages/fds-service/src/owner/dto/owner.dto";
import { fdsServiceAxios } from "@/packages/fds-service/src/shared/fds-service.axios";
import { useQuery, type UseQueryResult } from "@tanstack/react-query";

export function getOwnerApi({ oid }: { oid: string }): Promise<OwnerDto> {
  return fdsServiceAxios.get(`/owner/${oid}`);
}

export function useGetOwnerQuery({
  oid,
}: {
  oid: string;
}): UseQueryResult<OwnerDto, Error> {
  return useQuery<OwnerDto, Error>({
    queryKey: ["owner", oid],
    queryFn: () => getOwnerApi({ oid }),
  });
}
