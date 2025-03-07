import axios from "axios";

const API_URL = "http://localhost:8081/api/inventory";

export const getInventory = async (productName) => {
    try {
        const response = await axios.get(`${API_URL}/${productName}`);
        return response.data;
    } catch (error) {
        console.error("Error fetching inventory:", error);
        throw error;
    }
};
