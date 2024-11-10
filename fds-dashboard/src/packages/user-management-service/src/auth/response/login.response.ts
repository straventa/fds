import type { BaseResponse } from "@/packages/base/src/response/base.response";
import type { UserAccountResponse } from "@/packages/user-management-service/src/user-account/response/user-account.response";
export type LoginResponse = {
  accessToken: string;
  refreshToken: string;
  userAccount: UserAccountResponse;
} & BaseResponse;
