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
import {
  deleteProduct,
  getProductParts,
  getProducts,
} from "../../../server/api";
import { ProductPart } from "../../../models/productPart";
import { ModalProductParts } from "./ModalProductParts/ModalProductParts";
import { ModalManageProducts } from "./ModalManageProduct/ModalManageProduct";

/**
 * ManageProducts component allows viewing, adding, editing, and deleting products.
 */
export const ManageProducts = () => {
  const [products, setProducts] = useState<Product[]>([]);
  const [productParts, setProductParts] = useState<ProductPart[]>([]);
  const [isProductPartModalOpen, setIsProductPartModalOpen] =
    useState<boolean>(false);
  const [isProductModalOpen, setIsProductModalOpen] = useState<boolean>(false);
  const [productSelected, setProductSelected] = useState<Product>();

  /**
   * Fetch products and product parts when the component is mounted.
   */
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
  /**
   * Delete the product by calling the deleteProduct API and remove it from the list.
   */
  async function handleDeleteProduct(id: number) {
    try {
      await deleteProduct(id);
      setProducts(products.filter((p) => p.id != id));
    } catch (error) {
      console.error("Error deleting product with id", id);
    }
  }

  return (
    <Section>
      <Container>
        <PrimaryTitle $fontSize={"2.4rem"}>Manage your products</PrimaryTitle>
        <PrimaryButton
          $backgroundColor="#f83"
          onClick={() => {
            setIsProductModalOpen(true);
            setProductSelected(undefined);
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
                      <TableButton
                        type="button"
                        $backgroundColor="red"
                        onClick={() => handleDeleteProduct(product.id)}
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
        <ModalProductParts
          closeModal={setIsProductPartModalOpen}
          isOpen={isProductPartModalOpen}
          parts={productParts}
          productId={productSelected?.id}
        />
        <ModalManageProducts
          products={products}
          changeProducts={setProducts}
          product={productSelected}
          setIsOpen={setIsProductModalOpen}
          isOpen={isProductModalOpen}
        />
      </Container>
    </Section>
  );
};
