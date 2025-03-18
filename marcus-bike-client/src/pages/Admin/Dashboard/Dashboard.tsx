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
        <Link to="/admin/manage/products">
          <PrimaryButton>Manage products</PrimaryButton>
        </Link>
        <Link to="/admin/manage/productparts">
          <PrimaryButton>Manage product parts</PrimaryButton>
        </Link>
      </Container>
    </Section>
  );
};
