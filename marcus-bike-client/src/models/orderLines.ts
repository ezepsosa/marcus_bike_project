import { OrderLineProductPartInsert } from "./orderLineProductPart";
import { Product } from "./products";

export interface OrderLine {
  id: number;
  product: Product;
  quantity: number;
}

export interface OrderLineInsert {
  productId: number;
  quantity: number;
  orderLineProductParts: OrderLineProductPartInsert[];
}
