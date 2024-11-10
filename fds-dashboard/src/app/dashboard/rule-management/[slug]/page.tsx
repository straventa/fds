"use client";
import FieldAndComponent from "@/packages/base/src/components/FieldAndComponent";
import FieldAndValue from "@/packages/base/src/components/FieldAndValue";
import type { RuleDto } from "@/packages/user-management-service/src/rule/dto/rule.dto";
import { useGetRuleStatisticsQuery } from "@/packages/user-management-service/src/rule/query/get-rule-statistics.query";
import { useGetRuleQuery } from "@/packages/user-management-service/src/rule/query/get-rule.query";
import { usePutRuleQuery } from "@/packages/user-management-service/src/rule/query/put-rule.query";
import { getRiskLevelColor } from "@/packages/user-management-service/src/rule/util/rule.util";
import { useGetMeQuery } from "@/packages/user-management-service/src/user-account/query/get-me.query";
import {
  Badge,
  Box,
  Button,
  Card,
  Center,
  Divider,
  Flex,
  Modal,
  Select,
  SimpleGrid,
  Space,
  Stack,
  Switch,
  Text,
  Textarea,
  TextInput,
  UnstyledButton,
} from "@mantine/core";
import { DatePickerInput } from "@mantine/dates";
import { useDisclosure } from "@mantine/hooks";
import { notifications } from "@mantine/notifications";
import { IconChevronLeft, IconReload } from "@tabler/icons-react";
import { useParams, useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import { BeatLoader } from "react-spinners";
export default function Page() {
  const ruleId = useParams().slug as string;
  const router = useRouter();
  const [opened, { open, close }] = useDisclosure(false);
  const [rule, setRule] = useState<RuleDto>();
  const getRuleQuery = useGetRuleQuery({ ruleId });
  const ptRuleQuery = usePutRuleQuery();
  const getMeQuery = useGetMeQuery();
  const [startDate, setStartDate] = useState<Date>(
    new Date(new Date().setDate(new Date().getDate() - 7))
  );
  const [endDate, setEndDate] = useState<Date>(new Date());

  const getRuleStatisticsQuery = useGetRuleStatisticsQuery({
    ruleId,
    startDate: startDate,
    endDate: endDate,
  });
  useEffect(() => {
    if (getRuleQuery.isError) {
      router.back();
      notifications.show({
        title: "Error",
        message: getRuleQuery.error?.message,
        color: "red",
      });
    }
  }, [getRuleQuery.isError]);
  useEffect(() => {
    if (getRuleQuery.isSuccess) {
      setRule(getRuleQuery.data);
    }
  }, [getRuleQuery.isSuccess]);

  useEffect(() => {
    if (
      startDate !== null &&
      endDate !== null &&
      startDate !== undefined &&
      endDate !== undefined
    ) {
      getRuleStatisticsQuery.refetch();
    }
  }, [startDate, endDate]);

  useEffect(() => {
    if (ptRuleQuery.isSuccess) {
      notifications.show({
        title: "Success",
        message: "Rule updated successfully",
        color: "green",
      });
      close();
    } else if (ptRuleQuery.isError) {
      notifications.show({
        title: "Error",
        message: ptRuleQuery.error?.message,
        color: "red",
      });
    }
  }, [ptRuleQuery.isSuccess, ptRuleQuery.isError]);

  if (rule === undefined) {
    return (
      <Center my={"200"}>
        <BeatLoader size={48} color="#4AD2F5" />
      </Center>
    );
  }
  return (
    <Box>
      <Modal
        opened={opened}
        onClose={close}
        title="Edit Rule"
        size={"xl"}
        radius={"md"}
      >
        <Box>
          <TextInput
            label="Rule Name"
            placeholder="Rule Name"
            value={rule.ruleName}
            onChange={(e) => {
              setRule({
                ...rule,
                ruleName: e.target.value,
              });
            }}
            required
          />
          <Divider />
          <Space h={16} />
          <Textarea
            label="Rule Description"
            placeholder="Rule Description"
            value={rule.ruleDescription}
            onChange={(e) => {
              setRule({
                ...rule,
                ruleDescription: e.target.value,
              });
            }}
            required
          />
          <Divider />
          <Space h={16} />
          <Select
            label="Risk Level"
            placeholder="Risk Level"
            value={rule.riskLevel}
            onChange={(_value, option) => {
              setRule({
                ...rule,
                riskLevel: option.value,
              });
            }}
            required
            data={[
              { value: "High Risk", label: "High Risk" },
              { value: "Medium Risk", label: "Medium Risk" },
              { value: "Low Risk", label: "Low Risk" },
            ]}
          />
          <Divider />
          <Space h={16} />
          <Select
            label="Source Data"
            placeholder="Source Data"
            value={rule.sourceData}
            onChange={(_value, option) => {
              setRule({
                ...rule,
                sourceData: option.value,
              });
            }}
            required
            data={[
              { value: "ISO", label: "ISO" },
              { value: "JSON", label: "JSON" },
            ]}
          />
          <Divider />
          <Space h={16} />
          <Text fz={16} fw={400}>
            Assigned Queue
          </Text>
          {rule.userAccountRule.map((userAccountRule) => (
            <>
              <Space h={16} />
              <Flex justify={"space-between"} align={"center"}>
                <Flex align={"center"} gap={8}>
                  <Switch
                    checked={userAccountRule.isActive}
                    onChange={() => {
                      setRule({
                        ...rule,
                        userAccountRule: rule.userAccountRule.map((u) =>
                          u.userAccount.userAccountId ===
                          userAccountRule.userAccount.userAccountId
                            ? {
                                ...u,
                                isActive: !userAccountRule.isActive,
                              }
                            : u
                        ),
                      });
                    }}
                  />
                  <Space w={8} />
                  <Flex
                    key={userAccountRule.userAccount.userAccountId}
                    justify={"flex-start"}
                    align={"center"}
                    gap={15}
                  >
                    <Text miw={100} fw={500}>
                      {userAccountRule.userAccount.username}
                    </Text>
                    <Text c={"gray"} fw={400}>
                      {userAccountRule.userAccount.email}
                    </Text>
                  </Flex>
                </Flex>
              </Flex>
              <Space h={16} />
              <Divider />
              <Space h={16} />
            </>
          ))}
          <Button
            onClick={() => {
              ptRuleQuery.mutate({
                ruleId: rule.ruleId,
                ruleDto: rule,
              });
            }}
            loading={ptRuleQuery.isPending}
            my={8}
            color="#4AD2F5"
          >
            Save
          </Button>
        </Box>
      </Modal>
      <Box>
        <UnstyledButton
          onClick={() => router.push("/dashboard/rule-management")}
        >
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
              Rule Name : {rule.ruleName}
            </Text>
            <Space h={12} />
            <Badge size="xl" color={getRiskLevelColor(rule.riskLevel)}>
              {rule.riskLevel}
            </Badge>
          </Box>
          {getMeQuery.isLoading ||
          getMeQuery.data === undefined ||
          getMeQuery.data?.role?.at(0)?.roleId === "USER" ? null : (
            <Button
              onClick={open}
              leftSection={<IconChevronLeft size={16} />}
              loading={getRuleQuery.isLoading}
              color="#4AD2F5"
            >
              Edit
            </Button>
          )}
        </Flex>
      </Card>
      <Space h={32} />
      <Card shadow="xs" radius="md">
        <Text fz={20} fw={400}>
          User Account Details
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
            field={"Rule ID"}
            value={rule?.ruleId ?? ""}
            isLoading={getRuleQuery.isLoading}
          />
          <FieldAndValue
            field={"Rule Name"}
            value={rule?.ruleName ?? ""}
            isLoading={getRuleQuery.isLoading}
          />
          <FieldAndValue
            field={"Rule Description"}
            value={rule?.ruleDescription ?? ""}
            isLoading={getRuleQuery.isLoading}
          />
          <FieldAndValue
            field={"Source Data"}
            value={rule?.sourceData ?? ""}
            isLoading={getRuleQuery.isLoading}
          />
          <FieldAndValue
            field={"Risk Level"}
            value={rule?.riskLevel ?? ""}
            isLoading={getRuleQuery.isLoading}
          />
          <FieldAndComponent
            field={"Assigned User Accounts"}
            value={
              <Stack>
                {rule.userAccountRule.map((userAccountRule) => {
                  if (userAccountRule.isActive) {
                    return (
                      <Flex
                        key={userAccountRule.userAccount.userAccountId}
                        justify={"flex-start"}
                        align={"center"}
                        gap={15}
                      >
                        <Text miw={100} fw={500}>
                          {userAccountRule.userAccount.username}
                        </Text>
                        <Text c={"gray"} fw={400}>
                          {userAccountRule.userAccount.email}
                        </Text>
                      </Flex>
                    );
                  }
                })}
              </Stack>
            }
            isLoading={getRuleQuery.isLoading}
          />
        </SimpleGrid>
      </Card>
      <Space h={32} />
      <Card shadow="xs" radius="md">
        <Flex justify={"space-between"} align={"center"}>
          <Text fz={20} fw={400}>
            Rule Statistics
          </Text>
          <Flex align={"center"} gap={8}>
            <Button
              leftSection={<IconReload size={16} />}
              onClick={() => {
                getRuleStatisticsQuery.refetch();
              }}
              loading={
                getRuleStatisticsQuery.isLoading ||
                getRuleStatisticsQuery.isFetching
              }
              color="#4AD2F5"
            >
              Refresh
            </Button>
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
                // getAlertFdsParameterPagination.setValue({
                //   startDate: value[0] as Date,
                //   endDate: value[1] as Date,
                // });
              }}
            />
          </Flex>
        </Flex>
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
            field={"Alert Total"}
            value={getRuleStatisticsQuery?.data?.alertTotal.toString() ?? ""}
            isLoading={
              getRuleStatisticsQuery.isLoading ||
              getRuleStatisticsQuery.isFetching
            }
          />{" "}
          <FieldAndValue
            field={"Alert Waiting"}
            value={getRuleStatisticsQuery?.data?.alertWaiting.toString() ?? ""}
            isLoading={
              getRuleStatisticsQuery.isLoading ||
              getRuleStatisticsQuery.isFetching
            }
          />
          <FieldAndValue
            field={"Alert Reviewed"}
            value={getRuleStatisticsQuery?.data?.alertReviewed.toString() ?? ""}
            isLoading={
              getRuleStatisticsQuery.isLoading ||
              getRuleStatisticsQuery.isFetching
            }
          />
          <FieldAndValue
            field={"% Alert reviewed/Alert waiting"}
            value={
              ((
                ((getRuleStatisticsQuery?.data?.alertReviewed ?? 0) /
                  (getRuleStatisticsQuery?.data?.alertWaiting ?? 1)) *
                100
              ).toFixed(2) ?? "") + "%"
            }
            isLoading={
              getRuleStatisticsQuery.isLoading ||
              getRuleStatisticsQuery.isFetching
            }
          />
        </SimpleGrid>
      </Card>
      <Space h={32} />
    </Box>
  );
}
