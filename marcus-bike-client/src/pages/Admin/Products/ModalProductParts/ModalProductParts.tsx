import { useEffect, useState } from "react";
import {
  Table,
  TableButton,
  Tbody,
  TdBody,
  ThBody,
  Thead,
  TrTable,
} from "../../../../components/styles";
import { ModalContainer, ModalContent } from "./styles";
import { ModalProductPartsProps } from "./types";
import { ProductPart } from "../../../../models/productPart";
import {
  deletePartFromProduct,
  getPartsFromProduct,
} from "../../../../server/api";

export const ModalProductParts = ({
  isOpen,
  productId,
  parts,
}: ModalProductPartsProps) => {
  const [productParts, setProductParts] = useState<ProductPart[]>([]);
  useEffect(() => {
    async function loadProductParts(id: number) {
      setProductParts(await getPartsFromProduct(id));
    }
    if (productId) loadProductParts(productId);
  }, [productId]);

  async function deletePart(partId: number) {
    try {
      if (productId) await deletePartFromProduct(partId, productId);
      setProductParts(productParts.filter((part) => part.id != partId));
    } catch (error) {
      console.error("Error deleting part:", partId);
    }
  }

  if (isOpen)
    return (
      <ModalContainer>
        <ModalContent>
          <Table $width="80%">
            <Thead>
              <TrTable>
                <ThBody>Category</ThBody>
                <ThBody>Base price</ThBody>
                <ThBody>Description</ThBody>
                <ThBody>Actions</ThBody>
              </TrTable>
            </Thead>
            <Tbody>
              {productParts.map((part, index) => {
                return (
                  <TrTable key={index}>
                    <TdBody>
                      {String(part.productPartCategory)
                        .replace("_", " ")
                        .toLowerCase()}
                    </TdBody>
                    <TdBody>{part.basePrice}</TdBody>
                    <TdBody>{part.partOption}</TdBody>
                    <TdBody>
                      <TableButton
                        onClick={() => deletePart(part.id)}
                        $backgroundColor="red"
                      >
                        Delete
                      </TableButton>
                    </TdBody>
                  </TrTable>
                );
              })}
            </Tbody>
          </Table>
        </ModalContent>
      </ModalContainer>
    );
};
