import React, { useState } from 'react';
import { NavLink } from 'react-router-dom';
import './ForgotPassword.css';

const ForgotPassword = () => {
  const [email, setEmail] = useState('');
  const [error, setError] = useState('');
  const [successMessage, setSuccessMessage] = useState('');
  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleChange = e => {
    setEmail(e.target.value);
    setError('');
  };

  const validateEmail = () => {
    if (!email) {
      return 'Email je obavezan';
    } else if (!/\S+@\S+\.\S+/.test(email)) {
      return 'Email nije validan';
    }
    return '';
  };

  const handleSubmit = e => {
    e.preventDefault();

    const validationError = validateEmail();
    if (validationError) {
      setError(validationError);
      return;
    }

    setIsSubmitting(true);

    // Simulate API call for password reset
    setTimeout(() => {
      setIsSubmitting(false);
      setSuccessMessage(
        'Link za resetovanje lozinke je poslat na vašu email adresu.',
      );
      setEmail('');
    }, 1500);
  };

  return (
    <div className="forgotPasswordContainer">
      <div className="forgotPasswordCard">
        <h2 className="forgotPasswordTitle">Zaboravljena lozinka</h2>

        {successMessage ? (
          <div className="successMessage">
            <p>{successMessage}</p>
            <NavLink to="/login" className="backToLoginLink">
              Nazad na prijavu
            </NavLink>
          </div>
        ) : (
          <>
            <p className="forgotPasswordDescription">
              Unesite vašu email adresu i poslaćemo vam link za resetovanje
              lozinke.
            </p>

            <form onSubmit={handleSubmit} className="forgotPasswordForm">
              <div className="formGroup">
                <label htmlFor="email" className="formLabel">
                  Email
                </label>
                <input
                  type="email"
                  id="email"
                  className={`formInput ${error ? 'inputError' : ''}`}
                  value={email}
                  onChange={handleChange}
                  placeholder="Unesite vaš email"
                />
                {error && <span className="errorMessage">{error}</span>}
              </div>

              <button
                type="submit"
                className="resetButton"
                disabled={isSubmitting}
              >
                {isSubmitting ? 'Slanje...' : 'Pošalji link za resetovanje'}
              </button>
            </form>

            <div className="loginPrompt">
              Setili ste se lozinke?{' '}
              <NavLink to="/login" className="loginLink">
                Nazad na prijavu
              </NavLink>
            </div>
          </>
        )}
      </div>
    </div>
  );
};

export default ForgotPassword;
