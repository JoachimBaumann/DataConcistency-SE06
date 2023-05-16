
import React, {Component, useState} from "react";
import { useEffect } from "react";
import axios from "axios";
import Catalog from "./Catalog";
import { useLocation } from 'react-router-dom'

function ListingDetail () {

  const { state } = useLocation()
  const { id } = state;
  console.log(id);

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
        <p>Listing Title: {listing.listingName} </p>
        <p> Listing Description: {listing.listingDescription} </p>
      </div>
    )
    }

  
  export default ListingDetail;