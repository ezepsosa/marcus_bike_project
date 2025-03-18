import { FormikHandlers } from "formik";

export interface FormikLabelInputProps {
  label: string;
  value: string;
  handleChange: FormikHandlers["handleChange"];
}
