import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Login from './components/Login';

function App() {
    return (
        <Router>
            <div className="App">
                <Switch>
                    <Route path="/login" component={Login} />
                    <Route path="/" exact>
                        <h1>Welcome to the Application</h1>
                    </Route>
                </Switch>
            </div>
        </Router>
    );
}

export default App;