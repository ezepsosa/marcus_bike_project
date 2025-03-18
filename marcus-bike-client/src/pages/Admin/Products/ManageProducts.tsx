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
import { Product } from "../../../models/products";
import { getProductParts, getProducts } from "../../../server/api";
import { ProductPart } from "../../../models/productPart";
import { ModalProductParts } from "./ModalProductParts/ModalProductParts";
import { ModalManageProducts } from "./ModalManageProduct/ModalManageProduct";

export const ManageProducts = () => {
  const [products, setProducts] = useState<Product[]>([]);
  const [productParts, setProductParts] = useState<ProductPart[]>([]);
  const [isProductPartModalOpen, setIsProductPartModalOpen] =
    useState<boolean>(false);
  const [isProductModalOpen, setIsProductModalOpen] = useState<boolean>(false);
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
        <PrimaryButton
          $backgroundColor="#f83"
          onClick={() => {
            setIsProductModalOpen(true);
            () => setProductSelected(undefined);
          }}
        >
          Add product
        </PrimaryButton>
        <Table>
          <Thead>
            <TrTable>
              <ThBody>Product name</ThBody>
              <ThBody>Brand</ThBody>
              <ThBody>Material</ThBody>
              <ThBody>Category</ThBody>
              <ThBody>Actions</ThBody>
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
                          setIsProductPartModalOpen(true);
                          setProductSelected(product);
                        }}
                      >
                        Manage parts
                      </TableButton>
                      <TableButton
                        $color="black"
                        $backgroundColor="#ffc107"
                        type="button"
                        onClick={() => {
                          setIsProductModalOpen(true);
                          setProductSelected(product);
                        }}
                      >
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
          closeModal={setIsProductPartModalOpen}
          isOpen={isProductPartModalOpen}
          parts={productParts}
          productId={productSelected?.id}
        />
        <ModalManageProducts
          product={productSelected}
          setIsOpen={setIsProductModalOpen}
          isOpen={isProductModalOpen}
        />
      </Container>
    </Section>
  );
};
