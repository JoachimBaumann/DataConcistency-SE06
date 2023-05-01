import React from "react";


function Listing({ listing }){
    return(
        <div className="Listing">
            <img src={listing.image} alt={listing.name}/>
            <h2>{listing.name}</h2>
            <p>{listing.description}</p>
            <button>See Listing</button>
        </div>
    )
}

export default Listing