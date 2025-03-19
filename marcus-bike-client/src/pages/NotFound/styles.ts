import styled from "styled-components";
import { GeneralColors } from "../../styles/sharedStyles";

export const Container = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
  flex-direction: column;
`;

export const Section = styled.section`
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background-color: ${GeneralColors.backgroundPrimary};
  flex-grow: 1;
`;
