import React, { useState, useRef } from 'react';
import honeyImage from '../assets/med.jpg';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import Slider from 'react-slick';
import PropTypes from 'prop-types';

import './ProductDetails.css';

const ProductDetails = () => {
  const NextArrow = ({ onClick }) => {
    return (
      <div className="nextArrow" onClick={onClick}>
        <svg
          xmlns="http://www.w3.org/2000/svg"
          style={{ width: '50px', height: '50px' }}
          fill="currentColor"
          class="bi bi-arrow-right-circle"
          viewBox="0 0 16 16"
        >
          <path
            fill-rule="evenodd"
            d="M1 8a7 7 0 1 0 14 0A7 7 0 0 0 1 8m15 0A8 8 0 1 1 0 8a8 8 0 0 1 16 0M4.5 7.5a.5.5 0 0 0 0 1h5.793l-2.147 2.146a.5.5 0 0 0 .708.708l3-3a.5.5 0 0 0 0-.708l-3-3a.5.5 0 1 0-.708.708L10.293 7.5z"
          />
        </svg>
      </div>
    );
  };

  const PrevArrow = ({ onClick }) => {
    return (
      <div className="prevArrow" onClick={onClick}>
        <svg
          xmlns="http://www.w3.org/2000/svg"
          style={{ width: '50px', height: '50px' }}
          fill="currentColor"
          class="bi bi-arrow-left-circle"
          viewBox="0 0 16 16"
        >
          <path
            fill-rule="evenodd"
            d="M1 8a7 7 0 1 0 14 0A7 7 0 0 0 1 8m15 0A8 8 0 1 1 0 8a8 8 0 0 1 16 0m-4.5-.5a.5.5 0 0 1 0 1H5.707l2.147 2.146a.5.5 0 0 1-.708.708l-3-3a.5.5 0 0 1 0-.708l3-3a.5.5 0 1 1 .708.708L5.707 7.5z"
          />
        </svg>
      </div>
    );
  };

  NextArrow.propTypes = {
    onClick: PropTypes.func.isRequired,
  };

  PrevArrow.propTypes = {
    onClick: PropTypes.func.isRequired,
  };
  const [dragging, setDragging] = useState(false);
  const [activeSlide, setActiveSlide] = useState(0); //eslint-disable-line no-unused-vars

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
      { id: 1, name: 'Related Product 1', price: '$49.99', image: honeyImage },
      { id: 2, name: 'Related Product 2', price: '$59.99', image: honeyImage },
      { id: 3, name: 'Related Product 3', price: '$89.99', image: honeyImage },
      { id: 4, name: 'Related Product 4', price: '$79.99', image: honeyImage },
      { id: 1, name: 'Related Product 1', price: '$49.99', image: honeyImage },
      { id: 2, name: 'Related Product 2', price: '$59.99', image: honeyImage },
      { id: 3, name: 'Related Product 3', price: '$89.99', image: honeyImage },
      { id: 4, name: 'Related Product 4', price: '$79.99', image: honeyImage },
      { id: 1, name: 'Related Product 1', price: '$49.99', image: honeyImage },
      { id: 2, name: 'Related Product 2', price: '$59.99', image: honeyImage },
      { id: 3, name: 'Related Product 3', price: '$89.99', image: honeyImage },
      { id: 4, name: 'Related Product 4', price: '$79.99', image: honeyImage },
      { id: 1, name: 'Related Product 1', price: '$49.99', image: honeyImage },
      { id: 2, name: 'Related Product 2', price: '$59.99', image: honeyImage },
      { id: 3, name: 'Related Product 3', price: '$89.99', image: honeyImage },
      { id: 4, name: 'Related Product 4', price: '$79.99', image: honeyImage },
      { id: 1, name: 'Related Product 1', price: '$49.99', image: honeyImage },
      { id: 2, name: 'Related Product 2', price: '$59.99', image: honeyImage },
      { id: 3, name: 'Related Product 3', price: '$89.99', image: honeyImage },
      { id: 4, name: 'Related Product 4', price: '$79.99', image: honeyImage },
    ],
  };

  const settings = {
    dots: false,
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
          dots: false,
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
    centerMode: false,
    variableWidth: true,
  };
  const handleClick = e => {
    if (dragging) {
      e.preventDefault();
    }
  };

  return (
    <div className="container">
      <div className="productDetailsInfo">
        <div className="images">
          <img
            src={product.mainImage}
            alt={product.name}
            className="mainImage"
          />
          <div className="thumbnails">
            {product.thumbnails.map((thumb, index) => (
              <img
                key={index}
                src={thumb}
                alt={`Thumbnail ${index + 1}`}
                className="thumbnail"
              />
            ))}
          </div>
        </div>
        <div className="details">
          <h1>{product.name}</h1>
          <p>{product.description}</p>
          <p className="productDetailsPrice">{product.price}</p>
          <div className="buttons">
            <button className="addToCart">Dodaj u korpu</button>
            <button className="addToWishlist">Sačuvaj proizvod</button>
          </div>
        </div>
      </div>

      <div className="relatedProducts">
        <h2>Slicni proizvodi</h2>
        <Slider {...settings} className="relatedList">
          {product.relatedProducts.map(related => (
            <div key={related.id} className="relatedCard">
              <img
                src={related.image}
                alt={related.name}
                className="relatedImage"
              />
              <h4>{related.name}</h4>
              <div className="relatedPrice">{related.price}</div>
            </div>
          ))}
        </Slider>
      </div>
    </div>
  );
};

export default ProductDetails;
