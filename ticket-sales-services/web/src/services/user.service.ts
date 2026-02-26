import { UserSchema, type UserDTO } from "@/schemas/user.schema";
import { BaseService } from "./base.service";

class UserService extends BaseService<UserDTO> {
  constructor() {
    super("/users/users", UserSchema);
  }
}

export const userService = new UserService();
