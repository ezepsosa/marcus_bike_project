import { Outlet } from "react-router-dom";
import { Container } from "../styles";
import { Navbar } from "./Navbar/Navbar";
import { Footer } from "./Footer/Footer";

// Main layout component that includes the navbar, footer, and dynamic content (Outlet)
export const Layout = () => {
  return (
    <Container>
      <Navbar />
      <Outlet />
      <Footer />
    </Container>
  );
};
