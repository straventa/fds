"use client";
import { Button, Card, Center, Stack, Text, Title } from "@mantine/core";
import { IconCheck } from "@tabler/icons-react";
import { useRouter } from "next/navigation";

export default function Page() {
  const router = useRouter();
  return (
    <Center miw={"400px"} h={"screen"} mih={"700px"}>
      <Card
        p={30}
        shadow="xl"
        my={"xl"}
        miw={"400px"}
        maw={"400px"}
        radius={"md"}
      >
        <Stack align="center">
          <IconCheck size={32} />
          <Title order={2}>Forgot Password</Title>
          <Text>
            If your email exists in our system, you will receive an email to
            reset your password.
          </Text>
        </Stack>

        <Button
          fullWidth
          mt="xl"
          bg={"#4AD2F5"}
          onClick={() => {
            router.replace("/login");
          }}
        >
          Continue to Login
        </Button>
      </Card>
    </Center>
  );
}
