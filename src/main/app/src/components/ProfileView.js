import React from 'react'
import { connect } from 'react-redux'


function ProfileView(props) {
    return (

        <form className=" container">
            <div className="form-group row ">
                <label for="staticEmail" className="col-sm-2 col-form-label p-text-bold">Email:</label>
                <div className="col-sm-10">
                    <div> {props.state.user}</div>
                </div>
            </div>
            <div className="form-group row">
                <label for="staticEmail" className="col-sm-2 col-form-label p-text-bold">First Name:</label>
                <div className="col-sm-10">
                    <div> {props.state.firstName}</div>
                </div>
            </div>
            <div className="form-group row">
                <label for="staticEmail" className="col-sm-2 col-form-label p-text-bold">Last Name:</label>
                <div className="col-sm-10">
                    <div>  {props.state.lastName}</div>
                </div>
            </div>
        </form>
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
)(ProfileView)
