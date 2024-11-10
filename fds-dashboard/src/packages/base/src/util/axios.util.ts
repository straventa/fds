import { authRequestInterceptor } from "@/packages/base/src/interceptors/auth.interceptor";
import { csrfInterceptor } from "@/packages/base/src/interceptors/csrf.interceptor";
import { errorInterceptor } from "@/packages/base/src/interceptors/error.interceptor";
import { queryParamsInterceptor } from "@/packages/base/src/interceptors/query-params.interceptor";
import { responseInterceptor } from "@/packages/base/src/interceptors/response.interceptor";
import { sanitizeInterceptor } from "@/packages/base/src/interceptors/sanitize.interceptor";
import { sortInterceptor } from "@/packages/base/src/interceptors/sort.interceptor";
import axios from "axios";

export function createAxiosInstance(baseUrl: string, withCredentials = false) {
  const instance = axios.create({
    baseURL: baseUrl,
    withCredentials,
    ...(withCredentials && {
      xsrfCookieName: "XSRF-TOKEN",
      xsrfHeaderName: "X-XSRF-TOKEN",
    }),
  });

  instance.interceptors.request.use(csrfInterceptor);
  instance.interceptors.request.use(queryParamsInterceptor);
  instance.interceptors.request.use(authRequestInterceptor);
  instance.interceptors.request.use(sortInterceptor);
  instance.interceptors.request.use(sanitizeInterceptor);

  if (withCredentials) {
    instance.interceptors.response.use(responseInterceptor, errorInterceptor);
  }

  return instance;
}
