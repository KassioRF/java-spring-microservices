// NotificationContext.tsx
import { Notification } from "@/components/notification";
import { createContext, useContext, useState } from "react";

type Notification = {
  id: string;
  message: string;
  type: "success" | "error" | "warning";
};

const NotificationContext = createContext<{
  notify: (msg: string, type?: Notification["type"]) => void;
}>({ notify: () => {} });

export const NotificationProvider = ({
  children,
}: {
  children: React.ReactNode;
}) => {
  const [notifications, setNotifications] = useState<Notification[]>([]);

  const notify = (message: string, type: Notification["type"] = "success") => {
    const id = Date.now().toString();
    setNotifications((prev) => [...prev, { id, message, type }]);
    setTimeout(() => {
      setNotifications((prev) => prev.filter((n) => n.id !== id));
    }, 3000);
  };

  return (
    <NotificationContext.Provider value={{ notify }}>
      {children}

      <div className="fixed top-4 right-4 flex flex-col gap-2 z-50">
        {notifications.map((n) => (
          <Notification key={n.id} message={n.message} type={n.type} />
        ))}
      </div>
    </NotificationContext.Provider>
  );
};

export const useNotification = () => useContext(NotificationContext);
