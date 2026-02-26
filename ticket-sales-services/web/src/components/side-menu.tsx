import { cn } from "@/lib/utils";
import { Link } from "react-router-dom";
import { SellTicketModal } from "./sell-ticket-modal";

export function SideMenu({ className }: { className?: string }) {
  const cssClass: string =
    "ps-8 py-4 rounded hover:bg-gray-500/20 transition-colors";
  return (
    <>
      <div
        className={cn(
          "flex flex-col w-1/4 min-h-screen p-4 space-y-2 text-2xl font-bold border bg-gray/10 backdrop-blur-m",
          className,
        )}
      >
        {/* <Link to="/sell-ticket" className={cssClass}>
        Sell Ticket
      </Link> */}
        <SellTicketModal />
        <Link to="/" className={cssClass}>
          Events
        </Link>
        <Link to="/sales" className={cssClass}>
          Sales
        </Link>
        <Link to="/users" className={cssClass}>
          Users
        </Link>
      </div>
    </>
  );
}
