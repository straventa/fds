"use client";
import BasePagination from "@/packages/base/src/components/BasePagination";
import SkeletonRows from "@/packages/base/src/components/SkeletonRows";
import { TimeUtil } from "@/packages/base/src/util/time.util";
import { useGetUserAccountRulesQuery } from "@/packages/user-management-service/src/user-account-rule/query/get-user-account-rules.query";
import { useGetUserAccountParameterPagination } from "@/packages/user-management-service/src/user-account/hooks/get-user-account-parameter.pagination";
import { useDeleteUserAccountQuery } from "@/packages/user-management-service/src/user-account/query/delete-user-account.query";
import { useGetMeQuery } from "@/packages/user-management-service/src/user-account/query/get-me.query";
import { useGetUserAccountsQuery } from "@/packages/user-management-service/src/user-account/query/get-user-accounts.query";
import { usePostUserAccountQuery } from "@/packages/user-management-service/src/user-account/query/post-user-account.query";
import { UserAccountResponse } from "@/packages/user-management-service/src/user-account/response/user-account.response";
import { getRoleColor } from "@/packages/user-management-service/src/user-account/util/user-account.util";
import {
  ActionIcon,
  Alert,
  Badge,
  Box,
  Button,
  Card,
  Divider,
  Flex,
  Modal,
  ScrollArea,
  Select,
  Space,
  Switch,
  Table,
  Text,
  TextInput,
  Tooltip,
  UnstyledButton,
} from "@mantine/core";
import { useDisclosure } from "@mantine/hooks";
import { notifications } from "@mantine/notifications";
import {
  IconArrowDown,
  IconArrowUp,
  IconCheck,
  IconEye,
  IconInfoCircle,
  IconPlus,
  IconTrash,
  IconX,
} from "@tabler/icons-react";
import { debounce } from "lodash";
import { useRouter } from "next/navigation";
import { useCallback, useEffect, useState } from "react";
import { BarLoader } from "react-spinners";

