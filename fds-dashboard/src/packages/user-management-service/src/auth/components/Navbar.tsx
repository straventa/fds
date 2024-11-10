"use client";
import customLocalStorage from "@/packages/base/src/util/local-storage.util";
import type { NavbarItemProps } from "@/packages/user-management-service/src/auth/components/NavbarItem";
import NavbarItem from "@/packages/user-management-service/src/auth/components/NavbarItem";
import { usePostLogoutQuery } from "@/packages/user-management-service/src/auth/query/post-logout.quey";
import { useGetMeQuery } from "@/packages/user-management-service/src/user-account/query/get-me.query";
import {
  ActionIcon,
  Anchor,
  AppShell,
  Box,
  Breadcrumbs,
  Burger,
  Card,
  Flex,
  Menu,
  Space,
  Stack,
  Text,
  UnstyledButton,
} from "@mantine/core";
import { useDisclosure, useMediaQuery } from "@mantine/hooks";
import { notifications } from "@mantine/notifications";
import {
  IconAlarm,
  IconArchive,
  IconReport,
  IconSettings,
  IconUser,
} from "@tabler/icons-react";
import Image from "next/image";
import { usePathname, useRouter } from "next/navigation";
import { useEffect, useState } from "react";
// const useLogout = (startTime) => {
//   const [timer, setTimer] = useState(startTime);
//   useEffect(() => {
//     const myInterval = setInterval(() => {
//       if (timer > 0) {
//         setTimer(timer - 1);
//       }
//     }, 1000);
//     const resetTimeout = () => {
//       setTimer(startTime);
//     };
//     const events = [
//       "load",
//       "mousemove",
//       "mousedown",
//       "click",
//       "scroll",
//       "keypress",
//     ];
//     for (const i in events) {
//       window.addEventListener(events[i], resetTimeout);
//     }
//     return () => {
//       clearInterval(myInterval);
//       for (const i in events) {
//         window.removeEventListener(events[i], resetTimeout);
//       }
//     };
//   });
//   return timer;
// };
const useLogout = (startTime, isLoggedIn = true) => {
  const [timer, setTimer] = useState(() => {
    // Only check localStorage if user is logged in
    if (isLoggedIn) {
      const storedTime = localStorage.getItem("lastActiveTime");
      if (storedTime) {
        const elapsedSeconds = Math.floor(
          (Date.now() - parseInt(storedTime)) / 1000
        );
        const remainingTime = Math.max(startTime - elapsedSeconds, 0);
        // If remaining time is 0, clear storage and return full time
        if (remainingTime === 0) {
          localStorage.removeItem("lastActiveTime");
          return startTime;
        }
        return remainingTime;
      }
    }
    return startTime;
  });

  useEffect(() => {
    // Reset timer and storage when login status changes
    if (isLoggedIn) {
      setTimer(startTime);
      localStorage.setItem("lastActiveTime", Date.now().toString());
    } else {
      localStorage.removeItem("lastActiveTime");
    }
  }, [isLoggedIn, startTime]);

  useEffect(() => {
    if (!isLoggedIn) return; // Don't run timer if not logged in

    // Clear localStorage if timer reaches 0
    if (timer === 0) {
      localStorage.removeItem("lastActiveTime");
      return; // Don't set up interval if already timed out
    }

    const myInterval = setInterval(() => {
      setTimer((currentTimer) => {
        if (currentTimer > 0) {
          return currentTimer - 1;
        }
        // Clear localStorage when hitting 0
        localStorage.removeItem("lastActiveTime");
        return 0;
      });
    }, 1000);

    const resetTimeout = () => {
      setTimer(startTime);
      localStorage.setItem("lastActiveTime", Date.now().toString());
    };

    const events = [
      "load",
      "mousemove",
      "mousedown",
      "click",
      "scroll",
      "keypress",
    ];

    const handleVisibilityChange = () => {
      if (document.visibilityState === "visible") {
        const storedTime = localStorage.getItem("lastActiveTime");
        if (storedTime) {
          const elapsedSeconds = Math.floor(
            (Date.now() - parseInt(storedTime)) / 1000
          );
          const remainingTime = Math.max(startTime - elapsedSeconds, 0);
          setTimer(remainingTime);
        } else {
          // If no stored time, reset to full duration
          setTimer(startTime);
        }
      }
    };

    document.addEventListener("visibilitychange", handleVisibilityChange);

    events.forEach((event) => {
      window.addEventListener(event, resetTimeout);
    });

    return () => {
      clearInterval(myInterval);
      document.removeEventListener("visibilitychange", handleVisibilityChange);
      events.forEach((event) => {
        window.removeEventListener(event, resetTimeout);
      });
    };
  }, [timer, startTime, isLoggedIn]);

  return timer;
};

