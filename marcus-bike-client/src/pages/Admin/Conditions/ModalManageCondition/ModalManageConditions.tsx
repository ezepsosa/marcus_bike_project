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
import { postCondition } from "../../../../server/api";
import { ProductPart } from "../../../../models/productPart";
import { ModalManageConditionsProps } from "./types";
import { ProductPartCondition } from "../../../../models/productPartCondition";

export const ModalManageConditions = ({
  isOpen,
  setIsOpen,
  changeConditions,
  conditions,
  productParts,
}: ModalManageConditionsProps) => {
  async function handleNewCondition(values: ProductPartCondition) {
    try {
      if (values) {
        const conditionToAdd: ProductPartCondition = await postCondition(
          values
        );
        changeConditions([...conditions, conditionToAdd]);
        setIsOpen(false);
      }
    } catch (error) {
      console.error("Error adding product part", values);
    }
  }

  if (isOpen)
    return (
      <ModalContainer>
        <ModalContent>
          <SpanText $fontSize="1.5rem">Add new condition</SpanText>
          <Formik<ProductPartCondition>
            initialValues={{
              partId: -1,
              dependantPartId: -1,
              priceAdjustment: 0,
              isRestriction: false,
            }}
            validate={(values) => {
              const errors: Record<string, string> = {};
              if (Number(values.partId) < 0) {
                errors.partId = "Select a valid option";
              } else if (Number(values.dependantPartId) < 0) {
                errors.dependantPartId = "Select a valid option";
              } else if (
                Number(values.partId) === Number(values.dependantPartId)
              ) {
                errors.partId = "You can't select the same part twice";
                errors.dependantPartId = "You can't select the same part twice";
              } else if (
                Number(values.priceAdjustment) < 0 ||
                !values.priceAdjustment
              ) {
                errors.priceAdjustment = "Price can not be negative";
              }
              return errors;
            }}
            onSubmit={(values: ProductPartCondition) => {
              handleNewCondition(values);
            }}
          >
            {({ values, handleChange }) => (
              <FormikForm>
                <FormContainer>
                  <FieldFormikWithErrorContainer>
                    <FieldLabelContainer>
                      <LabelText>Primary part :</LabelText>
                      <FormikSelectField
                        as="select"
                        name="partId"
                        value={values.partId}
                        onChange={handleChange}
                      >
                        <Option value="" disabled>
                          -- Select a part --
                        </Option>
                        {productParts.map((part: ProductPart) => (
                          <Option key={part.id} value={part.id}>
                            {part.partOption} -{" "}
                            {part.productPartCategory
                              .toString()
                              .replace("-", " ")}{" "}
                          </Option>
                        ))}
                      </FormikSelectField>
                    </FieldLabelContainer>
                    <ErrorMessageFormik component="div" name="partId" />
                  </FieldFormikWithErrorContainer>
                  <FieldFormikWithErrorContainer>
                    <FieldLabelContainer>
                      <LabelText>Primary part :</LabelText>
                      <FormikSelectField
                        as="select"
                        name="dependantPartId"
                        value={values.dependantPartId}
                        onChange={handleChange}
                      >
                        <Option value="" disabled>
                          -- Select a part --
                        </Option>
                        {productParts.map((part: ProductPart) => (
                          <Option key={part.id} value={part.id}>
                            {part.partOption} -{" "}
                            {part.productPartCategory
                              .toString()
                              .replace("-", " ")}{" "}
                          </Option>
                        ))}
                      </FormikSelectField>
                    </FieldLabelContainer>
                    <ErrorMessageFormik
                      component="div"
                      name="dependantPartId"
                    />
                  </FieldFormikWithErrorContainer>
                  <FieldFormikWithErrorContainer>
                    <FieldLabelContainer>
                      <LabelText>Price :</LabelText>
                      <FormikInputField
                        as="input"
                        type="number"
                        min="0"
                        step="0.01"
                        name="priceAdjustment"
                        value={values.priceAdjustment}
                        onChange={handleChange}
                      />
                    </FieldLabelContainer>
                    <ErrorMessageFormik
                      component="div"
                      name="priceAdjustment"
                    />
                  </FieldFormikWithErrorContainer>
                  <FieldLabelContainer>
                    <LabelText>Restriction:</LabelText>
                    <div>
                      <FormikCheckBox
                        type="checkbox"
                        name="isRestriction"
                        checked={values.isRestriction}
                        onChange={handleChange}
                      />
                    </div>
                  </FieldLabelContainer>
                </FormContainer>
                <ButtonContainer>
                  <TableButton $backgroundColor="black" type="submit">
                    Add condition
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
