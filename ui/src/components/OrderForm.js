import React, { useState } from "react";
import { createOrder } from "../api";

const OrderForm = () => {
  const [order, setOrder] = useState({
    name: "",
    productName: "",
    quantity: 1,
  });

  const handleChange = (e) => {
    setOrder({ ...order, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    await createOrder(order);
    alert("Order created successfully!");
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>Create Order</h2>
      <input type="text" name="name" placeholder="Name" onChange={handleChange} />
      <input type="text" name="productName" placeholder="Product Name" onChange={handleChange} />
      <input type="number" name="quantity" placeholder="Quantity" onChange={handleChange} />
      <button type="submit">Submit</button>
    </form>
  );
};

export default OrderForm;
