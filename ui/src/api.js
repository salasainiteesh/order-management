import axios from "axios";

const API_BASE_URL = "http://localhost:8081/api";

export const getOrders = async () => {
  const response = await axios.get(`${API_BASE_URL}/orders`);
  return response.data;
};

export const createOrder = async (order) => {
  const response = await axios.post(`${API_BASE_URL}/orders`, order);
  return response.data;
};

export const getInventory = async () => {
  const response = await axios.get(`${API_BASE_URL}/inventory`);
  return response.data;
};
