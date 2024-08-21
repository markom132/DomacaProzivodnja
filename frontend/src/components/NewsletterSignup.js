import React from 'react';

const NewsletterSignup = () => {
  return (
    <section style={styles.newsletterSection}>
      <h2 style={styles.sectionTitle}>Stay Updated!</h2>
      <p style={styles.description}>Sign up for our newsletter to get the latest updates and special offers.</p>
      <form style={styles.form}>
        <input type="email" placeholder="Your email address" style={styles.input}/>
        <button style={styles.button}>Subscribe</button>
      </form>
    </section>
  );
};

const styles = {
  newsletterSection: {
    padding: '20px',
    backgroundColor: '#f9f9f9',
    textAlign: 'center',
  },
  sectionTitle: {
    marginBottom: '10px',
    fontSize: '1.5em',
  },
  description: {
    marginBottom: '20px',
    fontSize: '1em',
    color: '#555',
  },
  form: {
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    gap: '10px',
  },
  input: {
    padding: '10px',
    borderRadius: '5px',
    border: '1px solid #ddd',
    outline: 'none',
    width: '250px',
  },
  button: {
    padding: '10px 20px',
    borderRadius: '5px',
    border: 'none',
    backgroundColor: '#dff542',
    cursor: 'pointer',
  },
};

export default NewsletterSignup;
