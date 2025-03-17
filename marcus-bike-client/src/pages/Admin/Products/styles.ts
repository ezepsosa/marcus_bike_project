import styled from "styled-components";
import { GeneralColors } from "../../../styles/sharedStyles";

export const Container = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: top;
  flex-direction: column;
`;

export const Section = styled.section`
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 1rem;
  background-color: ${GeneralColors.backgroundPrimary};
  flex-grow: 1;
`;

export const ButtonContainer = styled.div`
  dispplay: flex;
  flex-direction: row;
`;
