import { Header } from "./components/header";
import { SideMenu } from "./components/side-menu";

export default function Layout({ children }: { children: React.ReactNode }) {
  return (
    <>
      <Header />
      <div className="flex min-h-screen">
        <SideMenu />
        <main className="flex-1 p-4">{children}</main>
      </div>
    </>
  );
}
