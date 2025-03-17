import { LinkText, PrimaryTitle } from "../../../components/styles";
import {
  ProductCard,
  ProductImage,
  Section,
  ProductInfoContainer,
  GridProducts,
} from "./styles";
import { useEffect, useState } from "react";
import { Product } from "../../../models/products";
import { getProducts } from "../../../server/api";
import { TbBoxModel } from "react-icons/tb";
import { MdBlurCircular, MdCategory } from "react-icons/md";
import { GiMetalPlate } from "react-icons/gi";
import { ProductInfo } from "./ProductInfo/ProductInfo";

export const ProductCatalogue = () => {
  const [products, setProduct] = useState<Product[]>([]);

  useEffect(() => {
    async function loadProducts() {
      setProduct(await getProducts());
    }
    loadProducts();
  }, []);

  return (
    <Section>
      <PrimaryTitle>Select your base model</PrimaryTitle>
      <GridProducts>
        {products.map((product) => (
          <LinkText
            to={`/customize/details`}
            state={product}
            key={String(product.id)}
          >
            <ProductCard>
              <ProductImage src={product.imageUrl} />
              <ProductInfoContainer>
                <ProductInfo
                  icon={<TbBoxModel color="#fff" />}
                  label="Model"
                  value={product.productName}
                />
                <ProductInfo
                  icon={<MdBlurCircular color="#fff" />}
                  label="Brand"
                  value={product.brand}
                />
                <ProductInfo
                  icon={<MdCategory color="#fff" />}
                  label="Category"
                  value={product.category}
                />
                <ProductInfo
                  icon={<GiMetalPlate color="#fff" />}
                  label="Material"
                  value={product.material}
                />
              </ProductInfoContainer>
            </ProductCard>
          </LinkText>
        ))}
      </GridProducts>
    </Section>
  );
};
