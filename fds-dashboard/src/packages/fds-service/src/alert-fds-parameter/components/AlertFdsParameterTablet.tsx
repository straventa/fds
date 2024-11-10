"use client";
import BasePagination from "@/packages/base/src/components/BasePagination";
import SkeletonRows from "@/packages/base/src/components/SkeletonRows";
import { TimeUtil } from "@/packages/base/src/util/time.util";
import { useGetAlertFdsParameterPagination } from "@/packages/fds-service/src/alert-fds-parameter/hooks/get-alert-fds-parameter.pagination";
import { useGetAlertFdsParameter } from "@/packages/fds-service/src/alert-fds-parameter/query/get-alert-fds-parameter.query";
import { getUniqueIdUtil } from "@/packages/fds-service/src/alert-fds-parameter/util/get-unique-id.util";
import {
  ActionIcon,
  Badge,
  Box,
  Button,
  Card,
  Flex,
  ScrollArea,
  Select,
  Space,
  Stack,
  Table,
  Text,
  TextInput,
  UnstyledButton,
} from "@mantine/core";
import { DatePickerInput } from "@mantine/dates";
import { useMediaQuery } from "@mantine/hooks";
import { notifications } from "@mantine/notifications";
import { IconArrowDown, IconArrowUp, IconEye } from "@tabler/icons-react";
import { useRouter } from "next/navigation";
import { useEffect } from "react";
import { BarLoader } from "react-spinners";

