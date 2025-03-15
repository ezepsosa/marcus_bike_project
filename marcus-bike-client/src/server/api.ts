import axios, { AxiosResponse } from "axios";
import { Product } from "../models/products";

const API_BASE_URL = `http://localhost:8080/api/`;

const apiService = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    "Content-type": "application/json",
  },
});

export async function getProducts(): Promise<Product[]> {
  try {
    const res: AxiosResponse<Product[]> = await apiService.get("products");
    return res.data;
  } catch (error) {
    if (axios.isAxiosError(error)) {
      throw {
        message: "Failed to fetch products",
        statusText: error.response?.statusText || "Network error",
        status: error.response?.status || 500,
      };
    } else {
      throw {
        message: "An unexpected error occurred fetching products",
        statusText: "Unknown Error",
        status: 500,
      };
    }
  }
}
