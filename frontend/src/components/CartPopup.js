import React from 'react';
import med from '../assets/med.jpg';
import cucumber from '../assets/cucumber.jpg';
import milk from '../assets/milk.jpg';
import PropTypes from 'prop-types';
import './CartPopup.css';

const CartPopup = ({ setPopupVisible }) => {
  const getTotalPrice = () => {
    return cartItems
      .reduce((total, item) => total + item.price * item.quantity, 0)
      .toFixed(2);
  };

  const cartItems = [
    { id: 1, name: 'Organski med', price: 7999, quantity: 1, imageUrl: med },
    { id: 2, name: 'Krastavci', price: 3999, quantity: 1, imageUrl: cucumber },
    { id: 3, name: 'Mleko', price: 3999, quantity: 1, imageUrl: milk },
    { id: 4, name: 'Organski med', price: 7999, quantity: 1, imageUrl: med },
    { id: 5, name: 'Krastavci', price: 3999, quantity: 1, imageUrl: cucumber },
    { id: 6, name: 'Mleko', price: 3999, quantity: 1, imageUrl: milk },
    { id: 7, name: 'Organski med', price: 7999, quantity: 1, imageUrl: med },
    { id: 8, name: 'Krastavci', price: 3999, quantity: 1, imageUrl: cucumber },
    { id: 9, name: 'Mleko', price: 3999, quantity: 1, imageUrl: milk },
  ];

  return (
    <div
      className='cartPopup'
      onMouseEnter={() => setPopupVisible(true)}
      onMouseLeave={() => setPopupVisible(false)}
    >
      {cartItems.length > 0 ? (
        <>
          <div>
            {cartItems.map(item => (
              <div key={item.id} className='popupCartItem'>
                <img
                  src={item.imageUrl}
                  alt={item.name}
                  className='productImagePopup'
                />
                <div className='productInfoPopup'>
                  <strong>{item.name}</strong>
                  <p>
                    {item.quantity} x {(item.price / 100).toFixed(2)} RSD
                  </p>
                </div>
                <span className='pricePopup'>
                  {((item.price * item.quantity) / 100).toFixed(2)} RSD
                </span>
              </div>
            ))}
          </div>
          <div className='totalPrice'>
            <strong>Ukupno: {getTotalPrice()} RSD</strong>
          </div>
          <div className='buttonContainer'>
            <button className='cartButton'>Pregled korpe</button>
            <button className='checkoutButtonPopup'>Poruči</button>
          </div>
        </>
      ) : (
        <p>Vaša korpa je prazna</p>
      )}
    </div>
  );
};

CartPopup.propTypes = {
  setPopupVisible: PropTypes.func.isRequired,
};

export default CartPopup;
