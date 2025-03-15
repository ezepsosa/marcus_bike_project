import { BrowserRouter, Route, Routes } from "react-router-dom";
import { Layout } from "../components/Layout/Layout";
import { Home } from "../pages/Home/Home";

export function AppRoutes() {
  return (
    <BrowserRouter>
      <Routes>
        <Route element={<Layout />}>
          <Route path="/" element={<Home />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}
