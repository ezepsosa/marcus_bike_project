import { ProductPart } from "../../../../models/productPart";

export interface ModalManageProductPartsProps {
  productPart?: ProductPart;
  isOpen: boolean;
  setIsOpen: (value: boolean) => void;
  changeProductParts: (value: ProductPart[]) => void;
  productParts: ProductPart[];
}
