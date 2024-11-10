import type { BaseResponse } from "@/packages/base/src/response/base.response";

export type RoleResponse = {
  roleId: string;
  roleName: string;
  roleCode: string;
  roleDescription: string;
} & BaseResponse;
