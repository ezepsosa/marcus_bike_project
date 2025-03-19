import { Navigate, Outlet } from "react-router-dom";
import { useUserAuth } from "../context/User/useUserAuth";

export const AdminRoutes = () => {
  const { token, role } = useUserAuth();
  return token && role == "ADMIN" ? <Outlet /> : <Navigate to="/" replace />;
};
