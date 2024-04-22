import axios from "axios";

const instance = axios.create({
  baseURL: 'http://localhost:8080',
});

const Login = async(values) =>{
    try {
        const response = await instance.post(`/auth/login?username=${values.email}&password=${values.password}`);
        return response;
      } catch (error) {
        console.error("Error logging: ", error);
        throw error;
      }
}

const Register = async(values) =>{
  try {
    const response = await instance.post(`/user/Create`, {
        "name": values.username,
        "email": values.email,
        "password": values.password,
        "admin": "false",
    },
    {
      headers: {
        "Authorization" : `Bearer ${sessionStorage.getItem('tokens')}`
      }
    });
    return response;
  } catch (error) {
    console.error("Error register: ", error);
    throw error;
  }
}

export default {
  Login,
  Register
};