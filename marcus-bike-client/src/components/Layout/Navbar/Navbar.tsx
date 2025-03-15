import { GeneralColors } from "../../../styles/sharedStyles";
import { PrimaryButton } from "../../styles";
import {
  AuthNavContainer,
  Header,
  LinkMenu,
  Nav,
  ShoppingCarCounter,
  TextLogo,
  UnorderedList,
} from "./styles";
import { GiShoppingCart } from "react-icons/gi";

export const Navbar = () => {
  return (
    <Header>
      <Nav>
        <TextLogo>MarcuShop</TextLogo>
        <UnorderedList>
          <LinkMenu href="/">HOME</LinkMenu>
          <LinkMenu href="/customize">CUSTOMIZE YOUR BIKE</LinkMenu>
          <LinkMenu href="#">CONTACT</LinkMenu>
        </UnorderedList>
        <AuthNavContainer>
          <PrimaryButton>Login</PrimaryButton>
          <PrimaryButton $padding="0.3rem 0.7rem">
            <ShoppingCarCounter color="white">0</ShoppingCarCounter>
            <GiShoppingCart
              size="1.2rem"
              color={GeneralColors.linkPrimary}
            ></GiShoppingCart>
          </PrimaryButton>
        </AuthNavContainer>
      </Nav>
    </Header>
  );
};
