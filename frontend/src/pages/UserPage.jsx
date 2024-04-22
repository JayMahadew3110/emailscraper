import React, { useEffect, useState } from "react";
import '/src/pages/Style.css';
import { useNavigate } from "react-router-dom";
import UserService from "../services/UserService";

function UserPage() {
    const Navigate = useNavigate();
    const [UserList, setUserList] = useState([]);
    const [token, setToken] = useState(sessionStorage.getItem('tokens'))

    async function LoadUsers()
    {
        await UserService.getAllUsers(token)
            .then(res => setUserList(res.data))
    }
    function deleteUser(userId)
    {
        UserService.deleteUser(userId, token)
        .then(LoadUsers)
    }
    function makeAdmin(userId)
    {
        UserService.promoteAdmin(userId, token)
        .then(LoadUsers)
    }

    useEffect(()=> {
        setToken(sessionStorage.getItem('tokens'));
        LoadUsers();
    }, [])

    return (
        <div className="div">
        <div className="title">Users:</div>
        <div className="userList">
                {
                        UserList.map((user, index) => {
                            return<div className="userLine"><div className="user-name">{user.id} : {user.name}</div><button onClick={()=> makeAdmin(user.id)} className="action-button">Make admin</button><button onClick={()=> deleteUser(user.id)} className="action-button">Delete</button></div>
                        })
                    }
                </div>
        <button className="smallbutton" onClick={() => Navigate('/RegisterPage')}>Add new user</button><br/>
        <button className="smallbutton" onClick={() => Navigate('/')}>Back</button>
    </div>
    )
}

export default UserPage;