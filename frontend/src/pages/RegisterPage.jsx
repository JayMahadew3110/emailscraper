import React, { useEffect, useState } from "react";
import '/src/pages/Style.css';
import { useNavigate } from "react-router-dom";
import AuthManager from '../services/AuthService';
import { ToastContainer, toast } from 'react-toastify';
import { useFormik } from "formik";
import * as Yup from 'yup';
import { VscEyeClosed } from "react-icons/vsc"
import { HiOutlineEye } from "react-icons/hi"

function RegisterPage() {
    const Navigate = useNavigate();
    const [token, setToken] = useState(sessionStorage.getItem('tokens'))
    const [showPassword, setShowPassword] = useState(false);
    const togglePasswordVisibility = () => {
      setShowPassword(!showPassword);
    };

    useEffect(()=> {
        setToken(sessionStorage.getItem('tokens'));
    }, [])

    const SignupSchema = Yup.object().shape({
        password: Yup.string()
          .min(3, 'Too Short!')
          .max(30, 'Too Long!')
          .required('Password is required'),
        repeatpassword: Yup.string()
          .min(3, 'Too Short!')
          .max(30, 'Too Long!')
          .required('Please repeat your password')
          .oneOf([Yup.ref('password'), null], "Passwords don't match"),
        username: Yup.string()
            .min(3, 'Too Short!')
            .max(30, 'Too Long!')
            .required('username is required'),
        email: Yup.string()
          .email('Invalid email')
          .required('Email is required')
      });

    const formik = useFormik({
        initialValues: {
          email: '',
          password: '',
          repeatpassword: '',
          username: '',
        },
        validationSchema: SignupSchema,
        onSubmit: async values => {
          let response = await AuthManager.Register(values);
          if (response.data.success) {
            Navigate("/UserPage")
            console.log("SUCCESS");
            }
            else {
            if (response.data.emailTaken)
            {
                alert("Email is already registered");
            }
            else 
            {
                console.log("Error while trying to register")
            }
            }}});

            return (
                <div className="loginform">
                    <form onSubmit={formik.handleSubmit}>
                    <div className="loginformtitle">Register</div>
                    <label className="loginformtext" htmlFor="email">Username: </label><br/>
                    <input className="loginforminputfieldemail" type="text" id="username" name="username" value={formik.values.username} onBlur={() => {formik.setFieldTouched("username")}} onChange={formik.handleChange} /><br/>
                    {formik.errors.username && formik.touched.username ? <label className='loginformerrortext'>{formik.errors.username}</label> : null}<br/>
                    <label className="loginformtext" htmlFor="email">Email: </label><br/>
                    <input className="loginforminputfieldemail" type="text" id="email" name="email" value={formik.values.email} onBlur={() => {formik.setFieldTouched("email")}} onChange={formik.handleChange} /><br/>
                    {formik.errors.email && formik.touched.email ? <label className='loginformerrortext'>{formik.errors.email}</label> : null}<br/>
                    <label className="loginformtext" htmlFor="email">Password: </label><br/>
                    <input type={showPassword ? 'text' : 'password'} id="password" name="password" value={formik.values.password} onBlur={() => {formik.setFieldTouched("password")}} onChange={formik.handleChange} className='passwordInput loginforminputfieldpassword'/>
                    <span className='showPasswordIcon' onClick={togglePasswordVisibility} style={{ cursor: 'pointer' }}>
                    {showPassword ? <VscEyeClosed fontSize={25} /> : <HiOutlineEye fontSize={25} />}<br/>
                    </span>
                    {formik.errors.password && formik.touched.password ? <label className='loginformerrortext'>{formik.errors.password}</label> : null}<br></br> <br></br>
                    <label className="loginformtext" htmlFor="email">Repeat password: </label><br/>
                    <input type={showPassword ? 'text' : 'password'} id="repeatpassword" name="repeatpassword" value={formik.values.repeatpassword} onBlur={() => {formik.setFieldTouched("repeatpassword")}} onChange={formik.handleChange} className='passwordInput loginforminputfieldpassword'/>
                    <span className='   ' onClick={togglePasswordVisibility} style={{ cursor: 'pointer' }}>
                    {showPassword ? <VscEyeClosed fontSize={25} /> : <HiOutlineEye fontSize={25} />}<br/>
                    </span>
                    {formik.errors.repeatpassword && formik.touched.repeatpassword ? <label className='loginformerrortext'>{formik.errors.repeatpassword}</label> : null}<br></br> <br></br>
                    <button className='smallbutton' type="submit">Register</button><br/>
                    <button className="smallbutton" onClick={() => Navigate('/UserPage')}>Back</button>
                    <ToastContainer />
                    </form>
                </div>
        )
}

export default RegisterPage;