import { useState } from "react";

import { Button } from "@/components/ui/button";
import {
  Dialog,
  DialogContent,
  DialogFooter,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import { useUpdateSaleStatus } from "@/hooks/sales/update-sale-status";
import { SaleStatus } from "@/types/sale-status";

export function UpdateSaleStatusModal({
  saleId,
  currentStatus,
  onClose,
}: {
  saleId: string;
  currentStatus: string;
  onClose?: () => void;
}) {
  const [status, setStatus] = useState(currentStatus);
  const mutation = useUpdateSaleStatus();

  const handleUpdateStatus = () => {
    mutation.mutate({ saleId, status });
    onClose?.();
  };

  return (
    <Dialog open={true} onOpenChange={onClose}>
      <DialogContent>
        <DialogHeader>
          <DialogTitle>Update Sale Status</DialogTitle>
        </DialogHeader>
        <select
          value={status}
          onChange={(e) => setStatus(e.target.value)}
          className="mt-2 w-full border rounded p-1"
        >
          {Object.values(SaleStatus).map((s) => (
            <option key={s} value={s}>
              {s}
            </option>
          ))}
        </select>
        <DialogFooter className="mt-4">
          <Button
            onClick={handleUpdateStatus}
            disabled={mutation.status === "pending"}
          >
            OK
          </Button>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  );
}
