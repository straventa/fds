"use client";
import RuleTable from "@/packages/user-management-service/src/rule/components/RuleTable";
import { Space, Text } from "@mantine/core";

export default function Page() {
  return (
    <>
      <Text fz={28} fw={500}>
        Rule Management
      </Text>
      <Space h={8} />
      <Text fz={16} fw={400}>
        Manage your rules for fraud detection system
      </Text>
      <Space h={24} />
      <RuleTable />
    </>
  );
}
