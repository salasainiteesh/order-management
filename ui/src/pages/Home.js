import React from "react";
import { Link } from "react-router-dom";

const Home = () => {
  return (
    <div>
      <h1>Order Management System</h1>
      <nav>
        <Link to="/orders">Orders</Link> | <Link to="/inventory">Inventory</Link>
      </nav>
    </div>
  );
};

export default Home;
