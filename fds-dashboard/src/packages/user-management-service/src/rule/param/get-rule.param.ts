import type { BaseParam } from "@/packages/base/src/param/base.param";

export type GetRuleParam = {
  riskLevel: string | null;
  ruleName: string | null;
  ruleDescription: string | null;
  sourceData: string | null;
  userAccountId: string[] | null;
} & BaseParam;
