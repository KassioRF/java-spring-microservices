import Layout from "@/layout";
import EventsPage from "@/pages/events-page";
import SalesPage from "@/pages/sales-page";
import UsersPage from "@/pages/users-page";
import { BrowserRouter, Route, Routes } from "react-router-dom";

export default function AppRoutes() {
  return (
    <BrowserRouter>
      <Routes>
        <Route
          path="/"
          element={
            <Layout>
              <EventsPage />
            </Layout>
          }
        />
        <Route
          path="/sales"
          element={
            <Layout>
              <SalesPage />
            </Layout>
          }
        />
        <Route
          path="/users"
          element={
            <Layout>
              <UsersPage />
            </Layout>
          }
        />
      </Routes>
    </BrowserRouter>
  );
}
