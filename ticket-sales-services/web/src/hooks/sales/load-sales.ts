import type { SaleDTO } from "@/schemas/sale.schema";
import { saleService } from "@/services/sale.service";
import { useQuery, type UseQueryResult } from "@tanstack/react-query";
import type { AxiosError } from "axios";

export function loadSales(): UseQueryResult<SaleDTO[], AxiosError> {
  return useQuery<SaleDTO[], AxiosError>({
    queryKey: ["sales"],
    queryFn: () => saleService.findAll(),
  });
}
