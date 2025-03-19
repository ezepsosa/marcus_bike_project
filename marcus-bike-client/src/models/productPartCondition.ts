import { ProductPart } from "./productPart";

export interface ProductPartCondition {
  partId: number;
  dependantPartId: number;
  priceAdjustment: number;
  isRestriction: boolean;
}

export interface ProductPartConditionInsert {
  partId: number;
  dependantPartId: number;
  priceAdjustment: number;
  isRestriction: boolean;
}

export interface ProductPartConditionExtended {
  part: ProductPart;
  dependantPart: ProductPart;
  priceAdjustment: number;
  isRestriction: boolean;
}
