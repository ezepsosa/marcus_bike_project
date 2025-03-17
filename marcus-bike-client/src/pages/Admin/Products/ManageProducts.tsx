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
} from "../../../components/styles";
import { ButtonContainer, Container, Section } from "./styles";
import { Product } from "../../../models/products";
import { getProductParts, getProducts } from "../../../server/api";
import { ProductPart } from "../../../models/productPart";
import { ModalProductParts } from "./ModalProductParts/ModalProductParts";

export const ManageProducts = () => {
  const [products, setProducts] = useState<Product[]>([]);
  const [productParts, setProductParts] = useState<ProductPart[]>([]);
  const [isModalOpen, setIsModalOpen] = useState<boolean>(false);
  const [productSelected, setProductSelected] = useState<Product>();

  useEffect(() => {
    async function loadProducts() {
      setProducts(await getProducts());
    }
    async function loadParts() {
      setProductParts(await getProductParts());
    }
    loadProducts();
    loadParts();
  }, []);

  return (
    <Section>
      <Container>
        <PrimaryTitle $fontSize={"2.4rem"}>Manage your products</PrimaryTitle>
        <PrimaryButton $backgroundColor="#f83">Add product</PrimaryButton>
        <Table>
          <Thead>
            <TrTable>
              <ThBody>Product name</ThBody>
              <ThBody>Brand</ThBody>
              <ThBody>Material</ThBody>
              <ThBody>Category</ThBody>
              <ThBody>Options</ThBody>
            </TrTable>
          </Thead>
          <Tbody>
            {products.map((product, index) => {
              return (
                <TrTable key={index}>
                  <TdBody>{product.productName}</TdBody>
                  <TdBody>{product.brand}</TdBody>
                  <TdBody>{product.material}</TdBody>
                  <TdBody>{product.category}</TdBody>
                  <TdBody>
                    <ButtonContainer>
                      <TableButton
                        onClick={() => {
                          setIsModalOpen(true);
                          setProductSelected(product);
                        }}
                      >
                        Manage parts
                      </TableButton>
                      <TableButton $color="black" $backgroundColor="#ffc107">
                        Edit product
                      </TableButton>
                    </ButtonContainer>
                  </TdBody>
                </TrTable>
              );
            })}
          </Tbody>
        </Table>
        <ModalProductParts
          closeModal={() => setIsModalOpen}
          isOpen={isModalOpen}
          parts={productParts}
          productId={productSelected?.id}
        />
      </Container>
    </Section>
  );
};
