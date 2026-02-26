import { CreateEventModal } from "@/components/create-event-modal";
import { ErrorBox } from "@/components/error-box";
import { Loading } from "@/components/loading";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card"; // shadcn card
import { loadEvents } from "@/hooks/events/load-events";
import type { EventDTO } from "@/schemas/event.schema";

interface EventsGridProps {
  data: EventDTO[];
}

export default function EventsPage() {
  const { data, isLoading, error } = loadEvents();

  if (isLoading) return <div>Loading...</div>;
  if (error) return <div>Error: {error.message}</div>;

  return (
    <div className="flex flex-col gap-6">
      <div className="flex justify-between items-center">
        <h1 className="text-5xl font-bold">Events</h1>
        <CreateEventModal />
      </div>

      {isLoading && <Loading />}
      {error && <ErrorBox message={(error as any).message} />}
      {data && !isLoading && !error && <EventsGrid data={data} />}
    </div>
  );
}

function EventsGrid({ data }: EventsGridProps) {
  return (
    <>
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
        {data?.map((event) => (
          <Card key={event.id} className="bg-slate-900 border border-slate-700">
            <CardHeader>
              <CardTitle className="text-slate-300">{event.name}</CardTitle>
              <CardDescription>{event.type}</CardDescription>
            </CardHeader>
            <CardContent className=" text-slate-300">
              <p>{event.description}</p>
              <p className="mt-2 font-semibold">Price: ${event.price}</p>
              <p className="text-xs mt-1">
                Date: {new Date(event.eventDate).toLocaleString()}
              </p>
            </CardContent>
          </Card>
        ))}
      </div>
    </>
  );
}
