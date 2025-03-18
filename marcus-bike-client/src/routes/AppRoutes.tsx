import { BrowserRouter, Route, Routes } from "react-router-dom";
import { Layout } from "../components/Layout/Layout";
import { Home } from "../pages/Home/Home";
import { ProductCatalogue } from "../pages/Products/ProductCatalog/ProductCatalogue";
import { ProductDetail } from "../pages/Products/ProductDetail/ProductDetail";
import { Dashboard } from "../pages/Admin/Dashboard/Dashboard";
import { ManageProducts } from "../pages/Admin/Products/ManageProducts";
import { ManageProductParts } from "../pages/Admin/Products copy/ManageProductParts";

export function AppRoutes() {
  return (
    <BrowserRouter>
      <Routes>
        <Route element={<Layout />}>
          <Route path="/" element={<Home />} />
          <Route path="/customize" element={<ProductCatalogue />} />
          <Route path="/customize/details" element={<ProductDetail />} />
          <Route path="/admin">
            <Route path="dashboard" element={<Dashboard />} />
            <Route path="manage/products" element={<ManageProducts />} />
            <Route
              path="manage/productparts"
              element={<ManageProductParts />}
            />
          </Route>
        </Route>
      </Routes>
    </BrowserRouter>
  );
}
