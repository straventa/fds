import type { LoginResponse } from "@/packages/user-management-service/src/role";
import ls from "localstorage-slim";
ls.config.encrypt = true;

class CustomLocalStorage {
  getItem(name: string): string | null | Promise<string | null> {
    return ls.get(name);
  }
  setItem(name: string, value: string): void | Promise<void> {
    ls.set(name, value);
  }
  removeItem(name: string): void | Promise<void> {
    ls.remove(name);
  }
  setAccessToken(accessToken: string) {
    ls.set("accessToken", accessToken);
  }
  getAccessToken() {
    return ls.get("accessToken");
  }

  setLoginResponse(loginResponse: LoginResponse) {
    const stringf = JSON.stringify(loginResponse);
    ls.set("loginResponse", stringf);
  }

  getLoginResponse(): LoginResponse | null {
    const get = ls.get("loginResponse") as string;
    const res = JSON.parse(get);
    return res;
  }

  clearAllData() {
    ls.clear();
  }
}

const customLocalStorage = new CustomLocalStorage();
export default customLocalStorage;
