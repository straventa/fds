"use client";
import type { FraudType, RemindType } from "@/app/dashboard/alert/[slug]/page";
import BasePagination from "@/packages/base/src/components/BasePagination";
import DateRangeComponent from "@/packages/base/src/components/DateRangeComponent";
import SkeletonRows from "@/packages/base/src/components/SkeletonRows";
import { convertStringToTime } from "@/packages/base/src/util/string.util";
import { TimeUtil } from "@/packages/base/src/util/time.util";
import { useGetFdsParametersQuery } from "@/packages/user-management-service/src/fds-parameter/query/get-fds-parameters.query";
import { SearchableMultiSelect } from "@/packages/user-management-service/src/fds-transactions/components/BasicMultiSelect";
import { FdsTransactionsDto } from "@/packages/user-management-service/src/fds-transactions/dto/fds-transactions.dto";
import { useGetFdsTransactionsPagination } from "@/packages/user-management-service/src/fds-transactions/hooks/get-fds-transactions.pagination";
import { useCancelGetFdsTransactionReviewQuery } from "@/packages/user-management-service/src/fds-transactions/query/get-cancel-fds-transaction-review.query";
import { useGetFdsTransactionsQuery } from "@/packages/user-management-service/src/fds-transactions/query/get-fds-transactions.query";
import { usePostFdsTransactionCancelReviewBulkQuery } from "@/packages/user-management-service/src/fds-transactions/query/post-fds-transaction-cancel-review-bulk.query";
import { usePostFdsTransactionCancelReviewQuery } from "@/packages/user-management-service/src/fds-transactions/query/post-fds-transaction-cancel-review.query";
import { usePostFdsTransactionReviewBulkQuery } from "@/packages/user-management-service/src/fds-transactions/query/post-fds-transaction-review-bulk.query";
import { usePostFdsTransactionReviewQuery } from "@/packages/user-management-service/src/fds-transactions/query/post-fds-transaction-review.query";
import { usePutFdsTransactionsQuery } from "@/packages/user-management-service/src/fds-transactions/query/put-fds-transactions.query";
import { getRiskLevelColor } from "@/packages/user-management-service/src/rule/util/rule.util";
import { useGetUserAccountRulesByUserAccountIdQuery } from "@/packages/user-management-service/src/user-account-rule/query/get-user-account-rules-by-user-account-id.query";
import { useGetMeQuery } from "@/packages/user-management-service/src/user-account/query/get-me.query";
import {
  ActionIcon,
  Badge,
  Box,
  Button,
  Card,
  Checkbox,
  Flex,
  Modal,
  NumberFormatter,
  ScrollArea,
  Select,
  Space,
  Stack,
  Table,
  Text,
  Textarea,
  TextInput,
  UnstyledButton,
} from "@mantine/core";
import { DateTimePicker } from "@mantine/dates";
import { useDisclosure } from "@mantine/hooks";
import { notifications } from "@mantine/notifications";
import {
  IconArrowDown,
  IconArrowUp,
  IconEye,
  IconPencil,
} from "@tabler/icons-react";
import { debounce } from "lodash";
import { useRouter } from "next/navigation";
import { useCallback, useEffect, useState } from "react";
import { BarLoader } from "react-spinners";

