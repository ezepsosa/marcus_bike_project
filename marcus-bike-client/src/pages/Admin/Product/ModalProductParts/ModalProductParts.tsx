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

/**
 * Modal component to manage product parts, allowing to add and remove parts from a product.
 */
export const ModalProductParts = ({
  isOpen,
  productId,
  parts,
  closeModal,
}: ModalProductPartsProps) => {
  const [productParts, setProductParts] = useState<ProductPart[]>([]);

  /**
   * Fetch product parts when the product ID changes and store them in the state.
   */
  useEffect(() => {
    async function loadProductParts(id: number) {
      setProductParts(await getPartsFromProduct(id));
    }
    if (productId) loadProductParts(productId);
  }, [productId]);

  /**
   * Async function to delete a part from the product and update the product parts list.
   */
  async function deletePart(partId: number) {
    try {
      if (productId) await deletePartFromProduct(partId, productId);
      setProductParts((prev) => prev.filter((part) => part.id != partId));
    } catch (error) {
      console.error("Error deleting part:", partId);
    }
  }

  /**
   * Async function to handle submitting a part to add it to the product and update the list.
   */
  async function handleSubmit(part: ProductPart) {
    try {
      if (productId) await postPartFromProduct(productId, part.id);
      setProductParts((prev) => [...prev, part]);
    } catch (error) {
      console.error("Error adding part:", part);
    }
  }

  /**
   * Filters available parts that are not already associated with the current product.
   */
  const availableOptions = parts.filter(
    (part) =>
      !productParts.map((productPart) => productPart.id).includes(part.id)
  );

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
            initialValues={{
              selectedPart: "",
            }}
            onSubmit={(values) => {
              const selectedId = Number(values.selectedPart);
              if (!selectedId) return;
              const selectedPart = parts.find((part) => part.id === selectedId);
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
                    disabled={availableOptions.length === 0}
                    as="select"
                    name="selectedPart"
                    value={values.selectedPart}
                    onChange={handleChange}
                  >
                    <Option value="" disabled>
                      -- Select a part --
                    </Option>
                    {availableOptions.map((option) => (
                      <Option key={option.id} value={option.id.toString()}>
                        {option.partOption} -{" "}
                        {String(option.productPartCategory)
                          .replace("_", " ")
                          .toLowerCase()}{" "}
                      </Option>
                    ))}
                  </FormikSelectField>
                  <ButtonContainer>
                    <TableButton
                      type="submit"
                      $backgroundColor="black"
                      disabled={
                        availableOptions.length === 0 || !values.selectedPart
                      }
                    >
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
