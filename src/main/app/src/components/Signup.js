
import React, { useState, useEffect, useRef } from 'react';
import { Toast } from 'primereact/toast';

//*Router Import
import {
    BrowserRouter as Router,
    Switch,
    Route,
    Link,
    Redirect,
    useHistory
} from "react-router-dom"; //*Router Import end
import ApiService from '../service/ApiService';

const login = {
    textDecoration: 'none'
}

export default function Signup() {

    const history = useHistory();

    let emptyUser = {
        id: null,
        email: '',
        firstName: '',
        lastName: '',
        password: '',
        confirmPassword: ''
    };

    const [user, setUser] = useState(emptyUser);
    const toast = useRef(null);

    const handleSubmit = (event) => {
        event.preventDefault();
        if (isPasswordAndConfirmPasswordAreEqual(user.password, user.confirmPassword)) {
            const productService = new ApiService();

            productService.createUser(user.email, user.firstName, user.lastName, user.password).then((res) => {
                if (res == 201) {
                    toast.current.show({ severity: 'success', summary: 'Successful', detail: 'Your Account created Successfully', life: 3000 });
                    history.push("/");

                }
                else {
                    toast.current.show({ severity: 'error', summary: 'Error', detail: res, life: 3000 });

                }

            }).catch((error) => {
                toast.current.show({ severity: 'error', summary: 'Error', detail: "error occurred while signup try again", life: 3000 });
            });
        }
        else {
            toast.current.show({ severity: 'error', summary: 'Error', detail: 'Password and confirm password should be the same', life: 3000 });
        }
        setUser(emptyUser);
    }

    const onInputChange = (e, name) => {
        console.log("change");
        const val = (e.target && e.target.value) || '';
        let _user = { ...user };
        _user[`${name}`] = val;

        setUser(_user);
    }

    const isPasswordAndConfirmPasswordAreEqual = (password, confirmPassword) => {
        if (password === confirmPassword) {
            return true;
        }
        else {
            return false;
        }

    }

    return (
        <div className="text-center container">

            <Toast ref={toast} />

            <img className="mt-5 mb-4" src="ApiMonitorLogo.svg" alt="" width="92" height="77" />
            <main className="form-signin">
                <form onSubmit={handleSubmit}>
                    <h1 className="h3 mb-3 fw-semi-bold">Sign Up</h1>

                    <div className="form-floating mb-2">
                        <input type="email" required className="form-control" value={user.email} id="floatingInput" placeholder="name@example.com" onChange={(e) => onInputChange(e, 'email')} />
                        <label htmlFor="floatingInput">Email address</label>
                    </div>
                    <div className="form-floating mb-2">
                        <input type="text" required className="form-control" value={user.firstName} id="floatingInput" placeholder="First Name" onChange={(e) => onInputChange(e, 'firstName')} />
                        <label htmlFor="floatingInput">First Name</label>
                    </div>
                    <div className="form-floating mb-2">
                        <input type="text" required className="form-control" value={user.lastName} id="floatingInput" placeholder="Last Name" onChange={(e) => onInputChange(e, 'lastName')} />
                        <label htmlFor="floatingInput">Last Name</label>
                    </div>
                    <div className="form-floating mb-2">
                        <input type="password" required className="form-control" value={user.password} id="floatingPassword" placeholder="Password" onChange={(e) => onInputChange(e, 'password')} />
                        <label htmlFor="floatingPassword">Password</label>
                    </div>
                    <div className="form-floating mb-2">
                        <input type="password" required className="form-control" value={user.confirmPassword} id="floatingPassword" placeholder="Password" onChange={(e) => onInputChange(e, 'confirmPassword')} />
                        <label htmlFor="floatingPassword">Confirm Password</label>
                    </div>

                    <div className="mb-4">
                        <h6>Already have an account? <Link style={login} to="login"> Login Here </Link></h6>
                    </div>
                    <button className="w-100 btn btn-lg btn-primary" type="submit">Sign Up</button>
                </form>
            </main>
        </div>
    )
}
