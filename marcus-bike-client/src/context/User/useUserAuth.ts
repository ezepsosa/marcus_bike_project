import { useContext } from "react";
import { UserContext } from "./UserContext";
import { UserContextType } from "./types";

export const useUserAuth = (): UserContextType => {
  const context = useContext(UserContext);
  if (!context) {
    throw new Error("useUserAuth has to be used within a UserProvider");
  }
  return context;
};
