import type { FdsTransactionsPageableDto } from "@/packages/user-management-service/src/fds-transactions/dto/fds-transactions.dto";
import type { GetFdsTransactionsParam } from "@/packages/user-management-service/src/fds-transactions/param/get-fds-transactions.param";
import { userManagementServiceAxios } from "@/packages/user-management-service/src/shared/user-management.axios";
import { type UseQueryResult, useQuery } from "@tanstack/react-query";

export function getFdsTransactionsApi({
  getFdsTransactionsParam,
}: {
  getFdsTransactionsParam: GetFdsTransactionsParam;
}): Promise<FdsTransactionsPageableDto> {
  console.log("called");

  return userManagementServiceAxios.post("/v2/fds-transaction", {
    ...getFdsTransactionsParam,
  });
}
// export function usePostChangePasswordQuery(): UseMutationResult<
//   ChangePasswordResponse,
//   ErrorResponse,
//   ChangePasswordRequest
// > {
//   return useMutation<
//     ChangePasswordResponse,
//     ErrorResponse,
//     ChangePasswordRequest
//   >({
//     mutationFn: (changePasswordRequest) =>
//       postChangePasswordApi({ changePasswordRequest }),
//   });
// }

export function useGetFdsTransactionsQuery({
  getFdsTransactionsParam,
}: {
  getFdsTransactionsParam: GetFdsTransactionsParam;
}): UseQueryResult<FdsTransactionsPageableDto, Error> {
  return useQuery<FdsTransactionsPageableDto, Error>({
    queryKey: ["fds-transactions", getFdsTransactionsParam],
    queryFn: () => getFdsTransactionsApi({ getFdsTransactionsParam }),
    staleTime: 0, // Force immediate fetch
  });
}
