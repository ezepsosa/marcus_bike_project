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
import { Product } from "../../../../models/products";
import { FormikLabelInput } from "../../../../components/FormikLabelInput/FormikLabelInput";

export const ModalManageProducts = ({
  product,
  isOpen,
  setIsOpen,
}: ModalManageProductProps) => {
  function handleNewProduc(values: Product) {
    console.log(values);
  }
  function handleEditProduc(values: Product) {
    console.log(values);
  }
  console.log(product);
  if (isOpen)
    return (
      <ModalContainer>
        <ModalContent>
          <SpanText $fontSize="1.5rem">
            {product ? "Edit" : "Add new"} Product
          </SpanText>
          <Formik<Product>
            initialValues={
              product ?? {
                id: -1,
                productName: "",
                brand: "",
                category: "",
                material: "",
                imageUrl: "",
              }
            }
            validate={(values) => {
              const errors: Record<string, string> = {};
              console.log("sadsadasd");
              Object.keys(values).forEach((attribute) =>
                values[attribute as keyof Product] == ""
                  ? (errors[attribute] = "This field cannot be empty")
                  : null
              );
              return errors;
            }}
            onSubmit={(values: Product) => {
              product ? handleNewProduc(values) : handleEditProduc(values);
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
                            value={String(values[fieldName as keyof Product])}
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
