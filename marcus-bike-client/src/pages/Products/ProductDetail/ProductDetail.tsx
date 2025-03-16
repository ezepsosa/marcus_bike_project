import { Form, useLocation, useNavigate, useSubmit } from "react-router-dom";
import { Container, Section } from "../styles";
import { useEffect } from "react";

export const ProductDetail = () => {
  const location = useLocation();
  const { id, productName, imageUrl } = location.state || {};
  const submit = useSubmit();

  const navigate = useNavigate();

  useEffect(() => {
    if (!location.state) {
      navigate("/");
    }
  }, [location.state, navigate]);
  return (
    <Section>
      <Container>
        <Form
          onChange={(event) => {
            submit(event.currentTarget);
          }}
        >
          {id}
          {productName}
          {imageUrl}
        </Form>
      </Container>
    </Section>
  );
};
