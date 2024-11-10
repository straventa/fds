import type { BaseDateParam } from "@/packages/base/src/param/base-date.param";
import { useState } from "react";

export type BaseDateFunction = {
  startDate: Date;
  endDate: Date;

  setStartDate: (date: Date) => void;
  setEndDate: (date: Date) => void;
  setValue: (value: { startDate: Date; endDate: Date }) => void;
};

export function useBaseDate({
  startDate = new Date(new Date().setDate(new Date().getDate() - 30)),
  endDate = new Date(),
}: BaseDateParam): BaseDateFunction {
  const [sd, setStartDate] = useState(startDate);
  const [ed, setEndDate] = useState(endDate);

  const setValue = ({
    startDate,
    endDate,
  }: {
    startDate: Date;
    endDate: Date;
  }) => {
    setStartDate(startDate);
    setEndDate(endDate);
  };

  return {
    startDate: sd!,
    endDate: ed!,
    setStartDate,
    setEndDate,
    setValue,
  };
}