export default function AlertFdsParameterTable() {
  const router = useRouter();
  const getAlertFdsParameterPagination = useGetAlertFdsParameterPagination({
    page: 0,
    size: 10,
    startDate: new Date(new Date().setDate(new Date().getDate() - 30)),
    endDate: new Date(),
    sort: "authSeqNo",
    order: "desc",
    tid: null,
    mid: null,
    assignedTo: "admin",
    actionType: null,
  });
  const getAlertFdsParameterQuery = useGetAlertFdsParameter({
    getAlertFdsParameterParam: {
      page: getAlertFdsParameterPagination.page,
      size: getAlertFdsParameterPagination.size,
      sort: getAlertFdsParameterPagination.sort,
      order: getAlertFdsParameterPagination.order,
      startDate: getAlertFdsParameterPagination.startDate,
      endDate: getAlertFdsParameterPagination.endDate,

      mid: getAlertFdsParameterPagination.mid,
      tid: getAlertFdsParameterPagination.tid,
      assignedTo: getAlertFdsParameterPagination.assignedTo,
      actionType: getAlertFdsParameterPagination.actionType,
    },
  });

  const isMobile = useMediaQuery("(max-width: 768px)");
  const columns = [
    {
      title: "MID",
      dataIndex: "mid",
      key: "mid",
      left: 0,
    },
    {
      title: "TID",
      dataIndex: "tid",
      key: "tid",
      left: 120,
    },
    {
      title: "Action",
      dataIndex: "escalation",
      key: "escalation",
      left: 220,
    },
    {
      title: "Auth Seq No",
      dataIndex: "authSeqNo",
      key: "authSeqNo",
    },
    {
      title: "Card No",
      dataIndex: "cardNo",
      key: "cardNo",
    },
    {
      title: "Member Bank Acquirer",
      dataIndex: "memberBankAcq",
      key: "memberBankAcq",
    },
    {
      title: "Merchant Name",
      dataIndex: "merchantName",
      key: "merchantName",
    },

    {
      title: "RRN",
      dataIndex: "rrn",
      key: "rrn",
    },
    {
      title: "Issuer",
      dataIndex: "issuer",
      key: "issuer",
    },
    {
      title: "Auth Date",
      dataIndex: "authDate",
      key: "authDate",
    },
    {
      title: "Auth Time",
      dataIndex: "authTime",
      key: "authTime",
    },
    {
      title: "Auth Amount",
      dataIndex: "authAmount",
      key: "authAmount",
    },
    {
      title: "Trace No",
      dataIndex: "traceNo",
      key: "traceNo",
    },
    {
      title: "Message Type ID",
      dataIndex: "messageTypeId",
      key: "messageTypeId",
    },
    {
      title: "Auth Sale Type",
      dataIndex: "authSaleType",
      key: "authSaleType",
    },
    {
      title: "Auth Internal Response Code",
      dataIndex: "authIntnRspnCd",
      key: "authIntnRspnCd",
    },
    {
      title: "Reason Contents",
      dataIndex: "reasonContents",
      key: "reasonContents",
    },
    {
      title: "Installment Count",
      dataIndex: "installmentCount",
      key: "installmentCount",
    },
    {
      title: "Switch Brand",
      dataIndex: "switchBrand",
      key: "switchBrand",
    },
    {
      title: "POS Entry Mode Detail",
      dataIndex: "posEntryModeDetail",
      key: "posEntryModeDetail",
    },
    {
      title: "Card Type Code",
      dataIndex: "cardTypeCode",
      key: "cardTypeCode",
    },
    {
      title: "Onus Code",
      dataIndex: "onusCode",
      key: "onusCode",
    },
    {
      title: "ECI Value",
      dataIndex: "eciValue",
      key: "eciValue",
    },
    {
      title: "Approval Code",
      dataIndex: "approvalCode",
      key: "approvalCode",
    },
    {
      title: "PG Name",
      dataIndex: "pgName",
      key: "pgName",
    },
    {
      title: "PG Type",
      dataIndex: "pgType",
      key: "pgType",
    },
    {
      title: "Issuer Member No",
      dataIndex: "issuerMemberNo",
      key: "issuerMemberNo",
    },
    {
      title: "Business Type",
      dataIndex: "businessType",
      key: "businessType",
    },
    {
      title: "Channel",
      dataIndex: "channel",
      key: "channel",
    },
    {
      title: "Issuer Country",
      dataIndex: "issuerCountry",
      key: "issuerCountry",
    },

    {
      title: "Action Type",
      dataIndex: "actionType",
      key: "actionType",
    },
    {
      title: "Fraud Type",
      dataIndex: "fraudType",
      key: "fraudType",
    },
    {
      title: "Fraud Pod Type",
      dataIndex: "fraudPodType",
      key: "fraudPodType",
    },
    {
      title: "Fraud Note",
      dataIndex: "fraudNote",
      key: "fraudNote",
    },
    {
      title: "Confirmed By",
      dataIndex: "confirmedBy",
      key: "confirmedBy",
    },
    {
      title: "Confirmed Date Time",
      dataIndex: "confirmedDateTime",
      key: "confirmedDateTime",
    },
    {
      title: "Remind Note",
      dataIndex: "remindNote",
      key: "remindNote",
    },
    {
      title: "Remind Date",
      dataIndex: "remindDate",
      key: "remindDate",
    },
    {
      title: "Assigned To",
      dataIndex: "assignedTo",
      key: "assignedTo",
    },
    {
      title: "Assigned Date Time",
      dataIndex: "assignedDateTime",
      key: "assignedDateTime",
    },
    {
      title: "Parameter Values",
      dataIndex: "parameterValues",
      key: "parameterValues",
      width: "200px",
    },
  ];

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
    getAlertFdsParameterPagination.assignedTo,
    getAlertFdsParameterPagination.actionType,
  ]);

  return (
    <Box>
      <Flex
        my={24}
        justify={{
          sm: "center",
          md: "space-between",
        }}
        direction={{
          base: "column",
          xs: "column",
          sm: "column",
          md: "row",
        }}
        gap={24}
      >
        <Button.Group>
          {" "}
          <Button
            variant="default"
            size={isMobile ? "xs" : "sm"}
            disabled={
              getAlertFdsParameterPagination.startDate.getFullYear() ===
                new Date(
                  new Date().setDate(new Date().getDate() - 1)
                ).getFullYear() &&
              getAlertFdsParameterPagination.startDate.getMonth() ===
                new Date(
                  new Date().setDate(new Date().getDate() - 1)
                ).getMonth() &&
              getAlertFdsParameterPagination.startDate.getDate() ===
                new Date(new Date().setDate(new Date().getDate() - 1)).getDate()
            }
            onClick={() => {
              getAlertFdsParameterPagination.setValue({
                startDate: new Date(
                  new Date().setDate(new Date().getDate() - 1)
                ),
                endDate: new Date(),
              });
            }}
          >
            24 Hours
          </Button>
          <Button
            variant="default"
            size={isMobile ? "xs" : "sm"}
            disabled={
              getAlertFdsParameterPagination.startDate.getFullYear() ===
                new Date(
                  new Date().setDate(new Date().getDate() - 7)
                ).getFullYear() &&
              getAlertFdsParameterPagination.startDate.getMonth() ===
                new Date(
                  new Date().setDate(new Date().getDate() - 7)
                ).getMonth() &&
              getAlertFdsParameterPagination.startDate.getDate() ===
                new Date(new Date().setDate(new Date().getDate() - 7)).getDate()
            }
            onClick={() => {
              getAlertFdsParameterPagination.setValue({
                startDate: new Date(
                  new Date().setDate(new Date().getDate() - 7)
                ),
                endDate: new Date(),
              });
            }}
          >
            7 Days
          </Button>
          <Button
            variant="default"
            size={isMobile ? "xs" : "sm"}
            disabled={
              getAlertFdsParameterPagination.startDate.getFullYear() ===
                new Date(
                  new Date().setDate(new Date().getDate() - 30)
                ).getFullYear() &&
              getAlertFdsParameterPagination.startDate.getMonth() ===
                new Date(
                  new Date().setDate(new Date().getDate() - 30)
                ).getMonth() &&
              getAlertFdsParameterPagination.startDate.getDate() ===
                new Date(
                  new Date().setDate(new Date().getDate() - 30)
                ).getDate()
            }
            onClick={() => {
              getAlertFdsParameterPagination.setValue({
                startDate: new Date(
                  new Date().setDate(new Date().getDate() - 30)
                ),
                endDate: new Date(),
              });
            }}
          >
            30 Days
          </Button>
          <Button
            variant="default"
            size={isMobile ? "xs" : "sm"}
            disabled={
              getAlertFdsParameterPagination.startDate.getFullYear() ===
                new Date(
                  new Date().setDate(new Date().getDate() - 365)
                ).getFullYear() &&
              getAlertFdsParameterPagination.startDate.getMonth() ===
                new Date(
                  new Date().setDate(new Date().getDate() - 365)
                ).getMonth() &&
              getAlertFdsParameterPagination.startDate.getDate() ===
                new Date(
                  new Date().setDate(new Date().getDate() - 365)
                ).getDate()
            }
            onClick={() => {
              getAlertFdsParameterPagination.setValue({
                startDate: new Date(
                  new Date().setDate(new Date().getDate() - 365)
                ),
                endDate: new Date(),
              });
            }}
          >
            12 Months
          </Button>
        </Button.Group>
        <DatePickerInput
          type="range"
          placeholder="Select date range"
          value={[
            getAlertFdsParameterPagination.startDate,
            getAlertFdsParameterPagination.endDate,
          ]}
          onChange={(value) => {
            if (value[0] === null && value[1] === null) {
              notifications.show({
                title: "Error",
                message: "Please select a valid date range",
                color: "red",
              });
              return;
            }
            getAlertFdsParameterPagination.setValue({
              startDate: value[0] as Date,
              endDate: value[1] as Date,
            });
          }}
        />
      </Flex>
      {/* <Flex justify="space-between">
          <Title order={3}>Filters</Title>
        </Flex> */}
      <Box my="md">
        <Flex gap={12} align={"center"} wrap={"wrap"}>
          <Text fz={16} fw={400}>
            Filter by
          </Text>
          <TextInput
            placeholder="Search by MID"
            value={getAlertFdsParameterPagination.mid ?? ""}
            onChange={(e) =>
              getAlertFdsParameterPagination.setMid(e.currentTarget.value)
            }
          />
          <TextInput
            placeholder="Search by TID"
            value={getAlertFdsParameterPagination.tid ?? ""}
            onChange={(e) =>
              getAlertFdsParameterPagination.setTid(e.currentTarget.value)
            }
          />
          <Select
            placeholder="Search Assigned To"
            data={[
              {
                label: "Assigned To Me",
                value: "admin",
              },
              {
                label: "All",
                value: "all",
              },
            ]}
            value={getAlertFdsParameterPagination.assignedTo}
            onChange={(_value, option) => {
              if (option === null) {
                getAlertFdsParameterPagination.setAssignedTo(null);
              } else {
                getAlertFdsParameterPagination.setAssignedTo(option.value);
              }
            }}
          />
          <Select
            placeholder="Action Type"
            data={[
              { value: "REMIND", label: "REMIND" },
              { value: "NORMAL", label: "NORMAL" },
              { value: "WATCHLIST", label: "WATCHLIST" },
              { value: "FRAUD", label: "FRAUD" },
            ]}
            value={getAlertFdsParameterPagination.actionType}
            onChange={(_value, option) => {
              if (option === null) {
                getAlertFdsParameterPagination.setActionType(null);
              } else {
                getAlertFdsParameterPagination.setActionType(option.value);
              }
            }}
          />
          {/* <Select
              clearable
              // label="Payment Gateway"
              placeholder="Select payment gateway"
              data={getPaymentGatewayQuery.data?.content?.map((e) => {
                return {
                  label: e.paymentGatewayName,
                  value: e.paymentGatewayId,
                };
              })}
              value={getAlertFdsParameterPagination.paymentGatewayId}
              onChange={(_value, option) => {
                if (option === null) {
                  getAlertFdsParameterPagination.setPaymentGatewayId(null);
                } else {
                  getAlertFdsParameterPagination.setPaymentGatewayId(option.value);
                }
              }}
            /> */}
          {/* {getRisksQuery.isLoading ? (
            <Skeleton width={200} height={40} />
          ) : (
            <Select
              clearable
              // label="Payment Provider"
              placeholder="Search By Risks"
              data={getRisksQuery.data?.content?.map((e) => {
                return {
                  label: e.riskName,
                  value: e.riskId,
                };
              })}
              value={getAlertFdsParameterPagination.riskId}
              onChange={(_value, option) => {
                if (option === null) {
                  getAlertFdsParameterPagination.setRiskId(null);
                } else {
                  getAlertFdsParameterPagination.setRiskId(option.value);
                }
              }}
            />
          )}
          <Select
            clearable
            // label="Status"
            placeholder="Select status"
            data={[
              {
                label: "SUCCESS",
                value: "SUCCESS",
              },
              {
                label: "PENDING",
                value: "PENDING",
              },
              {
                label: "EXPIRED",
                value: "EXPIRED",
              },
            ]}
            value={getAlertFdsParameterPagination.transactionStatus}
            onChange={(_value, option) => {
              if (option === null) {
                getAlertFdsParameterPagination.setTransactionStatus(null);
              } else {
                getAlertFdsParameterPagination.setTransactionStatus(option.value);
              }
            }}
          /> */}
        </Flex>
        {/* <Grid my="md">
            <Grid.Col
              span={{
                xs: 12,
                md: 2,
              }}
            >
              <Text fz={16} fw={400}>
                Filter by
              </Text>
            </Grid.Col>
            <Grid.Col
              span={{
                xs: 12,
                md: 2,
              }}
            >
              <Select
                // label="Payment Gateway"
                placeholder="Select payment gateway"
                data={getPaymentGatewayQuery.data?.content?.map((e) => {
                  return {
                    label: e.paymentGatewayName,
                    value: e.paymentGatewayId,
                  };
                })}
                value={getAlertFdsParameterPagination.paymentGatewayId}
                onChange={(_value, option) =>
                  getAlertFdsParameterPagination.setPaymentGatewayId(option.value)
                }
              />
            </Grid.Col>
            <Grid.Col
              span={{
                xs: 12,
                md: 2,
              }}
            >
              <Select
                // label="Payment Provider"
                placeholder="Search payment provider"
                data={getPaymentProvidersQuery.data?.content?.map((e) => {
                  return {
                    label: e.paymentProviderName,
                    value: e.paymentProviderId,
                  };
                })}
                value={getAlertFdsParameterPagination.paymentProviderId}
                onChange={(_value, option) =>
                  getAlertFdsParameterPagination.setPaymentProviderId(option.value)
                }
              />
            </Grid.Col>
            <Grid.Col
              span={{
                xs: 12,
                md: 2,
              }}
            >
              <Select
                // label="Status"
                placeholder="Select status"
                data={[
                  {
                    label: "SUCCESS",
                    value: "SUCCESS",
                  },
                  {
                    label: "PENDING",
                    value: "PENDING",
                  },
                  {
                    label: "EXPIRED",
                    value: "EXPIRED",
                  },
                ]}
                value={getAlertFdsParameterPagination.transactionStatus}
                onChange={(_value, option) =>
                  getAlertFdsParameterPagination.setTransactionStatus(option.value)
                }
              />
            </Grid.Col>
          </Grid> */}
      </Box>
      <Card shadow="xs" radius="md" p={0}>
        <Box mih={"800px"}>
          <BarLoader
            width={"100%"}
            loading={
              getAlertFdsParameterQuery.isLoading ||
              getAlertFdsParameterQuery.isFetching ||
              getAlertFdsParameterQuery.isRefetching
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
                        style={{
                          width: e.width ?? "600px",
                        }}
                        pos={e.left !== undefined ? "sticky" : "static"}
                        left={e.left !== undefined ? e.left : "auto"}
                        bg={e.left !== undefined ? "#F6F6F6" : "transparent"}
                      >
                        <UnstyledButton
                          onClick={() => {
                            // getAlertFdsParameterPagination.setValue({
                            //   sort: e.key,
                            //   order:
                            //     getAlertFdsParameterPagination.sort === e.key
                            //       ? getAlertFdsParameterPagination.order === "asc"
                            //         ? "desc"
                            //         : "asc"
                            //       : "asc",
                            // });
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
                            <Text>{e.title}</Text>
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
                          {e.mid}
                        </Table.Td>
                        <Table.Td
                          px={24}
                          py={12}
                          left={120}
                          pos={"sticky"}
                          bg={"#F6F6F6"}
                        >
                          {e.tid}
                        </Table.Td>
                        <Table.Td
                          px={24}
                          py={12}
                          left={220}
                          pos={"sticky"}
                          bg={"#F6F6F6"}
                        >
                          <ActionIcon
                            variant="transparent"
                            size="xl"
                            aria-label="Gradient action icon"
                            gradient={{ from: "blue", to: "cyan", deg: 90 }}
                            onClick={() => {
                              router.push(
                                `/dashboard/alert/${getUniqueIdUtil({
                                  alertFdsParameter: e,
                                })}`
                              );
                            }}
                          >
                            <IconEye />
                          </ActionIcon>
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
                          {e.authTime}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          {e.authAmount}
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
                          {e.fraudNote}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          {e.confirmedBy}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          {TimeUtil.formatTime(e.confirmedDateTime)}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          {e.remindNote}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          {TimeUtil.formatTime(e.remindDate)}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          {e.assignedTo}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          {TimeUtil.formatTime(e.assignedDateTime)}
                        </Table.Td>
                        <Table.Td px={24} py={12} miw={"100px"}>
                          <Stack>
                            {e.parameterValues.split(",").map((e, i) => {
                              return <Badge key={i}>{e}</Badge>;
                            })}
                          </Stack>
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
          />
        </Box>
      </Card>
    </Box>
  );
}
