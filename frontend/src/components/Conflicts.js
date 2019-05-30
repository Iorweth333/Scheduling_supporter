import React, {Component} from 'react';
import {connect} from "react-redux";
import {fetchConflicts} from "../actions";
import {Spinner} from "react-bootstrap";
import '../App.css';
import Jumbotron from 'react-bootstrap/Jumbotron'
import TabContainer from "react-bootstrap/es/TabContainer";


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
                        <h2>{ conflict.typeOfConflict }</h2>
                        <h5>{ conflict.lesson1.subject.name }</h5>
                        <h5>{ conflict.lesson2.subject.name }</h5>
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
                <div className="header">
                    <div style={{padding: "50px"}}>
                        <div className="row"><div className="col-xs-12">
                            <div className="buttons_line">
                            <div className="divider">
                                <button className="btn btn-success" onClick={this.redirectToUpload}>Upload Schedule</button>
                            </div>
                            <div className="divider">
                                <button className="btn btn-success" onClick={this.redirectToLessons}>Lessons</button>
                            </div>
                            </div>
                        </div></div>
                    </div>
                </div>
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