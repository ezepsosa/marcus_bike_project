import { PrimaryButton } from "../styles";
import { Container, LinkMenu, Nav, TextLogo, UnorderedList } from "./styles";

export const Navbar = () => {
  return (
    <Container>
      <Nav>
        <TextLogo>MarcuShop</TextLogo>
        <UnorderedList>
          <LinkMenu href="/">HOME</LinkMenu>
          <LinkMenu href="/customize">CUSTOMIZE YOUR BIKE</LinkMenu>
          <LinkMenu href="#">CONTACT</LinkMenu>
        </UnorderedList>
        <PrimaryButton>Login</PrimaryButton>
      </Nav>
    </Container>
  );
};
