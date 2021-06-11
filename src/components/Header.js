import React from 'react'
import { AppBar, Toolbar } from "@material-ui/core";
import { Link } from 'react-router-dom'

import "./Nav.css";


export default function Header() {
  const displayDesktop = () => {
    return <Toolbar>
    
      <ul className="nav">
        <li className="item "><Link className="link" to='/'>HOME</Link></li>
        <li className="item "><Link className="link" to='/Digit'>DIGIT</Link></li>
        <li className="item "><Link className="link" to='/Churn'>CHURN</Link></li>
      </ul>
   
    </Toolbar>;
  };
  
  return (
    <header>
       <AppBar>{displayDesktop()}</AppBar>
   
  </header>
  );
};
