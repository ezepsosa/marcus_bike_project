import { useContext } from "react";
import { CartContextType } from "./types";
import { CartContext } from "./CartContext";

export const useCart = (): CartContextType => {
  const context = useContext(CartContext);
  if (!context) {
    throw new Error("useCart has to be used within a UserProvider");
  }
  return context;
};
