import { z } from "zod";

export const UserSchema = z.object({
  id: z.uuid(),
  name: z.string(),
  email: z.string().email(),
  city: z.string(),
});

export type UserDTO = z.infer<typeof UserSchema>;

export const CreateUserSchema = UserSchema.omit({ id: true });
export type CreateUserDTO = z.infer<typeof CreateUserSchema>;

export const UpdateUserSchema = CreateUserSchema.partial();
export type UpdateUserDTO = z.infer<typeof UpdateUserSchema>;
