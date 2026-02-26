import { Alert, AlertDescription, AlertTitle } from "@/components/ui/alert"; // shadcn alert

export function ErrorBox({ message }: { message?: string }) {
  return (
    <Alert className="bg-red-800/20  border-red-700/20">
      <AlertTitle className="text-xl">Error</AlertTitle>
      <AlertDescription className="text-gray-800">
        {message || "Something went wrong."}
      </AlertDescription>
    </Alert>
  );
}