const navbarItem: NavbarItemProps[] = [
  {
    title: "Alert",
    icon: <IconAlarm />,
    href: "/dashboard/alert",
    children: [],
  },
  {
    title: "Report",
    icon: <IconReport />,
    href: "/dashboard/report",
    children: [],
  },
  {
    title: "User Management",
    icon: <IconUser />,
    href: "/dashboard/user-management",
    children: [],
  },
  {
    title: "Rule Management",
    icon: <IconArchive />,
    href: "/dashboard/rule-management",
    children: [],
  },
  {
    title: "Settings",
    icon: <IconSettings />,
    href: "/dashboard/settings",
    children: [],
  },
];

export default function NavbarV2({
  children,
}: Readonly<{ children: React.ReactNode }>) {
  const timer = useLogout(600);
  const router = useRouter();
  const pathname = usePathname();
  const postLogoutQuery = usePostLogoutQuery();
  const [opened, { toggle }] = useDisclosure();
  const isMobile = useMediaQuery("(max-width: 768px)");
  const getMeQuery = useGetMeQuery();
  const pathnamesAnchor = pathname.split("/").map((item, i) => {
    return (
      <UnstyledButton
        key={i}
        onClick={() => {
          const tmpPath = pathname
            .split("/")
            .slice(0, i + 1)
            .join("/");
          router.push(tmpPath);
        }}
        style={{ cursor: "pointer" }}
      >
        <Anchor key={i}>
          {item
            .split("-")
            .map((word) => word.charAt(0).toUpperCase() + word.slice(1))
            .join(" ")}
        </Anchor>
      </UnstyledButton>
    );
  });

  useEffect(() => {
    if (getMeQuery.isError) {
      customLocalStorage.clearAllData();
      router.push("/login");
      notifications.show({
        title: "Error",
        message: "Session expired, please login again",
        color: "red",
      });
    }
  }, [getMeQuery.isError]);

  useEffect(() => {
    if (postLogoutQuery.isSuccess) {
      router.replace(
        "/login?error=No activity for 10 minutes, please login again."
      );
    }
  }, [postLogoutQuery.isSuccess]);
  useEffect(() => {
    if (timer === 0) {
      postLogoutQuery.mutate();
    }
  }, [timer]);
  return (
    <AppShell
      navbar={{ width: 300, breakpoint: "sm", collapsed: { mobile: !opened } }}
      padding="md"
    >
      <AppShell.Header hiddenFrom="sm">
        <Flex px={"md"} my={24} align="center" justify={"space-between"}>
          <Burger opened={opened} onClick={toggle} hiddenFrom="sm" size="sm" />
          <Image
            src="/images/xl-logo.svg"
            priority
            alt="Mantine logo"
            height={75}
            width={75}
          />
          <Box />
          {/* <Title order={3} ml="sm">
              Fraud Detection System
            </Title> */}
        </Flex>
      </AppShell.Header>
      <AppShell.Navbar>
        <Flex px={"md"} my={24} align="center" justify={"space-between"}>
          <Box visibleFrom="sm" />
          <Burger opened={opened} onClick={toggle} hiddenFrom="sm" size="sm" />
          <Image
            src="/images/xl-logo.svg"
            alt="Mantine logo"
            height={75}
            priority
            width={75}
          />
          <Box />
          {/* <Title order={3} ml="sm">
              Fraud Detection System
            </Title> */}
        </Flex>
        {/* Navbar */}
        {/* {Array(15)
          .fill(0)
          .map((_, index) => (
            <Skeleton key={index} h={30} mt="sm" animate={false} />
          ))} */}
        <Stack justify={"space-between"} h={"100%"} gap={0}>
          <Stack gap={0}>
            {navbarItem.map((item, index) => (
              <NavbarItem
                key={index}
                title={item.title}
                icon={item.icon}
                href={item.href}
                active={pathname.startsWith(item.href)}
                toggle={toggle}
                // eslint-disable-next-line react/no-children-prop
                children={item.children}
              />
            ))}
          </Stack>
          {/* <Button
            variant="gradient"
            bg="red"
            size="xl"
            mt="xl"
            rightSection={<IconOutbound size={18} />}
            onClick={() => {
              customLocalStorage.clearAllData();
              router.push("/login");
            }}
          >
            Sign Out
          </Button> */}
        </Stack>
      </AppShell.Navbar>
      <AppShell.Main
        bg={"#F9FAFB"}
        pt={{ base: "100px", xs: "100px", sm: "0", md: 0, lg: 0, xl: 0 }}
      >
        <Flex
          justify={"space-between"}
          // px={{ base: "xs", xs: "xs", sm: "md", md: "md", lg: "lg", xl: "lg" }}
          mt={{
            base: "xs",
            xs: "xs",
            sm: "md",
            md: "md",
            lg: "md",
            xl: "md",
          }}
          ml={{
            base: "xs",
            xs: "xs",
            sm: "md",
            md: "md",
            lg: "md",
            xl: "md",
          }}
          direction={isMobile ? "column" : "row"}
          pos={"sticky"}
          top={isMobile ? "90px" : "0"}
          bg={"linear-gradient(90deg, rgba(255, 255, 255, 0) 0%, #F9FAFB 100%)"}
          w={"100%"}
          style={{
            backdropFilter: "blur(4px)",
            WebkitBackdropFilter: "blur(4px)",
            zIndex: 100,
          }}
        >
          <Breadcrumbs>{pathnamesAnchor}</Breadcrumbs>
          <Space
            h={{ base: "xs", xs: "xs", sm: "md", md: "md", lg: "md", xl: "md" }}
            hiddenFrom="sm"
          />
          <Card radius={"sm"} p={"sm"} withBorder>
            <Flex align={"center"} justify={"space-between"}>
              <Stack gap={0}>
                <Text size="sm" fw={600}>
                  {getMeQuery.data?.username}
                </Text>
                <Text c="gray" size="sm" fw={400}>
                  {getMeQuery.data?.email}
                </Text>
              </Stack>
              <Menu shadow="md" width={200}>
                <Menu.Target>
                  <ActionIcon
                    variant="transparent"
                    aria-label="Settings"
                    title="Settings"
                    radius="xl"
                    size="xl"
                    color="gray"
                  >
                    <IconSettings size={isMobile ? 24 : 24} />
                  </ActionIcon>
                </Menu.Target>

                <Menu.Dropdown>
                  <Menu.Label>Settings</Menu.Label>
                  <Menu.Item
                    onClick={() => {
                      postLogoutQuery.mutate();
                    }}
                  >
                    Sign Out
                  </Menu.Item>
                </Menu.Dropdown>
              </Menu>
            </Flex>
          </Card>
        </Flex>

        <Box
          px={{ base: "xs", xs: "xs", sm: "md", md: "md", lg: "lg", xl: "lg" }}
          mb={"200px"}
        >
          {children}
        </Box>
      </AppShell.Main>
    </AppShell>
  );
}
