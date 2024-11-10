import { BaseDateParam } from "@/packages/base/src/param/base-date.param";
import { BaseParam } from "@/packages/base/src/param/base.param";

export type GetAlertFdsParameterParam = {
  mid: string | null;
  tid: string | null;
  assignedTo: string | null;
  actionType: string | null;
} & BaseParam &
  BaseDateParam;
