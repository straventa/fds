"use client";
import FieldAndValue from "@/packages/base/src/components/FieldAndValue";
import { TimeUtil } from "@/packages/base/src/util/time.util";
import { useGetCddQuery } from "@/packages/fds-service/src/cdd/query/get-cdd.query";
import { useGetMerchantQuery } from "@/packages/fds-service/src/merchant/query/get-merchant.query";
import { useGetOwnerQuery } from "@/packages/fds-service/src/owner/query/get-owner.query";
import { useGetFdsParametersQuery } from "@/packages/user-management-service/src/fds-parameter/query/get-fds-parameters.query";
import { useGetFdsTransactionNextQuery } from "@/packages/user-management-service/src/fds-transactions/query/get-fds-transaction-next.query";
import { useGetFdsTransactionQuery } from "@/packages/user-management-service/src/fds-transactions/query/get-fds-transaction.query";
import { usePutFdsTransactionQuery } from "@/packages/user-management-service/src/fds-transactions/query/put-fds-transaction.query";
import {
  Badge,
  Box,
  Button,
  Card,
  Flex,
  Group,
  Modal,
  Select,
  SimpleGrid,
  Space,
  Stack,
  Text,
  Textarea,
  TextInput,
  UnstyledButton,
} from "@mantine/core";
import { DateTimePicker } from "@mantine/dates";
import { useDisclosure } from "@mantine/hooks";
import { notifications } from "@mantine/notifications";
import {
  IconArrowRightBar,
  IconChevronLeft,
  IconPencil,
} from "@tabler/icons-react";
import { useParams, useRouter } from "next/navigation";
import { useEffect, useState } from "react";
export type FraudType = {
  fraudType: string | null;
  isFraudTypeOther: boolean | null;
  fraudPodType: string | null;
  isFraudPodTypeOther: boolean | null;

  fraudNote: string | null;
  fraudRemark;
};
export type RemindType = {
  remindDate: Date | null;
  remindNote: string | null;
};
export default function Page() {
  const transactionId = useParams().slug as string;
  const router = useRouter();
  const [opened, { open, close }] = useDisclosure(false);
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
  // const [fraudError, setFraudError] = useState<FraudType>({
  //   fraudType: null,
  //   fraudPodType: null,
  //   fraudNote: null,
  //   fraudRemark: null,
  // });
  // const [remindError, setRemindError] = useState<RemindType>({
  //   remindDate: null,
  //   remindNote: null,
  // });
  // const getFdsTransactionReviewQuery = useGetFdsTransactionReviewQuery({
  //   fdsTransactionId: transactionId,
  // });
  const getSingleAlertFdsParameter = useGetFdsTransactionQuery({
    fdsTransactionId: transactionId,
  });
  const putAlertFdsParameter = usePutFdsTransactionQuery();
  const getMerchantQuery = useGetMerchantQuery({
    mid: getSingleAlertFdsParameter.data?.mid ?? "",
  });
  const getOwnerQuery = useGetOwnerQuery({
    oid: getSingleAlertFdsParameter.data?.mid ?? "",
  });
  const getAlertFdsParameterNextQuery = useGetFdsTransactionNextQuery({
    fdsTransactionId: transactionId,
  });
  const getCddQuery = useGetCddQuery({
    cddId: getMerchantQuery.data?.mccCd ?? "",
  });
  const getFdsParametersQuery = useGetFdsParametersQuery({
    getFdsParameterParam: {
      page: 0,
      size: 10000,
      sort: "fdsParameterKey",
      order: "asc",
    },
  });
  console.log("getFdsParametersQuery", getFdsParametersQuery.data);

  useEffect(() => {
    if (getSingleAlertFdsParameter.isSuccess) {
      setActionType(getSingleAlertFdsParameter.data?.actionType ?? null);
      setFraud({
        fraudType: getSingleAlertFdsParameter.data?.fraudType ?? null,
        fraudPodType: getSingleAlertFdsParameter.data?.fraudPodType ?? null,
        fraudNote: getSingleAlertFdsParameter.data?.fraudNote ?? null,
        fraudRemark: getSingleAlertFdsParameter.data?.fraudRemark ?? null,
        isFraudTypeOther: false,
        isFraudPodTypeOther: false,
      });
      setRemind({
        remindDate: getSingleAlertFdsParameter.data?.remindDate
          ? new Date(getSingleAlertFdsParameter.data.remindDate)
          : null,
        remindNote: getSingleAlertFdsParameter.data?.remindNote ?? null,
      });
      getMerchantQuery.refetch();
      getOwnerQuery.refetch();
      getAlertFdsParameterNextQuery.refetch();
    }
  }, [getSingleAlertFdsParameter.isSuccess]);
  useEffect(() => {
    if (putAlertFdsParameter.isSuccess) {
      close();
      getSingleAlertFdsParameter.refetch();
      notifications.show({
        title: "Success",
        message: "Alert updated successfully",
        color: "green",
      });
    }
  }, [putAlertFdsParameter.isSuccess]);
  useEffect(() => {
    if (getMerchantQuery.isSuccess) {
      getCddQuery.refetch();
    }
  }, [getMerchantQuery.isSuccess]);

  const handleNextClick = async () => {
    if (getSingleAlertFdsParameter?.data?.actionType === null) {
      notifications.show({
        title: "Please review the alert",
        message: "Once completed, you may proceed to the next one.",
        color: "red",
      });
    } else {
      const nextTransactionId =
        getAlertFdsParameterNextQuery.data?.fdsTransactionId;
      const nextPath = `/dashboard/alert/${nextTransactionId}`;
      router.push(nextPath);
    }
  };
  const isDisabled =
    // For REMIND: disabled if remindDate is missing
    (actionType === "REMIND" && !remind.remindDate) ||
    // For WATCHLIST/FRAUD: disabled if any fraud fields are missing
    ((actionType === "WATCHLIST" || actionType === "FRAUD") &&
      (!fraud.fraudType || !fraud.fraudPodType || !fraud.fraudRemark));

  // For NORMAL/INVESTIGATION: always enabled (no need to add condition)

  // useEffect(() => {
  //   if (getFdsTransactionReviewQuery.isError) {
  //     router.back();
  //     notifications.show({
  //       title: "Failed",
  //       message: getFdsTransactionReviewQuery.error.message ?? "",
  //       color: "red",
  //     });
  //   }
  // }, [getFdsTransactionReviewQuery.isError]);
  useEffect(() => {
    if (getSingleAlertFdsParameter.isError) {
      router.back();
      notifications.show({
        title: "Error",
        message: getSingleAlertFdsParameter?.error?.message ?? "",
        color: "red",
      });
    }
  }, [getSingleAlertFdsParameter.isError]);
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
            loading={putAlertFdsParameter.isPending}
            onClick={() => {
              putAlertFdsParameter.mutate({
                fdsTransactionId: transactionId,
                // eslint-disable-next-line @typescript-eslint/ban-ts-comment
                //@ts-ignore
                data: {
                  ...getSingleAlertFdsParameter.data,
                  actionType: actionType ?? "",
                  fraudType: fraud?.fraudType ?? "",
                  fraudPodType: fraud?.fraudPodType ?? "",
                  fraudNote: fraud?.fraudNote ?? "",
                  remindDate: remind?.remindDate,
                  remindNote: remind?.remindNote ?? "",
                  // confirmedBy: "admin",
                  // confirmedDateTime: new Date(),
                },
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
      <Box>
        <UnstyledButton onClick={() => router.push("/dashboard/alert")}>
          <Flex align={"center"}>
            <IconChevronLeft size={24} />
            <Space w={8} />
            <Text fz={18} fw={400}>
              Back
            </Text>
          </Flex>
        </UnstyledButton>
      </Box>
      <Space h={32} />
      <Card shadow="xs" radius="md">
        <Flex justify={"space-between"} align={"center"}>
          <Box>
            <Text fz={24} fw={500}>
              Transaction ID :{" "}
              {getSingleAlertFdsParameter.data?.fdsTransactionId}
            </Text>
            <Space h={12} />
            <Text fz={16} fw={400} c={"#64748B"}>
              {TimeUtil.formatTime(
                getSingleAlertFdsParameter.data?.assignedDateTime
              )}
            </Text>
            <Space h={12} />{" "}
            <Box miw={"100px"}>
              <Stack>
                {getSingleAlertFdsParameter.data?.parameterValues
                  .split(",")
                  .map((e, i) => {
                    return <Badge key={i}>{e}</Badge>;
                  })}
              </Stack>
            </Box>
          </Box>
          <Group justify="center">
            <Button
              leftSection={<IconPencil size={24} />}
              color={"#4AD2F5"}
              onClick={open}
              disabled={
                getSingleAlertFdsParameter.isLoading ||
                getSingleAlertFdsParameter.isFetching ||
                getSingleAlertFdsParameter.isFetching
              }
              loading={
                getSingleAlertFdsParameter.isLoading ||
                getSingleAlertFdsParameter.isFetching ||
                getSingleAlertFdsParameter.isFetching
              }
            >
              Action
            </Button>
            <Button
              color={"#64748B"}
              onClick={() => {
                // if (
                //   getSingleAlertFdsParameter.data?.actionType === null ||
                //   getSingleAlertFdsParameter.data?.actionType === undefined
                // ) {
                //   notifications.show({
                //     title: "Please review the alert",
                //     message:
                //       "Once you review the alert, you can move to the next alert",
                //     color: "red",
                //   });
                // } else {
                //   const nextPath = `/dashboard/alert/${getAlertFdsParameterNextQuery.data?.fdsTransactionId}`;
                //   // router.push(nextPath);
                //   window.location.href = nextPath;
                // }
                handleNextClick();
              }}
              disabled={
                !getAlertFdsParameterNextQuery.data === null ||
                getAlertFdsParameterNextQuery.isError
              }
              loading={getAlertFdsParameterNextQuery.isLoading}
              leftSection={<IconArrowRightBar />}
            >
              Next
            </Button>
          </Group>
        </Flex>
      </Card>
      <Space h={32} />
      <Card shadow="xs" radius="md">
        <Text fz={20} fw={400}>
          Transaction Details
        </Text>
        <Space h={16} />
        <SimpleGrid
          cols={{
            xs: 1,
            md: 2,
            lg: 3,
          }}
          spacing={16}
        >
          <FieldAndValue
            field={"MID"}
            value={getSingleAlertFdsParameter.data?.mid ?? ""}
            isLoading={
              getSingleAlertFdsParameter.isLoading ||
              getSingleAlertFdsParameter.isFetching
            }
          />
          <FieldAndValue
            field={"TID"}
            value={getSingleAlertFdsParameter.data?.tid ?? ""}
            isLoading={
              getSingleAlertFdsParameter.isLoading ||
              getSingleAlertFdsParameter.isFetching
            }
          />
          <FieldAndValue
            field={"Auth Seq No"}
            value={getSingleAlertFdsParameter.data?.authSeqNo ?? ""}
            isLoading={
              getSingleAlertFdsParameter.isLoading ||
              getSingleAlertFdsParameter.isFetching
            }
          />
          <FieldAndValue
            field={"Card No"}
            value={getSingleAlertFdsParameter.data?.cardNo ?? ""}
            isLoading={
              getSingleAlertFdsParameter.isLoading ||
              getSingleAlertFdsParameter.isFetching
            }
          />
          <FieldAndValue
            field={"Member Bank Acq"}
            value={getSingleAlertFdsParameter.data?.memberBankAcq ?? ""}
            isLoading={
              getSingleAlertFdsParameter.isLoading ||
              getSingleAlertFdsParameter.isFetching
            }
          />

          <FieldAndValue
            field={"Merchant Name"}
            value={getSingleAlertFdsParameter.data?.merchantName ?? ""}
            isLoading={
              getSingleAlertFdsParameter.isLoading ||
              getSingleAlertFdsParameter.isFetching
            }
          />

          <FieldAndValue
            field={"RRN"}
            value={getSingleAlertFdsParameter.data?.rrn ?? ""}
            isLoading={
              getSingleAlertFdsParameter.isLoading ||
              getSingleAlertFdsParameter.isFetching
            }
          />

          <FieldAndValue
            field={"Issuer"}
            value={getSingleAlertFdsParameter.data?.issuer ?? ""}
            isLoading={
              getSingleAlertFdsParameter.isLoading ||
              getSingleAlertFdsParameter.isFetching
            }
          />

          <FieldAndValue
            field={"Auth Date"}
            value={getSingleAlertFdsParameter.data?.authDate ?? ""}
            isLoading={
              getSingleAlertFdsParameter.isLoading ||
              getSingleAlertFdsParameter.isFetching
            }
          />

          <FieldAndValue
            field={"Auth Time"}
            value={getSingleAlertFdsParameter.data?.authTime ?? ""}
            isLoading={
              getSingleAlertFdsParameter.isLoading ||
              getSingleAlertFdsParameter.isFetching
            }
          />

          <FieldAndValue
            field={"Auth Amount"}
            value={getSingleAlertFdsParameter.data?.authAmount.toString() ?? ""}
            isLoading={
              getSingleAlertFdsParameter.isLoading ||
              getSingleAlertFdsParameter.isFetching
            }
          />

          <FieldAndValue
            field={"Trace No"}
            value={getSingleAlertFdsParameter.data?.traceNo ?? ""}
            isLoading={
              getSingleAlertFdsParameter.isLoading ||
              getSingleAlertFdsParameter.isFetching
            }
          />

          <FieldAndValue
            field={"Message Type Id"}
            value={getSingleAlertFdsParameter.data?.messageTypeId ?? ""}
            isLoading={
              getSingleAlertFdsParameter.isLoading ||
              getSingleAlertFdsParameter.isFetching
            }
          />

          <FieldAndValue
            field={"Auth Sale Type"}
            value={getSingleAlertFdsParameter.data?.authSaleType ?? ""}
            isLoading={
              getSingleAlertFdsParameter.isLoading ||
              getSingleAlertFdsParameter.isFetching
            }
          />

          <FieldAndValue
            field={"Auth Intn Rspn Cd"}
            value={getSingleAlertFdsParameter.data?.authIntnRspnCd ?? ""}
            isLoading={
              getSingleAlertFdsParameter.isLoading ||
              getSingleAlertFdsParameter.isFetching
            }
          />

          <FieldAndValue
            field={"Reason Contents"}
            value={getSingleAlertFdsParameter.data?.reasonContents ?? ""}
            isLoading={
              getSingleAlertFdsParameter.isLoading ||
              getSingleAlertFdsParameter.isFetching
            }
          />

          <FieldAndValue
            field={"Installment Count"}
            value={
              getSingleAlertFdsParameter.data?.installmentCount.toString() ?? ""
            }
            isLoading={
              getSingleAlertFdsParameter.isLoading ||
              getSingleAlertFdsParameter.isFetching
            }
          />

          <FieldAndValue
            field={"Switch Brand"}
            value={getSingleAlertFdsParameter.data?.switchBrand ?? ""}
            isLoading={
              getSingleAlertFdsParameter.isLoading ||
              getSingleAlertFdsParameter.isFetching
            }
          />

          <FieldAndValue
            field={"Pos Entry Mode Detail"}
            value={getSingleAlertFdsParameter.data?.posEntryModeDetail ?? ""}
            isLoading={
              getSingleAlertFdsParameter.isLoading ||
              getSingleAlertFdsParameter.isFetching
            }
          />

          <FieldAndValue
            field={"Card Type Code"}
            value={getSingleAlertFdsParameter.data?.cardTypeCode ?? ""}
            isLoading={
              getSingleAlertFdsParameter.isLoading ||
              getSingleAlertFdsParameter.isFetching
            }
          />

          <FieldAndValue
            field={"Onus Code"}
            value={getSingleAlertFdsParameter.data?.onusCode ?? ""}
            isLoading={
              getSingleAlertFdsParameter.isLoading ||
              getSingleAlertFdsParameter.isFetching
            }
          />

          <FieldAndValue
            field={"Eci Value"}
            value={getSingleAlertFdsParameter.data?.eciValue ?? ""}
            isLoading={
              getSingleAlertFdsParameter.isLoading ||
              getSingleAlertFdsParameter.isFetching
            }
          />

          <FieldAndValue
            field={"Approval Code"}
            value={getSingleAlertFdsParameter.data?.approvalCode ?? ""}
            isLoading={
              getSingleAlertFdsParameter.isLoading ||
              getSingleAlertFdsParameter.isFetching
            }
          />

          <FieldAndValue
            field={"PG Name"}
            value={getSingleAlertFdsParameter.data?.pgName ?? ""}
            isLoading={
              getSingleAlertFdsParameter.isLoading ||
              getSingleAlertFdsParameter.isFetching ||
              getSingleAlertFdsParameter.isFetching
            }
          />

          <FieldAndValue
            field={"PG Type"}
            value={getSingleAlertFdsParameter.data?.pgType ?? ""}
            isLoading={
              getSingleAlertFdsParameter.isLoading ||
              getSingleAlertFdsParameter.isFetching ||
              getSingleAlertFdsParameter.isFetching
            }
          />

          <FieldAndValue
            field={"Issuer Member No"}
            value={getSingleAlertFdsParameter.data?.issuerMemberNo ?? ""}
            isLoading={
              getSingleAlertFdsParameter.isLoading ||
              getSingleAlertFdsParameter.isFetching ||
              getSingleAlertFdsParameter.isFetching
            }
          />

          <FieldAndValue
            field={"Business Type"}
            value={getSingleAlertFdsParameter.data?.businessType ?? ""}
            isLoading={
              getSingleAlertFdsParameter.isLoading ||
              getSingleAlertFdsParameter.isFetching ||
              getSingleAlertFdsParameter.isFetching
            }
          />

          <FieldAndValue
            field={"Channel"}
            value={getSingleAlertFdsParameter.data?.channel ?? ""}
            isLoading={
              getSingleAlertFdsParameter.isLoading ||
              getSingleAlertFdsParameter.isFetching ||
              getSingleAlertFdsParameter.isFetching
            }
          />

          <FieldAndValue
            field={"Issuer Country"}
            value={getSingleAlertFdsParameter.data?.issuerCountry ?? ""}
            isLoading={
              getSingleAlertFdsParameter.isLoading ||
              getSingleAlertFdsParameter.isFetching ||
              getSingleAlertFdsParameter.isFetching
            }
          />
        </SimpleGrid>
      </Card>
      <Space h={32} />

      <Card shadow="xs" radius="md">
        <Text fz={20} fw={400}>
          Merchant Details
        </Text>
        <Space h={16} />
        <SimpleGrid cols={{ xs: 1, md: 2, lg: 3 }} spacing={16}>
          <FieldAndValue
            field={"MID"}
            value={getMerchantQuery.data?.mid ?? ""}
            isLoading={getMerchantQuery.isLoading}
          />
          <FieldAndValue
            field={"TID"}
            value={getSingleAlertFdsParameter.data?.tid ?? ""}
            isLoading={
              getSingleAlertFdsParameter.isLoading ||
              getSingleAlertFdsParameter.isFetching
            }
          />
          <FieldAndValue
            field={"Merchant Name"}
            value={getMerchantQuery.data?.merNm ?? ""}
            isLoading={getMerchantQuery.isLoading}
          />
          <FieldAndValue
            field={"Registration Date"}
            value={TimeUtil.formatTime(
              getDate(
                `${getMerchantQuery.data?.newRegtDate}${getMerchantQuery.data?.newRegtTime}`
              )
            )}
            isLoading={getMerchantQuery.isLoading}
          />
          <FieldAndValue
            field={"MCC"}
            value={getMerchantQuery.data?.mccCd ?? ""}
            isLoading={getMerchantQuery.isLoading}
          />
          <FieldAndValue
            field={"MCC Detail"}
            value={getCddQuery.data?.cdExpl ?? ""}
            isLoading={getMerchantQuery.isLoading || getCddQuery.isLoading}
          />
          <FieldAndValue
            field={"Segment"}
            value={getSingleAlertFdsParameter.data?.businessType ?? ""}
            isLoading={
              getSingleAlertFdsParameter.isLoading ||
              getSingleAlertFdsParameter.isFetching
            }
          />
          <FieldAndValue
            field={"Merchant Address"}
            value={`${getMerchantQuery.data?.offclMerDtlAddr1 ?? ""} ${
              getMerchantQuery.data?.offclMerDtlAddr2 ?? ""
            } ${getMerchantQuery.data?.offclMerDtlAddr3 ?? ""}`}
            isLoading={
              getSingleAlertFdsParameter.isLoading ||
              getSingleAlertFdsParameter.isFetching
            }
          />
        </SimpleGrid>
      </Card>
      <Space h={32} />

      <Card shadow="xs" radius="md">
        <Text fz={20} fw={400}>
          Owner Details
        </Text>
        <Space h={16} />
        <SimpleGrid cols={{ xs: 1, md: 2, lg: 3 }} spacing={16}>
          <FieldAndValue
            field={"Owner Name"}
            value={getOwnerQuery.data?.rpsvNm ?? ""}
            isLoading={getOwnerQuery.isLoading}
          />
          <FieldAndValue
            field={"Home Number"}
            value={`${getOwnerQuery.data?.homTelRgnCd ?? ""}-${
              getOwnerQuery.data?.homTelNo ?? ""
            }`}
            isLoading={getOwnerQuery.isLoading}
          />
          <FieldAndValue
            field={"Phone Number"}
            value={`${getOwnerQuery.data?.rpsvHpTelNo2 ?? ""}${
              getOwnerQuery.data?.rpsvHpTelNo1 ?? ""
            }`}
            isLoading={getOwnerQuery.isLoading}
          />
          <FieldAndValue
            field={"Email"}
            value={getOwnerQuery.data?.rpsvEmailAddr ?? ""}
            isLoading={getOwnerQuery.isLoading}
          />
          <FieldAndValue
            field={"Owner Address"}
            value={`${getOwnerQuery.data?.homDtlAddr1 ?? ""} ${
              getOwnerQuery.data?.homDtlAddr2 ?? ""
            } ${getOwnerQuery.data?.homDtlAddr3 ?? ""}`}
            isLoading={getOwnerQuery.isLoading}
          />
        </SimpleGrid>
      </Card>
      <Space h={32} />
      <Card shadow="xs" radius="md">
        <Text fz={20} fw={400}>
          Fraud Details
        </Text>
        <Space h={16} />
        <SimpleGrid cols={{ xs: 1, md: 2, lg: 3 }} spacing={16}>
          <FieldAndValue
            field={"Action Type"}
            value={getSingleAlertFdsParameter.data?.actionType ?? ""}
            isLoading={
              getSingleAlertFdsParameter.isLoading ||
              getSingleAlertFdsParameter.isFetching
            }
          />{" "}
          <FieldAndValue
            field={"Fraud Type"}
            value={getSingleAlertFdsParameter.data?.fraudType ?? ""}
            isLoading={
              getSingleAlertFdsParameter.isLoading ||
              getSingleAlertFdsParameter.isFetching
            }
          />
          <FieldAndValue
            field={"Fraud Pod Type"}
            value={getSingleAlertFdsParameter.data?.fraudPodType ?? ""}
            isLoading={
              getSingleAlertFdsParameter.isLoading ||
              getSingleAlertFdsParameter.isFetching
            }
          />
          <FieldAndValue
            field={"Fraud Note"}
            value={getSingleAlertFdsParameter.data?.fraudNote ?? ""}
            isLoading={
              getSingleAlertFdsParameter.isLoading ||
              getSingleAlertFdsParameter.isFetching
            }
          />
          <FieldAndValue
            field={"Assigned To"}
            value={
              getSingleAlertFdsParameter.data?.assignedUserAccount?.username ??
              ""
            }
            isLoading={
              getSingleAlertFdsParameter.isLoading ||
              getSingleAlertFdsParameter.isFetching
            }
          />
          <FieldAndValue
            field={"Assigned Date Time"}
            value={TimeUtil.formatTime(
              getSingleAlertFdsParameter.data?.assignedDateTime
            )}
            isLoading={
              getSingleAlertFdsParameter.isLoading ||
              getSingleAlertFdsParameter.isFetching
            }
          />
          <FieldAndValue
            field={"Reviewed By"}
            value={
              getSingleAlertFdsParameter.data?.confirmedUserAccount?.username ??
              ""
            }
            isLoading={
              getSingleAlertFdsParameter.isLoading ||
              getSingleAlertFdsParameter.isFetching
            }
          />
          <FieldAndValue
            field={"Review Date Time"}
            value={TimeUtil.formatTime(
              getSingleAlertFdsParameter.data?.confirmedDateTime
            )}
            isLoading={
              getSingleAlertFdsParameter.isLoading ||
              getSingleAlertFdsParameter.isFetching
            }
          />
        </SimpleGrid>
      </Card>
      <Space h={32} />
      <Card shadow="xs" radius="md">
        <Text fz={20} fw={400}>
          Remind Details
        </Text>
        <Space h={16} />
        <SimpleGrid cols={{ xs: 1, md: 2, lg: 3 }} spacing={16}>
          <FieldAndValue
            field={"Remind Date"}
            value={TimeUtil.formatTime(
              getSingleAlertFdsParameter.data?.remindDate
            )}
            isLoading={
              getSingleAlertFdsParameter.isLoading ||
              getSingleAlertFdsParameter.isFetching
            }
          />
          <FieldAndValue
            field={"Remind Note"}
            value={getSingleAlertFdsParameter.data?.remindNote ?? ""}
            isLoading={
              getSingleAlertFdsParameter.isLoading ||
              getSingleAlertFdsParameter.isFetching
            }
          />
        </SimpleGrid>
      </Card>
    </Box>
  );
}

function getDate(str: string) {
  try {
    const year = parseInt(str.slice(0, 4), 10);
    const month = parseInt(str.slice(4, 6), 10) - 1; // Month is zero-based in JS
    const day = parseInt(str.slice(6, 8), 10);
    const hours = parseInt(str.slice(8, 10), 10);
    const minutes = parseInt(str.slice(10, 12), 10);
    const seconds = parseInt(str.slice(12, 14), 10);

    // Create the Date object
    const date = new Date(year, month, day, hours, minutes, seconds);
    return date;
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
  } catch (e) {
    return new Date();
  }
}
