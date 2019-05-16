import React, { Component } from 'react';
import logo from '../logo.svg';
import '../App.css';
//import {redirectToUpload} from "../actions";

class App extends Component {
  constructor(props) {
    super(props);
    document.title = "Scheduling Supporter"
  }
  redirectToUpload = () => {
    this.props.history.push(`/upload`)
  }
  render() {
    return (
        <div style={{padding: "50px"}}>
        <div className="row"><div className="col-xs-12">
            <button className="btn btn-success" onClick={this.redirectToUpload}>Upload Schedule</button>
        </div></div>
        </div>
    );
  }
}

export default App;
