"use client";
import { usePostForgotPasswordQuery } from "@/packages/user-management-service/src/auth/query/post-forgot-password.query";
import {
  type ForgotPasswordRequest,
  ForgotPasswordSchema,
} from "@/packages/user-management-service/src/auth/request/forgot-password-request";
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
import { IconArrowLeft, IconInfoCircle, IconKey } from "@tabler/icons-react";
import { useRouter } from "next/navigation";

export default function ForgotPassword() {
  const router = useRouter();

  const postForgotPasswordQuery = usePostForgotPasswordQuery();

  const form = useForm<ForgotPasswordRequest>({
    initialValues: {
      email: "",
    },
    validate: zodResolver(ForgotPasswordSchema),
  });
  async function handleSubmit({ value }: { value: ForgotPasswordRequest }) {
    await postForgotPasswordQuery.mutate(value);
    router.push("/forgot-password/success");
  }
  // useEffect(() => {
  //   if (postForgotPasswordQuery.isSuccess) {
  //     router.push("/forgot-password/success");
  //   }
  // }, [postForgotPasswordQuery.isSuccess]);
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
            <IconKey size={32} />
            <Title order={2}>Forgot Password</Title>
          </Stack>
          {postForgotPasswordQuery.isError && (
            <Alert
              radius={"md"}
              variant="light"
              color="red"
              p={10}
              my={10}
              icon={<IconInfoCircle />}
            >
              {postForgotPasswordQuery.error?.message ?? "Something went wrong"}
            </Alert>
          )}
          <Space h={8} />
          <TextInput
            label="Email"
            placeholder="Enter your Email"
            required
            {...form.getInputProps("email")}
            // error={
            //   postForgotPasswordQuery?.error?.errors?.find(
            //     (e) => e.field === "email"
            //   )?.defaultMessage ?? ""
            // }
          />
          <Button
            fullWidth
            mt="md"
            type="submit"
            loading={postForgotPasswordQuery.isPending}
            bg={"#4AD2F5"}
          >
            Reset password
          </Button>
          <Button
            fullWidth
            mt="md"
            variant="transparent"
            // loading={postForgotPasswordQuery.isPending}
            c={"grey"}
            onClick={() => {
              router.push("/login");
            }}
            leftSection={
              <ActionIcon variant="transparent" c={"grey"}>
                <IconArrowLeft />
              </ActionIcon>
            }
          >
            Back to log in
          </Button>
        </Card>
      </form>
    </Center>
  );
}
