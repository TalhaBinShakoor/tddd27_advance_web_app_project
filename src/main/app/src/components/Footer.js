import React from 'react'
import Card from 'react-bootstrap/Card'; //*Import React-Bootstrap

export default function Footer() {
    const footer = {
        position: 'fixed',
        left: '0',
        bottom: '0',
        width: '100%',
        backgroundColor: '#007bff',
        color: 'white',
        textAlign: 'center'
    }
    return (
        <Card className="text-center" style={footer} >
            <Card.Header>API Monitor &copy; 2021-22</Card.Header>
        </Card>
    )
}
