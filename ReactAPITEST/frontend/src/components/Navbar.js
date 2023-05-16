export default function Navbar(){


    return <nav className="nav">
        <a href="/" className="site-title">Home</a>
        <ul>
                <li>
                <a href="/createListing">Create Listing</a>
                </li>
                <li>
                <a href="/createAccount">Create Account</a>
            </li>
        </ul>
    </nav>
}