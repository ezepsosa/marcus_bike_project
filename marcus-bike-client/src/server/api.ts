import axios, { AxiosResponse } from "axios";
import { Product } from "../models/products";
import { ProductPart } from "../models/productPart";

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

export async function getProductDetails(id: number): Promise<ProductPart[]> {
  try {
    const res: AxiosResponse<ProductPart[]> = await apiService.get(
      `products/${id}/productparts`
    );
    return res.data;
  } catch (error) {
    if (axios.isAxiosError(error)) {
      throw {
        message: "Failed to fetch product parts",
        statusText: error.response?.statusText || "Network error",
        status: error.response?.status || 500,
      };
    } else {
      throw {
        message: "An unexpected error occurred fetching product parts",
        statusText: "Unknown Error",
        status: 500,
      };
    }
  }
}
