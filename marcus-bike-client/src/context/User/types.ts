import { AuthResponseToken, LoginUser } from "../../models/user";

export interface UserContextType {
  userId: number | null;
  username: string | null;
  role: string | null;
  token: string | null;
  setToken: (token: string | null) => void;
  setUserId: (id: number | null) => void;
  setRole: (role: string | null) => void;
  setUsername: (username: string | null) => void;
  login: (loginUser: LoginUser) => Promise<AuthResponseToken>;
  logout: () => void;
}
