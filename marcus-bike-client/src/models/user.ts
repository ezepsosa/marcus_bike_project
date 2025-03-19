export interface User {
  id: number;
  username: string;
  email: string;
  role: string;
}

export interface insertUser {
  username: string;
  password: string;
  email: string;
}

export interface LoginUser {
  username: string;
  password: string;
}

export interface AuthResponseToken {
  userId: number;
  username: string;
  role: string;
  token: string;
}
