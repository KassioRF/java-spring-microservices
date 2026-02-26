import type { ZodType } from "zod";
import { http } from "./http";

export abstract class BaseService<T> {
  protected readonly path: string;
  protected readonly schema: ZodType<T>;
  constructor(path: string, schema: ZodType<T>) {
    this.path = path;
    this.schema = schema;
  }

  async findAll(): Promise<T[]> {
    const res = await http.get(this.path);
    return this.schema.array().parse(res.data);
  }

  async findById(id: string): Promise<T> {
    const res = await http.get(`${this.path}/id/${id}`);
    return this.schema.parse(res.data);
  }
}
