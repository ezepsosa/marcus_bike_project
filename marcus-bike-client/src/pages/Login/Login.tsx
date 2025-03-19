import {
  PrimaryButton,
  PrimaryText,
  PrimaryTitle,
} from "../../components/styles";
import { Container, Section } from "./styles";

export const Login = () => {
  return (
    <Section>
      <Container>
        <PrimaryTitle $fontSize={"2.4rem"}> Login</PrimaryTitle>
        <PrimaryText>Insert your credentials.</PrimaryText>
        <PrimaryButton>Login</PrimaryButton>
      </Container>
    </Section>
  );
};
