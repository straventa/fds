"use client";
import FieldAndValue from "@/packages/base/src/components/FieldAndValue";
import { TimeUtil } from "@/packages/base/src/util/time.util";
import { useGetMeQuery } from "@/packages/user-management-service/src/user-account/query/get-me.query";
import {
  Box,
  Button,
  Card,
  Flex,
  SimpleGrid,
  Space,
  Text,
  Title,
  Tooltip,
} from "@mantine/core";
import { useRouter } from "next/navigation";

export default function Page() {
  const getMeQuery = useGetMeQuery();
  const router = useRouter();
  return (
    <Box>
      <Title order={2}>Profile</Title>
      <Text c={"gray"}>Details about your user profile</Text>
      <Space h={24} />
      <Card shadow="xs" radius={"md"}>
        <Text fz={20} fw={400}>
          Profile Details
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
            field={"ID"}
            value={getMeQuery.data?.userAccountId ?? ""}
            isLoading={getMeQuery.isLoading || getMeQuery.isRefetching}
          />
          <FieldAndValue
            field={"Username"}
            value={getMeQuery.data?.username ?? ""}
            isLoading={getMeQuery.isLoading || getMeQuery.isRefetching}
          />
          <FieldAndValue
            field={"Email"}
            value={getMeQuery.data?.email ?? ""}
            isLoading={getMeQuery.isLoading || getMeQuery.isRefetching}
          />
          <FieldAndValue
            field={"Role"}
            value={getMeQuery.data?.role?.at(0)?.roleName ?? ""}
            isLoading={getMeQuery.isLoading || getMeQuery.isRefetching}
          />
        </SimpleGrid>
      </Card>
      <Space h={32} />

      <Card shadow="xs" radius={"md"}>
        <Text fz={20} fw={400}>
          Credentials Details
        </Text>
        <Space h={16} />
        <Flex justify={"space-between"} align={"center"}>
          <Tooltip label="Password should be changed atleast every 90 days.">
            <FieldAndValue
              field={"Last Password Change"}
              value={
                TimeUtil.formatTime(getMeQuery.data?.passwordDateChanged) ?? ""
              }
              isLoading={getMeQuery.isLoading || getMeQuery.isRefetching}
            />
          </Tooltip>
          <Button
            color={"#4AD2F5"}
            onClick={() => {
              router.push("/dashboard/settings/change-password");
            }}
          >
            Change Password
          </Button>
        </Flex>
      </Card>
    </Box>
  );
}
