// Footer.jsx
import React from 'react';

const Footer = () => {
  return (
    <footer style={styles.footer}>
      <div style={styles.footerContent}>
        <div style={styles.footerSection}>
          <h4>O nama</h4>
          <p>Information about your company or website.</p>
        </div>
        <div style={styles.footerSection}>
          <h4>Contact</h4>
          <p>Email: info@domaciproizvodi.com</p>
          <p>Phone: +381 11 1234567</p>
        </div>
        <div style={styles.footerSection}>
          <h4>Follow Us</h4>
          <p>Social media links</p>
        </div>
      </div>
      <div style={styles.footerBottom}>
        &copy; 2023 DomaciProizvodi. All rights reserved.
      </div>
    </footer>
  );
};

const styles = {
  footer: {
    backgroundColor: '#f4f4f4',
    padding: '20px',
    marginTop: '40px',
  },
  footerContent: {
    display: 'flex',
    justifyContent: 'space-around',
    textAlign: 'left',
  },
  footerSection: {
    flex: 1,
    margin: '0 20px',
  },
  footerBottom: {
    textAlign: 'center',
    marginTop: '20px',
    borderTop: '1px solid #ddd',
    paddingTop: '10px',
    color: '#666',
  }
};

export default Footer;
