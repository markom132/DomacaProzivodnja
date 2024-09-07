// Footer.jsx
import React from 'react';
import './Footer.css';

const Footer = () => {
  return (
    <footer className='footer'>
      <div className='footerContent'>
        <div className='footerSection'>
          <h4>O nama</h4>
          <p>Information about your company or website.</p>
        </div>
        <div className='footerSection'>
          <h4>Contact</h4>
          <p>Email: info@domaciproizvodi.com</p>
          <p>Phone: +381 11 1234567</p>
        </div>
        <div className='footerSection'>
          <h4>Follow Us</h4>
          <p>Social media links</p>
        </div>
      </div>
      <div className='footerBottom'>
        &copy; 2023 DomaciProizvodi. All rights reserved.
      </div>
    </footer>
  );
};


export default Footer;
