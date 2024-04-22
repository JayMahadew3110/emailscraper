import axios from "axios";

const hostname = 'http://localhost:8080'

function getUserDetails(id, token) {
    return axios.get(`${hostname}/user/GetById?userId=${id}`,{
        headers: {
            "Authorization" : `Bearer ${token}`
        }})
}

function getAllUsers(token) {
    return axios.get(`${hostname}/user`,{
        headers: {
            "Authorization" : `Bearer ${token}`
        }})
}

function deleteUser(userId,token) {
    return axios.delete(`${hostname}/user/${userId}`,{
        headers: {
            "Authorization" : `Bearer ${token}`
        }})
}

function promoteAdmin(userId,token)
{
    return axios.post(`${hostname}/user/promote?userId=${userId}`, {},{
        headers: {
            "Authorization" : `Bearer ${token}`
        }})
}

export default {
    getUserDetails,
    getAllUsers,
    deleteUser,
    promoteAdmin
}