"use client";
import { useRouter } from "next/navigation";
import { useEffect } from "react";

export default function Page() {
  //redirect to home page
  const router = useRouter();

  useEffect(() => {
    router.push("/dashboard/alert");
  });
  return <div>Page</div>;
}
