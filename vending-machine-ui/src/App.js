import React, { useState, useEffect } from 'react';
import './App.css';

function App() {
  const [productId, setProductId] = useState('');
  const [quantity, setQuantity] = useState('');
  const [inventory, setInventory] = useState([]);
  const [totalMoney, setTotalMoney] = useState(0);

  const handleRestock = async (event) => {
    event.preventDefault();
    
    const response = await fetch('http://localhost:8080/vending-machine/restock', {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ productId, quantity: parseInt(quantity) }),
    });

    if (response.ok) {
      alert('Product restocked successfully');
      loadInventory();
    } else {
      alert('Failed to restock product');
    }

    setProductId('');
    setQuantity('');
  };

  const loadInventory = async () => {
    const response = await fetch('http://localhost:8080/vending-machine/products');
    const data = await response.json();
    setInventory(data);
  };

  const loadTotalMoney = async () => {
    const response = await fetch('http://localhost:8080/vending-machine/total-money');
    const data = await response.json();
    setTotalMoney(data / 100);
  };

  useEffect(() => {
    loadInventory();
    loadTotalMoney();
  }, []);

  return (
    <div className="App">
      <h1>Vending Machine Management</h1>
      
      {}
      <div>
        <h2>Restock Products</h2>
        <form onSubmit={handleRestock}>
          <label>
            Product ID:
            <input
              type="text"
              value={productId}
              onChange={(e) => setProductId(e.target.value)}
              required
            />
          </label>
          <br />
          <label>
            Quantity:
            <input
              type="number"
              value={quantity}
              onChange={(e) => setQuantity(e.target.value)}
              required
            />
          </label>
          <br />
          <button type="submit">Restock</button>
        </form>
      </div>

      {}
      <div>
        <h2>Inventory</h2>
        <ul>
          {inventory.map((product) => (
            <li key={product.id}>
              {product.productName} - Quantity: {product.quantity}
            </li>
          ))}
        </ul>
      </div>

      {}
      <div>
        <h2>Total Money Stored</h2>
        <p>{(totalMoney / 100).toFixed(4)} USD</p>
      </div>
    </div>
  );
}

export default App;
