import {
  useBaseDate,
  type BaseDateFunction,
} from "@/packages/base/src/hooks/base-date.pagination.hooks";
import {
  useBasePagination,
  type BasePaginationFunction,
} from "@/packages/base/src/hooks/base.pagination.hooks";
import type { GetAlertFdsParameterParam } from "@/packages/fds-service/src/alert-fds-parameter/param/get-alert-fds-parameter.param";
import { useState } from "react";

export type GetAlertFdsParameterPagination = {
  setMid: (mid: string) => void;
  setTid: (tid: string) => void;
  setAssignedTo: (assignedTo: string | null) => void;
  setActionType: (actionType: string | null) => void;
} & BaseDateFunction &
  BasePaginationFunction &
  GetAlertFdsParameterParam;

export function useGetAlertFdsParameterPagination({
  page,
  size,
  sort,
  order,
  startDate,
  endDate,
  totalItems,
  mid,
  tid,
  assignedTo,
  actionType,
}: GetAlertFdsParameterParam): GetAlertFdsParameterPagination {
  const [valMid, setMid] = useState<string | null>(mid);
  const [valTid, setTid] = useState<string | null>(tid);
  const [valAssignedTo, setAssignedTo] = useState<string | null>(assignedTo);
  const [valActionType, setActionType] = useState<string | null>(actionType);
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
    mid: valMid,
    tid: valTid,
    assignedTo: valAssignedTo,
    actionType: valActionType,
    setMid,
    setTid,
    setAssignedTo,
    setActionType,

    ...baseDate,
    ...basePagination,
  };
}
