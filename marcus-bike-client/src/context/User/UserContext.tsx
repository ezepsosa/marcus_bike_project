import { createContext, ReactNode, useState, useEffect } from "react";
import { AuthResponseToken, LoginUser } from "../../models/user";
import { authenticateUser } from "../../server/api";
import { UserContextType } from "./types";

const UserContext = createContext<UserContextType | undefined>(undefined);

/**
 * Provides authentication state and methods for user login/logout.
 * Stores authentication details in local storage for persistence.
 */
const UserProvider = ({ children }: { children: ReactNode }) => {
  const [token, setToken] = useState<string | null>(null);
  const [userId, setUserId] = useState<number | null>(null);
  const [role, setRole] = useState<string | null>(null);
  const [username, setUsername] = useState<string | null>(null);

  useEffect(() => {
    const storedToken = localStorage.getItem("token");
    const storedUserId = localStorage.getItem("userId");
    const storedRole = localStorage.getItem("role");
    const storedUsername = localStorage.getItem("username");

    if (storedToken) {
      setToken(storedToken);
      setUserId(storedUserId ? Number(storedUserId) : null);
      setRole(storedRole);
      setUsername(storedUsername);
    }
  }, []);

  const login = async (loginUser: LoginUser): Promise<AuthResponseToken> => {
    try {
      const loginInfo = await authenticateUser(loginUser);
      setToken(loginInfo.token);
      setUserId(loginInfo.userId);
      setRole(loginInfo.role);
      setUsername(loginInfo.username);
      localStorage.setItem("token", loginInfo.token);
      localStorage.setItem("userId", String(loginInfo.userId));
      localStorage.setItem("role", loginInfo.role);
      localStorage.setItem("username", loginInfo.username);

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
    localStorage.removeItem("token");
    localStorage.removeItem("userId");
    localStorage.removeItem("role");
    localStorage.removeItem("username");
  };

  return (
    <UserContext.Provider
      value={{
        token,
        userId,
        role,
        username,
        login,
        logout,
      }}
    >
      {children}
    </UserContext.Provider>
  );
};

export { UserContext, UserProvider };
