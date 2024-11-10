import {
  Checkbox,
  Combobox,
  Divider,
  Group,
  Pill,
  PillsInput,
  ScrollArea,
  useCombobox,
} from "@mantine/core";
import { useState } from "react";

interface Item {
  key: string;
  value: string;
  isDisabled?: boolean;
}

interface GroupedData {
  group: string;
  items: Item[];
}

interface Props {
  data: GroupedData[];
  value?: string[];
  onChange?: (value: string[]) => void;
  maxWidth?: number | string;
  maxDropdownHeight?: number | string;
  maxInputHeight?: number | string;
}

export function SearchableMultiSelect({
  data,
  value: externalValue,
  onChange: externalOnChange,
  maxWidth = 400,
  maxDropdownHeight = 300,
}: Props) {
  const combobox = useCombobox({
    onDropdownClose: () => combobox.resetSelectedOption(),
    onDropdownOpen: () => combobox.updateSelectedOptionIndex("active"),
  });

  const [search, setSearch] = useState("");
  const [internalValue, setInternalValue] = useState<string[]>([]);

  const value =
    externalValue !== undefined && externalValue !== null
      ? externalValue
      : internalValue;
  const setValue = (newValue: string[]) => {
    if (externalOnChange) {
      externalOnChange(newValue);
    } else {
      setInternalValue(newValue);
    }
  };

  const itemsMap = new Map<string, Item>();
  data.forEach((group) => {
    group.items.forEach((item) => {
      itemsMap.set(item.value, item);
    });
  });

  const handleValueSelect = (val: string) =>
    setValue(
      value.includes(val) ? value.filter((v) => v !== val) : [...value, val]
    );

  const handleValueRemove = (val: string) =>
    setValue(value.filter((v) => v !== val));

  const handleGroupSelect = (groupItems: Item[]) => {
    // eslint-disable-next-line @typescript-eslint/ban-ts-comment
    //@ts-expect-error
    setValue((current) => {
      const newValues = new Set(current);
      let allGroupItemsSelected = true;

      console.log("groupitems", groupItems);
      for (const item of groupItems) {
        if (!newValues.has(item.value)) {
          allGroupItemsSelected = false;
          break;
        }
      }

      if (allGroupItemsSelected) {
        console.log("deselect");
        return current.filter(
          (value) => !groupItems.some((item) => item.value === value)
        );
      } else {
        console.log("select");

        groupItems.forEach((item) => {
          if (!item.isDisabled) {
            newValues.add(item.value);
          }
        });
        return Array.from(newValues);
      }
    });
  };

  const values = value
    .map((itemValue) => {
      const item = itemsMap.get(itemValue);
      return item ? (
        <Pill
          key={itemValue}
          withRemoveButton
          onRemove={() => handleValueRemove(itemValue)}
        >
          {item.key}
        </Pill>
      ) : null;
    })
    .filter(Boolean);

  const options = data.map((group) => {
    const filteredItems = group.items.filter((item) =>
      item.key.toLowerCase().includes(search.trim().toLowerCase())
    );

    if (filteredItems.length === 0) return null;

    const allGroupItemsSelected = filteredItems.every((item) =>
      value.includes(item.value)
    );

    const someGroupItemsSelected = filteredItems.some((item) =>
      value.includes(item.value)
    );

    return (
      <Combobox.Group key={group.group} my={"md"}>
        <Divider
          labelPosition="left"
          label={
            <Checkbox
              color={"#4AD2F5"}
              checked={allGroupItemsSelected}
              indeterminate={!allGroupItemsSelected && someGroupItemsSelected}
              onChange={() => handleGroupSelect(filteredItems)}
              label={`${group.group}`}
            />
          }
        />
        {filteredItems.map((item) => (
          <Combobox.Option
            value={item.value}
            key={item.value}
            active={value.includes(item.value)}
          >
            <Group gap="sm">
              <Checkbox
                disabled={item?.isDisabled ?? false}
                color={"#4AD2F5"}
                checked={value.includes(item.value)}
                onChange={() => handleValueSelect(item.value)}
                onClick={(e) => e.stopPropagation()}
              />
              <span>{item.key}</span>
            </Group>
          </Combobox.Option>
        ))}
      </Combobox.Group>
    );
  });

  return (
    <div
      style={{
        maxWidth: maxWidth,
        minWidth: maxWidth,
      }}
    >
      <Combobox
        store={combobox}
        onOptionSubmit={handleValueSelect}
        withinPortal={false}
      >
        <Combobox.DropdownTarget>
          <PillsInput onClick={() => combobox.openDropdown()}>
            <ScrollArea mah={100}>
              <Pill.Group>
                {`${values.length}`}
                <Divider orientation="vertical" />
                <Combobox.EventsTarget>
                  <PillsInput.Field
                    onFocus={() => combobox.openDropdown()}
                    onBlur={() => combobox.closeDropdown()}
                    value={search}
                    placeholder="Search for value"
                    onChange={(event) => {
                      combobox.updateSelectedOptionIndex();
                      setSearch(event.currentTarget.value);
                    }}
                    onKeyDown={(event) => {
                      if (event.key === "Backspace" && search.length === 0) {
                        event.preventDefault();
                        handleValueRemove(value[value.length - 1]);
                      }
                    }}
                    style={{
                      minHeight: "auto", // Prevent input from expanding
                      height: "auto",
                    }}
                  />
                </Combobox.EventsTarget>
              </Pill.Group>
            </ScrollArea>
          </PillsInput>
        </Combobox.DropdownTarget>

        <Combobox.Dropdown>
          <Combobox.Options
            style={{
              maxHeight: maxDropdownHeight,
              overflow: "auto",
            }}
          >
            {options.some((option) => option !== null) ? (
              options
            ) : (
              <Combobox.Empty>Nothing found...</Combobox.Empty>
            )}
          </Combobox.Options>
        </Combobox.Dropdown>
      </Combobox>
    </div>
  );
}
