import type { BasePageableDto } from "@/packages/base/src/dto/base-pageable.dto";
import type { BaseResponse } from "@/packages/base/src/response/base.response";
import type { UserAccountRuleDto } from "@/packages/user-management-service/src/user-account-rule/dto/user-account-rule.dto";

export type RuleDto = {
  ruleId: string;
  ruleName: string;
  ruleDescription: string;
  sourceData: string;
  riskLevel: string;
  riskLevelNumber: number;

  userAccountRule: UserAccountRuleDto[];
} & BaseResponse;

export type RulePageableDto = {
  content: RuleDto[];
} & BasePageableDto;
