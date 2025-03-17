import { Link } from "react-router-dom";
import {
  PrimaryButton,
  PrimaryText,
  PrimaryTitle,
} from "../../../components/styles";
import { Container, Section } from "./styles";

export const Dashboard = () => {
  return (
    <Section>
      <Container>
        <PrimaryTitle $fontSize={"2.4rem"}> Welcome Mike</PrimaryTitle>
        <PrimaryText $fontSize="1.5rem">
          Keep track of your products, parts and conditions from this dashboard
        </PrimaryText>
        <Link to="/customize">
          <PrimaryButton>Manage products</PrimaryButton>
        </Link>
      </Container>
    </Section>
  );
};
