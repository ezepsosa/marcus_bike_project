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

export interface loginUser {
  username: string;
  password: string;
}
