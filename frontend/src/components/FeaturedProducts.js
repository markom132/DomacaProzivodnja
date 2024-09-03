import React from 'react';
import { Link } from 'react-router-dom';
import ProductCard from './ProductCard';
import honeyImage from '../assets/med.jpg'; // Putanja do slike
import cucumber from '../assets/cucumber.jpg';
import milk from '../assets/milk.jpg';

const FeaturedProducts = () => {
  const products = [
    { id: 1, image: honeyImage, name: 'Organski med', price: '$7.99' },
    { id: 2, image: cucumber, name: 'Krastavci', price: '$24.99' },
    { id: 3, image: milk, name: 'Mleko', price: '$8.99' },
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
