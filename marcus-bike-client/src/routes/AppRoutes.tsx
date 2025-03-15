import { BrowserRouter, Route, Routes } from "react-router-dom";
import App from "../App";
import { Layout } from "../components/Layout/Layout";

export function AppRoutes() {
  return (
    <BrowserRouter>
      <Routes>
        <Route element={<Layout />}>
          <Route path="/" element={<App />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}
