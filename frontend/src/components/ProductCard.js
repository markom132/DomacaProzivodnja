import React from 'react';
import PropTypes from 'prop-types';

const ProductCard = ({ image, name, price }) => {
  const [isHovered, setIsHovered] = React.useState(false);

  return (
    <div
      style={{
        ...styles.card,
        transform: isHovered ? 'scale(1.05)' : 'scale(1)',
      }}
      onMouseOver={() => setIsHovered(true)}
      onMouseOut={() => setIsHovered(false)}
    >
      <div style={styles.imageContainer}>
        <img
          src={image}
          alt={name}
          style={{
            ...styles.image,
            ...(isHovered ? styles.imageHover : {}),
          }}
        />
      </div>
      <div style={styles.info}>
        <h3>{name}</h3>
        <p>{price}</p>
        <button
          style={{
            ...styles.button,
            ...(isHovered ? styles.buttonHover : {}),
          }}
        >
          Dodaj u korpu
        </button>
      </div>
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
    position: 'relative',
    overflow: 'hidden',
    border: ' 1px solid #ddd',
    borderRadius: '10px',
    transition: 'transform 0.3s ease',
    margin: '10px',
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
    transition: 'transform 0.3s ease',
  },
  imageHover: {
    trnasform: 'scale(1.1)',
  },
  info: {
    padding: '15px',
    textAlign: 'center',
  },
  button: {
    backgroundColor: '#f8f8f8',
    border: 'none',
    borderRadius: '5px',
    padding: '10px 20px',
    cursor: 'pointer',
    transition: 'background-color 0.3 ease',
  },
  buttonHover: {
    backgroundColor: '#ff9800',
    color: '#fff',
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
