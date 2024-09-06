import React from 'react';
import cucumber from '../assets/cucumber.jpg';
import broccoli from '../assets/broccoli.jpg';
import milk from '../assets/milk.jpg';
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
          <div key={product.name} style={styles.productCard}>
            <img
              src={product.image}
              alt={product.name}
              style={styles.productImage}
            />
            <h3 style={styles.productName}>{product.name}</h3>
            <p style={styles.productPrice}>{product.price}</p>
            <button style={styles.addButton}>Add to cart</button>
          </div>
        ))}
      </div>
      <button style={styles.showMoreButton}>Show more</button>
    </section>
  );
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
    padding: '10px',
    border: '1px solid #ddd',
    borderRadius: '10px',
    textAlign: 'center',
    backgroundColor: '#fff',
  },
  productImage: {
    width: '100%',
    borderRadius: '10px',
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
