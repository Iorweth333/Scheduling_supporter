import React, {Component} from 'react';
import {connect} from "react-redux";
import {fetchLesson} from "../actions";

class ConflictLesson extends Component {

    constructor(props, lessonId) {
        super(props);

        document.title = "Scheduling Supporter";

        this.state = {
            lessonId: lessonId,
        }
    }

    componentDidMount() {
        this.props.fetchLesson();
    }

    parseLesson(lesson) {
        return (
            <h3>{ lesson.subject.name }</h3>
        )
    }

    componentWillReceiveProps(nextProps, nextContext) {
        if (nextProps.lesson) {
            this.setState({lesson: this.parseLesson(nextProps.lesson)});
        }
    }
}

function mapStateToProps(state){
    return{
        lesson: state.lesson.lesson,
    };
}

export default connect(mapStateToProps, {fetchLesson})(ConflictLesson);