import React, { useState } from 'react';
import { NavLink, useLocation } from 'react-router-dom';
import CartPopup from './CartPopup';
import LogoSVGIcon from './LogoSVGIcon';
import './Header.css';

const Header = () => {
  const location = useLocation();

  const [isPopupVisible, setPopupVisible] = useState(false);

  return (
    <header className="header">
      <div className="logo">
        <LogoSVGIcon />
        <span>DomaciProizvodi</span>
      </div>
      <nav>
        <ul className="navList">
          <li className="navItemContainer">
            <NavLink
              to="/"
              className={({ isActive }) =>
                isActive ? 'navItemActive' : 'navItem'
              }
            >
              Pocetna
            </NavLink>
          </li>
          <li className="navItemContainer">
            <NavLink
              to="/category"
              className={({ isActive }) =>
                isActive ? 'navItemActive' : 'navItem'
              }
            >
              Kategorije
            </NavLink>
          </li>
          <li className="navItemContainer">
            <NavLink
              to="/wishlist"
              className={({ isActive }) =>
                isActive ? 'navItemActive' : 'navItem'
              }
            >
              Sačuvani prozivodi
            </NavLink>
          </li>
          <li
            className="navItemContainer"
            onMouseEnter={() => setPopupVisible(true)}
            onMouseLeave={() => setPopupVisible(false)}
          >
            <div className="cartWrapper">
              <NavLink
                to="/cart"
                className={({ isActive }) =>
                  isActive ? 'navItemActive' : 'navItem'
                }
              >
                Korpa
              </NavLink>
              {isPopupVisible && location.pathname !== '/cart' && (
                <CartPopup setPopupVisible={setPopupVisible} />
              )}
            </div>
          </li>
        </ul>
      </nav>
      <div className="searchBar">
        <input type="text" placeholder="Search..." className="searchInput" />
        <button className="searchButton">Search</button>
      </div>
    </header>
  );
};

export default Header;
