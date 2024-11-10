import { AxiosResponse } from "axios";

export function responseInterceptor(response: AxiosResponse<any, any>) {
  return response.data;
}
