import {
  ErrorMessageFormik,
  FieldFormikWithErrorContainer,
  FormContainer,
  FormikForm,
  PrimaryButton,
  PrimaryText,
  PrimaryTitle,
} from "../../components/styles";
import { Container, Section } from "./styles";
import { LoginUser } from "../../models/user";
import { Formik, FormikHelpers } from "formik";
import { FormikLabelInput } from "../../components/FormikLabelInput/FormikLabelInput";
import { authenticateUser } from "../../server/api";
import { useNavigate } from "react-router-dom";

export const Login = () => {
  const navigate = useNavigate();
  async function handleLogin(
    loginUser: LoginUser,
    setErrors: FormikHelpers<LoginUser>["setErrors"]
  ) {
    try {
      console.log(await authenticateUser(loginUser));
      navigate("/");
    } catch (error: unknown) {
      if (typeof error === "object" && error !== null && "status" in error) {
        console.error("Status code:", (error as { status: number }).status);
        setErrors({
          username: "Invalid credentials",
          password: "Invalid credentials",
        });
      }
    }
  }
  return (
    <Section>
      <Container>
        <PrimaryTitle $fontSize={"2.4rem"}> Login</PrimaryTitle>
        <PrimaryText>Insert your credentials.</PrimaryText>
        <Formik<LoginUser>
          initialValues={{
            username: "",
            password: "",
          }}
          validate={(values) => {
            const errors: Record<string, string> = {};
            if (!values.password || values.password == "") {
              errors.password = "This field cannot be empty";
            }
            if (!values.username || values.username == "") {
              errors.username = "This field cannot be empty";
            }
            return errors;
          }}
          onSubmit={(values: LoginUser, { setErrors }) => {
            handleLogin(values, setErrors);
          }}
        >
          {({ values, handleChange }) => (
            <FormikForm $width="50%">
              <FormContainer>
                <FieldFormikWithErrorContainer>
                  <FormikLabelInput
                    key="username"
                    handleChange={handleChange}
                    label="username"
                    value={values.username}
                  />
                  <ErrorMessageFormik component="div" name="username" />
                </FieldFormikWithErrorContainer>
                <FieldFormikWithErrorContainer>
                  <FormikLabelInput
                    key="password"
                    handleChange={handleChange}
                    label="password"
                    value={values.password}
                  />
                  <ErrorMessageFormik component="div" name="password" />
                </FieldFormikWithErrorContainer>
              </FormContainer>
              <PrimaryButton type="submit">Login</PrimaryButton>
            </FormikForm>
          )}
        </Formik>
      </Container>
    </Section>
  );
};
