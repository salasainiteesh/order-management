import React, { useState, useEffect } from "react";
import axios from "axios";

const InventoryPage = () => {
  const [inventory, setInventory] = useState([]);
  const [updateProduct, setUpdateProduct] = useState("");
  const [updateQuantity, setUpdateQuantity] = useState("");

  // Fetch inventory on page load
  useEffect(() => {
    fetchInventory();
  }, []);

  const fetchInventory = async () => {
    try {
      const response = await axios.get("http://localhost:8081/api/inventory");
      setInventory(response.data);
    } catch (error) {
      console.error("Error fetching inventory:", error);
    }
  };

  // Handle Update Inventory Button Click
  const handleUpdateInventory = async () => {
    if (!updateProduct || !updateQuantity) {
      alert("Please enter both Product Name and Quantity");
      return;
    }

    try {
      await axios.post("http://localhost:8081/api/inventory/increase", null, {
        params: {
          productName: updateProduct,
          quantity: updateQuantity,
        },
      });

      alert("Inventory updated successfully!");

      setUpdateProduct("");
      setUpdateQuantity("");

      // Force Refresh Inventory Table After Update
      await fetchInventory();
    } catch (error) {
      console.error("Error updating inventory:", error);
      alert("Failed to update inventory!");
    }
  };


  return (
    <div>
      <h2>Inventory List</h2>
      <table border="1">
        <thead>
          <tr>
            <th>Product Name</th>
            <th>Quantity</th>
          </tr>
        </thead>
        <tbody>
          {inventory.map((item, index) => (
            <tr key={index}>
              <td>{item.productName}</td>
              <td>{item.quantity}</td>
            </tr>
          ))}
        </tbody>
      </table>

      <h3>Update Inventory</h3>
      <input
        type="text"
        placeholder="Product Name"
        value={updateProduct}
        onChange={(e) => setUpdateProduct(e.target.value)}
      />
      <input
        type="number"
        placeholder="Quantity"
        value={updateQuantity}
        onChange={(e) => setUpdateQuantity(e.target.value)}
      />
      <button onClick={handleUpdateInventory}>Update Inventory</button>
    </div>
  );
};

export default InventoryPage;
