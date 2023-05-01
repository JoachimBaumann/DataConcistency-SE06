import './App.css';
import { useEffect, useState } from 'react';
import Catalog from './components/Catalog.js'

function App() {

  const [listings, setListings] = useState([]);

    useEffect(()=> {
      fetch("INSERT API FOR CATALOG ITEMS")
      .then(response => response.json())
      .then(data => setListings(data))
      .catch(error => console.log(error));
    }, [])




  return (
    <div className="App">
      <header className="App-header">
        <p> Auction Catalog</p>
        <Catalog listings={listings}/>
      </header>
    </div>
  );
}

export default App;
