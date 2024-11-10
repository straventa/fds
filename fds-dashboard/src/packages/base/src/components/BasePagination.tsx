import type { BasePaginationFunction } from "@/packages/base/src/hooks/base.pagination.hooks";
import type { BasePageableResponse } from "@/packages/base/src/response/base.pageable.response";
import {
  Box,
  Flex,
  Grid,
  Group,
  NumberFormatter,
  Pagination,
  Select,
  Space,
  Text,
} from "@mantine/core";
import type { UseQueryResult } from "@tanstack/react-query";
import { useEffect } from "react";

export default function BasePagination({
  pageable,
  query,
  additionalLoading,
}: {
  pageable: BasePaginationFunction;
  query: UseQueryResult<BasePageableResponse, unknown>;
  additionalLoading?: boolean;
}) {
  const {
    setPage,
    setPageSize,
    nextPage,
    previousPage,
    // lastPage,
    page,
    size,
    // totalItems,
  } = pageable;
  const { data, isLoading, isFetching, isRefetching } = query;
  useEffect(() => {
    if (page !== data?.pageable.page) {
      if (data?.pageable.page !== undefined) {
        setPage(data.pageable.page);
      }
    }
  }, [data?.pageable.page]);

  useEffect(() => {
    if (size !== data?.pageable.size) {
      if (data?.pageable.size !== undefined) {
        setPageSize(data.pageable.size);
      }
    }
  }, [data?.pageable.size]);

  return (
    <Grid align="center" my={"sm"}>
      <Grid.Col
        span={{
          xs: 2,
          md: 2,
        }}
      >
        <Flex align={"center"}>
          <Text>{"Show"}</Text>
          <Box mr={5} />
          <Select
            value={size.toString()}
            onChange={(value) => {
              setPageSize(Number(value));
            }}
            data={[
              { label: "100", value: "100" },
              { label: "200", value: "200" },
              { label: "500", value: "500" },
              { label: "1000", value: "1000" },
            ]}
            placeholder="Size"
            radius="sm"
            w={130}
          />
          <Box ml={5} />
          <Space w={4} />
          <Text>{" of "}</Text>
          <Space w={4} />
          <NumberFormatter
            value={data?.totalElements}
            thousandSeparator={"."}
            decimalSeparator=","
          />
        </Flex>
      </Grid.Col>

      <Grid.Col
        span={{
          xs: 3,
          md: 2,
        }}
      ></Grid.Col>
      <Grid.Col
        span={{
          xs: 6,
          md: 4,
        }}
      >
        <Pagination.Root
          total={data?.totalPages || 100}
          value={page + 1}
          onChange={(value) => {
            setPage(value - 1);
          }}
          color={"#4AD2F5"}
          disabled={
            isLoading || isFetching || isRefetching || additionalLoading
          }
        >
          <Group gap={5} justify="center">
            <Pagination.First
              onClick={() => {
                setPage(0);
              }}
            />
            <Pagination.Previous
              onClick={async () => {
                previousPage();
              }}
            />
            <Pagination.Items />
            <Pagination.Next
              onClick={async () => {
                nextPage();
              }}
            />
            <Pagination.Last
              onClick={async () => {
                setPage((data?.totalPages ?? 0) - 1);
              }}
            />
          </Group>
        </Pagination.Root>
      </Grid.Col>
      <Grid.Col
        span={{
          xs: 12,
          md: 4,
        }}
      ></Grid.Col>
    </Grid>
  );
}
