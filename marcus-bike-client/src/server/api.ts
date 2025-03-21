import { AuthResponseToken, LoginUser } from "./../models/user";
import axios, {
  AxiosError,
  AxiosResponse,
  InternalAxiosRequestConfig,
} from "axios";
import { Product, ProductInsert } from "../models/products";
import { ProductPart, ProductPartInsert } from "../models/productPart";
import {
  ProductPartCondition,
  ProductPartConditionInsert,
} from "../models/productPartCondition";

const API_BASE_URL = `http://localhost:8080/api/`;

// Axios instance setup for API base URL and default headers.
const apiService = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    "Content-type": "application/json",
  },
});

// Interceptor to attach the token to each request if it exists in localStorage.
apiService.interceptors.request.use(
  (config: InternalAxiosRequestConfig): InternalAxiosRequestConfig => {
    const token = localStorage.getItem("token");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error: AxiosError) => {
    return Promise.reject(error);
  }
);

// Function to fetch products from the API.
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

// Function to fetch product parts for a specific product.
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

// Function to fetch all product part conditions.
export async function getProductPartConditions(): Promise<
  ProductPartCondition[]
> {
  try {
    const res: AxiosResponse<ProductPartCondition[]> = await apiService.get(
      `productpartconditions`
    );
    return res.data;
  } catch (error) {
    if (axios.isAxiosError(error)) {
      throw {
        message: "Failed to fetch product part conditions",
        statusText: error.response?.statusText || "Network error",
        status: error.response?.status || 500,
      };
    } else {
      throw {
        message:
          "An unexpected error occurred fetching product part conditions",
        statusText: "Unknown Error",
        status: 500,
      };
    }
  }
}
// Function to fetch product parts for products
export async function getProductParts(): Promise<ProductPart[]> {
  try {
    const res: AxiosResponse<ProductPart[]> = await apiService.get(
      `productparts`
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

// Function to fetch product parts for a specific product.
export async function getPartsFromProduct(id: number): Promise<ProductPart[]> {
  try {
    const res: AxiosResponse<ProductPart[]> = await apiService.get(
      `products/${id}/productparts`
    );
    return res.data;
  } catch (error) {
    if (axios.isAxiosError(error)) {
      throw {
        message: "Failed to fetch product parts from product",
        statusText: error.response?.statusText || "Network error",
        status: error.response?.status || 500,
      };
    } else {
      throw {
        message:
          "An unexpected error occurred fetching product parts from product",
        statusText: "Unknown Error",
        status: 500,
      };
    }
  }
}

// Function to delete a product part from the product.
export async function deletePartFromProduct(
  partId: number,
  productId: number
): Promise<void> {
  try {
    await apiService.delete(`products/${productId}/productparts/${partId}`);
  } catch (error) {
    if (axios.isAxiosError(error)) {
      throw {
        message: "Failed to delete product parts from product",
        statusText: error.response?.statusText || "Network error",
        status: error.response?.status || 500,
      };
    } else {
      throw {
        message:
          "An unexpected error occurred deleting product parts from product",
        statusText: "Unknown Error",
        status: 500,
      };
    }
  }
}
// Function to add a product part to a product.
export async function postPartFromProduct(
  productId: number,
  partId: number
): Promise<void> {
  try {
    const payload = {
      productId,
      productPartsId: [partId],
    };
    await apiService.post(`products/${productId}/productparts`, payload);
  } catch (error) {
    if (axios.isAxiosError(error)) {
      throw {
        message: "Failed to add a part to the product",
        statusText: error.response?.statusText || "Network error",
        status: error.response?.status || 500,
      };
    } else {
      throw {
        message:
          "An unexpected error occurred while adding a part to the product",
        statusText: "Unknown Error",
        status: 500,
      };
    }
  }
}
// Function to add a product
export async function postProduct(product: ProductInsert): Promise<Product> {
  try {
    const res = await apiService.post(`products`, product);
    return res.data;
  } catch (error) {
    if (axios.isAxiosError(error)) {
      throw {
        message: "Failed to add a product",
        statusText: error.response?.statusText || "Network error",
        status: error.response?.status || 500,
      };
    } else {
      throw {
        message: "An unexpected error occurred while adding a product",
        statusText: "Unknown Error",
        status: 500,
      };
    }
  }
}
// Function to update a product
export async function updateProduct(
  product: ProductInsert,
  id: number
): Promise<Product> {
  try {
    const res = await apiService.put(`products/${id}`, product);
    return res.data;
  } catch (error) {
    if (axios.isAxiosError(error)) {
      throw {
        message: "Failed to edit a product",
        statusText: error.response?.statusText || "Network error",
        status: error.response?.status || 500,
      };
    } else {
      throw {
        message: "An unexpected error occurred while editing a product",
        statusText: "Unknown Error",
        status: 500,
      };
    }
  }
}

// Function to delete a a product
export async function deleteProduct(id: number): Promise<Product> {
  try {
    const res = await apiService.delete(`products/${id}`);
    return res.data;
  } catch (error) {
    if (axios.isAxiosError(error)) {
      throw {
        message: "Failed to delete a product",
        statusText: error.response?.statusText || "Network error",
        status: error.response?.status || 500,
      };
    } else {
      throw {
        message: "An unexpected error occurred while deleting a product",
        statusText: "Unknown Error",
        status: 500,
      };
    }
  }
}
// Function to delete a product part
export async function deleteProductPart(id: number): Promise<void> {
  try {
    await apiService.delete(`productparts/${id}`);
  } catch (error) {
    if (axios.isAxiosError(error)) {
      throw {
        message: "Failed to delete product part",
        statusText: error.response?.statusText || "Network error",
        status: error.response?.status || 500,
      };
    } else {
      throw {
        message: "An unexpected error occurred deleting product part",
        statusText: "Unknown Error",
        status: 500,
      };
    }
  }
}

// Function to add a product part
export async function postProductPart(
  productPart: ProductPartInsert
): Promise<ProductPart> {
  try {
    const res = await apiService.post(`productparts`, productPart);
    return res.data;
  } catch (error) {
    if (axios.isAxiosError(error)) {
      throw {
        message: "Failed to add a product part",
        statusText: error.response?.statusText || "Network error",
        status: error.response?.status || 500,
      };
    } else {
      throw {
        message: "An unexpected error occurred while adding a product part",
        statusText: "Unknown Error",
        status: 500,
      };
    }
  }
}

// Function to update a product part
export async function updateProductPart(
  productPart: ProductPartInsert,
  id: number
): Promise<ProductPart> {
  try {
    const res = await apiService.put(`productparts/${id}`, productPart);
    return res.data;
  } catch (error) {
    if (axios.isAxiosError(error)) {
      throw {
        message: "Failed to edit a product part",
        statusText: error.response?.statusText || "Network error",
        status: error.response?.status || 500,
      };
    } else {
      throw {
        message: "An unexpected error occurred while editing a product part",
        statusText: "Unknown Error",
        status: 500,
      };
    }
  }
}

// Function to fetch all conditions
export async function getPartsConditions(): Promise<ProductPartCondition[]> {
  try {
    const res: AxiosResponse<ProductPartCondition[]> = await apiService.get(
      `productpartconditions`
    );
    return res.data;
  } catch (error) {
    if (axios.isAxiosError(error)) {
      throw {
        message: "Failed to fetch product parts from product",
        statusText: error.response?.statusText || "Network error",
        status: error.response?.status || 500,
      };
    } else {
      throw {
        message:
          "An unexpected error occurred fetching product parts from product",
        statusText: "Unknown Error",
        status: 500,
      };
    }
  }
}

// Function to add a condition
export async function postCondition(
  condition: ProductPartConditionInsert
): Promise<ProductPartCondition> {
  try {
    const res = await apiService.post(`productpartconditions`, condition);
    return res.data;
  } catch (error) {
    if (axios.isAxiosError(error)) {
      throw {
        message: "Failed to add a condition",
        statusText: error.response?.statusText || "Network error",
        status: error.response?.status || 500,
      };
    } else {
      throw {
        message: "An unexpected error occurred while adding a condition",
        statusText: "Unknown Error",
        status: 500,
      };
    }
  }
}

// Function to delete a condition
export async function deleteCondition(
  partId: number,
  dependantPartId: number
): Promise<void> {
  try {
    await apiService.delete(
      `productpartconditions/${partId}/${dependantPartId}`
    );
  } catch (error) {
    if (axios.isAxiosError(error)) {
      throw {
        message: "Failed to delete a condition",
        statusText: error.response?.statusText || "Network error",
        status: error.response?.status || 500,
      };
    } else {
      throw {
        message: "An unexpected error occurred deleting a condition",
        statusText: "Unknown Error",
        status: 500,
      };
    }
  }
}

// Function to authenticate an user
export async function authenticateUser(
  loginUser: LoginUser
): Promise<AuthResponseToken> {
  try {
    const res = await apiService.post(`login`, loginUser);
    return res.data;
  } catch (error) {
    if (axios.isAxiosError(error)) {
      throw {
        message: "Failed to authenticate",
        statusText: error.response?.statusText || "Network error",
        status: error.response?.status || 500,
      };
    } else {
      throw {
        message: "An unexpected error occurred while adding a condition",
        statusText: "Unknown Error",
        status: 500,
      };
    }
  }
}
