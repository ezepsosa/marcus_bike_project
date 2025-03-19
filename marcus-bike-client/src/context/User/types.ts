import { AuthResponseToken, LoginUser } from "../../models/user";

export interface UserContextType {
  userId: number | null;
  username: string | null;
  role: string | null;
  token: string | null;
  login: (loginUser: LoginUser) => Promise<AuthResponseToken>;
  logout: () => void;
}
