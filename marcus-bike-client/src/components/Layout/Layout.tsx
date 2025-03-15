import { Outlet } from "react-router-dom";
import { Container } from "../styles";
import { Navbar } from "./Navbar/Navbar";
import { Footer } from "./Footer/Footer";

export const Layout = () => {
  return (
    <Container>
      <Navbar />
      <Outlet />
      <Footer />
    </Container>
  );
};
