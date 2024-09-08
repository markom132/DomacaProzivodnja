import React from 'react';
import WishlistSVGIcon from './WishlistSVGIcon';
import './WishlistPage.css';

const WishlistPage = () => {
  return (
    <div className='pageContainer'>
      <h2 className='pageTitle'>Sačuvani proizvodi</h2>
      <div class="cart-container">
        <WishlistSVGIcon />
      </div>
      <p>Izgleda da nemate sačuvane proizvode. 
      <br />Počnite da istražujete i sačuvajte svoje omiljene artikle!</p>
      <button>Istraži ponudu</button>
    </div>
  );
};

export default WishlistPage;
