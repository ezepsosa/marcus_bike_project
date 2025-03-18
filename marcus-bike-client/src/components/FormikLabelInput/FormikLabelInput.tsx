import { FormikInputField, FieldLabelContainer, LabelText } from "../styles";
import { FormikLabelInputProps } from "./types";

export const FormikLabelInput = ({
  label,
  value,
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
        name={label}
        value={value}
        onChange={handleChange}
      />
    </FieldLabelContainer>
  );
};
