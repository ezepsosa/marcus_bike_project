import { useEffect, useState } from "react";
import {
  FormikForm,
  FormikSelectField,
  LabelText,
  Option,
  SelectContainer,
  Table,
  TableButton,
  Tbody,
  TdBody,
  ThBody,
  Thead,
  TrTable,
  ModalContainer,
  ModalContent,
  ButtonContainer,
} from "../../../../components/styles";
import { ModalProductPartsProps } from "./types";
import { ProductPart } from "../../../../models/productPart";
import {
  deletePartFromProduct,
  getPartsFromProduct,
  postPartFromProduct,
} from "../../../../server/api";
import { Formik } from "formik";

export const ModalProductParts = ({
  isOpen,
  productId,
  parts,
  closeModal,
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

  async function handleSubmit(part: ProductPart) {
    try {
      if (productId) await postPartFromProduct(productId, part.id);
      setProductParts((prev) => [...prev, part]);
    } catch (error) {
      console.error("Error adding part:", part.id);
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
              {productParts.map((part) => {
                return (
                  <TrTable key={part.id}>
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
          <Formik
            initialValues={{ selectedPart: "" }}
            onSubmit={(values) => {
              const selectedPart = parts.find(
                (part) => part.id === Number(values.selectedPart)
              );
              if (selectedPart) {
                handleSubmit(selectedPart);
              }
            }}
          >
            {({ values, handleChange }) => (
              <FormikForm>
                <SelectContainer>
                  <LabelText $color="white" $fontSize="1.2rem">
                    Select a part to add:
                  </LabelText>
                  <FormikSelectField
                    as="select"
                    name="selectedPart"
                    value={values.selectedPart}
                    onChange={handleChange}
                  >
                    {parts
                      .filter(
                        (part) =>
                          !productParts
                            .map((productPart) => productPart.id)
                            .includes(part.id)
                      )
                      .map((option) => (
                        <Option key={option.id} value={option.id.toString()}>
                          {option.partOption} -{" "}
                          {String(option.productPartCategory)
                            .replace("_", " ")
                            .toLowerCase()}
                        </Option>
                      ))}
                  </FormikSelectField>
                  <ButtonContainer>
                    <TableButton type="submit" $backgroundColor="black">
                      Add part
                    </TableButton>
                    <TableButton
                      type="button"
                      $backgroundColor="gray"
                      onClick={() => closeModal(false)}
                    >
                      Close
                    </TableButton>
                  </ButtonContainer>
                </SelectContainer>
              </FormikForm>
            )}
          </Formik>
        </ModalContent>
      </ModalContainer>
    );
};
