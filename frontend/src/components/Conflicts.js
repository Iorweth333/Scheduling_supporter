import React, {Component} from 'react';
import {connect} from "react-redux";
import '../App.css';
import Jumbotron from 'react-bootstrap/Jumbotron'
import TabContainer from "react-bootstrap/es/TabContainer";
import Conflict from "./Conflict";
import {Spinner} from "react-bootstrap";


class Conflicts extends Component {


    renderConflicts(){

        const {lessonsConflicts, lessons} = this.props;

        if(!lessonsConflicts.length){
            return (
                 <p>There are no conflicts</p>
            );
        }

        if(!lessons){
            return <div><Spinner animation="grow" /></div>;
        }

        return lessonsConflicts.map( (conflict, index) => {
            const lesson1 = lessons.find(lesson => lesson.id === conflict.lessonId1);
            const lesson2 = lessons.find(lesson => lesson.id === conflict.lessonId2);

            return (
                <div key={index}>
                    <Jumbotron fluid style={{padding: "20px"}}>
                        <TabContainer>
                            <h4 className="conflicts">{conflict.typeOfConflict} CONFLICT</h4>
                            <Conflict lesson={lesson1}/>
                            <Conflict lesson={lesson2}/>
                        </TabContainer>
                    </Jumbotron>
                </div>
            );
        });
    };

    render() {

        return (
            <div>
                <div className="Conflicts">
                    {this.renderConflicts()}
                </div>
            </div>
        )
    }
}



function mapStateToProps(state){
    return{
        loading: state.lessons.loading,
        error: state.lessons.error,
        lessons: state.lessons.lessons,
    };
}

export default connect(mapStateToProps, {})(Conflicts);

