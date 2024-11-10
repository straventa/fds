"use client";
import { Box, Button, Flex, Select, Space, Tabs, Text } from "@mantine/core";
import { DatePickerInput } from "@mantine/dates";
import { useMediaQuery } from "@mantine/hooks";
import { notifications } from "@mantine/notifications";
import dynamic from "next/dynamic";
import { useState } from "react";
const RuleNameDetection = dynamic(
  () => import("@/app/dashboard/report/components/RuleNameDetection")
);

const AnalystPerformance = dynamic(
  () => import("@/app/dashboard/report/components/AnalystPerformance")
);
const MerchantReport = dynamic(
  () => import("@/app/dashboard/report/components/MerchantReport")
);
export default function Page() {
  const options = [
    {
      label: "Analyst Alert Performance",
      value: "analyst-alert-performance",
    },
    {
      label: "Rule Name Detection",
      value: "rule-name-detection",
    },
    {
      label: "Merchant Detection",
      value: "merchant-detection",
    },
  ];
  const [startDate, setStartDate] = useState<Date>(
    new Date(new Date().getTime() - 7 * 24 * 60 * 60 * 1000)
  );
  const [activeTab, setActiveTab] = useState<{
    label: string;
    value: string;
  }>({
    label: "Analyst Alert Performance",
    value: "analyst-alert-performance",
  });

  const [endDate, setEndDate] = useState<Date>(new Date());

  const isMobile = useMediaQuery("(max-width: 768px)");
  return (
    <>
      <Text fz={28} fw={500}>
        Report
      </Text>
      <Space h={8} />
      <Text fz={16} fw={400}>
        View reports for fraud detection system parameters
      </Text>
      <Space h={24} />
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
          <Button
            variant="default"
            size={isMobile ? "xs" : "sm"}
            disabled={
              startDate.getFullYear() ===
                new Date(
                  new Date().setDate(new Date().getDate() - 1)
                ).getFullYear() &&
              startDate.getMonth() ===
                new Date(
                  new Date().setDate(new Date().getDate() - 1)
                ).getMonth() &&
              startDate.getDate() ===
                new Date(new Date().setDate(new Date().getDate() - 1)).getDate()
            }
            onClick={() => {
              setStartDate(
                new Date(new Date().setDate(new Date().getDate() - 1))
              );
              setEndDate(new Date());
            }}
          >
            24 Hours
          </Button>
          <Button
            variant="default"
            size={isMobile ? "xs" : "sm"}
            disabled={
              startDate.getFullYear() ===
                new Date(
                  new Date().setDate(new Date().getDate() - 7)
                ).getFullYear() &&
              startDate.getMonth() ===
                new Date(
                  new Date().setDate(new Date().getDate() - 7)
                ).getMonth() &&
              startDate.getDate() ===
                new Date(new Date().setDate(new Date().getDate() - 7)).getDate()
            }
            onClick={() => {
              setStartDate(
                new Date(new Date().setDate(new Date().getDate() - 7))
              );
              setEndDate(new Date());
            }}
          >
            7 Days
          </Button>
          <Button
            variant="default"
            size={isMobile ? "xs" : "sm"}
            disabled={
              startDate.getFullYear() ===
                new Date(
                  new Date().setDate(new Date().getDate() - 30)
                ).getFullYear() &&
              startDate.getMonth() ===
                new Date(
                  new Date().setDate(new Date().getDate() - 30)
                ).getMonth() &&
              startDate.getDate() ===
                new Date(
                  new Date().setDate(new Date().getDate() - 30)
                ).getDate()
            }
            onClick={() => {
              setStartDate(
                new Date(new Date().setDate(new Date().getDate() - 30))
              );
              setEndDate(new Date());
            }}
          >
            30 Days
          </Button>
          <Button
            variant="default"
            size={isMobile ? "xs" : "sm"}
            disabled={
              startDate.getFullYear() ===
                new Date(
                  new Date().setDate(new Date().getDate() - 365)
                ).getFullYear() &&
              startDate.getMonth() ===
                new Date(
                  new Date().setDate(new Date().getDate() - 365)
                ).getMonth() &&
              startDate.getDate() ===
                new Date(
                  new Date().setDate(new Date().getDate() - 365)
                ).getDate()
            }
            onClick={() => {
              setStartDate(
                new Date(new Date().setDate(new Date().getDate() - 365))
              );
              setEndDate(new Date());
            }}
          >
            12 Months
          </Button>
        </Button.Group>
        <DatePickerInput
          type="range"
          placeholder="Select date range"
          value={[startDate, endDate]}
          onChange={(value) => {
            if (value[0] === null && value[1] === null) {
              notifications.show({
                title: "Error",
                message: "Please select a valid date range",
                color: "red",
              });
              return;
            }
            setStartDate(value[0] as Date);
            setEndDate(value[1] as Date);
          }}
        />
      </Flex>
      <Flex w={"100%"} justify={"flex-start"} align={"center"}>
        <Text fz={16} fw={400}>
          Select report type
        </Text>
        <Space w={16} />
        <Select
          miw={300}
          data={options}
          onChange={(value) => {
            setActiveTab(
              options.find((option) => option.value === value) as {
                label: string;
                value: string;
              }
            );
          }}
          value={activeTab.value}
        />
      </Flex>

      <Box>
        <Tabs value={activeTab.value}>
          <Tabs.Panel value="analyst-alert-performance">
            <AnalystPerformance startDate={startDate} endDate={endDate} />
          </Tabs.Panel>
          <Tabs.Panel value="rule-name-detection">
            <RuleNameDetection startDate={startDate} endDate={endDate} />
          </Tabs.Panel>
          <Tabs.Panel value="merchant-detection">
            <MerchantReport startDate={startDate} endDate={endDate} />
          </Tabs.Panel>
        </Tabs>
      </Box>
    </>
  );
}
