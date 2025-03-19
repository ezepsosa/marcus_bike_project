import { BrowserRouter, Route, Routes } from "react-router-dom";
import { Layout } from "../components/Layout/Layout";
import { Home } from "../pages/Home/Home";
import { ProductCatalogue } from "../pages/Products/ProductCatalog/ProductCatalogue";
import { ProductDetail } from "../pages/Products/ProductDetail/ProductDetail";
import { Dashboard } from "../pages/Admin/Dashboard/Dashboard";
import { ManageConditions } from "../pages/Admin/Conditions/ManageCondition";
import { ManageProducts } from "../pages/Admin/Product/ManageProducts";
import { ManageProductParts } from "../pages/Admin/ProductPart/ManageProductParts";
import { Login } from "../pages/Login/Login";
import { UserProvider } from "../context/User/UserContext";
import { AdminRoutes } from "./AdminRoutes";

export function AppRoutes() {
  return (
    <UserProvider>
      <BrowserRouter>
        <Routes>
          <Route element={<Layout />}>
            <Route path="/" element={<Home />} />
            <Route path="/login" element={<Login />} />
            <Route path="/customize" element={<ProductCatalogue />} />
            <Route path="/customize/details" element={<ProductDetail />} />
            <Route path="/admin" element={<AdminRoutes />}>
              <Route path="dashboard" element={<Dashboard />} />
              <Route path="manage/products" element={<ManageProducts />} />
              <Route
                path="manage/productparts"
                element={<ManageProductParts />}
              />
              <Route path="manage/conditions" element={<ManageConditions />} />
            </Route>
          </Route>
        </Routes>
      </BrowserRouter>
    </UserProvider>
  );
}
