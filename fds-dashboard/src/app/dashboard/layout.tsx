import Navbar from "@/packages/user-management-service/src/auth/components/Navbar";
import type { Metadata } from "next";
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
    <>
      <Navbar> {children}</Navbar>
    </>
  );
}
