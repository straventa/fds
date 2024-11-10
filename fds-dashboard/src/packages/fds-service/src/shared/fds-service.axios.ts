import { createAxiosInstance } from "@/packages/base/src/util/axios.util";
import { NEXT_PUBLIC_FDS_SERVICE_URL } from "@/packages/fds-service/src/shared/fds-service.config";

export const fdsServiceAxios = createAxiosInstance(
  NEXT_PUBLIC_FDS_SERVICE_URL,
  true
);
export const fdsServiceRawAxios = createAxiosInstance(
  NEXT_PUBLIC_FDS_SERVICE_URL,
  false
);
