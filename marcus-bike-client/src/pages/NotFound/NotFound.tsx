import { PrimaryTitle } from "../../components/styles";
import { Container, Section } from "./styles";

export const NotFound = () => {
  return (
    <Section>
      <Container>
        <PrimaryTitle $fontSize={"2.4rem"}> Page Not Found</PrimaryTitle>
        <PrimaryTitle $fontSize={"1.7rem"}>
          Oops! This page doesn't exist, but don't worry—you’re just a ride away
          from finding the bike of your dreams. Explore our collection and get
          back on track!
        </PrimaryTitle>
      </Container>
    </Section>
  );
};
