import { Spinner } from "@/components/ui/spinner"; // shadcn spinner

interface LoadingProps {
  /** Ocupa toda a tela ou apenas o container */
  small?: boolean;
  /** Cor opcional do spinner */
  color?: string;
  /** Tamanho opcional do spinner */
  size?: number;
}

export function Loading({
  small,
  color = "blue-500",
  size = 12,
}: LoadingProps) {
  return (
    <div
      className={`flex justify-center items-center ${
        small ? "py-2 h-auto" : "h-full py-20"
      }`}
    >
      <Spinner className={`w-${size} h-${size} text-${color}`} />
    </div>
  );
}
