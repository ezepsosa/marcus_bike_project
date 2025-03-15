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
  isRestriction: number;
}
