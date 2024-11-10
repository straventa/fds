import * as z from "zod";

export type ChangePasswordRequest = {
  password: string;
  confirmPassword: string;
  oldPassword: string;
};

export const ChangePasswordSchema = z.object({
  password: z.string(),
  confirmPassword: z.string(),
  oldPassword: z.string(),
});
export type ChangePasswordZod = z.infer<typeof ChangePasswordSchema>;
