import { OrderLine, OrderLineInsert } from "./orderLines";

export interface Order {
  id: number;
  finalPrice: number;
  createdAt: Date;
  orderLines: OrderLine[];
}

export interface OrderInsert {
  userId: number;
  finalPrice: number;
  orderLines: OrderLineInsert[];
}
