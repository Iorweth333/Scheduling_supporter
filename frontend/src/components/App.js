import React, { Component } from 'react';
import '../App.css';

class App extends Component {

  constructor(props) {
    super(props);
    document.title = "Scheduling Supporter";
  }

  redirectToUpload = () => {
    this.props.history.push(`/upload`);
  };

  render() {
    return (
        <div className="header">
          <div style={{padding: "50px"}}>
          <div className="row"><div className="col-xs-12">
              <button className="btn btn-success" onClick={this.redirectToUpload}>Upload Schedule</button>
          </div></div>
          </div>
        </div>
    );
  }
}

export default App;
