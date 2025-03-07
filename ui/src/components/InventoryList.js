import axios from "axios";
import { useState, useEffect } from "react";

function InventoryList() {
    const [productName, setProductName] = useState("");
    const [quantity, setQuantity] = useState("");
    const [inventory, setInventory] = useState([]); // Holds the list of inventory items

    // Fetch inventory from the backend
    const fetchInventory = async () => {
        try {
            const response = await axios.get("http://localhost:8081/api/inventory");
            setInventory(response.data);
        } catch (error) {
            console.error("Error fetching inventory:", error);
            alert("Failed to load inventory.");
        }
    };

    // Function to add a new product
    const addProduct = async () => {
        try {
            console.log("Adding product:", productName, quantity);
            const response = await axios.post("http://localhost:8081/api/inventory/add",
                {
                    productName: productName,
                    quantity: parseInt(quantity)
                }
            );

            console.log("Added product response:", response.data);
            alert("Product added successfully!");
            setProductName("");
            setQuantity("");

            fetchInventory();  // Refresh the table
        } catch (error) {
            console.error("Error adding product:", error);
            alert("Failed to add product.");
        }
    };


    // Fetch inventory when the component loads
    useEffect(() => {
        fetchInventory();
    }, []);

    return (
        <div>
            <h2>Inventory Management</h2>

            {/* Form to add a new product */}
            <div>
                <input
                    type="text"
                    placeholder="Product Name"
                    value={productName}
                    onChange={(e) => setProductName(e.target.value)}
                />
                <input
                    type="number"
                    placeholder="Quantity"
                    value={quantity}
                    onChange={(e) => setQuantity(e.target.value)}
                />
                <button onClick={addProduct}>Add Product</button>
            </div>

            {/* Display inventory table */}
            <h3>Current Inventory</h3>
            <table border="1">
                <thead>
                    <tr>
                        <th>Product Name</th>
                        <th>Quantity</th>
                    </tr>
                </thead>
                <tbody>
                    {inventory.length > 0 ? (
                        inventory.map((item, index) => (
                            <tr key={index}>
                                <td>{item.productName}</td>
                                <td>{item.quantity}</td>
                            </tr>
                        ))
                    ) : (
                        <tr>
                            <td colSpan="2">No inventory available</td>
                        </tr>
                    )}
                </tbody>
            </table>
        </div>
    );
}

export default InventoryList;
