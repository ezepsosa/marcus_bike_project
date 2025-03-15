import styled from "styled-components";
import { colors, fonts } from "../../../styles/sharedStyles";

export const Header = styled.header``;

export const Container = styled.div`
  display: flex;
  justify-content: center;
  background-color: ${colors.primary};
  width: 100%;
  height: 4rem;
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

export const LinkMenu = styled.a`
  color: ${colors.linkPrimary};
  font-weight: 400;
  text-decoration: none;
  font-family: ${fonts.montserratFontFamily};
  &:hover {
    color: #fff;
  }
`;

export const TextLogo = styled.h1`
  color: ${colors.seconday};
  font-family: ${fonts.montserratFontFamily};

  font-weight: 700;
`;
