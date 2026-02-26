import {
  CreateSaleSchema,
  SaleSchema,
  UpdateSaleStatusSchema,
  type CreateSaleDTO,
  type SaleDTO,
  type UpdateSaleStatusDTO,
} from "@/schemas/sale.schema";
import { BaseService } from "./base.service";
import { http } from "./http";

class SaleService extends BaseService<SaleDTO> {
  constructor() {
    super("/sales/sales", SaleSchema);
  }

  async create(data: CreateSaleDTO): Promise<SaleDTO> {
    const payload = CreateSaleSchema.parse(data);
    const res = await http.post(this.path, payload);
    return SaleSchema.parse(res.data);
  }

  async updateSaleStatus(data: UpdateSaleStatusDTO): Promise<SaleDTO> {
    const payload = UpdateSaleStatusSchema.parse(data);
    const url = `${this.path}/status`;
    return await http.post(url, payload);
  }
}

export const saleService = new SaleService();
