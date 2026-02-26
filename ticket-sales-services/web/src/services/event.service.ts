import {
  CreateEventSchema,
  EventSchema,
  type CreateEventDTO,
  type EventDTO,
} from "@/schemas/event.schema";
import { BaseService } from "./base.service";
import { http } from "./http";

class EventService extends BaseService<EventDTO> {
  constructor() {
    super("/sales/events", EventSchema);
  }

  async create(data: CreateEventDTO): Promise<EventDTO> {
    const payload = CreateEventSchema.parse(data);
    const res = await http.post(this.path, payload);
    return EventSchema.parse(res.data);
  }
}

export const eventService = new EventService();
