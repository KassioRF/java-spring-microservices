import { ErrorBox } from "@/components/error-box";
import { Loading } from "@/components/loading";
import { Button } from "@/components/ui/button";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { loadEvents } from "@/hooks/events/load-events";
import { loadUsers } from "@/hooks/users/load-users";
import type { EventDTO } from "@/schemas/event.schema";
import type { UserDTO } from "@/schemas/user.schema";
import { useState } from "react";

import { useCreateSale } from "@/hooks/sales/create-sale";
import { NewSaleStatus } from "@/types/sale-status";

export default function SellTicketPage() {
  const {
    data: events,
    isLoading: loadingEvents,
    error: eventsError,
  } = loadEvents();
  const {
    data: users,
    isLoading: loadingUsers,
    error: usersError,
  } = loadUsers();

  const [selectedEvent, setSelectedEvent] = useState<string | null>(null);
  const [selectedUser, setSelectedUser] = useState<string | null>(null);
  const [status, setStatus] = useState<NewSaleStatus>(NewSaleStatus.OPEN);

  const createSale = useCreateSale();

  const handleSubmit = () => {
    if (!selectedUser || !selectedEvent) return;
    console.log(
      JSON.stringify({
        userId: selectedUser,
        eventId: selectedEvent,
        status: status,
      }),
    );
    createSale.mutate({
      userId: selectedUser,
      eventId: selectedEvent,
      status: status,
    });
  };

  if (loadingEvents || loadingUsers) return <Loading small />;
  if (eventsError) return <ErrorBox message={(eventsError as any).message} />;
  if (usersError) return <ErrorBox message={(usersError as any).message} />;

  return (
    <div className="max-w-xl mx-auto mt-10 flex flex-col  gap-8">
      <h1 className="text-4xl font-bold">Sell Ticket</h1>

      {/* Select do evento */}
      <Select onValueChange={setSelectedEvent}>
        <SelectTrigger className="w-full">
          <SelectValue placeholder="Select Event" />
        </SelectTrigger>
        <SelectContent>
          {events?.map((event: EventDTO) => (
            <SelectItem key={event.id} value={event.id}>
              {event.name} (${event.price})
            </SelectItem>
          ))}
        </SelectContent>
      </Select>

      {/* Select do usuário */}
      <Select onValueChange={setSelectedUser}>
        <SelectTrigger className="w-full">
          <SelectValue placeholder="Select User" />
        </SelectTrigger>
        <SelectContent>
          {users?.map((user: UserDTO) => (
            <SelectItem key={user.id} value={user.id}>
              {user.name} ({user.email})
            </SelectItem>
          ))}
        </SelectContent>
      </Select>

      {/* Status da venda */}
      <Select
        onValueChange={(v: string) => setStatus(v as NewSaleStatus)}
        value={status}
      >
        <SelectTrigger>
          <SelectValue placeholder="Select Status" />
        </SelectTrigger>
        <SelectContent>
          {Object.values(NewSaleStatus).map((s) => (
            <SelectItem key={s} value={s}>
              {s}
            </SelectItem>
          ))}
        </SelectContent>
      </Select>

      <Button onClick={handleSubmit} className="bg-blue-600 hover:bg-blue-700">
        Submit Sale
      </Button>
    </div>
  );
}
