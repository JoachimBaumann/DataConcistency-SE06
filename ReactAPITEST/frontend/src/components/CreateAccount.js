import React, { useState } from "react";
import axios from "axios";


const CreateAccount = () => {
    const url="http://localhost:8080/accounts/"


    const[username, setUsername] = useState("");
    const[password, setPassword] = useState("");
    const[email, setEmail] = useState("");

    const handleSubmit = async (e) => {
        e.preventDefault();
        console.log(username, password, email);

        try{
            const resp = await axios.post(url, {username: username, password: password, email: email});
            console.log(resp.data)
        } catch(error){
            console.log(error.response)
        }
    };
    
    return (
        <section>
            <h2 className="text-center">Create Account</h2>
            <form className="form" onSubmit={handleSubmit}>
            <div className="form-row">
            <label htmlFor="username" className="form-label">
            username                
            </label>
            <input type="text" 
            className="form-input" 
            id="username" 
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            />
            </div>
            <div className="form-row">
            <label htmlFor="password" className="form-label">
            password                
            </label>
            <input type="password" 
            className="form-input" 
            id="password" 
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            />
            </div>
            <div className="form-row">
            <label htmlFor="email" className="form-label">
            email                
            </label>
            <input type="text" 
            className="form-input" 
            id="email" 
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            />
            </div>
            <button>click me</button>
            </form>
        </section>
    )
}
export default CreateAccount