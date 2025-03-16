import { LabelText, PrimaryText } from "../../../components/styles";
import { Container, LabelContainer } from "./styles";
import { ProductInfoProps } from "./types";

export const ProductInfo = ({ value, icon, label }: ProductInfoProps) => {
  return (
    <Container>
      <LabelContainer>
        {icon}
        <LabelText>{label}:</LabelText>
      </LabelContainer>
      <PrimaryText>{value}</PrimaryText>
    </Container>
  );
};
