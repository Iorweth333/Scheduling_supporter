import React, {Component} from 'react';
import {connect} from 'react-redux';
import {fetchLessons} from "../actions";

import Spreadsheet from "react-spreadsheet";


class Xlsx extends Component{


    componentDidMount() {
        this.props.fetchLessons();
    }

    constructor (props) {
        super(props);
    }

    render(){

        const { lessons } = this.props;

        if (!lessons) {
            return <div>Loading...</div>;
        }

        return(
            <div>
                <Spreadsheet data={this.props.lessons} />
            </div>
        );
    }
}

function mapStateToProps(state){
    return{
        lessons: state.lessons
    };
}

export default connect(mapStateToProps, {fetchLessons})(Xlsx);




