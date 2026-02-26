import { useState } from "react";
import { Controller, useForm } from "react-hook-form";

import { Button } from "@/components/ui/button";
import {
  Dialog,
  DialogContent,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";

import { ErrorBox } from "@/components/error-box";
import { Loading } from "@/components/loading";
import { loadEvents } from "@/hooks/events/load-events";
import { useCreateSale } from "@/hooks/sales/create-sale";
import { loadUsers } from "@/hooks/users/load-users";
import { NewSaleStatus } from "@/types/sale-status";

type FormValues = {
  eventId: string;
  userId: string;
  status: NewSaleStatus;
};

export function SellTicketModal() {
  const [isOpen, setIsOpen] = useState(false);
  const createSale = useCreateSale();

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

  const { control, handleSubmit, reset } = useForm<FormValues>({
    defaultValues: {
      eventId: "",
      userId: "",
      status: NewSaleStatus.OPEN,
    },
  });

  const onSubmit = (data: FormValues) => {
    createSale.mutate(data, {
      onSuccess: () => {
        reset(); // limpa o form
        setIsOpen(false); // fecha modal
      },
    });
  };

  if (eventsError) return <ErrorBox message={(eventsError as any).message} />;
  if (usersError) return <ErrorBox message={(usersError as any).message} />;

  return (
    <Dialog open={isOpen} onOpenChange={setIsOpen}>
      <DialogTrigger asChild>
        {/* Este botão vai ser o link do side menu */}
        <Button className="text-lg font-bold py-6">Sell Ticket</Button>
      </DialogTrigger>

      <DialogContent className="sm:max-w-md">
        <DialogHeader>
          <DialogTitle>Sell Ticket</DialogTitle>
        </DialogHeader>

        {(loadingEvents || loadingUsers) && <Loading />}

        <form className="grid gap-4 mt-2" onSubmit={handleSubmit(onSubmit)}>
          {/* Select do Evento */}
          <Controller
            name="eventId"
            control={control}
            render={({ field }) => (
              <Select
                onValueChange={field.onChange}
                value={field.value}
                disabled={loadingEvents}
              >
                <SelectTrigger className="w-full">
                  <SelectValue placeholder="Select Event" />
                </SelectTrigger>
                <SelectContent>
                  {events?.map((event) => (
                    <SelectItem key={event.id} value={event.id}>
                      {event.name} (${event.price})
                    </SelectItem>
                  ))}
                </SelectContent>
              </Select>
            )}
          />

          {/* Select do Usuário */}
          <Controller
            name="userId"
            control={control}
            render={({ field }) => (
              <Select
                onValueChange={field.onChange}
                value={field.value}
                disabled={loadingUsers}
              >
                <SelectTrigger className="w-full">
                  <SelectValue placeholder="Select User" />
                </SelectTrigger>
                <SelectContent>
                  {users?.map((user) => (
                    <SelectItem key={user.id} value={user.id}>
                      {user.name} ({user.email})
                    </SelectItem>
                  ))}
                </SelectContent>
              </Select>
            )}
          />

          {/* Select Status */}
          <Controller
            name="status"
            control={control}
            render={({ field }) => (
              <Select onValueChange={field.onChange} value={field.value}>
                <SelectTrigger className="w-full">
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
            )}
          />

          <DialogFooter className="flex justify-end gap-2 mt-4">
            <Button
              type="button"
              variant="secondary"
              onClick={() => setIsOpen(false)}
            >
              Cancel
            </Button>
            <Button type="submit" disabled={createSale.status === "pending"}>
              Submit Sale
            </Button>
          </DialogFooter>
        </form>
      </DialogContent>
    </Dialog>
  );
}
