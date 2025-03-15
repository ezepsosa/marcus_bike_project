import styled from "styled-components";
import { fonts, GeneralColors } from "../styles/sharedStyles";
import { ButtonProps, TextProps } from "./types";

export const Container = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 100%;
  overflow: hidden;
`;

export const PrimaryText = styled.p<TextProps>`
  color: ${GeneralColors.textPrimary};
  font-size: ${({ $fontSize }) => $fontSize || "0.8em"};
  font-family: ${fonts.robotoFontFamily};
  font-weight: 400;
  margin: 0.5rem;
`;

export const PrimaryTitle = styled.h1<TextProps>`
  color: ${GeneralColors.textHighlight};
  font-size: ${({ $fontSize }) => $fontSize || "2rem"};
  font-family: ${fonts.playFairFontFamily};
  font-weight: 400;
  margin: 0.5rem;
`;

export const SecondaryButton = styled.button<ButtonProps>`
  position: relative;
  background-color: ${GeneralColors.SecondaryButtonBackground};
  border-radius: 0.2rem;
  padding: ${({ $padding }) => $padding || "0.5rem"};
  color: ${GeneralColors.SecondayButtonColor};
  border: 0.0625rem solid ${GeneralColors.borderPrimary};
  font-family: ${fonts.playFairFontFamily};
  font-weight: 400;
  &:hover {
    background-color: ${GeneralColors.SecondayButtonHover};
  }
`;
export const PrimaryButton = styled.button<ButtonProps>`
  position: relative;
  background-color: ${GeneralColors.PrimaryButtonBackground};
  border-radius: 1rem;
  padding: ${({ $padding }) => $padding || "0.7rem 2rem"};
  color: ${GeneralColors.PrimaryButtonColor};
  font-family: ${fonts.playFairFontFamily};
  font-weight: 600;
  font-size: 0.9rem;
  border: 0.0625rem solid ${GeneralColors.borderPrimary};
  &:hover {
    background-color: ${GeneralColors.PrimaryButtonHover};
    transform: scale(1.05);
  }
  transition: background 0.3s, transform 0.2s;
`;
