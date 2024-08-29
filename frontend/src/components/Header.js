import React from 'react';
import { NavLink, useLocation } from 'react-router-dom';

const Header = () => {
  const location = useLocation(); // Prikupljanje informacija o trenutnoj ruti

  return (
    <header style={styles.header}>
      <div style={styles.logo}>🍏 DomaciProizvodi</div>
      <nav>
        <ul style={styles.navList}>
          <li style={styles.navItemContainer}>
            <NavLink
              exact
              to="/"
              style={({ isActive }) => ({
                ...styles.navItem,
                color: isActive ? '#dff542' : 'black',
                fontWeight: isActive ? 'bold' : 'normal',
                borderBottom: isActive ? '2px solid #dff542' : 'none',
              })}
            >
              Home
              <span
                style={{
                  ...styles.line,
                  width: location.pathname === '/' ? '100%' : '0%',
                }}
              ></span>
            </NavLink>
          </li>
          <li style={styles.navItemContainer}>
            <NavLink
              to="/category"
              style={({ isActive }) => ({
                ...styles.navItem,
                color: isActive ? '#dff542' : 'black',
                fontWeight: isActive ? 'bold' : 'normal',
                borderBottom: isActive ? '2px solid #dff542' : 'none',
              })}
            >
              Categories
              <span
                style={{
                  ...styles.line,
                  width: location.pathname === '/category' ? '100%' : '0%',
                }}
              ></span>
            </NavLink>
          </li>
          <li style={styles.navItemContainer}>
            <NavLink
              to="/wishlist"
              style={({ isActive }) => ({
                ...styles.navItem,
                color: isActive ? '#dff542' : 'black',
                fontWeight: isActive ? 'bold' : 'normal',
                borderBottom: isActive ? '2px solid #dff542' : 'none',
              })}
            >
              Wishlist
            </NavLink>
          </li>
          <li style={styles.navItemContainer}>
            <NavLink
              to="/cart"
              style={({ isActive }) => ({
                ...styles.navItem,
                color: isActive ? '#dff542' : 'black',
                fontWeight: isActive ? 'bold' : 'normal',
                borderBottom: isActive ? '2px solid #dff542' : 'none',
              })}
            >
              Shopping Cart
            </NavLink>
          </li>
        </ul>
      </nav>
      <div style={styles.searchBar}>
        <input type="text" placeholder="Search..." style={styles.searchInput} />
        <button style={styles.searchButton}>Search</button>
      </div>
    </header>
  );
};

const styles = {
  header: {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    padding: '10px 20px',
    backgroundColor: '#f8f8f8',
  },
  logo: { fontSize: '1.5em', fontWeight: 'bold' },
  navList: { display: 'flex', listStyle: 'none', gap: '20px' },
  searchBar: { display: 'flex', alignItems: 'center' },
  searchInput: {
    padding: '10px 20px',
    borderRadius: '999px',
    border: '1px solid #ddd',
    outline: 'none',
  },
  searchButton: {
    padding: '10px 20px',
    marginLeft: '10px',
    backgroundColor: '#dff542',
    border: 'none',
    borderRadius: '999px',
    cursor: 'pointer',
  },
  navItemContainer: {
    position: 'relative',
    display: 'inline-block',
  },
  navItem: {
    textDecoration: 'none',
    color: 'black',
    padding: '5px 0',
    transition: 'color 0.3s ease',
    position: 'relative',
  },
  activeNavItem: {
    color: '#dff542',
    fontWeight: 'bold',
  },
  line: {
    position: 'absolute',
    bottom: 0,
    left: 0,
    height: '2px',
    backgroundColor: '#dff542',
    transition: 'width 0.3s ease',
  },
  lineActive: {
    width: '100%',
  },
};

export default Header;
