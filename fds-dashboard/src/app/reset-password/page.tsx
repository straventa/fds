"use client";
import { usePostSetupPasswordQuery } from "@/packages/user-management-service/src/auth/query/post-setup-password.query";
import {
  ResetPasswordSchema,
  type ResetPasswordRequest,
} from "@/packages/user-management-service/src/auth/request/reset-password-request";
import {
  ActionIcon,
  Alert,
  Button,
  Card,
  Center,
  Space,
  Stack,
  TextInput,
  Title,
} from "@mantine/core";
import { useForm, zodResolver } from "@mantine/form";
import {
  IconEye,
  IconEyeOff,
  IconInfoCircle,
  IconLock,
} from "@tabler/icons-react";
import { useRouter, useSearchParams } from "next/navigation";
import { useEffect, useState } from "react";

export default function Page() {
  const router = useRouter();
  const searchParams = useSearchParams();
  const token = searchParams.get("token");
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);

  const postResetPasswordQuery = usePostSetupPasswordQuery();

  const form = useForm<ResetPasswordRequest>({
    initialValues: {
      confirmPassword: "",
      password: "",
      token: token,
    },
    validate: zodResolver(ResetPasswordSchema),
  });
  function handleSubmit({ value }: { value: ResetPasswordRequest }) {
    postResetPasswordQuery.mutate(value);
  }

  useEffect(() => {
    if (postResetPasswordQuery.isSuccess) {
      router.push("/reset-password/success");
    }
  }, [postResetPasswordQuery.isSuccess]);
  return (
    <Center miw={"400px"} h={"screen"} mih={"700px"}>
      <form
        onSubmit={form.onSubmit((value) => {
          handleSubmit({
            value: value,
          });
        })}
      >
        <Card
          p={30}
          shadow="xl"
          my={"xl"}
          miw={"400px"}
          maw={"400px"}
          radius={"md"}
        >
          <Stack align="center">
            <IconLock size={32} />
            <Title order={2}>Reset Password</Title>
          </Stack>
          {postResetPasswordQuery.isError && (
            <Alert
              radius={"md"}
              variant="light"
              color="red"
              p={10}
              my={10}
              icon={<IconInfoCircle />}
            >
              {postResetPasswordQuery.error?.message ?? "Something went wrong"}
            </Alert>
          )}
          {postResetPasswordQuery?.error?.errors?.find(
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
              {postResetPasswordQuery?.error?.errors?.find(
                (e) => e.code === "PasswordMatches"
              )?.defaultMessage ?? "Something went wrong"}
            </Alert>
          )}
          <Space h={8} />
          <TextInput
            label="Password"
            placeholder="Your password"
            required
            mt="md"
            {...form.getInputProps("password")}
            error={
              postResetPasswordQuery?.error?.errors?.find(
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
              postResetPasswordQuery?.error?.errors?.find(
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
            loading={postResetPasswordQuery.isPending}
            bg={"#4AD2F5"}
          >
            Reset Password
          </Button>
        </Card>
      </form>
    </Center>
  );
}
