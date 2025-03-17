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
  height: auto;
  width: 38rem;
  margin: 1rem;
  padding: 2rem;
  display: flex;
  flex-direction: column;
  align-items: center;
  border: 1px solid #fff;
  border-radius: 1.5rem;
  background-color: ${GeneralColors.backgroundSecondary};
`;
