import { Box, Collapse, Flex, Group, Space, Stack, Text } from "@mantine/core";
import { useDisclosure } from "@mantine/hooks";
import { IconChevronDown, IconChevronUp } from "@tabler/icons-react";
import { usePathname, useRouter } from "next/navigation";

export type NavbarItemProps = {
  title: string;
  icon?: React.ReactNode;
  href: string;
  active?: boolean;
  toggle?: () => void;
  bg?: string;
  children: NavbarItemProps[];
};

export default function NavbarItem({
  title,
  icon,
  href,
  active,
  children,
}: NavbarItemProps) {
  const pathname = usePathname();
  const [opened, { toggle: tp }] = useDisclosure(false);

  const router = useRouter();
  return (
    <Flex
      style={{
        cursor: "pointer",
      }}
      onClick={() => {
        if (children?.length === 0) {
          router.push(href);
        } else {
          tp();
        }
      }}
    >
      <Box
        w={"10px"}
        h={"100%"}
        bg={active ? "#4AD2F5" : "white"}
        style={{
          borderTopRightRadius: 6,
          borderBottomRightRadius: 6,
        }}
      />
      <Space w={16} />
      <Group
        py={14}
        pr={16}
        pl={32}
        bg={active ? "#4AD2F5" : "white"}
        style={{
          borderTopLeftRadius: 6,
          borderBottomLeftRadius: 6,
        }}
        w={"100%"}
      >
        <Flex align="center" justify={"space-between"}>
          <Box
            style={{
              borderRadius: 6,
            }}
            bg={active ? "#4AD2F5" : "white"}
          >
            <Box
              c={active ? "white" : "black"}
              pt={8}
              pb={8}
              pl={12}
              pr={12}
              style={{
                borderRadius: 6,
              }}
              bg={active ? "#4AD2F5" : "white"}
            >
              <Flex align="center" justify={"space-between"}>
                <Group>
                  {icon ?? <Box w="25px" />}
                  <Text fz={16} fw={"600"}>
                    {title}
                  </Text>
                </Group>
                {children?.length > 0 ? (
                  opened ? (
                    <IconChevronUp />
                  ) : (
                    <IconChevronDown />
                  )
                ) : null}
              </Flex>
            </Box>
          </Box>
          {children?.length > 0 && (
            <Collapse in={opened}>
              <Stack>
                {children?.map((item, index) => (
                  <NavbarItem
                    key={index}
                    title={item.title}
                    icon={item.icon}
                    href={item.href}
                    active={item.href === pathname}
                    toggle={tp}
                    // eslint-disable-next-line react/no-children-prop
                    children={item.children}
                  />
                ))}
              </Stack>
            </Collapse>
          )}
        </Flex>
      </Group>
    </Flex>
  );
  // return (
  //   <Accordion.Item
  //     value="1"
  //     onClick={() => {
  //       if (isParent) {
  //         router.push(href);
  //         if (toggle) {
  //           toggle();
  //         }
  //       }
  //     }}
  //   >
  //     <Accordion.Control>
  //       <Box
  //         pt={8}
  //         pb={8}
  //         pl={12}
  //         pr={12}
  //         style={{
  //           borderRadius: 6,
  //         }}
  //         bg={active ? "#F9FAFB" : "white"}
  //       >
  //         <Flex align="center">
  //           <Group>
  //             {icon}
  //             <Text fz={16} fw={"600"}>
  //               {title}
  //             </Text>
  //           </Group>
  //         </Flex>
  //       </Box>
  //     </Accordion.Control>
  //     {isParent && (
  //       <Accordion.Panel>
  //         {children?.map((item, index) => (
  //           <NavbarItem
  //             key={index}
  //             title={item.title}
  //             icon={item.icon}
  //             href={item.href}
  //             active={item.href === router.pathname}
  //             isParent={item.isParent}
  //             children={item.children}
  //           />
  //         ))}
  //       </Accordion.Panel>
  //     )}
  //   </Accordion.Item>
  // );
}
