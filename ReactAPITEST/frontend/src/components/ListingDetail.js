
import React, {Component, useState, useContext} from "react";
import { useEffect } from "react";
import axios from "axios";
import { useLocation } from 'react-router-dom'
import UserContext from "./UserContext";
import Card from 'react-bootstrap/Card';

function ListingDetail () {


  //ID from selected item in catalog
  const { state } = useLocation()
  const { id } = state;
  console.log(id);

  //post URL
  //INSERT POST URL ONCE MADE.
  const url = "http://localhost:8080/bid/newbid"


  //current user ID
  const currentUser = useContext(UserContext);

  const [selectedUserID, setSelectedUserID] = useState("")

  switch(currentUser){
    case "Lone": setSelectedUserID(1);
    case "Lars": setSelectedUserID(2);
    case "Lotte": setSelectedUserID(3);
    }

  //ID from selected item mapped to variable
  const listingID = useState({id});

  //useState for bid
  const [bid, setBid] = useState("");

  //handleSubmit for onclick /post to URL
  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log(
      "UserID: ",
      selectedUserID,
      "ListingID: ",
      listingID,
      "bid: ",
      bid
    )
    try{
      const resp = await axios.post(url, {
        userID: selectedUserID,
        listingID: listingID,
        amount: bid
      });
      console.log(resp.data)
    } catch(error){
      console.log(error.response)
    }
  };

  const [listing, setListing] = useState("")

  useEffect(() => {
    axios.get(`http://localhost:8081/api/v1/listing/${id}`)
      .then(response => {
        console.log(response)
        setListing(response.data);
      })
      .catch(error => {
        console.error(error);
      });
  }, [id]);



  
    // Fetch item data based on the itemId and render the details
    return (
          <div>
            <Card className="card-listing">
            <img className="listing-img" src={process.env.PUBLIC_URL + '../images/placeholder.jpg'}></img>
            <p className="card-title">Listing Title: {listing.listingName} </p>
            <a> Listing Description: {listing.listingDescription} </a>
            <p> Price: {listing.listingPrice} DKK</p>
            </Card>
          
          <section>
            <form className="bid-form" onSubmit={handleSubmit}>
            <input type="number" 
                className="bid-input" 
                id="bid" 
                value={bid}
                onChange={(e) => setBid(e.target.value)}
            />
            <button> Bid </button>
            </form>
          </section>
        </div>




    )
    }

  
  export default ListingDetail;