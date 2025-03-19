import { useLocation, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import {
  getProductDetails,
  getProductPartConditions,
} from "../../../server/api";
import { ProductPart } from "../../../models/productPart";
import { Container, Section } from "./style";
import {
  FormikForm,
  FormikSelectField,
  LabelText,
  Option,
  PrimaryButton,
  SpanText,
  SelectContainer,
} from "../../../components/styles";
import { ProductImage } from "../ProductCatalog/styles";
import { ProductPartCondition } from "../../../models/productPartCondition";
import { ErrorMessage, Formik } from "formik";
import { useUserAuth } from "../../../context/User/useUserAuth";
import { useCart } from "../../../context/Cart/useCart";
import { OrderLineInsert } from "../../../models/orderLines";

export const ProductDetail = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const { token } = useUserAuth();
  const { addToCart } = useCart();

  const { id, productName, imageUrl } = location.state || {};
  const [parts, setProductParts] = useState<ProductPart[]>([]);

  const [errors, setErrors] = useState<string[]>([]);

  const [extraPrice, setExtra] = useState<string[]>([]);

  const [selectedParts, setSelectedParts] = useState<Record<string, number>>(
    {}
  );
  const [conditions, setConditions] = useState<ProductPartCondition[]>([]);
  const [totalPrice, setTotalPrice] = useState<number>(0);

  // Initial api load
  useEffect(() => {
    async function loadProductDetails(productId: number) {
      const parts = await getProductDetails(productId);
      setProductParts(parts.filter((part: ProductPart) => part.isAvailable));
    }
    async function loadProductPartConditions() {
      setConditions(await getProductPartConditions());
    }

    if (!location.state) {
      navigate("/");
    } else {
      if (id) loadProductDetails(id);
      loadProductPartConditions();
    }
  }, [location.state, navigate, id]);

  // Updating selected parts
  function changeSelectedParts(type: string, value: number) {
    setSelectedParts((prev) => ({
      ...prev,
      [type]: value,
    }));
  }

  // Check compatibilities and prices
  useEffect(() => {
    function checkExtraPrice() {
      let resPrice: number = 0;
      const selectedRestrictions = conditions.filter(
        (e) =>
          e.isRestriction == false &&
          Object.values(selectedParts).includes(e.partId) &&
          Object.values(selectedParts).includes(e.dependantPartId)
      );
      if (selectedRestrictions.length > 0) {
        resPrice = selectedRestrictions.reduce(
          (acc, e) => acc + e.priceAdjustment,
          0
        );
        setExtra(
          selectedRestrictions.map(
            (e) =>
              `Parts ${
                parts.find((part) => part.id == e.dependantPartId)?.partOption
              } and ${
                parts.find((part) => part.id == e.partId)?.partOption
              } together add an extra of ${e.priceAdjustment}€`
          )
        );
      }
      return resPrice;
    }
    function checkIncompatibility() {
      let res: string[] = [];
      const selectedRestrictions = conditions.filter(
        (e) =>
          e.isRestriction == true &&
          Object.values(selectedParts).includes(e.partId) &&
          Object.values(selectedParts).includes(e.dependantPartId)
      );
      if (selectedRestrictions.length > 0) {
        res = selectedRestrictions.map(
          (e) =>
            `Parts ${
              parts.find((part) => part.id == e.dependantPartId)?.partOption
            } and ${
              parts.find((part) => part.id == e.partId)?.partOption
            } cannot be selected together`
        );
      }
      return res;
    }
    function checkPrice() {
      const totalPrice = Object.values(selectedParts).reduce(
        (totalCost: number, id: number) =>
          totalCost +
          (parts.find((element) => element.id === id)?.basePrice || 0),
        0
      );
      return totalPrice;
    }
    setTotalPrice(checkExtraPrice() + checkPrice());
    setErrors(checkIncompatibility());
  }, [selectedParts, parts, conditions]);

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
        <ProductImage src={`/${imageUrl}`} />
        <SpanText $fontSize="1.5rem">Model: {productName}</SpanText>
        <SpanText $fontSize="1.2rem" $color="#ffc600">
          Customize your model
        </SpanText>
        <SpanText $fontSize="1.2rem">
          Customize your bike by choosing from the following options:
        </SpanText>
        <br />

        <Formik
          initialValues={{}}
          onSubmit={() => {
            if (errors.length > 0) {
              alert("You cannot add this product with incompatible parts");
            } else {
              const productPartsList = Object.entries(selectedParts).map(
                ([, value]) => ({
                  productPart: value,
                  finalPrice:
                    parts.find((part) => part.id === value)?.basePrice || 0,
                })
              );
              const orderLine: OrderLineInsert = {
                productId: id,
                quantity: 1,
                orderLineProductParts: productPartsList,
              };
              addToCart(orderLine);
            }
          }}
        >
          <FormikForm>
            {groupedParts &&
              Object.keys(groupedParts).map((type) => {
                const options = groupedParts[type];
                return (
                  <SelectContainer key={type}>
                    <LabelText $color="gray" $fontSize="1.2rem">
                      Select the {type.replace("_", " ").toLowerCase()}:
                    </LabelText>
                    <FormikSelectField
                      as={"select"}
                      onChange={(e: React.ChangeEvent<HTMLSelectElement>) => {
                        changeSelectedParts(type, Number(e.target.value));
                      }}
                    >
                      <Option value="">--</Option>
                      {options.map((option) => (
                        <Option key={option.id} value={option.id}>
                          {option.partOption} - {option.basePrice}€
                        </Option>
                      ))}
                    </FormikSelectField>
                    <ErrorMessage name={`selectedParts.${type}`}>
                      {(msg) => (
                        <SpanText $fontSize="0.9rem" $color="#ff5e5e">
                          {msg}
                        </SpanText>
                      )}
                    </ErrorMessage>
                  </SelectContainer>
                );
              })}
            {errors?.map((e, index) => {
              return (
                <SpanText key={index} $fontSize="0.9rem" $color="#ff5e5e">
                  {e}
                </SpanText>
              );
            })}
            {extraPrice.map((e, index) => {
              return (
                <SpanText key={index} $fontSize="0.9rem" $color="#ffd9a7">
                  {e}
                </SpanText>
              );
            })}

            <SpanText $fontSize="1.2rem" $color="#ffc600">
              Total: {totalPrice}€
            </SpanText>
            {token ? (
              <PrimaryButton type="submit">Add to cart</PrimaryButton>
            ) : (
              <PrimaryButton type="button" onClick={() => navigate("/login")}>
                Login First
              </PrimaryButton>
            )}
          </FormikForm>
        </Formik>
      </Container>
    </Section>
  );
};
