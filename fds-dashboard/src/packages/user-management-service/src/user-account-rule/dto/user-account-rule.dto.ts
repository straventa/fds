import type { BasePageableDto } from "@/packages/base/src/dto/base-pageable.dto";
import type { BaseDto } from "@/packages/base/src/dto/base.dto";

import type { RuleDto } from "@/packages/user-management-service/src/rule/dto/rule.dto";
import type { UserAccountResponse } from "@/packages/user-management-service/src/user-account/response/user-account.response";

export type UserAccountRuleDto = {
  userRuleId: string;
  isActive: boolean;
  priority: number;
  userAccount: UserAccountResponse;
  rule: RuleDto;
} & BaseDto;

export type UserAccountRulePageableDto = {
  content: UserAccountRuleDto[];
} & BasePageableDto;
