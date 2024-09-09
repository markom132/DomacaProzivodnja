import React from 'react';
import './NewsletterSignup.css';

const NewsletterSignup = () => {
  return (
    <section className='newsletterSection'>
      <h2 className='sectionTitle'>Ostanite u toku!</h2>
      <p className='description'>
        Prijavite se na naš  newsletter da bi dobili najnovije izmene i specijalne ponude.
      </p>
      <form className='form'>
        <input
          type="email"
          placeholder="Vaša email adresa"
          className='input'
        />
        <button className='buttonNS'>Zapratite nas</button>
      </form>
    </section>
  );
};

export default NewsletterSignup;
