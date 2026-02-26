import type { UserDTO } from "@/schemas/user.schema";
import { userService } from "@/services/user.service";
import { useQuery, type UseQueryResult } from "@tanstack/react-query";
import type { AxiosError } from "axios";

export function loadUsers(): UseQueryResult<UserDTO[], AxiosError> {
  return useQuery<UserDTO[], AxiosError>({
    queryKey: ["users"],
    queryFn: () => userService.findAll(),
  });
}
