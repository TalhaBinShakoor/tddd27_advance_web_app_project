import React, { useState, useRef, useEffect } from 'react'
import { connect } from 'react-redux'

//*Router Import
import {
  BrowserRouter as Router,
  Redirect,
  useHistory
} from "react-router-dom"; //Router Import end

function Logout(props) {

  const history = useHistory();

  useEffect(() => {
    console.log("?????");
    props.dispatch({ type: 'USER_LOGOUT', payload: { user: null, token: null } });
    localStorage.removeItem('token');
    history.push("/");
  }, []);

  return (
    <div className="text-center container">

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
)(Logout)
