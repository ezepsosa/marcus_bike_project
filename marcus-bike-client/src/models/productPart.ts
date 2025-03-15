export interface ProductPart {
  id: number;
  partOption: string;
  basePrice: number;
  productPartCategory: string;
}

export interface ProductPartInsert {
  partOption: string;
  basePrice: number;
  isAvailable: boolean;
  productPartCategory: string;
}
