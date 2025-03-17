export interface Product {
  id: number;
  productName: string;
  brand: string;
  category: string;
  material: string;
  imageUrl: string;
}

export interface ProductInsert {
  productName: string;
  brand: string;
  category: string;
  material: string;
  imageUrl: string;
}
