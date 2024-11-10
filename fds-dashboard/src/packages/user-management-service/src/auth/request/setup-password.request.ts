import * as z from "zod";

export type SetupPasswordRequest = {
  password: string;
  confirmPassword: string;
  token: string;
};
export const SetupPasswordSchema = z.object({
  password: z.string(),
  confirmPassword: z.string(),
});
export type LoginRequestZod = z.infer<typeof SetupPasswordSchema>;
