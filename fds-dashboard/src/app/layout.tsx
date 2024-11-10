import type { Metadata } from "next";
import { Inter } from "next/font/google";
// import "./globals.css";

import AuthLayout from "@/packages/user-management-service/src/auth/components/AuthLayout";
import ReactQueryClientProvider from "@/packages/user-management-service/src/auth/components/ReactQueryProvider";

import "@mantine/charts/styles.css";
import { createTheme, Loader, MantineProvider } from "@mantine/core";
import "@mantine/core/styles.css";
import "@mantine/dates/styles.css";
import { ModalsProvider } from "@mantine/modals";
import { Notifications } from "@mantine/notifications";
import "@mantine/notifications/styles.css";
import { Suspense } from "react";
const inter = Inter({ subsets: ["latin"] });
const theme = createTheme({});

export const metadata: Metadata = {
  title: "FDS Dashboard",
  description: "FDS Dashboard",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <body
        className={inter.className}
        style={{
          background: "#F9FAFB",
        }}
      >
        <AuthLayout>
          <ReactQueryClientProvider>
            <MantineProvider
              theme={theme}
              forceColorScheme="light"
              defaultColorScheme="light"
            >
              <Suspense fallback={<Loader />}>
                <Notifications />
                <ModalsProvider>{children}</ModalsProvider>
              </Suspense>
            </MantineProvider>
          </ReactQueryClientProvider>
        </AuthLayout>
      </body>
    </html>
  );
}
