import { Link } from "react-router-dom";
import {
  PrimaryButton,
  PrimaryText,
  PrimaryTitle,
} from "../../../components/styles";
import { Container, Section } from "./styles";
import { useUserAuth } from "../../../context/User/useUserAuth";

export const Dashboard = () => {
  const { username } = useUserAuth();
  return (
    <Section>
      <Container>
        <PrimaryTitle $fontSize={"2.4rem"}>
          {" "}
          Welcome{" "}
          {username
            ? username?.charAt(0).toUpperCase() + username?.slice(1)
            : null}
        </PrimaryTitle>
        <PrimaryText $fontSize="1.5rem">
          Keep track of your products, parts and conditions from this dashboard
        </PrimaryText>
        <Link to="/admin/manage/products">
          <PrimaryButton>Manage products</PrimaryButton>
        </Link>
        <Link to="/admin/manage/productparts">
          <PrimaryButton>Manage product parts</PrimaryButton>
        </Link>
        <Link to="/admin/manage/conditions">
          <PrimaryButton>Manage conditions</PrimaryButton>
        </Link>
      </Container>
    </Section>
  );
};
