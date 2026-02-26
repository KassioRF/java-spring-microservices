import { useNotification } from "@/providers/notification-provider";
import type { UpdateSaleStatusDTO } from "@/schemas/sale.schema";
import { saleService } from "@/services/sale.service";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { AxiosError } from "axios";

export function useUpdateSaleStatus() {
  const queryClient = useQueryClient();
  const { notify } = useNotification();

  return useMutation({
    mutationFn: (data: UpdateSaleStatusDTO) =>
      saleService.updateSaleStatus(data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["sales"] });
      notify("Sale status updated!", "success");
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
