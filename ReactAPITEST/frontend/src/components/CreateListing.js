import React, { useEffect, useState, useContext} from "react";
import axios from "axios";
import UserContext from "./UserContext";
import { Link, useNavigate } from "react-router-dom";


const CreateListing = () => {
    const url="http://localhost:8080/api/v1/listings/"


    const[listingName, setListingName] = useState("");
    const[closed, setClosed] = useState("");
    const[listingPrice, setListingPrice] = useState("");
    const[listingDescription, setListingDescription] = useState("");
    const[listingCondition, setListingCondition] = useState("");
    const[pictureURL, setPictureURL] = useState("");
    const selectedUserID = useContext(UserContext); // Access the selectedUserID from context
    const [currentUserID, setCurrentUserID] = useState("");
    const navigate = useNavigate();
  
    useEffect(() => {
      setCurrentUserID(selectedUserID);
    }, [selectedUserID]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        console.log(
            listingName, 
            listingPrice, 
            listingDescription, 
            listingCondition, 
            pictureURL,
            currentUserID
            );

        try{
            const resp = await axios.post(url, {
                listingName: listingName, 
                listingPrice: listingPrice, 
                listingDescription: listingDescription, 
                listingCondition: listingCondition,
                pictureURL: pictureURL
            });
            console.log(resp.data)
            navigate("/")
        } catch(error){
            console.log(error.response)
        }
    };
    
    return (
        <section>
            <h2 className="text-center">Create Listing</h2>
                <form className="form" onSubmit={handleSubmit}>
                    <div className="form-row">
                        <label htmlFor="listingName" className="form-label">
                        Listing Name:                 
                        </label>
                <input type="text" 
                className="form-input" 
                id="listingName" 
                value={listingName}
                onChange={(e) => setListingName(e.target.value)}
                />
                    </div>
                <div className="form-row">
                 <label htmlFor="listingPrice" className="form-label">
                Listing Price:                
                </label>
                <input type="text" 
                 className="form-input" 
                id="listingPrice" 
                value={listingPrice}
                onChange={(e) => setListingPrice(e.target.value)}
                />
                </div>
            <div className="form-row">
                <label htmlFor="listingDescription" className="form-label">
                Listing Description:               
                </label>
                <input type="text" 
                className="form-input" 
                id="listingDescription" 
                value={listingDescription}
                onChange={(e) => setListingDescription(e.target.value)}
                />
            </div>
            <div className="form-row">
                <label htmlFor="listingCondition" className="form-label">
                Listing Condition:                
                </label>
                <input type="text" 
                className="form-input" 
                id="listingCondition" 
                value={listingCondition}
                onChange={(e) => setListingCondition(e.target.value)}
            />
            </div>
             <div className="form-row">
             <label htmlFor="pictureURL" className="form-label">
            Images:                
             </label>
                <input type="text" 
                className="form-input" 
                id="pictureURL" 
             value={pictureURL}
                onChange={(e) => setPictureURL(e.target.value)}
            />
            </div>
            <button>
                Submit listing
            </button>
            </form>
        </section>
    )
}
export default CreateListing