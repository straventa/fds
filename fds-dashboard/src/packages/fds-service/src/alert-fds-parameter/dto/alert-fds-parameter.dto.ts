import { BasePageableResponse } from "@/packages/base/src/response/base.pageable.response";
export type AlertFdsParameterDto = {
  authSeqNo: string;
  cardNo: string;
  memberBankAcq: string;
  merchantName: string;
  mid: string;
  tid: string;
  rrn: string;
  issuer: string;
  authDate: string;
  authTime: string;
  authAmount: number;
  traceNo: string;
  messageTypeId: string;
  authSaleType: string;
  authIntnRspnCd: string;
  reasonContents: string;
  installmentCount: number;
  switchBrand: string;
  posEntryModeDetail: string;
  cardTypeCode: string;
  onusCode: string;
  eciValue: string;
  approvalCode: string;
  pgName: string;
  pgType: string;
  issuerMemberNo: string;
  businessType: string;
  channel: string;
  issuerCountry: string;
  parameterValues: string;
  actionType: string | null;
  fraudType: string | null;
  fraudPodType: string | null;
  fraudNote: string | null;
  confirmedBy: string | null;
  confirmedDateTime: Date | null;
  remindNote: string | null;
  remindDate: Date | null;
  assignedTo: string | null;
  assignedDateTime: Date | null;
  authDateTime: Date | null;
  uniqueId: string | null;

  activityId: string | null;
  id: string | null;
};

export type AlertFdsParameterPageableDto = {
  content: AlertFdsParameterDto[];
} & BasePageableResponse;