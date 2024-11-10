import UserManagementTable from "@/packages/user-management-service/src/user-account/components/UserAccountTable";
import { Space, Text } from "@mantine/core";
export default function Page() {
  return (
    <>
      <Text fz={28} fw={500}>
        User Management
      </Text>
      <Space h={8} />
      <Text fz={16} fw={400}>
        Manage user accounts
      </Text>
      <Space h={24} />
      <UserManagementTable />
    </>
  );
}
