import React, {useState} from 'react';
import { Link } from 'react-router-dom';
import ProductCard from './ProductCard';
import honeyImage from '../assets/med.jpg'; // Putanja do slike
import cucumber from '../assets/cucumber.jpg';
import milk from '../assets/milk.jpg';
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import Slider from 'react-slick';


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
  const [activeSlide, setActiveSlide] = useState(0);

  const settings = {
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 5,
    slidesToScroll: 3,
    beforeChange: (current, next) => 
      {
        setActiveSlide(next)
        setDragging(true)
      },
    afterChange: () => setDragging(false),
    responsive: [
      {
        breakpoint: 1024,
        settings: {
          slidesToShow: 3,
          slidesToScroll: 3,
          infinite: true,
          dots: true
        }
      },
      {
        breakpoint: 600,
        settings: {
          slidesToShow: 2,
          slidesToScroll: 2,
          initialSlide: 2
        }
      },
      {
        breakpoint: 480,
        settings: {
          slidesToShow: 1,
          slidesToScroll: 1
        }
      }
    ]
  };

  const handleClick = (e) => {
    if (dragging) {
      e.preventDefault();
    }
  };

  return (
    <section style={styles.featuredSection}>
      <h2>Featured Products</h2>
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
};

export default FeaturedProducts;
