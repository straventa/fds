export type BasePageableResponse = {
  pageable: PageableResponse;
  total: number;
  last: boolean;
  totalPages: number;
  totalElements: number;
  first: boolean;
  size: number;
  number: number;
  sort: SortResponse;
  numberOfElements: number;
  empty: boolean;
};

export type PageableResponse = {
  page: number;
  size: number;
  sort: SortResponse;
  offset: number;
  pageNumber: number;
  paged: boolean;
  unpaged: boolean;
};

export type SortResponse = {
  orders: any[];
  empty: boolean;
  sorted: boolean;
  unsorted: boolean;
};
