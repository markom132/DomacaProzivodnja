import React from 'react';
import PropTypes from 'prop-types';

const ProductCard = ({ image, name, price }) => {
  return (
    <div style={styles.card}>
      <img src={image} alt={name} style={styles.image} />
      <h3>{name}</h3>
      <p>{price}</p>
    </div>
  );
};

ProductCard.propTypes = {
  image: PropTypes.string.isRequired,
  name: PropTypes.string.isRequired,
  price: PropTypes.string.isRequired,
};

const styles = {
  card: {
    border: '1px solid #ccc',
    borderRadius: '30px',
    padding: '10px',
    textAlign: 'center',
    boxShadow: '0px 0px 15px rgba(0, 0, 0, 0.1)',
    maxWidth: '250px',
  },
  image: {
    width: '100%',
    height: 'auto',
    borderRadius: '20px',
  },
};

export default ProductCard;
