import React from 'react';
import './NewsletterSignup.css';

const NewsletterSignup = () => {
  return (
    <section className='newsletterSection'>
      <h2 className='sectionTitle'>Stay Updated!</h2>
      <p className='description'>
        Sign up for our newsletter to get the latest updates and special offers.
      </p>
      <form className='form'>
        <input
          type="email"
          placeholder="Your email address"
          className='input'
        />
        <button className='button'>Subscribe</button>
      </form>
    </section>
  );
};

export default NewsletterSignup;
