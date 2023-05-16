import React from "react";
import CreateAccount from "./components/CreateAccount";
import CreateListing from "./components/CreateListing";
import Catalog from "./components/Catalog";
import ListingDetail from "./components/ListingDetail";
import {BrowserRouter, Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar";

export default function App(){
  return(
    <><Navbar />
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Catalog />}> </Route>
        <Route path="/createListing" element={<CreateListing />}> </Route>
        <Route path="/listing/:id" element={<ListingDetail />}>  </Route>
        <Route path="/createAccount" element={<CreateAccount />}> </Route>
      </Routes>
    </BrowserRouter></>

  )
}

//add to routes, once AccountBackend has been coupled on docker-compose.
//<Route path="/createAccount" element={<CreateAccount/>}> </Route>
