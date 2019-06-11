import React, {Component} from 'react';
import '../App.css';

export default class Conflict extends Component {

    render() {
        const {lesson} = this.props;
        if(!lesson){
            return(
                <div></div>
            )
        }
        return (
            <div>
                <p className="conflict">{lesson.subject.name}</p>
                <p>{lesson.lecturer.name} {lesson.lecturer.surname}</p>
                <p>students group: {lesson.studentsGroup.name}</p>
                <p>{lesson.date.substring(0, lesson.date.length - 12)}&emsp;{lesson.startsAt} - {lesson.endsAt}, {lesson.classroom.number}</p>
            </div>
        );
    }

}



