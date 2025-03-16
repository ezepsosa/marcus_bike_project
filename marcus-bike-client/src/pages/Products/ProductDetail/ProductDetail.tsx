import { useLocation, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { getProductDetails } from "../../../server/api";
import { ProductPart } from "../../../models/productPart";
import { Container, Section } from "./style";

export const ProductDetail = () => {
  const location = useLocation();
  const { id, productName, imageUrl } = location.state || {};
  const [parts, setProductParts] = useState<ProductPart[]>();

  const navigate = useNavigate();

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
  console.log(parts);
  return (
    <Section>
      <Container>
        {id}
        {productName}
        {imageUrl}
      </Container>
    </Section>
  );
};
