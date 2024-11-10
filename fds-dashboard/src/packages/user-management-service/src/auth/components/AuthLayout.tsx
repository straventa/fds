"use client";

import { userManagementServiceAxios } from "@/packages/user-management-service/src/shared/user-management.axios";
import type { UserAccountResponse } from "@/packages/user-management-service/src/user-account/response/user-account.response";
import { usePathname, useRouter } from "next/navigation";
import { useEffect, useState } from "react";

// Types
interface AuthState {
  isAuthenticated: boolean;
  isLoading: boolean;
  user: UserAccountResponse;
}

const PUBLIC_PATHS = [
  "/login",
  "/forgot-password",
  "/forgot-password/success",
  "/setup-password",
  "/setup-password/success",
  "/setup-password/expired",
  "/reset-password",
  "/reset-password/success",
  "/reset-password/expired",
] as const;

const AuthLayout = ({ children }: { children: React.ReactNode }) => {
  const router = useRouter();
  const pathname = usePathname();
  const [authState, setAuthState] = useState<AuthState>({
    isAuthenticated: false,
    isLoading: true,
    user: null,
  });

  // Check if current path is public
  const isPublicPath = PUBLIC_PATHS.includes(pathname as any);
  useEffect(() => {
    const checkAuthStatus = async () => {
      try {
        // Call your auth status endpoint
        const response = await userManagementServiceAxios.get("/auth/status", {
          withCredentials: true,
        });
        setAuthState({
          isAuthenticated: true,
          isLoading: false,
          user: response.data,
        });

        // Redirect authenticated users away from public pages
        if (isPublicPath) {
          router.replace("/dashboard/alert");
        }
      } catch (error) {
        setAuthState({
          isAuthenticated: false,
          isLoading: false,
          user: null,
        });
        // Redirect unauthenticated users to login
        if (!isPublicPath) {
          // Store the attempted URL to redirect back after login
          sessionStorage.setItem("redirectAfterLogin", pathname);
          router.replace("/login?error=" + (error?.message ?? ""));
        }
      }
    };

    checkAuthStatus();
  }, [pathname]);

  // Show loading state
  if (authState.isLoading) {
    return <LoadingSpinner />;
  }

  // Protected routes check
  if (!isPublicPath && !authState.isAuthenticated) {
    return null; // Router will handle redirect
  }

  // Public routes check
  if (isPublicPath && authState.isAuthenticated) {
    return null; // Router will handle redirect
  }

  return <>{children}</>;
};

// Loading spinner component
const LoadingSpinner = () => (
  <div className="flex items-center justify-center min-h-screen">
    <div className="animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-blue-500"></div>
  </div>
);

export default AuthLayout;
