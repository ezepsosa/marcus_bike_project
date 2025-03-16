import styled from "styled-components";
import { fonts, GeneralColors } from "../styles/sharedStyles";
import { ButtonProps, TextProps } from "./types";
import { Link } from "react-router-dom";
import { Field } from "formik";

export const Container = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 100%;
  overflow-x: hidden;
  overflow-y: auto;
`;

export const PrimaryText = styled.p<TextProps>`
  color: ${GeneralColors.textPrimary};
  font-size: ${({ $fontSize }) => $fontSize || "0.8em"};
  font-family: ${fonts.robotoFontFamily};
  font-weight: 400;
  margin: 0.5rem;
  word-break: break-word;
  overflow-wrap: break-word;
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
  transition: background 0.3s, transform 0.2s;
  margin: 0.5rem;
  &:hover {
    background-color: ${GeneralColors.PrimaryButtonHover};
    transform: scale(1.05);
  }
`;

export const LinkText = styled(Link)`
  text-decoration: none;
`;

export const SpanText = styled.span<TextProps>`
  color: ${({ $color }) => $color || GeneralColors.textPrimary};
  font-size: ${({ $fontSize }) => $fontSize || "1rem"};
  font-family: ${fonts.robotoFontFamily};
  font-weight: 600;
  margin: 0.2rem 0;
`;

export const LabelText = styled.label<TextProps>`
  color: ${({ $color }) => $color || GeneralColors.textPrimary};
  font-size: ${({ $fontSize }) => $fontSize || "0.8rem"};
  font-family: ${fonts.robotoFontFamily};
  font-weight: 600;
  font-style: italic;
`;

export const FormikSelectField = styled(Field)`
  width: 80%;
  margin: 0.5rem;

  background-color: ${GeneralColors.backgroundSecondary};
  padding: 0.5rem;
  color: ${GeneralColors.textHighlight};
  border-radius: 0.25rem;
  border: 1px solid ${GeneralColors.textHighlight};
  &:hover {
    background-color: ${GeneralColors.SecondayBackground};
  }
  font-family: ${fonts.robotoFontFamily};
  font-weight: 600;
`;

export const Option = styled.option``;
