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
import { Input } from "@/components/ui/input";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { Textarea } from "@/components/ui/textarea";

import { CreateEventSchema, type CreateEventDTO } from "@/schemas/event.schema";

import { ErrorBox } from "@/components/error-box";
import { useCreateEvent } from "@/hooks/events/create-event";
import { loadUsers } from "@/hooks/users/load-users";
import { EventType } from "@/types/event-type";
import { zodResolver } from "@hookform/resolvers/zod";

export function CreateEventModal() {
  const [isOpen, setIsOpen] = useState(false);
  const mutation = useCreateEvent();
  const {
    data: users,
    isLoading: loadingUsers,
    error: usersError,
  } = loadUsers();

  const {
    register,
    handleSubmit,
    control,
    reset,
    formState: { errors },
  } = useForm<CreateEventDTO>({
    resolver: zodResolver(CreateEventSchema),
    defaultValues: {
      name: "",
      description: "",
      type: "",
      price: 0,
      eventDate: "",
      organizerId: "",
    },
  });

  const onSubmit = (data: CreateEventDTO) => {
    mutation.mutate(data, {
      onSuccess: () => setIsOpen(false),
    });
    reset();
  };

  if (usersError) return <ErrorBox message={(usersError as any).message} />;

  return (
    <Dialog open={isOpen} onOpenChange={setIsOpen}>
      <DialogTrigger asChild>
        <Button className="text-lg font-bold">Create Event</Button>
      </DialogTrigger>
      <DialogContent className="sm:max-w-125">
        <DialogHeader>
          <DialogTitle>Create Event</DialogTitle>
        </DialogHeader>

        <form className="grid gap-2 mt-2" onSubmit={handleSubmit(onSubmit)}>
          <Input placeholder="Event Name" {...register("name")} />
          {errors.name && (
            <span className="text-red-400 text-sm">{errors.name.message}</span>
          )}

          <Textarea placeholder="Description" {...register("description")} />
          {errors.description && (
            <span className="text-red-400 text-sm">
              {errors.description.message}
            </span>
          )}

          <Controller
            name="type"
            control={control}
            render={({ field }) => (
              <Select onValueChange={field.onChange} value={field.value}>
                <SelectTrigger className="w-full">
                  <SelectValue placeholder="Select Event Type" />
                </SelectTrigger>
                <SelectContent>
                  {Object.values(EventType).map((t) => (
                    <SelectItem key={t} value={t}>
                      {t}
                    </SelectItem>
                  ))}
                </SelectContent>
              </Select>
            )}
          />
          {errors.type && (
            <span className="text-red-400 text-sm">{errors.type.message}</span>
          )}

          <Input
            type="number"
            placeholder="Price"
            {...register("price", { valueAsNumber: true })}
          />
          {errors.price && (
            <span className="text-red-400 text-sm">{errors.price.message}</span>
          )}

          <Input
            type="datetime-local"
            placeholder="Event Date"
            {...register("eventDate")}
          />
          {errors.eventDate && (
            <span className="text-red-400 text-sm">
              {errors.eventDate.message}
            </span>
          )}

          {/* Select do Organizer */}
          <Controller
            name="organizerId"
            control={control}
            render={({ field }) => (
              <Select
                onValueChange={field.onChange}
                value={field.value}
                disabled={loadingUsers}
              >
                <SelectTrigger className="w-full">
                  <SelectValue placeholder="Select Organizer" />
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
          {errors.organizerId && (
            <span className="text-red-400 text-sm">
              {errors.organizerId.message}
            </span>
          )}

          <DialogFooter className="mt-4 flex justify-end gap-2">
            <Button
              type="button"
              variant="secondary"
              onClick={() => setIsOpen(false)}
            >
              Cancel
            </Button>
            <Button type="submit" disabled={mutation.status === "pending"}>
              Create
            </Button>
          </DialogFooter>
        </form>
      </DialogContent>
    </Dialog>
  );
}
