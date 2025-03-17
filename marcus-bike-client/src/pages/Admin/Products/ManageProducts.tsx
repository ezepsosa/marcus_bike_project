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
import { getProducts } from "../../../server/api";

export const ManageProducts = () => {
  const [products, setProducts] = useState<Product[]>([]);

  useEffect(() => {
    async function loadProducts() {
      setProducts(await getProducts());
    }
    loadProducts();
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
                      <TableButton>Manage parts</TableButton>
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
      </Container>
    </Section>
  );
};
