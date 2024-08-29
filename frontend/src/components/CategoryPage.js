import React from 'react';
import CategorySelector from './CategorySelector';
import NewsletterSignup from './NewsletterSignup';
import ProductList from './ProductList';

const CategoryPage = () => {
  return (
    <div>
      <CategorySelector />
      <ProductList />
      <NewsletterSignup />
    </div>
  );
};

export default CategoryPage;
