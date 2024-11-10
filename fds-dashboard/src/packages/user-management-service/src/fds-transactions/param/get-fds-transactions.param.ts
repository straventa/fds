import type { BaseDateParam } from "@/packages/base/src/param/base-date.param";
import type { BaseParam } from "@/packages/base/src/param/base.param";

export type GetFdsTransactionsParam = {
  mid: string | null;
  tid: string | null;
  actionType: string[] | null;
  ruleId: string[] | null;
  isConfirmed: boolean | null;
  isAll: boolean | null;
} & BaseParam &
  BaseDateParam;
