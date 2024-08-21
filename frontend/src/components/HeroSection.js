import React from 'react';
import promoImage from '../assets/homePhoto.jpg'; // Putanja do slike

const HeroSection = () => {
  return (
    <section style={{ ...styles.hero, backgroundImage: `url(${promoImage})` }}>
      <div style={styles.promoText}>Specijalna ponuda domacih proizvoda!</div>
    </section>
  );
};

const styles = {
  hero: {
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
    overflow: 'hidden'
  },
  promoText: {
    fontSize: '2em',
    fontWeight: 'bold',
    backgroundColor: 'rgba(0, 0, 0, 0.5)', // Prozirna pozadina za tekst
    padding: '20px',
    borderRadius: '10px',
    display: 'inline-block',
  }
};

export default HeroSection;
