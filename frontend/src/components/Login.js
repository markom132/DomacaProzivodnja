import React, { useState } from 'react';
import { NavLink } from 'react-router-dom';
import './Login.css';

const Login = () => {
  const [formData, setFormData] = useState({
    email: '',
    password: '',
  });
  const [errors, setErrors] = useState({});

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
    
    // Clear error when user starts typing
    if (errors[name]) {
      setErrors({
        ...errors,
        [name]: '',
      });
    }
  };

  const validateForm = () => {
    const newErrors = {};
    
    if (!formData.email) {
      newErrors.email = 'Email je obavezan';
    } else if (!/\S+@\S+\.\S+/.test(formData.email)) {
      newErrors.email = 'Email nije validan';
    }
    
    if (!formData.password) {
      newErrors.password = 'Lozinka je obavezna';
    } else if (formData.password.length < 6) {
      newErrors.password = 'Lozinka mora imati najmanje 6 karaktera';
    }
    
    return newErrors;
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    
    const newErrors = validateForm();
    if (Object.keys(newErrors).length > 0) {
      setErrors(newErrors);
      return;
    }
    
    // Here you would typically handle the login logic
    console.log('Login attempt with:', formData);
    
    // For now, just simulate a successful login
    alert('Uspešna prijava!');
  };

  return (
    <div className="loginContainer">
      <div className="loginCard">
        <h2 className="loginTitle">Prijava</h2>
        <form onSubmit={handleSubmit} className="loginForm">
          <div className="formGroup">
            <label htmlFor="email" className="formLabel">Email</label>
            <input
              type="email"
              id="email"
              name="email"
              className={`formInput ${errors.email ? 'inputError' : ''}`}
              value={formData.email}
              onChange={handleChange}
              placeholder="Unesite vaš email"
            />
            {errors.email && <span className="errorMessage">{errors.email}</span>}
          </div>
          
          <div className="formGroup">
            <label htmlFor="password" className="formLabel">Lozinka</label>
            <input
              type="password"
              id="password"
              name="password"
              className={`formInput ${errors.password ? 'inputError' : ''}`}
              value={formData.password}
              onChange={handleChange}
              placeholder="Unesite vašu lozinku"
            />
            {errors.password && <span className="errorMessage">{errors.password}</span>}
          </div>
          
          <div className="forgotPassword">
            <NavLink to="/forgot-password" className="forgotPasswordLink">
              Zaboravili ste lozinku?
            </NavLink>
          </div>
          
          <button type="submit" className="loginButton">
            Prijavi se
          </button>
        </form>
        
        <div className="registerPrompt">
          Nemate nalog?{' '}
          <NavLink to="/register" className="registerLink">
            Registrujte se
          </NavLink>
        </div>
      </div>
    </div>
  );
};

export default Login;