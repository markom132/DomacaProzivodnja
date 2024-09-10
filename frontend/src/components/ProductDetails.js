import React from 'react';
import honeyImage from '../assets/med.jpg';
import './ProductDetails.css';

const ProductDetails = () => {
  const product = {
    name: 'Med',
    description: 'Ovo je opis testnog prozivoda----MED',
    price: '600 RSD',
    mainImage: honeyImage,
    thumbnails: [honeyImage, honeyImage, honeyImage],
    relatedProducts: [
      { id: 1, name: 'Related Product 1', price: '$49.99', image: honeyImage },
      { id: 2, name: 'Related Product 2', price: '$59.99', image: honeyImage },
      { id: 3, name: 'Related Product 3', price: '$89.99', image: honeyImage },
      { id: 4, name: 'Related Product 4', price: '$79.99', image: honeyImage },
    ],
  };

  return (
    <div className='container'>
      <div className='productDetailsInfo'>
        <div className='images'>
          <img
            src={product.mainImage}
            alt={product.name}
            className='mainImage'
          />
          <div className='thumbnails'>
            {product.thumbnails.map((thumb, index) => (
              <img
                key={index}
                src={thumb}
                alt={`Thumbnail ${index + 1}`}
                className='thumbnail'
              />
            ))}
          </div>
        </div>
        <div className='details'>
          <h1>{product.name}</h1>
          <p>{product.description}</p>
          <p className='productDetailsPrice'>{product.price}</p>
          <div className='buttons'>
            <button className='addToCart'>Dodaj u korpu</button>
            <button className='addToWishlist'>Sačuvaj proizvod</button>
          </div>
        </div>
      </div>

      <div className='relatedProducts'>
        <h2>Slicni proizvodi</h2>
        <div className='relatedList'>
          {product.relatedProducts.map(related => (
            <div key={related.id} className='relatedCard'>
              <img
                src={related.image}
                alt={related.name}
                className='relatedImage'
              />
              <h4>{related.name}</h4>
              <div className='relatedPrice'>{related.price}</div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default ProductDetails;
