import { ErrorBox } from "@/components/error-box";
import { Loading } from "@/components/loading";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card"; // shadcn card
import { loadUsers } from "@/hooks/users/load-users";
import type { UserDTO } from "@/schemas/user.schema";

interface UsersGridProps {
  data: UserDTO[];
}

export default function UsersPage() {
  const { data, isLoading, error } = loadUsers();

  // const handleCreateUser = () => {
  //   console.log("Criar novo usuário"); // futuramente abrirá o form
  // };

  return (
    <div className="flex flex-col gap-6">
      <div className="flex justify-between items-center">
        <h1 className="text-5xl font-bold">Users</h1>
        {/* <Button
          onClick={handleCreateUser}
          className="bg-blue-600 hover:bg-blue-700"
        >
          Create User
        </Button> */}
      </div>

      {/* Status handling */}
      {isLoading && <Loading />}
      {error && <ErrorBox message={(error as any).message} />}
      {/* {data && !isLoading && !error && <UsersGrid data={data} />} */}
      {data && !isLoading && !error && <UsersList data={data} />}
    </div>
  );
}

function UsersGrid({ data }: UsersGridProps) {
  return (
    <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
      {data.map((user) => (
        <Card key={user.id} className="bg-slate-900 border border-slate-700">
          <CardHeader>
            <CardTitle className="text-slate-300">{user.name}</CardTitle>
            <CardDescription>{user.city}</CardDescription>
          </CardHeader>
          <CardContent className="text-slate-300">
            <p>Email: {user.email}</p>
            <p className="text-xs mt-1">ID: {user.id}</p>
          </CardContent>
        </Card>
      ))}
    </div>
  );
}

interface UsersListProps {
  data: UserDTO[];
}

export function UsersList({ data }: UsersListProps) {
  return (
    <div className="overflow-x-auto">
      <table className="min-w-full border border-slate-700 text-slate-300">
        <thead className="bg-slate-800 text-left">
          <tr>
            <th className="px-4 py-2 border-b border-slate-700">Name</th>
            <th className="px-4 py-2 border-b border-slate-700">Email</th>
            <th className="px-4 py-2 border-b border-slate-700">City</th>
            <th className="px-4 py-2 border-b border-slate-700">ID</th>
          </tr>
        </thead>
        <tbody>
          {data.map((user) => (
            <tr
              key={user.id}
              className="text-slate-800 hover:bg-slate-300 transition-colors "
            >
              <td className="px-4 py-2">{user.name}</td>
              <td className="px-4 py-2">{user.email}</td>
              <td className="px-4 py-2">{user.city}</td>
              <td className="px-4 py-2 text-xs">{user.id}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
