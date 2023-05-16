import React, { useState } from "react";
import axios from "axios";


const CreateListing = () => {
    const url="http://localhost:8081/api/v1/listings/"


    const[listingName, setListingName] = useState("");
    const[closed, setClosed] = useState("");
    const[listingPrice, setListingPrice] = useState("");
    const[listingDescription, setListingDescription] = useState("");
    const[listingCondition, setListingCondition] = useState("");
    const[pictureURL, setPictureURL] = useState("");

    const handleSubmit = async (e) => {
        e.preventDefault();
        console.log(
            listingName, 
            closed, 
            listingPrice, 
            listingDescription, 
            listingCondition, 
            pictureURL
            );

        try{
            const resp = await axios.post(url, {
                listingName: listingName, 
                closed: closed, 
                listingPrice: listingPrice, 
                listingDescription: listingDescription, 
                listingCondition: listingCondition,
                pictureURL: pictureURL
            });
            console.log(resp.data)
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
            <label htmlFor="closed" className="form-label">
            Listing Status:                 
            </label>
            <input type="closed" 
            className="form-input" 
            id="closed" 
            value={closed}
            onChange={(e) => setClosed(e.target.value)}
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
            <button>click me</button>
            </form>
        </section>
    )
}
export default CreateListing