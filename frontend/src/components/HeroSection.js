import React, { useState, useEffect } from 'react';
import promoImage from '../assets/homePhoto.jpg';
import image1 from '../assets/broccoli.jpg';
import image2 from '../assets/cucumber.jpg';
import image3 from '../assets/med.jpg';
import image4 from '../assets/milk.jpg';
import './HeroSection.css';

const HeroSection = () => {
  const images = [promoImage, image1, image2, image3, image4];
  const [currentIndex, setCurrentIndex] = useState(0);
  const [isHovered, setIsHovered] = useState(false);

  useEffect(() => {
    const intervalId = setInterval(() => {
      setCurrentIndex(prevIndex => (prevIndex + 1) % images.length);
    }, 5000);

    return () => clearInterval(intervalId);
  }, [images.length]);

  return (
    <section className='hero'>
      {images.map((image, index) => (
        <div
          key={index}
          className={`imageContainerHeroPage ${currentIndex === index ? 'visible' : 'hidden'}`}
          style={{
            backgroundImage: `linear-gradient(rgba(0, 0, 0, 0.6), rgba(0, 0, 0, 0.6)), url(${image})`,
          }}
        />
      ))}
      <div className='textContainer'>
        <div className='promoText'>Specijalna ponuda domacih proizvoda</div>
        <button
          className='ctaButtonHeroS'
          onMouseOver={() => setIsHovered(true)}
          onMouseOut={() => setIsHovered(false)}
        >
          {' '}
          Istraži ponudu
        </button>
      </div>
    </section >
  );
};

const styles = {
  imageContainer: {
    position: 'absolute',
    top: 0,
    left: 0,
    width: '100%',
    height: '100%',
    backgroundSize: 'cover',
    backgroundPosition: 'center',
    transition: 'opacity 1s ease-in-out',
  },
};



export default HeroSection;
