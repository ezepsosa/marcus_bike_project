import { ProductPart } from "../../../../models/productPart";

export interface ModalProductPartsProps {
  productId?: number;
  parts: ProductPart[];
  isOpen: boolean;
  closeModal: () => void;
}
