import React, { useEffect, useState } from "react";
import axios from "axios";
import { Link, useLocation } from 'react-router-dom'

function Catalog(){
    const [listings, setListings] = useState([]);

    useEffect(() => {
        axios.get("http://localhost:8081/api/v1/listings/")
        .then(response => {
            setListings(response.data);
        })
        .catch(error => {
            console.error(error);
        });
    }, [])

    return (
        <div>
          <ul className="listingUL">
            {listings.map(listing => (
              <li className="listingList" key={listing.listingID} orientation="horizontal">
                <Link to= {`/listing/${listing.listingID}`} state={{ id: listing.listingID }}>
                <h2>{listing.listingName}</h2>
                <p>{listing.listingDescription}</p>
                <img href="./frontend/Images/placeholder.png"/>
                </Link>
              </li>
            ))}
          </ul>
        </div>
      );
}

export default Catalog;