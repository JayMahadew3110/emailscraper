import React, { useEffect, useState } from "react";
import '/src/pages/Style.css';
import { useNavigate } from "react-router-dom";
import UserService from "../services/UserService";

function HomePage(props) {
    const Navigate = useNavigate();
    const [hasUserId, setHasUserId] = useState([])
    const [isAdmin, setIsAdmin] = useState(false) 
    useEffect(() => {
        const userId = sessionStorage.getItem('userId');
        if (userId) {
            const numericUserId = Number(userId);
            setHasUserId(true)
            UserService.getUserDetails(numericUserId,sessionStorage.getItem('tokens'))
                .then(res => setIsAdmin(res.data.admin));
            console.log('Converted numericUserId:', numericUserId);
        } else {
            setHasUserId(false)
            console.error('No user ID found');
        }
    }, []);

    function destroyToken()
    {
        sessionStorage.removeItem("userId");
        sessionStorage.removeItem("tokens");
        props.tokenRemover('');
        setHasUserId(false);
        setIsAdmin(false)
    }

    return (
            <div className="div">
                <div className="title">Email web scraper</div>
                <button className="button" onClick={() => Navigate('EmailPage')}>Emails page</button> 
                {
                    isAdmin &&
                    <div>
                        <button className="smallbutton" onClick={() => Navigate('UserPage')}>Users</button>
                    </div>
                }
                <br/>
                {
                    !hasUserId ? 
                    <div>
                        <button className="smallbutton" onClick={() => Navigate('LoginPage')}>Login</button> 
                    </div>
                    :
                    <div>
                        <button className="smallbutton" onClick={() => destroyToken()}>Logout</button> 
                    </div>
                }

            </div>
    )
}

export default HomePage;