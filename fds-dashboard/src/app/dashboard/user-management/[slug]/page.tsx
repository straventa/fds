"use client";
import FieldAndComponent from "@/packages/base/src/components/FieldAndComponent";
import FieldAndValue from "@/packages/base/src/components/FieldAndValue";
import { getRiskLevelColor } from "@/packages/user-management-service/src/rule/util/rule.util";
import { useGetMeQuery } from "@/packages/user-management-service/src/user-account/query/get-me.query";
import { useGetUserAccountQuery } from "@/packages/user-management-service/src/user-account/query/get-user-account.query";
import { usePutUserAccountQuery } from "@/packages/user-management-service/src/user-account/query/put-user-account.query";
import type { UserAccountResponse } from "@/packages/user-management-service/src/user-account/response/user-account.response";
import {
  Badge,
  Box,
  Button,
  Card,
  Center,
  Divider,
  Flex,
  Group,
  Modal,
  ScrollArea,
  SimpleGrid,
  Space,
  Stack,
  Switch,
  Text,
  TextInput,
  Tooltip,
  UnstyledButton,
} from "@mantine/core";
import { useDisclosure } from "@mantine/hooks";
import { notifications } from "@mantine/notifications";
import {
  IconChevronLeft,
  IconInfoCircle,
  IconPencil,
} from "@tabler/icons-react";
import { useParams, useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import { BeatLoader } from "react-spinners";
export default function Page() {
  const userAccountId = useParams().slug as string;
  const router = useRouter();
  const [opened, { open, close }] = useDisclosure(false);
  const [userAccount, setUserAccount] = useState<UserAccountResponse>();
  const putUserAccountQuery = usePutUserAccountQuery();
  const getUserAccountQuery = useGetUserAccountQuery({ userAccountId });
  const getMeQuery = useGetMeQuery();

  function setActive(isActive: boolean, userAccountRuleId: string) {
    setUserAccount({
      ...userAccount!,
      userAccountRule: userAccount!.userAccountRule.map((e) => {
        if (e.userRuleId === userAccountRuleId) {
          return {
            ...e,
            isActive: isActive,
          };
        }
        return e;
      }),
    });
  }
  useEffect(() => {
    if (getUserAccountQuery.isError) {
      router.back();
      notifications.show({
        title: "Error",
        message: getUserAccountQuery.error?.message,
        color: "red",
      });
    }
  }, [getUserAccountQuery.isError]);

  useEffect(() => {
    if (getUserAccountQuery.isSuccess) {
      setUserAccount(getUserAccountQuery.data);
    }
  }, [getUserAccountQuery.isSuccess]);

  useEffect(() => {
    if (putUserAccountQuery.isSuccess) {
      notifications.show({
        title: "Success",
        message: "User Account updated successfully",
        color: "green",
      });
      close();
    }
  }, [putUserAccountQuery.isSuccess]);
  useEffect(() => {
    if (putUserAccountQuery.isError) {
      notifications.show({
        title: "Error",
        message: putUserAccountQuery.error?.message,
        color: "red",
      });
    }
  }, [putUserAccountQuery.isError]);

  if (userAccount === undefined) {
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
        title="Edit User"
        size={"xl"}
        radius={"md"}
      >
        <Box>
          <TextInput
            label="Username"
            placeholder="Username"
            value={userAccount?.username ?? ""}
            onChange={(e) => {
              setUserAccount({
                ...userAccount,
                username: e.target.value,
              });
            }}
            required
          />
          <Space h={16} />
          <TextInput
            label="Email"
            placeholder="Email"
            value={userAccount?.email ?? ""}
            onChange={(e) => {
              setUserAccount({
                ...userAccount,
                email: e.target.value,
              });
            }}
            required
          />
          <Space h={16} />
          <Text fw={400} fz={14}>
            Is User Locked
          </Text>
          <Space h={4} />
          <Switch
            size="md"
            color="#4AD2F5"
            label=""
            checked={userAccount.isLocked}
            onChange={() => {
              setUserAccount({
                ...userAccount,
                isLocked: false,
              });
            }}
          />
          <Space h={16} />
          <Divider />
          <Space h={16} />{" "}
          <ScrollArea h={"500px"}>
            <Flex justify={"space-between"} align={"center"}>
              <Badge fz={16} fw={500} color="red">
                High Risk
              </Badge>
              <Switch
                w={200}
                color="#4AD2F5"
                label="All High Risk"
                onChange={(value) => {
                  setUserAccount({
                    ...userAccount,
                    userAccountRule: userAccount!.userAccountRule.map((e) => {
                      if (e.rule.riskLevel.toLowerCase() === "high risk") {
                        return {
                          ...e,
                          isActive: value.currentTarget.checked,
                        };
                      }
                      return e;
                    }),
                  });
                }}
                checked={userAccount!.userAccountRule
                  .filter((e) => e.rule.riskLevel.toLowerCase() === "high risk")
                  .every((e) => e.isActive === true)}
              />
            </Flex>
            <Card>
              {userAccount!.userAccountRule.map((e) => {
                if (
                  e.rule.riskLevel.toLocaleLowerCase().includes("high risk")
                ) {
                  return (
                    <Flex align={"center"} key={e.userRuleId}>
                      <Switch
                        color="#4AD2F5"
                        label={e.rule.ruleName}
                        checked={e.isActive}
                        onChange={(value) => {
                          setActive(value.currentTarget.checked, e.userRuleId);
                        }}
                        my={8}
                      />
                      <Space w={8} />
                      <Tooltip label={e.rule.ruleDescription}>
                        <IconInfoCircle color="gray" size={16} />
                      </Tooltip>
                    </Flex>
                  );
                }
              })}
            </Card>
            <Space h={16} />
            <Divider />
            <Space h={16} />
            <Flex justify={"space-between"} align={"center"}>
              <Badge fz={16} fw={500} color="yellow">
                Medium Risk
              </Badge>
              <Switch
                w={200}
                color="#4AD2F5"
                label="All Medium Risk"
                onChange={(value) => {
                  setUserAccount({
                    ...userAccount,
                    userAccountRule: userAccount!.userAccountRule.map((e) => {
                      if (e.rule.riskLevel.toLowerCase() === "medium risk") {
                        return {
                          ...e,
                          isActive: value.currentTarget.checked,
                        };
                      }
                      return e;
                    }),
                  });
                }}
                checked={userAccount!.userAccountRule
                  .filter(
                    (e) => e.rule.riskLevel.toLowerCase() === "medium risk"
                  )
                  .every((e) => e.isActive === true)}
              />
            </Flex>
            <Card>
              {userAccount!.userAccountRule.map((e) => {
                if (
                  e.rule.riskLevel.toLocaleLowerCase().includes("medium risk")
                ) {
                  return (
                    <Flex align={"center"} key={e.userRuleId}>
                      <Switch
                        color="#4AD2F5"
                        label={e.rule.ruleName}
                        checked={e.isActive}
                        onChange={(value) => {
                          setUserAccount({
                            ...userAccount,
                            userAccountRule: userAccount!.userAccountRule.map(
                              (rule) => {
                                if (rule.userRuleId === e.userRuleId) {
                                  return {
                                    ...rule,
                                    isActive: value.currentTarget.checked,
                                  };
                                }
                                return rule;
                              }
                            ),
                          });
                        }}
                        my={8}
                      />
                      <Space w={8} />
                      <Tooltip label={e.rule.ruleDescription}>
                        <IconInfoCircle color="gray" size={16} />
                      </Tooltip>
                    </Flex>
                  );
                }
              })}
            </Card>
            <Space h={16} />
            <Divider />
            <Space h={16} />
            <Flex justify={"space-between"} align={"center"}>
              <Badge fz={16} fw={500} color="green">
                Low Risk
              </Badge>
              <Switch
                w={200}
                color="#4AD2F5"
                label="All Low Risk"
                onChange={(value) => {
                  setUserAccount({
                    ...userAccount,
                    userAccountRule: userAccount!.userAccountRule.map((e) => {
                      if (e.rule.riskLevel.toLowerCase() === "low risk") {
                        return {
                          ...e,
                          isActive: value.currentTarget.checked,
                        };
                      }
                      return e;
                    }),
                  });
                }}
                checked={userAccount!.userAccountRule
                  .filter((e) => e.rule.riskLevel.toLowerCase() === "low risk")
                  .every((e) => e.isActive === true)}
              />
            </Flex>
            <Card>
              {userAccount!.userAccountRule.map((e) => {
                if (e.rule.riskLevel.toLocaleLowerCase().includes("low risk")) {
                  return (
                    <Flex align={"center"} key={e.userRuleId}>
                      <Switch
                        color="#4AD2F5"
                        label={e.rule.ruleName}
                        checked={e.isActive}
                        onChange={(value) => {
                          setUserAccount({
                            ...userAccount,
                            userAccountRule: userAccount!.userAccountRule.map(
                              (rule) => {
                                if (rule.userRuleId === e.userRuleId) {
                                  return {
                                    ...rule,
                                    isActive: value.currentTarget.checked,
                                  };
                                }
                                return rule;
                              }
                            ),
                          });
                        }}
                        my={8}
                      />
                      <Space w={8} />
                      <Tooltip label={e.rule.ruleDescription}>
                        <IconInfoCircle color="gray" size={16} />
                      </Tooltip>
                    </Flex>
                  );
                }
              })}
            </Card>
          </ScrollArea>
          <Space h={16} />
          <Space h={16} />{" "}
          <Button
            onClick={() => {
              putUserAccountQuery.mutate({
                userAccountId: userAccount!.userAccountId,
                userAccountRequest: userAccount,
              });
            }}
            loading={putUserAccountQuery.isPending}
            my={8}
            color="#4AD2F5"
          >
            Save
          </Button>
        </Box>
      </Modal>
      <Box>
        <UnstyledButton
          onClick={() => router.push("/dashboard/user-management")}
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
              User Account ID : {userAccount!.userAccountId}
            </Text>
            <Space h={12} />
            <Badge size="xl" color="#4AD2F5">
              {userAccount!.role.at(0).roleName ?? ""}
            </Badge>
          </Box>
          {getMeQuery.isLoading ||
          getMeQuery.data === undefined ||
          getMeQuery.data.role.at(0).roleName === "USER" ? null : (
            <Button
              onClick={open}
              leftSection={<IconPencil size={16} />}
              loading={getUserAccountQuery.isLoading}
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
            field={"User Account ID"}
            value={userAccount?.userAccountId ?? ""}
            isLoading={getUserAccountQuery.isLoading}
          />
          <FieldAndValue
            field={"Username"}
            value={userAccount?.username ?? ""}
            isLoading={getUserAccountQuery.isLoading}
          />
          <FieldAndValue
            field={"Email"}
            value={userAccount?.email ?? ""}
            isLoading={getUserAccountQuery.isLoading}
          />
          <FieldAndValue
            field={"Is Locked?"}
            value={userAccount?.isLocked ? "True" : "False"}
            isLoading={getUserAccountQuery.isLoading}
          />
          <FieldAndComponent
            field="Role"
            value={
              <Group>
                {userAccount!.role.map((role) => (
                  <Badge key={role.roleId} color="#4AD2F5">
                    {role.roleName}
                  </Badge>
                ))}
              </Group>
            }
          />
          <FieldAndComponent
            field="Risk Alert"
            value={
              <Stack>
                {userAccount!.userAccountRule.map((e) => {
                  if (e.isActive) {
                    return (
                      <Tooltip
                        label={e.rule.ruleDescription}
                        key={e.userRuleId}
                      >
                        <Badge color={getRiskLevelColor(e.rule.riskLevel)}>
                          {e.rule.ruleName}
                        </Badge>
                      </Tooltip>
                    );
                  }
                })}
              </Stack>
            }
          />
        </SimpleGrid>
      </Card>
      <Space h={32} />
    </Box>
  );
}
