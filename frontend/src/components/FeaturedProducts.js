import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import ProductCard from './ProductCard';
import honeyImage from '../assets/med.jpg';
import cucumber from '../assets/cucumber.jpg';
import milk from '../assets/milk.jpg';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import Slider from 'react-slick';
import PropTypes from 'prop-types';

const NextArrow = ({ onClick }) => {
  return (
    <div style={styles.nextArrow} onClick={onClick}>
      <span style={styles.arrowIcon}>&rarr;</span>
    </div>
  );
};

const PrevArrow = ({ onClick }) => {
  return (
    <div style={styles.prevArrow} onClick={onClick}>
      <span style={styles.arrowIcon}>&larr;</span>
    </div>
  );
};

NextArrow.propTypes = {
  onClick: PropTypes.func.isRequired,
};

PrevArrow.propTypes = {
  onClick: PropTypes.func.isRequired,
};

const FeaturedProducts = () => {
  const products = [
    { id: 1, image: honeyImage, name: 'Organski med', price: '$7.99' },
    { id: 2, image: cucumber, name: 'Krastavci', price: '$24.99' },
    { id: 3, image: milk, name: 'Mleko', price: '$8.99' },
    { id: 1, image: honeyImage, name: 'Organski med', price: '$7.99' },
    { id: 1, image: honeyImage, name: 'Organski med', price: '$7.99' },
    { id: 1, image: honeyImage, name: 'Organski med', price: '$7.99' },
    { id: 1, image: honeyImage, name: 'Organski med', price: '$7.99' },
    { id: 1, image: honeyImage, name: 'Organski med', price: '$7.99' },
    { id: 1, image: honeyImage, name: 'Organski med', price: '$7.99' },
    { id: 1, image: honeyImage, name: 'Organski med', price: '$7.99' },
    { id: 1, image: honeyImage, name: 'Organski med', price: '$7.99' },
    { id: 1, image: honeyImage, name: 'Organski med', price: '$7.99' },
    { id: 1, image: honeyImage, name: 'Organski med', price: '$7.99' },
  ];

  const [dragging, setDragging] = useState(false);
  const [activeSlide, setActiveSlide] = useState(0); //eslint-disable-line no-unused-vars

  const settings = {
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 5,
    slidesToScroll: 3,
    nextArrow: <NextArrow  onClick={() => {}} />,
    prevArrow: <PrevArrow  onClick={() => {}} />,
    beforeChange: (current, next) => {
      setActiveSlide(next);
      setDragging(true);
    },
    afterChange: () => setDragging(false),
    responsive: [
      {
        breakpoint: 1024,
        settings: {
          slidesToShow: 3,
          slidesToScroll: 3,
          infinite: true,
          dots: true,
        },
      },
      {
        breakpoint: 600,
        settings: {
          slidesToShow: 2,
          slidesToScroll: 2,
          initialSlide: 2,
        },
      },
      {
        breakpoint: 480,
        settings: {
          slidesToShow: 1,
          slidesToScroll: 1,
        },
      },
    ],
  };

  const handleClick = e => {
    if (dragging) {
      e.preventDefault();
    }
  };

  return (
    <section style={styles.featuredSection}>
      <h2>Popularni proizvodi</h2>
      <Slider {...settings}>
        {products.map(product => (
          <Link
            to={`/product/${product.id}`}
            key={product.id}
            style={styles.productLink}
            onClick={handleClick}
          >
            <ProductCard
              image={product.image}
              name={product.name}
              price={product.price}
            />
          </Link>
        ))}
      </Slider>
    </section>
  );
};

const styles = {
  featuredSection: {
    padding: '20px',
    textAlign: 'center',
  },
  productLink: {
    textDecoration: 'none',
    color: 'inherit',
    flex: '1 1 250px',
    maxWidth: '300px',
  },
  prevArrow: {
    position: 'absolute',
    top: '50%',
    left: '10px',
    transform: 'translateY(-50%)',
    backgroundColor: '#fff',
    border: '2px solid #ddd',
    borderRadius: '50%',
    width: '40px',
    height: '40px',
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    cursor: 'pointer',
    zIndex: 2,
    boxShadow: '0 4px 8px rgba(0, 0, 0, 0.2)',
  },
  nextArrow: {
    position: 'absolute',
    top: '50%',
    right: '10px',
    transform: 'translateY(-50%)',
    backgroundColor: '#fff',
    border: '2px solid #ddd',
    borderRadius: '50%',
    width: '40px',
    height: '40px',
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    cursor: 'pointer',
    zIndex: 2,
    boxShadow: '0 4px 8px rgba(0, 0, 0, 0.2)',
  },
  arrowIcon: {
    fontSize: '18px',
    color: '#333',
  },
};

export default FeaturedProducts;
