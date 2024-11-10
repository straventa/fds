export function authRequestInterceptor(config: any) {
  // No need to manually set Authorization header - cookies will be sent automatically
  config.headers!.Accept = "application/json";
  // Important for cookies
  config.withCredentials = true;
  return config;
}
