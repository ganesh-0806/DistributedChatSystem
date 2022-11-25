import Button from 'react-bootstrap/Button';
import Container from 'react-bootstrap/Container';
import React, { Component }  from 'react';
import Form from 'react-bootstrap/Form';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';

function ChatNavbar() {
    return (
    <Navbar bg="light" expand="lg">
        <Container fluid>
        <Navbar.Brand href="#">Chat App</Navbar.Brand>
        <Navbar.Toggle aria-controls="navbarScroll" />
        <Navbar.Collapse id="navbarScroll">
            <Nav
            className="me-auto my-2 my-lg-0"
            style={{ maxHeight: '100px' }}
            navbarScroll
            >
            
            </Nav>
            <Form className="d-flex">
            <Form.Control
                type="search"
                placeholder="Search"
                className="me-2"
                aria-label="Search"
            />
            <Button style={{ whiteSpace: 'nowrap' }} variant="outline-success">Add Friend</Button>
            </Form>
        </Navbar.Collapse>
        </Container>
    </Navbar>
    );
}

export default ChatNavbar;