// interceptors/sort.interceptor.ts
export const sortInterceptor = (config: any) => {
  if (config.params?.sort && config.params?.order) {
    config.params.sort = `${config.params.sort},${config.params.order}`;
    delete config.params.order;
  }
  return config;
};
