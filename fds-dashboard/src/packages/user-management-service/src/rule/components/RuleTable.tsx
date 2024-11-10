"use client";
import { useGetRulePagination } from "@/packages/user-management-service/src/rule/hooks/get-rule-pagination.hooks";
import { useGetRulesQuery } from "@/packages/user-management-service/src/rule/query/get-rules.query";
import { useRouter } from "next/navigation";
import { useCallback, useEffect, useState } from "react";

import BasePagination from "@/packages/base/src/components/BasePagination";
import SkeletonRows from "@/packages/base/src/components/SkeletonRows";
import { TimeUtil } from "@/packages/base/src/util/time.util";
import { getRiskLevelColor } from "@/packages/user-management-service/src/rule/util/rule.util";
import {
  ActionIcon,
  Badge,
  Box,
  Card,
  Flex,
  ScrollArea,
  Select,
  Space,
  Table,
  Text,
  TextInput,
  UnstyledButton,
} from "@mantine/core";
import { IconArrowDown, IconArrowUp, IconEye } from "@tabler/icons-react";
import { debounce } from "lodash";
import { BarLoader } from "react-spinners";

export default function RuleTable() {
  const router = useRouter();
  const getRulePagination = useGetRulePagination({
    page: 0,
    size: 10,
    sort: "riskLevelNumber",
    order: "desc",
    totalItems: 0,
    riskLevel: null,
    ruleName: null,
    ruleDescription: null,
    sourceData: null,
    userAccountId: null,
  });

  const getRulesQuery = useGetRulesQuery({
    getRulesParam: {
      page: getRulePagination.page,
      size: getRulePagination.size,
      sort: getRulePagination.sort,
      order: getRulePagination.order,
      riskLevel: getRulePagination.riskLevel,
      ruleName: getRulePagination.ruleName,
      ruleDescription: getRulePagination.ruleDescription,
      sourceData: getRulePagination.sourceData,
      userAccountId: getRulePagination.userAccountId,
    },
  });

  const [localRuleName, setLocalRuleName] = useState(
    getRulePagination.ruleName ?? ""
  );
  const [localRuleDescription, setLocalRuleDescription] = useState(
    getRulePagination.ruleDescription ?? ""
  );
  const debouncedSearch = useCallback(
    debounce((value: string, type: "ruleName" | "ruleDescription") => {
      if (type === "ruleName") {
        getRulePagination.setRuleName(value);
      } else {
        getRulePagination.setRuleDescription(value);
      }
    }, 1000),
    []
  );
  const columns = [
    {
      title: "Rule ID",
      dataIndex: "ruleId",
      key: "ruleId",
    },
    {
      title: "Rule Name",
      dataIndex: "ruleName",
      key: "ruleName",
    },
    {
      title: "Rule Description",
      dataIndex: "ruleDescription",
      key: "ruleDescription",
    },
    {
      title: "Risk Level",
      dataIndex: "riskLevelNumber",
      key: "riskLevelNumber",
    },
    {
      title: "Source Data",
      dataIndex: "sourceData",
      key: "sourceData",
    },

    {
      title: "Created By",
      dataIndex: "createdBy",
      key: "createdBy",
    },
    {
      title: "Created Date",
      dataIndex: "createdDate",
      key: "createdDate",
    },
    {
      title: "Last Modified By",
      dataIndex: "lastModifiedBy",
      key: "lastModifiedBy",
    },
    {
      title: "Last Modified Date",
      dataIndex: "lastModifiedDate",
      key: "lastModifiedDate",
    },
    {
      title: "Action",
      key: null,
    },
  ];

  useEffect(() => {
    getRulesQuery.refetch();
  }, [
    getRulePagination.page,
    getRulePagination.size,
    getRulePagination.sort,
    getRulePagination.order,
    getRulePagination.riskLevel,
    getRulePagination.ruleName,
    getRulePagination.ruleDescription,
    getRulePagination.sourceData,
    getRulePagination.userAccountId,
  ]);

  return (
    <Box>
      <Box my="md">
        <Flex gap={12} align={"center"} wrap={"wrap"}>
          <Text fz={16} fw={400}>
            Filter by
          </Text>
          <TextInput
            placeholder="Search by Rule Name"
            value={localRuleName}
            onChange={(e) => {
              const value = e.target.value;
              setLocalRuleName(value);
              debouncedSearch(value, "ruleName");
            }}
          />
          <TextInput
            placeholder="Search by Rule Description"
            value={localRuleDescription}
            onChange={(e) => {
              const value = e.target.value;
              setLocalRuleDescription(value);
              debouncedSearch(value, "ruleDescription");
            }}
          />
          <Select
            placeholder="Search by Risk Level"
            data={[
              {
                label: "High Risk",
                value: "High Risk",
              },
              {
                label: "Medium Risk",
                value: "Medium Risk",
              },
              {
                label: "Low Risk",
                value: "Low Risk",
              },
            ]}
            value={getRulePagination.riskLevel ?? ""}
            onChange={(_value, option) => {
              if (option === null) {
                getRulePagination.setRiskLevel(null);
              } else {
                getRulePagination.setRiskLevel(option.value);
              }
            }}
          />
          {/* <Select
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
            value={getRulePagination.assignedTo}
            onChange={(_value, option) => {
              if (option === null) {
                getRulePagination.setAssignedTo(null);
              } else {
                getRulePagination.setAssignedTo(option.value);
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
            value={getRulePagination.actionType}
            onChange={(_value, option) => {
              if (option === null) {
                getRulePagination.setActionType(null);
              } else {
                getRulePagination.setActionType(option.value);
              }
            }}
          /> */}
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
              value={getRulePagination.paymentGatewayId}
              onChange={(_value, option) => {
                if (option === null) {
                  getRulePagination.setPaymentGatewayId(null);
                } else {
                  getRulePagination.setPaymentGatewayId(option.value);
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
              value={getRulePagination.riskId}
              onChange={(_value, option) => {
                if (option === null) {
                  getRulePagination.setRiskId(null);
                } else {
                  getRulePagination.setRiskId(option.value);
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
            value={getRulePagination.transactionStatus}
            onChange={(_value, option) => {
              if (option === null) {
                getRulePagination.setTransactionStatus(null);
              } else {
                getRulePagination.setTransactionStatus(option.value);
              }
            }}
          /> */}
        </Flex>
      </Box>
      <Card shadow="xs" radius="md" p={0}>
        <Box mih={"800px"}>
          <BarLoader
            width={"100%"}
            loading={
              getRulesQuery.isLoading ||
              getRulesQuery.isFetching ||
              getRulesQuery.isRefetching
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
                      <Table.Th key={i} c={"#64748B"}>
                        <UnstyledButton
                          style={{
                            cursor: e.key === null ? "default" : "pointer",
                          }}
                          onClick={() => {
                            if (e.key !== null) {
                              getRulePagination.setSort(e.key);
                              getRulePagination.setOrder(
                                getRulePagination.sort === e.key
                                  ? getRulePagination.order === "asc"
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
                            {e.key === null ? null : getRulePagination.sort ===
                              e.key ? (
                              getRulePagination.order === "asc" ? (
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
                {getRulesQuery.data !== undefined ? (
                  getRulesQuery.data.content?.map((e, i) => {
                    return (
                      <Table.Tr key={i}>
                        <Table.Td px={24} py={12}>
                          {e.ruleId}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          {e.ruleName}
                        </Table.Td>
                        <Table.Td
                          px={24}
                          py={12}
                          style={{
                            width: 400,
                          }}
                        >
                          {e.ruleDescription}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          <Badge color={getRiskLevelColor(e.riskLevel)}>
                            {e.riskLevel}
                          </Badge>
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          {e.sourceData}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          {e.createdBy}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          {TimeUtil.formatTime(e.createdDate)}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          {e.lastModifiedBy}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          {TimeUtil.formatTime(e.lastModifiedDate)}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          <ActionIcon
                            onClick={() => {
                              router.push(
                                `/dashboard/rule-management/${e.ruleId}`
                              );
                            }}
                          >
                            <IconEye />
                          </ActionIcon>
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
          <BasePagination pageable={getRulePagination} query={getRulesQuery} />
        </Box>
      </Card>
    </Box>
  );
}
