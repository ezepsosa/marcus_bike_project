import { SpanText, PrimaryText } from "../../../../components/styles";
import { Container, LabelContainer } from "./styles";
import { ProductInfoProps } from "./types";

export const ProductInfo = ({ value, icon, label }: ProductInfoProps) => {
  return (
    <Container>
      <LabelContainer>
        {icon}
        <SpanText>{label}:</SpanText>
      </LabelContainer>
      <PrimaryText>{value}</PrimaryText>
    </Container>
  );
};
