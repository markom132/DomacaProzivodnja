import React, { useState } from 'react';
import cucumber from '../assets/cucumber.jpg';
import broccoli from '../assets/broccoli.jpg';
import milk from '../assets/milk.jpg';
import PropTypes from 'prop-types';
import { Link } from 'react-router-dom';
import './ProductList.css';

const ProductList = () => {
  const products = [
    { id: 1, name: 'Cucumber1', price: '$0.99', image: cucumber },
    { id: 2, name: 'Broccoli1', price: '$1.29', image: broccoli },
    { id: 3, name: 'Milk1', price: '$1.99', image: milk },
    { id: 4, name: 'Cucumber2', price: '$0.99', image: cucumber },
    { id: 5, name: 'Broccoli2', price: '$1.29', image: broccoli },
    { id: 6, name: 'Milk2', price: '$1.99', image: milk },
    { id: 7, name: 'Cucumber3', price: '$0.99', image: cucumber },
    { id: 8, name: 'Broccoli3', price: '$1.29', image: broccoli },
    { id: 9, name: 'Milk3', price: '$1.99', image: milk },
    { id: 10, name: 'Cucumber4', price: '$0.99', image: cucumber },
    { id: 11, name: 'Broccoli4', price: '$1.29', image: broccoli },
    { id: 12, name: 'Milk4', price: '$1.99', image: milk },
    { id: 13, name: 'Cucumber5', price: '$0.99', image: cucumber },
    { id: 14, name: 'Broccoli5', price: '$1.29', image: broccoli },
    { id: 15, name: 'Milk5', price: '$1.99', image: milk },
    { id: 16, name: 'Cucumber6', price: '$0.99', image: cucumber },
    { id: 17, name: 'Broccoli6', price: '$1.29', image: broccoli },
    { id: 18, name: 'Milk6', price: '$1.99', image: milk },
    { id: 19, name: 'Cucumber7', price: '$0.99', image: cucumber },
    { id: 20, name: 'Broccoli7', price: '$1.29', image: broccoli },
    { id: 21, name: 'Milk7', price: '$1.99', image: milk },
  ];

  return (
    <section className='productSection'>
      <h2 className='sectionTitle'>Products</h2>
      <div className='productList'>
        {products.map(product => (
          <Link
            to={`/product/${product.id}`}
            key={product.id}
            className='productLink'
          >
            <ProductCard key={product.id} product={product} />
          </Link>
        ))}
      </div>
      <button className='showMoreButton'>Show more</button>
    </section>
  );
};

const ProductCard = ({ product }) => {
  const [isHovered, setIsHovered] = useState(false);

  return (
    <div
      className='productCard'
    >
      <div className='productImageContainer'>
        <img
          src={product.image}
          alt={product.name}
          className='productImage'
        />
      </div>
      <h3 className='productName'>{product.name}</h3>
      <p className='productPrice'>{product.price}</p>
      <button
        className='addButton'
        style={{
          backgroundColor: isHovered ? '#c5d047' : '#dff542',
          transform: isHovered ? 'translateY(-5px)' : 'translateY(0)',
          transition: 'background-color 0.3s ease, transform 0.3s ease',
        }}
      >
        Dodaj u korpu
      </button>
    </div>
  );
};

ProductCard.propTypes = {
  product: PropTypes.shape({
    image: PropTypes.string.isRequired,
    name: PropTypes.string.isRequired,
    price: PropTypes.string.isRequired,
  }).isRequired,
};

export default ProductList;