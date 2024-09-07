import React, { useState, useEffect, useRef } from 'react';
import { Link } from 'react-router-dom';
import ProductCard from './ProductCard';
import honeyImage from '../assets/med.jpg';
import cucumber from '../assets/cucumber.jpg';
import milk from '../assets/milk.jpg';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import Slider from 'react-slick';
import PropTypes from 'prop-types';

import './FeaturedProducts.css';

const NextArrow = ({ onClick }) => {
  return (
    <div className='nextArrow' onClick={onClick}>
      <span className='arrowIcon'>&rarr;</span>
    </div>
  );
};

const PrevArrow = ({ onClick }) => {
  return (
    <div className='prevArrow' onClick={onClick}>
      <span className='arrowIcon'>&larr;</span>
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
  const [isVisible, setIsVisible] = useState(false);
  const buttonRef = useRef(null);

  useEffect(() => {
    const observer = new IntersectionObserver(
      entries => {
        entries.forEach(entry => {
          if (entry.isIntersecting) {
            setIsVisible(true);
          }
        });
      },
      { threshold: 0.1 },
    );

    if (buttonRef.current) {
      observer.observe(buttonRef.current);
    }

    return () => {
      if (buttonRef.current) {
        observer.unobserve(buttonRef.current);
      }
    };
  }, []);

  const handleAnimationEnd = () => {
    setIsVisible(false);
  };

  const settings = {
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 5,
    slidesToScroll: 3,
    autoplay: true,
    autoplaySpeed: 3000,
    nextArrow: <NextArrow onClick={() => {}} />,
    prevArrow: <PrevArrow onClick={() => {}} />,
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
    <section className='featuredSection'>
      <h2>Popularni proizvodi</h2>
      <Slider {...settings}>
        {products.map(product => (
          <Link
            to={`/product/${product.id}`}
            key={product.id}
            className='productLink'
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
      <div className='ctaContainer'>
        <Link to="/products">
          <button
            ref={buttonRef}
            className={`ctaButton ${isVisible ? 'bounce' : ''}`}
            onAnimationEnd={handleAnimationEnd}
          >
            {' '}
            Pogledajte više proizvoda
          </button>
        </Link>
      </div>
    </section>
  );
};

const styles = {

};

export default FeaturedProducts;