export default function FdsTransactionsTable() {
  const router = useRouter();
  const getAlertFdsParameterPagination = useGetFdsTransactionsPagination({
    getFdsTransactionsParam: {
      page: 0,
      size: 5,
      startDate: new Date(new Date().setDate(new Date().getDate() - 30)),
      endDate: new Date(),
      sort: "rule.riskLevelNumber",
      order: "desc",
      tid: null,
      mid: null,
      actionType: null,
      ruleId: null,
      isConfirmed: false,
      isAll: null,
    },
  });
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  const cancelGetFdsTransactionReviewQuery =
    useCancelGetFdsTransactionReviewQuery();
  const [fdsTransactionsList, setFdsTransactionsList] = useState<
    FdsTransactionsDto[]
  >([]);

  const putFdsTransactionsQuery = usePutFdsTransactionsQuery();
  const [opened, { open, close }] = useDisclosure(false);

  const [localMid, setLocalMid] = useState(
    getAlertFdsParameterPagination.mid ?? ""
  );
  const [actionType, setActionType] = useState<string | null>("NORMAL");
  const [fraud, setFraud] = useState<FraudType>({
    fraudType: null,
    isFraudTypeOther: false,
    fraudPodType: null,
    isFraudPodTypeOther: false,
    fraudNote: null,
    fraudRemark: null,
  });
  const [remind, setRemind] = useState<RemindType>({
    remindDate: null,
    remindNote: null,
  });
  const [localTid, setLocalTid] = useState(
    getAlertFdsParameterPagination.tid ?? ""
  );
  const debouncedSearch = useCallback(
    debounce((value: string, type: "mid" | "tid") => {
      if (type === "mid") {
        getAlertFdsParameterPagination.setMid(value);
      } else {
        getAlertFdsParameterPagination.setTid(value);
      }
    }, 1000),
    []
  );

  const getAlertFdsParameterQuery = useGetFdsTransactionsQuery({
    getFdsTransactionsParam: {
      page: getAlertFdsParameterPagination.page,
      size: getAlertFdsParameterPagination.size,
      sort: getAlertFdsParameterPagination.sort,
      order: getAlertFdsParameterPagination.order,
      startDate: getAlertFdsParameterPagination.startDate,
      endDate: getAlertFdsParameterPagination.endDate,

      mid: getAlertFdsParameterPagination.mid,
      tid: getAlertFdsParameterPagination.tid,
      actionType: getAlertFdsParameterPagination.actionType,
      ruleId: getAlertFdsParameterPagination.ruleId,
      isConfirmed: getAlertFdsParameterPagination.isConfirmed,
      isAll: getAlertFdsParameterPagination.isAll,
    },
  });
  const postFdsTransactionReviewQuery = usePostFdsTransactionReviewQuery();
  const postFdsTransactionCancelReviewQuery =
    usePostFdsTransactionCancelReviewQuery();
  const postFdsTransactionCancelReviewBulkQuery =
    usePostFdsTransactionCancelReviewBulkQuery();
  const postFdsTransactionReviewBulkQuery =
    usePostFdsTransactionReviewBulkQuery();
  const getFdsParametersQuery = useGetFdsParametersQuery({
    getFdsParameterParam: {
      page: 0,
      size: 10000,
      sort: null,
      order: null,
    },
  });
  console.log("getFdsParametersQuery", getFdsParametersQuery.data);
  const getMeQuery = useGetMeQuery();
  const getUserAccountRulesByUserAccountIdQuery =
    useGetUserAccountRulesByUserAccountIdQuery({
      userAccountId: getMeQuery?.data?.userAccountId ?? "",
    });
  const toggleFdsTransactions = async (dto: FdsTransactionsDto) => {
    console.log("trigger toggle");
    try {
      const isSelected = fdsTransactionsList.some(
        (selectedDto) => selectedDto.fdsTransactionId === dto.fdsTransactionId
      );

      if (isSelected) {
        // If selected, cancel review first
        await postFdsTransactionCancelReviewQuery.mutateAsync({ data: dto });

        // Then update state
        setFdsTransactionsList((prevSelected) =>
          prevSelected.filter(
            (selectedDto) =>
              selectedDto.fdsTransactionId !== dto.fdsTransactionId
          )
        );
      } else {
        // If not selected, post review first
        await postFdsTransactionReviewQuery.mutateAsync({ data: dto });

        // Then update state
        setFdsTransactionsList((prevSelected) => [...prevSelected, dto]);
      }
    } catch (error: any) {
      // Handle any errors here
      console.error("Error toggling transaction:", error);
      notifications.show({
        title: "Failed",
        message: error.message,
        color: "red",
      });
    }
  };
  const toggleAllFdsTransactions = async (event) => {
    const { checked } = event.target;
    console.log("event.target", checked);

    try {
      if (checked) {
        const result = await postFdsTransactionReviewBulkQuery.mutateAsync({
          data: getAlertFdsParameterQuery.data.content,
        });

        setFdsTransactionsList(result); // Update state with successful additions
      } else {
        await postFdsTransactionCancelReviewBulkQuery.mutateAsync({
          data: fdsTransactionsList,
        });
        setFdsTransactionsList([]); // Clear the list after canceling
      }
    } catch (error) {
      console.error("Error toggling all transactions:", error);
      notifications.show({
        title: "Failed",
        message: error.message,
        color: "red",
      });
    }
  };

  console.log("fdsTransactionsList", fdsTransactionsList);
  // const isMobile = useMediaQuery("(max-width: 768px)");
  const columns = [
    {
      title: "Select",
      type: "component",
      component: (
        <Checkbox
          disabled={
            getAlertFdsParameterQuery.isLoading ||
            getAlertFdsParameterQuery.isFetching ||
            getAlertFdsParameterQuery.isRefetching ||
            postFdsTransactionReviewBulkQuery.isPending ||
            postFdsTransactionCancelReviewBulkQuery.isPending ||
            postFdsTransactionCancelReviewQuery.isPending ||
            postFdsTransactionReviewQuery.isPending
          }
          onChange={(e) => {
            toggleAllFdsTransactions(e);
          }}
        />
      ),
      key: null,
      left: 0,
    },
    {
      title: "MID",
      key: "mid",
      left: 80,
    },
    {
      title: "TID",
      key: "tid",
      left: 220,
    },
    {
      title: "Action",
      key: "escalation",
      left: 320,
    },

    {
      title: "Risk Levels",
      key: "rule.riskLevelNumber",
    },
    {
      title: "Transaction ID",
      key: "transactionId",
    },
    {
      title: "Auth Seq No",
      key: "authSeqNo",
    },

    {
      title: "Card No",
      key: "cardNo",
    },
    {
      title: "Member Bank Acquirer",
      key: "memberBankAcq",
    },
    {
      title: "Merchant Name",
      key: "merchantName",
    },

    {
      title: "RRN",
      key: "rrn",
    },
    {
      title: "Issuer",
      key: "issuer",
    },
    {
      title: "Auth Date",
      key: "authDate",
    },
    {
      title: "Auth Time",
      key: "authTime",
    },
    {
      title: "Auth Amount",
      key: "authAmount",
    },
    {
      title: "Trace No",
      key: "traceNo",
    },
    {
      title: "Message Type ID",
      key: "messageTypeId",
    },
    {
      title: "Auth Sale Type",
      key: "authSaleType",
    },
    {
      title: "Auth Internal Response Code",
      key: "authIntnRspnCd",
    },
    {
      title: "Reason Contents",
      key: "reasonContents",
    },
    {
      title: "Installment Count",
      key: "installmentCount",
    },
    {
      title: "Switch Brand",
      key: "switchBrand",
    },
    {
      title: "POS Entry Mode Detail",
      key: "posEntryModeDetail",
    },
    {
      title: "Card Type Code",
      key: "cardTypeCode",
    },
    {
      title: "Onus Code",
      key: "onusCode",
    },
    {
      title: "ECI Value",
      key: "eciValue",
    },
    {
      title: "Approval Code",
      key: "approvalCode",
    },
    {
      title: "PG Name",
      key: "pgName",
    },
    {
      title: "PG Type",
      key: "pgType",
    },
    {
      title: "Issuer Member No",
      key: "issuerMemberNo",
    },
    {
      title: "Business Type",
      key: "businessType",
    },
    {
      title: "Channel",
      key: "channel",
    },
    {
      title: "Issuer Country",
      key: "issuerCountry",
    },

    {
      title: "Action Type",
      key: "actionType",
    },
    {
      title: "Fraud Type",
      key: "fraudType",
    },
    {
      title: "Fraud Pod Type",
      key: "fraudPodType",
    },
    {
      title: "Fraud Remark",
      key: "fraudRemark",
    },
    {
      title: "Fraud Note",
      key: "fraudNote",
    },

    {
      title: "Remind Note",
      key: "remindNote",
    },
    {
      title: "Remind Date",
      key: "remindDate",
    },
    {
      title: "Confirmed By",
      key: "confirmedBy",
    },
    {
      title: "Confirmed Date Time",
      key: "confirmedDateTime",
    },
  ];
  const isDisabled =
    // For REMIND: disabled if remindDate is missing
    (actionType === "REMIND" && !remind.remindDate) ||
    // For WATCHLIST/FRAUD: disabled if any fraud fields are missing
    ((actionType === "WATCHLIST" || actionType === "FRAUD") &&
      (!fraud.fraudType || !fraud.fraudPodType || !fraud.fraudRemark));

  // For NORMAL/INVESTIGATION: always enabled (no need to add condition)

  useEffect(() => {
    getAlertFdsParameterQuery.refetch();
  }, [
    getAlertFdsParameterPagination.page,
    getAlertFdsParameterPagination.size,
    getAlertFdsParameterPagination.sort,
    getAlertFdsParameterPagination.order,
    getAlertFdsParameterPagination.startDate,
    getAlertFdsParameterPagination.endDate,
    getAlertFdsParameterPagination.mid,
    getAlertFdsParameterPagination.tid,
    getAlertFdsParameterPagination.actionType,
    getAlertFdsParameterPagination.ruleId,
    getAlertFdsParameterPagination.isConfirmed,
    getAlertFdsParameterPagination.isAll,
  ]);
  useEffect(() => {
    const timer = setTimeout(() => {
      getAlertFdsParameterQuery.refetch();
    }, 1000); // 1000 ms = 1 second

    // Cleanup the timeout when the component unmounts or dependencies change
    return () => clearTimeout(timer);
  }, []);
  useEffect(() => {
    if (getMeQuery.isSuccess) {
      getUserAccountRulesByUserAccountIdQuery.refetch();
    }
  }, [getMeQuery.isSuccess]);
  useEffect(() => {
    if (getUserAccountRulesByUserAccountIdQuery.isSuccess) {
      getAlertFdsParameterQuery.refetch();
    }
  }, [getAlertFdsParameterQuery.isSuccess]);
  useEffect(() => {
    if (putFdsTransactionsQuery.isSuccess) {
      close();
      getAlertFdsParameterQuery.refetch();
      notifications.show({
        title: "Success",
        message: "Alert updated successfully",
        color: "green",
      });
      setFdsTransactionsList([]);
    } else if (putFdsTransactionsQuery.isError) {
      notifications.show({
        title: "Failed",
        message: "Alert failed to update.",
        color: "red",
      });
    }
  }, [putFdsTransactionsQuery.isSuccess, putFdsTransactionsQuery.isError]);
  console.log(
    "getAlertFdsParameterPagination.ruleId",
    getAlertFdsParameterPagination.ruleId
  );
  return (
    <Box>
      <Modal
        opened={opened}
        onClose={close}
        title="Edit Alert"
        size={"xl"}
        radius={"md"}
      >
        <Box>
          <Stack justify="space-between">
            <Select
              onChange={(value) => {
                setActionType(value ?? null);

                setFraud({
                  fraudType: null,
                  isFraudTypeOther: false,
                  fraudPodType: null,
                  isFraudPodTypeOther: false,
                  fraudNote: null,
                  fraudRemark: null,
                });
                setRemind({
                  remindDate: null,
                  remindNote: null,
                });
              }}
              label="Select Action Type"
              placeholder="Select action type"
              required
              value={actionType}
              data={[
                { value: "NORMAL", label: "NORMAL" },
                { value: "INVESTIGATION", label: "INVESTIGATION" },
                { value: "REMIND", label: "REMIND" },
                { value: "WATCHLIST", label: "WATCHLIST" },
                { value: "FRAUD", label: "FRAUD" },
              ]}
            />
          </Stack>
          {(actionType === "FRAUD" || actionType === "WATCHLIST") && (
            <>
              <Select
                allowDeselect={false}
                label="Fraud Type"
                placeholder="Select fraud  type"
                required
                value={fraud?.fraudType}
                onChange={(value) => {
                  setFraud((prev) => {
                    return {
                      ...prev,
                      fraudType: value === "OTHERS" ? null : value ?? "",
                      isFraudTypeOther: value.toLowerCase().includes("other")
                        ? true
                        : false,
                    };
                  });
                }}
                error={
                  ((actionType === "FRAUD" || actionType === "WATCHLIST") &&
                    fraud.fraudType === null) ||
                  fraud.fraudType === ""
                    ? "Fraud Type cant be empty"
                    : null
                }
                disabled={
                  getFdsParametersQuery.isLoading ||
                  getFdsParametersQuery.isRefetching
                }
                data={getFdsParametersQuery.data.content
                  .filter((e) => e.fdsParameterCategory === "FRAUD_TYPE")
                  .map((e) => ({
                    value: e.fdsParameterValue,
                    label: e.fdsParameterKey,
                  }))}
              />
              {fraud.isFraudTypeOther ? (
                <TextInput
                  required
                  error={
                    ((actionType === "FRAUD" || actionType === "WATCHLIST") &&
                      fraud.fraudType === null) ||
                    fraud.fraudType === ""
                      ? "Fraud Type cant be empty"
                      : null
                  }
                  label={"Input your Fraud Type"}
                  onChange={(value) => {
                    setFraud({
                      ...fraud,
                      fraudType: value.target.value,
                    });
                  }}
                />
              ) : (
                <></>
              )}
              <Select
                allowDeselect={false}
                label="Fraud Pod Type"
                placeholder="Select fraud pod type"
                required
                error={
                  ((actionType === "FRAUD" || actionType === "WATCHLIST") &&
                    fraud.fraudPodType === null) ||
                  fraud.fraudPodType === ""
                    ? "Fraud Pod Type cant be empty"
                    : null
                }
                value={fraud?.fraudPodType}
                onChange={(value) => {
                  setFraud((prev) => {
                    return {
                      ...prev,
                      fraudPodType: value === "OTHERS" ? null : value ?? "",
                      isFraudPodTypeOther: value.toLowerCase().includes("other")
                        ? true
                        : false,
                    };
                  });
                }}
                disabled={
                  getFdsParametersQuery.isLoading ||
                  getFdsParametersQuery.isRefetching
                }
                data={getFdsParametersQuery.data.content
                  .filter((e) => e.fdsParameterCategory === "FRAUD_POD_TYPE")
                  .map((e) => ({
                    value: e.fdsParameterValue,
                    label: e.fdsParameterKey,
                  }))}
                // data={[
                //   { value: "MONITORING_TEAM", label: "Monitoring Team" },
                //   { value: "MEMBER_BANK", label: "Member Bank" },
                //   { value: "CNS", label: "CNS" },
                //   { value: "", label: "Others" },
                // ]}
              />
              {fraud.isFraudPodTypeOther ? (
                <TextInput
                  required
                  label={"Input your Pod Type"}
                  onChange={(value) => {
                    setFraud({
                      ...fraud,
                      fraudPodType: value.target.value,
                    });
                  }}
                  error={
                    ((actionType === "FRAUD" || actionType === "WATCHLIST") &&
                      fraud.fraudPodType === null) ||
                    fraud.fraudPodType === ""
                      ? "Fraud Pod Type cant be empty"
                      : null
                  }
                />
              ) : (
                <></>
              )}
              <Select
                allowDeselect={false}
                label="Remark"
                placeholder="Select remark"
                required
                value={fraud?.fraudRemark}
                onChange={(value) => {
                  setFraud((prev) => {
                    return {
                      ...prev,
                      fraudRemark: value ?? "",
                    };
                  });
                }}
                error={
                  ((actionType === "FRAUD" || actionType === "WATCHLIST") &&
                    fraud.fraudRemark === null) ||
                  fraud.fraudRemark === ""
                    ? "Remark Type cant be empty"
                    : null
                }
                disabled={
                  getFdsParametersQuery.isLoading ||
                  getFdsParametersQuery.isRefetching
                }
                data={getFdsParametersQuery.data.content
                  .filter((e) => e.fdsParameterCategory === "FRAUD_REMARK")
                  .map((e) => ({
                    value: e.fdsParameterValue,
                    label: e.fdsParameterKey,
                  }))}
              />
              <Textarea
                label="Fraud Note"
                placeholder="Fraud Note"
                value={fraud?.fraudNote ?? ""}
                onChange={(event) => {
                  const value = event.target.value;
                  setFraud((prev) => {
                    return {
                      ...prev,
                      fraudNote: value,
                    };
                  });
                }}
              />
            </>
          )}
          {actionType === "REMIND" && (
            <>
              <DateTimePicker
                label="Remind Date Time"
                placeholder="Select date time"
                value={remind?.remindDate ?? null}
                onChange={(value) => {
                  setRemind((prev) => {
                    return {
                      ...prev,
                      remindDate: value,
                    };
                  });
                }}
                error={
                  remind.remindDate === null ? "Select valid date range." : null
                }
              />

              <Textarea
                label="Remind Note"
                placeholder="Remind Note"
                value={remind?.remindNote ?? ""}
                onChange={(event) => {
                  const value = event.target.value;
                  setRemind((prev) => {
                    return {
                      ...prev,
                      remindNote: value,
                    };
                  });
                }}
              />
            </>
          )}
          <Button
            loading={putFdsTransactionsQuery.isPending}
            onClick={() => {
              const transformedRequest = fdsTransactionsList.map((e) => ({
                ...e,
                actionType: actionType ?? "",
                fraudType: fraud?.fraudType ?? "",
                fraudPodType: fraud?.fraudPodType ?? "",
                fraudNote: fraud?.fraudNote ?? "",
                remindDate: remind?.remindDate,
                remindNote: remind?.remindNote ?? "",
              }));
              putFdsTransactionsQuery.mutate({
                data: transformedRequest,
              });
            }}
            color={"#4AD2F5"}
            my={8}
            disabled={isDisabled}
          >
            Save
          </Button>
        </Box>
      </Modal>
      <DateRangeComponent
        startDate={getAlertFdsParameterPagination.startDate}
        endDate={getAlertFdsParameterPagination.endDate}
        setDate={getAlertFdsParameterPagination.setValue}
      />
      <Box my="md">
        <Flex gap={12} align={"center"} wrap={"wrap"}>
          <Text fz={16} fw={400}>
            Filter by
          </Text>
          <TextInput
            placeholder="Search by MID"
            value={localMid}
            onChange={(e) => {
              const value = e.currentTarget.value;
              setLocalMid(value);
              debouncedSearch(value, "mid");
            }}
          />
          <TextInput
            placeholder="Search by TID"
            value={localTid}
            onChange={(e) => {
              const value = e.currentTarget.value;
              setLocalTid(value);
              debouncedSearch(value, "tid");
            }}
          />
          {/* <MultiSelect
            placeholder="Action Type"
            data={[
              { value: "REMIND", label: "REMIND" },
              { value: "NORMAL", label: "NORMAL" },
              { value: "WATCHLIST", label: "WATCHLIST" },
              { value: "INVESTIGATION", label: "INVESTIGATION" },
              { value: "FRAUD", label: "FRAUD" },
            ]}
            value={getAlertFdsParameterPagination.actionType}
            onChange={(value) => {
              if (value === null) {
                getAlertFdsParameterPagination.setActionType(null);
              } else {
                getAlertFdsParameterPagination.setActionType(value);
              }
            }}
          /> */}
          <Select
            placeholder="Confirmed"
            data={[
              { value: "CONFIRMED", label: "CONFIRMED" },
              { value: "NOT CONFIRMED", label: "NOT CONFIRMED" },
            ]}
            value={
              getAlertFdsParameterPagination.isConfirmed === true
                ? "CONFIRMED"
                : getAlertFdsParameterPagination.isConfirmed === false
                ? "NOT CONFIRMED"
                : null
            }
            onChange={(_value, option) => {
              if (option === null) {
                getAlertFdsParameterPagination.setIsConfirmed(null);
              } else if (option.value === "CONFIRMED") {
                getAlertFdsParameterPagination.setIsConfirmed(true);
                getAlertFdsParameterPagination.setActionType([
                  "NORMAL",
                  "FRAUD",
                  "WATCHLIST",
                ]);
              } else if (option.value === "NOT CONFIRMED") {
                getAlertFdsParameterPagination.setIsConfirmed(false);
                getAlertFdsParameterPagination.setActionType([
                  "INVESTIGATION",
                  "REMIND",
                ]);
              } else {
                getAlertFdsParameterPagination.setIsConfirmed(null);
              }
            }}
          />
          <SearchableMultiSelect
            value={getAlertFdsParameterPagination.ruleId}
            onChange={getAlertFdsParameterPagination.setRuleId}
            data={[
              {
                group: "High Risk",
                items:
                  getUserAccountRulesByUserAccountIdQuery.data === null ||
                  getUserAccountRulesByUserAccountIdQuery.data === undefined
                    ? []
                    : getUserAccountRulesByUserAccountIdQuery.data
                        .filter((e) => e.rule.riskLevelNumber === 2)
                        .map((e) => ({
                          // Added parentheses here
                          value: e.rule.ruleId,
                          key: e.rule.ruleName,
                          isDisabled:
                            e.isActive &&
                            !(getMeQuery.data.role.at(0).roleName !== "USER"),
                        })),
              },
              {
                group: "Medium Risk",
                items:
                  getUserAccountRulesByUserAccountIdQuery.data === null ||
                  getUserAccountRulesByUserAccountIdQuery.data === undefined
                    ? []
                    : getUserAccountRulesByUserAccountIdQuery.data
                        .filter((e) => e.rule.riskLevelNumber === 1)
                        .map((e) => ({
                          // Added parentheses here
                          value: e.rule.ruleId,
                          key: e.rule.ruleName,
                          isDisabled:
                            e.isActive &&
                            !(getMeQuery.data.role.at(0).roleName !== "USER"),
                        })),
              },
              {
                group: "Low Risk",
                items:
                  getUserAccountRulesByUserAccountIdQuery.data === null ||
                  getUserAccountRulesByUserAccountIdQuery.data === undefined
                    ? []
                    : getUserAccountRulesByUserAccountIdQuery.data
                        .filter((e) => e.rule.riskLevelNumber === 0)
                        .map((e) => ({
                          // Added parentheses here
                          value: e.rule.ruleId,
                          key: e.rule.ruleName,
                          isDisabled:
                            e.isActive &&
                            !(getMeQuery.data.role.at(0).roleName !== "USER"),
                        })),
              },
            ]}
          />
          {/* <MultiSelect
            placeholder="Search by Risk Level"
            data={[
              { group: "Frontend", items: ["React", "Angular"] },
              { group: "Backend", items: ["Node", "Django"] },
              { group: "Backend", items: ["Spring"] },
            ]}
            component={}
            // data={[
            //   {
            //     label: "High Risk",
            //     value: "High Risk",
            //   },
            //   {
            //     label: "Medium Risk",
            //     value: "Medium Risk",
            //   },
            //   {
            //     label: "Low Risk",
            //     value: "Low Risk",
            //   },
            // ]}
          /> */}
        </Flex>

        <Space h={16} />
        <Flex align={"center"} justify={"flex-end"}>
          <Button
            onClick={open}
            color={"#4AD2F5"}
            leftSection={<IconPencil size={24} />}
            disabled={fdsTransactionsList.length === 0}
          >
            Action
          </Button>
        </Flex>
      </Box>
      <Card shadow="xs" radius="md" p={0}>
        <Box mih={"800px"}>
          <BarLoader
            width={"100%"}
            loading={
              getAlertFdsParameterQuery.isLoading ||
              getAlertFdsParameterQuery.isFetching ||
              getAlertFdsParameterQuery.isRefetching ||
              postFdsTransactionReviewBulkQuery.isPending ||
              postFdsTransactionCancelReviewBulkQuery.isPending
            }
            color="#4AD2F5"
          />
          <ScrollArea h={"800px"}>
            <Table
              striped
              highlightOnHover
              horizontalSpacing={"xl"}
              verticalSpacing={"xl"}
            >
              <Table.Thead pos={"sticky"} bg={"#F6F6F6"}>
                <Table.Tr>
                  {columns.map((e, i) => {
                    return (
                      <Table.Th
                        key={i}
                        c={"#64748B"}
                        pos={e.left !== undefined ? "sticky" : "static"}
                        left={e.left !== undefined ? e.left : "auto"}
                        bg={e.left !== undefined ? "#F6F6F6" : "transparent"}
                      >
                        <UnstyledButton
                          disabled={
                            getAlertFdsParameterQuery.isLoading ||
                            getAlertFdsParameterQuery.isFetching ||
                            getAlertFdsParameterQuery.isRefetching ||
                            postFdsTransactionReviewBulkQuery.isPending ||
                            postFdsTransactionCancelReviewBulkQuery.isPending
                          }
                          onClick={() => {
                            if (e.key !== null) {
                              getAlertFdsParameterPagination.setSort(e.key);
                              getAlertFdsParameterPagination.setOrder(
                                getAlertFdsParameterPagination.sort === e.key
                                  ? getAlertFdsParameterPagination.order ===
                                    "asc"
                                    ? "desc"
                                    : "asc"
                                  : "asc"
                              );
                            }
                          }}
                        >
                          <Flex>
                            {e.type === "component" ? (
                              <>{e.component}</>
                            ) : (
                              <Text>{e.title}</Text>
                            )}
                            <Space w={4} />
                            {e.key ===
                            null ? null : getAlertFdsParameterPagination.sort ===
                              e.key ? (
                              getAlertFdsParameterPagination.order === "asc" ? (
                                <IconArrowUp />
                              ) : (
                                <IconArrowDown />
                              )
                            ) : (
                              <></>
                            )}
                          </Flex>
                        </UnstyledButton>
                      </Table.Th>
                    );
                  })}
                </Table.Tr>
              </Table.Thead>
              <Table.Tbody>
                {getAlertFdsParameterQuery.data !== undefined ? (
                  getAlertFdsParameterQuery.data.content?.map((e, i) => {
                    return (
                      <Table.Tr key={i}>
                        <Table.Td
                          px={24}
                          py={12}
                          left={0}
                          pos={"sticky"}
                          bg={"#F6F6F6"}
                        >
                          <Checkbox
                            disabled={
                              getAlertFdsParameterQuery.isLoading ||
                              getAlertFdsParameterQuery.isFetching ||
                              getAlertFdsParameterQuery.isRefetching ||
                              postFdsTransactionReviewBulkQuery.isPending ||
                              postFdsTransactionCancelReviewBulkQuery.isPending ||
                              postFdsTransactionCancelReviewQuery.isPending ||
                              postFdsTransactionReviewQuery.isPending
                            }
                            checked={fdsTransactionsList.some(
                              (selectedDto) =>
                                selectedDto.fdsTransactionId ===
                                e.fdsTransactionId
                            )}
                            onChange={() => {
                              toggleFdsTransactions(e);
                            }}
                          />
                        </Table.Td>
                        <Table.Td
                          px={24}
                          py={12}
                          left={80}
                          pos={"sticky"}
                          bg={"#F6F6F6"}
                        >
                          {e.mid}
                        </Table.Td>
                        <Table.Td
                          px={24}
                          py={12}
                          left={220}
                          pos={"sticky"}
                          bg={"#F6F6F6"}
                        >
                          {e.tid}
                        </Table.Td>
                        <Table.Td
                          px={24}
                          py={12}
                          left={320}
                          pos={"sticky"}
                          bg={"#F6F6F6"}
                        >
                          <ActionIcon
                            variant="transparent"
                            size="xl"
                            aria-label="Gradient action icon"
                            color={"#4AD2F5"}
                            // gradient={{ from: "blue", to: "cyan", deg: 90 }}
                            disabled={fdsTransactionsList.length > 0}
                            onClick={async () => {
                              try {
                                console.log("ok");
                                await postFdsTransactionReviewQuery.mutateAsync(
                                  { data: e }
                                );
                                console.log("ok2");

                                router.push(
                                  `/dashboard/alert/${e.fdsTransactionId}`
                                );
                              } catch (error) {
                                console.log("ok3");

                                notifications.show({
                                  title: "Failed",
                                  message: error?.message ?? "",
                                  color: "red",
                                });
                              }
                            }}
                          >
                            <IconEye />
                          </ActionIcon>
                        </Table.Td>
                        <Table.Td px={24} py={12} w={"200px"} miw={"500px"}>
                          {e.rule.map((e, i) => {
                            return (
                              <Badge
                                key={i}
                                size="md"
                                color={getRiskLevelColor(e.riskLevel)}
                              >
                                {e.riskLevel} {" - "}
                                {e.ruleName}
                              </Badge>
                            );
                          })}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          {e.fdsTransactionId}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          {e.authSeqNo}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          {e.cardNo}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          {e.memberBankAcq}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          {e.merchantName}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          {e.rrn}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          {e.issuer}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          {e.authDate}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          {convertStringToTime(e.authTime)}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          <NumberFormatter
                            value={e.authAmount ?? "0"}
                            thousandSeparator=","
                            decimalSeparator="."
                            decimalScale={2}
                          />
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          {e.traceNo}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          {e.messageTypeId}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          {e.authSaleType}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          {e.authIntnRspnCd}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          {e.reasonContents}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          {e.installmentCount}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          {e.switchBrand}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          {e.posEntryModeDetail}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          {e.cardTypeCode}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          {e.onusCode}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          {e.eciValue}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          {e.approvalCode}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          {e.pgName}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          {e.pgType}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          {e.issuerMemberNo}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          {e.businessType}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          {e.channel}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          {e.issuerCountry}
                        </Table.Td>
                        {/* actionType: string; fraudType: string; fraudPodType:
                        string; fraudNote: string; confirmedBy: string;
                        confirmedDateTime: Date; remindNote: string; remindDate:
                        Date; assignedTo: string; assignedDateTime: Date; */}
                        <Table.Td px={24} py={12}>
                          {e.actionType}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          {e.fraudType}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          {e.fraudPodType}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          {e.fraudRemark}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          {e.fraudNote}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          {e.remindNote}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          {TimeUtil.formatTime(e.remindDate)}
                        </Table.Td>
                        {/* <Table.Td px={24} py={12}>
                          {e.assignedUserAccount?.username ?? ""}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          {TimeUtil.formatTime(e.assignedDateTime)}
                        </Table.Td> */}
                        <Table.Td px={24} py={12}>
                          {e.confirmedUserAccount?.username}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          {TimeUtil.formatTime(e.confirmedDateTime)}
                        </Table.Td>
                      </Table.Tr>
                    );
                  })
                ) : (
                  <SkeletonRows numOfRows={10} numOfColumns={columns.length} />
                )}
              </Table.Tbody>
            </Table>
          </ScrollArea>
        </Box>
        <Box p={24}>
          <BasePagination
            pageable={getAlertFdsParameterPagination}
            query={getAlertFdsParameterQuery}
            additionalLoading={
              getAlertFdsParameterQuery.isLoading ||
              getAlertFdsParameterQuery.isFetching ||
              getAlertFdsParameterQuery.isRefetching ||
              postFdsTransactionReviewBulkQuery.isPending ||
              postFdsTransactionCancelReviewBulkQuery.isPending
            }
          />
        </Box>
      </Card>
    </Box>
  );
}
