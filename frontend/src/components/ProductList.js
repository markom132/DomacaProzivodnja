import React, { useState } from 'react';
import cucumber from '../assets/cucumber.jpg';
import broccoli from '../assets/broccoli.jpg';
import milk from '../assets/milk.jpg';
import PropTypes from 'prop-types';

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
    <section style={styles.productSection}>
      <h2 style={styles.sectionTitle}>Products</h2>
      <div style={styles.productList}>
        {products.map(product => (
          <ProductCard key={product.id} product={product} />
        ))}
      </div>
      <button style={styles.showMoreButton}>Show more</button>
    </section>
  );
};

const ProductCard = ({ product }) => {
  const [isHovered, setIsHovered] = useState(false);

  return (
    <div
      style={{
        ...styles.productCard,
        transform: isHovered ? 'scale(1.05)' : 'scale(1)',
        transition: 'transform 0.3s ease',
      }}
      onMouseOver={() => setIsHovered(true)}
      onMouseOut={() => setIsHovered(false)}
    >
      <div style={styles.productImageContainer}>
        <img
          src={product.image}
          alt={product.name}
          style={{
            ...styles.productImage,
            border: isHovered ? '2px solid #dff542' : 'none',
            transition: 'border 0.3s ease',
          }}
        />
      </div>
      <h3 style={styles.productName}>{product.name}</h3>
      <p style={styles.productPrice}>{product.price}</p>
      <button
        style={{
          ...styles.addButton,
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

const styles = {
  productSection: {
    padding: '20px',
  },
  sectionTitle: {
    marginBottom: '10px',
    fontSize: '1.5em',
  },
  productList: {
    display: 'grid',
    gridTemplateColumns: 'repeat(auto-fit, minmax(150px, 1fr))',
    gap: '20px',
  },
  productCard: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    padding: '10px',
    border: '1px solid #ddd',
    borderRadius: '10px',
    textAlign: 'center',
    backgroundColor: '#fff',
    overflow: 'hidden',
    boxSizing: 'border-box',
    marginBottom: '20px',
    transition: 'transform 0.3s ease',
    position: 'relative',
  },
  productImageContainer: {
    width: '100%',
    height: '100px',
    overflow: 'hidden',
    borderRadius: '10px',
    marginBottom: '10px',
  },
  productImage: {
    width: '100%',
    height: '100%',
    objectFit: 'cover',
    transition: 'border 0.3s ease',
  },
  productName: {
    fontSize: '1.2em',
    margin: '10px 0',
  },
  productPrice: {
    fontSize: '1em',
    color: '#555',
  },
  addButton: {
    padding: '10px 20px',
    borderRadius: '20px',
    border: 'none',
    backgroundColor: '#dff542',
    cursor: 'pointer',
    transition: 'background-color 0.3s ease, transform 0.3s ease',
    position: 'absolute',
    bottom: '10px',
  },
  showMoreButton: {
    marginTop: '20px',
    padding: '10px 20px',
    borderRadius: '20px',
    border: 'none',
    backgroundColor: '#dff542',
    cursor: 'pointer',
  },
};

export default ProductList;
