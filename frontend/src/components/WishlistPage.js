import React from 'react';

const WishlistPage = () => {
  return (
    <div style={styles.pageContainer}>
      <h2 style={styles.pageTitle}>Wishlist</h2>
      <p>Your wishlist is currently empty.</p>
      {/* Ovde ćeš prikazivati artikle na wishlisti kada budu dodati */}
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

export default WishlistPage;
