import customLocalStorage from "@/packages/base/src/util/local-storage.util";
import type { LoginRequest } from "@/packages/user-management-service/src/auth/request/login.request";
import type { LoginResponse } from "@/packages/user-management-service/src/role";
import { userManagementServiceAxios } from "@/packages/user-management-service/src/shared/user-management.axios";
import { useMutation, type UseMutationResult } from "@tanstack/react-query";

export function postLoginApi({
  loginRequest,
}: {
  loginRequest: LoginRequest;
}): Promise<LoginResponse> {
  return userManagementServiceAxios.post("/auth/login", loginRequest);
}
export function usePostLoginQuery(): UseMutationResult<
  LoginResponse,
  Error,
  LoginRequest
> {
  return useMutation<LoginResponse, Error, LoginRequest>({
    mutationFn: (loginRequest) => postLoginApi({ loginRequest }),
    onSuccess: (data: LoginResponse) => {
      customLocalStorage.setLoginResponse(data);
      // localStorage.setItem("accessToken", data.accessToken);
    },
  });
}
