import React, { useState, useEffect } from 'react';
import promoImage from '../assets/homePhoto.jpg';
import image1 from '../assets/broccoli.jpg';
import image2 from '../assets/cucumber.jpg';
import image3 from '../assets/med.jpg';
import image4 from '../assets/milk.jpg';
import { width } from '@fortawesome/free-solid-svg-icons/fa0';

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
    <section
      style={styles.hero}>
      {images.map((image, index) => (
        <div key={index}
          style={{
            ...styles.imageContainer,
            opacity: currentIndex === index ? 1 : 0,
            zIndex: currentIndex === index ? 1 : 0,
            backgroundImage: `linear-gradient(rgba(0, 0, 0, 0.5), rgba(0, 0, 0, 0.5)), url(${image})`,
          }}
        />
      ))}
      <div style={styles.textContainer}>
        <div style={styles.promoText}>Specijalna ponuda domacih proizvoda</div>
        <button
          style={{
            ...styles.ctaButton,
            backgroundColor: isHovered
              ? styles.ctaButtonHover.backgroundColor
              : styles.ctaButton.backgroundColor,
          }}
          onMouseOver={() => setIsHovered(true)}
          onMouseOut={() => setIsHovered(false)}
        >
          {' '}
          Istraži ponudu
        </button>
      </div>
    </section>
  );
};

const styles = {
  hero: {
    position: 'relative',
    height: '400px',
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    padding: '0 20px',
    textAlign: 'center',
    backgroundSize: 'cover',
    backgroundPosition: 'center',
    color: '#fff',
    borderRadius: '20px',
    marginBottom: '20px',
    overflow: 'hidden',
  },
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
  textContainer: {
    position: 'relative',
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
  },
  promoText: {
    fontSize: '2em',
    fontWeight: 'bold',
    backgroundColor: 'rgba(0, 0, 0, 0.5)',
    padding: '20px',
    borderRadius: '10px',
    zIndex: 2,
    marginBottom: '20px',
    animation: 'slideIn 2s ease-out',
  },
  ctaButton: {
    backgroundColor: '#f7c648',
    color: '#000',
    border: 'none',
    padding: '10px 20px',
    borderRadius: '5px',
    fontSize: '1em',
    cursor: 'pointer',
    transition: 'background-color 0.3s ease',
    zIndex: 1,
    animation: 'slideIn 2s ease-out'
  },
  ctaButtonHover: {
    backgroundColor: '#d1a51d',
  },
};

const styleSheet = document.styleSheets[0];
styleSheet.insertRule(
  `
  @keyframes slideIn {
    from { opacity: 0; transform: translateY(50px); }
    to {opacity: 1; transform: translateY(0); }
    }
    `,
  styleSheet.cssRules.length,
);

export default HeroSection;
