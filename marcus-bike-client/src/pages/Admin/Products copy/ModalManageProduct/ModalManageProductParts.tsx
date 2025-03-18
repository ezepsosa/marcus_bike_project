import { Formik } from "formik";
import {
  ButtonContainer,
  FieldFormikWithErrorContainer,
  FormContainer,
  FormikForm,
  FormikInputField,
  FormikSelectField,
  InputLabelContainer,
  LabelText,
  ModalContainer,
  ModalContent,
  Option,
  SpanText,
  TableButton,
} from "../../../../components/styles";
import { postProductPart, updateProductPart } from "../../../../server/api";
import { ModalManageProductPartsProps } from "./types";
import { ProductPart, ProductPartInsert } from "../../../../models/productPart";
import { ProductPartCategory } from "../../../../models/productPartCategory";

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
              Object.keys(values).forEach((attribute) =>
                values[attribute as keyof ProductPartInsert] == ""
                  ? (errors[attribute] = "This field cannot be empty")
                  : null
              );
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
                    <InputLabelContainer>
                      <LabelText>Option :</LabelText>
                      <FormikInputField
                        as="input"
                        name="partOption"
                        value={values.partOption}
                        onChange={handleChange}
                      />
                    </InputLabelContainer>
                  </FieldFormikWithErrorContainer>
                  <FieldFormikWithErrorContainer>
                    <FormikSelectField
                      as="select"
                      name="productPartCategory"
                      value={values.productPartCategory}
                      onChange={handleChange}
                    >
                      <Option value="" disabled>
                        -- Select a part --
                      </Option>
                      {Object.keys(ProductPartCategory)
                        .filter((category) => isNaN(Number(category)))
                        .map((category) => (
                          <Option
                            key={category}
                            value={
                              ProductPartCategory[
                                category as keyof typeof ProductPartCategory
                              ]
                            }
                          >
                            {category.replace("_", " ").toLowerCase()}{" "}
                          </Option>
                        ))}
                    </FormikSelectField>
                  </FieldFormikWithErrorContainer>
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
