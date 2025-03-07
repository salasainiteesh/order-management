import axios from "axios";
import { useState, useEffect } from "react";

function OrderList() {
    const [orders, setOrders] = useState([]);
    const [name, setName] = useState("");
    const [productName, setProductName] = useState("");
    const [quantity, setQuantity] = useState("");

    // Fetch orders from backend
    const fetchOrders = async () => {
        try {
            const response = await axios.get("http://localhost:8081/api/orders");
            setOrders(response.data);
        } catch (error) {
            console.error("Error fetching orders:", error);
        }
    };

    useEffect(() => {
        fetchOrders(); // Fetch orders on component load
    }, []);

    const addOrder = async () => {
        try {
            await axios.post("http://localhost:8081/api/orders", {
                name,
                productName,
                quantity: parseInt(quantity),
            });

            alert("Order placed successfully!");
            setName("");
            setProductName("");
            setQuantity("");
            fetchOrders();  //
        } catch (error) {
            console.error("Error placing order:", error);
            alert("Failed to place order.");
        }
    };

    return (
        <div>
            <h2>Place Order</h2>

            <div>
                <input type="text" placeholder="Customer Name" value={name} onChange={(e) => setName(e.target.value)} />
                <input type="text" placeholder="Product Name" value={productName} onChange={(e) => setProductName(e.target.value)} />
                <input type="number" placeholder="Quantity" value={quantity} onChange={(e) => setQuantity(e.target.value)} />
                <button onClick={addOrder}>Submit</button>
            </div>


            <h3>Current Orders</h3>
            <table border="1">
                <thead>
                    <tr>
                        <th>Order ID</th>
                        <th>Customer Name</th>
                        <th>Product Name</th>
                        <th>Quantity</th>
                    </tr>
                </thead>
                <tbody>
                    {orders.length > 0 ? (
                        orders.map((order, index) => (
                            <tr key={index}>
                                <td>{order.id}</td>
                                <td>{order.name}</td>
                                <td>{order.productName}</td>
                                <td>{order.quantity}</td>
                            </tr>
                        ))
                    ) : (
                        <tr>
                            <td colSpan="4">No orders available</td>
                        </tr>
                    )}
                </tbody>
            </table>
        </div>
    );
}

export default OrderList;
