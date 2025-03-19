import { createContext, ReactNode, useState } from "react";
import { CartContextType } from "./types";
import { OrderLineInsert } from "../../models/orderLines";

const CartContext = createContext<CartContextType | undefined>(undefined);

const CartProvider = ({ children }: { children: ReactNode }) => {
  const [cart, setCart] = useState<OrderLineInsert[]>([]);

  const addToCart = (orderLine: OrderLineInsert): void => {
    setCart((prev) => [...prev, orderLine]);
  };

  const deleteFromCart = (orderLine: OrderLineInsert): void => {
    setCart((prev) => [
      ...prev.filter(
        (orderlineCart: OrderLineInsert) =>
          orderlineCart.orderLineProductParts != orderLine.orderLineProductParts
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
