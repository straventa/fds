export type BasePageableDto = {
  pageable: PageableDto;
  total: number;
  last: boolean;
  totalPages: number;
  totalElements: number;
  first: boolean;
  size: number;
  number: number;
  sort: SortDto;
  numberOfElements: number;
  empty: boolean;
};

export type PageableDto = {
  page: number;
  size: number;
  sort: SortDto;
  offset: number;
  pageNumber: number;
  paged: boolean;
  unpaged: boolean;
};

export type SortDto = {
  orders: any[];
  empty: boolean;
  sorted: boolean;
  unsorted: boolean;
};
