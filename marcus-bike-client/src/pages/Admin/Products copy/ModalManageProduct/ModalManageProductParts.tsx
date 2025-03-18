import { Formik } from "formik";
import {
  ButtonContainer,
  ErrorMessageFormik,
  FieldFormikWithErrorContainer,
  FormContainer,
  FormikForm,
  FormikInputField,
  FormikSelectField,
  FieldLabelContainer,
  LabelText,
  ModalContainer,
  ModalContent,
  Option,
  SpanText,
  TableButton,
  FormikCheckBox,
} from "../../../../components/styles";
import { postProductPart, updateProductPart } from "../../../../server/api";
import { ModalManageProductPartsProps } from "./types";
import { ProductPart, ProductPartInsert } from "../../../../models/productPart";
import { ProductPartCategory } from "../../../../models/productPartCategory";
import { ChangeEvent } from "react";

export const ModalManageProductParts = ({
  productPart,
  isOpen,
  productParts,
  setIsOpen,
  changeProductParts,
}: ModalManageProductPartsProps) => {
  async function handleNewProduc(values: ProductPartInsert) {
    try {
      if (values) {
        const productPartToAdd: ProductPart = await postProductPart(values);
        changeProductParts([...productParts, productPartToAdd]);
        setIsOpen(false);
      }
    } catch (error) {
      console.error("Error adding product part", values);
    }
  }
  async function handleEditProduc(values: ProductPartInsert) {
    try {
      const { ...productPartInsert } = values as ProductPartInsert;
      Reflect.deleteProperty(productPartInsert, "id");
      if (values && productPart?.id) {
        const productToAdd: ProductPart = await updateProductPart(
          productPartInsert,
          productPart.id
        );
        changeProductParts(
          productParts.map((p: ProductPart) =>
            p.id === productPart.id ? productToAdd : p
          )
        );
        setIsOpen(false);
      }
    } catch (error) {
      console.error("Error updating product", values);
    }
  }

  if (isOpen)
    return (
      <ModalContainer>
        <ModalContent>
          <SpanText $fontSize="1.5rem">
            {productPart ? "Edit" : "Add new"} Product Part
          </SpanText>
          <Formik<ProductPartInsert>
            initialValues={
              productPart ?? {
                partOption: "",
                basePrice: 0,
                isAvailable: false,
                productPartCategory: ProductPartCategory.CHAIN_TYPE,
              }
            }
            validate={(values) => {
              const errors: Record<string, string> = {};
              if (!values.partOption) {
                errors.partOption = "This field cannot be empty";
              } else if (isNaN(Number(values.basePrice))) {
                errors.basePrice = "Enter a valid number";
              }

              return errors;
            }}
            onSubmit={(values: ProductPartInsert) => {
              productPart ? handleEditProduc(values) : handleNewProduc(values);
            }}
          >
            {({ values, handleChange }) => (
              <FormikForm>
                <FormContainer>
                  <FieldFormikWithErrorContainer>
                    <FieldLabelContainer>
                      <LabelText>Option :</LabelText>
                      <FormikInputField
                        as="input"
                        name="partOption"
                        value={values.partOption}
                        onChange={handleChange}
                      />
                    </FieldLabelContainer>
                    <ErrorMessageFormik component="div" name="partOption" />
                  </FieldFormikWithErrorContainer>
                  <FieldFormikWithErrorContainer>
                    <FieldLabelContainer>
                      <LabelText>Price :</LabelText>
                      <FormikInputField
                        as="input"
                        type="number"
                        min="0"
                        step="0.01"
                        name="basePrice"
                        value={values.basePrice}
                        onChange={handleChange}
                      />
                    </FieldLabelContainer>
                    <ErrorMessageFormik component="div" name="basePrice" />
                  </FieldFormikWithErrorContainer>
                  <FieldLabelContainer>
                    <LabelText>Category :</LabelText>
                    <FormikSelectField
                      as="select"
                      name="productPartCategory"
                      value={values.productPartCategory}
                      onChange={(e: ChangeEvent<HTMLSelectElement>) => {
                        handleChange(e);
                        const selectedValue = e.target.value as string;

                        if (
                          Object.values(ProductPartCategory).includes(
                            selectedValue as unknown as ProductPartCategory
                          )
                        ) {
                          values.productPartCategory =
                            selectedValue as unknown as ProductPartCategory;
                        }
                      }}
                    >
                      <Option value="" disabled>
                        -- Select a part --
                      </Option>
                      {Object.keys(ProductPartCategory)
                        .filter((category) => isNaN(Number(category)))
                        .map((category) => (
                          <Option key={category} value={category}>
                            {category.replace("_", " ").toLowerCase()}{" "}
                          </Option>
                        ))}
                    </FormikSelectField>
                  </FieldLabelContainer>
                  <FieldLabelContainer>
                    <LabelText>Available:</LabelText>
                    <div>
                      <FormikCheckBox
                        type="checkbox"
                        name="isAvailable"
                        checked={values.isAvailable}
                        onChange={handleChange}
                      />
                    </div>
                  </FieldLabelContainer>
                </FormContainer>
                <ButtonContainer>
                  <TableButton $backgroundColor="black" type="submit">
                    {productPart ? "Edit" : "Add"} product part
                  </TableButton>
                  <TableButton
                    type="button"
                    $backgroundColor="gray"
                    onClick={() => setIsOpen(false)}
                  >
                    Close
                  </TableButton>
                </ButtonContainer>
              </FormikForm>
            )}
          </Formik>
        </ModalContent>
      </ModalContainer>
    );
};
