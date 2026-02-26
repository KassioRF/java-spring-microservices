import { useNotification } from "@/providers/notification-provider";
import type { CreateEventDTO } from "@/schemas/event.schema";
import { eventService } from "@/services/event.service";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { AxiosError } from "axios";

export function useCreateEvent() {
  const queryClient = useQueryClient();
  const { notify } = useNotification();

  return useMutation({
    mutationFn: (data: CreateEventDTO) => eventService.create(data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["events"] });
      notify("Event completed successfully", "success");
    },
    onError: (err: unknown) => {
      let message = "Unknown error";

      if (err instanceof AxiosError) {
        message = err.response?.data?.message || err.message;
      } else if (err instanceof Error) {
        message = err.message;
      }
      console.log(err);
      notify(message, "error");
    },
  });
}
