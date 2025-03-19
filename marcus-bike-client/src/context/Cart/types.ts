import { OrderLineInsert } from "../../models/orderLines";

export interface CartContextType {
  cart: OrderLineInsert[];
  addToCart: (order: OrderLineInsert) => void;
  deleteFromCart: (order: OrderLineInsert) => void;
}