export default function UserAccountTable() {
  const router = useRouter();
  const [opened, { open, close }] = useDisclosure(false);
  const [openeedDelete, { open: openDelete, close: closeDelete }] =
    useDisclosure(false);
  const [deletedUser, setDeletedUser] = useState<UserAccountResponse | null>(
    null
  );
  const postUserAccountQuery = usePostUserAccountQuery();
  const getMeQuery = useGetMeQuery();

  const [newUserAccount, setNewUserAccount] = useState<UserAccountResponse>({
    userAccountId: "",
    username: "",
    email: "",
    // password: "Admin123!@#",
    isLocked: null,
    isPasswordSetup: null,
    role: [],
    userAccountRule: [],
    createdDate: new Date(),
    createdBy: "",
    lastModifiedDate: new Date(),
    lastModifiedBy: "",
    passwordDateChanged: new Date(),
  });
  const getUserAccountParameterPagination =
    useGetUserAccountParameterPagination({
      page: 0,
      size: 10,
      sort: "userAccountId",
      order: "asc",
      username: null,
      email: null,
      ruleName: null,
      roleName: null,
    });
  const [localEmail, setLocalEmail] = useState(
    getUserAccountParameterPagination.email ?? ""
  );
  const [localUsername, setLocalUsername] = useState(
    getUserAccountParameterPagination.username ?? ""
  );
  const getUserAccountRulesQuery = useGetUserAccountRulesQuery();
  const deleteUserAccountQuery = useDeleteUserAccountQuery();
  const getUserAccountQuery = useGetUserAccountsQuery({
    getUserAccountParam: {
      page: getUserAccountParameterPagination.page,
      size: getUserAccountParameterPagination.size,
      sort: getUserAccountParameterPagination.sort,
      order: getUserAccountParameterPagination.order,
      username: getUserAccountParameterPagination.username,
      email: getUserAccountParameterPagination.email,
      ruleName: getUserAccountParameterPagination.ruleName,
      roleName: getUserAccountParameterPagination.roleName,
    },
  });

  const columns = [
    {
      title: "Action",
      key: null,
      left: 0,
    },
    {
      title: "ID",
      key: "userAccountId",
    },
    {
      title: "Username",
      key: "username",
    },
    {
      title: "E-Mail",
      key: "email",
    },
    {
      title: "Is Locked",
      key: null,
    },
    {
      title: "Is Password Setup",
      key: null,
    },
    {
      title: "Role",
      key: null,
      width: 200,
    },
    {
      title: "Rule Count",
      key: null,
    },
    {
      title: "Created By",
      key: "createdBy",
    },
    {
      title: "Created Date",
      key: "createdDate",
    },
    {
      title: "Last Modified By",
      key: "lastModifiedBy",
    },
    {
      title: "Last Modified Date",
      key: "lastModifiedDate",
    },
  ];
  function setActive(isActive: boolean, userAccountRuleId: string) {
    setNewUserAccount({
      ...newUserAccount!,
      userAccountRule: newUserAccount!.userAccountRule.map((e) => {
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
    if (getUserAccountRulesQuery.isSuccess) {
      setNewUserAccount({
        ...newUserAccount,
        userAccountRule: getUserAccountRulesQuery.data,
      });
    }
  }, [getUserAccountRulesQuery.isSuccess]);
  const debouncedSearch = useCallback(
    debounce((value: string, type: "email" | "username") => {
      if (type === "email") {
        getUserAccountParameterPagination.setEmail(value);
      } else {
        getUserAccountParameterPagination.setUsername(value);
      }
    }, 1000),
    []
  );

  useEffect(() => {
    getUserAccountQuery.refetch();
  }, [
    getUserAccountParameterPagination.email,
    getUserAccountParameterPagination.page,
    getUserAccountParameterPagination.roleName,
    getUserAccountParameterPagination.ruleName,
    getUserAccountParameterPagination.size,
    getUserAccountParameterPagination.sort,
    getUserAccountParameterPagination.username,
    getUserAccountParameterPagination.order,
  ]);

  // useEffect(() => {
  //   if (postUserAccountQuery.isError) {
  //     notifications.show({
  //       title: "Error",
  //       message: postUserAccountQuery.error?.message,
  //       color: "red",
  //     });
  //   }
  // }, [postUserAccountQuery.isError]);

  useEffect(() => {
    if (postUserAccountQuery.isSuccess) {
      notifications.show({
        title: "Success",
        message: "User Account has been added",
        color: "green",
      });
      close();
      getUserAccountQuery.refetch();
    }
  }, [postUserAccountQuery.isSuccess]);
  useEffect(() => {
    if (deleteUserAccountQuery.isSuccess) {
      closeDelete();
      notifications.show({
        title: "Success",
        message: "User Account successfully deleted",
        color: "green",
      });
      getUserAccountQuery.refetch();
    }
  }, [deleteUserAccountQuery.isSuccess]);
  console.log("newUserAccount", newUserAccount);
  return (
    <Box>
      <Modal
        title={"Confirm Delete User"}
        opened={openeedDelete}
        onClose={closeDelete}
        size={"md"}
      >
        <Text>
          Are you sure want to delete{" "}
          <span
            style={{
              fontWeight: "bold",
            }}
          >
            {deletedUser?.email ?? ""}
          </span>
        </Text>

        <Space h={20} />
        <Flex justify={"space-between"} align={"center"}>
          <Button
            color="grey"
            onClick={closeDelete}
            loading={deleteUserAccountQuery.isPending}
          >
            Cancel
          </Button>
          <Button
            loading={deleteUserAccountQuery.isPending}
            color="red"
            onClick={() => {
              deleteUserAccountQuery.mutate(deletedUser.userAccountId);
            }}
          >
            Delete
          </Button>
        </Flex>
      </Modal>
      <Modal
        title="Add User"
        size="xl"
        padding="lg"
        opened={opened}
        onClose={close}
      >
        {postUserAccountQuery.isError && (
          <Alert
            radius={"md"}
            variant="light"
            color="red"
            p={10}
            my={10}
            icon={<IconInfoCircle />}
          >
            {postUserAccountQuery.error?.message ?? "Something went wrong"}
          </Alert>
        )}
        <TextInput
          label="Username"
          placeholder="Enter username"
          value={newUserAccount.username}
          error={
            postUserAccountQuery?.error?.errors?.find(
              (e) => e.field === "username"
            )?.defaultMessage ?? ""
          }
          onChange={(e) =>
            setNewUserAccount({ ...newUserAccount, username: e.target.value })
          }
        />
        <Space h={8} />
        <TextInput
          label="Email"
          placeholder="Enter email"
          value={newUserAccount.email}
          error={
            postUserAccountQuery?.error?.errors?.find(
              (e) => e.field === "email"
            )?.defaultMessage ?? ""
          }
          onChange={(e) =>
            setNewUserAccount({ ...newUserAccount, email: e.target.value })
          }
        />
        <Space h={8} />
        <Select
          label="Role"
          placeholder="Select role"
          data={[
            {
              label: "SUPERVISOR",
              value: "SUPERVISOR",
            },
            {
              label: "USER",
              value: "USER",
            },
          ]}
          error={
            postUserAccountQuery?.error?.errors?.find((e) => e.field === "role")
              ?.defaultMessage ?? ""
          }
          value={newUserAccount.role.at(0)?.roleId}
          onChange={(e) => {
            if (e === "SUPERVISOR") {
              setNewUserAccount({
                ...newUserAccount,
                role: [
                  {
                    roleId: e,
                    roleCode: "001",
                    roleName: e,
                    roleDescription: "Supervisor",
                    createdDate: new Date(),
                    createdBy: "",
                    lastModifiedDate: new Date(),
                    lastModifiedBy: "",
                  },
                ],
              });
            } else {
              setNewUserAccount({
                ...newUserAccount,
                role: [
                  {
                    roleId: e ?? "",
                    roleCode: "002",
                    roleName: e ?? "",
                    roleDescription: "User",
                    createdDate: new Date(),
                    createdBy: "",
                    lastModifiedDate: new Date(),
                    lastModifiedBy: "",
                  },
                ],
              });
            }
          }}
        />
        <Space h={8} />
        <Text fz={16} fw={500} mb={"xl"}>
          Rule
        </Text>
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
                setNewUserAccount({
                  ...newUserAccount,
                  userAccountRule: newUserAccount!.userAccountRule.map((e) => {
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
              checked={newUserAccount!.userAccountRule
                .filter((e) => e.rule.riskLevel.toLowerCase() === "high risk")
                .every((e) => e.isActive === true)}
            />
          </Flex>
          <Card>
            {newUserAccount!.userAccountRule.map((e) => {
              if (e.rule.riskLevel.toLocaleLowerCase().includes("high risk")) {
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
                setNewUserAccount({
                  ...newUserAccount,
                  userAccountRule: newUserAccount?.userAccountRule.map((e) => {
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
              checked={newUserAccount!.userAccountRule
                .filter((e) => e.rule.riskLevel.toLowerCase() === "medium risk")
                .every((e) => e.isActive === true)}
            />
          </Flex>
          <Card>
            {newUserAccount!.userAccountRule.map((e) => {
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
            <Badge fz={16} fw={500} color="green">
              Low Risk
            </Badge>
            <Switch
              w={200}
              color="#4AD2F5"
              label="All Low Risk"
              onChange={(value) => {
                setNewUserAccount({
                  ...newUserAccount,
                  userAccountRule: newUserAccount?.userAccountRule.map((e) => {
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
              checked={newUserAccount!.userAccountRule
                .filter((e) => e.rule.riskLevel.toLowerCase() === "low risk")
                .every((e) => e.isActive === true)}
            />
          </Flex>
          <Card>
            {newUserAccount!.userAccountRule.map((e) => {
              if (e.rule.riskLevel.toLocaleLowerCase().includes("low risk")) {
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
        </ScrollArea>
        <Space h={16} />
        {/* <MultiSelect
          label="Rule"
          placeholder="Select role"
          data={[
            {
              label: "SUPERADMIN",
              value: "SUPERADMIN",
            },
            {
              label: "USER",
              value: "USER",
            },
          ]}
          value={newUserAccount.roleId}
          onChange={(e) => {
            setNewUserAccount({ ...newUserAccount, roleId: e ?? "" });
          }}
        /> */}
        <Space h={8} />
        {getMeQuery.isLoading ||
        getMeQuery.data === undefined ||
        getMeQuery.data.role.at(0)?.roleName === "USER" ? null : (
          <Button
            color={"#4AD2F5"}
            onClick={() => {
              postUserAccountQuery.mutate(newUserAccount);
            }}
            loading={
              postUserAccountQuery.isPending
              // getUserAccountQuery.isLoading ||
              // getUserAccountQuery.isRefetching ||
              // getUserAccountQuery.isPending ||
              // deleteUserAccountQuery.isPending
            }
          >
            Add User
          </Button>
        )}
      </Modal>
      <Box my="md">
        <Flex align={"center"} justify={"space-between"}>
          <Flex gap={12} align={"center"} wrap={"wrap"}>
            <Text fz={16} fw={400}>
              Filter by
            </Text>
            <TextInput
              placeholder="Search by Username"
              value={localUsername}
              onChange={(e) =>
                // getUserAccountParameterPagination.setUsername(
                //   e.currentTarget.value
                // )
                {
                  const value = e.currentTarget.value;
                  setLocalUsername(value);
                  debouncedSearch(value, "username");
                }
              }
            />
            <TextInput
              placeholder="Search by Email"
              value={localEmail}
              onChange={(e) =>
                // getUserAccountParameterPagination.setEmail(
                //   e.currentTarget.value
                // )
                {
                  const value = e.currentTarget.value;
                  setLocalEmail(value);
                  debouncedSearch(value, "email");
                }
              }
            />
          </Flex>
          {getMeQuery.isLoading ||
          getMeQuery.data === undefined ||
          getMeQuery.data.role.at(0)?.roleName === "USER" ? null : (
            <Button
              leftSection={<IconPlus />}
              color={"#4AD2F5"}
              onClick={() => {
                open();
              }}
              loading={
                postUserAccountQuery.isPending ||
                getUserAccountQuery.isLoading ||
                getUserAccountQuery.isRefetching ||
                getUserAccountQuery.isPending ||
                deleteUserAccountQuery.isPending
              }
            >
              Add User
            </Button>
          )}
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
                value={getUserAccountParameterPagination.paymentGatewayId}
                onChange={(_value, option) =>
                  getUserAccountParameterPagination.setPaymentGatewayId(option.value)
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
                value={getUserAccountParameterPagination.paymentProviderId}
                onChange={(_value, option) =>
                  getUserAccountParameterPagination.setPaymentProviderId(option.value)
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
                value={getUserAccountParameterPagination.transactionStatus}
                onChange={(_value, option) =>
                  getUserAccountParameterPagination.setTransactionStatus(option.value)
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
              getUserAccountQuery.isLoading ||
              getUserAccountQuery.isFetching ||
              getUserAccountQuery.isRefetching
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
                        w={e.width ?? 100}
                        pos={e.left !== undefined ? "sticky" : "static"}
                        left={e.left !== undefined ? e.left : "auto"}
                        bg={e.left !== undefined ? "#F6F6F6" : "transparent"}
                      >
                        <UnstyledButton
                          style={{
                            cursor: e.key === null ? "default" : "pointer",
                          }}
                          onClick={() => {
                            // getUserAccountParameterPagination.setValue({
                            //   sort: e.key,
                            //   order:
                            //     getUserAccountParameterPagination.sort === e.key
                            //       ? getUserAccountParameterPagination.order === "asc"
                            //         ? "desc"
                            //         : "asc"
                            //       : "asc",
                            // });
                            if (e.key !== null) {
                              getUserAccountParameterPagination.setSort(e.key);
                              getUserAccountParameterPagination.setOrder(
                                getUserAccountParameterPagination.sort === e.key
                                  ? getUserAccountParameterPagination.order ===
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
                            null ? null : getUserAccountParameterPagination.sort ===
                              e.key ? (
                              getUserAccountParameterPagination.order ===
                              "asc" ? (
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
                {getUserAccountQuery.data !== undefined ? (
                  getUserAccountQuery.data.content?.map((e, i) => {
                    return (
                      <Table.Tr key={i}>
                        <Table.Td
                          bg={"#F6F6F6"}
                          px={24}
                          py={12}
                          left={0}
                          pos={"sticky"}
                        >
                          <Flex justify={"space-between"} align={"center"}>
                            <ActionIcon
                              color={"#4AD2F5"}
                              onClick={() => {
                                router.push(
                                  `/dashboard/user-management/${e.userAccountId}`
                                );
                              }}
                              loading={
                                getUserAccountQuery.isLoading ||
                                getUserAccountQuery.isRefetching ||
                                getUserAccountQuery.isPending ||
                                deleteUserAccountQuery.isPending
                              }
                            >
                              <IconEye />
                            </ActionIcon>
                            <Space w={4} />
                            <ActionIcon
                              color={"red"}
                              onClick={() => {
                                setDeletedUser(e);
                                openDelete();
                              }}
                              loading={
                                getUserAccountQuery.isLoading ||
                                getUserAccountQuery.isRefetching ||
                                getUserAccountQuery.isPending ||
                                deleteUserAccountQuery.isPending
                              }
                            >
                              <IconTrash />
                            </ActionIcon>
                          </Flex>
                        </Table.Td>
                        <Table.Td px={24} py={12} left={100}>
                          {e.userAccountId}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          {e.username}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          {e.email}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          {e.isLocked ? (
                            <IconCheck color="green" size={32} />
                          ) : (
                            <IconX color="red" size={32} />
                          )}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          {e.isPasswordSetup ? (
                            <IconCheck color="green" size={32} />
                          ) : (
                            <IconX color="red" size={32} />
                          )}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          {e.role.map((e, i) => {
                            return (
                              <Badge
                                key={i}
                                w={100}
                                color={getRoleColor(e.roleName)}
                              >
                                {e.roleName}
                              </Badge>
                            );
                          })}
                        </Table.Td>
                        <Table.Td px={24} py={12}>
                          {
                            e.userAccountRule.filter((e) => e.isActive === true)
                              .length
                          }
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
            pageable={getUserAccountParameterPagination}
            query={getUserAccountQuery}
          />
        </Box>
      </Card>
    </Box>
  );
}
