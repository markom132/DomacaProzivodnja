import React from 'react';
import med from '../assets/med.jpg';
import cucumber from '../assets/cucumber.jpg';
import milk from '../assets/milk.jpg';
import PropTypes from 'prop-types';

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
      style={styles.cartPopup}
      onMouseEnter={() => setPopupVisible(true)}
      onMouseLeave={() => setPopupVisible(false)}
    >
      {cartItems.length > 0 ? (
        <>
          <div style={styles.cartList}>
            {cartItems.map(item => (
              <div key={item.id} style={styles.popupCartItem}>
                <img
                  src={item.imageUrl}
                  alt={item.name}
                  style={styles.productImagePopup}
                />
                <div style={styles.productInfoPopup}>
                  <strong>{item.name}</strong>
                  <p>
                    {item.quantity} x {(item.price / 100).toFixed(2)} RSD
                  </p>
                </div>
                <span style={styles.pricePopup}>
                  {((item.price * item.quantity) / 100).toFixed(2)} RSD
                </span>
              </div>
            ))}
          </div>
          <div style={styles.totalPrice}>
            <strong>Ukupno: {getTotalPrice()} RSD</strong>
          </div>
          <div style={styles.buttonContainer}>
            <button style={styles.cartButton}>Pregled korpe</button>
            <button style={styles.checkoutButton}>Poruči</button>
          </div>
        </>
      ) : (
        <p>Vaša korpa je prazna</p>
      )}
    </div>
  );
};

const styles = {
  cartPopup: {
    position: 'absolute',
    top: '25px',
    right: '0',
    width: '300px',
    maxHeight: '400px',
    overflowY: 'auto',
    backgroundColor: '#fff',
    border: '1px solid #ccc',
    borderRadius: '8px',
    padding: '20px',
    boxShadow: '0px 4px 8px rgba(0, 0, 0, 0.1)',
    zIndex: '10',
  },
  popupCartItem: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'space-between',
    marginBottom: '10px',
    borderBottom: '1px solid #ddd',
    paddingBottom: '10px',
  },
  productImagePopup: {
    width: '50px',
    height: '50px',
    objectFit: 'cover',
    borderRadius: '5px',
    marginRight: '10px',
  },
  productInfoPopup: {
    flex: '2',
    textAlign: 'left',
  },
  pricePopup: {
    flex: '1',
    textAlign: 'right',
    fontSize: '1.2em',
    color: '#333',
  },
  totalPrice: {
    textAlign: 'right',
    fontWeight: 'bold',
  },
  buttonContainer: {
    display: 'flex',
    justifyContent: 'space-between',
    gap: '10px',
    marginTop: '10px',
  },
  cartButton: {
    flex: '1',
    padding: '10px',
    backgroundColor: '#dff542',
    color: '#000',
    textDecoration: 'none',
    borderRadius: '5px',
    border: 'none',
    cursor: 'pointer',
    textAlign: 'center',
  },
  checkoutButton: {
    flex: '1',
    padding: '10px',
    backgroundColor: '#32CD32',
    color: '#fff',
    textDecoration: 'none',
    borderRadius: '5px',
    border: 'none',
    cursor: 'pointer',
    textAlign: 'center',
  },
};

CartPopup.propTypes = {
  setPopupVisible: PropTypes.func.isRequired,
};

export default CartPopup;
