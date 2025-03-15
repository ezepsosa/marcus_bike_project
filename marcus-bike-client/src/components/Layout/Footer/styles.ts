import styled from "styled-components";
import { GeneralColors } from "../../../styles/sharedStyles";

export const FooterContainer = styled.footer`
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: ${GeneralColors.headerFooter};
  width: 100%;
  height: 2rem;
  border-bottom: 0.25px solid #444;
`;
