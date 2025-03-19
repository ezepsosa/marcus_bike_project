import { createContext, ReactNode, useState } from "react";
import { AuthResponseToken, LoginUser } from "../../models/user";
import { authenticateUser } from "../../server/api";
import { UserContextType } from "./types";

const UserContext = createContext<UserContextType | undefined>(undefined);

const UserProvider = ({ children }: { children: ReactNode }) => {
  const [token, setToken] = useState<string | null>(null);
  const [userId, setUserId] = useState<number | null>(null);
  const [role, setRole] = useState<string | null>(null);
  const [username, setUsername] = useState<string | null>(null);

  const login = async (loginUser: LoginUser): Promise<AuthResponseToken> => {
    try {
      const loginInfo = await authenticateUser(loginUser);
      setToken(loginInfo.token);
      setUserId(loginInfo.userId);
      setRole(loginInfo.role);
      setUsername(loginInfo.username);
      localStorage.setItem("token", loginInfo.token);
      return loginInfo;
    } catch (error) {
      console.error("Failed to authenticate user");
      throw error;
    }
  };

  const logout = () => {
    setToken(null);
    setUserId(null);
    setRole(null);
    setUsername(null);
    localStorage.removeItem("token");
  };

  return (
    <UserContext.Provider
      value={{
        token,
        setToken,
        userId,
        setUserId,
        role,
        setRole,
        username,
        setUsername,
        login,
        logout,
      }}
    >
      {children}
    </UserContext.Provider>
  );
};

export { UserContext, UserProvider };
