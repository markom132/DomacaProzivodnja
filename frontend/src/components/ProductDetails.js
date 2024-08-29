import React from 'react';
import honeyImage from '../assets/med.jpg'; // Putanja do slike

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
    <div style={styles.container}>
      <div style={styles.productInfo}>
        <div style={styles.images}>
          <img
            src={product.mainImage}
            alt={product.name}
            style={styles.mainImage}
          />
          <div style={styles.thumbnails}>
            {product.thumbnails.map((thumb, index) => (
              <img
                key={index}
                src={thumb}
                alt={`Thumbnail ${index + 1}`}
                style={styles.thumbnail}
              />
            ))}
          </div>
        </div>
        <div style={styles.details}>
          <h1>{product.name}</h1>
          <p>{product.description}</p>
          <p style={styles.price}>{product.price}</p>
          <div style={styles.buttons}>
            <button style={styles.addToCart}>Dodaj u korpu</button>
            <button style={styles.addToWishlist}>Dodaj na listu zelja</button>
          </div>
        </div>
      </div>

      <div style={styles.relatedProducts}>
        <h2>Slicni proizvodi</h2>
        <div style={styles.relatedList}>
          {product.relatedProducts.map(related => (
            <div key={related.id} style={styles.relatedCard}>
              <img
                src={related.image}
                alt={related.name}
                style={styles.relatedImage}
              />
              <div>{related.name}</div>
              <div style={styles.relatedPrice}>{related.price}</div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

const styles = {
  container: {
    padding: '20px',
  },
  productInfo: {
    display: 'flex',
    marginBottom: '40px',
  },
  images: {
    flex: 1,
    marginRight: '20px',
  },
  mainImage: {
    width: '100%',
    borderRadius: '10px',
  },
  thumbnails: {
    display: 'flex',
    justifyContent: 'space-between',
    marginTop: '10px',
  },
  thumbnail: {
    width: '30%',
    cursor: 'pointer',
    borderRadius: '10px',
  },
  details: {
    flex: 2,
  },
  price: {
    fontSize: '1.5em',
    fontWeight: 'bold',
    margin: '20px 0',
  },
  buttons: {
    display: 'flex',
    gap: '10px',
    marginBottom: '20px',
  },
  addToCart: {
    padding: '10px 20px',
    backgroundColor: '#dff542',
    border: 'none',
    borderRadius: '999px',
    cursor: 'pointer',
  },
  addToWishlist: {
    padding: '10px 20px',
    backgroundColor: '#ddd',
    border: 'none',
    borderRadius: '999px',
    cursor: 'pointer',
  },
  backToCategories: {
    color: '#dff542',
    textDecoration: 'none',
    fontWeight: 'bold',
  },
  relatedProducts: {
    marginTop: '40px',
  },
  relatedList: {
    display: 'flex',
    justifyContent: 'center',
    gap: '30px',
  },
  relatedCard: {
    textAlign: 'center',
    padding: '10px',
    border: '1px solid #ddd',
    borderRadius: '10px',
    maxWidth: '200px',
  },
  relatedImage: {
    width: '100%',
    borderRadius: '10px',
  },
  relatedPrice: {
    fontWeight: 'bold',
    marginTop: '10px',
  },
};

export default ProductDetails;
