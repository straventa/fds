import {
  useBaseDate,
  type BaseDateFunction,
} from "@/packages/base/src/hooks/base-date.pagination.hooks";
import {
  useBasePagination,
  type BasePaginationFunction,
} from "@/packages/base/src/hooks/base.pagination.hooks";
import type { GetFdsTransactionsParam } from "@/packages/user-management-service/src/fds-transactions/param/get-fds-transactions.param";
import { useState } from "react";

export type GetFdsTransactionPagination = {
  setMid: (mid: string) => void;
  setTid: (tid: string) => void;
  setActionType: (actionType: string[] | null) => void;
  setRuleId: (ruleId: string[] | null) => void;
  setIsConfirmed: (isConfirmed: boolean | null) => void;
  setIsAll: (isAll: boolean | null) => void;
} & BaseDateFunction &
  BasePaginationFunction &
  GetFdsTransactionsParam;

export function useGetFdsTransactionsPagination({
  getFdsTransactionsParam,
}: {
  getFdsTransactionsParam: GetFdsTransactionsParam;
}): GetFdsTransactionPagination {
  const [valMid, setMid] = useState<string | null>(getFdsTransactionsParam.mid);
  const [valTid, setTid] = useState<string | null>(getFdsTransactionsParam.tid);

  const [valActionType, setActionType] = useState<string[] | null>(
    getFdsTransactionsParam.actionType
  );
  const [valRuleId, setRuleId] = useState<string[] | null>(
    getFdsTransactionsParam.ruleId
  );
  const [valIsConfirmed, setIsConfirmed] = useState<boolean | null>(
    getFdsTransactionsParam.isConfirmed
  );
  const [valIsAll, setIsAll] = useState<boolean | null>(
    getFdsTransactionsParam.isAll
  );

  const basePagination = useBasePagination({
    page: getFdsTransactionsParam.page,
    size: getFdsTransactionsParam.size,
    sort: getFdsTransactionsParam.sort,
    order: getFdsTransactionsParam.order,
    totalItems: getFdsTransactionsParam.totalItems,
  });
  const baseDate = useBaseDate({
    startDate: getFdsTransactionsParam.startDate,
    endDate: getFdsTransactionsParam.endDate,
  });

  return {
    mid: valMid,
    tid: valTid,
    actionType: valActionType,
    ruleId: valRuleId,
    isConfirmed: valIsConfirmed,
    isAll: valIsAll,
    setMid,
    setTid,
    setActionType,
    setRuleId,
    setIsConfirmed,
    setIsAll,

    ...baseDate,
    ...basePagination,
  };
}
