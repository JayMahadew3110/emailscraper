
import React, { useEffect, useState } from "react";
import '/src/pages/Style.css';
import { useNavigate } from "react-router-dom";
import AuthManager from '../services/AuthService';
import { ToastContainer, toast } from 'react-toastify';
import { useFormik } from "formik";
import * as Yup from 'yup';
import { MdOutlineArrowForwardIos } from 'react-icons/md'
import { VscEyeClosed } from "react-icons/vsc"
import { HiOutlineEye } from "react-icons/hi"

const LoginPage = ({setToken}) =>{
    const navigate = useNavigate();
    useEffect(() => {
      const userId = sessionStorage.getItem('userId');
      console.log('Retrieved userId:', userId);
      if (userId) {
          navigate('/')
      }
  }, []);

    const [showPassword, setShowPassword] = useState(false);
    const togglePasswordVisibility = () => {
      setShowPassword(!showPassword);
    };

    const SignupSchema = Yup.object().shape({
        password: Yup.string()
          .min(1, 'Too Short!')
          .max(30, 'Too Long!')
          .required('Password is required'),
        email: Yup.string()
          .email('Invalid email')
          .required('Email is required')
      });

    const formik = useFormik({
        initialValues: {
          email: '',
          password: '',
        },
        validationSchema: SignupSchema,
        onSubmit: async values => {
          let response = await AuthManager.Login(values);
          if (response.data.success) {
            sessionStorage.setItem('tokens', response.data.result.token)
            setToken(response.data.result.token)
            sessionStorage.setItem('userId', response.data.result.id)
            navigate("/")
            }
          else {
            console.log(response.data.message);
            alert("email or password wrong");
            }}});
    return (
            <div className="loginform">
                <form onSubmit={formik.handleSubmit}>
                <div className="loginformtitle">Login</div>
                <label className="loginformtext" htmlFor="email">Email: </label><br/>
                <input className="loginforminputfieldemail" type="text" id="email" name="email" value={formik.values.email} onBlur={() => {formik.setFieldTouched("email")}} onChange={formik.handleChange} /><br/>
                {formik.errors.email && formik.touched.email ? <label className='loginformerrortext'>{formik.errors.email}</label> : null}<br/>
                <label className="loginformtext" htmlFor="email">Password: </label><br/>
                <input type={showPassword ? 'text' : 'password'} id="password" name="password" value={formik.values.password} onBlur={() => {formik.setFieldTouched("password")}} onChange={formik.handleChange} className='passwordInput loginforminputfieldpassword'/>
                <span className='showPasswordIcon' onClick={togglePasswordVisibility} style={{ cursor: 'pointer' }}>
                {showPassword ? <VscEyeClosed fontSize={25} /> : <HiOutlineEye fontSize={25} />}<br/>
                </span>
                {formik.errors.password && formik.touched.password ? <label className='loginformerrortext'>{formik.errors.password}</label> : null}<br></br> <br></br>
                <button className='smallbutton' type="submit">Login</button>
                <div className="loginformregisterlink" onClick={() => navigate("/")}>Don't have an account yet? Ask your system administrator to create one</div>
                <ToastContainer />
                </form>
            </div>
    )
}

export default LoginPage;