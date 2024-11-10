import { userManagementServiceRawAxios } from "@/packages/user-management-service/src/shared/user-management.axios";
import { useMutation } from "@tanstack/react-query";
import type { AxiosResponse } from "axios";

export function getFdsTransactionMerchantExcelApi({
  startDate,
  endDate,
}: {
  startDate: Date;
  endDate: Date;
}): Promise<AxiosResponse> {
  // Change the return type to Blob
  return userManagementServiceRawAxios
    .get(`/fds-transaction/report/merchant/excel`, {
      params: {
        startDate: startDate,
        endDate: endDate,
      },
      responseType: "blob", // Ensure the response type is set to blob
    })
    .then((response) => {
      return response;
    }); // Return response.data directly
}

export function useGetFdsTransactionMerchantExcelQuery() {
  return useMutation({
    mutationFn: getFdsTransactionMerchantExcelApi,
    onSuccess: (data) => {
      const url = window.URL.createObjectURL(new Blob([data.data]));
      const link = document.createElement("a");
      link.href = url;
      link.setAttribute(
        "download",
        data.headers["content-disposition"].replace("attachment; filename=", "")
      );

      document.body.appendChild(link);

      link.click();

      link.parentNode?.removeChild(link);
    },
    onError: (error) => {
      console.error("Error downloading the Excel report:", error);
    },
  });
}
