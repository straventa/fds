import { Button, Flex } from "@mantine/core";
import { DatePickerInput } from "@mantine/dates";
import { notifications } from "@mantine/notifications";

export default function DateRangeComponent({
  startDate,
  endDate,
  setDate,
}: {
  startDate: Date;
  endDate: Date;
  setDate: (value: { startDate: Date; endDate: Date }) => void;
}) {
  return (
    <Flex
      my={24}
      justify={{
        sm: "center",
        md: "space-between",
      }}
      direction={{
        base: "column",
        xs: "column",
        sm: "column",
        md: "row",
      }}
      gap={24}
    >
      <Button.Group>
        <Button
          variant="default"
          size={"xs"}
          disabled={
            startDate.getFullYear() ===
              new Date(
                new Date().setDate(new Date().getDate() - 1)
              ).getFullYear() &&
            startDate.getMonth() ===
              new Date(
                new Date().setDate(new Date().getDate() - 1)
              ).getMonth() &&
            startDate.getDate() ===
              new Date(new Date().setDate(new Date().getDate() - 1)).getDate()
          }
          onClick={() => {
            setDate({
              startDate: new Date(new Date().setDate(new Date().getDate() - 1)),
              endDate: new Date(),
            });
          }}
        >
          24 Hours
        </Button>
        <Button
          variant="default"
          size={"xs"}
          disabled={
            startDate.getFullYear() ===
              new Date(
                new Date().setDate(new Date().getDate() - 7)
              ).getFullYear() &&
            startDate.getMonth() ===
              new Date(
                new Date().setDate(new Date().getDate() - 7)
              ).getMonth() &&
            startDate.getDate() ===
              new Date(new Date().setDate(new Date().getDate() - 7)).getDate()
          }
          onClick={() => {
            setDate({
              startDate: new Date(new Date().setDate(new Date().getDate() - 7)),
              endDate: new Date(),
            });
          }}
        >
          7 Days
        </Button>
        <Button
          variant="default"
          size={"xs"}
          disabled={
            startDate.getFullYear() ===
              new Date(
                new Date().setDate(new Date().getDate() - 30)
              ).getFullYear() &&
            startDate.getMonth() ===
              new Date(
                new Date().setDate(new Date().getDate() - 30)
              ).getMonth() &&
            startDate.getDate() ===
              new Date(new Date().setDate(new Date().getDate() - 30)).getDate()
          }
          onClick={() => {
            setDate({
              startDate: new Date(
                new Date().setDate(new Date().getDate() - 30)
              ),
              endDate: new Date(),
            });
          }}
        >
          30 Days
        </Button>
        <Button
          variant="default"
          size={"xs"}
          disabled={
            startDate.getFullYear() ===
              new Date(
                new Date().setDate(new Date().getDate() - 365)
              ).getFullYear() &&
            startDate.getMonth() ===
              new Date(
                new Date().setDate(new Date().getDate() - 365)
              ).getMonth() &&
            startDate.getDate() ===
              new Date(new Date().setDate(new Date().getDate() - 365)).getDate()
          }
          onClick={() => {
            setDate({
              startDate: new Date(
                new Date().setDate(new Date().getDate() - 365)
              ),
              endDate: new Date(),
            });
          }}
        >
          12 Months
        </Button>
      </Button.Group>
      <DatePickerInput
        type="range"
        placeholder="Select date range"
        value={[startDate, endDate]}
        onChange={(value) => {
          if (value[0] === null && value[1] === null) {
            notifications.show({
              title: "Error",
              message: "Please select a valid date range",
              color: "red",
            });
            return;
          }
          setDate({
            startDate: value[0] as Date,
            endDate: value[1] as Date,
          });
        }}
      />
    </Flex>
  );
}
