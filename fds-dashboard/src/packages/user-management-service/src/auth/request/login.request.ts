import * as z from "zod";

export type LoginRequest = {
  password: string;
  email: string | null;
  username: string;
};
export const LoginRequestSchema = z.object({
  password: z.string(),
  username: z.string(),
});
export type LoginRequestZod = z.infer<typeof LoginRequestSchema>;
