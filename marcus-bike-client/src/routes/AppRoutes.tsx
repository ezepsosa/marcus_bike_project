import { BrowserRouter, Route, Routes } from "react-router-dom";
import { Layout } from "../components/Layout/Layout";
import { Home } from "../pages/Home/Home";
import { ProductCatalogue } from "../pages/Products/ProductCatalogue";
import { ProductDetail } from "../pages/Products/ProductDetail/ProductDetail";

export function AppRoutes() {
  return (
    <BrowserRouter>
      <Routes>
        <Route element={<Layout />}>
          <Route path="/" element={<Home />} />
          <Route path="/customize" element={<ProductCatalogue />} />
          <Route path="/customize/details" element={<ProductDetail />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}
