import styled from "styled-components";
import { GeneralColors } from "../../../styles/sharedStyles";

export const Section = styled.section`
  width: 100%;
  display: flex;
  padding: 1rem;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background-color: ${GeneralColors.backgroundPrimary};
  flex-grow: 1;
`;

export const Container = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
`;
