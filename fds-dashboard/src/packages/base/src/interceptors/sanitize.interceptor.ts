export function sanitizeInterceptor(config: any) {
  // const removeWhitespace = (value: any): any => {
  //   if (typeof value === "string") {
  //     return value.trim();
  //   }
  //   return value;
  // };

  // const processData = (data: any): any => {
  //   if (Array.isArray(data)) {
  //     return data.map((item) => processData(item));
  //   }

  //   if (data && typeof data === "object") {
  //     return Object.keys(data).reduce((acc, key) => {
  //       acc[key] = processData(data[key]);
  //       return acc;
  //     }, null);
  //   }

  //   return removeWhitespace(data);
  // };

  // if (config.data) {
  //   config.data = processData(config.data);
  // }

  // if (config.params) {
  //   config.params = processData(config.params);
  // }

  return config;
}
