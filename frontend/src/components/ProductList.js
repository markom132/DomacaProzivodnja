import React from 'react';
import cucumber from '../assets/cucumber.jpg';
import broccoli from '../assets/broccoli.jpg';
import milk from '../assets/milk.jpg';
const ProductList = () => {
  const products = [
    { name: 'Cucumber', price: '$0.99', image: cucumber },
    { name: 'Broccoli', price: '$1.29', image: broccoli },
    { name: 'Milk', price: '$1.99', image: milk },
    // Dodaj više proizvoda sa odgovarajućim podacima
  ];

  return (
    <section style={styles.productSection}>
      <h2 style={styles.sectionTitle}>Products</h2>
      <div style={styles.productList}>
        {products.map(product => (
          <div key={product.name} style={styles.productCard}>
            <img src={product.image} alt={product.name} style={styles.productImage}/>
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
