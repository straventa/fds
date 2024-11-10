"use client";

import { clearAllCookies } from "@/packages/base/src/util/cookie.util";
import { usePostLoginQuery } from "@/packages/user-management-service/src/auth/query/post-login.query";
import {
  type LoginRequest,
  LoginRequestSchema,
} from "@/packages/user-management-service/src/auth/request/login.request";
import {
  Alert,
  Anchor,
  Box,
  Button,
  Center,
  Flex,
  Paper,
  PasswordInput,
  Space,
  TextInput,
  Title,
} from "@mantine/core";
import { useForm, zodResolver } from "@mantine/form";
import { IconInfoCircle } from "@tabler/icons-react";
import Image from "next/image";
import { useRouter, useSearchParams } from "next/navigation";
import { useEffect } from "react";
const styles = {
  wrapper: {
    minHeight: "100vh", // Converted rem(900px) to '900px' assuming default rem = 1px
    backgroundSize: "cover",
    backgroundImage: "url(/backcover.jpeg)",
  },
  form: {
    borderRight: "1px solid", // Converted rem(1px) to '1px'
    borderColor: "var(--mantine-color-gray-3)",
    minHeight: "900px", // Converted rem(900px) to '900px'
    maxWidth: "450px", // Converted rem(450px) to '450px'
    paddingTop: "80px", // Converted rem(80px) to '80px'
  },
  formMobile: {
    // Styles specific to smaller screens (using media queries in JS)
    maxWidth: "100%",
  },
  title: {
    color: "var(--mantine-color-black)",
    fontFamily: "Greycliff CF, var(--mantine-font-family)",
  },
};

export default function LoginForm() {
  const router = useRouter();
  const searchParams = useSearchParams();
  const loginError = searchParams.get("error");
  // const { menu, setMenu } = useGlobalStore();
  const { error, isError, isSuccess, mutate, isPending } = usePostLoginQuery();

  const handleLogin = (value: LoginRequest) => {
    clearAllCookies();
    mutate(value);
  };

  const form = useForm<LoginRequest>({
    initialValues: {
      email: null,
      password: "",
      username: "",
    },
    validate: zodResolver(LoginRequestSchema),
  });
  useEffect(() => {
    if (isSuccess) {
      router.push("/dashboard/alert");
    }
  }, [isSuccess]);
  return (
    <form
      onSubmit={form.onSubmit((values) => {
        handleLogin(values);
      })}
    >
      <div style={styles.wrapper}>
        <Paper style={styles.form} radius={0} p={30} mih={"100vh"}>
          <Center>
            <Image
              priority
              src="/images/xl-logo.svg"
              alt="logo"
              width={100}
              height={100}
            />
          </Center>
          <Title order={2} style={styles.title} ta={"center"} my={"md"}>
            Fraud Detection System
          </Title>
          {isError === false && loginError && (
            <Alert
              variant="light"
              color="red"
              p={10}
              my={10}
              icon={<IconInfoCircle />}
            >
              {loginError ?? "Something went wrong"}
            </Alert>
          )}
          {isError && (
            <Alert
              variant="light"
              color="red"
              p={10}
              my={10}
              icon={<IconInfoCircle />}
            >
              {error?.message ?? "Something went wrong"}
            </Alert>
          )}
          <TextInput
            label="Username"
            required
            type="username"
            {...form.getInputProps("username")}
          />
          <PasswordInput
            label="Password"
            placeholder="Your password"
            required
            mt="md"
            {...form.getInputProps("password")}
          />
          <Space h={16} />

          <Flex justify={"space-between"} align={"center"}>
            <Box />
            <Anchor
              size="sm"
              c={"#7F56D9"}
              onClick={() => {
                router.push("/forgot-password");
              }}
            >
              Forgot password?
            </Anchor>
          </Flex>
          <Button
            fullWidth
            mt="xl"
            type="submit"
            loading={isPending}
            bg={"#4AD2F5"}
          >
            Sign in
          </Button>
        </Paper>
      </div>
    </form>
  );
}
