import { cn } from "@/lib/utils";
import { AlertTriangle, CheckCircle, XCircle } from "lucide-react";

type NotificationType = "success" | "error" | "warning";

interface NotificationProps {
  message: string;
  type?: NotificationType;
  className?: string;
}

export function Notification({
  message,
  type = "success",
  className,
}: NotificationProps) {
  // Define cores e ícone conforme tipo
  let icon = <CheckCircle className="w-5 h-5" />;
  let border = "border-green-500";
  let bg = "bg-slate-900";
  let text = "text-green-400";

  if (type === "error") {
    icon = <XCircle className="w-5 h-5" />;
    border = "border-red-500";
    text = "text-red-400";
  } else if (type === "warning") {
    icon = <AlertTriangle className="w-5 h-5" />;
    border = "border-yellow-500";
    text = "text-yellow-400";
  }

  return (
    <div
      className={cn(
        `fixed top-20 right-4 w-80 flex items-center gap-2 ${border} ${bg} ${text} p-4 rounded shadow-md z-50 animate-fade-in-out`,
        className,
      )}
    >
      {icon}
      <span className="text-sm">{message}</span>
    </div>
  );
}
