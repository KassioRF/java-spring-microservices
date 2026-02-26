import type { EventDTO } from "@/schemas/event.schema";
import { eventService } from "@/services/event.service";
import { useQuery, type UseQueryResult } from "@tanstack/react-query";
import type { AxiosError } from "axios";

export function loadEvents(): UseQueryResult<EventDTO[], AxiosError> {
  return useQuery<EventDTO[], AxiosError>({
    queryKey: ["events"],
    queryFn: () => eventService.findAll(),
  });
}
