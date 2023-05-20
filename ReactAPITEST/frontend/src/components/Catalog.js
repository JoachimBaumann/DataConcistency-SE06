import React, { useEffect, useState } from "react";
import axios from "axios";
import { Link, useLocation } from 'react-router-dom'
import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';

function Catalog(){
    const [listings, setListings] = useState([]);

    useEffect(() => {
        axios.get("http://localhost:8080/api/v1/listings/")
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
export default Catalog;