// import type { BaseParam } from "@/packages/base/param/base.param";
import type { BaseParam } from "@/packages/base/src/param/base.param";
import { useState } from "react";

export type BasePaginationFunction = {
  page: number;
  size: number;
  sort: string;
  order: "asc" | "desc";
  totalItems: number;

  setTotalItems: (totalItems: number) => void;
  setPage: (page: number) => void;
  setPageSize: (size: number) => void;
  nextPage: () => void;
  firstPage: () => void;
  previousPage: () => void;
  lastPage: () => void;
  setOrder: (order: "asc" | "desc") => void;
  setSort: (sort: string) => void;
  setSortDesc: () => void;
  setSortAsc: () => void;
};

export const useBasePagination = ({
  totalItems = 0,
  page = 1,
  size = 10,
  sort = "createdDate",
  order = "desc",
}: BaseParam): BasePaginationFunction => {
  const [currentPage, setCurrentPage] = useState(page);
  const [currentPageSize, setCurrentPageSize] = useState(size);
  const [currentTotalItems, setCurrentTotalItems] = useState(totalItems);
  const [currentSort, setCurrentSort] = useState(sort);
  const [currentOrder, setCurrentOrder] = useState(order);

  const totalPages = Math.ceil(totalItems / size);

  // const startItemIndex = (currentPage - 1) * size;
  // const endItemIndex = Math.min(startItemIndex + size - 1, totalItems - 1);

  const setPage = (page: number) => {
    setCurrentPage(page);
  };

  const nextPage = () => setPage(currentPage + 1);

  const prevPage = () => setPage(currentPage - 1);

  const firstPage = () => setPage(1);

  const lastPage = () => setPage(totalPages);

  const setPageSize = (size: number) => {
    setCurrentPageSize(size);
  };

  const setTotalItems = (totalItems: number) => {
    setCurrentTotalItems(totalItems);
  };

  const setSort = (sort: string) => {
    setCurrentSort(sort);
  };

  const setOrder = (order: "asc" | "desc") => {
    setCurrentOrder(order);
  };

  const setSortAsc = () => {
    setCurrentOrder("asc");
  };

  const setSortDesc = () => {
    setCurrentOrder("desc");
  };

  return {
    page: currentPage,
    size: currentPageSize,
    totalItems: currentTotalItems,
    setTotalItems,
    setPage,
    setPageSize,
    nextPage,
    firstPage,
    previousPage: prevPage,
    lastPage,
    sort: currentSort,
    order: currentOrder,
    setOrder,
    setSort,
    setSortAsc,
    setSortDesc,
  };
};
