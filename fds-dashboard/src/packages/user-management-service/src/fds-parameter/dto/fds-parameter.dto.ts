import type { BasePageableDto } from "@/packages/base/src/dto/base-pageable.dto";

export type FdsParameterDto = {
  fdsParameterId: string;
  fdsParameterKey: string;
  fdsParameterValue: string;
  fdsParameterCategory: string;
};

export type FdsParameterPageableDto = {
  content: FdsParameterDto[];
} & BasePageableDto;
