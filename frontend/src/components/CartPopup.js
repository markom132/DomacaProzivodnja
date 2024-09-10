import React, { useState } from 'react';
import med from '../assets/med.jpg';
import cucumber from '../assets/cucumber.jpg';
import milk from '../assets/milk.jpg';
import PropTypes from 'prop-types';
import EmptyCartSVGIcon from './EmptyCartSVGIcon';
import './CartPopup.css';

const CartPopup = ({ setPopupVisible }) => {
  const getTotalPrice = () => {
    return cartItems
      .reduce((total, item) => total + (item.price / 100) * item.quantity, 0)
      .toFixed(2);
  };

  const [cartItems, setCartItems] = useState([
    { id: 1, name: 'Organski med', price: 7999, quantity: 1, imageUrl: med },
    { id: 2, name: 'Krastavci', price: 3999, quantity: 1, imageUrl: cucumber },
    { id: 3, name: 'Mleko', price: 3999, quantity: 1, imageUrl: milk },
    { id: 4, name: 'Organski med', price: 7999, quantity: 1, imageUrl: med },
    { id: 5, name: 'Krastavci', price: 3999, quantity: 1, imageUrl: cucumber },
    { id: 6, name: 'Mleko', price: 3999, quantity: 1, imageUrl: milk },
    { id: 7, name: 'Organski med', price: 7999, quantity: 1, imageUrl: med },
    { id: 8, name: 'Krastavci', price: 3999, quantity: 1, imageUrl: cucumber },
    { id: 9, name: 'Mleko', price: 3999, quantity: 1, imageUrl: milk },
  ]);
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
    <div
      className="cartPopup"
      onMouseEnter={() => setPopupVisible(true)}
      onMouseLeave={() => setPopupVisible(false)}
    >
      {cartItems.length > 0 ? (
        <>
          <div>
            {cartItems.map(item => (
              <div key={item.id} className="popupCartItem">
                <img
                  src={item.imageUrl}
                  alt={item.name}
                  className="productImagePopup"
                />
                <div className="productInfoPopup">
                  <strong>{item.name}</strong>
                  <p>
                    {item.quantity} x {(item.price / 100).toFixed(2)} RSD
                  </p>
                </div>
                <span className="pricePopup">
                  {((item.price * item.quantity) / 100).toFixed(2)} RSD
                </span>
                <div class="quantity-controls">
                  <button
                    class="quantity-button"
                    onClick={() => decreaseQuantity(item.id)}
                  >
                    −
                  </button>
                  <span class="quantity-display">{item.quantity}</span>
                  <button
                    class="quantity-button"
                    onClick={() => increaseQuantity(item.id)}
                  >
                    +
                  </button>
                </div>
                <button
                  class="remove-button"
                  onClick={() => removeItem(item.id)}
                >
                  ×
                </button>
              </div>
            ))}
          </div>
          <div className="totalPrice">
            <strong>Ukupno: {getTotalPrice()} RSD</strong>
          </div>
          <div className="buttonContainer">
            <button className="cartButton">Pregled korpe</button>
            <button className="checkoutButtonPopup">Poruči</button>
          </div>
        </>
      ) : (
        <div className="emptyCartPopup">
          <p>
            Vaša korpa je trenutno prazna. Pogledajte naše proizvode i dodajte
            ih u korpu!
          </p>
          <EmptyCartSVGIcon />
          <button className="checkoutButtonPopup">Istraži ponudu</button>
        </div>
      )}
    </div>
  );
};

CartPopup.propTypes = {
  setPopupVisible: PropTypes.func.isRequired,
};

export default CartPopup;
