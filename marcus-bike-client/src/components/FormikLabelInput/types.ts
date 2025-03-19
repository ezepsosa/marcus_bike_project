import { FormikHandlers } from "formik";

export interface FormikLabelInputProps {
  label: string;
  value: string;
  type?: string;
  handleChange: FormikHandlers["handleChange"];
}
