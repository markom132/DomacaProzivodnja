import React from 'react';

const CategorySelector = () => {
  const categories = ['Top offers', 'Homemade meals', 'Local drinks', 'Miscellaneous'];

  return (
    <section style={styles.categorySection}>
      <h2 style={styles.sectionTitle}>Explore by categories</h2>
      <div style={styles.categoryList}>
        {categories.map(category => (
          <button key={category} style={styles.categoryButton}>{category}</button>
        ))}
      </div>
    </section>
  );
};

const styles = {
  categorySection: {
    padding: '20px',
  },
  sectionTitle: {
    marginBottom: '10px',
    fontSize: '1.5em',
  },
  categoryList: {
    display: 'flex',
    flexWrap: 'wrap',
    gap: '10px',
  },
  categoryButton: {
    padding: '10px 20px',
    borderRadius: '20px',
    border: '1px solid #ddd',
    backgroundColor: '#fff',
    cursor: 'pointer',
  },
};

export default CategorySelector;
