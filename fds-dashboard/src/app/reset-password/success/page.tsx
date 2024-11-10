"use client";
import { Button, Card, Center, Stack, Text, Title } from "@mantine/core";
import { IconCheck } from "@tabler/icons-react";
import { useRouter } from "next/navigation";

export default function Page() {
  const router = useRouter();
  return (
    <Center miw={"400px"} h={"screen"} mih={"700px"}>
      <Card p={30} shadow="xl" my={"xl"} miw={"400px"} radius={"md"}>
        <Stack align="center">
          <IconCheck size={32} />
          <Title order={2}>Reset Password</Title>
          <Text>Your password have been successfully reset.</Text>
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
