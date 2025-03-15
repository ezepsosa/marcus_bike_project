import styled from "styled-components";
import { GeneralColors } from "../styles/sharedStyles";
import { PrimaryButtonProps } from "./types";

export const Container = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 100%;
  overflow: hidden;
`;

export const PrimaryButton = styled.button<PrimaryButtonProps>`
  position: relative;
  background-color: ${({ $color }) => $color || GeneralColors.primary};
  border-radius: 0.2rem;
  padding: ${({ $padding }) => $padding || "0.5rem"};
  color: ${GeneralColors.seconday};
  border: 0.0625rem solid ${GeneralColors.seconday};
  &:hover {
    background-color: ${GeneralColors.linkSecondary};
  }
`;
