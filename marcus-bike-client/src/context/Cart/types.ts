import { OrderLine } from "../../models/orderLines";

export interface CartContextType {
  cart: OrderLine[];
  addToCart: (order: OrderLine) => void;
  deleteFromCart: (order: OrderLine) => void;
}
