import React from 'react';

const CartPage = () => {
  return (
    <div style={styles.pageContainer}>
      <h2 style={styles.pageTitle}>Shopping Cart</h2>
      <p>Your cart is currently empty.</p>
      {/* Ovde ćeš prikazivati artikle u korpi kada budu dodati */}
    </div>
  );
};

const styles = {
  pageContainer: {
    padding: '20px',
    textAlign: 'center',
  },
  pageTitle: {
    fontSize: '2em',
    marginBottom: '20px',
  },
};

export default CartPage;
