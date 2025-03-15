import styled from "styled-components";
import { GeneralColors, fonts } from "../../../styles/sharedStyles";
import { Link } from "react-router-dom";

export const Header = styled.header`
  display: flex;
  justify-content: center;
  background-color: ${GeneralColors.headerFooter};
  width: 100%;
  height: 5rem;
  border-bottom: 0.25px solid #444;
`;

export const Nav = styled.nav`
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  height: 100%;
  padding-inline: 2rem;
`;

export const UnorderedList = styled.ul`
  display: flex;
  gap: 1rem;
`;

export const LinkMenu = styled(Link)`
  color: ${GeneralColors.textSecondary};
  font-weight: 400;
  text-decoration: none;
  font-size: 0.9rem;
  font-family: ${fonts.montserratFontFamily};
  &:hover {
    color: #fff;
  }
`;

export const TextLogo = styled.h1`
  color: ${GeneralColors.textHighlight};
  font-family: ${fonts.montserratFontFamily};
  font-weight: 700;
`;

export const ShoppingCarCounter = styled.span`
  position: absolute;
  top: -0.6rem;
  right: -0.6rem;
  background-color: red;
  padding: 0.2rem;
  border-radius: 2rem;
`;

export const AuthNavContainer = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 1rem;
`;
