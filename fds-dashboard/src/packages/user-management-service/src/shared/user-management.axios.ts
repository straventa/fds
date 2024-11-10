import { createAxiosInstance } from "@/packages/base/src/util/axios.util";
import { NEXT_PUBLIC_USER_MANAGEMENT_SERVICE_URL } from "@/packages/user-management-service/src/shared/user-management.config";

export const userManagementServiceAxios = createAxiosInstance(
  NEXT_PUBLIC_USER_MANAGEMENT_SERVICE_URL,
  true
);
export const userManagementServiceRawAxios = createAxiosInstance(
  NEXT_PUBLIC_USER_MANAGEMENT_SERVICE_URL,
  false
);
