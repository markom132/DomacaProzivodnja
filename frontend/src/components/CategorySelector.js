import React from 'react';
import './CategorySelector.css';

const CategorySelector = () => {
  const categories = [
    'Top offers',
    'Homemade meals',
    'Local drinks',
    'Miscellaneous',
  ];

  return (
    <section className='categorySection'>
      <h2 className='sectionTitle'>Istraži po kategorijama</h2>
      <div className='categoryList'>
        {categories.map(category => (
          <button key={category} className='categoryButton'>
            {category}
          </button>
        ))}
      </div>
    </section>
  );
};

export default CategorySelector;
