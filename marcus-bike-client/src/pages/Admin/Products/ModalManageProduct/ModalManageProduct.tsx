import { Formik } from "formik";
import {
  ButtonContainer,
  ErrorMessageFormik,
  FieldFormikWithErrorContainer,
  FormContainer,
  FormikForm,
  ModalContainer,
  ModalContent,
  SpanText,
  TableButton,
} from "../../../../components/styles";
import { ModalManageProductProps } from "./types";
import { ProductInsert } from "../../../../models/products";
import { FormikLabelInput } from "../../../../components/FormikLabelInput/FormikLabelInput";
import { postProduct, updateProduct } from "../../../../server/api";

export const ModalManageProducts = ({
  product,
  isOpen,
  setIsOpen,
}: ModalManageProductProps) => {
  async function handleNewProduc(values: ProductInsert) {
    try {
      if (values) await postProduct(values);
      alert("product created");
      setIsOpen(false);
    } catch (error) {
      console.error("Error adding product", values);
    }
  }
  async function handleEditProduc(values: ProductInsert) {
    try {
      const { ...productInsert } = product as ProductInsert;
      Reflect.deleteProperty(productInsert, "id");
      if (values && product?.id) await updateProduct(productInsert, product.id);
      alert("product updated");
      setIsOpen(false);
    } catch (error) {
      console.error("Error updating product", values);
    }
  }

  if (isOpen)
    return (
      <ModalContainer>
        <ModalContent>
          <SpanText $fontSize="1.5rem">
            {product ? "Edit" : "Add new"} Product
          </SpanText>
          <Formik<ProductInsert>
            initialValues={
              product ?? {
                productName: "",
                brand: "",
                category: "",
                material: "",
                imageUrl: "",
              }
            }
            validate={(values) => {
              const errors: Record<string, string> = {};
              Object.keys(values).forEach((attribute) =>
                values[attribute as keyof ProductInsert] == ""
                  ? (errors[attribute] = "This field cannot be empty")
                  : null
              );
              return errors;
            }}
            onSubmit={(values: ProductInsert) => {
              product ? handleEditProduc(values) : handleNewProduc(values);
            }}
          >
            {({ values, handleChange }) => (
              <FormikForm>
                <FormContainer>
                  {Object.keys(values)
                    .filter((fieldName) => fieldName != "id")
                    .map((fieldName: string) => {
                      return (
                        <FieldFormikWithErrorContainer key={fieldName}>
                          <FormikLabelInput
                            key={fieldName}
                            handleChange={handleChange}
                            label={fieldName}
                            value={values[fieldName as keyof ProductInsert]}
                          />
                          <ErrorMessageFormik
                            component="div"
                            name={fieldName}
                          />
                        </FieldFormikWithErrorContainer>
                      );
                    })}
                </FormContainer>
                <ButtonContainer>
                  <TableButton $backgroundColor="black" type="submit">
                    {product ? "Edit" : "Add"} product
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
