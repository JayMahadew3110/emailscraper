import React, { useEffect, useState } from "react";
import '/src/pages/Style.css';
import { useNavigate } from "react-router-dom";
import EmailService from "../services/EmailService";

function EmailPage() {
    const Navigate = useNavigate();
    const [EmailList, setEmailList] = useState([]);
    const [token, setToken] = useState(sessionStorage.getItem('tokens'))

    async function LoadEmails()
    {
        await EmailService.getAllEmails(token)
            .then(res => setEmailList(res.data))
    }

    useEffect(()=> {
        setToken(sessionStorage.getItem('tokens'));
        console.log("test");
        LoadEmails();
    }, [])

    return (
        <div className="div">
        <div className="title">Scraped emails:</div>
        <div className="emailList">
                {
                        EmailList.map((email, index) => {
                            return<div>{email.id} : {email.email}</div>
                        })
                    }
                </div>
        <button className="smallbutton" onClick={() => Navigate('/')}>Back</button>
    </div>
    )
}

export default EmailPage;