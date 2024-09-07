import React from 'react';
import PropTypes from 'prop-types';
import './ProductCard.css';

const ProductCard = ({ image, name, price }) => {
  const [isHovered, setIsHovered] = React.useState(false);

  return (
    <div
      className='card'
      onMouseOver={() => setIsHovered(true)}
      onMouseOut={() => setIsHovered(false)}
    >
      <div className='imageContainer'>
        <img
          src={image}
          alt={name}
          className='image'
        />
      </div>
      <div className='info'>
        <h3 className='name'>{name}</h3>
        <p className='price'>{price}</p>
        <button
          className='button'
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

export default ProductCard;
