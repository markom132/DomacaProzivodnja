import React from 'react';
import { Link } from 'react-router-dom';
import ProductCard from './ProductCard';
import honeyImage from '../assets/med.jpg'; // Putanja do slike
import mugsImage from '../assets/med.jpg';
import jamsImage from '../assets/med.jpg';

const FeaturedProducts = () => {
  const products = [
    { id: 1, image: honeyImage, name: 'Organic Honey', price: '$7.99' },
    { id: 2, image: mugsImage, name: 'Handmade Ceramic Mugs', price: '$24.99' },
    { id: 3, image: jamsImage, name: 'Homemade Jams', price: '$8.99' },
  ];

  return (
    <section style={styles.featuredSection}>
      <h2>Featured Products</h2>
      <div style={styles.productList}>
        {products.map(product => (
          <Link
            to={`/product/${product.id}`}
            key={product.id}
            style={styles.productLink}
          >
            <ProductCard
              image={product.image}
              name={product.name}
              price={product.price}
            />
          </Link>
        ))}
      </div>
    </section>
  );
};

const styles = {
  featuredSection: {
    padding: '20px',
    textAlign: 'center',
  },
  productList: {
    display: 'flex',
    justifyContent: 'center',
    gap: '30px',
  },
  productLink: {
    textDecoration: 'none',
    color: 'inherit',
  },
};

export default FeaturedProducts;
