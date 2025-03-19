import { SpanText, PrimaryText } from "../../../../components/styles";
import { Container, LabelContainer } from "./styles";
import { ProductInfoProps } from "./types";
/**
 * Component to display product information.
 * It shows a label with an icon and the value for a specific product attribute.
 */

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
