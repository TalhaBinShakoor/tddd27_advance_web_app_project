import 'primereact/resources/themes/saga-blue/theme.css';
import 'primereact/resources/primereact.min.css';
import 'primeicons/primeicons.css';
import 'primeflex/primeflex.css';

import Footer from './Footer'
import Signup from './Signup'
import Header from './Header'
import WebService from './WebService';
import Dashboard from './Dashboard';
import ProfileView from './ProfileView';
import 'bootstrap/dist/css/bootstrap.min.css'; //*Import React-Bootstrap CSS
import { connect } from 'react-redux'

//*Router Import
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link,
  useHistory
} from "react-router-dom"; //*Router Import end
import Logout from './Logout';

export default function MainPage() {
  return (
    <Router>

      <div className="App">
        <Header />

        <Switch>
          <Route exact path="/">
            <Dashboard />
          </Route>

          <Route exact path="/dashboard">
            <Dashboard />
          </Route>

          <Route exact path="/profile">
            <ProfileView />
          </Route>

          <Route exact path="/display-as-list">
            <WebService />
          </Route>

          <Route exact path="/data-table-crud-demo">
            <WebService />
          </Route>

          <Route exact path="/signup">
            <Signup />
          </Route>

          <Route exact path="/logout">
            <Logout />
          </Route>

        </Switch>

        <Footer />
      </div>

    </Router>
  );
}

//export default App;
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