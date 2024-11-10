import { Box, Skeleton, Text } from "@mantine/core";
export default function FieldAndValue({
  field,
  value,
  isLoading,
}: {
  field: string;
  value: string;
  isLoading?: boolean;
}) {
  return (
    <>
      <Box>
        <Text fw={400} c={"#64748B"} fz={14}>
          {field}
        </Text>
        {isLoading ? (
          <Skeleton radius={"md"} w={"50%"} h="20px" />
        ) : (
          <Text>
            {value === "" || value === null || value === undefined
              ? "-"
              : value}
          </Text>
        )}
      </Box>
    </>
  );
}
