import React from 'react';
import CategorySelector from './CategorySelector';
import NewsletterSignup from './NewsletterSignup';
import ProductList from './ProductList';

const CategoryPage = () => {
  console.log('CategoryPage rendered');

  return (
    <div>
      <CategorySelector />
      <ProductList />
      <NewsletterSignup />
    </div>
  );
};

export default CategoryPage;
