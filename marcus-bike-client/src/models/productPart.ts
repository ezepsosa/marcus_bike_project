import { ProductPartCategory } from "./productPartCategory";

export interface ProductPart {
  id: number;
  partOption: string;
  basePrice: number;
  isAvailable: boolean;
  productPartCategory: ProductPartCategory;
}

export interface ProductPartInsert {
  partOption: string;
  basePrice: number;
  isAvailable: boolean;
  productPartCategory: ProductPartCategory;
}
