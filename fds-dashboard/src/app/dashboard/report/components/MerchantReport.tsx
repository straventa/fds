"use client";

import { useGetFdsTransactionMerchantCsvQuery } from "@/packages/user-management-service/src/fds-transactions/query/get-alert-fds-transactions-report-merchant-csv.query";
import { useGetFdsTransactionMerchantExcelQuery } from "@/packages/user-management-service/src/fds-transactions/query/get-alert-fds-transactions-report-merchant-excel.query";
import { useGetFdsTransactionsReportMerchantQuery } from "@/packages/user-management-service/src/fds-transactions/query/get-fds-transactions-report-merchant.query";
import {
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
import { IconCsv, IconFileExcel } from "@tabler/icons-react";
import { BarLoader } from "react-spinners";

export default function MerchantReport({
  startDate,
  endDate,
}: {
  startDate: Date;
  endDate: Date;
}) {
  const getAlertFdsParameterMerchantQuery =
    useGetFdsTransactionsReportMerchantQuery({
      getFdsTransactionsReportAnalystParam: {
        startDate,
        endDate,
      },
    });

  const getFdsTransactionMerchantExcelQuery =
    useGetFdsTransactionMerchantExcelQuery();
  const getFdsTransactionMerchantCsvQuery =
    useGetFdsTransactionMerchantCsvQuery();

  return (
    <>
      <Flex w={"100%"} justify={"flex-end"}>
        <Button
          leftSection={<IconFileExcel />}
          loading={getFdsTransactionMerchantExcelQuery.isPending}
          onClick={() => {
            getFdsTransactionMerchantExcelQuery.mutate({
              startDate,
              endDate,
            });
          }}
          color="#4AD2F5"
        >
          Download Excel
        </Button>
        <Space w={16} />
        <Button
          leftSection={<IconCsv />}
          loading={getFdsTransactionMerchantCsvQuery.isPending}
          onClick={() => {
            getFdsTransactionMerchantCsvQuery.mutate({
              startDate,
              endDate,
            });
          }}
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
              getAlertFdsParameterMerchantQuery.isLoading ||
              getAlertFdsParameterMerchantQuery.isFetching ||
              getAlertFdsParameterMerchantQuery.isRefetching
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
                    <Table.Th>MID</Table.Th>
                    <Table.Th>Merchant Name</Table.Th>
                    <Table.Th>Member Bank</Table.Th>
                    <Table.Th>Channel</Table.Th>
                    <Table.Th>Genuine</Table.Th>
                    <Table.Th>Investigation</Table.Th>
                    <Table.Th>Watchlist</Table.Th>
                    <Table.Th>Fraud</Table.Th>
                    <Table.Th>Remind</Table.Th>
                    <Table.Th>Pending</Table.Th>
                    <Table.Th>Total</Table.Th>
                    <Table.Th>False Positive</Table.Th>
                  </Table.Tr>
                </Table.Thead>
                <Table.Tbody>
                  {getAlertFdsParameterMerchantQuery.data?.map((analyst) => {
                    return (
                      <Table.Tr key={analyst.mid}>
                        <Table.Td>{analyst.mid}</Table.Td>
                        <Table.Td>{analyst.merchantName}</Table.Td>
                        <Table.Td>{analyst.memberBank}</Table.Td>
                        <Table.Td>{analyst.channel}</Table.Td>

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
                            value={analyst.pending}
                            decimalSeparator=","
                            thousandSeparator="."
                          />
                        </Table.Td>
                        <Table.Td>
                          <NumberFormatter
                            value={analyst.total}
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
      </Card>
    </>
  );
}
