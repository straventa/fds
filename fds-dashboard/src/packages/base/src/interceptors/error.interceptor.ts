import { AxiosError } from "axios";

export type ErrorResponse = {
  timestamp: Date;
  status: number;
  error: string;
  trace: string;
  message: string;
  errors: Error[];
  path: string;
};

export type Error = {
  codes: string[];
  arguments: Argument[];
  defaultMessage: string;
  objectName: string;
  field: string;
  rejectedValue: string;
  bindingFailure: boolean;
  code: string;
};

export type Argument = {
  codes: string[];
  arguments: null;
  defaultMessage: string;
  code: string;
};

export type RejectedValue = {};

// Transform Axios error to custom ERPErrorResponse
export function errorInterceptor(error: AxiosError): Promise<ErrorResponse> {
  // Directly cast the error response to ERPErrorResponse format
  const erpErrorResponse: ErrorResponse = error.response?.data as ErrorResponse;

  // Reject the promise with the transformed ERPErrorResponse
  return Promise.reject(erpErrorResponse);
}

// export function errorInterceptor(error: AxiosError): Promise<ERPErrorResponse> {
//   return Promise.reject(error);
// }
