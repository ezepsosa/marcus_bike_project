import { ProductPart } from "../../../../models/productPart";
import { ProductPartCondition } from "../../../../models/productPartCondition";

export interface ModalManageConditionsProps {
  isOpen: boolean;
  setIsOpen: (value: boolean) => void;
  changeConditions: (value: ProductPartCondition[]) => void;
  conditions: ProductPartCondition[];
  productParts: ProductPart[];
}
