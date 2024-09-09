import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import med from '../assets/med.jpg';
import cucumber from '../assets/cucumber.jpg';
import milk from '../assets/milk.jpg';
import EmptyCartSVGIcon from './EmptyCartSVGIcon';
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
        <div className='emptyCart'>
          <EmptyCartSVGIcon />
          <h2>Vaša korpa je trenutno prazna.</h2>
          <p> Istražite našu ponudu i dodajte proizvode u korpu!</p>
          <a href="/proizvodi" className="cta-button">Istraži proizvode</a>

        </div>
      ) : (
        <div>
          {cartItems.map(item => (
            <div key={item.id} className='cartItem'>
              <Link to={`/product/${item.id}`}>
                <img
                  src={item.imageUrl}
                  alt={item.name}
                  className='productImageCart'
                />
              </Link>
              <div className='productInfo'>
                <Link to={`/product/${item.id}`} style={{ textDecoration: 'none' }}>
                  <strong>{item.name}</strong>
                </Link>
                <p>{item.description}</p>
              </div>
              <div className='quantityControl'>
                <button
                  onClick={() => decreaseQuantity(item.id)}
                  className='quantityButton'
                >
                  -
                </button>
                <span style={{ fontWeight: 'bold', fontSize: '18px' }}>{item.quantity}</span>
                <button
                  onClick={() => increaseQuantity(item.id)}
                  className='quantityButton'
                >
                  +
                </button>
              </div>
              <span className='priceCartItems'>
                {(item.price * item.quantity).toFixed(2)} RSD
              </span>
              <button
                onClick={() => removeItem(item.id)}
                className='removeButton'
                title="Ukloni proizvod"
              >
                x
              </button>
            </div>
          ))}
          <div className='checkoutSection'>
            <strong>Ukupno: {getTotalPrice()} RSD</strong>
            <button className='checkoutButton separateButton'>
              Poruči
            </button>
          </div>
        </div>
      )}
    </div>
  );
};

export default CartPage;
