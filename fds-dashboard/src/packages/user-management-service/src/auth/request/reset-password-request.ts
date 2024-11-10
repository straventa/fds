import * as z from "zod";

export type ResetPasswordRequest = {
  password: string;
  confirmPassword: string;
  token: string;
};

export const ResetPasswordSchema = z.object({
  password: z.string(),
  confirmPassword: z.string(),
  token: z.string(),
});
export type ResetPasswordZod = z.infer<typeof ResetPasswordSchema>;
