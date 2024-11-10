"use client";
import { Skeleton, Table } from "@mantine/core";

export default function SkeletonRows({
  numOfRows,
  numOfColumns,
}: {
  numOfRows: number;
  numOfColumns: number;
}) {
  return (
    <>
      {Array.from({ length: numOfRows }).map((_, rowIndex) => (
        <Table.Tr key={rowIndex}>
          {Array.from({ length: numOfColumns }).map((_, colIndex) => (
            <Table.Td key={colIndex}>
              <Skeleton key={colIndex} height={10} mt={6} radius="xl" />
            </Table.Td>
          ))}
        </Table.Tr>
      ))}
    </>
  );
}
