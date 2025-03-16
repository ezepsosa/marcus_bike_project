import { useLocation, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import {
  getProductDetails,
  getProductPartConditions,
} from "../../../server/api";
import { ProductPart } from "../../../models/productPart";
import { Container, FormikForm, Section, SelectContainer } from "./style";
import {
  LabelText,
  Option,
  PrimaryButton,
  Select,
  SpanText,
} from "../../../components/styles";
import { ProductImage } from "../ProductCatalog/styles";
import { ProductPartCondition } from "../../../models/productPartCondition";
import { Formik } from "formik";
import { access } from "fs";

export const ProductDetail = () => {
  const location = useLocation();
  const navigate = useNavigate();

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
      setProductParts(await getProductDetails(productId));
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

  function changeSelectedParts(type: string, value: number) {
    setSelectedParts((prev) => ({
      ...prev,
      [type]: value,
    }));
  }

  //Check compatibilities and prices
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
        <ProductImage src={imageUrl} />
        <SpanText $fontSize="1.5rem">Model: {productName}</SpanText>
        <SpanText $fontSize="1.2rem" $color="#ffc600">
          Customize your model
        </SpanText>
        <SpanText $fontSize="1.2rem">
          Customize your bike by choosing from the following options:
        </SpanText>
        <br />

        <Formik
          initialValues={{ selectedParts: [] }}
          onSubmit={() => console.log("hi")}
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
                    <Select
                      onChange={(e) =>
                        changeSelectedParts(type, Number(e.target.value))
                      }
                    >
                      <Option value="">--</Option>
                      {options.map((option) => (
                        <Option key={option.id} value={option.id}>
                          {option.partOption} - {option.basePrice}€
                        </Option>
                      ))}
                    </Select>
                  </SelectContainer>
                );
              })}
            {errors?.map((e) => {
              return (
                <SpanText $fontSize="0.9rem" $color="#ff5e5e">
                  {e}
                </SpanText>
              );
            })}
            {extraPrice.map((e) => {
              return (
                <SpanText $fontSize="0.9rem" $color="#ffd9a7">
                  {e}
                </SpanText>
              );
            })}

            <SpanText $fontSize="1.2rem" $color="#ffc600">
              Total: {totalPrice}€
            </SpanText>
            <PrimaryButton>Add to cart</PrimaryButton>
          </FormikForm>
        </Formik>
      </Container>
    </Section>
  );
};
