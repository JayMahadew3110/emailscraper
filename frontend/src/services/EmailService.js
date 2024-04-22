import axios from "axios";

const hostname = 'http://localhost:8080'

function getAllEmails(token) {
    return axios.get(`${hostname}/emails`,{
        headers: {
            "Authorization" : `Bearer ${token}`
        }})
}

export default {
    getAllEmails
}