"use client";

import { useGetAlertFdsTransactionAnalystCsvQuery } from "@/packages/user-management-service/src/fds-transactions/query/get-alert-fds-parameter-analyst-csv.query";
import { useGetAlertFdsTransactionAnalystExcelQuery } from "@/packages/user-management-service/src/fds-transactions/query/get-alert-fds-parameter-analyst-excel.query";
import { useGetFdsTransactionsReportAnalystQuery } from "@/packages/user-management-service/src/fds-transactions/query/get-fds-transactions-report-analyst.query";
import { getRiskLevelColor } from "@/packages/user-management-service/src/rule/util/rule.util";
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
} from "@mantine/core";
import { IconCsv, IconFileExcel } from "@tabler/icons-react";
import { BarLoader } from "react-spinners";

export default function AnalystPerformance({
  startDate,
  endDate,
}: {
  startDate: Date;
  endDate: Date;
}) {
  const getAlertFdsParameterAnalystQuery =
    useGetFdsTransactionsReportAnalystQuery({
      getFdsTransactionsReportAnalystParam: {
        startDate,
        endDate,
      },
    });

  const getAlertFdsTransactionAnalystExcelQuery =
    useGetAlertFdsTransactionAnalystExcelQuery();
  const getAlertFdsTransactionAnalystCsvQuery =
    useGetAlertFdsTransactionAnalystCsvQuery();

  return (
    <>
      <Flex w={"100%"} justify={"flex-end"}>
        <Button
          leftSection={<IconFileExcel />}
          onClick={() => {
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
                    <Table.Th>Risk Level</Table.Th>
                    <Table.Th>Analyst</Table.Th>
                    {/* <Table.Th>Pending</Table.Th> */}
                    <Table.Th>Genuine</Table.Th>
                    <Table.Th>Investigation</Table.Th>
                    <Table.Th>Watchlist</Table.Th>
                    <Table.Th>Fraud</Table.Th>
                    <Table.Th>Remind</Table.Th>
                    <Table.Th>Total</Table.Th>
                  </Table.Tr>
                </Table.Thead>
                <Table.Tbody>
                  {getAlertFdsParameterAnalystQuery.data?.map((analyst, i) => {
                    if (analyst.analyst === "0") {
                      return;
                    } else {
                      return (
                        <Table.Tr key={i}>
                          <Table.Td>
                            <Badge color={getRiskLevelColor(analyst.riskLevel)}>
                              {analyst.riskLevel}
                            </Badge>
                          </Table.Td>

                          <Table.Td>{analyst.analyst}</Table.Td>
                          {/* <Table.Td>
                            <NumberFormatter
                              value={analyst.pending}
                              decimalSeparator=","
                              thousandSeparator="."
                            />
                          </Table.Td> */}
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
                        </Table.Tr>
                      );
                    }
                  })}
                </Table.Tbody>
              </Table>
            </Box>
          </ScrollArea>
        </Box>
      </Card>
    </>
  );
}
