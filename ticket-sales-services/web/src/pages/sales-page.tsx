import { ErrorBox } from "@/components/error-box";
import { Loading } from "@/components/loading";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card"; // shadcn card
import { UpdateSaleStatusModal } from "@/components/update-sale-status-modal";
import { loadSales } from "@/hooks/sales/load-sales";
import { useUserById } from "@/hooks/users/find-user-by-id";
import type { SaleDTO } from "@/schemas/sale.schema";
import { useState } from "react";

interface SalesGridProps {
  data: SaleDTO[];
}

export default function SalesPage() {
  const { data, isLoading, error } = loadSales();

  return (
    <div className="flex flex-col gap-6">
      <div className="flex justify-between items-center">
        <h1 className="text-5xl font-bold">Sales</h1>
      </div>

      {/* Status handling */}
      {isLoading && <Loading />}
      {error && <ErrorBox message={(error as any).message} />}
      {data && !isLoading && !error && <SalesGrid data={data} />}
    </div>
  );
}

function SalesGrid({ data }: SalesGridProps) {
  return (
    <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
      {data.map((sale) => (
        <SaleCard key={sale.id} sale={sale} />
      ))}
    </div>
  );
}

export function SaleCard({ sale }: { sale: SaleDTO }) {
  const {
    data: user,
    isLoading: userLoading,
    error: userError,
  } = useUserById(sale.userId);
  const [isModalOpen, setModalOpen] = useState(false);

  return (
    <>
      <Card key={sale.id} className="bg-slate-900 border border-slate-700">
        <CardHeader className="flex justify-between items-center">
          <CardTitle className="text-slate-300">{sale.event.name}</CardTitle>
          <div className="flex items-center gap-2">
            <CardDescription>{sale.status}</CardDescription>
            <button
              className="text-sm px-2 py-1 border rounded text-slate-200 border-slate-600 hover:bg-slate-700"
              onClick={() => setModalOpen(true)}
            >
              Edit
            </button>
          </div>
        </CardHeader>
        <CardContent className="text-slate-300">
          <p>{sale.event.description}</p>
          <p className="mt-2 font-semibold">Price: ${sale.event.price}</p>
          <p className="text-xs mt-1">
            Sale Date: {new Date(sale.dateTime).toLocaleString()}
          </p>

          {userLoading && (
            <p className="text-xs text-slate-400 mt-1">Loading user...</p>
          )}
          {userError && (
            <p className="text-xs text-red-400 mt-1">Error loading user</p>
          )}
          {user && (
            <p className="text-xs mt-1">
              Buyer: {user.name} ({user.email})
            </p>
          )}
        </CardContent>
      </Card>

      {isModalOpen && (
        <UpdateSaleStatusModal
          saleId={sale.id}
          currentStatus={sale.status}
          onClose={() => setModalOpen(false)}
        />
      )}
    </>
  );
}
