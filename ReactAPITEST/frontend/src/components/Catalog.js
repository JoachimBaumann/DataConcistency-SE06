import React, { useEffect, useState } from "react";
import axios from "axios";
import { Link, useLocation } from 'react-router-dom'
import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';

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
      <div className="catalogContainer">
      <div className="cardContainer">
        {listings.map(listing => (
          <Card key={listing.listingID} className="mb-3" style={{width: '18rem'}}>
            <Card.Body>
              <Card.Img className="cardImg" src={process.env.PUBLIC_URL + './images/placeholder.jpg'} />
              <Card.Title className="cardTitle">{listing.listingName}</Card.Title>
              <Card.Text className="cardDescription">{listing.listingDescription}</Card.Text>
              <Link className="listingLink" to={`/listing/${listing.listingID}`} state={{ id: listing.listingID }}>
                View Details
              </Link>
            </Card.Body>
          </Card>
        ))}
      </div>
      </div>
    );
}
/*
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
*/
export default Catalog;