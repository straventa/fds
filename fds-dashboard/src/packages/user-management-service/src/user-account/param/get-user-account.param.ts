import { BaseParam } from "@/packages/base/src/param/base.param";
import { BaseDateParam } from "@/packages/base/src/param/base-date.param";
export type GetUserAccountParam = {
  username: string | null;
  email: string | null;
  ruleName: string | null;
  roleName: string | null;
} & BaseParam &
  BaseDateParam;
