import React from 'react';
import {Menubar} from "primereact/menubar";
import {ReactComponent as Logo} from "assets/logo.svg";

const Header = () => {

  const start = () => {
    return (
      <>
        <Logo style={{height: "45px", width: "100px", marginRight: "10px"}}/>
      </>
    )
  }

  const end = () => {
    return (
      <h2 className="mr-4">Welcome To The Configuration App</h2>
    )
  }

  return (
    <Menubar start={start} end={end}/>
  )
}

export default Header;