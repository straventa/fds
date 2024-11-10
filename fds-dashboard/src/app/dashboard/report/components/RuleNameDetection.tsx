"use client";

import {
  Badge,
  Box,
  Button,
  Card,
  Flex,
  NumberFormatter,
  ScrollArea,
  Space,
  Table,
  Text,
} from "@mantine/core";
import { BarLoader } from "react-spinners";

import { useGetFdsTransactionRuleNameCsvQuery } from "@/packages/user-management-service/src/fds-transactions/query/get-alert-fds-transactions-report-rule-name-csv.query";
import { useGetFdsTransactionRuleNameExcelQuery } from "@/packages/user-management-service/src/fds-transactions/query/get-alert-fds-transactions-report-rule-name-excel.query";
import { useGetFdsTransactionsReportRuleNameQuery } from "@/packages/user-management-service/src/fds-transactions/query/get-fds-transactions-report-rule-name.query";
import { getRiskLevelColor } from "@/packages/user-management-service/src/rule/util/rule.util";
import { IconCsv, IconFileExcel } from "@tabler/icons-react";

export default function RuleNameDetection({
  startDate,
  endDate,
}: {
  startDate: Date;
  endDate: Date;
}) {
  const getAlertFdsParameterAnalystQuery =
    useGetFdsTransactionsReportRuleNameQuery({
      getFdsTransactionsReportAnalystParam: {
        startDate,
        endDate,
      },
    });

  const getAlertFdsTransactionAnalystExcelQuery =
    useGetFdsTransactionRuleNameExcelQuery();
  const getAlertFdsTransactionAnalystCsvQuery =
    useGetFdsTransactionRuleNameCsvQuery();

  return (
    <>
      <Flex w={"100%"} justify={"flex-end"}>
        <Button
          leftSection={<IconFileExcel />}
          onClick={() => {
            console.log("downloading excel");

            getAlertFdsTransactionAnalystExcelQuery.mutate({
              startDate,
              endDate,
            });
          }}
          loading={getAlertFdsTransactionAnalystExcelQuery.isPending}
          color="#4AD2F5"
        >
          Download Excel
        </Button>
        <Space w={16} />
        <Button
          leftSection={<IconCsv />}
          onClick={() => {
            console.log("downloading csv");
            getAlertFdsTransactionAnalystCsvQuery.mutate({
              startDate,
              endDate,
            });
          }}
          loading={getAlertFdsTransactionAnalystCsvQuery.isPending}
          color="#4AD2F5"
        >
          Download CSV
        </Button>
      </Flex>
      <Card shadow="xs" py="md" radius="md" mt="md">
        <Box mih={"800px"}>
          <BarLoader
            width={"100%"}
            loading={
              getAlertFdsParameterAnalystQuery.isLoading ||
              getAlertFdsParameterAnalystQuery.isFetching ||
              getAlertFdsParameterAnalystQuery.isRefetching
            }
            color="#4AD2F5"
          />
          <ScrollArea h={"800px"}>
            <Box>
              <Table
                striped
                highlightOnHover
                horizontalSpacing={"xl"}
                verticalSpacing={"xl"}
              >
                <Table.Thead pos={"sticky"} bg={"#F6F6F6"}>
                  <Table.Tr>
                    <Table.Th>Rule Name</Table.Th>
                    <Table.Th>Risk Level</Table.Th>
                    <Table.Th>Genuine</Table.Th>
                    <Table.Th>Investigation</Table.Th>

                    <Table.Th>Watchlist</Table.Th>
                    <Table.Th>Fraud</Table.Th>
                    <Table.Th>Remind</Table.Th>
                    {/* <Table.Th>Pending</Table.Th> */}

                    <Table.Th>Total Alert</Table.Th>

                    <Table.Th>False Positive</Table.Th>
                  </Table.Tr>
                </Table.Thead>
                <Table.Tbody>
                  {getAlertFdsParameterAnalystQuery.data?.map((analyst) => {
                    return (
                      <Table.Tr key={analyst.ruleName}>
                        <Table.Td>{analyst.ruleName}</Table.Td>
                        <Table.Td>
                          <Badge color={getRiskLevelColor(analyst.riskLevel)}>
                            {analyst.riskLevel}
                          </Badge>
                        </Table.Td>

                        <Table.Td>
                          <NumberFormatter
                            value={analyst.genuine}
                            decimalSeparator=","
                            thousandSeparator="."
                          />
                        </Table.Td>
                        <Table.Td>
                          <NumberFormatter
                            value={analyst.investigation}
                            decimalSeparator=","
                            thousandSeparator="."
                          />
                        </Table.Td>
                        <Table.Td>
                          <NumberFormatter
                            value={analyst.watchlist}
                            decimalSeparator=","
                            thousandSeparator="."
                          />
                        </Table.Td>
                        <Table.Td>
                          <NumberFormatter
                            value={analyst.fraud}
                            decimalSeparator=","
                            thousandSeparator="."
                          />
                        </Table.Td>
                        <Table.Td>
                          <NumberFormatter
                            value={analyst.remind}
                            decimalSeparator=","
                            thousandSeparator="."
                          />
                        </Table.Td>
                        {/* <Table.Td>
                          <NumberFormatter
                            value={analyst.pending}
                            decimalSeparator=","
                            thousandSeparator="."
                          />
                        </Table.Td> */}
                        <Table.Td>
                          <NumberFormatter
                            value={
                              Number(analyst.genuine) +
                              Number(analyst.watchlist) +
                              Number(analyst.fraud) +
                              Number(analyst.remind)
                            }
                            decimalSeparator=","
                            thousandSeparator="."
                          />
                        </Table.Td>
                        <Table.Td>
                          <Text>{`${analyst.falsePositive}%`}</Text>
                        </Table.Td>
                      </Table.Tr>
                    );
                  })}
                </Table.Tbody>
              </Table>
            </Box>
          </ScrollArea>
        </Box>
        <Box p={24}>
          {/* <BasePagination
            pageable={getUserAccountParameterPagination}
            query={getUserAccountQuery}
          /> */}
        </Box>
      </Card>
    </>
  );
}
