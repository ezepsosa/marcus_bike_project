import { useEffect, useState } from "react";
import {
  PrimaryButton,
  PrimaryTitle,
  TableButton,
  Table,
  Tbody,
  TdBody,
  ThBody,
  Thead,
  TrTable,
  ButtonContainer,
} from "../../../components/styles";
import { Container, Section } from "./styles";
import { deleteProductPart, getProductParts } from "../../../server/api";
import { ProductPart } from "../../../models/productPart";
import { ModalManageProductParts } from "./ModalManageProduct/ModalManageProductParts";

export const ManageProductParts = () => {
  const [productParts, setProductParts] = useState<ProductPart[]>([]);
  const [isProductModalOpen, setIsProductModalOpen] = useState<boolean>(false);
  const [productPartSelected, setProductPartSelected] = useState<ProductPart>();

  useEffect(() => {
    async function loadParts() {
      setProductParts(await getProductParts());
    }
    loadParts();
  }, []);

  async function handleDeleteProductPart(id: number) {
    try {
      await deleteProductPart(id);
      setProductParts(productParts.filter((p: ProductPart) => p.id != id));
    } catch (error) {
      console.error("Error deleting product part with id", id);
    }
  }

  return (
    <Section>
      <Container>
        <PrimaryTitle $fontSize={"2.4rem"}>Manage your parts</PrimaryTitle>
        <PrimaryButton
          $backgroundColor="#f83"
          onClick={() => {
            setIsProductModalOpen(true);
            setProductPartSelected(undefined);
          }}
        >
          Add part
        </PrimaryButton>
        <Table>
          <Thead>
            <TrTable>
              <ThBody>Category</ThBody>
              <ThBody>Name</ThBody>
              <ThBody>Price</ThBody>
              <ThBody>Available</ThBody>
              <ThBody>Actions</ThBody>
            </TrTable>
          </Thead>
          <Tbody>
            {productParts.map((productPart: ProductPart, index) => {
              return (
                <TrTable key={index}>
                  <TdBody>
                    {productPart.productPartCategory
                      .toString()
                      .replace("_", " ")}
                  </TdBody>
                  <TdBody>{productPart.partOption}</TdBody>
                  <TdBody>{productPart.basePrice}</TdBody>
                  <TdBody>{String(productPart.isAvailable)}</TdBody>
                  <TdBody>
                    <ButtonContainer>
                      <TableButton
                        $color="black"
                        $backgroundColor="#ffc107"
                        type="button"
                        onClick={() => {
                          setIsProductModalOpen(true);
                          setProductPartSelected(productPart);
                        }}
                      >
                        Edit part
                      </TableButton>
                      <TableButton
                        type="button"
                        $backgroundColor="red"
                        onClick={() => handleDeleteProductPart(productPart.id)}
                      >
                        Delete
                      </TableButton>
                    </ButtonContainer>
                  </TdBody>
                </TrTable>
              );
            })}
          </Tbody>
        </Table>
        <ModalManageProductParts
          productPart={productPartSelected}
          changeProductParts={setProductParts}
          setIsOpen={setIsProductModalOpen}
          isOpen={isProductModalOpen}
          productParts={productParts}
        />
      </Container>
    </Section>
  );
};
