import React, { useState } from 'react';
import med from '../assets/med.jpg';
import cucumber from '../assets/cucumber.jpg';
import milk from '../assets/milk.jpg';
import './CartPage.css';

const CartPage = () => {
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
      id: 4,
      name: 'Organski med',
      description: 'Organski med Opis',
      quantity: 1,
      price: 7999,
      imageUrl: med,
    },
    {
      id: 5,
      name: 'Krastavci',
      description: 'Krastavci Opis',
      quantity: 1,
      price: 3999,
      imageUrl: cucumber,
    },
    {
      id: 6,
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
    <div className='pageCartContainer'>
      <h2 className='pageTitleCart'>Vaša korpa</h2>
      {cartItems.length === 0 ? (
        <p>Vaša korpa je prazna.</p>
      ) : (
        <div>
          {cartItems.map(item => (
            <div key={item.id} className='cartItem'>
              <img
                src={item.imageUrl}
                alt={item.name}
                className='productImage'
              />
              <div className='productInfo'>
                <strong>{item.name}</strong>
                <p>{item.description}</p>
              </div>
              <div className='quantityControl'>
                <button
                  onClick={() => decreaseQuantity(item.id)}
                  className='quantityButton'
                >
                  -
                </button>
                <span>{item.quantity}</span>
                <button
                  onClick={() => increaseQuantity(item.id)}
                  className='quantityButton'
                >
                  +
                </button>
              </div>
              <span className='price'>
                {(item.price * item.quantity).toFixed(2)} RSD
              </span>
              <button
                onClick={() => removeItem(item.id)}
                className='removeButton'
              >
                x
              </button>
            </div>
          ))}
          <div className='totalPrice'>
            <strong>Ukupno: {getTotalPrice()} RSD</strong>
          </div>
        </div>
      )}
    </div>
  );
};

export default CartPage;
