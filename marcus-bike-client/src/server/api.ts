import axios, { AxiosResponse } from "axios";
import { Product, ProductInsert } from "../models/products";
import { ProductPart } from "../models/productPart";
import { ProductPartCondition } from "../models/productPartCondition";

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
