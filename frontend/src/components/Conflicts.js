import React, {Component} from 'react';
import {connect} from "react-redux";
import {fetchConflicts} from "../actions";
import {Spinner} from "react-bootstrap";

class Conflicts extends Component {
    constructor(props) {
        super(props);

        document.title = "Scheduling Supporter";

        this.state = {
            conflicts: []
        };
    }

    parseConflicts(conflicts) {
        return conflicts.map( conflict => {
            return (
                <div className="conflict">
                    <p>{ conflict.typeOfConflict }</p>
                    <p>{ conflict.lessonId1 }</p>
                    <p>{ conflict.lessonId2 }</p>
                </div>
        )
        });
    }

    componentDidMount() {
        if (this.props.loading) {
            this.props.fetchConflicts();
        }
    }

    componentWillReceiveProps(nextProps, nextContext) {
        this.setState({conflicts: this.parseConflicts(nextProps.conflicts)});
    }

    render() {
        const { error, loading, conflicts } = this.props;

        if (error) {
            return <div>Error! {error.message}</div>;
        }

        if (loading) {
            return <div><Spinner animation="grow" /></div>;
        }

        return (
            <div className="Conflicts">
                {this.state.conflicts}
            </div>
        )
    }
}

function mapStateToProps(state){
    return{
        loading: state.conflicts.loading,
        error: state.conflicts.error,
        conflicts: state.conflicts.conflicts,
    };
}

export default connect(mapStateToProps, {fetchConflicts})(Conflicts);