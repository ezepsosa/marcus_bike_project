import { ProductPart } from "./productPart";

export interface OrderLineProductPart {
  productPart: ProductPart;
  finalPrice: number;
}

export interface OrderLineProductPartInsert {
  productPart: number;
  finalPrice: number;
}
