import { ModalContainer, ModalContent } from "./styles";
import { ModalProductPartsProps } from "./types";

export const ModalProductParts = ({
  isOpen,
  productId,
  parts,
}: ModalProductPartsProps) => {
  console.log(productId);
  console.log(parts);
  if (isOpen)
    return (
      <ModalContainer>
        <ModalContent></ModalContent>
      </ModalContainer>
    );
};
