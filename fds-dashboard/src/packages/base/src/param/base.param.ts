export type BaseParam = {
  page: number;
  size: number;
  sort: string;
  order: "asc" | "desc";
  totalItems?: number;
};
