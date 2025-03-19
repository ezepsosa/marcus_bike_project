import { createContext, ReactNode, useState } from "react";
import { CartContextType } from "./types";
import { OrderLineInsert } from "../../models/orderLines";

// Create a React Context for managing the shopping cart state.
const CartContext = createContext<CartContextType | undefined>(undefined);

const CartProvider = ({ children }: { children: ReactNode }) => {
  // State to store the shopping cart items.
  const [cart, setCart] = useState<OrderLineInsert[]>([]);

  const addToCart = (orderLine: OrderLineInsert): void => {
    setCart((prev) => [...prev, orderLine]);
  };
  /*
   * Removes an order line from the cart.
   * This compares orderLineProductParts instead of a unique identifier,
   * which may lead to unexpected behavior if different items have the same structure.
   */
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
