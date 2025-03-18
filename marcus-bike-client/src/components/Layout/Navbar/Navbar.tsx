import { GeneralColors } from "../../../styles/sharedStyles";
import { SecondaryButton } from "../../styles";
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
          <LinkMenu to="/">HOME</LinkMenu>
          <LinkMenu to="/customize">CUSTOMIZE YOUR BIKE</LinkMenu>
          <LinkMenu to="#">CONTACT</LinkMenu>
          <LinkMenu to="/admin/dashboard">ADMIN</LinkMenu>
        </UnorderedList>
        <AuthNavContainer>
          <SecondaryButton>Login</SecondaryButton>
          <SecondaryButton $padding="0.3rem 0.7rem">
            <ShoppingCarCounter color="white">0</ShoppingCarCounter>
            <GiShoppingCart
              size="1.2rem"
              color={GeneralColors.textPrimary}
            ></GiShoppingCart>
          </SecondaryButton>
        </AuthNavContainer>
      </Nav>
    </Header>
  );
};
