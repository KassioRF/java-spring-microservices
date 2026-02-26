import { z } from "zod";

export const EventSchema = z.object({
  id: z.uuid(),
  organizerId: z.uuid(),
  name: z.string(),
  description: z.string(),
  type: z.string(),
  price: z.number(),
  eventDate: z.string(),
  createdAt: z.string(),
  updatedAt: z.string(),
});

export type EventDTO = z.infer<typeof EventSchema>;

export const CreateEventSchema = EventSchema.omit({
  id: true,
  createdAt: true,
  updatedAt: true,
});

export type CreateEventDTO = z.infer<typeof CreateEventSchema>;

export const UpdateEventSchema = CreateEventSchema.partial();
export type UpdateEventDTO = z.infer<typeof UpdateEventSchema>;
