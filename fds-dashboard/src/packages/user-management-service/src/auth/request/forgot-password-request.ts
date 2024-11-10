import * as z from "zod";

export type ForgotPasswordRequest = {
  email: string;
};

export const ForgotPasswordSchema = z.object({
  email: z.string().email(),
});
export type ForgotPasswordZod = z.infer<typeof ForgotPasswordSchema>;
