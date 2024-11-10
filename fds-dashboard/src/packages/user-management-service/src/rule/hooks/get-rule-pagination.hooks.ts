import {
  useBasePagination,
  type BasePaginationFunction,
} from "@/packages/base/src/hooks/base.pagination.hooks";
import type { GetRuleParam } from "@/packages/user-management-service/src/rule/param/get-rule.param";
import { useState } from "react";

export type GetRulePaginiation = {
  setRiskLevel: (riskLevel: string | null) => void;
  setRuleName: (ruleName: string | null) => void;
  setRuleDescription: (ruleDescription: string | null) => void;
  setSourceData: (sourceData: string | null) => void;
  setUserAccountId: (userAccountId: string[] | null) => void;
} & BasePaginationFunction &
  GetRuleParam;

export function useGetRulePagination({
  page,
  size,
  sort,
  order,
  totalItems,
  riskLevel,
  ruleName,
  ruleDescription,
  sourceData,
  userAccountId,
}: GetRuleParam): GetRulePaginiation {
  const [valRiskLevel, setRiskLevel] = useState(riskLevel);
  const [valRuleName, setRuleName] = useState(ruleName);
  const [valRuleDescription, setRuleDescription] = useState(ruleDescription);
  const [valSourceData, setSourceData] = useState(sourceData);
  const [valUserAccountId, setUserAccountId] = useState(userAccountId);
  const basePagination = useBasePagination({
    page,
    size,
    sort,
    order,
    totalItems,
  });

  return {
    riskLevel: valRiskLevel,
    ruleName: valRuleName,
    ruleDescription: valRuleDescription,
    sourceData: valSourceData,
    userAccountId: valUserAccountId,
    setRiskLevel,
    setRuleName,
    setRuleDescription,
    setSourceData,
    setUserAccountId,

    ...basePagination,
  };
}
