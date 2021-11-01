import React from 'react'
import '../App.css';
import Navbar from 'react-bootstrap/Navbar'; //*Import React-Bootstrap
import Nav from 'react-bootstrap/Nav'; //*Import React-Bootstrap
import Form from 'react-bootstrap/Form'; //*Import React-Bootstrap
import FormControl from 'react-bootstrap/FormControl'; //*Import React-Bootstrap
import NavDropdown from 'react-bootstrap/NavDropdown'; //*Import React-Bootstrap
import Button from 'react-bootstrap/Button'; //*Import React-Bootstrap

//*Router Import
import {
    BrowserRouter as Router,
    Switch,
    Route,
    Link
} from "react-router-dom"; //*Router Import end

const apiMonitor = {
    color: 'white',
    textDecoration: 'none'
}
const home = {
    color: 'white',
    textDecoration: 'none'
}
const dashboard = {
    color: 'white',
    textDecoration: 'none'
}
const changePassword = {
    textDecoration: 'none',
    color: 'Black',
}
const createWebService = {
    textDecoration: 'none',
    color: 'Black',
}
const displayList = {
    textDecoration: 'none',
    color: 'Black',
    paddingRight: '35px',
    paddingTop: '12px',
    paddingBottom: '12px'
}
const profile = {
    textDecoration: 'none',
    color: 'Black',
    paddingRight: '92px',
    paddingTop: '12px',
    paddingBottom: '12px'
}
const logout = {
    textDecoration: 'none',
    color: 'Black',
    paddingRight: '98px',
    paddingTop: '6px',
    paddingBottom: '8px'
}

export default function Header() {
    return (
        <>
            <Navbar className="sticky-top" bg="primary" variant="dark" expand="lg" >
                <Navbar.Brand> <Link style={apiMonitor} to="/"> API Monitor </Link> </Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="mr-auto">
                        <Nav.Link> <Link className="dashBoard" style={dashboard} to="/dashboard"> Dashboard </Link> </Nav.Link>
                        <NavDropdown title="Web Services" id="basic-nav-dropdown">
                            <NavDropdown.Item > <Link style={displayList} to="/display-as-list"> Display as list </Link></NavDropdown.Item>
                        </NavDropdown>
                        <NavDropdown title="Users" id="basic-nav-dropdown">
                            <NavDropdown.Item > <Link style={profile} to="profile"> Profile </Link></NavDropdown.Item>
                            <NavDropdown.Divider />
                            <NavDropdown.Item> <Link style={logout} to="/logout"> Logout </Link> </NavDropdown.Item>
                        </NavDropdown>
                    </Nav>
                    <Form inline>
                        <FormControl type="text" placeholder="Search" className="mr-sm-2" />
                        <Button variant="outline-light">Search</Button>
                    </Form>
                </Navbar.Collapse>
            </Navbar >
            <br />

        </>
    )
}
