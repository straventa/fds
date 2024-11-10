import type { BasePageableResponse } from "@/packages/base/src/response/base.pageable.response";
import type { BaseResponse } from "@/packages/base/src/response/base.response";
import type { RoleResponse } from "@/packages/user-management-service/src/role/response/role.response";
import type { UserAccountRuleDto } from "@/packages/user-management-service/src/user-account-rule/dto/user-account-rule.dto";
export type UserAccountResponse = {
  userAccountId: string;
  username: string;
  email: string;
  passwordDateChanged: Date;
  isLocked: boolean;
  isPasswordSetup: boolean;
  role: RoleResponse[];
  userAccountRule: UserAccountRuleDto[];
} & BaseResponse;

export type UserAccountPageableResponse = {
  content: UserAccountResponse[];
} & BasePageableResponse;
