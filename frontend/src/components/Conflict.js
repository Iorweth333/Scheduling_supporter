import React, {Component} from 'react';
import '../App.css';

export default class Conflict extends Component {

    render() {
        return (
            <div>
                <p className="conflict">{ this.props.lesson.subject.name }</p>
                <p>{ this.props.lesson.lecturer.name } {this.props.lesson.lecturer.surname}</p>
                <p>students group: {this.props.lesson.studentsGroup.name}</p>
                <p>{ this.props.lesson.date.substring(0, this.props.lesson.date.length - 12) }&emsp;{ this.props.lesson.startsAt } - { this.props.lesson.endsAt }, {this.props.lesson.classroom.number}</p>
            </div>
        );
    }

}