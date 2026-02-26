import type { UserDTO } from "@/schemas/user.schema";
import { userService } from "@/services/user.service";
import { useQuery, type UseQueryResult } from "@tanstack/react-query";
import type { AxiosError } from "axios";

export function useUserById(
  userId: string,
): UseQueryResult<UserDTO, AxiosError> {
  return useQuery<UserDTO, AxiosError>({
    queryKey: ["user", userId],
    queryFn: () => userService.findById(userId),
    enabled: !!userId, // só busca se userId existir
  });
}
