import { createContext, ReactNode, useState } from "react";
import { CartContextType } from "./types";
import { OrderLine } from "../../models/orderLines";

const CartContext = createContext<CartContextType | undefined>(undefined);

const CartProvider = ({ children }: { children: ReactNode }) => {
  const [cart, setCart] = useState<OrderLine[]>([]);

  const addToCart = (orderLine: OrderLine): void => {
    setCart((prev) => [...prev, orderLine]);
  };

  const deleteFromCart = (orderLine: OrderLine): void => {
    setCart((prev) => [
      ...prev.filter(
        (orderlineCart: OrderLine) => orderlineCart.id != orderLine.id
      ),
    ]);
  };

  return (
    <CartContext.Provider
      value={{
        cart,
        addToCart,
        deleteFromCart,
      }}
    >
      {children}
    </CartContext.Provider>
  );
};

export { CartContext, CartProvider };
