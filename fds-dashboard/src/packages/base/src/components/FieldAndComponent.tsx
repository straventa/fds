import { Box, Skeleton, Text } from "@mantine/core";
export default function FieldAndComponent({
  field,
  value,
  isLoading,
}: {
  field: string;
  value: React.ReactNode;
  isLoading?: boolean;
}) {
  return (
    <>
      <Box>
        <Text fw={400} c={"#64748B"} fz={14}>
          {field}
        </Text>
        <Box>
          {isLoading ? <Skeleton radius={"md"} w={"50%"} h="20px" /> : value}
        </Box>
      </Box>
    </>
  );
}
