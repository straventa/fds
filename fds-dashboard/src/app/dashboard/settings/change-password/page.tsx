"use client";
import { usePostChangePasswordQuery } from "@/packages/user-management-service/src/auth/query/post-change-password.query";
import {
  ChangePasswordSchema,
  type ChangePasswordRequest,
} from "@/packages/user-management-service/src/auth/request/change-password.request";
import {
  ActionIcon,
  Alert,
  Box,
  Button,
  Card,
  Flex,
  Space,
  Text,
  TextInput,
  Title,
  UnstyledButton,
} from "@mantine/core";
import { useForm, zodResolver } from "@mantine/form";
import {
  IconChevronLeft,
  IconEye,
  IconEyeOff,
  IconInfoCircle,
} from "@tabler/icons-react";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";

export default function ChangePassword() {
  const router = useRouter();
  const [showOldPassword, setShowOldPassword] = useState(false);
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);

  const postChangePasswordQuery = usePostChangePasswordQuery();

  const form = useForm<ChangePasswordRequest>({
    initialValues: {
      confirmPassword: "",
      password: "",
      oldPassword: "",
    },
    validate: zodResolver(ChangePasswordSchema),
  });
  function handleSubmit({ value }: { value: ChangePasswordRequest }) {
    postChangePasswordQuery.mutate(value);
  }
  useEffect(() => {
    if (postChangePasswordQuery.isSuccess) {
      form.reset();
    }
  }, [postChangePasswordQuery.isSuccess]);
  return (
    <form
      onSubmit={form.onSubmit((value) => {
        handleSubmit({
          value: value,
        });
      })}
    >
      <Box>
        <UnstyledButton onClick={() => router.push("/dashboard/settings")}>
          <Flex align={"center"}>
            <IconChevronLeft size={24} />
            <Space w={8} />
            <Text fz={18} fw={400}>
              Back
            </Text>
          </Flex>
        </UnstyledButton>
      </Box>
      <Space h={32} />

      <Card shadow="xs" radius={"md"}>
        <Title order={2}>Change Password</Title>
        {postChangePasswordQuery.isSuccess && (
          <Alert
            radius={"md"}
            variant="light"
            color="green"
            p={10}
            my={10}
            icon={<IconInfoCircle />}
          >
            {"Successfully change password"}
          </Alert>
        )}
        {postChangePasswordQuery.isError && (
          <Alert
            radius={"md"}
            variant="light"
            color="red"
            p={10}
            my={10}
            icon={<IconInfoCircle />}
          >
            {postChangePasswordQuery.error?.message ?? "Something went wrong"}
          </Alert>
        )}
        {postChangePasswordQuery?.error?.errors?.find(
          (e) => e.code === "PasswordMatches"
        )?.defaultMessage && (
          <Alert
            radius={"md"}
            variant="light"
            color="red"
            p={10}
            my={10}
            icon={<IconInfoCircle />}
          >
            {postChangePasswordQuery?.error?.errors?.find(
              (e) => e.code === "PasswordMatches"
            )?.defaultMessage ?? "Something went wrong"}
          </Alert>
        )}
        <Space h={8} />
        <TextInput
          label="Old Password"
          placeholder="Your old password"
          required
          mt="md"
          {...form.getInputProps("oldPassword")}
          error={
            postChangePasswordQuery?.error?.errors?.find(
              (e) => e.field === "oldPassword"
            )?.defaultMessage ?? ""
          }
          type={showOldPassword ? "text" : "password"}
          rightSection={
            <ActionIcon
              onClick={() => {
                setShowOldPassword(!showOldPassword);
              }}
              variant="transparent"
            >
              {showOldPassword ? (
                <IconEyeOff color={"#4AD2F5"} />
              ) : (
                <IconEye color={"#4AD2F5"} />
              )}
            </ActionIcon>
          }
        />
        <Space h={8} />
        <TextInput
          label="Password"
          placeholder="Your password"
          required
          mt="md"
          {...form.getInputProps("password")}
          error={
            postChangePasswordQuery?.error?.errors?.find(
              (e) => e.field === "password"
            )?.defaultMessage ?? ""
          }
          type={showPassword ? "text" : "password"}
          rightSection={
            <ActionIcon
              onClick={() => {
                setShowPassword(!showPassword);
              }}
              variant="transparent"
            >
              {showPassword ? (
                <IconEyeOff color={"#4AD2F5"} />
              ) : (
                <IconEye color={"#4AD2F5"} />
              )}
            </ActionIcon>
          }
        />
        <Space h={8} />
        <TextInput
          label="Confirm Password"
          placeholder="Confirm your password"
          required
          mt="md"
          type={showConfirmPassword ? "text" : "password"}
          {...form.getInputProps("confirmPassword")}
          error={
            postChangePasswordQuery?.error?.errors?.find(
              (e) => e.field === "confirmPassword"
            )?.defaultMessage ?? ""
          }
          rightSection={
            <ActionIcon
              onClick={() => {
                setShowConfirmPassword(!showConfirmPassword);
              }}
              variant="transparent"
            >
              {showConfirmPassword ? (
                <IconEyeOff color={"#4AD2F5"} />
              ) : (
                <IconEye color={"#4AD2F5"} />
              )}
            </ActionIcon>
          }
        />
        <Button
          fullWidth
          mt="xl"
          type="submit"
          loading={postChangePasswordQuery.isPending}
          bg={"#4AD2F5"}
        >
          Change Password
        </Button>
      </Card>
    </form>
  );
}
