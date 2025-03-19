import { useContext } from "react";
import { CartContextType } from "./types";
import { CartContext } from "./CartContext";

/**
 * Custom hook to access the shopping cart context.
 * Must be used within a `CartProvider`, otherwise, it will throw an error.
 */
export const useCart = (): CartContextType => {
  const context = useContext(CartContext);
  if (!context) {
    throw new Error("useCart has to be used within a UserProvider");
  }
  return context;
};
