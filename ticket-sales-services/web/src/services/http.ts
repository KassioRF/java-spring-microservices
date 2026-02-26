import { env } from "@/env";
import axios from "axios";

export const http = axios.create({
  baseURL: env.API_URL,
});
