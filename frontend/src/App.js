import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Header from './components/Header';
import HeroSection from './components/HeroSection';
import FeaturedProducts from './components/FeaturedProducts';
import Footer from './components/Footer';
import ProductDetails from './components/ProductDetails';
import CategoryPage from './components/CategoryPage';
import CartPage from './components/CartPage';  // Stranica za korpu
import WishlistPage from './components/WishlistPage';  // Stranica za wishlist


function App() {
  return (
    <Router>
      <Header />
      <Routes>
        <Route path="/" element={<><HeroSection /><FeaturedProducts /></>} />
        <Route path="/product/:id" element={<ProductDetails />} />
        <Route path="/category" element={<CategoryPage/>} />
        <Route path="/cart" element={<CartPage/>} /> 
        <Route path="/wishlist" element={<WishlistPage/>} />
      </Routes>
      <Footer />
    </Router>
  );
}

export default App;
