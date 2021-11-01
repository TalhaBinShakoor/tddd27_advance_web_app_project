import { createStore } from 'redux'

const initialState = {
  user: null,
  token: null,
  firstName: null,
  lastName: null
}

const reducer = (state = initialState, action) => {
  if (action.type === 'USER_LOGIN') {
    return Object.assign({}, state, {
      //user: state.user.concat(action.payload),
      user: action.payload.user,
      token: action.payload.token,
      firstName: action.payload.firstName,
      lastName: action.payload.lastName

    })
  }

  if (action.type === 'USER_LOGOUT') {
    return {
      ...state,
      user: null,
      token: null,
      firstName: null,
      lastName: null
    }
  }

  return state
}

const store = createStore(
  reducer,
  window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__()
)

export default store

