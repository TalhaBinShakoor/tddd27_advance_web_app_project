import React, { useState, useRef } from 'react'
import ApiService from '../service/ApiService';
import { Toast } from 'primereact/toast';
import { connect } from 'react-redux'

//*Router Import
import {
    BrowserRouter as Router,
    Switch,
    Route,
    Link,
    Redirect,
    useHistory
} from "react-router-dom"; //Router Import end

const signUp = {
    textDecoration: 'none'
}

function Login(props) {
    let emptyUser = {
        email: '',
        password: ''
    };

    const [user, setUser] = useState(emptyUser);
    const productService = new ApiService();
    const toast = useRef(null);

    const handleSubmit = (event) => {
        event.preventDefault();
        productService.authenticateUser(user.email, user.password).then((res) => {
            if (res.token) {
                toast.current.show({ severity: 'success', summary: 'Successful', detail: ' login opertaion completed Successfully', life: 3000 });
                props.dispatch({ type: 'USER_LOGIN', payload: { user: res.token.split("-")[0], token: res.token, firstName: res.firstName, lastName: res.lastName } });
                localStorage.setItem('token', res.token);
                <Redirect to='/App' />;

            }
            /* else {
                toast.current.show({ severity: 'error', summary: 'Successful', detail: 'Error occurred while trying to create new Web service', life: 3000 });
            } */

        }).catch((error) => {
            toast.current.show({ severity: 'error', summary: 'Error', detail: error.message, life: 3000 });
        });
        setUser(emptyUser);
    }

    const onInputChange = (e, name) => {
        console.log("change");
        const val = (e.target && e.target.value) || '';
        let _user = { ...user };
        _user[`${name}`] = val;

        setUser(_user);
    }
    return (
        <div className="text-center container">
            <Toast ref={toast} />

            <img className="mt-5 mb-4" src="ApiMonitorLogo.svg" alt="" width="92" height="77" />
            <main className="form-signin">
                <form onSubmit={handleSubmit}>
                    <h1 className="h3 mb-3 fw-semi-bold">Login</h1>

                    <div className="form-floating mb-2">
                        <input type="email" className="form-control" id="floatingInput" placeholder="name@example.com"
                            value={user.email} onChange={(e) => onInputChange(e, 'email')} required />
                        <label htmlFor="floatingInput">Email address</label>
                    </div>
                    <div className="form-floating mb-2">
                        <input type="password" className="form-control" id="floatingPassword" placeholder="Password"
                            value={user.password} onChange={(e) => onInputChange(e, 'password')} required />
                        <label htmlFor="floatingPassword">Password</label>
                    </div>
                    <div className="mb-4">
                        <h6>Don't have an account? <Link style={signUp} to="signup"> Signup Here </Link></h6>
                    </div>
                    <button className="w-100 btn btn-lg btn-primary" type="submit">Login</button>
                </form>
            </main>
        </div>
    )
}

const mapStateToProps = state => {
    //return { posts: state.posts }
    return {
        state
    }
}

const mapDispatchToProps = dispatch => {
    return {
        dispatch
    }
}

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(Login)
