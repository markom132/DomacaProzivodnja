import React, { useState } from 'react';
import { NavLink, useLocation } from 'react-router-dom';
import CartPopup from './CartPopup';
import LogoSVGIcon from './LogoSVGIcon';
import './Header.css';

const Header = () => {
  const location = useLocation();

  const [isPopupVisible, setPopupVisible] = useState(false);
  const isLoggedIn = false;

  return (
    <header className="header">
      <div className="logo">
        <LogoSVGIcon />
        <span>DomaciProizvodi</span>
      </div>
      <div className="navContainer">
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
      </div>
      <div className="rightSection">
        <div className="searchBar">
          <input type="text" placeholder="Search..." className="searchInput" />
          <button className="searchButton">Search</button>
        </div>

        {isLoggedIn ? (
          <NavLink to="/profile" className="profileIcon" title="Profil">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              x="0px"
              y="0px"
              width="50"
              height="50"
              viewBox="0 0 48 48"
            >
              <path d="M24 4A10 10 0 1024 24 10 10 0 1024 4zM36.021 28H11.979C9.785 28 8 29.785 8 31.979V33.5c0 3.312 1.885 6.176 5.307 8.063C16.154 43.135 19.952 44 24 44c7.706 0 16-3.286 16-10.5v-1.521C40 29.785 38.215 28 36.021 28z"></path>
            </svg>
          </NavLink>
        ) : (
          <div>
            <NavLink to="/login" className="authButtonText">
              Prijavi se
            </NavLink>
          </div>
        )}
      </div>
    </header>
  );
};

export default Header;
