function toHyphenCase(str: string) {
  return str.replace(/([a-z])([A-Z])/g, "$1-$2").toLowerCase();
}

function localDateToISOString(date: Date): string {
  const offset = date.getTimezoneOffset() * 60000;
  const localDate = new Date(date.getTime() - offset);
  return localDate.toISOString();
}

function transformKeysToHyphenCase(obj: { [key: string]: any }) {
  if (obj && typeof obj === "object" && !Array.isArray(obj)) {
    const newObj: { [key: string]: any } = {};
    for (const key in obj) {
      if (obj.hasOwnProperty(key)) {
        newObj[toHyphenCase(key)] = obj[key];
      }
    }
    return newObj;
  }
  return obj;
}

function transformQueryParams(params: { [key: string]: any }): {
  [key: string]: any;
} {
  const transformedParams: { [key: string]: any } = {};
  for (const key in params) {
    if (params[key] instanceof Date) {
      transformedParams[key] = localDateToISOString(params[key]);
    } else if (typeof params[key] === "object" && params[key] !== null) {
      transformedParams[key] = transformQueryParams(params[key]);
    } else {
      transformedParams[key] = params[key];
    }
  }
  return transformedParams;
}

export const queryParamsInterceptor = (config: any) => {
  if (config.params) {
    config.params = transformKeysToHyphenCase(config.params);
    config.params = transformQueryParams(config.params);
  }
  return config;
};
