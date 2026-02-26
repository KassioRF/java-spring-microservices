import { z } from "zod";
import { EventSchema } from "./event.schema";

export const SaleSchema = z.object({
  id: z.uuid(),
  userId: z.uuid(),
  event: EventSchema.omit({ createdAt: true, updatedAt: true }),
  dateTime: z.string(),
  status: z.string(),
  createdAt: z.string(),
  updatedAt: z.string(),
});

export type SaleDTO = z.infer<typeof SaleSchema>;

export const CreateSaleSchema = z.object({
  userId: z.uuid(),
  eventId: z.uuid(),
  status: z.string(),
});

export type CreateSaleDTO = z.infer<typeof CreateSaleSchema>;

export const UpdateSaleStatusSchema = z.object({
  saleId: z.uuid(),
  status: z.string(),
});

export type UpdateSaleStatusDTO = z.infer<typeof UpdateSaleStatusSchema>;
