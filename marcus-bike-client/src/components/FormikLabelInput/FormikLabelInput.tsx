import { FormikInputField, FieldLabelContainer, LabelText } from "../styles";
import { FormikLabelInputProps } from "./types";

// Component for rendering a labeled input field using Formik
export const FormikLabelInput = ({
  label,
  value,
  type,
  handleChange,
}: FormikLabelInputProps) => {
  return (
    <FieldLabelContainer>
      <LabelText>
        {label
          .replace(/([A-Z])/g, " $1")
          .replace(/^./, (str) => str.toUpperCase())}
        :
      </LabelText>
      <FormikInputField
        as="input"
        type={type}
        name={label}
        value={value}
        onChange={handleChange}
      />
    </FieldLabelContainer>
  );
};
