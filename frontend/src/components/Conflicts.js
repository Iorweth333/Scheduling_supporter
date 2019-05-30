import React, {Component} from 'react';
import {connect} from "react-redux";
import {fetchConflicts} from "../actions";
import {Spinner} from "react-bootstrap";
import '../App.css';
import Jumbotron from 'react-bootstrap/Jumbotron'
import TabContainer from "react-bootstrap/es/TabContainer";
import Conflict from "./Conflict";


export class Conflicts extends Component {

    constructor(props) {
        super(props);

        document.title = "Scheduling Supporter";

        this.state = {
            conflicts: []
        };
    }

    redirectToUpload = () => {
        this.props.history.push(`/upload`);
    };

    redirectToLessons = () => {
        this.props.history.push(`/lessons`);
    };


    parseConflicts(conflicts) {
        return conflicts.map( (conflict, index) => {
            return (
                <div key={ index }>
                <Jumbotron fluid style={{padding: "20px"}}>
                    <TabContainer>
                        <h4 className="conflicts">{ conflict.typeOfConflict } CONFLICT</h4>
                        <Conflict lesson={ conflict.lesson1 }/>
                        <Conflict lesson={ conflict.lesson2 }/>
                    </TabContainer>
                </Jumbotron>
                </div>
        );
        });
    }

    noConflicts() {
        return (
            <p>There are no conflicts</p>
        );
    }

    componentDidMount() {
        if (this.props.loading) {
            this.props.fetchConflicts();
        }
    }

    componentWillReceiveProps(nextProps, nextContext) {
        if (nextProps.conflicts && nextProps.conflicts.length) {
            this.setState({conflicts: this.parseConflicts(nextProps.conflicts)});
            return;
        }
        this.setState({conflicts: this.noConflicts()})
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
            <div>
                <div className="Conflicts">
                    {this.state.conflicts}
                </div>
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