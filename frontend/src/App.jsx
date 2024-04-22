import './App.css';
import { Route, Routes, BrowserRouter as Router } from "react-router-dom";
import React, { useEffect, useState } from 'react';

import HomePage from './pages/HomePage'
import RegisterPage from './pages/RegisterPage';
import LoginPage from './pages/LoginPage';
import EmailPage from './pages/EmailPage';
import UserPage from './pages/UserPage';
import ProtectedRoute from './components/Routes/ProtectedRoute';

function App() {
  const [token, setToken] = useState(sessionStorage.getItem('tokens'))
  useEffect(()=>{
    setToken(sessionStorage.getItem('tokens'))
  },[sessionStorage.getItem('tokens')])
  return (
    <div className="App">
      <Router>
        <Routes>
          <Route path="/" element={<HomePage tokenRemover={setToken}/>} />
          <Route path="/LoginPage" element={<LoginPage setToken={setToken}/>} />
          <Route path="/RegisterPage" element={<ProtectedRoute token={token}><RegisterPage/></ProtectedRoute>} />
          <Route path="/EmailPage" element={<ProtectedRoute token={token}><EmailPage/></ProtectedRoute>} />
          <Route path="/UserPage" element={<ProtectedRoute token={token}><UserPage/></ProtectedRoute>} />
        </Routes>
      </Router>
    </div>
  );
}

export default App;
