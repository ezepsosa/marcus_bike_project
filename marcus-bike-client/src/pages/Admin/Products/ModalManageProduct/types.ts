import { Product } from "../../../../models/products";

export interface ModalManageProductProps {
  product?: Product;
  isOpen: boolean;
  setIsOpen: (value: boolean) => void;
}
