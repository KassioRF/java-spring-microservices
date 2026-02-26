import { useNotification } from "@/providers/notification-provider";
import type { CreateSaleDTO } from "@/schemas/sale.schema";
import { saleService } from "@/services/sale.service";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { AxiosError } from "axios";

export function useCreateSale() {
  const queryClient = useQueryClient();
  const { notify } = useNotification();

  return useMutation({
    mutationFn: (data: CreateSaleDTO) => saleService.create(data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["sales"] });
      notify("Sale completed successfully", "success");
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
