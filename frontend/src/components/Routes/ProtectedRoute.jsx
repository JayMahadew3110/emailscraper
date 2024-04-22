import React from 'react'
import { Navigate } from 'react-router-dom'

const ProtectedRoute = ({token, children }) => {
    if (!token) {
      return <Navigate to='/LoginPage' replace />;
    }
    return children;
  };

export default ProtectedRoute