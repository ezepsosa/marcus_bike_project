import { useCart } from "../../../context/Cart/useCart";
import { useUserAuth } from "../../../context/User/useUserAuth";
import { GeneralColors } from "../../../styles/sharedStyles";
import { LinkText, SecondaryButton } from "../../styles";
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
  const { token, role, logout } = useUserAuth();
  const { cart } = useCart();
  return (
    <Header>
      <Nav>
        <TextLogo>MarcuShop</TextLogo>
        <UnorderedList>
          <LinkMenu to="/">HOME</LinkMenu>
          <LinkMenu to="/customize">CUSTOMIZE YOUR BIKE</LinkMenu>
          <LinkMenu to="#">CONTACT</LinkMenu>
          {token && role == "ADMIN" ? (
            <LinkMenu to="/admin/dashboard">ADMIN</LinkMenu>
          ) : null}
        </UnorderedList>

        <AuthNavContainer>
          {!token ? (
            <LinkText to="/login">
              {" "}
              <SecondaryButton>Login</SecondaryButton>{" "}
            </LinkText>
          ) : null}
          {token ? (
            <LinkText to="/login">
              {" "}
              <SecondaryButton onClick={logout}>Logout</SecondaryButton>{" "}
            </LinkText>
          ) : null}
          <SecondaryButton $padding="0.3rem 0.7rem">
            <ShoppingCarCounter color="white">{cart.length}</ShoppingCarCounter>
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
