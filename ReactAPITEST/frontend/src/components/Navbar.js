import React, { useContext } from "react";
import UserContext from "./UserContext";

const Navbar = ({ setSelectedUserID }) => {
  const selectedUserID = useContext(UserContext);

  const handleSelectChange = (e) => {
    const selectedUserID = e.target.value;
    setSelectedUserID(selectedUserID);
  };

  return (
    <nav className="nav">
      <a href="/" className="site-title">
        Home
      </a>
      <ul>
        <li>
          <a href="/createListing">Create Listing</a>
        </li>
        <li className="select-styling">
          <select name="users" id="user-select" onChange={handleSelectChange}>
            <option value="">--Choose user--</option>
            <option value="1">Lars</option>
            <option value="2">Lone</option>
            <option value="3">Lotte</option>
          </select>
        </li>
      </ul>
      <p>Selected User ID: {selectedUserID}</p>
    </nav>
  );
};

export default Navbar;
//  <a href="/createAccount">Create Account</a>