import styled from "styled-components";
import { GeneralColors } from "../styles/sharedStyles";
import { PrimaryButtonProps, PrimaryTextProps } from "./types";

export const Container = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 100%;
  overflow: hidden;
`;

export const PrimaryButton = styled.button<PrimaryButtonProps>`
  position: relative;
  background-color: ${({ $color }) =>
    $color || GeneralColors.backgroundPrimary};
  border-radius: 0.2rem;
  padding: ${({ $padding }) => $padding || "0.5rem"};
  color: ${GeneralColors.textPrimary};
  border: 0.0625rem solid ${GeneralColors.borderPrimary};
  &:hover {
    background-color: ${GeneralColors.backgroundSecondary};
  }
`;

export const PrimaryText = styled.p<PrimaryTextProps>`
  color: ${GeneralColors.textPrimary};
  font-size: ${({ $fontSize }) => $fontSize || "0.8em"};
`;
