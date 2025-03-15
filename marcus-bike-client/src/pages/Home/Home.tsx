import { Link } from "react-router-dom";
import {
  PrimaryButton,
  PrimaryText,
  PrimaryTitle,
} from "../../components/styles";
import { Container, Section } from "./styles";

export const Home = () => {
  return (
    <Section>
      <Container>
        <PrimaryTitle $fontSize={"2.4rem"}> Your bike, your style</PrimaryTitle>
        <PrimaryText>
          Customize every detail with high quality materials.
        </PrimaryText>
        <Link to="/customize">
          <PrimaryButton>Customize now</PrimaryButton>
        </Link>
      </Container>
    </Section>
  );
};
