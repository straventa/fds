import FdsTransactionsTable from "@/packages/user-management-service/src/fds-transactions/components/FdsTransactionsTable";
import { Space, Text } from "@mantine/core";
export default function Page() {
  return (
    <>
      <Text fz={28} fw={500}>
        Alert
      </Text>
      <Space h={8} />
      <Text fz={16} fw={400}>
        Alert from transactions
      </Text>
      <Space h={24} />
      {/* <AlertFdsParameterTable /> */}
      <FdsTransactionsTable />
    </>
  );
}
