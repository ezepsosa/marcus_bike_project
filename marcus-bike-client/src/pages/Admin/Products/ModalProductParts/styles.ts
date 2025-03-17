import styled from "styled-components";
import { GeneralColors } from "../../../../styles/sharedStyles";

export const ModalContainer = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: flex-start;
  background-color: rgba(77, 77, 77, 0.12);
  height: 80;
  overflow-y: auto;
  padding-top: 2rem;
`;

export const ModalContent = styled.div`
  height: auto;
  width: 50%;
  margin: 1rem;
  padding: 1rem;
  display: flex;
  flex-direction: column;
  align-items: center;
  border: 1px solid #fff;
  border-radius: 1rem;
  background-color: ${GeneralColors.backgroundSecondary};
`;
