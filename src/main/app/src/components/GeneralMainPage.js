import 'primereact/resources/themes/saga-blue/theme.css';
import 'primereact/resources/primereact.min.css';
import 'primeicons/primeicons.css';
import 'primeflex/primeflex.css';

import Signup from './Signup';
import Login from './Login';
import 'bootstrap/dist/css/bootstrap.min.css'; //*Import React-Bootstrap CSS

//*Router Import
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link
} from "react-router-dom"; //*Router Import end

export default function GeneralMainPage() {
  const NoMatch = () => <Login />;

  return (
    <Router>
      <div className="App">
        <Switch>

          <Route exact path="/signup">
            <Signup />
          </Route>

          <Route exact path="/" >
            <Login />
          </Route>

          <Route exact path="/login" >
            <Login />
          </Route>

          <Route exact path="/logout" >
            <Login />
          </Route>

          <Route component={NoMatch} />

        </Switch>
      </div>

    </Router>
  );
}



