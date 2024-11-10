import type { MerchantDto } from "@/packages/fds-service/src/merchant/dto/merchant.dto";
import { fdsServiceAxios } from "@/packages/fds-service/src/shared/fds-service.axios";
import { useQuery, type UseQueryResult } from "@tanstack/react-query";

export function getMerchantApi({ mid }: { mid: string }): Promise<MerchantDto> {
  return fdsServiceAxios.get(`/merchant/${mid}`);
}

export function useGetMerchantQuery({
  mid,
}: {
  mid: string;
}): UseQueryResult<MerchantDto, Error> {
  return useQuery<MerchantDto, Error>({
    queryKey: ["merchant", mid],
    queryFn: () => getMerchantApi({ mid }),
  });
}
