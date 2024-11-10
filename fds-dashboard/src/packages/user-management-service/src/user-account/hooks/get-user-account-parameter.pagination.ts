import {
  useBasePagination,
  type BasePaginationFunction,
} from "@/packages/base/src/hooks/base.pagination.hooks";
import { useBaseDate } from "@/packages/base/src/hooks/base-date.pagination.hooks";
import { useState } from "react";
import type { GetUserAccountParam } from "@/packages/user-management-service/src/user-account/param/get-user-account.param";
export type GetUserAccountParameterPagination = {
  setUsername: (username: string | null) => void;
  setEmail: (email: string | null) => void;
  setRuleName: (ruleName: string | null) => void;
  setRoleName: (roleName: string | null) => void;
} & BasePaginationFunction &
  GetUserAccountParam;

export function useGetUserAccountParameterPagination({
  page,
  size,
  sort,
  order,
  startDate,
  endDate,
  totalItems,
  username,
  email,
  ruleName,
  roleName,
}: GetUserAccountParam): GetUserAccountParameterPagination {
  const [valUsername, setUsername] = useState(username);
  const [valEmail, setEmail] = useState(email);
  const [valRuleName, setRuleName] = useState(ruleName);
  const [valRoleName, setRoleName] = useState(roleName);
  const basePagination = useBasePagination({
    page,
    size,
    sort,
    order,
    totalItems,
  });
  const baseDate = useBaseDate({
    startDate,
    endDate,
  });

  return {
    username: valUsername,
    email: valEmail,
    ruleName: valRuleName,
    roleName: valRoleName,
    setUsername,
    setEmail,
    setRuleName,
    setRoleName,

    ...baseDate,
    ...basePagination,
  };
}
