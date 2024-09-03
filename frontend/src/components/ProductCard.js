import React from 'react';
import PropTypes from 'prop-types';

const ProductCard = ({ image, name, price }) => {
  return (
    <div style={styles.card}>
      <div style={styles.imageContainer}>
        <img src={image} alt={name} style={styles.image} />
      </div>
      <h3 style={styles.name}>{name}</h3>
      <p style={styles.price}>{price}</p>
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
   display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    textAlign: 'center',
    borderRadius: '10px',
    overflow: 'hidden',
    boxShadow: '0px 4px 6px rgba(0, 0, 0, 0.1)',
    backgroundColor: '#fff',
    height: '100%'
  },
  imageContainer: {
    width: '100%',
    height: '150px',
    overflow: 'hidden',
  },
  image: {
    width: '100%',
    height: '100%',
    objectFit: 'cover',
  },
  name: {
    margin: '10px 0',
    fontSize: '1.2em',
    fontWeight: 'bold',
  },
  price: {
    fontSize: '1em',
    color: '#555',
  },
};

export default ProductCard;
