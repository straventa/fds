// interceptors/csrf.interceptor.ts
const checkCsrfToken = () => {
  const cookies = document.cookie.split(";").reduce((acc, cookie) => {
    const [name, value] = cookie.trim().split("=");
    acc[name] = value;
    return acc;
  }, {} as { [key: string]: string });

  return cookies["XSRF-TOKEN"];
};

export const csrfInterceptor = (config: any) => {
  const csrfToken = checkCsrfToken();
  if (csrfToken) {
    config.headers["X-XSRF-TOKEN"] = decodeURIComponent(csrfToken);
  } else {
    console.warn("No CSRF token found in cookies");
  }
  return config;
};
