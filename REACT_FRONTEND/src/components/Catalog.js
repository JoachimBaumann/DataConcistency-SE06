import React from "react";
import Listing from "../Listing/Listing.js";


function Catalog({listings}){
    return (
    <div className="Catalog">
        {listings.map(listing =>
            <Listing key ={listing.id} listing={listing}/>
            )}
    </div>
    );
}

export default Catalog

