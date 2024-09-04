import React, { useState } from 'react';
import med from '../assets/med.jpg';
import cucumber from '../assets/cucumber.jpg';
import milk from '../assets/milk.jpg';

const CartPage = () => {
  // Mock podaci
  const [cartItems, setCartItems] = useState([
    {
      id: 1,
      name: 'Organski med',
      description: 'Organski med Opis',
      quantity: 1,
      price: 7999,
      imageUrl: med,
    },
    {
      id: 2,
      name: 'Krastavci',
      description: 'Krastavci Opis',
      quantity: 1,
      price: 3999,
      imageUrl: cucumber,
    },
    {
      id: 3,
      name: 'Mleko',
      description: 'Mleko Opis',
      quantity: 1,
      price: 3999,
      imageUrl: milk,
    },
    {
      id: 1,
      name: 'Organski med',
      description: 'Organski med Opis',
      quantity: 1,
      price: 7999,
      imageUrl: med,
    },
    {
      id: 2,
      name: 'Krastavci',
      description: 'Krastavci Opis',
      quantity: 1,
      price: 3999,
      imageUrl: cucumber,
    },
    {
      id: 3,
      name: 'Mleko',
      description: 'Mleko Opis',
      quantity: 1,
      price: 3999,
      imageUrl: milk,
    },
  ]);

  const getTotalPrice = () => {
    return cartItems
      .reduce((total, item) => total + item.price * item.quantity, 0)
      .toFixed(2);
  };

  const increaseQuantity = id => {
    setCartItems(
      cartItems.map(item =>
        item.id === id ? { ...item, quantity: item.quantity + 1 } : item,
      ),
    );
  };

  const decreaseQuantity = id => {
    setCartItems(
      cartItems.map(item =>
        item.id === id && item.quantity > 1
          ? { ...item, quantity: item.quantity - 1 }
          : item,
      ),
    );
  };

  const removeItem = id => {
    setCartItems(cartItems.filter(item => item.id !== id));
  };

  return (
    <div style={styles.pageContainer}>
      <h2 style={styles.pageTitle}>Vaša korpa</h2>
      {cartItems.length === 0 ? (
        <p>Vaša korpa je prazna.</p>
      ) : (
        <div>
          {cartItems.map(item => (
            <div key={item.id} style={styles.cartItem}>
              <img
                src={item.imageUrl}
                alt={item.name}
                style={styles.productImage}
              />
              <div style={styles.productInfo}>
                <strong>{item.name}</strong>
                <p>{item.description}</p>
              </div>
              <div style={styles.quantityControl}>
                <button
                  onClick={() => decreaseQuantity(item.id)}
                  style={styles.quantityButton}
                >
                  -
                </button>
                <span>{item.quantity}</span>
                <button
                  onClick={() => increaseQuantity(item.id)}
                  style={styles.quantityButton}
                >
                  +
                </button>
              </div>
              <span style={styles.price}>
                {(item.price * item.quantity).toFixed(2)} RSD
              </span>
              <button
                onClick={() => removeItem(item.id)}
                style={styles.removeButton}
              >
                x
              </button>
            </div>
          ))}
          <div style={styles.totalPrice}>
            <strong>Ukupno: {getTotalPrice()} RSD</strong>
          </div>
        </div>
      )}
    </div>
  );
};

const styles = {
  pageContainer: {
    padding: '20px',
    textAlign: 'center',
    backgroundColor: '#fff',
    borderRadius: '10px',
    boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)',
  },
  pageTitle: {
    fontSize: '2em',
    marginBottom: '20px',
  },
  cartItem: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'space-between',
    marginBottom: '20px',
    padding: '10px',
    borderBottom: '1px solid #ccc',
  },
  productImage: {
    width: '80px',
    height: '80px',
    objectFit: 'cover',
    borderRadius: '10px',
    marginRight: '15px',
  },
  productInfo: {
    flex: '2',
    textAlign: 'left',
  },
  quantityControl: {
    display: 'flex',
    alignItems: 'center',
    flex: '1',
    justifyContent: 'center',
  },
  quantityButton: {
    padding: '5px 10px',
    margin: '0 10px',
    fontSize: '1.2em',
    border: 'none',
    backgroundColor: '#f0f0f0',
    cursor: 'pointer',
  },
  price: {
    flex: '1',
    textAlign: 'right',
    fontSize: '1.2em',
    color: '#333',
  },
  removeButton: {
    flex: '0.5',
    border: 'none',
    backgroundColor: 'transparent',
    color: 'red',
    cursor: 'pointer',
    fontSize: '1.5em',
  },
  totalPrice: {
    textAlign: 'right',
    fontSize: '1.5em',
    marginTop: '20px',
  },
};

export default CartPage;
