import { useLocation, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { getProductDetails } from "../../../server/api";
import { ProductPart } from "../../../models/productPart";
import { Container, Section } from "./style";
import {
  LabelText,
  Option,
  Select,
  SpanText,
} from "../../../components/styles";
import { ProductImage } from "../ProductCatalog/styles";

export const ProductDetail = () => {
  const location = useLocation();
  const navigate = useNavigate();

  const { id, productName, imageUrl } = location.state || {};
  const [parts, setProductParts] = useState<ProductPart[]>();

  useEffect(() => {
    async function loadProductDetails(productId: number) {
      setProductParts(await getProductDetails(productId));
    }

    if (!location.state) {
      navigate("/");
    } else {
      loadProductDetails(id);
    }
  }, [location.state, navigate, id]);

  const groupedParts = parts?.reduce((list, part) => {
    if (!list[part.productPartCategory]) {
      list[part.productPartCategory] = [];
    }
    list[part.productPartCategory].push(part);
    return list;
  }, {} as Record<string, ProductPart[]>);

  return (
    <Section>
      <Container>
        <ProductImage src={imageUrl} />
        <SpanText $fontSize="1.5rem">Model: {productName}</SpanText>
        <SpanText $fontSize="1.2rem" $color="#ffc600">
          Customize your model
        </SpanText>
        <SpanText $fontSize="1.2rem">
          Customize your bike by choosing from the following options:
        </SpanText>
        <br />

        {groupedParts &&
          Object.keys(groupedParts).map((type) => {
            const options = groupedParts[type];
            return (
              <>
                <LabelText $color="gray" $fontSize="1.2rem">
                  Select the {type.replace("_", " ").toLowerCase()}:
                </LabelText>
                <Select key={type}>
                  {options.map((option) => (
                    <Option key={option.id}>
                      {option.partOption} - {option.basePrice}€
                    </Option>
                  ))}
                </Select>
              </>
            );
          })}
      </Container>
    </Section>
  );
};
