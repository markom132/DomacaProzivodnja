import React, { useState, useEffect } from 'react';
import promoImage from '../assets/homePhoto.jpg';
import image1 from '../assets/broccoli.jpg';
import image2 from '../assets/cucumber.jpg';
import image3 from '../assets/med.jpg';
import image4 from '../assets/milk.jpg';

const HeroSection = () => {
  const images = [promoImage, image1, image2, image3, image4];
  const [currentIndex, setCurrentIndex] = useState(0);

  useEffect(() => {
    const intervalId = setInterval(() => {
      setCurrentIndex(prevIndex => (prevIndex + 1) % images.length);
    }, 5000);

    return () => clearInterval(intervalId);
  }, [images.length]);

  return (
    <section
      style={{
        ...styles.hero,
        backgroundImage: `linear-gradient(rgba(0, 0, 0, 0.5), rgba(0, 0, 0, 0.5)), url(${images[currentIndex]})`,
      }}
    >
      <div style={styles.promoText}>Specijalna ponuda domacih proizvoda!</div>
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
    transition: 'background-image 1s ease-in-out',
  },
  promoText: {
    position: 'relative',
    fontSize: '2em',
    fontWeight: 'bold',
    backgroundColor: 'rgba(0, 0, 0, 0.5)',
    padding: '20px',
    borderRadius: '10px',
    zIndex: 2, // Ensure the text is above the overlay
  },
};

export default HeroSection;
